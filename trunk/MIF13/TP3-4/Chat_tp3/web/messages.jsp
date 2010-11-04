<%@page import="java.math.BigDecimal"%>
<jsp:useBean id="gestion" scope="application" class="Gestion.GestionMessages"/>
<jsp:useBean id="outils" scope="application" class="Gestion.Outils"/>

<%

    Cookie tmpCookie = null;
    String nomCookie = "lastModified";
    boolean afficher = false;

    if(request.getMethod().equalsIgnoreCase("GET")){

        //teste de l'existance du cookie
        if(request.getCookies() == null){
            
            tmpCookie = new Cookie(nomCookie, gestion.stringSize());

            //ajout du cookie à la réponse
            response.addCookie(tmpCookie);

        }
        else {
            
            //recherche du bon cookie
            tmpCookie = outils.getCookie(request.getCookies(), nomCookie);

            String messagesServer = gestion.stringSize(); //nb messages côté serveur
            String messagesClient = tmpCookie.getValue(); //nb messages côté client

            //comparaison du nombre de messages client/serveur
            if(!messagesClient.equalsIgnoreCase(messagesServer)) afficher = true;
        }

    }
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="refresh" content="2" />
        <title>Chat-Room</title>
    </head>

    <body onload="document.location='#EnBas'">

        <h2>Chat - Room ___ooo(O.O)ooo___</h2>

        <% if(request.getMethod().equalsIgnoreCase("POST")){ %>
                <jsp:include page="stockage.jsp"/>
        <% } %>

        <% if(afficher){ %>
            <jsp:include page="affichage.jsp"/>
        <% }%>

    </body>
</html>