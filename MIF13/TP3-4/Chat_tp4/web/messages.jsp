<%
    //Pour éviter que l'on se connecte, sans même avoir de session (php like)
    String sessionNom = (String) request.getSession(false).getAttribute("nom");
    if (sessionNom == null) response.sendRedirect("connexion.html");

%>

<%@page import="Gestion.Message"%>

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

    //gestion.ajouterMessage(new Message("Bob", "test")); //test
    
    //teste la méthode utilisée
    if(methode.equalsIgnoreCase("GET")){

        //teste de l'existence du cookie, création si nécessaire
        if(tmpCookie == null){
            Cookie creation = new Cookie(nomCookie, gestion.stringSize());
            creation.setMaxAge(-1);
            response.addCookie(creation);
            response.setStatus(204);
        }
        else {
 
            //nb messages côté client
            nbMessClient = Integer.parseInt(tmpCookie.getValue());
            //nb messages côté serveur
            nbMessServeur = gestion.intSize();

            /*
            * Comparaison du nombre de messages, client/serveur.
            * Si < est vrai, alors on va chercher les nouveaux messages,
            * sinon on dit au client qu'il n'y a pas de nouveau contenu à récupérer (204)
            */
            if(nbMessClient < nbMessServeur){
                afficherMessages = true;
                response.addCookie(new Cookie(nomCookie, gestion.stringSize()));
            }
            else //Envoie de "204 No Content" => aucun nouveau message à récupérer (par défaut : 200 OK)
                response.setStatus(204);
        }
    }
    //Un message va être ajouté
    else if(methode.equalsIgnoreCase("POST"))
        ajouterMessage = true;

%>

<% if(ajouterMessage){ %> <jsp:include page="stockage.jsp" /> <% } %>

<% if(afficherMessages){%>
<%@page contentType="text/xml" pageEncoding="UTF-8" %>
<Messages>
<%for(int i = nbMessClient; i < gestion.intSize(); i++){%>
    <Message>
        <jsp:include page="date/heureXML.jsp" />
        <Auteur><%= gestion.getMessage(i).getUtilisateur() %></Auteur>
        <Texte><%= gestion.getMessage(i).getContenu() %></Texte>
    </Message>
<%}%>
</Messages>
<% } %>
