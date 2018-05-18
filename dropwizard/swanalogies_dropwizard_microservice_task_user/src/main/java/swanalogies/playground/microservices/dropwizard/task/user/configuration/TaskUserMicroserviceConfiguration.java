package swanalogies.playground.microservices.dropwizard.task.user.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TaskUserMicroserviceConfiguration extends Configuration {

    @NotNull
    @JsonProperty
    private CouchbaseFactory couchbaseFactory;
}
