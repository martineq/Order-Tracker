<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Edición de  completada</title>
</head>
<body>
            <h1>Entrada guardada en la agenda</h1>
            
            <div> Nuevos datos: </div>
<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Cliente</th>
    <th class="tg-yw4l">Vendedor</th>
    <th class="tg-yw4l">Horario</th>
    <th class="tg-yw4l">Día</th>
  </tr>
  
  <tr>
    <td class="tg-yw4l">${params.nameclient}</td>
    <td class="tg-yw4l">${params.nameseller}</td>
    <td class="tg-yw4l">${time} </td>
    <td class="tg-yw4l">${day}</td>
  </tr>

  </table>
</div>

</br>
<div align="center">
<a href="/agenda/index"><button type="button" class="btn btn-default">Volver</button></a>
</div>

</body>
</html>
