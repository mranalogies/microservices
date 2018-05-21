package swanalogies.playground.microservices.dropwizard.task.user.resources;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import swanalogies.playground.microservices.dropwizard.task.user.api.User;
import swanalogies.playground.microservices.dropwizard.task.user.dao.UserDAO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("v1/Users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor
@Slf4j
public class UserResource {
    private UserDAO dao;

    @GET()
    @Path("/{userID}")
    public Response getUserByInternalID(@PathParam("userID") final String userID) {
        try {
            User user = dao.getUserByInternalID(userID);
            if (user != null) {
                return Response.status(Response.Status.OK)
                        .entity(user)
                        .build();
            } else {
                //not found
                return Response.status(Response.Status.NOT_FOUND)
                        .build();
            }
        } catch (Exception ex) {
            log.error("Couldn't retrieve user with userID={}", userID, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @POST
    public Response createUser(@NotNull @Valid final User user, @Context UriInfo uriInfo) {
        try {
            User createdUser = dao.createUser(user);

            return Response.status(Response.Status.CREATED)
                    .location(URI.create(uriInfo.getPath() + createdUser.getInternalUserID()))
                    .build();
        } catch (Exception ex) {
            log.error("Couldn't create user", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
