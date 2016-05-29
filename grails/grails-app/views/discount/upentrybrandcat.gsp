<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Agregar descuento </title>

</style>

</head>
<body>
<h1>Agregar descuento </h1>


<g:if test="${validDiscounts == false }">
Los descuentos introducidos son invalidos.
<br>
</g:if>

<g:if test="${validRanges == false}">
Los rangos introducidos son invalidos
<br>
</g:if>

<g:if test="${validDates == false}">
Las Fechas introducidas son invalidas
<br>
</g:if>

<g:if test="${validDates == false || validRanges == false || validDiscounts == false }">

<script>
function goBack() {
    window.history.back();
}
</script>

Vuelva al formulario para corregir el error:
<button onclick="goBack()">Volver</button>
<br>

</g:if>

<g:elseif test="${discountOverlap == true}">
!!! hay superposicion con otro descuento. Puede agregar el descuento, pero se borrarán los descuentos existentes que sean conflictivos.
<div align="center">
<g:link action="newdiscountbrand"  params="[discountOverlap:("${discountOverlap}") , datebeg:("${datebeg}"), dateend:("${dateend}"), range:("${params.range}"), desc1:("${desc1}"), desc2:("${desc2}"), desc3:("${desc3}"), descfinal:("${descfinal}"), ran2:("${ran2}"), ran3:("${ran3}") , brandid:("${params.brandid}"), category:("${params.category}") ]" ><button type="button" class="btn btn-default">Continuar</button></g:link>  </div>

</g:elseif>
<g:else>
Todos los datos introducidos son validos y no existe superposición con ningún descuento existente. Presione continuar para guardar el descuento.

<br><div align="center">
<g:link action="newdiscountbrand"  params="[ discountOverlap:("${discountOverlap}") , datebeg:("${datebeg}"), dateend:("${dateend}"), range:("${params.range}"), desc1:("${desc1}"), desc2:("${desc2}"), desc3:("${desc3}"), descfinal:("${descfinal}"), ran2:("${ran2}"), ran3:("${ran3}") , brandid:("${params.brandid}"), category:("${params.category}")   ]" ><button type="button" class="btn btn-default">Continuar</button></g:link> </div>
</g:else>

</body>
</html>
