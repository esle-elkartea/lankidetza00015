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

import net.idtoki.adimde.manager.ProductosManager;
import net.idtoki.adimde.group.ProductosGroupBean;
import net.idtoki.adimde.helper.ProductosHelper;
import net.idtoki.adimde.model.OperariosPeer;
import net.idtoki.adimde.model.ProductosPeer;
import net.idtoki.adimde.model.Productos;

import net.idtoki.adimde.manager.ProduccionesManager;
import net.idtoki.adimde.group.ProduccionesGroupBean;
import net.idtoki.adimde.model.ProduccionesPeer;
import net.idtoki.adimde.helper.ProduccionesHelper;

import net.idtoki.adimde.helper.ComposicionesHelper;
import net.idtoki.adimde.helper.PartesHelper;


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
 
 
public class ProductosController
    extends net.idtoki.adimde.controller.BaseProductosController
{
 private static final Logger logger = Logger.getLogger("net.idtoki.adimde.controller.ProductosController");
 private TransformerFactory tFactory = TransformerFactory.newInstance();
 private Transformer productos_transformer = null;  
 private Transformer productoss_transformer = null; 

 public void init(ServletConfig config)
 {
	super.init();
	ResourceBundle resource = ResourceBundle.getBundle("net/idtoki/adimde/app/config/app-config");
	File productos = new File(resource.getString("app.xsl.templates.dir")+"/PdfProductos.xslt");
	Source xslSource = new StreamSource(productos);
	File productoss = new File(resource.getString("app.xsl.templates.dir")+"/PdfListaProductos.xslt");
	Source xslSourceT = new StreamSource(productoss); 	  
	try{
		productos_transformer = tFactory.newTransformer(xslSource);
		productoss_transformer = tFactory.newTransformer(xslSourceT);			  
	}catch (Exception e){
	  e.printStackTrace();
	}
 }


//Funci�n para la inserci�n/actualizaci�n de registros

public void serviceAddProductos(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
 {
	 //Con esto se consigue transformar el request a UTF
	 //para temas de acentos y otros caracteres
	 utf8RequestService(request);

	 //recojo los parametros del formulario y doy de alta un nuevo elmento
	 try
	 {
		 Productos elProductos = ProductosHelper.createObj(request);
		 ProductosGroupBean gbProductos = new ProductosGroupBean();
		 elProductos.setFecha(FormatUtils.ddmmaaaa2aaaammdd(elProductos.getFecha(),"-",""));
		 gbProductos.setElemento(elProductos);
		 gbProductos.save();
	 }
	 catch(TorqueException te)
	 {
		 response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	 }
 }


//Funci�n para la eliminaci�n de un registro
//este m�todo invoca al m�todo public void deleteProductos(int idBorrar)
//definido en el ProductosManager
 public void serviceDeleteProductos(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
 {
	 int idBorrar=-1;
	 idBorrar=WebUtils.getintParam(request, "borrarId");
	 if (idBorrar!=-1)
 		 if (ProductosManager.borraProductos(idBorrar))
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
		  c.add(ProductosPeer.FECHA,(Object)cadena,Criteria.ISNULL);
		  break;
	 case 2:
		  //Caso dos de Filtrado
		  //c.add(Campo,valor);
		  c.add(ProductosPeer.FECHA,(Object)cadena,Criteria.ISNOTNULL);
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

 private Criteria criteriaProductosTableContent(HttpServletRequest request,Criteria c) throws IOException, ServletException
 {
 	String param = "";	 
	 param =  WebUtils.getStringParam(request, new ProduccionesHelper().getIdproduccionName());
	 if(param != null)
		 TorqueUtils.addEqualCriteria(c,ProductosPeer.IDPRODUCCION,param);
	  String paramSortCol =  WebUtils.getStringParam(request, "sort_col");
	  String paramSortDir =  WebUtils.getStringParam(request, "sort_dir");
	  int paramFiltro =  WebUtils.getintParam(request, "filtro");
	  c = ordenacion(c,paramSortCol,paramSortDir);
	  c = filtro(c,paramFiltro);

 	  return c;
 } 
 
 public String getPathElementProductos(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TorqueException
 {
	 Criteria c= new Criteria();
	 String strPath = "";
	 int claveId=-1;
	 int claveFkId=-1;	 
	 claveId=WebUtils.getintParam(request, "productos.IDPRODUCTO");
	 if (claveId!=-1)
	 {
		c.add(ProductosPeer.IDPRODUCTO, claveId);
		ProductosGroupBean trgb = ProductosManager.getProductoss(c);							
		strPath = trgb.getProductos(0).getPathProductosParsed(request.getQueryString());
	 }
	 return "<path>" + strPath + "</path>";
 } 
 
 public String getPathTableContentProductos(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TorqueException
 {
	 Criteria c= new Criteria();
	 String strPath = "";
	 int claveId=-1;	 
	 claveId=WebUtils.getintParam(request, "producciones.IDPRODUCCION");
	 if(claveId != -1){
		 strPath = ProduccionesManager.getProducciones(claveId).getPathProduccionesParsed(request.getQueryString());
	 }		 	 
	 return "<path>" + strPath + "</path>";
 }  


public void  serviceProductosTableContent(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TorqueException
 {
	 utf8RequestService(request);
	 int numElemPedidosBD = 40;
	 int gap = 0;//gap = viewedRows - numElemVisiblesUltPag	  
	 DinamicGridBean dgb = WebUtils.getDinamicGridBeanParam(request,numElemPedidosBD,gap); 
	 Criteria c =ProductosManager.buildSearchCriteria(dgb);
	 c =criteriaProductosTableContent(request,c);	 
	 ProductosGroupBean cgb = ProductosManager.getProductoss(c);
	 dgb.setTotalSize(cgb.getTotalSize());
	  
	 String[] methodos= new String[] {
		ProductosHelper.IDPRODUCCION_GET_METHOD_NAME+"ProductosParsed"
		,ProductosHelper.FECHA_GET_METHOD_NAME+"ProductosParsed"
		,ProductosHelper.REFERENCIA_GET_METHOD_NAME+"ProductosParsed"
		,ProductosHelper.ALMACEN_GET_METHOD_NAME+"ProductosParsed"
		,"getHijosComposicionesProductos"
		,"getHijosPartesProductos"
		,"getEditProductos"
		,"getDeleteProductos"
	};
	 
	 StringBuffer cadena=null;
	 cadena = AjaxUtils.buildXmlAjaxResponseTableContentFromListObj(cgb.getAlmacen(),methodos,ProductosHelper.IDPRODUCTO_GET_METHOD_NAME, dgb,"ISO-8859-1"); 
	 cadena.insert(cadena.indexOf("</ajax-response>"),"<response type='object' id='divPath'>" + getPathTableContentProductos(request,response) + "</response>");
	 xmlResponseService(response,cadena); 
 }  

 public void serviceProductosUlContent(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
 {
	 utf8RequestService(request);
	 String[] methodos= new String[] {
	 		"getULContentProductosParsed"
			 };
	 String param =  WebUtils.getStringParam(request, "value");
	 if(param==null || param.length() <= 0)
		 param =  WebUtils.getStringParam(request, new ComposicionesHelper().getIdproductoName());
	 if(param==null || param.length() <= 0)
		 param =  WebUtils.getStringParam(request, new PartesHelper().getIdproductoName());
	 	 
	 ProductosGroupBean mgb = ProductosManager.getProductoss(ProductosManager.buildSearchCriteria(param));
	 simpleResponseService(response, AjaxUtils.buildAjaxULContentFromListObj(mgb.getAlmacen(),methodos, ProductosHelper.COMPLEX_ID_GET_METHOD,"Productos"));
 } 



 public void serviceProductosElement(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TorqueException
 {
  StringBuffer cadena=null;
  Criteria c= new Criteria();
  int claveId=-1;
  claveId=WebUtils.getintParam(request, "productos.IDPRODUCTO");
  if (claveId!=-1)
  {
	  c.add(ProductosPeer.IDPRODUCTO, claveId);
  }
  c.addAscendingOrderByColumn(ProductosPeer.IDPRODUCTO);
  ProductosGroupBean trgb = ProductosManager.getProductoss(c);
  
  if (trgb.getTotalSize()!=0)
  {
	  String [] parametros={
		ProductosHelper.IDPRODUCTO_GET_METHOD_NAME
		,ProductosHelper.IDPRODUCCION_GET_METHOD_NAME+"ProductosParsed"
		,ProductosHelper.FECHA_GET_METHOD_NAME+"ProductosParsed"
		,ProductosHelper.REFERENCIA_GET_METHOD_NAME+"ProductosParsed"
		,ProductosHelper.ALMACEN_GET_METHOD_NAME+"ProductosParsed"
		,"getCosteMaterialesProductosParsed"
		,"getManoObraProductosParsed"
	  };
	  cadena=trgb.buildXml(parametros,null,"ISO-8859-1");	
	  cadena.insert(cadena.indexOf("</result>"),getPathElementProductos(request,response));  
  }
  xmlResponseService(response, cadena);
 } 
 

 protected StringBuffer updateIdproductoResponseCallBack(String s)
 {
  ArrayList a = AjaxUtils.splitIdFields(s);
  return new StringBuffer(ProductosManager.getProductos(Integer.parseInt(a.get(0).toString())).getIdproductoProductosParsed());
 }  
 

 protected StringBuffer updateIdproduccionResponseCallBack(String s)
 {
  ArrayList a = AjaxUtils.splitIdFields(s);
  return new StringBuffer(ProductosManager.getProductos(Integer.parseInt(a.get(0).toString())).getIdproduccionProductosParsed());
 }   
 

 protected StringBuffer updateFechaResponseCallBack(String s)
 {
  ArrayList a = AjaxUtils.splitIdFields(s);
  return new StringBuffer(ProductosManager.getProductos(Integer.parseInt(a.get(0).toString())).getFechaProductosParsed());
 }  
 

 protected StringBuffer updateReferenciaResponseCallBack(String s)
 {
  ArrayList a = AjaxUtils.splitIdFields(s);
  return new StringBuffer(ProductosManager.getProductos(Integer.parseInt(a.get(0).toString())).getReferenciaProductosParsed());
 }  
 

 protected StringBuffer updateAlmacenResponseCallBack(String s)
 {
  ArrayList a = AjaxUtils.splitIdFields(s);
  return new StringBuffer(ProductosManager.getProductos(Integer.parseInt(a.get(0).toString())).getAlmacenProductosParsed());
 }  
 
 
 
 public void serviceProductosProducciones(HttpServletRequest request, HttpServletResponse response) throws IOException, TorqueException, ServletException
 {
  String cadena=null;
  Criteria c= new Criteria();
  int claveId=-1;
  claveId=WebUtils.getintParam(request, "producciones.IDPRODUCCION");
  if (claveId!=-1)
  {
	  c.add(ProduccionesPeer.IDPRODUCCION, claveId);
  }
  c.addAscendingOrderByColumn(ProduccionesPeer.IDPRODUCCION);
  ProduccionesGroupBean trgb = ProduccionesManager.getProduccioness(c);
  
  if (trgb.getTotalSize()!=0)
  {
	  cadena = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>";
	  cadena = cadena + "<result><productos.IDPRODUCCION>" + trgb.getProducciones(0).getULContentProduccionesParsed()  + "</productos.IDPRODUCCION></result>";
  }
  
  xmlResponseService(response, new StringBuffer(cadena));
 }  
  			

 public void serviceGetProductosDetallePdf(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
 {
	 utf8RequestService(request);
	 String[] getMethodos= new String[] {
		ProductosHelper.IDPRODUCTO_GET_METHOD_NAME
		,ProductosHelper.IDPRODUCCION_GET_METHOD_NAME+"ProductosParsed"
		,ProductosHelper.FECHA_GET_METHOD_NAME+"ProductosParsed"
		,ProductosHelper.REFERENCIA_GET_METHOD_NAME+"ProductosParsed"
		,ProductosHelper.ALMACEN_GET_METHOD_NAME+"ProductosParsed"
		,"getCosteMaterialesProductosParsed"
		,"getManoObraProductosParsed"		
			 };
	  
	 
	 ProductosGroupBean tgb = new ProductosGroupBean();
	 try
	{
		tgb.setElemento(ProductosHelper.getProductos(request));
	} catch (TorqueException e)
	{
		logger.severe(e.getMessage());
	}
	
	 byte[] content =  PdfUtils.getBytes(replaceStringBuffer(tgb.buildXml(getMethodos, null,"ISO-8859-1"),"n/a"," "), productos_transformer,"ISO-8859-1");
	 response.addHeader("content-disposition","attachment;filename=Productos.pdf");
	 response.setContentType("application/pdf");
	 response.setHeader("Cache-Control", "");//para que funcione en IE
	 response.setContentLength(content.length);
	 response.getOutputStream().write(content);
	 response.getOutputStream().flush();  
	 response.getOutputStream().close();
	
 }  

 public void serviceProductosTableContentPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
 {
  	utf8RequestService(request);
  	Criteria c= new Criteria();
  	String paramQuery =  WebUtils.getStringParam(request, "query");
  	if ((paramQuery != null)&& (paramQuery.compareTo("")!=0) )
		c = ProductosManager.buildSearchCriteria(paramQuery);   
  	c =criteriaProductosTableContent(request,c);	  
  	ProductosGroupBean tgb = ProductosManager.getProductoss(c);
  
  	String[] methodos= new String[] {	
		ProductosHelper.IDPRODUCCION_GET_METHOD_NAME+"ProductosParsed"
		,ProductosHelper.FECHA_GET_METHOD_NAME+"ProductosParsed"
		,ProductosHelper.REFERENCIA_GET_METHOD_NAME+"ProductosParsed"
		,ProductosHelper.ALMACEN_GET_METHOD_NAME+"ProductosParsed"
			};
  
	 byte[] content =  PdfUtils.getBytes(replaceStringBuffer(tgb.buildXml(methodos, null,"ISO-8859-1"),"n/a"," "), productoss_transformer,"ISO-8859-1");
	 response.addHeader("content-disposition","attachment;filename=ListaProductos.pdf");
	 response.setContentType("application/pdf");
	 response.setHeader("Cache-Control", "");//para que funcione en IE
	 response.setContentLength(content.length);
	 response.getOutputStream().write(content);
	 response.getOutputStream().flush();  
	 response.getOutputStream().close();
 }

}


