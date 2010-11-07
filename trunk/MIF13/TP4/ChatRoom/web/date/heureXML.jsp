<%-- Crée le code xml de l'heure, qui sera inséré dans une page xml--%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%

    /*
    * Création d'un calenrier Géorgien pour la France.
    * Les méthodes getHours/Minutes/Seconds de Date sont dépréciées.
    * On préferera la classe Calendar.
    */
    Calendar calendar = new GregorianCalendar(new Locale("Fr"));
    calendar.setTime(new Date());

    int aff = 10;
    
    int nbheures = Calendar.HOUR_OF_DAY;
    int nbMin = Calendar.MINUTE;
    int nbSec = Calendar.SECOND;

    //Arrangement de l'affichage, ne fonctionne pas :p
    String heures = "" + calendar.get(nbheures);
    if(heures.length() == 1) heures = "0" + heures;

    String minutes = "" + calendar.get(nbMin);
    if(minutes.length() == 1) minutes = "0" + minutes;

    String secondes = "" + calendar.get(nbSec);
    if(secondes.length() == 1) secondes = "0" + secondes;

%>
<%@page contentType="text/xml" pageEncoding="UTF-8" %>
<date>
    <heures><%=heures%></heures>
    <minutes><%=minutes%></minutes>
    <secondes><%=secondes%></secondes>
</date>
