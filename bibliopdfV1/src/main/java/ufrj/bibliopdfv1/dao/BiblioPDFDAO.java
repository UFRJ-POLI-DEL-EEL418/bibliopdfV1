package ufrj.bibliopdfv1.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.Normalizer;
import java.util.Set;
import javax.json.JsonObject;
import ufrj.bibliopdfv1.Iniciador;
import ufrj.bibliopdfv1.dto.RespostaCompletaDTO;
import ufrj.bibliopdfv1.dto.RespostaDTO;
import utils.Utils;

public class BiblioPDFDAO extends BaseDAO {

//------------------------------------------------------------------------------ 
    public RespostaCompletaDTO searchbyid(String id) {
        RespostaCompletaDTO listaRefsDTO = new RespostaCompletaDTO();
        RespostaDTO umaRefDTO = new RespostaDTO();
        Connection conexao = null;
        try {
            conexao = getConnection();
            PreparedStatement comandoSQL = conexao.prepareStatement(
                    "SELECT * FROM dadoscatalogo WHERE patrimonio=?;");

            comandoSQL.setLong(1, Long.parseLong(id));

            ResultSet rs = comandoSQL.executeQuery();
            long patrimonio = 0L;
            if (rs.next()) {
                patrimonio = rs.getLong("patrimonio");
                umaRefDTO.setPatrimonio(Long.toString(patrimonio));
                umaRefDTO.setTitulo(rs.getString("titulo"));
                umaRefDTO.setAutoria(rs.getString("autoria"));
                umaRefDTO.setVeiculo(rs.getString("veiculo"));
                umaRefDTO.setNomeOriginalArquivo(rs.getString("nomeoriginalarquivo"));
                umaRefDTO.setData_publicacao(rs.getString("data_publicacao"));
                umaRefDTO.setNrohits("1");
                umaRefDTO.setPalchave(buscarPalavrasChave(patrimonio));
//System.out.println("=== RefDTO: "+umaRefDTO.toString());                
            } else {
                umaRefDTO.setPatrimonio("0");
                umaRefDTO.setTitulo("ERRO");
            }
        } catch (Exception e) {
            umaRefDTO.setPatrimonio("0");
            umaRefDTO.setTitulo("ERRO");
            e.printStackTrace();
        }

        try {
            if (conexao != null) {
                conexao.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        listaRefsDTO.addResposta(umaRefDTO);
        return listaRefsDTO;
    }
//------------------------------------------------------------------------------
    public boolean contains(Object[] keys, String key) {
        boolean yes = false;
        for (Object temp : keys) {
            if (((String) temp).equals(key)) {
                yes = true;
            }
        }
        return yes;
    }
//------------------------------------------------------------------------------
    public RespostaCompletaDTO deleteReference(String id) {
        RespostaCompletaDTO listaRefsDTO = new RespostaCompletaDTO();
        RespostaDTO umaRefDTO = new RespostaDTO();
        listaRefsDTO.addResposta(umaRefDTO);

        try {
            File file = new File(Iniciador.FILES_DIRECTORY_FULL_PATH+id);
            if(file.delete()){
                listaRefsDTO.setSucesso(true);
System.out.println(file.getName() + " is deleted!");
            }else{
                listaRefsDTO.setSucesso(false);
System.out.println("Delete operation is failed.");
                return listaRefsDTO;
            }
        }catch(Exception e){
            listaRefsDTO.setSucesso(false);
            e.printStackTrace();
            return listaRefsDTO;
        }

        long patrimonio = Long.parseLong(id);
        try {
            Connection conexao = getConnection();
            PreparedStatement comandoSQL = conexao.prepareStatement(
                    "DELETE FROM dadoscatalogo WHERE patrimonio=?;");
            comandoSQL.setLong(1, patrimonio);
            int count = comandoSQL.executeUpdate();
            if (count != 1) {
                listaRefsDTO.setSucesso(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            listaRefsDTO.setSucesso(false);
        }
        
        return listaRefsDTO;
    }
//------------------------------------------------------------------------------
    public RespostaCompletaDTO deleteFile(String id) {
        RespostaCompletaDTO listaRefsDTO = new RespostaCompletaDTO();
        RespostaDTO umaRefDTO = new RespostaDTO();
        listaRefsDTO.addResposta(umaRefDTO);
        try {
            File file = new File(Iniciador.FILES_DIRECTORY_FULL_PATH+"/"+id);
            if(file.delete()){
                listaRefsDTO.setSucesso(true);
System.out.println(file.getName() + " is deleted!");
            }else{
                listaRefsDTO.setSucesso(false);
System.out.println("Delete operation is failed.");
                return listaRefsDTO;
            }
        }catch(Exception e){
            listaRefsDTO.setSucesso(false);
            e.printStackTrace();
            return listaRefsDTO;
        }

        long patrimonio = Long.parseLong(id);
        try {
            Connection conexao = getConnection();
            PreparedStatement comandoSQL = conexao.prepareStatement(
                    "UPDATE dadoscatalogo SET nomeoriginalarquivo=DEFAULT"+
                    " WHERE patrimonio=?;");
            comandoSQL.setLong(1, patrimonio);
            int count = comandoSQL.executeUpdate();
            if (count != 1) {
                listaRefsDTO.setSucesso(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            listaRefsDTO.setSucesso(false);
        }
        return listaRefsDTO;
    }
//------------------------------------------------------------------------------

    public RespostaCompletaDTO compositeSearch(JsonObject dados, String offset) {
        RespostaCompletaDTO listaRefsDTO = new RespostaCompletaDTO();
        RespostaDTO umaRefDTO = null;

        Set<String> okeys = dados.keySet();
        Object[] keys = okeys.toArray();

        String preparedStatement = prepararComandoSQL3(dados, offset);
        String preparedStatementView = prepararSQLcount(dados, offset);
//System.out.println("--- [compositeSearch] String preparedStatement: "+preparedStatement);

        try (Connection conexao = getConnection()) {

            PreparedStatement comandoCountSQL = conexao.prepareStatement(preparedStatementView);
            PreparedStatement comandoSQL = conexao.prepareStatement(preparedStatement);

            int deslocamento = 0;

            if (contains(keys, "titulo") && !dados.getString("titulo").isEmpty()) {
                String[] palavrasDoTitulo = extrairPalavrasDeCampo(dados, "titulo");
                for (int i = 0; i < palavrasDoTitulo.length; i++) {
                    comandoSQL.setString(i + 1, palavrasDoTitulo[i]);
                    comandoCountSQL.setString(i + 1, palavrasDoTitulo[i]);
                }
                deslocamento = palavrasDoTitulo.length;
            }
//System.out.println("=== [compositeSearch] deslocamento(depois de titulo): "+deslocamento);

            if (contains(keys, "autoria") && !dados.getString("autoria").isEmpty()) {
                String[] palavrasDaAutoria = extrairPalavrasDeCampo(dados, "autoria");
                for (int i = 0; i < palavrasDaAutoria.length; i++) {
                    comandoSQL.setString(i + 1 + deslocamento, palavrasDaAutoria[i]);
                    comandoCountSQL.setString(i + 1 + deslocamento, palavrasDaAutoria[i]);
                }
                deslocamento += palavrasDaAutoria.length;
            }
//System.out.println("=== [compositeSearch] deslocamento(depois de autoria): "+deslocamento);

            if (contains(keys, "veiculo") && !dados.getString("veiculo").isEmpty()) {
                String[] palavrasDoVeiculo = extrairPalavrasDeCampo(dados, "veiculo");
                for (int i = 0; i < palavrasDoVeiculo.length; i++) {
                    comandoSQL.setString(i + 1 + deslocamento, palavrasDoVeiculo[i]);
                    comandoCountSQL.setString(i + 1 + deslocamento, palavrasDoVeiculo[i]);
                }
                deslocamento += palavrasDoVeiculo.length;
            }
//System.out.println("=== [compositeSearch] deslocamento(depois de veiculo): "+deslocamento);

            if (contains(keys, "data_publicacao1") && !dados.getString("data_publicacao1").isEmpty()) {
                comandoSQL.setString(deslocamento + 1, dados.getString("data_publicacao1"));
                comandoCountSQL.setString(deslocamento + 1, dados.getString("data_publicacao1"));
                comandoSQL.setString(deslocamento + 2, dados.getString("data_publicacao2"));
                comandoCountSQL.setString(deslocamento + 2, dados.getString("data_publicacao2"));
                deslocamento += 2;
            }
//System.out.println("=== [compositeSearch] deslocamento(depois de data_publicacao 1 e 2): "+deslocamento);

            if (contains(keys, "palchave") && !dados.getString("palchave").isEmpty()) {
                String[] palChaveNormalizadas = separarPalChaveNormalizadas(dados);
                for (int i = 0; i < palChaveNormalizadas.length; i++) {
                    comandoSQL.setString(i + 1 + deslocamento, palChaveNormalizadas[i]);
                    comandoCountSQL.setString(i + 1 + deslocamento, palChaveNormalizadas[i]);
                }
                deslocamento += palChaveNormalizadas.length;
            }
//System.out.println("=== [compositeSearch] deslocamento(depois de palchave): "+deslocamento);

            comandoSQL.setInt(deslocamento + 1, Integer.parseInt(dados.getString("offset")));

            System.out.println("  comandoSQL -----------------------\n" + comandoSQL + "\n");
            System.out.println("  comandoViewSQL -------------------\n" + comandoCountSQL + "\n");

            // BEGIN TRANSACTION
            conexao.setAutoCommit(false);

            ResultSet rs = comandoCountSQL.executeQuery();
            if (rs.next()) {
                listaRefsDTO.setTotalNroRows(rs.getInt(1));
                System.out.println("======== TOTAL NRO ROWS: " + listaRefsDTO.getTotalNroRows());
            }

            long patrimonio = 0;
            rs = comandoSQL.executeQuery();
            while (rs.next()) {
                patrimonio = rs.getLong("patrimonio");
                umaRefDTO = new RespostaDTO();
                umaRefDTO.setPatrimonio(Long.toString(patrimonio));
                umaRefDTO.setTitulo(rs.getString("titulo"));
                umaRefDTO.setAutoria(rs.getString("autoria"));
                umaRefDTO.setVeiculo(rs.getString("veiculo"));
                umaRefDTO.setNomeOriginalArquivo(rs.getString("nomeoriginalarquivo"));
                umaRefDTO.setData_publicacao(rs.getString("data_publicacao"));
                umaRefDTO.setNrohits(Integer.toString(rs.getInt("nrohits")));
                umaRefDTO.setPalchave(buscarPalavrasChave(patrimonio));
//System.out.println("=== RefDTO: "+umaRefDTO.toString());                
                listaRefsDTO.addResposta(umaRefDTO);
            }

            // END TRANSACTION
            conexao.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaRefsDTO;
    }
//------------------------------------------------------------------------------    

    private String buscarPalavrasChave(long patrimonio) {
        String palChave = "";
        Connection conexao = null;
        try {
            conexao = getConnection();
            PreparedStatement comandoSQL = conexao.prepareStatement(
                    "SELECT * FROM palavras_chave WHERE patrimonio = ?;");
            comandoSQL.setLong(1, patrimonio);
            ResultSet rs = comandoSQL.executeQuery();
            while (rs.next()) {
                palChave += rs.getString("palChave") + ";";
            }
        } catch (Exception e) {
            palChave = "?";
            e.printStackTrace();
        }
        try {
            if (conexao != null) {
                conexao.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return palChave;
    }
//------------------------------------------------------------------------------    

    private String prepararComandoSQL3(JsonObject dados, String offset) {

        String inicioComando
                = "select T6.patrimonio, T6.titulo, T6.autoria, T6.veiculo,"
                + " T6.nomeoriginalarquivo, T6.data_publicacao, T6.titulonormal,"
                + " sum(dm) as nrohits from (\n";

        String inicioOpcaoTitulo
                = "(select T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo,"
                + " T1.nomeoriginalarquivo, T1.data_publicacao, T1.titulonormal,\n"
                + " (count(*)) AS dm from dadoscatalogo T1 \n"
                + "inner join palavrastitulonormal T2 on (T1.patrimonio=T2.patrimonio) "
                + "where \n";

        String parametroTitulo
                = "T2.palavra_titulo_normal like ? \n";

        String or = "OR \n";

        String fimOpcaoTitulo
                = "group by T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, "
                + "T1.nomeoriginalarquivo, T1.data_publicacao, T1.titulonormal) \n";

        String union = "union all\n";

        String inicioOpcaoAutoria
                = "(select T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, "
                + " T1.nomeoriginalarquivo, T1.data_publicacao, T1.titulonormal,\n"
                + " (count(*)) AS dm from dadoscatalogo T1 \n"
                + " inner join palavrasautorianormal T3 on (T1.patrimonio=T3.patrimonio) "
                + " where \n";

        String parametroAutoria
                = " T3.palavra_autoria_normal like ? \n";

        String fimOpcaoAutoria
                = " group by T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, "
                + " T1.nomeoriginalarquivo, T1.data_publicacao, T1.titulonormal) \n";

        String inicioOpcaoVeiculo
                = "(select T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo,"
                + " T1.nomeoriginalarquivo, T1.data_publicacao, T1.titulonormal,\n"
                + " (count(*)) AS dm from dadoscatalogo T1 \n"
                + " inner join palavrasveiculonormal T4 on (T1.patrimonio=T4.patrimonio)"
                + " where \n";

        String parametroVeiculo
                = "T4.palavra_veiculo_normal like ? \n";

        String fimOpcaoVeiculo
                = "group by T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, "
                + "T1.nomeoriginalarquivo, T1.data_publicacao, T1.titulonormal) \n";

        String opcaoDataPublicacao
                = "(select T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, "
                + " T1.nomeoriginalarquivo, T1.data_publicacao, T1.titulonormal,\n"
                + " (count(*)) AS dm from dadoscatalogo T1 where \n"
                + " T1.data_publicacao>= ? and T1.data_publicacao<= ? \n"
                + " group by T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, "
                + " T1.nomeoriginalarquivo, T1.data_publicacao) \n";

        String inicioOpcaoPalChave
                = "(select T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, "
                + " T1.nomeoriginalarquivo, T1.data_publicacao, T1.titulonormal,\n"
                + " (count(*)) AS dm from dadoscatalogo T1 \n"
                + " inner join palavras_chave T5 on (T1.patrimonio=T5.patrimonio) where \n";

        String parametroPalChave
                = "T5.palchavenormal = ? \n";

        String fimOpcaoPalChave
                = "group by T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, "
                + "T1.nomeoriginalarquivo, T1.data_publicacao, T1.titulonormal) \n";

        String fimComando
                = ") as T6 group by 1,2,3,4,5,6,7 ORDER BY nrohits DESC,titulonormal ASC"
                + " LIMIT 5 OFFSET ?;";

        Set<String> okeys = dados.keySet();
        Object[] keys = okeys.toArray();

        //===== INICIO
        String comando = inicioComando;
        if (contains(keys, "titulo") && !dados.getString("titulo").isEmpty()) {
            String[] palavrasDoTitulo = extrairPalavrasDeCampo(dados, "titulo");
            comando = comando + inicioOpcaoTitulo;
            for (int i = 0; i < palavrasDoTitulo.length; i++) {
                comando = comando + parametroTitulo;
                if (i < (palavrasDoTitulo.length - 1)) {
                    comando = comando + "OR \n";
                }
            }
            comando = comando + fimOpcaoTitulo;
        }

        if (contains(keys, "autoria") && !dados.getString("autoria").isEmpty()) {

            if (contains(keys, "titulo") && !dados.getString("titulo").isEmpty()) {
                comando = comando + union;
            }

            String[] palavrasDaAutoria = extrairPalavrasDeCampo(dados, "autoria");
            comando = comando + inicioOpcaoAutoria;
            for (int i = 0; i < palavrasDaAutoria.length; i++) {
                comando = comando + parametroAutoria;
                if (i < (palavrasDaAutoria.length - 1)) {
                    comando = comando + "OR \n";
                }
            }
            comando = comando + fimOpcaoAutoria;
        }

        if (contains(keys, "veiculo") && !dados.getString("veiculo").isEmpty()) {

            if (    (contains(keys, "titulo") && !dados.getString("titulo").isEmpty())||
                    (contains(keys, "autoria") && !dados.getString("autoria").isEmpty())) {
                comando = comando + union;
            }

            String[] palavrasDoVeiculo = extrairPalavrasDeCampo(dados, "veiculo");
            comando = comando + inicioOpcaoVeiculo;
            for (int i = 0; i < palavrasDoVeiculo.length; i++) {
                comando = comando + parametroVeiculo;
                if (i < (palavrasDoVeiculo.length - 1)) {
                    comando = comando + "OR \n";
                }
            }
            comando = comando + fimOpcaoVeiculo;
        }

        if (contains(keys, "data_publicacao1") && !dados.getString("data_publicacao1").isEmpty()) {
            if (    (contains(keys, "titulo") && !dados.getString("titulo").isEmpty())||
                    (contains(keys, "autoria") && !dados.getString("autoria").isEmpty())||
                    (contains(keys, "veiculo") && !dados.getString("veiculo").isEmpty())) {
                comando = comando + union;
            }
            comando = comando + opcaoDataPublicacao;
        }

        if (contains(keys, "palchave") && !dados.getString("palchave").isEmpty()) {

            if (    (contains(keys, "titulo") && !dados.getString("titulo").isEmpty())||
                    (contains(keys, "autoria") && !dados.getString("autoria").isEmpty())||
                    (contains(keys, "veiculo") && !dados.getString("veiculo").isEmpty())||
                    (contains(keys, "data_publicacao1") && !dados.getString("data_publicacao1").isEmpty())) {
                comando = comando + union;
            }

            String[] palChaveNormalizadas = separarPalChaveNormalizadas(dados);
            comando = comando + inicioOpcaoPalChave;
            for (int i = 0; i < palChaveNormalizadas.length; i++) {
                comando = comando + parametroPalChave;
                if (i < (palChaveNormalizadas.length - 1)) {
                    comando = comando + "OR \n";
                }
            }
            comando = comando + fimOpcaoPalChave;
        }

        comando = comando + fimComando;
        return comando;
    }
//------------------------------------------------------------------------------    

    private String prepararSQLcount(JsonObject dados, String offset) {

        String inicioComando
                = "select count(*) from (\n"
                + "select T6.patrimonio, T6.titulo, T6.autoria, T6.veiculo,"
                + " T6.nomeoriginalarquivo, T6.data_publicacao,"
                + " sum(dm) as nrohits from (\n";

        String inicioOpcaoTitulo
                = "(select T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo,"
                + " T1.nomeoriginalarquivo, T1.data_publicacao,\n"
                + "(count(*)) AS dm from dadoscatalogo T1 \n"
                + "inner join palavrastitulonormal T2 on (T1.patrimonio=T2.patrimonio) "
                + "where \n";

        String parametroTitulo
                = "T2.palavra_titulo_normal like ? \n";

        String or = "OR \n";

        String fimOpcaoTitulo
                = "group by T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, "
                + "T1.nomeoriginalarquivo, T1.data_publicacao) \n";

        String union = "union all\n";

        String inicioOpcaoAutoria
                = "(select T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, "
                + "T1.nomeoriginalarquivo, T1.data_publicacao,\n"
                + "(count(*)) AS dm from dadoscatalogo T1 \n"
                + "inner join palavrasautorianormal T3 on (T1.patrimonio=T3.patrimonio) "
                + "where \n";

        String parametroAutoria
                = "T3.palavra_autoria_normal like ? \n";

        String fimOpcaoAutoria
                = "group by T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, "
                + "T1.nomeoriginalarquivo, T1.data_publicacao) \n";

        String inicioOpcaoVeiculo
                = "(select T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, "
                + "T1.nomeoriginalarquivo, T1.data_publicacao, \n"
                + "(count(*)) AS dm from dadoscatalogo T1 \n"
                + "inner join palavrasveiculonormal T4 on (T1.patrimonio=T4.patrimonio) "
                + "where \n";

        String parametroVeiculo
                = "T4.palavra_veiculo_normal like ? \n";

        String fimOpcaoVeiculo
                = "group by T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, "
                + "T1.nomeoriginalarquivo, T1.data_publicacao) \n";

        String opcaoDataPublicacao
                = "(select T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, "
                + "T1.nomeoriginalarquivo, T1.data_publicacao,\n"
                + "(count(*)) AS dm from dadoscatalogo T1 where \n"
                + "T1.data_publicacao>= ? and T1.data_publicacao<= ? \n"
                + "group by T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, "
                + "T1.nomeoriginalarquivo, T1.data_publicacao) \n";

        String inicioOpcaoPalChave
                = "(select T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, "
                + "T1.nomeoriginalarquivo, T1.data_publicacao, \n"
                + "(count(*)) AS dm from dadoscatalogo T1 \n"
                + "inner join palavras_chave T5 on (T1.patrimonio=T5.patrimonio) where \n";

        String parametroPalChave
                = "T5.palchavenormal = ? \n";

        String fimOpcaoPalChave
                = "group by T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, "
                + "T1.nomeoriginalarquivo, T1.data_publicacao) \n";

        String fimComando
                = ") as T6 group by 1,2,3,4,5,6\n"
                + ") as t7;";

        Set<String> okeys = dados.keySet();
        Object[] keys = okeys.toArray();

        //===== INICIO
        String comando = inicioComando;
        if (contains(keys, "titulo") && !dados.getString("titulo").isEmpty()) {
            String[] palavrasDoTitulo = extrairPalavrasDeCampo(dados, "titulo");
            comando = comando + inicioOpcaoTitulo;
            for (int i = 0; i < palavrasDoTitulo.length; i++) {
                comando = comando + parametroTitulo;
                if (i < (palavrasDoTitulo.length - 1)) {
                    comando = comando + "OR \n";
                }
            }
            comando = comando + fimOpcaoTitulo;
        }

        if (contains(keys, "autoria") && !dados.getString("autoria").isEmpty()) {

            if (contains(keys, "titulo") && !dados.getString("titulo").isEmpty()) {
                comando = comando + union;
            }

            String[] palavrasDaAutoria = extrairPalavrasDeCampo(dados, "autoria");
            comando = comando + inicioOpcaoAutoria;
            for (int i = 0; i < palavrasDaAutoria.length; i++) {
                comando = comando + parametroAutoria;
                if (i < (palavrasDaAutoria.length - 1)) {
                    comando = comando + "OR \n";
                }
            }
            comando = comando + fimOpcaoAutoria;
        }

        if (contains(keys, "veiculo") && !dados.getString("veiculo").isEmpty()) {

            if (    (contains(keys, "titulo") && !dados.getString("titulo").isEmpty())||
                    (contains(keys, "autoria") && !dados.getString("autoria").isEmpty())) {
                comando = comando + union;
            }

            String[] palavrasDoVeiculo = extrairPalavrasDeCampo(dados, "veiculo");
            comando = comando + inicioOpcaoVeiculo;
            for (int i = 0; i < palavrasDoVeiculo.length; i++) {
                comando = comando + parametroVeiculo;
                if (i < (palavrasDoVeiculo.length - 1)) {
                    comando = comando + "OR \n";
                }
            }
            comando = comando + fimOpcaoVeiculo;
        }

        if (contains(keys, "data_publicacao1") && !dados.getString("data_publicacao1").isEmpty()) {
            if (    (contains(keys, "titulo") && !dados.getString("titulo").isEmpty())||
                    (contains(keys, "autoria") && !dados.getString("autoria").isEmpty())||
                    (contains(keys, "veiculo") && !dados.getString("veiculo").isEmpty())) {
                comando = comando + union;
            }
            comando = comando + opcaoDataPublicacao;
        }

        if (contains(keys, "palchave") && !dados.getString("palchave").isEmpty()) {

            if (    (contains(keys, "titulo") && !dados.getString("titulo").isEmpty())||
                    (contains(keys, "autoria") && !dados.getString("autoria").isEmpty())||
                    (contains(keys, "veiculo") && !dados.getString("veiculo").isEmpty())||
                    (contains(keys, "data_publicacao1") && !dados.getString("data_publicacao1").isEmpty())) {
                comando = comando + union;
            }

            String[] palChaveNormalizadas = separarPalChaveNormalizadas(dados);
            comando = comando + inicioOpcaoPalChave;
            for (int i = 0; i < palChaveNormalizadas.length; i++) {
                comando = comando + parametroPalChave;
                if (i < (palChaveNormalizadas.length - 1)) {
                    comando = comando + "OR \n";
                }
            }
            comando = comando + fimOpcaoPalChave;
        }

        comando = comando + fimComando;
        return comando;
    }
//------------------------------------------------------------------------------    

    private String[] extrairPalavrasDeCampo(JsonObject dados, String campo) {
        String termo = dados.getString(campo);
        String busca = Utils.removerDiacriticosNormalizar(termo);
        String[] temp = busca.split(" ");
        for (int i = 0; i < temp.length; i++) {
            temp[i] = temp[i].trim();
        }
        return temp;
    }
//------------------------------------------------------------------------------    

    private String[] separarPalChaveNormalizadas(JsonObject dados) {
        String termo = dados.getString("palchave");
        String[] temp = termo.split(";");
        for (int i = 0; i < temp.length; i++) {
            temp[i] = temp[i].trim();
            temp[i] = Utils.removerDiacriticosNormalizar(temp[i]);
        }
        return temp;
    }
//------------------------------------------------------------------------------    

    private String[] separarPalChaveOriginais(JsonObject dados) {
        String termo = dados.getString("palchave");
        String[] temp = termo.split(";");
        return temp;
    }
//------------------------------------------------------------------------------ 

    private String normalizarTitulo(String titulo) {
        if (titulo == null || titulo.equals("")) {
            return titulo;
        }
        titulo = titulo.trim();
        titulo = titulo.replaceAll("\\s+", "");
        final String decomposed = Normalizer.normalize(titulo, Normalizer.Form.NFD);
        String saida = decomposed.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        saida = saida.toUpperCase();
        saida = saida.replace("'", "");
        saida = saida.replace("´", "");
        saida = saida.replace("-", "");
        saida = saida.replace(":", "");
        saida = saida.replace("/", "");
        saida = saida.replace(".", "");
        saida = saida.replace(";", "");
        saida = saida.replace(",", "");
        saida = saida.replace("(", "");
        saida = saida.replace(")", "");
        return saida;
    }
//------------------------------------------------------------------------------ 

    public RespostaCompletaDTO saveNew(JsonObject dados) {
        RespostaCompletaDTO listaRefsDTO = new RespostaCompletaDTO();
        RespostaDTO umaRefDTO = new RespostaDTO();
        ResultSet rst = null;
        long patrimonio = 0L;

        String titulo = dados.getString("titulo").trim();
        titulo = titulo.replaceAll("\\s+", " ");
        String autoria = dados.getString("autoria").trim();
        autoria = autoria.replaceAll("\\s+", " ");
        String veiculo = dados.getString("veiculo").trim();
        veiculo = veiculo.replaceAll("\\s+", " ");
        String data_publicacao = dados.getString("data_publicacao").trim();
        data_publicacao = data_publicacao.replaceAll("\\s+", " ");
        if (data_publicacao.length() != 8) {
//==============================================================================
            listaRefsDTO.setSucesso(false);
            return listaRefsDTO;
//==============================================================================            
        }

        try (Connection conexao = getConnection()) {
            // BEGIN TRANSACTION
            conexao.setAutoCommit(false);

            // PRIMEIRA TABELA
            PreparedStatement comandoSQL = conexao.prepareStatement(
                    "INSERT INTO dadoscatalogo "
                    + "(titulo,autoria,veiculo,data_publicacao,titulonormal) "
                    + "VALUES(?,?,?,?,?) RETURNING patrimonio;");
            comandoSQL.setString(1, titulo);
            comandoSQL.setString(2, autoria);
            comandoSQL.setString(3, veiculo);
            comandoSQL.setString(4, data_publicacao);
            comandoSQL.setString(5, normalizarTitulo(titulo));
            rst = comandoSQL.executeQuery();
            rst.next();
            patrimonio = rst.getLong("patrimonio");

            // SEGUNDA TABELA
            String[] palavrasDaBusca = extrairPalavrasDeCampo(dados, "titulo");
            for (String cadaPalavra : palavrasDaBusca) {
                comandoSQL = conexao.prepareStatement(
                        "INSERT INTO palavrastitulonormal (palavra_titulo_normal,patrimonio) "
                        + "VALUES(?,?);");
                comandoSQL.setString(1, cadaPalavra);
                comandoSQL.setLong(2, patrimonio);
                comandoSQL.executeUpdate();
            }

            // TERCEIRA TABELA
            palavrasDaBusca = extrairPalavrasDeCampo(dados, "autoria");
            for (String cadaPalavra : palavrasDaBusca) {
                comandoSQL = conexao.prepareStatement(
                        "INSERT INTO palavrasautorianormal (palavra_autoria_normal,patrimonio) "
                        + "VALUES(?,?);");
                comandoSQL.setString(1, cadaPalavra);
                comandoSQL.setLong(2, patrimonio);
                comandoSQL.executeUpdate();
            }

            // QUARTA TABELA
            palavrasDaBusca = extrairPalavrasDeCampo(dados, "veiculo");
            for (String cadaPalavra : palavrasDaBusca) {
                comandoSQL = conexao.prepareStatement(
                        "INSERT INTO palavrasveiculonormal (palavra_veiculo_normal,patrimonio) "
                        + "VALUES(?,?);");
                comandoSQL.setString(1, cadaPalavra);
                comandoSQL.setLong(2, patrimonio);
                comandoSQL.executeUpdate();
            }

            // TABELA PALAVRAS_CHAVE
            palavrasDaBusca = separarPalChaveNormalizadas(dados);
            String[] palChaveOriginais = separarPalChaveOriginais(dados);
            int i = 0;
            for (String palChave : palavrasDaBusca) {
                comandoSQL = conexao.prepareStatement(
                        "INSERT INTO palavras_chave (palchave,patrimonio,palchavenormal) "
                        + "VALUES(?,?,?);");
                comandoSQL.setString(1, palChaveOriginais[i]);
                comandoSQL.setLong(2, patrimonio);
                comandoSQL.setString(3, palChave);
                comandoSQL.executeUpdate();
                i++;
            }

            // COMMIT TRANSACTION
            conexao.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        umaRefDTO.setPatrimonio(Long.toString(patrimonio));
        umaRefDTO.setTitulo(titulo);
        umaRefDTO.setAutoria(autoria);
        umaRefDTO.setVeiculo(veiculo);
        umaRefDTO.setData_publicacao(data_publicacao);
        listaRefsDTO.addResposta(umaRefDTO);
        listaRefsDTO.setSucesso(true);
        return listaRefsDTO;
    }
//------------------------------------------------------------------------------

    public RespostaCompletaDTO saveModif(JsonObject dados, String id) {
        RespostaCompletaDTO listaRefsDTO = new RespostaCompletaDTO();
        RespostaDTO umaRefDTO = new RespostaDTO();
        ResultSet rst = null;
//        long patrimonio = Long.parseLong(dados.getString("patrimonio"));
        long patrimonio = Long.parseLong(id);

        String titulo = dados.getString("titulo").trim();
        titulo = titulo.replaceAll("\\s+", " ");
        String autoria = dados.getString("autoria").trim();
        autoria = autoria.replaceAll("\\s+", " ");
        String veiculo = dados.getString("veiculo").trim();
        veiculo = veiculo.replaceAll("\\s+", " ");
        String data_publicacao = dados.getString("data_publicacao").trim();
        data_publicacao = data_publicacao.replaceAll("\\s+", " ");
        if (data_publicacao.length() != 8) {
//==============================================================================
            listaRefsDTO.setSucesso(false);
            return listaRefsDTO;
//==============================================================================            
        }
        try (Connection conexao = getConnection()) {
            // BEGIN TRANSACTION
            conexao.setAutoCommit(false);

            // PRIMEIRA TABELA
            PreparedStatement comandoSQL = conexao.prepareStatement(
                    "UPDATE dadoscatalogo SET "
                    + "titulo=?, autoria=?, veiculo=?, data_publicacao=? , titulonormal=?"
                    + "WHERE patrimonio=?;");
            comandoSQL.setString(1, titulo);
            comandoSQL.setString(2, autoria);
            comandoSQL.setString(3, veiculo);
            comandoSQL.setString(4, data_publicacao);
            comandoSQL.setString(5, normalizarTitulo(titulo));
            comandoSQL.setLong(6, patrimonio);
            int count = comandoSQL.executeUpdate();
            if (count != 1) {
                listaRefsDTO.setSucesso(false);
                listaRefsDTO.addResposta(umaRefDTO);
                return listaRefsDTO;
            } else {
                comandoSQL = conexao.prepareStatement(
                        "DELETE FROM palavrastitulonormal WHERE patrimonio=?;"
                );
                comandoSQL.setLong(1, patrimonio);
                comandoSQL.executeUpdate();

                comandoSQL = conexao.prepareStatement(
                        "DELETE FROM palavrasautorianormal WHERE patrimonio=?;"
                );
                comandoSQL.setLong(1, patrimonio);
                comandoSQL.executeUpdate();

                comandoSQL = conexao.prepareStatement(
                        "DELETE FROM palavrasveiculonormal WHERE patrimonio=?;"
                );
                comandoSQL.setLong(1, patrimonio);
                comandoSQL.executeUpdate();

                comandoSQL = conexao.prepareStatement(
                        "DELETE FROM palavras_chave WHERE patrimonio=?;"
                );
                comandoSQL.setLong(1, patrimonio);
                comandoSQL.executeUpdate();
            }

            // SEGUNDA TABELA
            String[] palavrasDaBusca = extrairPalavrasDeCampo(dados, "titulo");
            for (String cadaPalavra : palavrasDaBusca) {
                comandoSQL = conexao.prepareStatement(
                        "INSERT INTO palavrastitulonormal (palavra_titulo_normal,patrimonio) "
                        + "VALUES(?,?);");
                comandoSQL.setString(1, cadaPalavra);
                comandoSQL.setLong(2, patrimonio);
                comandoSQL.executeUpdate();
            }

            // TERCEIRA TABELA
            palavrasDaBusca = extrairPalavrasDeCampo(dados, "autoria");
            for (String cadaPalavra : palavrasDaBusca) {
                comandoSQL = conexao.prepareStatement(
                        "INSERT INTO palavrasautorianormal (palavra_autoria_normal,patrimonio) "
                        + "VALUES(?,?);");
                comandoSQL.setString(1, cadaPalavra);
                comandoSQL.setLong(2, patrimonio);
                comandoSQL.executeUpdate();
            }

            // QUARTA TABELA
            palavrasDaBusca = extrairPalavrasDeCampo(dados, "veiculo");
            for (String cadaPalavra : palavrasDaBusca) {
                comandoSQL = conexao.prepareStatement(
                        "INSERT INTO palavrasveiculonormal (palavra_veiculo_normal,patrimonio) "
                        + "VALUES(?,?);");
                comandoSQL.setString(1, cadaPalavra);
                comandoSQL.setLong(2, patrimonio);
                comandoSQL.executeUpdate();
            }

            // TABELA PALAVRAS_CHAVE
            palavrasDaBusca = separarPalChaveNormalizadas(dados);
            String[] palChaveOriginais = separarPalChaveOriginais(dados);
            int i = 0;
            for (String palChave : palavrasDaBusca) {
                comandoSQL = conexao.prepareStatement(
                        "INSERT INTO palavras_chave (palchave,patrimonio,palchavenormal) "
                        + "VALUES(?,?,?);");
                comandoSQL.setString(1, palChaveOriginais[i]);
                comandoSQL.setLong(2, patrimonio);
                comandoSQL.setString(3, palChave);
                comandoSQL.executeUpdate();
                i++;
            }

            // COMMIT TRANSACTION
            conexao.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        umaRefDTO.setPatrimonio(Long.toString(patrimonio));
        umaRefDTO.setTitulo(titulo);
        umaRefDTO.setAutoria(autoria);
        umaRefDTO.setVeiculo(veiculo);
        umaRefDTO.setData_publicacao(data_publicacao);
        listaRefsDTO.addResposta(umaRefDTO);
        listaRefsDTO.setSucesso(true);
        return listaRefsDTO;
    }
//------------------------------------------------------------------------------
    // TESTADO

    public RespostaCompletaDTO excluir(JsonObject dados) {
        RespostaCompletaDTO listaRefsDTO = new RespostaCompletaDTO();
        RespostaDTO umaRefDTO = new RespostaDTO();
        int res = 0;
        try (Connection conexao = getConnection()) {
            PreparedStatement comandoSQL = conexao.prepareStatement(
                    "DELETE FROM dadoscatalogo WHERE patrimonio=?;");
            comandoSQL.setLong(1, Long.parseLong(dados.getString("patrimonio")));
            res = comandoSQL.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        listaRefsDTO.addResposta(umaRefDTO);
        boolean umExcluido = (res == 1);
        listaRefsDTO.setSucesso(umExcluido);
        return listaRefsDTO;
    }
//------------------------------------------------------------------------------

    public String getNomeArquivoOriginal(String patrimonio) {
        String nomeoriginalarquivo = null;
        Connection conexao = null;
        try {
            conexao = getConnection();
            PreparedStatement comandoSQL = conexao.prepareStatement(
                    "SELECT nomeoriginalarquivo FROM dadoscatalogo WHERE patrimonio=?;");

            comandoSQL.setLong(1, Long.parseLong(patrimonio));

            ResultSet rs = comandoSQL.executeQuery();
            if (rs.next()) {
                nomeoriginalarquivo = rs.getString("nomeoriginalarquivo");
            } else {
                nomeoriginalarquivo = "";
            }
        } catch (Exception e) {
            nomeoriginalarquivo = "";
            e.printStackTrace();
        }

        try {
            if (conexao != null) {
                conexao.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nomeoriginalarquivo;
    }
//------------------------------------------------------------------------------

    public boolean salvarNomeArquivo(
            String strPatrimonio,
            String nomeOriginalArquivo) {
        long patrimonio = Long.parseLong(strPatrimonio);

        try {
            Connection conexao = getConnection();
            PreparedStatement comandoSQL = conexao.prepareStatement(
                    "UPDATE dadoscatalogo SET nomeoriginalarquivo=? "
                    + "WHERE patrimonio=?;");
            comandoSQL.setString(1, nomeOriginalArquivo);
            comandoSQL.setLong(2, patrimonio);
            int count = comandoSQL.executeUpdate();
            if (count == 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
//------------------------------------------------------------------------------

    public RespostaCompletaDTO getall(String offset) {
        RespostaCompletaDTO listaRefsDTO = new RespostaCompletaDTO();
        RespostaDTO umaRefDTO = new RespostaDTO();
        Connection conexao = null;
        try {
            conexao = getConnection();
            PreparedStatement comandoSQL = conexao.prepareStatement(
                    "SELECT * FROM dadoscatalogo ORDER BY patrimonio LIMIT 5 OFFSET ?;");
            comandoSQL.setInt(1, Integer.parseInt(offset));

            conexao.setAutoCommit(false);
            ResultSet rs = comandoSQL.executeQuery();
            long patrimonio = 0L;
            while (rs.next()) {
                patrimonio = rs.getLong("patrimonio");
                umaRefDTO = new RespostaDTO();
                umaRefDTO.setPatrimonio(Long.toString(patrimonio));
                umaRefDTO.setTitulo(rs.getString("titulo"));
                umaRefDTO.setAutoria(rs.getString("autoria"));
                umaRefDTO.setVeiculo(rs.getString("veiculo"));
                umaRefDTO.setNomeOriginalArquivo(rs.getString("nomeoriginalarquivo"));
                umaRefDTO.setData_publicacao(rs.getString("data_publicacao"));
                umaRefDTO.setNrohits("1");
                umaRefDTO.setPalchave(buscarPalavrasChave(patrimonio));
//System.out.println("=== RefDTO: "+umaRefDTO.toString());                
                listaRefsDTO.addResposta(umaRefDTO);
            }
            comandoSQL = conexao.prepareStatement(
                    "SELECT count(*) FROM dadoscatalogo;");
            rs = comandoSQL.executeQuery();
            if(rs.next()){
                listaRefsDTO.setTotalNroRows(rs.getInt(1));    
            }
            
            
            conexao.commit();
        } catch (Exception e) {
            umaRefDTO.setPatrimonio("0");
            umaRefDTO.setTitulo("ERRO");
            e.printStackTrace();
        }

        try {
            if (conexao != null) {
                conexao.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaRefsDTO;
    }
//------------------------------------------------------------------------------
}
