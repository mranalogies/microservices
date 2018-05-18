package swanalogies.playground.microservices.dropwizard.task.user.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Date;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User {
    private String internalUserID;
    private String userName;
    private String password;
    private String name;
    private Date registrationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getRegistrationDate() {
        return this.registrationDate;
    }
    //a user registers for the task service
    //a user can submit tasks
    //a user can accept/work on the tasks
}
