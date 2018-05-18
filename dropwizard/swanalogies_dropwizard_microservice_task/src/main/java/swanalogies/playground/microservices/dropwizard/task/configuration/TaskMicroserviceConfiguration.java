package swanalogies.playground.microservices.dropwizard.task.configuration;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TaskMicroserviceConfiguration extends Configuration {

    @NotNull
    @JsonProperty
    private CouchbaseFactory couchbaseFactory;
}
