package swanalogies.playground.microservices.dropwizard.task.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import com.couchbase.client.core.BackpressureException;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.util.retry.RetryBuilder;
import lombok.AllArgsConstructor;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class TaskHealthCheck extends HealthCheck {
    private final Bucket primaryBucket;

    @Override
    public Result check() throws Exception {
        try {
            primaryBucket.async()
                    .get("dummyValue")
                    .timeout(500, TimeUnit.MILLISECONDS)
                    .toBlocking()
                    .single();
        } catch (final NoSuchElementException ex) {
            return Result.healthy();
        } catch (final Exception ex) {
            return Result.unhealthy("Cannot connect to couchbase");
        }

        return Result.healthy();
    }
}
