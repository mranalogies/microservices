package swanalogies.playground.microservices.dropwizard.task.dao;

import swanalogies.playground.microservices.dropwizard.task.api.Task;

public interface TaskDAO {
    Task createTask(final Task newTask) throws Exception;
    Task getTask(final String taskID) throws Exception;
    Task updateTask(final String taskID, final Task updatedTask);
    Task deleteTask(final String taskID);
}
