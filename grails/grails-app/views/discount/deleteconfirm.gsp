<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Confirmación: Borrar un descuento</title>
</head>
<body>

<h1>¿Está seguro de que desea continuar?</h1>

<div align="center">¿Está seguro de querer borrar el descuento  ? <br>


<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Producto</th>
    <th class="tg-yw4l">Marca</th>
    <th class="tg-yw4l">Descuento</th>
    <th class="tg-yw4l">Inicio</th>
    <th class="tg-yw4l">Fin</th>
  </tr>
  

  <tr>
    <td class="tg-yw4l">
    
    <g:if test="${discount.product_id!= -1}" >
        ${product.name}
    </g:if>
    <g:else>
    <g:if test="${brand!= null}" >
    Todos los de la marca  ${brand.name}
    </g:if>
    <g:else>
    Todos
    </g:else>
    <g:if test="${discount.category!= 'none'}" >
       dentro de la categoría ${discount.category}
    </g:if>
    </g:else>

</td>
    <td class="tg-yw4l"><g:if test="${brand != null}" >
    ${brand.name}
    </g:if>
    <g:else>-
    </g:else></td>
    <td class="tg-yw4l">${description}</td>
    
    <td class="tg-yw4l">${initDisc} </td>
    <td class="tg-yw4l">${endDisc}  </td>


  </tr>


<div align="center">
<g:link action="delete"  params="[id: ("${discount.id}") ]" ><button type="button" class="btn btn-default">Borrar</button></g:link>
<g:link action="index" ><button type="button" class="btn btn-default">Volver</button></g:link>
 </div>
 
  </table>
</div>

</body>
</html>
