<!doctype html>
<html>
<head>
    <gvisualization:apiImport/>
    <meta name="layout" content="main"/>
    <title>Reportes gráficos</title>
</head>

<body>

<h1>Reportes gráficos: Ventas del año ${year} </h1>

    
    <center>
    <br>
    

<g:form controller="Graphics" action="annualsales" style="margin: 0;border:0px;width:500px">

<div id="mainContainer1">
    <div id="divA1">
    
    Ver para otro Año:
    
   
</div>    
    <div id="divB1">
    
    
    
     <div id="mainContainer">
    <div id="divA">
    
    
     <g:textField  type="text" name="year" required=""  pattern="^[0-9]+\\s*\$|^[0-9]+\\.?[0-9]+\\s*\$" />
    
    </div>
    <div id="divB">

     <g:actionSubmit  value="Ver" class="buttongraph" action="annualsales"/>  
     
     </div>
     
     </div>
     
</div>
</div>    

</g:form>

    <br>
<%-- ***Gráfico 1: Ventas anuales***
 Comparativo de ventas del año: Usa el filtro Año. Compara las ventas del año mes a mes.
 El formato gráfico será de barras verticales representando cada mes del año ordenadas de enero a diciembre.
--%>

<%-- Primero define las columnas de la tabla (sus tipos y nombres), y luego los datos de la tabla --%>
<%-- "datosVentasAnuales" debe estar ordenado por mes, de Enero a Diciembre  --%>
<%
    def columnasVentasAnuales = [['string', 'Mes'], ['number', 'Ventas (en $)']]
    def datosVentasAnuales = [['Ene', "${res[0]}"], ['Feb', "${res[1]}"], ['Mar', "${res[2]}"], ['Abr', "${res[3]}"], ['May', "${res[4]}"], ['Jun', "${res[5]}"],
                                 ['Jul', "${res[6]}"], ['Ago', "${res[7]}"], ['Sep', "${res[8]}"], ['Oct', "${res[9]}"], ['Nov', "${res[10]}"], ['Dic', "${res[11]}"]]
%>

<%-- Creo el gráfico con los datos cargados anteriormente --%>
<gvisualization:columnCoreChart elementId="ventasAnuales" title="Ventas del año" width="${1000}" height="${500}"
                                columns="${columnasVentasAnuales}" data="${datosVentasAnuales}" />
    <div id="ventasAnuales"></div>
  


</center>
</body>
</html>
