<jsp:useBean id="bean" scope="application" class="Gestion.GestionMessages"/>

<%
    //récupération du cookie chez le client
    Cookie[] cookie = request.getCookies();

    if(cookie == null){
        Cookie unCookie = new Cookie("lastModified", bean.getStringLastModified());
        response.addCookie(unCookie);
    }
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title></title>
    </head>

    <body>

        <h2>Chat - Room ___ooo(O.O)ooo___</h2>

        <% if(request.getMethod().equalsIgnoreCase("POST")){ %>
                <jsp:include page="stockage.jsp"/>
        <% } %>

        <jsp:forward page="affichage.jsp"/>


    </body>
</html>