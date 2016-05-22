<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Agenda por cliente</title>

</head>

<body>

<h1>Búsqueda de visitas agendadas para un cliente</h1>

  <div align="left">

<script>
function showDiv() {
   document.getElementById('searchDiv').style.display = "block";
}
</script>


<div id="searchDiv"  style="display:none;" class="answer_list" > 

<h2><b>Buscador</b></h2>
<g:form  controller="Agenda" action="index" style="margin: 0px;border:0px">

        
<label>DNI vendedor: </label> <g:textField  type="text" pattern="^[0-9]+\\s*\$|^[0-9]+\\.?[0-9]+\\s*\$"   name="dni"/>
<br>
<label>Nombre vendedor: </label>
<br>
<g:textField  type="text" name="name"/>

<g:actionSubmit class="buttonb" value="Buscar" action="index"/>
    </g:form>


        <br>
    <g:form controller="Agenda" action="searchnameclient" style="margin: 0;border:0px">
 
<label>Nombre cliente: </label>
<br>
<g:textField  type="text" required="" name="clientname"/>

  <g:actionSubmit class="buttonb" value="Buscar" action="searchnameclient"/>
    </g:form>
</div>


</div>

<div id="mainContainer">
    <div id="divA">
    <a><asset:image src="search.png"  style="width:38px;height:38px;" value="Buscar" onclick="showDiv()"/></a>
    
    </div>
</div>
 
<g:if test="${res != 0}">


<g:each in="${clientList}" var="cli" status="j">

<h2><b>Visitas agendadas para ${cli.name}</b></h2> <br>

    
<div class="table-responsive">
  <table class="table" style="border-top: 0px solid #fff; margin:0px">
     <tr>
    <th class="col-md-4">Vendedor</th>
    <th class="col-md-4">Dirección</th>
    <th class="col-md-3">Día y Horario</th>
    <th class="col-md-3">Estado</th>
    <th class="col-md-1">Borrar</th>
    <th class="col-md-1">Delegar</th>
  </tr>
  

<g:each in="${agendaList[j]}" var="res" status="i">


  <tr style="border-top: 0px solid #fff;  background: #fff ">
    <td class="tg-yw4l">${res[9]} 
    </td>
    
     <td class="tg-yw4l">
     ${res[6]}
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
<g:link title="Editar día y horario" action="editagenda" params="[agendaid: "${res[4]}" , clientid: "${res[5]}" ,  hour: "${res[3]}" , nameclient: "${res[1]}" , day: "${res[2]}" , nameseller: "${res[9]}" ]">
<asset:image src="edit.png" alt="Editar" style="width:20px;height:20px;"/> </g:link>
</td>

   <td class="tg-yw4l">${res[8]}</td>

   <td class="tg-yw4l">
   <g:link title="Borrar entrada" action="deleteconfirm" id="${res[4]}" params="[hour: "${res[3]}" , nameclient: "${res[1]}" , day: "${res[2]}" , nameseller: "${res[9]}" ]" ><asset:image src="delete.png" alt="Borrar" style="width:20px;height:20px;"/></g:link>
   </td>
   
   
   <td class="tg-yw4l">
   <g:link title="Asignar esta visita a otro vendedor" action="changeseller" params="[agendaid: "${res[4]}" , day: "${res[2]}" , hour: "${res[3]}", nameclient: "${res[1]}" , nameseller: "${res[9]}" ]" >
   
   <asset:image src="delegat.png" alt="Delegar" style="width:25px;height:25px;"/></g:link></td>
   
  </tr>


  </g:each>
  </table>
  


</div>

</g:each>
</g:if>

<g:else>
<br>
<p><b>No hay clientes para mostrar</b></p>
</g:else>

    
</body>
</html>
