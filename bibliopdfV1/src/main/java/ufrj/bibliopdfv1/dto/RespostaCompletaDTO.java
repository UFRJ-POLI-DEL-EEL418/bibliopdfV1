package ufrj.bibliopdfv1.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

public class RespostaCompletaDTO implements Serializable{
    
    private boolean sucesso;
    private int nroRows = 0;
    private int totalNroRows = 0;
    private ArrayList<RespostaDTO> listaDeRespostas = new ArrayList<>();

    public int getTotalNroRows() {
        return totalNroRows;
    }

    public void setTotalNroRows(int totalNroRows) {
        this.totalNroRows = totalNroRows;
    }

    public ArrayList<RespostaDTO> getListaDeRespostas() {
        return listaDeRespostas;
    }

    public void setListaDeRespostas(ArrayList<RespostaDTO> listaDeRespostas) {
        this.listaDeRespostas = listaDeRespostas;
    }

    public Iterator getIterator(){
        return listaDeRespostas.iterator();
    }

    public void addResposta(RespostaDTO resp){
        listaDeRespostas.add(resp);
        nroRows++;
        sucesso = true;
    }
    
    public int getNroRows() {
        return nroRows;
    }

    public void setNroRows(int nroRows) {
        this.nroRows = nroRows;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

// fazer inverso
    public JsonObject toJSON(){
        JsonArrayBuilder jsonListaDeRespostas = Json.createArrayBuilder();
        for (RespostaDTO resp : listaDeRespostas) {
            jsonListaDeRespostas.add(resp.toJson());
        }

        JsonObject jsonRspostaCompletaDTO = Json.createObjectBuilder()
            .add("sucesso",sucesso)
            .add("nroRows",(Integer.toString(nroRows)))
            .add("totalNroRows",(Integer.toString(totalNroRows)))
            .add("arrayDeRespostas",jsonListaDeRespostas.build())
            .build();
        
        return jsonRspostaCompletaDTO;
    }

    @Override
    public String toString(){
        return toJSON().toString();
    }
}
