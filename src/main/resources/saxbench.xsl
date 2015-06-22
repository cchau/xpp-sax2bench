<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" indent="yes"/>
    <xsl:template match="saxbench-results">

        <html>
            <head><title>SAXBench Results</title>
                <link rel="stylesheet" href="bluecast.css" type="text/css"/></head>

            <body>

                <center>
                    <p>
                        <h2>SOAP Results (Namespaces off)</h2>
                        <table border="0" class="databorder" cellspacing="0" cellpadding="1">
                            <tr>
                                <td>
                                    <table border="0" width="514" class="datatable">
                                        <tr class="dataheader">
                                            <th>Parser</th>
                                            <xsl:for-each select="parser[position()=1]/test[contains(@name,'SOAP')]">
                                                <th><xsl:value-of select="@name"/></th>
                                            </xsl:for-each>
                                        </tr>
                                        <xsl:for-each select="parser[contains(@name,'Oracle') or not(contains(@name,'w/NS'))]">
                                            <xsl:sort select="test[@name='SOAP 1 (0.5K)']/benchmark/@elapsed" order="ascending" data-type="number"/>
                                            <tr class="datarow">
                                                <td><xsl:value-of select="@name"/></td>
                                                <xsl:for-each select="test[contains(@name,'SOAP')]">
                                                    <td align="right"><xsl:value-of select="format-number(benchmark/@elapsed div benchmark/@iterations,'0.###')"/></td>
                                                </xsl:for-each>
                                            </tr>
                                        </xsl:for-each>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </p>

                    <p>
                        <h2>SOAP Results (Namespaces on)</h2>
                        <table border="0" class="databorder" cellspacing="0" cellpadding="1">
                            <tr>
                                <td>
                                    <table border="0" width="514" class="datatable">
                                        <tr class="dataheader">
                                            <th>Parser</th>
                                            <xsl:for-each select="parser[position()=1]/test[contains(@name,'SOAP')]">
                                                <th><xsl:value-of select="@name"/></th>
                                            </xsl:for-each>
                                        </tr>
                                        <xsl:for-each select="parser[contains(@name,'w/NS')]">
                                            <xsl:sort select="test[@name='SOAP 1 (0.5K)']/benchmark/@elapsed" order="ascending" data-type="number"/>
                                            <tr class="datarow">
                                                <td><xsl:value-of select="substring-before(@name,' w/NS')"/></td>
                                                <xsl:for-each select="test[contains(@name,'SOAP')]">
                                                    <td align="right"><xsl:value-of select="format-number(benchmark/@elapsed div benchmark/@iterations,'0.###')"/></td>
                                                </xsl:for-each>
                                            </tr>
                                        </xsl:for-each>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </p>

                    <p>
                        <h2>Random XML</h2>
                        <table border="0" class="databorder" cellspacing="0" cellpadding="1">
                            <tr>
                                <td>
                                    <table border="0" width="514" class="datatable">
                                        <tr class="dataheader">
                                            <th>Parser</th>
                                            <xsl:for-each select="parser[position()=1]/test[contains(@name,'Random')]">
                                                <th><xsl:value-of select="@name"/></th>
                                            </xsl:for-each>
                                        </tr>
                                        <xsl:for-each select="parser[contains(@name,'Oracle') or not(contains(@name,'w/NS'))]">
                                            <xsl:sort select="test[@name='Random 100 (33K)']/benchmark/@elapsed" order="ascending" data-type="number"/>
                                            <tr class="datarow">
                                                <td><xsl:value-of select="@name"/></td>
                                                <xsl:for-each select="test[contains(@name,'Random')]">
                                                    <td align="right"><xsl:value-of select="format-number(benchmark/@elapsed div benchmark/@iterations,'0.###')"/></td>
                                                </xsl:for-each>
                                            </tr>
                                        </xsl:for-each>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </p>

                    <p>
                        <h2>Mostly Tags</h2>
                        <table border="0" class="databorder" cellspacing="0" cellpadding="1">
                            <tr>
                                <td>
                                    <table border="0" width="514" class="datatable">
                                        <tr class="dataheader">
                                            <th>Parser</th>
                                            <xsl:for-each select="parser[position()=1]/test[contains(@name,'Topic Map')]">
                                                <th><xsl:value-of select="@name"/></th>
                                            </xsl:for-each>
                                        </tr>
                                        <xsl:for-each select="parser">
                                            <xsl:sort select="test[contains(@name,'Topic Map')]/benchmark/@elapsed" order="ascending" data-type="number"/>
                                            <tr class="datarow">
                                                <td><xsl:value-of select="@name"/></td>
                                                <xsl:for-each select="test[contains(@name,'Topic Map')]">
                                                    <td align="right"><xsl:value-of select="format-number(benchmark/@elapsed div benchmark/@iterations,'0.###')"/></td>
                                                </xsl:for-each>
                                            </tr>
                                        </xsl:for-each>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </p>

                    <p>
                        <h2>Mostly Text</h2>
                        <table border="0" class="databorder" cellspacing="0" cellpadding="1">
                            <tr>
                                <td>
                                    <table border="0" width="514" class="datatable">
                                        <tr class="dataheader">
                                            <th>Parser</th><th>Testaments</th>
                                        </tr>
                                        <xsl:for-each select="parser">
                                            <xsl:sort select="test[contains(@name,'Mostly Text')]/benchmark/@elapsed" order="ascending" data-type="number"/>
                                            <tr class="datarow">
                                                <td><xsl:value-of select="@name"/></td>
                                                <xsl:for-each select="test[contains(@name,'Mostly Text')]">
                                                    <td align="right"><xsl:value-of select="format-number(benchmark/@elapsed div benchmark/@iterations,'0.###')"/></td>
                                                </xsl:for-each>
                                            </tr>
                                        </xsl:for-each>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </p>




                </center>
            </body>
        </html>

    </xsl:template>
</xsl:stylesheet>