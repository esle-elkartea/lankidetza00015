function extrasNuevoFormulario(){
}
function extrasEdicionGrid(){
}
function extrasEdicionFormulario(){
	if (document.getElementById("imprimir_Operarios_1")){
		
		var divFechas = crearDiv();
		var spanInforme = crearLink();
		spanInforme.appendChild(divFechas);
		var element = document.getElementById("imprimir_Operarios_1");
		element.appendChild(spanInforme);
		//asignarOver(element.firstChild,"funcVer()");
		//element.appendChild(divFechas);	
		CalendarSetup("fecha1","fecha1Trigger");
		CalendarSetup("fecha2","fecha2Trigger");
	}
}

function crearLink(){
	var spanInforme = document.createElement("span");
	spanInforme.id="spanInforme";
	Element.addClassName(spanInforme,"spanInforme");
	var aInforme = document.createElement("a");
	aInforme.href = "#";
	aInforme.appendChild(document.createTextNode(" +"));
	asignarFuncion(aInforme,"funcVer()");
	spanInforme.appendChild(aInforme);
	return spanInforme;
}

function crearDiv(){
	var elemDiv = document.createElement("span");
	elemDiv.id="divFechas";
	elemDiv.name="divFechas";	
	Element.addClassName(elemDiv,"capaFechas");
	var span0 = document.createElement("span");
	Element.addClassName(span0,"spanDoble");
	span0.id="tituloFechas";	
	var span1 = document.createElement("span");
	Element.addClassName(span1,"spanDoble");
	var inputFecha1 = document.createElement("input");
	inputFecha1.id="fecha1";
	inputFecha1.name="fecha1";
	Element.addClassName(inputFecha1,"cajaFecha");
	var aFecha1 = document.createElement("a");
	aFecha1.href = "#";
	var imgFecha1 = document.createElement("img");
	imgFecha1.src = '../img/calendar.gif';
	imgFecha1.id="fecha1Trigger";
	var span2 = document.createElement("span");
	Element.addClassName(span2,"spanDoble");		
	var inputFecha2 = document.createElement("input");
	inputFecha2.id="fecha2";
	inputFecha2.name="fecha2";
	Element.addClassName(inputFecha2,"cajaFecha");
	var aFecha2 = document.createElement("a");
	aFecha2.href = "#";
	var imgFecha2 = document.createElement("img");	
	imgFecha2.src = '../img/calendar.gif';	
	imgFecha2.id="fecha2Trigger";
	var span3 = document.createElement("span");
	Element.addClassName(span3,"spanDoble");
	span3.id="confirmFechas";	
	var aAceptar = document.createElement("a");
	asignarFuncion(aAceptar,"funcAceptar()");
	var imgAceptar = document.createElement("img");
	imgAceptar.src = '../img/aceptar.gif';	
	var aCancelar = document.createElement("a");
	asignarFuncion(aCancelar,"funcCancelar()");
	var imgCancelar = document.createElement("img");
	imgCancelar.src = '../img/cancelar.gif';	
	
	span0.appendChild(document.createTextNode("Producciones con Fecha de parte entre: "));
	elemDiv.appendChild(span0);
	
	span1.appendChild(document.createTextNode("Fecha Inicio "));
	span1.appendChild(inputFecha1);
	aFecha1.appendChild(imgFecha1);
	span1.appendChild(aFecha1);
	elemDiv.appendChild(span1);
	
	span2.appendChild(document.createTextNode("Fecha Fin "));
	span2.appendChild(inputFecha2);
	aFecha2.appendChild(imgFecha2);
	span2.appendChild(aFecha2);
	elemDiv.appendChild(span2);		
	
	aAceptar.appendChild(imgAceptar);
	aAceptar.appendChild(document.createTextNode(" Aceptar "));
	span3.appendChild(aAceptar);
	aCancelar.appendChild(document.createTextNode(" Cancelar "));
	aCancelar.appendChild(imgCancelar);
	span3.appendChild(aCancelar);
	elemDiv.appendChild(span3);	
	return elemDiv;
}

function asignarFuncion(elem,func){
	if (!elem.setAttribute('onClick',func))
		elem['onclick']=new Function(func);	
}

function asignarOver(elem,func){
	if (!elem.setAttribute('onMouseOver',func))
		elem['onmouseover']=new Function(func);	
}

function asignarOut(elem,func){
	if (!elem.setAttribute('onMouseOut',func))
		elem['onmouseout']=new Function(func);	
}

function funcAceptar(){
	ponerParam("fecha1",document.getElementById("fecha1").value);
	ponerParam("fecha2",document.getElementById("fecha2").value);
	document.getElementById("divFechas").style.display = "none";
	imprimir('/adimde/Operarios/GetOperariosProduccionPdfServlet');
}

function funcCancelar(){
	document.getElementById("divFechas").style.display = "none";
}

function funcVer(){
	document.getElementById("divFechas").style.display = "block";
}
