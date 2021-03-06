<%@page import="java.net.URL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>BiblioPDF</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link href="<%= request.getScheme().toString() %>://fonts.googleapis.com/css?family=Fjord+One" rel="stylesheet" type="text/css">
        <link href="<%= request.getScheme().toString() %>://fonts.googleapis.com/css?family=Amaranth" rel="stylesheet" type="text/css">

        <link rel="shortcut icon" 
              type="image/x-icon"
              href="<%=
                new URL(request.getScheme(), 
                request.getServerName(), 
                request.getServerPort(), 
                request.getContextPath())%>/favicon.ico" 
        >

        <link rel="stylesheet" 
              type="text/css"
              href="<%=
                new URL(request.getScheme(), 
                request.getServerName(), 
                request.getServerPort(), 
                request.getContextPath())%>/css/bibliopdf.css" 
        >

        <script type="text/javascript" 
                src="<%=
                new URL(request.getScheme(), 
                request.getServerName(), 
                request.getServerPort(), 
                request.getContextPath())%>/js/busca_catalogacao.js"
        ></script>
    </head>

    <body>
        <div id="idDivCabecalho">
            <form id="idFormSair" method="POST" 
                  action="<%=   new URL(request.getScheme(), 
                                request.getServerName(), 
                                request.getServerPort(), 
                                request.getContextPath())%>/logout">
            <div id="menuSair"  class="Menu" onclick="document.getElementById('idFormSair').submit();">
                SAIR
            </div>
            </form>

            <div id="menuCatalogacao"  class="Menu">
                CATALOGAÇÃO
            </div>
            <div id="menuBusca"  class="Menu">
                BUSCA
            </div>
            <div id="idTituloPagina">
                <span id="idLabel1">BiblioPDF</span>
            </div>
        </div>

        <!--==========================================================================-->
        <div id="idDivBusca">
            <table id="tabelaBusca" class="center"  border="0">
                <tr>
                    <td>
                        &nbsp;
                    </td>
                    <td colspan="2" class="titulo">
                        BUSCA
                    </td>
                    <td style="width:200px;">
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td id="idMsgDialogo2" colspan="4" style="width:936px;">
                        Mensagem de dialogo da página de busca...
                    </td>
                </tr>
                <tr>
                    <td>
                        &nbsp;
                    </td>
                    <td colspan="2">
                        &nbsp;
                    </td>
                    <td style="width:200px;">
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td class="alinha_direita">
                        Patrimônio
                    </td>
                    <td class="alinha_esquerda" colspan="2">
                        <input type="text"     id="idpatrimonio2"/>
                    </td>
                    <td class="alinha_esquerda" style="width:200px;">
                        <input type="checkbox" id="idcheckpatrimonio"/>
                    </td>
                </tr>
                <tr>
                    <td class="alinha_direita">
                        Título
                    </td>
                    <td class="alinha_esquerda" colspan="2">
                        <input type="text"     id="idtitulo2"/>
                    </td>
                    <td class="alinha_esquerda" style="width:200px;">
                        <input type="checkbox" id="idchecktitulo"/>
                    </td>
                </tr>
                <tr>
                    <td class="alinha_direita">
                        Autoria
                    </td>
                    <td class="alinha_esquerda" colspan="2">
                        <input type="text"     id="idautoria2"/>
                    </td>
                    <td class="alinha_esquerda" style="width:200px;">
                        <input type="checkbox" id="idcheckautoria"/>
                    </td>
                </tr>
                <tr>
                    <td class="alinha_direita">
                        Veículo
                    </td>
                    <td class="alinha_esquerda" colspan="2">
                        <input type="text"     id="idveiculo2"/>
                    </td>
                    <td class="alinha_esquerda" style="width:200px;">
                        <input type="checkbox" id="idcheckveiculo"/>
                    </td>
                </tr>
                <tr>
                    <td class="alinha_direita">
                        Data da publicação
                    </td>
                    <td class="alinha_esquerda">
                        &nbsp;&nbsp;de:  <input type="text"    id="iddata_publicacao21"
                                                style="width:214px;"/>
                    </td>
                    <td class="alinha_esquerda">
                        &nbsp;&nbsp;até: <input type="text"    id="iddata_publicacao22"
                                                style="width:214px;"/>
                    </td>
                    <td class="alinha_esquerda" style="width:200px;">
                        <input type="checkbox" id="idcheckdata_publicacao"/>
                    </td>
                </tr>
                <tr>
                    <td class="alinha_direita">
                        Palavras-chave
                    </td>
                    <td class="alinha_esquerda" colspan="2">
                        <input type="text"     id="idpalchave2"/>
                    </td>
                    <td class="alinha_esquerda" style="width:200px;">
                        <input type="checkbox" id="idcheckpalchave"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="alinha_esquerda" valign="bottom" colspan="2" style="text-align:left;width:536px;">
                        <input type="button" class="botoesBusca" value="BUSCAR" id="idBuscar"/>
                        <input type="button" class="botoesBusca" value="LIMPAR" id="idLimparBusca"/>
                    </td>
                    <td style="width:200px;">
                        <div id="idLoading1"></div>
                    </td>
                </tr>
            </table>

            <table id="tabelaBusca2" class="center"  border="0">
                <tr>
                    <td class="alinha_direita" style="width:300px;">
                        &nbsp;
                    </td>
                    <td style="width:735px;">
                        Nro. total de registros encontrados:
                        <span id="idNroRows">0</span>
                    </td>
                    <td style="width:300px;">
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td colspan="3" style="width:936px;">
                        <form id="idFormResultados">
                            <div id="idTabelaResultados">
                            </div>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td style="text-align:center;">
                        <input type="button" value="< Página anterior" id="idPagAnterior"/>
                        <input type="text" id="idPaginaDestino" value="0">
                        <input type="button" value="Próxima página >"  id="idPagProxima"/>
                    </td>
                    <td style="width:200px;">&nbsp;</td>
                </tr>
            </table>
        </div>

        <!--==========================================================================-->
        <div id="idDivCatalogacao">
            <table id="tabelaCatalogacao" class="center" border="0">
                <tr>
                    <td>
                        &nbsp;
                    </td>
                    <td class="titulo">
                        CATALOGAÇÃO
                    </td>
                    <td>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td id="idMsgDialogo3" colspan="3">
                        Mensagem de dialogo da página de catalogação...
                    </td>
                </tr>
                <tr>
                    <td class="alinha_direita">
                        <input type="button" value="Anterior" id="idItemAnterior" class="botoesProxAnt"/>
                    </td>
                    <td>
                        &nbsp;
                    </td>
                    <td class="alinha_esquerda">
                        <input type="button" value="Próximo" id="idItemProximo" class="botoesProxAnt"/>
                    </td>
                </tr>
                <tr>
                    <td class="alinha_direita">
                        Patrimônio
                    </td>
                    <td style="text-align:left;">
                        <input type="text" id="idpatrimonio3" readonly/>
                    </td>
                    <td class="alinha_esquerda">
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td class="alinha_direita">
                        Título
                    </td>
                    <td style="text-align:left;">
                        <input type="text" id="idtitulo3"/>
                    </td>
                    <td>
                    </td>
                </tr>
                <tr>
                    <td class="alinha_direita">
                        Autoria
                    </td>
                    <td style="text-align:left;">
                        <input type="text" id="idautoria3"/>
                    </td>
                    <td>
                    </td>
                </tr>
                <tr>
                    <td class="alinha_direita">
                        Veículo
                    </td>
                    <td style="text-align:left;">
                        <input type="text" id="idveiculo3"/>
                    </td>
                    <td>
                    </td>
                </tr>
                <tr>
                    <td class="alinha_direita">
                        Data da publicação
                    </td>
                    <td style="text-align:left;">
                        <input type="text" id="iddata_publicacao3"
                               placeholder="AAAA    ou   AAAA-MM-DD"
                               readonly/>
                    </td>
                    <td>
                    </td>
                </tr>
                <tr>
                    <td class="alinha_direita">
                        Palavras-chave
                    </td>
                    <td style="text-align:left;">
                        <input type="text" id="idpalchave3" readonly/>
                    </td>
                    <td>
                    </td>
                </tr>
                <tr>
                    <td class="alinha_direita">
                        Nome original do arquivo
                    </td>
                    <td class="alinha_esquerda" colspan="2">
                        <input type="text"     id="idNomeOriginalArquivo" readonly/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td style="text-align:left;vertical-align:bottom;">
                        <form id="idFormDownload">
                            <input type="hidden" name="patrimonio"/>
                            <input type="hidden" name="view"/>
                            <input type="button" class="botoes" id="idEditar" value="EDITAR"/>
                            <input type="button" class="botoes" id="idDownload" value="DOWNLOAD"/>
                            <input type="button" class="botoes" id="idLimparCat" value="LIMPAR"/>
                        </form>
                    </td>
                    <td>
                        <div id="idLoading2"></div>
                    </td>
                </tr>
            </table>
            <table id="tabelaCatalogacao2" class="center" border="0">
                <tr>
                    <td></td>
                    <td style="text-align:left;">
                        <input type="button" class="botoes" id="idSalvarNovo" value="SALVAR COMO NOVO"/>
                        <input type="button" class="botoes" id="idSalvarModif" value="SALVAR MODIFICAÇÕES"/>
                        <input type="button" class="botoes" id="idExcluir" value="EXCLUIR"/>
                    </td>
                    <td>
                    </td>
                </tr>
                <tr>
                    <td>
                        &nbsp;<br>
                    </td>
                    <td>
                        &nbsp;
                    </td>
                    <td>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td class="alinha_direita">
                    </td>
                    <td  class="alinha_esquerda" colspan="2">
                        <form id="idFormUpload">
                            <input type="file" name="htmlFileElementName" id="idNomeArqUpload"/>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td class="alinha_direita">
                    </td>
                    <td  class="alinha_esquerda" colspan="2">
                        <input type="button" id="idSubirArquivo" value="SUBIR ARQUIVO"
                               class="botoesBusca"/>
                        <input type="button" id="idExcluirArquivo" value="EXCLUIR ARQUIVO"
                               class="botoesBusca"/>
                    </td>
                </tr>
            </table>
        </div>
        <!--==========================================================================-->
    </body>
</html>
