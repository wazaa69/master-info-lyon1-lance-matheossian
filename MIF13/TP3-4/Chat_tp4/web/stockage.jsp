<%
    //Pour éviter que l'on se connecte, sans même avoir de session (php like)
    String sessionNom = (String) request.getSession(false).getAttribute("nom");
    if (sessionNom == null) response.sendRedirect("connexion.html");

%>

<%-- Réception et mémorisation des messages envoyés par POST --%>

<%@page import="Gestion.Message"%>
<%@page import="javax.servlet.http.Cookie" %>
<jsp:useBean id="gestion" scope="application" class="Gestion.GestionMessages"/>

<%
    String messageRecu = request.getParameter("message");

    if(messageRecu != null && !messageRecu.isEmpty())
        gestion.ajouterMessage(new Message((String) session.getAttribute("nom"),messageRecu));
   
%>