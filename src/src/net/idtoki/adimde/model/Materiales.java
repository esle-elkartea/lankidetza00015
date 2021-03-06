
package net.idtoki.adimde.model;


import java.util.logging.Logger;

import net.zylk.tools.format.FormatUtils;

import org.apache.torque.TorqueException;
import org.apache.torque.om.Persistent;

/**
 * The skeleton for this class was autogenerated by Torque on:
 *
 * [Thu Oct 26 17:10:22 CEST 2006]
 *
 * You should add additional methods to this class to meet the
 * application requirements.  This class will only be generated as
 * long as it does not already exist in the output directory.
 */
public  class Materiales
    extends net.idtoki.adimde.model.BaseMateriales
    implements Persistent
{

private static final Logger logger = Logger.getLogger("net.idtoki.adimde.model.Materiales");


		
	public String getIdmaterialMaterialesParsed()
	{
		return FormatUtils.genericParsedStr(this.getIdmaterial() + "");
	}					

		
	public String getNombreMaterialesParsed()
	{
		return FormatUtils.genericParsedStr(this.getNombre());		
	}					

		
	public String getIdunidadmedidaMaterialesParsed()
	{
		String strFK=null;
		try
		{
			strFK=this.getUnidadesmedida().getULContentUnidadesmedidaParsed();
		}
		catch (TorqueException te) {
			logger.severe("No se ha podido localizar Unidadesmedida. getIdunidadmedidaMaterialesParsed()" );
			te.printStackTrace();
		}
		return FormatUtils.genericParsedStr(strFK);		
	}					

		
	public String getCosteMaterialesParsed()
	{
		return FormatUtils.genericParsedStr(this.getCoste() + "");	 
	}					


	public String getPathMaterialesParsed(String strQueryString) throws TorqueException
	{	
	  String a = "";
	  if (strQueryString.indexOf("unidadesmedida") != -1)	  
	  	a = a + this.getUnidadesmedida().getPathUnidadesmedidaParsed(strQueryString) + "/";
	  a = a + this.getNombreMaterialesParsed();  
	  return a;
	}

	public String getULContentMaterialesParsed() throws TorqueException
	{
		return FormatUtils.genericParsedStr(this.getIdmaterial() + "-" + this.getNombreMaterialesParsed());	
	}	

	public String getHijosComposicionesMateriales()
	{
		return "<a href='#' class='hijos' id='getHijosComposicionesMateriales" + this.getIdmaterial() + "' title='Composiciones'>&#160;&#160;&#160;&#160;</a>";
	}
	
	public String getDeleteMateriales()
	{
		return "<a href='#' class='eliminar' id='getDeleteMateriales" + this.getIdmaterial() + "' title='Borrar'>&#160;&#160;&#160;&#160;</a>";
	}	
	public String getEditMateriales()
	{
		return "<a href='#' class='edicion' id='getEditMateriales" + this.getIdmaterial() + "' title='Editar'>&#160;&#160;&#160;&#160;</a>";
	}

}
