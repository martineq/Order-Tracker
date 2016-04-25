<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Edición de agenda de ${params.name}</title>
</head>
<body>
            <h1>Edición de agenda de ${params.name}</h1>
<p>Edite los campos que desee</p>
            
<g:if test="${resula}">


<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="col-md-5">Cliente</th>
    <th class="col-md-1">Día</th>
    <th class="col-md-1">Horario</th>
    <th class="col-md-1">Editar</th>
    <th class="col-md-1">Borrar</th>
  </tr>
  

<g:each in="${resula}" var="res" status="i">


  <tr>
    <td class="tg-yw4l">${res[1]}</td>
    <td class="tg-yw4l">
    <g:if test="${res[2]==1}" >
Domingo
</g:if>
<g:if test="${res[2]==2}" >
Lunes
</g:if>
<g:if test="${res[2]==3}" >
Martes
</g:if>
<g:if test="${res[2]==4}" >
Miércoles
</g:if>
<g:if test="${res[2]==5}" >
Jueves
</g:if>
<g:if test="${res[2]==6}" >
Viernes
</g:if>
<g:if test="${res[2]==7}" >
Sábado
</g:if></td>
    <td class="tg-yw4l">${res[3]}</td>

  <td class="tg-yw4l">
  <g:link action="editagenda" params="[agendaid: "${res[4]}" , clientid: "${res[5]}" ,  hour: "${res[3]}" , nameclient: "${res[1]}" , day: "${res[2]}" , nameseller: "${params.name}" ]"><button type="button" class="btn btn-default">Editar</button></g:link>
  </td>
  
  <td class="tg-yw4l">
  <g:link action="show"><button type="button" class="btn btn-default">Borrar</button></g:link>
  </td>
  
  </tr>

  
  </g:each>
  </table>
  
</div>

</g:if>

<g:else>

<div align="center">

La agenda para este cliente está vacía.
</br>
<a href="/agenda/editlist"><button type="button" class="btn btn-default">Volver</button></a>
</div>
</g:else>

</body>
</html>
