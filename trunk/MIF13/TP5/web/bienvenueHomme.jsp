<%@page language="java"%>


<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Validation formulaire</title>
    </head>
    <body>
        <h2>Bonjour Monsieur</h2>
        <div style="color:blue;">
            Votre nom : <bean:write name="Profil" property="nom" /> <br/>
            Votre sexe : <bean:write name="Profil" property="sexe" />
        </div>
    </body>
</html>