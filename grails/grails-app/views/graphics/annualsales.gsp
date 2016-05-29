<!doctype html>
<html>
<head>
    <gvisualization:apiImport/>
    <meta name="layout" content="main"/>
    <title>Reportes gráficos</title>
</head>

<body>

<h1>Reportes gráficos: Ventas anuales </h1>
<center>

<%-- ***Gráfico 1: Ventas anuales***
 Comparativo de ventas del año: Usa el filtro Año. Compara las ventas del año mes a mes.
 El formato gráfico será de barras verticales representando cada mes del año ordenadas de enero a diciembre.
--%>

<%-- Primero define las columnas de la tabla (sus tipos y nombres), y luego los datos de la tabla --%>
<%-- "datosVentasAnuales" debe estar ordenado por mes, de Enero a Diciembre  --%>
<%
    def columnasVentasAnuales = [['string', 'Mes'], ['number', 'Ventas (en $)']]
    def datosVentasAnuales = [['Ene', 2], ['Feb', 4.5], ['Mar', 5.9], ['Abr', 6.8], ['May', 8.5], ['Jun', 6.5],
                                 ['Jul', 7], ['Ago', 6.8], ['Sep', 4.5], ['Oct', 6.7], ['Nov', 8.3], ['Dic', 12.5]]
%>

<%-- Creo el gráfico con los datos cargados anteriormente --%>
<gvisualization:columnCoreChart elementId="ventasAnuales" title="Ventas anuales" width="${1000}" height="${500}"
                                columns="${columnasVentasAnuales}" data="${datosVentasAnuales}" />
    <div id="ventasAnuales"></div>




</center>
</body>
</html>
