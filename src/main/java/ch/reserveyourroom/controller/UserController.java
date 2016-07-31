package ch.reserveyourroom.controller;

import ch.reserveyourroom.model.User;
import ch.reserveyourroom.service.UserService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by philippe.wanner on 19/07/16.
 */
@Path("/users")
public class UserController {

    @Inject
    private UserService userService;

    @Path("/all")
    @GET
    @Produces("application/json")
    public JsonArray getAll(){
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (User user : userService.getAll()){
            builder.add(Json.createObjectBuilder().add("email", user.getEmail()));
        }
        return builder.build();
    }
}
