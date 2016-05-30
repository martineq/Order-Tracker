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

<g:form controller="Graphics" action="topsellers" style="margin: 0px;border:0px;width:60%">


<div class="container">
   <div class="column-center2">
   
           <label>Mes: </label>
        <br>
        <g:select name="month"
               from="${months}" style="width: 80%" />
   
   </div>
   <div class="column-right2">

<label>Año </label> <g:textField  type="text"  name="year" pattern="^[0-9]+\\s*\$|^[0-9]\$"/>

   </div>
   <div class="column-left2">
    <label>Vendedor: </label>
        <br>
        <g:select name="seller"
                from="${sellersAll}"  style="width: 80%"/>
   </div>
</div>

<br>
<g:actionSubmit class="buttond" value="Ver" action="topsellers"   />
<br> <br>
</g:form>

<br>

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
<%-- ***Gráfico 3: Top 10 Vendedores***
 Top 10 ventas: Usa el filtro Vendedor, Mes, Año. Toma los 10 vendedores con mayor cantidad de ventas,
 además se muestra que porcentaje del total representan estos 10 vendedores. El formato gráfico será de torta dividida
 entre los 10 vendedores. En caso de elegir un vendedor específico se indicará que cantidad de ventas posee el mismo.
--%>

<%-- Primero define las columnas de la tabla (sus tipos y nombres), y luego los datos de la tabla --%>
<%-- "datosTopVendedores" debe estar ordenado por cantidad de ventas, de mayor a menor  --%>

<%
    def columnasTopVendedores = [['string', 'Vendedor'], ['number', 'Cantidad de ventas']]
    def datosTopVendedores = [[top10Names[0], numSales[0]], [top10Names[1], numSales[1]], [top10Names[2], numSales[2]], [top10Names[3], numSales[3]], [top10Names[4],numSales[4]],[top10Names[5], numSales[5]], [top10Names[6], numSales[6]], [top10Names[7], numSales[7]], [top10Names[8], numSales[8]], [top10Names[9], numSales[9]]]
%>

<%-- Creo el gráfico con los datos cargados anteriormente --%>
<gvisualization:pieCoreChart elementId="topVendedores" title="Top 10 vendedores" width="${600}" height="${600}"
                                columns="${columnasTopVendedores}" data="${datosTopVendedores}" />
<div id="topVendedores"></div>
        El resultado observado corresponde a un ${topSellersPercentage} % del total de vendedores
</g:if>
<g:else>
    <br>No hay resultados para mostrar
</g:else>


</center>
</body>
</html>
