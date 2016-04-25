<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Edición de agenda de vendedores</title>
</head>
<body>
            <h1>Edición de agenda de vendedores</h1>
<p> Seleccione un vendedor para editar su agenda semanal. Están ordenados por número de documento.</p>

            

<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Documento</th>
    <th class="tg-yw4l">Nombre</th>
    <th class="tg-yw4l">Editar agenda</th>
  </tr>
  
    <g:each in="${sellers}" var="seller" status="i">

  <tr>
    <td class="tg-yw4l"> ${seller.document_number}</td>
    <td class="tg-yw4l">${seller.name}</td>
    <td class="tg-yw4l"><g:link action="edit" id="${seller.id}" params="[name: ("${seller.name}") ]"  ><button type="button" class="btn btn-default">Editar</button></g:link></td>
  </tr>
</g:each>


  </table>
</div>

</body>
</html>
