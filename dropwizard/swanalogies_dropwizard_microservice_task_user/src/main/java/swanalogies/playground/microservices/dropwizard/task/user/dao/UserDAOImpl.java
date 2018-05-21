package swanalogies.playground.microservices.dropwizard.task.user.dao;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.PersistTo;
import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.error.DocumentDoesNotExistException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import swanalogies.playground.microservices.dropwizard.task.user.api.User;

import java.util.UUID;

@AllArgsConstructor
@Slf4j
public class UserDAOImpl implements UserDAO {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Bucket primaryBucket;

    @Override
    public User createUser(User newUser) throws Exception {
        generateUserID(newUser);
        final String userJSON = MAPPER.writeValueAsString(newUser);
        final RawJsonDocument userDoc = RawJsonDocument.create(newUser.getInternalUserID(), userJSON);
        try {
            primaryBucket.insert(userDoc, PersistTo.ONE);
        } catch (final Exception ex) {
            log.error("Error while creating user", ex);
            throw ex;
        }
        return newUser;
    }

    private void generateUserID(final User user) {
        //this is where you could create an id based on hash of username
        //in order to eliminate duplicate user creation requests
        //for the demo purposes we are simply going to generate a UUID
        user.setInternalUserID("user::" + UUID.randomUUID().toString());
    }

    @Override
    public User getUserByInternalID(final String internalUserID) throws Exception {
        RawJsonDocument userDoc = null;
        log.info("Retrieving user with userID={}", internalUserID);
        try {
            userDoc = primaryBucket.get(internalUserID, RawJsonDocument.class);
        } catch (final DocumentDoesNotExistException ex) {
            log.warn("User with userID={} does not exist", internalUserID);
        } catch (final Exception ex) {
            log.error("Error while retrieving user with userID={}", internalUserID);
            throw ex;
        }

        if (userDoc == null) {
            return null;
        }

        return MAPPER.readValue(userDoc.content(), User.class);
    }

    private User getUserByUserName(String userName) throws Exception {
        return null;
    }

    @Override
    public boolean checkHealth() {
        return true;
    }
}
