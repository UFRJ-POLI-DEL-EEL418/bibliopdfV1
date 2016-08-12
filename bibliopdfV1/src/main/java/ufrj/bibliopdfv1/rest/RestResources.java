package ufrj.bibliopdfv1.rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import ufrj.bibliopdfv1.dao.BiblioPDFDAO;
import ufrj.bibliopdfv1.dto.RespostaCompletaDTO;

@Path("services")
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
    
    @GET
    @Path("searchbyid/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("id") String id) {
        RespostaCompletaDTO respostaCompleta = null;
        respostaCompleta = new BiblioPDFDAO().searchbyid(id);
        return respostaCompleta.toString();
    }
    
}
