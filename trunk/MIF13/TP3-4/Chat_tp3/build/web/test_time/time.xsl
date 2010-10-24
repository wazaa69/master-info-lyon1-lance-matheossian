<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <xsl:apply-templates select="root/time" />
    </xsl:template>

    <xsl:template match="time">
        <p style="color: blue; font-weight: bold;"><xsl:value-of select="."/></p>
    </xsl:template>

</xsl:stylesheet>
