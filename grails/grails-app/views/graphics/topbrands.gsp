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
    def datosMarcasMasVendidas = [[top20Brands[0], numSales[0]], [top20Brands[1], numSales[1]], [top20Brands[2], numSales[2]], [top20Brands[3],  numSales[3]], [top20Brands[4],  numSales[4]],
                                  [top20Brands[5],  numSales[5]], [top20Brands[6],  numSales[6]], [top20Brands[7],  numSales[7]], [top20Brands[8], numSales[8]], [top20Brands[9], numSales[9]],
                                  [top20Brands[10],  numSales[10]], [top20Brands[11],  numSales[11]], [top20Brands[12],  numSales[12]], [top20Brands[13],  numSales[13]], [top20Brands[14],  numSales[14]],
                                  [top20Brands[15],  numSales[15]], [top20Brands[16], numSales[16]], [top20Brands[17],  numSales[17]], [top20Brands[18],  numSales[19]], [top20Brands[20],  numSales[20]]]
%>

<%-- Creo el gráfico con los datos cargados anteriormente --%>
<gvisualization:columnCoreChart elementId="marcasMasVendidas" title="Marcas mas vendidas" width="${1000}" height="${500}"
                                columns="${columnasMarcasMasVendidas}" data="${datosMarcasMasVendidas}" />
    <div id="marcasMasVendidas"></div>



</center>
</body>
</html>
