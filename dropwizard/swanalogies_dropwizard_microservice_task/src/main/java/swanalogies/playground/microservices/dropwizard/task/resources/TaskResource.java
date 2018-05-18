package swanalogies.playground.microservices.dropwizard.task.resources;

import lombok.AllArgsConstructor;
import swanalogies.playground.microservices.dropwizard.task.api.Task;
import swanalogies.playground.microservices.dropwizard.task.dao.TaskDAO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("v1/tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class TaskResource {

    private TaskDAO dao;

    @GET
    @Path("/{taskID}")
    public Response getTask(@PathParam("taskID") final String taskID) {
        try {
            Task task = dao.getTask(taskID);

            if (task == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .build();
            }

            return Response.status(Response.Status.OK)
                    .entity(task)
                    .build();
        } catch (final Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PUT
    @Path("/{taskID}")
    public Response updateTask(@PathParam("taskID") final String taskID,
                               @NotNull @Valid final Task updatedTask) {
        Task taskUpdated = dao.updateTask(taskID, updatedTask);

        return Response.status(Response.Status.OK).build();
    }

    @POST
    public Response createTask(@NotNull @Valid final Task newTask, @Context UriInfo uriInfo) {
        try {
            Task savedTask = dao.createTask(newTask);

            return Response.status(Response.Status.CREATED)
                    .location(URI.create(uriInfo.getPath() + savedTask.getTaskID()))
                    .build();
        } catch (final Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @DELETE
    public Response deleteTask(@PathParam("taskID") final String taskID) {
        Task deletedTask = dao.deleteTask(taskID);

        return Response.status(Response.Status.OK)
                .entity(deletedTask)
                .build();
    }
}
