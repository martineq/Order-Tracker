<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Productos</title>
</head>
<body>
            <h1>Listado de Productos</h1>


<div align="left">

<script>
function showDiv() {

var div = document.getElementById('searchDiv');
    if (div.style.display !== 'none') {
        div.style.display = 'none';
    }
    else {
        div.style.display = 'block';
    }
    
}
</script>

<div id="searchDiv"  style="display:none;" class="answer_list" > 


</div>


<div id="mainContainer">
    <div id="divA">
    <a><asset:image src="search.png"  style="width:38px;height:38px;" value="Buscar" onclick="showDiv()"/></a>
    
    </div>
    <div id="divB">

     <div align="right"><g:link action="up"><b>Agregar</b><asset:image src="add.png" alt="Agregar" style="width:38px;height:38px;"/></g:link></div>
     
     </div>
</div>            


<g:if test="${res>0}" >

<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Nombre</th>
    <th class="tg-yw4l">Marca</th>
    <th class="tg-yw4l">Categoría</th>
    <th class="tg-yw4l">Características</th>
    <th class="tg-yw4l">Precio</th>
    <th class="tg-yw4l">Stock</th>
    <th class="tg-yw4l"><div align="center">Imagen</div></th>
    <th class="tg-yw4l"><div align="center">Editar</div></th>
    <th class="tg-yw4l"><div align="center">Borrar</div></th>
  </tr>
  
    <g:each in="${products}" var="product" status="i">

  <tr>
    <td class="tg-yw4l">${product.name}</td>
    
    <td class="tg-yw4l">${brands[i].name}</td>
    
     <td class="tg-yw4l">${product.category}</td>
     
      <td class="tg-yw4l">${product.characteristic}</td>
      
      <td class="tg-yw4l">$ ${product.price}</td>
      
      <td class="tg-yw4l">${product.stock}</td>
      
    <td class="tg-yw4l">
    <div align="center">
    



    
<script>
function ${product.name[0]+product.id}(){
window.open("${g.createLink(controller: 'product', action: 'viewpic', params: [id:"${product.id}" ,img:"${product.image}"])}",'', 'width=500,height=500');
}
</script>

<a href="javascript:void()" onclick="javascript:${product.name[0]+product.id}()"> <asset:image src="view.png" title="Editar" alt="Editar" style="width:20px;height:20px;"/> </a>



    </div>
    </td>
    <td class="tg-yw4l">
    <div align="center">
    <g:link action="editproduct" params="[id:"${product.id}" ,name:"${product.name}"]" ><asset:image src="edit.png" title="Editar" alt="Editar" style="width:20px;height:20px;" /> </g:link>
    </div></td>
    <td class="tg-yw4l"><div align="center"><g:link action="deleteconfirm"  params="[id:"${product.id}" ,name:"${product.name}"]" ><asset:image src="delete.png" title="Borrar" alt="Borrar" style="width:20px;height:20px;"/></g:link></div></td>
  </tr>
</g:each>


  </table>
  
  </g:if>

<g:else >
No se encontraron resultados para mostrar.
</g:else>


</div>

</body>
</html>
