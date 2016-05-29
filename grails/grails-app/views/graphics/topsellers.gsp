<!doctype html>
<html>
<head>
    <gvisualization:apiImport/>
    <meta name="layout" content="main"/>
    <title>Reportes gráficos</title>
</head>

<body>

<h1>Reportes gráficos: Top 10 vendedores</h1>
<center>



<%-- ***Gráfico 3: Top 10 Vendedores***
 Top 10 ventas: Usa el filtro Vendedor, Mes, Año. Toma los 10 vendedores con mayor cantidad de ventas,
 además se muestra que porcentaje del total representan estos 10 vendedores. El formato gráfico será de torta dividida
 entre los 10 vendedores. En caso de elegir un vendedor específico se indicará que cantidad de ventas posee el mismo.
--%>

<%-- Primero define las columnas de la tabla (sus tipos y nombres), y luego los datos de la tabla --%>
<%-- "datosTopVendedores" debe estar ordenado por cantidad de ventas, de mayor a menor  --%>

<%
    def columnasTopVendedores = [['string', 'Vendedor'], ['number', 'Cantidad de ventas']]
    def datosTopVendedores = [['Vendedor Y', 8934], ['Vendedor J', 7912], ['Vendedor D', 6888], ['Vendedor Q', 5854], ['Vendedor B', 4822],
                                  ['Vendedor G', 3811], ['Vendedor T', 2793], ['Vendedor A', 1761], ['Vendedor C', 759], ['Vendedor N', 545]]
%>

<%-- Creo el gráfico con los datos cargados anteriormente --%>
<gvisualization:pieCoreChart elementId="topVendedores" title="Top 10 vendedores" width="${600}" height="${600}"
                                columns="${columnasTopVendedores}" data="${datosTopVendedores}" />
<div id="topVendedores"></div>


</center>
</body>
</html>
