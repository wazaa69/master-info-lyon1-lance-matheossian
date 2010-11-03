<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="refresh" content="2" />
        <title>Gestion des messages</title>
    </head>
    <body>

        <% if(request.getMethod().equalsIgnoreCase("POST")){ %>
                <jsp:include page="stockage.jsp"/>
        <% } %>
  
    </body>
</html>