<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Agregar descuento para un producto</title>

</style>
</head>
<body>
<h1>Agregar descuento para un producto</h1>
<p> Para empezar, seleccione el producto al que desea agregarle un descuento </p>



<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Nombre</th>
    <th class="tg-yw4l">Marca</th>
    <th class="tg-yw4l">Categoría</th>
    <th class="tg-yw4l"><div align="center">Seleccionar</div></th>
  </tr>
  
    <g:each in="${products}" var="product" status="i">

  <tr>
    <td class="tg-yw4l">${product.name}</td>
    <td class="tg-yw4l">${brands[i].name}</td>
    <td class="tg-yw4l">${product.category}</td>
    <td class="tg-yw4l"><div align="center"><g:link action="selectproduct" id="${product.id}" params="[productid:"${product.id}", nameproduct: "${product.name}" , brandname: "${brands[i].name}", productcategory:"${product.category}", range:'0' , productcost:"${product.price}" ]" ><asset:image src="ok.png" title="Seleccionar" alt="Seleccionar" style="width:20px;height:20px;"/> </g:link> </div> </td>
  </tr>
</g:each>


  </table>
</div>

</body>
</html>
