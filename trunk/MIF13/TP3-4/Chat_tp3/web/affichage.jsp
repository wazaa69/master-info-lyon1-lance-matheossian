<%-- Affichage des messages--%>

<jsp:useBean id="bean" scope="application" class="Gestion.GestionMessages"/>
<jsp:useBean id="outils" scope="application" class="Gestion.Outils"/>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="refresh" content="2" />
        <title>Chat-Room</title>
    </head>


    <body onload="document.location='#EnBas'">

        <h2>Chat-Room ___ooo(O.O)ooo___</h2>

        <%
            if(request.getIntHeader("If-Modified-Since") < bean.getLastModified()){

                for(int i = 0; i < bean.size(); i++){
                out.print(bean.getMessage(i).getUtilisateur() + " : ");
                out.print(bean.getMessage(i).getContenu() + "<br/><br/>");
                }
                
                //récupération du cookie chez le client
                Cookie unCookie = outils.getStringParam(request.getCookies(), "lastModified");

                //mise à jour du cookie
                unCookie.setValue(bean.getStringLastModified());
                
                
            }
            //les messages sont déjà à jour, tout est ok
            else response.setStatus(304);
        %>

        <a name="EnBas" />

    </body>
</html>