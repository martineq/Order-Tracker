<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Descuentos</title>
</head>
<body>
            <h1>Listado de Descuentos</h1>


            
<div align="right"><g:link action="upproduct"><b>Agregar descuento por producto</b><asset:image src="add.png" alt="Agregar" style="width:38px;height:38px;"/></g:link></div>
<div align="right"><g:link action="upbrand"><b>Agregar descuento por marca y/o categoría</b><asset:image src="add.png" alt="Agregar" style="width:38px;height:38px;"/></g:link></div>
<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Producto</th>
    <th class="tg-yw4l">Marca</th>
    <th class="tg-yw4l">Descuento</th>
    <th class="tg-yw4l">Inicio</th>
    <th class="tg-yw4l">Fin</th>
    <th class="tg-yw4l"><div align="center">Editar</div></th>
    <th class="tg-yw4l"><div align="center">Borrar</div></th>
  </tr>
  
    <g:each in="${discounts}" var="discount" status="i">

  <tr>
    <td class="tg-yw4l">
    
    <g:if test="${discount.product_id!= -1}" >
        ${products[i].name}
    </g:if>
    <g:else>
    Todos los de la marca  ${brands[i].name}
    <g:if test="${discount.category!= 'none'}" >
       dentro de la categoría ${discount.category}
    </g:if>
    </g:else>

</td>
    <td class="tg-yw4l">${brands[i].name}</td>
    <td class="tg-yw4l">${descriptions[i]}</td>
    
    <td class="tg-yw4l">${initDiscount[i]} </td>
    <td class="tg-yw4l">${endDiscount[i]}  </td>

    <td class="tg-yw4l">
    <div align="center">
    <g:link action="editdiscount" id="${discount.id}" params="[name: ("${discount.category}") ]" ><asset:image src="edit.png" title="Editar" alt="Editar" style="width:20px;height:20px;"/> </g:link>
    </div></td>
    <td class="tg-yw4l"><div align="center"><g:link action="deleteconfirm" id="${discount.id}" params="[name: ("${discount.category}") ]" ><asset:image src="delete.png" title="Borrar" alt="Borrar" style="width:20px;height:20px;"/></g:link></div></td>
  </tr>
</g:each>


  </table>
</div>

</body>
</html>
