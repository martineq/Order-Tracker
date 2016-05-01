<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Clientes</title>
</head>
<body>
            <h1>Listado de Clientes</h1>


            
<div align="right"><g:link action="up"><b>Agregar</b><asset:image src="add.png" alt="Editar" style="width:38px;height:38px;"/></g:link></div>
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
    <g:link action="clientdetails" id="${client.id}" params="[name:"${client.name}", mail:"${client.email}", city: "${client.city}", address: "${client.address}"]" ><asset:image src="view.png" title="Editar" alt="Editar" style="width:20px;height:20px;"/> </g:link>
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
