<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Vendedores</title>
</head>
<body>
            <h1>Listado de vendedores</h1>
<p>Listado de todos los vendedores, ordenados por número de documento.</p>

            

<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Documento</th>
    <th class="tg-yw4l">Nombre</th>
    <th class="tg-yw4l">zona</th>
  </tr>
  
    <g:each in="${sellers}" var="seller" status="i">

  <tr>
    <td class="tg-yw4l"> ${seller.document_number}</td>
    <td class="tg-yw4l">${seller.name}</td>
    <td class="tg-yw4l">${seller.zone}</td>
  </tr>
</g:each>


  </table>
</div>

</body>
</html>
