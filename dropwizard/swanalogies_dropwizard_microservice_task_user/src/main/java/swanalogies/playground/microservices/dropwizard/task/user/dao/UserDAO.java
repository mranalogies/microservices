package swanalogies.playground.microservices.dropwizard.task.user.dao;

import swanalogies.playground.microservices.dropwizard.task.user.api.User;

public interface UserDAO {

    User createUser(final User newUser) throws Exception;
    User getUserByInternalID(final String internalUserID) throws Exception;
    boolean checkHealth();
}
