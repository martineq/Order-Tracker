<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Clientes</title>
</head>
<body>
            <h1>Listado de Clientes</h1>


<div align="left">

<script>
function showDiv() {

var div = document.getElementById('searchDiv');
    if (div.style.display !== 'none') {
        div.style.display = 'none';
    }
    else {
        div.style.display = 'block';
    }
    
}
</script>


<div id="searchDiv"  style="display:none;" class="answer_list" > 

<h2><b>Buscador</b></h2>

    <g:form controller="Client" action="searchnameclient" style="margin: 0;border:0px;width:90%">
 
<label>Nombre: </label>
<br>
<g:textField  type="text" required="" name="name"/>
    <br>
  <g:actionSubmit class="buttond" value="Buscar" action="searchnameclient"/>    <br>
    </g:form>
    <br>
</div>

<div id="mainContainer">
    <div id="divA">
    <a><asset:image src="search.png"  style="width:38px;height:38px;" value="Buscar" onclick="showDiv()"/></a>
    
    </div>
    <div id="divB">

     <div align="right"><g:link action="up"><b>Agregar</b><asset:image src="add.png" alt="Agregar" style="width:38px;height:38px;"/></g:link></div>
     
     </div>
</div>            

<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Nombre</th>
    <th class="tg-yw4l">Dirección</th>
    <th class="tg-yw4l">Ciudad</th>
    <th class="tg-yw4l"><div align="center">Detalles</div></th>
    <th class="tg-yw4l"><div align="center">Editar</div></th>
    <th class="tg-yw4l"><div align="center">Borrar</div></th>
  </tr>
  
    <g:each in="${clients}" var="client" status="i">

  <tr>
    <td class="tg-yw4l">${client.name}</td>
    <td class="tg-yw4l">${client.address}</td>
    <td class="tg-yw4l">${client.city}</td>
    <td class="tg-yw4l">
    <div align="center">
    <g:link action="clientdetails" id="${client.id}" params="[name:"${client.name}", mail:"${client.email}", city: "${client.city}", address: "${client.address}"]" ><asset:image src="view.png" title="Ver" alt="Ver" style="width:20px;height:20px;"/> </g:link>
    </div>
    </td>
    <td class="tg-yw4l">
    <div align="center">
    <g:link action="editclient" id="${client.id}" params="[name: ("${client.name}") ]" ><asset:image src="edit.png" title="Editar" alt="Editar" style="width:20px;height:20px;"/> </g:link>
    </div></td>
    <td class="tg-yw4l"><div align="center"><g:link action="deleteconfirm" id="${client.id}" params="[name: ("${client.name}") ]" ><asset:image src="delete.png" title="Borrar" alt="Borrar" style="width:20px;height:20px;"/></g:link></div></td>
  </tr>
</g:each>


  </table>
</div>

</body>
</html>
