<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Agregar descuento para el producto ${params.nameproduct}</title>

</style>

</head>
<body>
<h1>Agregar descuento para el producto ${params.nameproduct}</h1>


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
<div align="center">
<asset:image src="warning.png" title="Editar" alt="Editar" style="width:25px;height:25px;"/> Hay superposicion con otro descuento. Puede agregar el descuento, pero se borrarán los descuentos existentes que sean conflictivos.</div>
<div align="center">
<g:link action="newdiscount"  params="[discountOverlap:("${discountOverlap}") , datebeg:("${datebeg}"), dateend:("${dateend}"), range:("${params.range}"), productid:("${params.productid}"), desc1:("${desc1}"), desc2:("${desc2}"), desc3:("${desc3}"), descfinal:("${descfinal}"), ran2:("${ran2}"), ran3:("${ran3}")  ]" ><button type="button" class="btn btn-default">Continuar</button></g:link>  </div>

</g:elseif>
<g:else>
Todos los datos introducidos son validos y no existe superposición con ningún descuento existente. Presione continuar para guardar el descuento.

<br><div align="center">
<br> 
<g:link action="newdiscount"  params="[ discountOverlap:("${discountOverlap}") , datebeg:("${datebeg}"), dateend:("${dateend}"), range:("${params.range}"), productid:("${params.productid}"), desc1:("${desc1}"), desc2:("${desc2}"), desc3:("${desc3}"), descfinal:("${descfinal}"), ran2:("${ran2}"), ran3:("${ran3}")  ]" ><button type="button" class="btn btn-default">Continuar</button></g:link> </div>
</g:else>

</body>
</html>
