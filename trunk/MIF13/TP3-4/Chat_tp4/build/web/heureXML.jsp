<%
    Date date = new Date();
    String chaine = date.toString();
%>
<%@page import="java.util.Date"%>
<%@page contentType="application/xml" pageEncoding="UTF-8"%>


<heure>
    <%= chaine  %>
</heure>