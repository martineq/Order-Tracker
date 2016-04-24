<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Confirmación: Borrar a ${params.name}</title>
</head>
<body>

<h1>¿Está seguro de que desea continuar?</h1>

<div align="center">¿Está seguro de querer borrar al cliente ${params.name} ? </div>

<div align="center">
<g:link action="delete" id="${params.id}"  params="[name: ("${params.name}") ]" ><button type="button" class="btn btn-default">Borrar</button></g:link>
<g:link action="listdelete" ><button type="button" class="btn btn-default">Volver</button></g:link>
 </div>
 
  </table>
</div>

</body>
</html>
