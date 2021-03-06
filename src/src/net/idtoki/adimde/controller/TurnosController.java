package net.idtoki.adimde.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.torque.TorqueException;
import org.apache.torque.util.Criteria;

import net.idtoki.adimde.manager.TurnosManager;
import net.idtoki.adimde.group.TurnosGroupBean;
import net.idtoki.adimde.helper.TurnosHelper;
import net.idtoki.adimde.model.TurnosPeer;
import net.idtoki.adimde.model.Turnos;


import net.idtoki.adimde.helper.PartesHelper;
import net.idtoki.adimde.helper.PerfilturnoHelper;


import net.zylk.tools.ajax.AjaxUtils;
import net.zylk.tools.ajax.AjaxUtils.DinamicGridBean;
import net.zylk.tools.format.FormatUtils;
import net.zylk.tools.pdf.PdfUtils;
import net.zylk.tools.xml.XmlUtils;
import net.zylk.torque.TorqueUtils;
import net.zylk.web.WebUtils;

/**
 * The skeleton for this class was autogenerated by Torque on:
 *
 * [Mon Oct 02 10:34:00 CEST 2006]
 *
 *  You should add additional methods to this class to meet the
 *  application requirements.  This class will only be generated as
 *  long as it does not already exist in the output directory.
 */
 
 
