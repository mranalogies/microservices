package swanalogies.playground.microservices.dropwizard.task;

import com.couchbase.client.java.Bucket;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import swanalogies.playground.microservices.dropwizard.task.configuration.TaskMicroserviceConfiguration;
import swanalogies.playground.microservices.dropwizard.task.dao.TaskDAO;
import swanalogies.playground.microservices.dropwizard.task.dao.TaskDAOImpl;
import swanalogies.playground.microservices.dropwizard.task.healthcheck.TaskHealthCheck;
import swanalogies.playground.microservices.dropwizard.task.resources.TaskResource;

@Slf4j
public class TaskApplication extends Application<TaskMicroserviceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new TaskApplication().run(args);
    }

    @Override
    public void run(TaskMicroserviceConfiguration taskMicroserviceConfiguration, Environment environment) throws Exception {
        log.info("Starting Task Microservice...");

        final Bucket bucket = taskMicroserviceConfiguration.getCouchbaseFactory().build(environment);
        final TaskDAO taskDAO = new TaskDAOImpl(bucket);
        final TaskResource taskResource = new TaskResource(taskDAO);
        final TaskHealthCheck taskHealthCheck = new TaskHealthCheck(bucket);

        environment.jersey().register(taskResource);
        environment.healthChecks().register("Couchbase", taskHealthCheck);
    }
}
