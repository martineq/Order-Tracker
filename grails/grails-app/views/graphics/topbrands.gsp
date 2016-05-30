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

<g:form controller="Graphics" action="topbrands" style="margin: 0px;border:0px;width:60%">



<div id="mainContainer">
    <div id="divA">
   
           <label>Mes: </label>
        <br>
        <g:select name="month"
               from="${months}" style="width: 80%" />
   
   </div>
   <div id="divB">

<label>Año </label> <g:textField  type="text"  name="year" pattern="^[0-9]+\\s*\$|^[0-9]\$"/>

   </div>
  
</div>

<br>
<g:actionSubmit class="buttond" value="Ver" action="topbrands"   />
<br> 
</g:form>

    <g:if test="${params.month!=null && params.month!=""}" >
        <label>Mes filtrado: ${params.month} </label>
    </g:if>
    <g:else>
        Mes filtrado: (Sin filtro aplicado)
    </g:else>

    <g:if test="${params.year!=null && params.year!=""}" >
        <label> / Año filtrado: ${params.year}  </label>
    </g:if>
    <g:else>
        / Año filtrado: (Sin filtro aplicado)
    </g:else>


    <g:if test="${numSales[0]>0}" >
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
                                  [top20Brands[5],  numSales[5]], [top20Brands[6],  numSales[6]], [top20Brands[7],  numSales[7]], [top20Brands[8], numSales[8]], [top20Brands[9], numSales[9]]]
%>

<%-- Creo el gráfico con los datos cargados anteriormente --%>
<gvisualization:columnCoreChart elementId="marcasMasVendidas" title="Marcas mas vendidas" width="${1000}" height="${500}"
                                columns="${columnasMarcasMasVendidas}" data="${datosMarcasMasVendidas}" />
    <div id="marcasMasVendidas"></div>

</g:if>
<g:else><br>
No hay resultados para mostrar.
</g:else>


</center>
</body>
</html>
