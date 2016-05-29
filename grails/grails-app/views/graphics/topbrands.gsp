<!doctype html>
<html>
<head>
    <gvisualization:apiImport/>
    <meta name="layout" content="main"/>
    <title>Reportes gráficos</title>
</head>

<body>

<h1>Reportes gráficos: Marcas mas vendidas</h1>
<center>

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



</center>
</body>
</html>
