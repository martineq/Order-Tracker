<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Cambio de Cliente</title>
</head>
<body>
            <h1>Cambio de Cliente</h1>


            
<div>Seleccione el nuevo cliente que quiere asignar al vendedor ${params.nameseller} el dia ${day} a las ${params.hour} hs </div>


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
    <td class="tg-yw4l"><div align="center"><g:link action="selectclient" id="${client.id}" params="[name: "${client.name}" , day:"${day}", hour: "${params.hour}" , vendedor: "${params.nameseller}", clientid:"${client.id}", agendaid:"${params.agendaid}" ]" ><asset:image src="ok.png" title="Seleccionar" alt="Seleccionar" style="width:20px;height:20px;"/> </g:link> </div> </td>
  </tr>
</g:each>


  </table>
</div>

</body>
</html>
