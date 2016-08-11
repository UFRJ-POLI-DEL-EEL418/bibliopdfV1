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
                request.getContextPath())%>/js/login.js"
        ></script>

        <script type="text/javascript" 
                src="<%=
                new URL(request.getScheme(), 
                request.getServerName(), 
                request.getServerPort(), 
                request.getContextPath())%>/js/md5.js"
        ></script>
    </head>

    <body>
        <div id="idDivCabecalho">
            <div id="menuEntrar"  class="Menu">
                ENTRAR
            </div>
            <div id="idTituloPagina">
                <span id="idLabel1">BiblioPDF</span>
            </div>
        </div>

        <div id="idDivLogin">
            <br>
            <br>
            <br>
            <form id="idForm" method="POST" action="j_security_check">
                Usuário
                <br>
                <input id="idUsuario" type="text" name="j_username"/><br>
                Senha
                <br>
                <input id="idSenha" type="password"/>
                <input id="idSenhaDigested" type="password" name="j_password" style="display:none;"/>
            </form>
            <br>
            <div style="text-align:center;font-size:16pt;color:#f1e2af">
            Erro 401 unauthorized
            <br>
            <br>
            Usuário ou senha inválidos,
            <br>
            <br>
            tente de novo 
            <a href="/bibliopdf/protegido/busca_catalogacao.jsp" style="color:#f1e2af;">
                aqui
            </a>.
            </div>
            <br>
            <br>
            <div id="idLabel2">v0 Alfa&copy;J.L.S.Leão, 2016 - jorge.leao@ufrj.br</div>
            <br>
            <br>
            <br>
            Datas e horários no formato ISO8601<br>
            (local combined date and time in UTC, without milliseconds, without timezone)
            <br>
            <br>
            Página carregada em:<br>
            <span id="idCurrentTime"></span>
            <br>
            <br>
            <span>
                org.glassfish.jersey.bundles: jax-rs-ri
                <br>
                Icons made by
                <a href="http://www.flaticon.com/authors/simpleicon" title="SimpleIcon">SimpleIcon</a>
                from
                <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a>
                is licensed by
                <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0">CC BY 3.0</a>
            </span>
        </div>

    </body>
</html>
