<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:html lang="true">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><bean:message key="welcome.title"/></title>
        <html:base/>
    </head>
    <body style="background-color: white">

        <logic:notPresent name="org.apache.struts.action.MESSAGE" scope="application">
            <div  style="color: red">
                ERROR:  Application resources not loaded -- check servlet container
                logs for error messages.
            </div>
        </logic:notPresent>

        <div>
            <h3>Bienvenue</h3>
            <p>Cette application va se comporter différemment pour les garçons et pour les filles.</p>
        </div>

        <div>

            <fieldset style="width:300px;"><legend>Connexion</legend>
                <html:form action="ValidationForm" >
                    Nom : <html:text property="nom" />
                    <br/>
                    Sexe :
                    <html:select property="sexe" >
                        <html:option value="Homme">Homme</html:option>
                        <html:option value="Femme">Femme</html:option>
                    </html:select>
                    <html:submit title="Envoyer" />
                </html:form>
            </fieldset>

            <br/>

            <a href="byeStruts.jsp" title="byeStruts">Quitter</a>

        </div>


    </body>
</html:html>