public class TurnosController
    extends net.idtoki.adimde.controller.BaseTurnosController
{
 private static final Logger logger = Logger.getLogger("net.idtoki.adimde.controller.TurnosController");
 private TransformerFactory tFactory = TransformerFactory.newInstance();
 private Transformer turnos_transformer = null;  
 private Transformer turnoss_transformer = null; 

 public void init(ServletConfig config)
 {
	super.init();
	ResourceBundle resource = ResourceBundle.getBundle("net/idtoki/adimde/app/config/app-config");
	File turnos = new File(resource.getString("app.xsl.templates.dir")+"/PdfTurnos.xslt");
	Source xslSource = new StreamSource(turnos);
	File turnoss = new File(resource.getString("app.xsl.templates.dir")+"/PdfListaTurnos.xslt");
	Source xslSourceT = new StreamSource(turnoss); 	  
	try{
		turnos_transformer = tFactory.newTransformer(xslSource);
		turnoss_transformer = tFactory.newTransformer(xslSourceT);			  
	}catch (Exception e){
	  e.printStackTrace();
	}
 }


//Funci�n para la inserci�n/actualizaci�n de registros

public void serviceAddTurnos(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
 {
	 //Con esto se consigue transformar el request a UTF
	 //para temas de acentos y otros caracteres
	 utf8RequestService(request);

	 //recojo los parametros del formulario y doy de alta un nuevo elmento
	 try
	 {
		 Turnos elTurnos = TurnosHelper.createObj(request);
		 TurnosGroupBean gbTurnos = new TurnosGroupBean();
		 gbTurnos.setElemento(elTurnos);
		 gbTurnos.save();
	 }
	 catch(TorqueException te)
	 {
		 response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	 }
 }


//Funci�n para la eliminaci�n de un registro
//este m�todo invoca al m�todo public void deleteTurnos(int idBorrar)
//definido en el TurnosManager
 public void serviceDeleteTurnos(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
 {
	 int idBorrar=-1;
	 idBorrar=WebUtils.getintParam(request, "borrarId");
	 if (idBorrar!=-1)
 		 if (TurnosManager.borraTurnos(idBorrar))
			 WebUtils.writeXmlResponse(response,XmlUtils.buildXmlOKResponse("ISO-8859-1"));
		 else
			 WebUtils.writeXmlResponse(response,XmlUtils.buildXmlNotOKResponse("ISO-8859-1"));			 
 }


// Funciones para las ordenaciones y filtrados de informaci�n

 private Criteria ordenacion(Criteria c,String CampoOrdenacion,String OrdenOrdenacion) 
 { 
 	if((OrdenOrdenacion != null )&& (OrdenOrdenacion.compareTo("ASC")==0))
 		if ((CampoOrdenacion != null))
 		{
 			c.addAscendingOrderByColumn(CampoOrdenacion.toString());
 		}
 	if  ((OrdenOrdenacion != null) && (OrdenOrdenacion.compareTo("DESC")==0))
 		if ((CampoOrdenacion != null))
		{
 			c.addDescendingOrderByColumn(CampoOrdenacion.toString());
 		}
 	return c;
 }

private Criteria filtro(Criteria c,int filtro) 
{ 
	String cadena = null;
	 switch (filtro)
	 {
	 case 1:
		  //Caso uno de Filtrado
		  //c.add(Campo,valor);
		  break;
	 case 2:
		  //Caso dos de Filtrado
		  //c.add(Campo,valor);
		  break;
	 default:
		 //caso por defecto
		 break;
	 }
	return c;
}

 public StringBuffer replaceStringBuffer (StringBuffer strBA, String strOrigen, String strDestino) 
 {
	 return new StringBuffer(strBA.toString().replaceAll(strOrigen,strDestino));
 }

 private Criteria criteriaTurnosTableContent(HttpServletRequest request,Criteria c) throws IOException, ServletException
 {
 	String param = "";	 
	  String paramSortCol =  WebUtils.getStringParam(request, "sort_col");
	  String paramSortDir =  WebUtils.getStringParam(request, "sort_dir");
	  int paramFiltro =  WebUtils.getintParam(request, "filtro");
	  c = ordenacion(c,paramSortCol,paramSortDir);
	  c = filtro(c,paramFiltro);

 	  return c;
 } 
 
 public String getPathElementTurnos(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TorqueException
 {
	 Criteria c= new Criteria();
	 String strPath = "";
	 int claveId=-1;
	 int claveFkId=-1;	 
	 claveId=WebUtils.getintParam(request, "turnos.IDTURNO");
	 if (claveId!=-1)
	 {
		c.add(TurnosPeer.IDTURNO, claveId);
		TurnosGroupBean trgb = TurnosManager.getTurnoss(c);							
		strPath = trgb.getTurnos(0).getPathTurnosParsed(request.getQueryString());
	 }
	 return "<path>" + strPath + "</path>";
 } 
 
 public String getPathTableContentTurnos(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TorqueException
 {
	 Criteria c= new Criteria();
	 String strPath = "";
	 int claveId=-1;	 
	 return "<path>" + strPath + "</path>";
 }  


public void  serviceTurnosTableContent(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TorqueException
 {
	 utf8RequestService(request);
	 int numElemPedidosBD = 40;
	 int gap = 0;//gap = viewedRows - numElemVisiblesUltPag	  
	 DinamicGridBean dgb = WebUtils.getDinamicGridBeanParam(request,numElemPedidosBD,gap); 
	 Criteria c =TurnosManager.buildSearchCriteria(dgb);
	 c =criteriaTurnosTableContent(request,c);	 
	 TurnosGroupBean cgb = TurnosManager.getTurnoss(c);
	 dgb.setTotalSize(cgb.getTotalSize());
	  
	 String[] methodos= new String[] {
		TurnosHelper.DESCRIPCION_GET_METHOD_NAME+"TurnosParsed"
		,"getHijosPartesTurnos"
		,"getHijosPerfilturnoTurnos"
		,"getEditTurnos"
		,"getDeleteTurnos"
	};
	 
	 StringBuffer cadena=null;
	 cadena = AjaxUtils.buildXmlAjaxResponseTableContentFromListObj(cgb.getAlmacen(),methodos,TurnosHelper.IDTURNO_GET_METHOD_NAME, dgb,"ISO-8859-1"); 
	 cadena.insert(cadena.indexOf("</ajax-response>"),"<response type='object' id='divPath'>" + getPathTableContentTurnos(request,response) + "</response>");
	 xmlResponseService(response,cadena); 
 }  

 public void serviceTurnosUlContent(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
 {
	 utf8RequestService(request);
	 String[] methodos= new String[] {
	 		"getULContentTurnosParsed"
			 };
	 String param =  WebUtils.getStringParam(request, "value");
	 if(param==null || param.length() <= 0)
		 param =  WebUtils.getStringParam(request, new PartesHelper().getIdturnoName());
	 if(param==null || param.length() <= 0)
		 param =  WebUtils.getStringParam(request, new PerfilturnoHelper().getIdturnoName());
	 	 
	 TurnosGroupBean mgb = TurnosManager.getTurnoss(TurnosManager.buildSearchCriteria(param));
	 simpleResponseService(response, AjaxUtils.buildAjaxULContentFromListObj(mgb.getAlmacen(),methodos, TurnosHelper.COMPLEX_ID_GET_METHOD,"Turnos"));
 } 



 public void serviceTurnosElement(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TorqueException
 {
  StringBuffer cadena=null;
  Criteria c= new Criteria();
  int claveId=-1;
  claveId=WebUtils.getintParam(request, "turnos.IDTURNO");
  if (claveId!=-1)
  {
	  c.add(TurnosPeer.IDTURNO, claveId);
  }
  c.addAscendingOrderByColumn(TurnosPeer.IDTURNO);
  TurnosGroupBean trgb = TurnosManager.getTurnoss(c);
  
  if (trgb.getTotalSize()!=0)
  {
	  String [] parametros={
		TurnosHelper.IDTURNO_GET_METHOD_NAME
		,TurnosHelper.DESCRIPCION_GET_METHOD_NAME+"TurnosParsed"
	  };
	  cadena=trgb.buildXml(parametros,null,"ISO-8859-1");	
	  cadena.insert(cadena.indexOf("</result>"),getPathElementTurnos(request,response));  
  }
  xmlResponseService(response, cadena);
 } 
 

 protected StringBuffer updateIdturnoResponseCallBack(String s)
 {
  ArrayList a = AjaxUtils.splitIdFields(s);
  return new StringBuffer(TurnosManager.getTurnos(Integer.parseInt(a.get(0).toString())).getIdturnoTurnosParsed());
 }  
 

 protected StringBuffer updateDescripcionResponseCallBack(String s)
 {
  ArrayList a = AjaxUtils.splitIdFields(s);
  return new StringBuffer(TurnosManager.getTurnos(Integer.parseInt(a.get(0).toString())).getDescripcionTurnosParsed());
 }  
 
 

 public void serviceGetTurnosDetallePdf(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
 {
	 utf8RequestService(request);
	 String[] getMethodos= new String[] {
		TurnosHelper.IDTURNO_GET_METHOD_NAME
		,TurnosHelper.DESCRIPCION_GET_METHOD_NAME+"TurnosParsed"
			 };
	  
	 
	 TurnosGroupBean tgb = new TurnosGroupBean();
	 try
	{
		tgb.setElemento(TurnosHelper.getTurnos(request));
	} catch (TorqueException e)
	{
		logger.severe(e.getMessage());
	}
	
	 byte[] content =  PdfUtils.getBytes(replaceStringBuffer(tgb.buildXml(getMethodos, null,"ISO-8859-1"),"n/a"," "), turnos_transformer,"ISO-8859-1");
	 response.addHeader("content-disposition","attachment;filename=Turnos.pdf");
	 response.setContentType("application/pdf");
	 response.setHeader("Cache-Control", "");//para que funcione en IE
	 response.setContentLength(content.length);
	 response.getOutputStream().write(content);
	 response.getOutputStream().flush();  
	 response.getOutputStream().close();
	
 }  

 public void serviceTurnosTableContentPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
 {
  	utf8RequestService(request);
  	Criteria c= new Criteria();
  	String paramQuery =  WebUtils.getStringParam(request, "query");
  	if ((paramQuery != null)&& (paramQuery.compareTo("")!=0) )
		c = TurnosManager.buildSearchCriteria(paramQuery);   
  	c =criteriaTurnosTableContent(request,c);	  
  	TurnosGroupBean tgb = TurnosManager.getTurnoss(c);
  
  	String[] methodos= new String[] {	
		TurnosHelper.DESCRIPCION_GET_METHOD_NAME+"TurnosParsed"
			};
  
	 byte[] content =  PdfUtils.getBytes(replaceStringBuffer(tgb.buildXml(methodos, null,"ISO-8859-1"),"n/a"," "), turnoss_transformer,"ISO-8859-1");
	 response.addHeader("content-disposition","attachment;filename=ListaTurnos.pdf");
	 response.setContentType("application/pdf");
	 response.setHeader("Cache-Control", "");//para que funcione en IE
	 response.setContentLength(content.length);
	 response.getOutputStream().write(content);
	 response.getOutputStream().flush();  
	 response.getOutputStream().close();
 }

}


