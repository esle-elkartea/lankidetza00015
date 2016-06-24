<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.0">
	<xsl:output method="fo"/>
	<xsl:include href="main.xslt"/>
	<xsl:template match="result">
		<xsl:for-each select="./Producciones">
			<fo:table table-layout="fixed" >
				<fo:table-column column-width="4cm"/>
				<fo:table-column column-width="4cm"/>
				<fo:table-column column-width="4cm"/>
				<fo:table-column column-width="4cm"/>				
				<fo:table-body>	
					<fo:table-row>
						<fo:table-cell text-align="left" padding="0.4cm" padding-after="0.1cm" padding-before="0.1cm"   table-layout="fixed">
							<fo:block font-size="10pt">
								Id.
							</fo:block>						
						</fo:table-cell>
						<fo:table-cell text-align="left" padding="0.4cm" padding-after="0.1cm" padding-before="0.1cm"   border-style="solid" border-width="0.1mm"  table-layout="fixed">
							<fo:block font-size="10pt">
								<xsl:value-of select="Idproduccion"/>
							</fo:block>						
						</fo:table-cell>
						<fo:table-cell text-align="left" padding="0.4cm" padding-after="0.1cm" padding-before="0.1cm"   table-layout="fixed">
							<fo:block font-size="10pt">
								Fecha de inicio
							</fo:block>						
						</fo:table-cell>
						<fo:table-cell text-align="left" padding="0.4cm" padding-after="0.1cm" padding-before="0.1cm"   border-style="solid" border-width="0.1mm"  table-layout="fixed">
							<fo:block font-size="10pt">
								<xsl:value-of select="FechainicioProduccionesParsed"/>
							</fo:block>						
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell text-align="left" padding="0.4cm" padding-after="0.1cm" padding-before="0.1cm"   table-layout="fixed">
							<fo:block font-size="10pt">
								Fecha de fin
							</fo:block>						
						</fo:table-cell>
						<fo:table-cell text-align="left" padding="0.4cm" padding-after="0.1cm" padding-before="0.1cm"   border-style="solid" border-width="0.1mm"  table-layout="fixed">
							<fo:block font-size="10pt">
								<xsl:value-of select="FechafinProduccionesParsed"/>
							</fo:block>						
						</fo:table-cell>
						<fo:table-cell text-align="left" padding="0.4cm" padding-after="0.1cm" padding-before="0.1cm"   table-layout="fixed">
							<fo:block font-size="10pt">
								Tipo de trabajo
							</fo:block>						
						</fo:table-cell>
						<fo:table-cell text-align="left" padding="0.4cm" padding-after="0.1cm" padding-before="0.1cm"   border-style="solid" border-width="0.1mm"  table-layout="fixed">
							<fo:block font-size="10pt">
								<xsl:value-of select="IdtipotrabajoProduccionesParsed"/>
							</fo:block>						
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>			
						<fo:table-cell text-align="left" padding="0.4cm" padding-after="0.1cm" padding-before="0.1cm"   table-layout="fixed">
							<fo:block font-size="10pt">
								Estado
							</fo:block>						
						</fo:table-cell>
						<fo:table-cell text-align="left" padding="0.4cm" padding-after="0.1cm" padding-before="0.1cm"   border-style="solid" border-width="0.1mm"  table-layout="fixed">
							<fo:block font-size="10pt">
								<xsl:value-of select="EstadoProduccionesParsed"/>
							</fo:block>						
						</fo:table-cell>
						<fo:table-cell text-align="left" padding="0.4cm" padding-after="0.1cm" padding-before="0.1cm"   table-layout="fixed">
							<fo:block font-size="10pt">
								Centro
							</fo:block>						
						</fo:table-cell>
						<fo:table-cell text-align="left" padding="0.4cm" padding-after="0.1cm" padding-before="0.1cm"   border-style="solid" border-width="0.1mm"  table-layout="fixed">
							<fo:block font-size="10pt">
								<xsl:value-of select="IdcentroProduccionesParsed"/>
							</fo:block>						
						</fo:table-cell>						
					</fo:table-row>
					<fo:table-row>			
						<fo:table-cell text-align="left" padding="0.4cm" padding-after="0.1cm" padding-before="0.1cm"   table-layout="fixed">
							<fo:block font-size="10pt">
								Materiales
							</fo:block>						
						</fo:table-cell>
						<fo:table-cell text-align="left" padding="0.4cm" padding-after="0.1cm" padding-before="0.1cm"   border-style="solid" border-width="0.1mm"  table-layout="fixed">
							<fo:block font-size="10pt">
								<xsl:value-of select="CosteMaterialesProduccionesParsed"/>
							</fo:block>						
						</fo:table-cell>
						<fo:table-cell text-align="left" padding="0.4cm" padding-after="0.1cm" padding-before="0.1cm"   table-layout="fixed">
							<fo:block font-size="10pt">
								Mano de obra
							</fo:block>						
						</fo:table-cell>
						<fo:table-cell text-align="left" padding="0.4cm" padding-after="0.1cm" padding-before="0.1cm"   border-style="solid" border-width="0.1mm"  table-layout="fixed">
							<fo:block font-size="10pt">
								<xsl:value-of select="ManoObraProduccionesParsed"/>
							</fo:block>						
						</fo:table-cell>						
					</fo:table-row>					
				</fo:table-body>
			</fo:table>																											
		</xsl:for-each>	
	</xsl:template>
	<xsl:include href="elementosCabecera.xslt"/>
</xsl:stylesheet>


