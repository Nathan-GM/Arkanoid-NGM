<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="no"/>
    <xsl:template match="/level">
        <html lang="es">
                <head>
                    <meta charset="UTF8"/>
                    <title>Level</title>
                </head>
        <body>
            <h2>
                Listado de niveles
            </h2>
            <xsl:for-each select="//level">
            <h3>
                Nombre:
            </h3>
            <p>
                <xsl:value-of select="./name"/>
            </p>
            <h3>
                Tamano:
            </h3>
            <p>
                Altura: <xsl:value-of select="./size/height"/>
                Anchura: <xsl:value-of select="./size/width"/>
            </p>

            <h3>
                Sonido:
            </h3>
            <p>
                 <xsl:value-of select="./sound"/>
            </p>
            <h3>
                Tiempo:
            </h3>
            <p>
                 <xsl:value-of select="./time"/>
            </p>
            <h3>
                Posicion del Fondo:
            </h3>
            <p>
                <xsl:value-of select="./backgroundPosition"/>
            </p>

            <h3>
                Previsualización del nivel:
            </h3>
            <table border="1">
                <tbody>
                    <xsl:for-each select=".//blocks">
                        <tr>
                            <xsl:for-each select=".//item">
                                <xsl:variable name="fondo">
                                    <xsl:choose>
                                        <xsl:when test="not(value)">C0C0C0</xsl:when>
                                        <xsl:when test="value ='Blue'">0070FF</xsl:when>
                                        <xsl:when test="value ='Red'">DC143C</xsl:when>
                                        <xsl:when test="value ='White'">FFFFFF</xsl:when>
                                        <xsl:when test="value ='Cyan'">00FFFF</xsl:when>
                                        <xsl:when test="value ='Magenta'">FF00FF</xsl:when>
                                        <xsl:when test="value ='Yellow'">FFFF00</xsl:when>
                                        <xsl:when test="value ='Orange'">FFA500</xsl:when>
                                        <xsl:when test="value ='Green'">008000</xsl:when>
                                    </xsl:choose>
                                </xsl:variable>
                                <td bgcolor="{$fondo}">
                                    <xsl:choose>
                                        <xsl:when test="not(value)">null</xsl:when>
                                        <xsl:when test="value ='Blue'">Blue</xsl:when>
                                        <xsl:when test="value ='Red'">Red</xsl:when>
                                        <xsl:when test="value ='White'">White</xsl:when>
                                        <xsl:when test="value ='Cyan'">Cyan</xsl:when>
                                        <xsl:when test="value ='Magenta'">Magenta</xsl:when>
                                        <xsl:when test="value ='Yellow'">Yellow</xsl:when>
                                        <xsl:when test="value ='Orange'">Orange</xsl:when>
                                        <xsl:when test="value ='Green'">Green</xsl:when>
                                    </xsl:choose>
                                </td>
                            </xsl:for-each>
                        </tr>
                    </xsl:for-each>
                </tbody>
            </table>
            </xsl:for-each>
        </body>
        </html>
    </xsl:template>
</xsl:stylesheet>