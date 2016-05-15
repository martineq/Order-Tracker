<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Detalle de pedido</title>
</head>
<body>
            <h1>Detalle de un pedido</h1>
Detalles del pedido del ${params.date}. 
<g:if test="${seller != null}"> Vendedor: ${seller.name}. </g:if>
  <g:if test="${client !=null}"> Cliente: ${client.name} </g:if>
  
  
  
<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Producto</th>
    <th class="tg-yw4l">Marca</th>
    <th class="tg-yw4l">Categoría</th>
    <th class="tg-yw4l">Precio por unidad</th>
    <th class="tg-yw4l">Cantidad</th>
  </tr>
  
    <g:each in="${orders}" var="order" status="i">

  <tr>
    
    <td class="tg-yw4l">${order.product_id}</td>
    
    <td class="tg-yw4l"> "MARCA" </td>
    
    <td class="tg-yw4l"> CATEGORIA </td>

    <td class="tg-yw4l"> $ </td>
    <td class="tg-yw4l"> ${order.requested_items} </td>
  </tr>
</g:each>


  </table>
  
</body>
</html>
