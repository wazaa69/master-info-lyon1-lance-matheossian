<%
    //---> ne pas utiliser d'include, sa ne fonctionne pas sinon ! :p
    //Pour éviter que l'on se connecte, sans même avoir de session (php like)
    String sessionNom = (String) request.getSession(false).getAttribute("nom");
    if (sessionNom == null) response.sendRedirect("connexion.html");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Chat-Room ___ooo(O.O)ooo___</title>

        <script type="text/javascript" src="js/ajax.js"></script>
        <script type="text/javascript" src="js/actions.js"></script>

    </head>

    <body onload="recupererMessages()">

        <div id="listeMessages">   
        </div>
        <a name="EnBas" />

        <fieldset>

            <legend>Message à envoyer</legend>

            <form  action="" onsubmit="stockerMessage()">
              <div>
                  <!-- <textarea cols="50" rows="5" class="saisie" name="message"></textarea> -->
                  <input type="text" id="saisie"/>
                  <input type="submit" />
              </div>
            </form>

        </fieldset>

        
        <form  method="GET" action="Logout">
            <div>
                <br/>
                <input type="submit" name="deconnexion" value="Déconnexion" />
            </div>
        </form>

    </body>
</html>
