package swanalogies.playground.microservices.dropwizard.task.user;

import com.couchbase.client.java.Bucket;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import swanalogies.playground.microservices.dropwizard.task.user.configuration.TaskUserMicroserviceConfiguration;
import swanalogies.playground.microservices.dropwizard.task.user.dao.UserDAO;
import swanalogies.playground.microservices.dropwizard.task.user.dao.UserDAOImpl;
import swanalogies.playground.microservices.dropwizard.task.user.healthcheck.TaskUserMicroserviceHealthCheck;
import swanalogies.playground.microservices.dropwizard.task.user.resources.UserResource;

public class TaskUserApplication extends Application<TaskUserMicroserviceConfiguration> {

    public static void main(String[] args) throws Exception {
        new TaskUserApplication().run(args);
    }

    @Override
    public void run(TaskUserMicroserviceConfiguration taskUserMicroserviceConfiguration, Environment environment) throws Exception {
        //setup couchbase connection
        final Bucket primaryBucket = taskUserMicroserviceConfiguration.getCouchbaseFactory().build(environment);

        //create dao
        final UserDAO dao = new UserDAOImpl(primaryBucket);

        //create resource
        final UserResource userResource = new UserResource(dao);

        //create healthcheck
        final TaskUserMicroserviceHealthCheck userMicroserviceHealthCheck = new TaskUserMicroserviceHealthCheck(dao);

        //register resource
        environment.jersey().register(userResource);

        //register healthcheck
        environment.healthChecks().register("Couchbase", userMicroserviceHealthCheck);

    }
}
