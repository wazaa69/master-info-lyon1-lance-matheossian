<%
    // Vérification de l'existence de la session
    String username = (String) request.getSession(false).getAttribute("username");
    if (username.isEmpty())
    {
        response.sendRedirect("index.html");
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="css/reset.css" />
        <link rel="stylesheet" type="text/css" href="css/main.css" />

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chat - messages</title>

        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
        <script type="text/javascript" src="js/jquery.timer.js"></script>
        <script type="text/javascript" src="js/jquery.xslt.js"></script>
        <script type="text/javascript" src="js/main.js"></script>
        <script type="text/javascript" src="js/post_message.js"></script>
    </head>
    <body>
        <div id="header">
            <h1>Le tchat du kikoolol</h1>
            <h2>MER IL ET FOU !</h2>
        </div>

        <div id="errors"></div>
        
        <div id="messages"></div>
        
        <div id="text-enter">
            <form method="post" action="" id="form">
                <p class="username">
                    Bienvenue, <strong><%= username %></strong> !
                    <a href="logout" target="_parent">D&eacute;connexion</a>
                </p>
                <p>
                    <label for="msg_text">Entrez un message : </label>
                    <input type="text" name="message" id="msg_text" value="" />
                    <input type="submit" value="Envoyer" id="msg_submit" />
                </p>
            </form>
        </div>
    </body>
</html>