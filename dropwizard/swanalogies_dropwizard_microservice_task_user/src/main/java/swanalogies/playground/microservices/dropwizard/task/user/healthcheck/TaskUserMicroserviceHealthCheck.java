package swanalogies.playground.microservices.dropwizard.task.user.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import lombok.AllArgsConstructor;
import swanalogies.playground.microservices.dropwizard.task.user.dao.UserDAO;

@AllArgsConstructor
public class TaskUserMicroserviceHealthCheck extends HealthCheck {
    private UserDAO dao;

    @Override
    protected Result check() throws Exception {
        if (dao.checkHealth()) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Cannot connect to database");
        }
    }
}
