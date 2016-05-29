<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Agregar entrada a agenda</title>
</head>
<body>
<h1>Agregar entrada a la agenda</h1>
<p> Introduzca los datos de la nueva entrada de la agenda del vendedor ${params.nameseller} </p>
<p> Para empezar, seleccione el cliente al que debe visitar. </p>



<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Nombre</th>
    <th class="tg-yw4l">Dirección</th>
    <th class="tg-yw4l">Ciudad</th>
    <th class="tg-yw4l">E-mail</th>
    <th class="tg-yw4l"><div align="center">Seleccionar</div></th>
  </tr>
  
    <g:each in="${clients}" var="client" status="i">

  <tr>
    <td class="tg-yw4l">${client.name}</td>
    <td class="tg-yw4l">${client.address}</td>
    <td class="tg-yw4l">${client.city}</td>
    <td class="tg-yw4l">${client.email}</td>
    <td class="tg-yw4l"><div align="center"><g:link action="selectdate" id="${client.id}" params="[clientid:"${client.id}", nameclient: "${client.name}" , seller: "${params.nameseller}", sellerid:"${params.sellerid}" ]" ><asset:image src="ok.png" title="Seleccionar" alt="Seleccionar" style="width:20px;height:20px;"/> </g:link> </div> </td>
  </tr>
</g:each>


  </table>
</div>

</body>
</html>
