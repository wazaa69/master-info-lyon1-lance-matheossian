<%@page import="javax.servlet.http.Cookie" %>
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
            Cookie creation = new Cookie(nomCookie, "" + 0);
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
            * Si < est vrai, alors on va chercher les nouveaux messages,
            * sinon on dit au client qu'il n'y a pas de nouveau contenu à récupérer (204)
            */
            if(nbMessClient < nbMessServeur){
                afficherMessages = true;
                Cookie c = new Cookie(nomCookie, gestion.stringSize());
                response.addCookie(c);
            }
            else response.setStatus(204); //Envoie de "No Content" => aucun nouveau message récupéré
        }
    }
    //Un message va être ajouté
    else if(methode.equals("POST")){
        ajouterMessage = true;
        afficherMessages = true;
    }

%>

<% if(ajouterMessage){ %> <jsp:include page="stockage.jsp" flush="true"/> <% } %>

<Messages>
    <% if(afficherMessages){
  
        for(int i = nbMessClient; i < gestion.intSize(); i++){%>
            <Message>
                <Auteur><%= gestion.getMessage(i).getUtilisateur() %></Auteur>"
                <Texte><%= gestion.getMessage(i).getContenu() %></Texte>
            </Message>
        <%}

    } %>
</Messages>