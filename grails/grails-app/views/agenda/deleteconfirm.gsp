<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Confirmación: Borrar entrada</title>
</head>
<body>

<h1>¿Está seguro de que desea continuar?</h1>

<div align="center">¿Está seguro de querer borrar la siguiente entrada? <br></div>











<div class="table-responsive">
  <table class="table">
     <tr>
     <th class="col-md-5">Vendedor</th>
    <th class="col-md-5">Cliente</th>
    <th class="col-md-4">Día</th>
    <th class="col-md-1">Horario</th>
  </tr>


  <tr>

  <td class="tg-yw4l">
    ${params.nameseller}</td>
    <td class="tg-yw4l">
    ${params.nameclient}</td>
    
<td class="tg-yw4l">

<g:if test="${params.day=='1'}" >
Domingo
</g:if>
<g:if test="${params.day=='2'}" >
Lunes
</g:if>
<g:if test="${params.day=='3'}" >
Martes
</g:if>
<g:if test="${params.day=='4'}" >
Miércoles
</g:if>
<g:if test="${params.day=='5'}" >
Jueves
</g:if>
<g:if test="${params.day=='6'}" >
Viernes
</g:if>
<g:if test="${params.day=='7'}" >
Sábado
</g:if>

</td>

<td class="tg-yw4l">
    ${params.hour}</td>
    
  </tr>

  </table>
  </div>







<div align="center">
<g:link action="delete" id="${params.id}" ><button type="button" class="btn btn-default">Borrar</button></g:link>
<g:link action="index" ><button type="button" class="btn btn-default">Volver</button></g:link>
 </div>

</body>
</html>
