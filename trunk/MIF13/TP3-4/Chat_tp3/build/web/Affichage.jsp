<%@page import="javax.servlet.http.Cookie" %>
<jsp:useBean id="messages" scope="application" class="chat.GestionMessages" />
<% 
    Cookie[] cookies = request.getCookies();
    int lastClientIndex = 0;
    int lastServerIndex;
    String cookieName = "chat_last_index";
    boolean printHtml = true;

    lastServerIndex = messages.getNbMessages();

    for(int i = 0; i < cookies.length; i++)
    {
        Cookie MonCookie = cookies[i];
        if (MonCookie.getName().equals(cookieName))
        {
            lastClientIndex = Integer.parseInt(cookies[i].getValue());
            break;
        }
    }
    if (lastClientIndex != 0)
    {
        if (lastClientIndex > lastServerIndex)
        {
            response.setStatus(204);
            printHtml = false;
        }
    }
   
    if (printHtml)
    {
        Cookie c = new Cookie(cookieName, String.valueOf(lastServerIndex));
        response.addCookie(c);
    }
%>
<% if (printHtml) { %>
<?xml version="1.0" encoding="UTF-8" ?>
<Messages>
    <% for (int i = messages.getNbMessages() - 1; i >= lastClientIndex; i--) { %>
    <Message>
        <Auteur><%= messages.getMessage(i).username %></Auteur>
        <Texte><%= messages.getMessage(i).text %></Texte>
    </Message>
    <% } %>
</Messages>
<% } %>
