package swanalogies.playground.microservices.dropwizard.task.user.configuration;

import com.couchbase.client.core.event.CouchbaseEvent;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Data
@Slf4j
public class CouchbaseFactory {

    @NotNull
    @JsonProperty
    private String bucketName;

    @NotNull
    @JsonProperty
    private String password;

    @NotNull
    @JsonProperty
    private String[] primaryCouchbaseCluster;

    @NotNull
    @JsonProperty
    private Long connectTimeout;

    public Bucket build(final Environment environment) throws Exception {
        try {
            final CouchbaseEnvironment couchbaseEnvironment = DefaultCouchbaseEnvironment.builder()
                    .connectTimeout(connectTimeout)
                    .build();

            couchbaseEnvironment.eventBus().get().subscribe(CouchbaseFactory::writeCouchbaseEvents);

            final Cluster primaryCluster = CouchbaseCluster.create(couchbaseEnvironment, Arrays.asList(primaryCouchbaseCluster));
            final Bucket primaryBucket = primaryCluster.openBucket(bucketName, password);

            environment.lifecycle().manage(new Managed() {
                @Override
                public void start() throws Exception {

                }

                @Override
                public void stop() throws Exception {
                    log.info("Shutting down couchbase client");
                    primaryBucket.close();

                    primaryCluster.disconnect();
                }
            });

            return primaryBucket;
        } catch (final Exception ex) {
            log.error("Error connecting to couchbase", ex);
            throw ex;
        }
    }

    private static void writeCouchbaseEvents(final CouchbaseEvent couchbaseEvent) {
        log.info("CouchbaseEvent: {}", couchbaseEvent.toString());
    }
}
