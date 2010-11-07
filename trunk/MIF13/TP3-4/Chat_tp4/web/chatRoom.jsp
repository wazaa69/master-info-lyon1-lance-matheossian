<%
    //---> ne pas utiliser d'include, sa ne fonctionne pas sinon ! :p
    //Pour éviter que l'on se connecte, sans même avoir de session (php like)
    String sessionNom = (String) request.getSession(false).getAttribute("nom");
    if (sessionNom == null) response.sendRedirect("connexion.html");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">


<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

        <title>Chat-Room</title>

        <link rel="stylesheet" type="text/css" href="css/template.css"  />

        <script type="text/javascript" src="js/ajax.js"></script>
        <script type="text/javascript" src="js/actions.js"></script>

    </head>

    <body onload="recupererMessages()">

        
       <h2 class="titre">___ooo(O.O)ooo___</h2>


        <form  id="deconnexion" method="get" action="Logout">
            <div>
                <input type="submit" name="deconnexion" value="Déconnexion" />
            </div>
        </form>

        <table id="tabMessagesEnTete">
            <thead>
                <tr align="left">
                    <th class="date bleu">Heure</th>
                    <th class="auteur violet">Auteur</th>
                    <th class="message">Message</th>
                </tr>
            </thead>
            <tbody>
                <tr><td></td></tr>
            </tbody>
        </table>


        <div id="main">
            <table>
                <thead>
                    <tr align="left">
                        <th class="date"></th>
                        <th class="auteur"></th>
                        <th class="message"></th>
                    </tr>
                </thead>
                <tbody id="tabMessagesContenu">
                    <tr><td></td></tr>
                </tbody>
            </table>

            <a name="dernierMessage" />
        </div>


        <div id="saisie">
            <fieldset>
                <legend>Message à envoyer</legend>
                <form action="" onsubmit="return stockerMessage('texte')">
                    <div>
                      <input type="text" id="texte"/>
                      <button>Envoyer</button>
                    </div>
                </form>
            </fieldset>
        </div>

    </body>
</html>