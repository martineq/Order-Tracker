<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Detalle de pedido</title>
</head>
<body>
            <h1>Detalle de un pedido</h1>
Detalles del pedido del ${params.date}. 
Vendedor: ${params.sellername}. 
 Cliente: ${params.clientname}
  
  
  
<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Producto</th>
    <th class="tg-yw4l">Marca</th>
    <th class="tg-yw4l">Categoría</th>
    <th class="tg-yw4l">Descripción</th>
    <th class="tg-yw4l">Precio por unidad</th>
    <th class="tg-yw4l">Cantidad</th>
  </tr>
  
    <g:each in="${orders}" var="order" status="i">

  <tr>
    
    <td class="tg-yw4l"> 
 ${order.productname} 
    </td>
    
    <td class="tg-yw4l"> ${order.brand}  </td>
    
    <td class="tg-yw4l"> 
    
${order.category}
</td>
    <td class="tg-yw4l"> 
     ${order.characteristic}
    
</td>
    <td class="tg-yw4l"> 
  $  ${order.price}
    
</td>
    <td class="tg-yw4l"> ${order.requested_items} </td>
  </tr>
</g:each>


  </table>
  
</body>
</html>
