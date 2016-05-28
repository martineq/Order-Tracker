<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Cambio registrado</title>
</head>
<body>
            <h1>Cambio registrado</h1>

<div align="center"><p>Se registraron los cambios en el siguiente pedido</p></div>
</br>
<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Fecha</th>
    <th class="tg-yw4l">Vendedor</th>
    <th class="tg-yw4l">Cliente</th>
    <th class="tg-yw4l">Estado</th>
    <th class="tg-yw4l">Total</th>
  </tr>

  <tr>
    <td class="tg-yw4l">${params.day}</td>
    <td class="tg-yw4l">
    ${order.sellername}
    </td>
    <td class="tg-yw4l">
    
     ${order.clientname}
    </td>
    <td class="tg-yw4l">${order.state} 
    
    </td>
    <td class="tg-yw4l">$ ${order.total_price}</td>

  </tr>

  </table>
  <br>
<div align="center">
<a href="/order/index"><button type="button" class="btn btn-default">Volver</button></a>
</div>

  </table>
</div>

</body>
</html>
