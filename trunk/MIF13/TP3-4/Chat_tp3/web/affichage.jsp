<%-- Affichage des messages--%>

<%@page import="Gestion.Message"%>
<jsp:useBean id="gestion" scope="application" class="Gestion.GestionMessages"/>
<jsp:useBean id="outils" scope="application" class="Gestion.Outils"/>


<%
    //recherche du bon cookie
    Cookie tmpCookie = outils.getCookie(request.getCookies(), "lastModified");

    //mise à jour du cookie client
    tmpCookie.setValue(gestion.stringSize());


    //gestion.ajouterMessage(new Message("teste","ajout dun message")); //ne pas activer, sauf pour tester

    for(int i = 0; i < gestion.intSize(); i++){
        out.print(gestion.getMessage(i).getUtilisateur() + " : ");
        out.print(gestion.getMessage(i).getContenu() + "<br/><br/>");
    }

%>

<a name="EnBas" />
