<!doctype html>
<html>
<head>
    <gvisualization:apiImport/>
    <meta name="layout" content="main"/>
    <title>Reportes gráficos</title>
</head>

<body>

<h1>Reportes gráficos</h1>
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




<%-- ***Gráfico 2: Marcas mas vendidas***
 Marcas mas vendidas del mes elegido: Usa el filtro Mes y Año. Obtiene para ese mes las marcas mas vendidas.
 El formato gráfico será de Barras verticales ordenadas decrecientemente.
 La cantidad total de barras en el gráfico estará definida por el tamaño de las mismas y el tamaño disponible en la página.
--%>

<%-- Primero define las columnas de la tabla (sus tipos y nombres), y luego los datos de la tabla --%>
<%-- "datosMarcasMasVendidas" debe estar ordenado por cantidad de ventas, de mayor a menor  --%>
<%-- *Hay que definir cuantas marcas se van a mostrar, en el ejemplo hay 22 elementos*  --%>
<%
    def columnasMarcasMasVendidas = [['string', 'Marca'], ['number', 'Cantidad de ventas']]
    def datosMarcasMasVendidas = [['Marca X', 934], ['Marca I', 912], ['Marca C', 888], ['Marca P', 854], ['Marca A', 822],
                                  ['Marca F', 811], ['Marca S', 793], ['Marca Z', 761], ['Marca B', 759], ['Marca V', 738],
                                  ['Marca D', 722], ['Marca G', 710], ['Marca J', 707], ['Marca N', 694], ['Marca L', 688],
                                  ['Marca O', 657], ['Marca E', 632], ['Marca H', 621], ['Marca W', 608], ['Marca K', 589],
                                  ['Marca Q', 572], ['Marca M', 545]]
%>

<%-- Creo el gráfico con los datos cargados anteriormente --%>
<gvisualization:columnCoreChart elementId="marcasMasVendidas" title="Marcas mas vendidas" width="${1000}" height="${500}"
                                columns="${columnasMarcasMasVendidas}" data="${datosMarcasMasVendidas}" />
    <div id="marcasMasVendidas"></div>




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
