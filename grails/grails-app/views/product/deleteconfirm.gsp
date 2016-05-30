<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Confirmación: Borrar a ${params.name}</title>
</head>
<body>




<g:if test="${discountconflict==false}" >
<h1>¿Está seguro de que desea continuar?</h1>

<div align="center">¿Está seguro de querer borrar el producto ${params.name} ? <br>
</div>

<div align="center">
<g:link action="delete" id="${params.id}"  params="[name: ("${params.name}") ]" ><button type="button" class="btn btn-default">Borrar</button></g:link>
<g:link action="index" ><button type="button" class="btn btn-default">Volver</button></g:link>
 </div>
 </g:if>

<g:else >
<h1><asset:image src="warning.png" title="Editar" alt="Editar" style="width:45px;height:45px;"/> Se detectó un conflicto</h1>
<div align="center">

 <g:if test="${discountconflict==true}" >
 ⚫ No puede eliminar este producto porque existen descuentos asociados a la misma.<br>
 </g:if>
 
 Elimine los conflictos existentes y vuelva a intentarlo.<br>
 <g:link action="index" ><button type="button" class="btn btn-default">Volver</button></g:link>
</g:else>

</div>


</div>

</body>
</html>
