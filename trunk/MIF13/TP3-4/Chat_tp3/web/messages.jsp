<%-- Test la méthode utilisée :
        - GET : test de l'existance du cookie, et comparaison de sa valeur
                au nombre total de messages mémorisés :
                   * maj des messages
                   * Ou renvoie du code status 304 si pas de maj à faire
        - POST : ajout d'un messages + réaffichage de tous les messages
--%>

<%@page import="java.math.BigDecimal"%>
<jsp:useBean id="gestion" scope="application" class="Gestion.GestionMessages"/>
<jsp:useBean id="outils" scope="application" class="Gestion.Outils"/>
<%
    String methode = request.getMethod();

    String nomCookie = "lastModified";
    Cookie tmpCookie = outils.getCookie(request.getCookies(), nomCookie);

    boolean afficherMessages = false;
    boolean ajouterMessage = false;

    int nbMessClient = 0;
    int nbMessServeur = 0;
    
    //teste la méthode utilisée
    if(methode.equals("GET")){

        //teste de l'existence du cookie, création si nécessaire
        if(tmpCookie == null){
            Cookie creation = new Cookie(nomCookie, ""+0);
            creation.setMaxAge(500);
            response.addCookie(creation);
        }

        else {
  
            //nb messages côté client
            nbMessClient = Integer.parseInt(tmpCookie.getValue());
            //nb messages côté serveur
            nbMessServeur = gestion.intSize();

            /*
            * Comparaison du nombre de messages, client/serveur.
            * Si < est vrai, alors on affiche les messages,
            * sinon on dit au client que tout va bien (304)
            */
            if(nbMessClient < nbMessServeur)
                afficherMessages = true;
            else response.setStatus(304); //Envoie de "Not Modified" => on laisse affiché les messages précédants
        }
    }
    //Un message va être ajouté et on va tous les réafficher
    else if(methode.equals("POST")){
        afficherMessages = true;
        ajouterMessage = true;
    }
    else{}

%>

<% if(ajouterMessage){ %>
        <jsp:include page="stockage.jsp" />
<% } %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="refresh" content="2" />
        <title>Chat-Room ___ooo(O.O)ooo___</title>
    </head>

    <body onload="document.location='#EnBas'">

        <% if(afficherMessages){
            //Bug : le cookie ne se met pas à jour dans l'affichage (sa devrait fonctionner, normalement), donc ce if est tout le temps appelé
            //out.print(gestion.compteurAffichage + " / " + nbMessClient + " / " + nbMessServeur + "<br/>");
            //out.print(nbMessClient + " / " + nbMessServeur);
        %>
            <jsp:include page="affichage.jsp" />
        <% } %>

        <a name="EnBas" />

    </body>
</html>