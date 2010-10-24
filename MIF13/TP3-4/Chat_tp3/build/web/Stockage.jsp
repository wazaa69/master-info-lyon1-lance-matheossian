<%-- 
    Document   : Stockage
    Created on : 12 oct. 2009, 11:23:46
    Author     : p0805455
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="messages" scope="application" class="chat.GestionMessages" />

<%
String message = request.getParameter("message");
String username = (String) request.getSession(false).getAttribute("username");

messages.addMessage(message, username);
%>
