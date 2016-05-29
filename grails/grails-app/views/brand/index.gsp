<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Marcas</title>
</head>
<body>
            <h1>Listado de Marcas</h1>


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

<h2><b>Buscador</b></h2>

    <g:form controller="Brand" action="index" style="margin: 0;border:0px;width:90%">
 
<label>Nombre: </label>
<br>
<g:textField  type="text" required="" name="name"/>
    <br>
  <g:actionSubmit class="buttond" value="Buscar" action="index"/>    <br>
    </g:form>
    <br>
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
    <th class="tg-yw4l"><div align="center">Imagen</div></th>
    <th class="tg-yw4l"><div align="center">Editar</div></th>
    <th class="tg-yw4l"><div align="center">Borrar</div></th>
  </tr>
  
    <g:each in="${brands}" var="brand" status="i">

  <tr>
    <td class="tg-yw4l">${brand.name}</td>
    <td class="tg-yw4l">
    <div align="center">
    



    
<script>
function ${brand.name[0]+brand.id}(){
window.open("${g.createLink(controller: 'brand', action: 'viewpic', params: [id:"${brand.id}" ,img:"${brand.image}"])}",'', 'width=500,height=500');
}
</script>

<a href="javascript:void()" onclick="javascript:${brand.name[0]+brand.id}()"> <asset:image src="view.png" title="Editar" alt="Editar" style="width:20px;height:20px;"/> </a>



    </div>
    </td>
    <td class="tg-yw4l">
    <div align="center">
    <g:link action="editbrand" params="[id:"${brand.id}" ,name:"${brand.name}"]" ><asset:image src="edit.png" title="Editar" alt="Editar" style="width:20px;height:20px;" /> </g:link>
    </div></td>
    <td class="tg-yw4l"><div align="center"><g:link action="deleteconfirm"  params="[id:"${brand.id}" ,name:"${brand.name}"]" ><asset:image src="delete.png" title="Borrar" alt="Borrar" style="width:20px;height:20px;"/></g:link></div></td>
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
