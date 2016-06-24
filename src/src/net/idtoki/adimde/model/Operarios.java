
package net.idtoki.adimde.model;


import java.util.List;
import java.util.logging.Logger;

import net.zylk.tools.format.FormatUtils;

import org.apache.commons.lang.ObjectUtils;
import org.apache.torque.TorqueException;
import org.apache.torque.om.Persistent;
import org.apache.torque.util.Criteria;

/**
 * The skeleton for this class was autogenerated by Torque on:
 *
 * [Mon Oct 02 10:34:00 CEST 2006]
 *
 * You should add additional methods to this class to meet the
 * application requirements.  This class will only be generated as
 * long as it does not already exist in the output directory.
 */
public  class Operarios
    extends net.idtoki.adimde.model.BaseOperarios
    implements Persistent
{

private static final Logger logger = Logger.getLogger("net.idtoki.adimde.model.Operarios");

	private String strFecha1="";
	private String strFecha2="";
		
	public String getIdoperarioOperariosParsed()
	{
		return FormatUtils.genericParsedStr(this.getIdoperario() + "");
	}					

		
	public String getIdperfilOperariosParsed()
	{
		String strFK=null;
		try
		{
			strFK=this.getPerfiles().getULContentPerfilesParsed();
		}
		catch (TorqueException te) {
			logger.severe("No se ha podido localizar Perfiles. getIdperfilOperariosParsed()" );
			te.printStackTrace();
		}
		return FormatUtils.genericParsedStr(strFK);		
	}					

		
	public String getNifOperariosParsed()
	{
		return FormatUtils.genericParsedStr(this.getNif());		
	}					

		
	public String getNombreOperariosParsed()
	{
		return FormatUtils.genericParsedStr(this.getNombre());		
	}					

		
	public String getApellidounoOperariosParsed()
	{
		return FormatUtils.genericParsedStr(this.getApellidouno());		
	}					

		
	public String getApellidodosOperariosParsed()
	{
		return FormatUtils.genericParsedStr(this.getApellidodos());		
	}					

		
	public String getFechaaltaOperariosParsed()
	{
		return FormatUtils.genericParsedStr(FormatUtils.aaaammdd2ddmmaaaa(this.getFechaalta(),"-"));		
	}					

		
	public String getFechabajaOperariosParsed()
	{
		return FormatUtils.genericParsedStr(FormatUtils.aaaammdd2ddmmaaaa(this.getFechabaja(),"-"));		
	}					

		
	public String getNumerossOperariosParsed()
	{
		return FormatUtils.genericParsedStr(this.getNumeross());		
	}					

		
	public String getDireccionOperariosParsed()
	{
		return FormatUtils.genericParsedStr(this.getDireccion());		
	}					

		
	public String getCpOperariosParsed()
	{
		return FormatUtils.genericParsedStr(this.getCp());		
	}					

		
	public String getIdlocalidadOperariosParsed()
	{
		String strFK=null;
		try
		{
			strFK=this.getLocalidades().getULContentLocalidadesParsed();
		}
		catch (TorqueException te) {
			logger.severe("No se ha podido localizar Localidades. getIdlocalidadOperariosParsed()" );
			te.printStackTrace();
		}
		return FormatUtils.genericParsedStr(strFK);		
	}					

		
	public String getTelefonoOperariosParsed()
	{
		return FormatUtils.genericParsedStr(this.getTelefono());		
	}					

		
	public String getMovilOperariosParsed()
	{
		return FormatUtils.genericParsedStr(this.getMovil());		
	}					

		
	public String getEmailOperariosParsed()
	{
		return FormatUtils.genericParsedStr(this.getEmail());		
	}					


	public String getPathOperariosParsed(String strQueryString) throws TorqueException
	{	
	  String a = "";
	  if (strQueryString.indexOf("localidades") != -1)	  
	  	a = a + this.getLocalidades().getPathLocalidadesParsed(strQueryString) + "/";
	  if (strQueryString.indexOf("perfiles") != -1)	  
	  	a = a + this.getPerfiles().getPathPerfilesParsed(strQueryString) + "/";
	  a = a + this.getNombreOperariosParsed() + " " + this.getApellidounoOperariosParsed();  
	  return a;
	}

	public String getULContentOperariosParsed() throws TorqueException
	{
		return FormatUtils.genericParsedStr(this.getIdoperario() + "-" + this.getNombreOperariosParsed() + " " + this.getApellidounoOperariosParsed());	
	}	

	public String getHijosPartesOperarios()
	{
		return "<a href='#' class='hijos' id='getHijosPartesOperarios" + this.getIdoperario() + "' title='Partes'>&#160;&#160;&#160;&#160;</a>";
	}
	
	public String getDeleteOperarios()
	{
		return "<a href='#' class='eliminar' id='getDeleteOperarios" + this.getIdoperario() + "' title='Borrar'>&#160;&#160;&#160;&#160;</a>";
	}	
	public String getEditOperarios()
	{
		return "<a href='#' class='edicion' id='getEditOperarios" + this.getIdoperario() + "' title='Editar'>&#160;&#160;&#160;&#160;</a>";
	}
	
    public List getPartess2() throws TorqueException
    {
      	Criteria c= new Criteria();
      	String strPar = "FECHA is NOT NULL";
      	if (getFecha1().compareTo("") != 0)
      		strPar = strPar + " AND FECHA > " + getFecha1();
      	if (getFecha2().compareTo("") != 0)
      		strPar = strPar + " AND FECHA < " + getFecha2();      	      	
      	c.addJoin(OperariosPeer.IDOPERARIO,PartesPeer.IDOPERARIO);
      	c.add(OperariosPeer.IDOPERARIO, getIdoperario());
      	c.add(PartesPeer.FECHA, (Object)strPar, c.CUSTOM);    	
        return getPartess(c);
    }	

    public String getFecha1()
    {
    	return strFecha1;
    }
                            
    public void setFecha1(String v) 
    {
    	this.strFecha1 = v;
    }
    
    public String getFecha2()
    {
    	return strFecha2;
    }
                            
    public void setFecha2(String v) 
    {
    	this.strFecha2 = v;
    }    
}
