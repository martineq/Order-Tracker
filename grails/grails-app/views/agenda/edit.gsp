<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Edición de agenda de ${params.name}</title>
</head>
<body>
            <h1>Edición de agenda de ${params.name}</h1>

            
<g:if test="${resula}">


<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="col-md-7">Cliente</th>
    <th class="col-md-1">Día</th>
    <th class="col-md-1">Horario</th>
    <th class="col-md-1">Editar</th>
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
  </tr>

  
  </g:each>
  </table>
  
</div>

</g:if>
<g:else>La agenda para este cliente está vacía.</g:else>

</body>
</html>
