<%@page import="java.util.Date" %>
<% Date currentDate = new Date(); %>
<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet href="time.xsl" type="text/xsl"?>
<root>
    <time><%= currentDate.toString() %></time>
</root>
