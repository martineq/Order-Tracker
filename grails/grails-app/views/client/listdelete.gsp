<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Clientes</title>
</head>
<body>
            <h1>Listado de Clientes</h1>

Seleccione el cliente que desea borrar
            

<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Nombre</th>
    <th class="tg-yw4l">Dirección</th>
    <th class="tg-yw4l">Ciudad</th>
    <th class="tg-yw4l">Borrar</th>
  </tr>
  
    <g:each in="${clients}" var="client" status="i">

  <tr>
    <td class="tg-yw4l">${client.name}</td>
    <td class="tg-yw4l">${client.address}</td>
    <td class="tg-yw4l">${client.city}</td>
    <td class="tg-yw4l"><g:link action="deleteconfirm" id="${client.id}" params="[name: ("${client.name}") ]" ><button type="button" class="btn btn-default">Borrar</button></g:link></td>
  </tr>
</g:each>


  </table>
</div>

</body>
</html>
