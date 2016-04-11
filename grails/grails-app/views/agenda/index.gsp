<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Agenda de vendedores</title>
</head>
<body>
            <h1>Agenda de vendedores</h1>
<p> Seleccione un vendedor para ver su agenda semanal. Están ordenados por número de documento.</p>

            

<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Documento</th>
    <th class="tg-yw4l">Nombre</th>
    <th class="tg-yw4l">Ver agenda</th>
  </tr>
  
    <g:each in="${sellers}" var="seller" status="i">

  <tr>
    <td class="tg-yw4l"> ${seller.document_number}</td>
    <td class="tg-yw4l">${seller.name}</td>
    <td class="tg-yw4l"><g:link action="show" id="${seller.id}" params="[day: -1 ]"><button type="button" class="btn btn-default">Ver</button></g:link></td>
  </tr>
</g:each>


  </table>
</div>

</body>
</html>
