<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">

    <xsl:template match="/">
        <html>
            <head>
                <title><xsl:value-of select="CV/Entete/Titre" /></title>
            </head>
            <body>
                <xsl:apply-templates />
            </body>
        </html>
    </xsl:template>

    <xsl:template match="Entete">

        <h1><xsl:value-of select="Titre" /></h1>
        <xsl:apply-templates select="Etudiant" />

    </xsl:template>

    <xsl:template match="Etudiant">
        <ul>
            <li><xsl:value-of select="Prenom" /> <xsl:value-of select="Nom" /> (Num <xsl:value-of select="@Numero" />)</li>
            <xsl:apply-templates select="Adresse" />
            <li><xsl:value-of select="Telephone" /></li>
            <li><xsl:value-of select="Email" /></li>
        </ul>
    </xsl:template>

    <xsl:template match="Adresse">
        <li><xsl:value-of select="." /></li>
    </xsl:template>

    <xsl:template match="Contenu">
        <li><xsl:value-of select="." /></li>
    </xsl:template>

    <xsl:template match="Rubrique">
        <h2><xsl:value-of select="@Titre" /></h2>
        <ul>
            <xsl:apply-templates />
        </ul>
    </xsl:template>

    <xsl:template match="Item[../@Titre='Formation']" >
        <li><xsl:value-of select="Date" /> : <xsl:value-of select="Contenu" /></li>
    </xsl:template>

    <xsl:template match="Item[../@Titre='Competences']" >
        <h3><xsl:value-of select="SousTitre" /></h3>
        <ul>
            <xsl:for-each select="Contenu">
            <li><xsl:value-of select="." /></li>
            </xsl:for-each>
        </ul>
    </xsl:template>

</xsl:stylesheet>