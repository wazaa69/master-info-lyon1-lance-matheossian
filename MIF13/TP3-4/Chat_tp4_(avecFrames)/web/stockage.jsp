<%-- Réception et mémorisation des messages envoyés par POST--%>

<%@page import="Gestion.Message"%>
<%@page import="javax.servlet.http.Cookie" %>
<jsp:useBean id="gestion" scope="application" class="Gestion.GestionMessages"/>

<%
    String messageRecu = request.getParameter("message");

    if(messageRecu != null && !messageRecu.isEmpty())
        gestion.ajouterMessage(new Message((String) session.getAttribute("nom"),messageRecu));
   
%>