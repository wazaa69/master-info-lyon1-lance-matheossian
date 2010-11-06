<%-- Réception et mémorisation des messages envoyés par POST--%>

<%@page import="Gestion.Message"%>
<jsp:useBean id="gestion" scope="application" class="Gestion.GestionMessages"/>
<jsp:useBean id="outils" scope="application" class="Gestion.Outils"/>
<%
    String messageRecu = request.getParameter("message");

    if(messageRecu != null && !messageRecu.isEmpty())
        gestion.ajouterMessage(new Message((String) session.getAttribute("nom"),messageRecu));
   
%>