<%-- Réception et mémorisation des messages envoyés par POST Affichage des messages--%>

<%@page import="Gestion.Message"%>

<jsp:useBean id="bean" scope="application" class="Gestion.GestionMessages"/>

<%
    String messageRecu = request.getParameter("message");

    if(messageRecu != null && !messageRecu.isEmpty()){
        bean.ajouterMessage(new Message((String) session.getAttribute("nom"),messageRecu));

        //Maj de la dernière modification sur le serveur
        bean.setLastModified();

    }
    
%>