package ufrj.bibliopdfv1.rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

@Path("methods")
public class RestResources {

    @Context
    private UriInfo context;

    public RestResources() {
    }

    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return "{\"msg\":\"OK\"}";
    }

    @PUT
    @Path("put")
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
