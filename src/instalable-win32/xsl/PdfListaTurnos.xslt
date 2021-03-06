<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java" version="1.0">
<xsl:output method="fo"/>
	<xsl:include href="mainLista.xslt"/>
	<xsl:template match="result">
		<fo:table border-style="solid" border-width="0.1mm" table-layout="fixed" space-before="1cm">
			<fo:table-column column-width="24cm"/>
			<fo:table-body>
				<fo:table-row background-color="#c0c0c0">
					<fo:table-cell text-align="center" border-style="solid" border-width="0.1mm" table-layout="fixed">
						<fo:block>Descripcion</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>	
		<xsl:for-each select="./Turnos">
			<fo:table border-style="solid" border-width="0.1mm" table-layout="fixed">
				<fo:table-column column-width="24cm"/>
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell text-align="center" padding-after="0.3cm" padding-before="0.3cm" border-style="solid" border-width="0.1mm" table-layout="fixed">		
							<fo:block>
								<xsl:value-of select="DescripcionTurnosParsed"/>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>						
		</xsl:for-each>
		<fo:table border-style="solid" border-width="0.1mm" table-layout="fixed">
			<fo:table-column column-width="24cm"/>
			<fo:table-body>
				<fo:table-row background-color="#c0c0c0">
					<fo:table-cell text-align="right" padding-right="0.5cm" border-style="solid" border-width="0.1mm" table-layout="fixed">
						<fo:block>		
							N� de registros: <xsl:value-of select="count(./Turnos)"/>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>					
		</fo:table>			
	</xsl:template>
	<xsl:include href="elementosCabecera.xslt"/>
</xsl:stylesheet>


