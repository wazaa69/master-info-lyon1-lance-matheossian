<%-- Crée le code xml de l'heure, qui sera inséré dans une page xml--%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%

    //Création d'un calenrier Géorgien pour la France
    Calendar calendar = new GregorianCalendar(new Locale("Fr"));
    calendar.setTime(new Date());

    int aff = 10;
    
    int nbhours = Calendar.HOUR_OF_DAY;
    int nbMin = Calendar.MINUTE;
    int nbSec = Calendar.SECOND;

    //Arrangement de l'affichage, ne fonctionne pas :p
    String hours = "" + calendar.get(nbhours);
    if(hours.length() == 1) hours = "0" + hours;

    String min = "" + calendar.get(nbMin);
    if(min.length() == 1) min = "0" + min;

    String sec = "" + calendar.get(nbSec);
    if(sec.length() == 1) sec = "0" + sec;

%>
<date>
    <heure><%=hours%>:</heure>
    <min><%=min%>:</min>
    <sec><%=sec%></sec>
</date>
