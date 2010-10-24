<%-- 
   1. Réception et mémorisation des messages envoyés par P
.OST :
à la réception d'une requête POST, la page récupère le texte
du message dans le paramètre de la requête ad hoc, ainsi que
le nom de son auteur dans l'attribut de session stocké par Init.
Elle crée alors un nouveau message, qu'elle place en queue de liste.

   2. Affichage des messages : à la réception d'une requête (GET ou POST),
la page parcourt la liste et affiche tous les messages avec la mise en
forme qui vous plaîra. En l'absence d'envoi de données de la part du client,
cet affichage s'actualisera toutes les 5 secondes.

--%>
<%@page import="Servlet.Message"%>
<%@page import="java.util.ArrayList"%>

<%

    if(request.getMethod().equalsIgnoreCase("POST")){
        }

    ArrayList<Message> listeMessages = new ArrayList<Message>();

    String login = new String(request.getParameter("login"));
  
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World mr <%= session.getAttribute("login") %></h1>

    </body>
</html>
