<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Vendedores</title>
</head>
<body>
            <h1>Listado de Clientes</h1>


            

<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Nombre</th>
    <th class="tg-yw4l">Dirección</th>
    <th class="tg-yw4l">Ciudad</th>
    <th class="tg-yw4l">E-mail</th>
  </tr>
  
    <g:each in="${clients}" var="client" status="i">

  <tr>
    <td class="tg-yw4l">${client.name}</td>
    <td class="tg-yw4l">${client.address}</td>
    <td class="tg-yw4l">${client.city}</td>
    <td class="tg-yw4l">${client.email}</td>
  </tr>
</g:each>


  </table>
</div>

</body>
</html>
