<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="ajax.js" type="text/javascript"></script>
    </head>


    <script type="text/javascript">

        function nextTime() {
            loadXMLAsynchroneously('POST','heureXML.jsp',null ,'heure');
            setTimeout("nextTime()",5000);
        }
        
    </script>
    

    <body onload="nextTime();">
        Heure magique : <div id="heure" ></div>
    </body>
</html>
