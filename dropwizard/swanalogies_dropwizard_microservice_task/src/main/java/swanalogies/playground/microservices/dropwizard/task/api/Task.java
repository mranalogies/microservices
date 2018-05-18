package swanalogies.playground.microservices.dropwizard.task.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Date;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Task {
    private String taskID;
    private String taskName;
    private String taskDescription;
    private String taskType;
    private String taskStatus;
    private Date taskDueDate;
    private Date taskCreatedDate;
    private String createdByUserID;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date getTaskDueDate() {
        return this.taskDueDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date getTaskCreatedDate() {
        return this.taskCreatedDate;
    }
}
