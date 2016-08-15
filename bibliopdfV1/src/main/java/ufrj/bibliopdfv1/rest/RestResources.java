package ufrj.bibliopdfv1.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import javax.json.Json;
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
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;

@Path("services")
public class RestResources {

    @Context
    private UriInfo context;

    public RestResources() {
    }

    @PUT
    @Path("put")
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
    
    @GET
    @Path("searchbyid/{offset}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String searchbyid(
            @PathParam("offset") String offset,
            @PathParam("id") String id) {
        RespostaCompletaDTO respostaCompleta = null;
        respostaCompleta = new BiblioPDFDAO().searchbyid(id);
        return respostaCompleta.toString();
    }
    
    @GET
    @Path("searchbyid/{offset}")
    @Produces(MediaType.APPLICATION_JSON)
    public String searchbyid(@PathParam("offset") String offset) {
        RespostaCompletaDTO respostaCompleta = null;
        respostaCompleta = new BiblioPDFDAO().getall(offset);
        return respostaCompleta.toString();
    }
    
    @POST
    @Path("compositesearch/{offset}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String compositesearch(
                        @PathParam("offset") String offset,
                        @Context HttpServletRequest request,
                        @Context HttpServletResponse response) {
        RespostaCompletaDTO respostaCompleta = 
                new BiblioPDFDAO().compositeSearch(getRequestJson(request),offset);
        return respostaCompleta.toString();
    }
    
    private JsonObject getRequestJson(HttpServletRequest request){
        JsonObject jsonDoPedido = null;
        try{
            BufferedReader br = new BufferedReader(
                                    new InputStreamReader(
                                            request.getInputStream(),"UTF8"));
            String json = br.readLine();
            br.close();
            JsonReader reader = Json.createReader(new StringReader(json));
            jsonDoPedido = reader.readObject();
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return jsonDoPedido;
    }
    
}
