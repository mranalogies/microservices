package swanalogies.playground.microservices.dropwizard.task.dao;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.PersistTo;
import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.error.DocumentDoesNotExistException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import swanalogies.playground.microservices.dropwizard.task.api.Task;

import java.util.UUID;

@AllArgsConstructor
@Slf4j
public class TaskDAOImpl implements TaskDAO {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private final Bucket primaryBucket;

    @Override
    public Task createTask(Task newTask) throws Exception {
        generateTaskID(newTask);
        final String taskJSON = MAPPER.writeValueAsString(newTask);
        log.info(taskJSON);
        final RawJsonDocument taskDoc = RawJsonDocument.create(newTask.getTaskID(), taskJSON);
        log.info(taskDoc.content());
        try {
            primaryBucket.insert(taskDoc, PersistTo.ONE);
        } catch (final Exception ex) {
            log.error("Error while creating task", ex);
            throw ex;
        }
        return newTask;
    }

    private void generateTaskID(Task task) {
        //this is where you could create an id based on hash of task properties
        //in order to eliminate duplicate task creation requests
        //for the demo purposes we are simply going to generate a UUID
        String id = "task::" + UUID.randomUUID();
        task.setTaskID(id);
    }

    @Override
    public Task getTask(String taskID) throws Exception {
        RawJsonDocument taskDoc = null;
        try {
            taskDoc = primaryBucket.get(taskID, RawJsonDocument.class);
        } catch (final DocumentDoesNotExistException ex) {
            log.warn("Task with taskID={} does not exist", taskID);
        } catch (final Exception ex) {
            log.error("Error while retrieving task with taskID={}", taskID);
            throw ex;
        }

        if (taskDoc == null) {
            return null;
        }

        return MAPPER.readValue(taskDoc.content(), Task.class);
    }

    @Override
    public Task updateTask(String taskID, Task updatedTask) {
        return null;
    }

    @Override
    public Task deleteTask(String taskID) {
        return null;
    }
}
