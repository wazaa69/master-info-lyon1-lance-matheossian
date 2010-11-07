<%-- Affiche les messages et met à jour le cookie --%>

<%@page import="Gestion.Message"%>
<jsp:useBean id="gestion" scope="application" class="Gestion.GestionMessages"/>
<jsp:useBean id="outils" scope="application" class="Gestion.Outils"/>

<%

    String nomCookie = "lastModified";

    gestion.compteurAffichage+=1; //Test : réactiver "compteurAffichage" de la classe GestionMessages

    for(int i = 0; i < gestion.intSize(); i++){
        out.print(gestion.getMessage(i).getUtilisateur() + " : ");
        out.print(gestion.getMessage(i).getContenu() + "<br/><br/>");
    }


    //recherche du bon cookie
    Cookie tmpCookie = outils.getCookie(request.getCookies(), nomCookie);

    //ni l'un ni l'autre ne fonctionne dans cette jsp, le cookie ne se met pas à jour
    tmpCookie.setValue(gestion.stringSize());
    //response.addCookie(new Cookie(nomCookie, gestion.stringSize()));

%>