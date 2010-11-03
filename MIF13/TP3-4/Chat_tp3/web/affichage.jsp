<%-- Affichage des messages--%>

<jsp:useBean id="bean" scope="application" class="Gestion.GestionMessages"/>





<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="refresh" content="2" />
        <title>Chat-Room</title>
    </head>


    <body onload="document.location='#EnBas'">

        <h2>Chat-Room ___ooo(O.O)ooo___</h2>

        <%
            for(int i = 0; i < bean.size(); i++){
                out.print(bean.getMessage(i).getUtilisateur() + " : ");
                out.print(bean.getMessage(i).getContenu() + "<br/><br/>");
            }
        %>

        <a name="EnBas" />

    </body>
</html>