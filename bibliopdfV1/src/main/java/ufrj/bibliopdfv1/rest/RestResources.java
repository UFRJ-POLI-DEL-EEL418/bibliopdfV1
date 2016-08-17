package ufrj.bibliopdfv1.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
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
import javax.servlet.http.Part;
import javax.ws.rs.POST;
import ufrj.bibliopdfv1.Iniciador;

@Path("services")
public class RestResources {

    @Context
    private UriInfo context;

    public RestResources() {
    }

    @POST
    @Path("savenew")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String saveNew(@Context HttpServletRequest request) {
        RespostaCompletaDTO respostaCompleta = 
                new BiblioPDFDAO().saveNew(getRequestJson(request));
        return respostaCompleta.toString();
    }
    
    @PUT
    @Path("savemodif/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String saveModif(
            @Context HttpServletRequest request,
            @PathParam("id") String id) {
        RespostaCompletaDTO respostaCompleta = 
                new BiblioPDFDAO().saveModif(getRequestJson(request),id);
        return respostaCompleta.toString();
    }
    
    @POST
    @Path("uploadfile/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String uploadFile(
            @Context HttpServletRequest request,
            @PathParam("id") String id) {
        boolean saved = false;
        String nomeArq = null;
        Part part = null;
        try {
            ArrayList parts = (ArrayList) request.getParts();
            Iterator itr = parts.iterator();
            while (itr.hasNext()) {
                part = (Part) itr.next();
                if (part.getName().compareTo("htmlFileElementName") == 0) {
                    nomeArq = extractFileName(part);
//System.out.println("[uploadFile] nomeArq: "+nomeArq);                    
                    String filePath = Iniciador.FILES_DIRECTORY_FULL_PATH + id;
//System.out.println("[uploadFile] filePath: "+filePath);                    
                    part.write(filePath);
                    saved = new BiblioPDFDAO().salvarNomeArquivo(id,nomeArq);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saved?"success":"failed";
    }
//--------------------------------------------------------
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
//--------------------------------------------------------
    
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
