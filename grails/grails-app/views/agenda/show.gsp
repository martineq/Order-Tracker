<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Agenda de vendedores</title>

    <style>
    .dropdown-menu-2 {
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
    <ul class="dropdown-menu-2 dropdown-menu center"  align="center">
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
  


 <div align="right"><g:link action="up" params="[sellerid: "${se.id}" , nameseller: "${se.name}" ]"><b>Agregar entrada</b><asset:image src="add.png" alt="Editar" style="width:38px;height:38px;"/></g:link></div>

 </g:each>
 
<g:if test="${resul}">


<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="col-md-6">Cliente</th>
    <th class="col-md-2">Día y Horario</th>
    <th class="col-md-1">Borrar</th>
    <th class="col-md-1">Delegar</th>
  </tr>
  

<g:each in="${resul}" var="res" status="i">


  <tr>
    <td class="tg-yw4l">${res[1]} 
    
    
    
    <g:link title="Editar cliente" action="selectnewclient" params="[agendaid: "${res[4]}" , clientid: "${res[5]}" ,  hour: "${res[3]}" , nameclient: "${res[1]}" , day: "${res[2]}" , nameseller: "${sell.name}" ]">
    <asset:image src="edit.png" alt="Editar" style="width:20px;height:20px;"/></g:link>
    
    
    </td>
    <td class="tg-yw4l">
    ${res[3]} - 
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
</g:if>
<g:link title="Editar día y horario" action="editagenda" params="[agendaid: "${res[4]}" , clientid: "${res[5]}" ,  hour: "${res[3]}" , nameclient: "${res[1]}" , day: "${res[2]}" , nameseller: "${sell.name}" ]">
<asset:image src="edit.png" alt="Editar" style="width:20px;height:20px;"/> </g:link>
</td>

   <td class="tg-yw4l">
   <g:link title="Borrar entrada" action="deleteconfirm" id="${res[4]}" params="[hour: "${res[3]}" , nameclient: "${res[1]}" , day: "${res[2]}" , nameseller: "${sell.name}" ]" ><asset:image src="delete.png" alt="Borrar" style="width:20px;height:20px;"/></g:link>
   </td>
   
   
   <td class="tg-yw4l">
   <g:link title="Asignar esta visita a otro vendedor" action="changeseller" params="[agendaid: "${res[4]}" , day: "${res[2]}" , hour: "${res[3]}", nameclient: "${res[1]}" , nameseller: "${sell.name}" ]" >
   
   <asset:image src="delegat.png" alt="Delegar" style="width:25px;height:25px;"/></g:link></td>
   
  </tr>


  </g:each>
  </table>
  
</div>

</g:if>

<g:elseif test="${dayr != '-1'}">
<br>
<p><b>No hay clientes agendados para mostrar</b></p>
</g:elseif>

</body>
</html>
