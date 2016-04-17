<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Agenda de vendedores</title>

    <style>
    .dropdown-menu {
  left: 50%;
  right: auto;
  text-align: center;
  transform: translate(-50%, 0);
}
</style>

</head>

<body>

<h1>Agenda del vendedor: 


<g:each in="${sell}" var="se" status="i">

 ${se.name}
 </h1>
</br>
<p>¿Para qué día desea ver el calendario del vendedor?</p> 

  <div class="dropdown" align="center">
    <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Seleccione el dia...
    <span class="caret"></span></button>
    <ul class="dropdown-menu center"  align="center">
      <li><g:link action="show" params="[day: 1 ]"  id="${se.id}">Domingo</g:link></li>
     <li><g:link action="show" params="[day: 2 ]"  id="${se.id}">Lunes</g:link></li>
      <li><g:link action="show" params="[day: 3 ]"  id="${se.id}">Martes</g:link></li>
       <li><g:link action="show" params="[day: 4 ]"  id="${se.id}">Miércoles</g:link></li>
      <li><g:link action="show" params="[day: 5 ]"  id="${se.id}">Jueves</g:link></li>
      <li><g:link action="show" params="[day: 6 ]"  id="${se.id}">Viernes</g:link></li>
      <li><g:link action="show" params="[day: 7 ]"  id="${se.id}">Sábado</g:link></li>
      <li><g:link action="show" params="[day: 0 ]"  id="${se.id}">Todos</g:link></li>
    </ul>
  </div>
  
</g:each>

<g:if test="${resul}">


<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Cliente</th>
    <th class="tg-yw4l">Día</th>
    <th class="tg-yw4l">Horario</th>
  </tr>
  

<g:each in="${resul}" var="res" status="i">


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

<g:elseif test="${dayr != '-1'}">
<br>
<p><b>No hay clientes agendados...</b></p>
</g:elseif>

</body>
</html>
