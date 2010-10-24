<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : time.xsl
    Created on : 20 octobre 2009, 10:39
    Author     : p0805455
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>

    <xsl:template match="time">
        <p><xsl:value-of select="."/></p>
    </xsl:template>

</xsl:stylesheet>
