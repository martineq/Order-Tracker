<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Vendedores</title>
</head>

<body>
            <h1>Vendedores</h1>
<p>Listado de todos los vendedores, ordenados por número de documento.</p>

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
<g:form controller="Seller" action="index" style="margin: 0px;border:0px;width:90%">

<div id="divA">
<label>DNI: </label> <g:textField  type="text" pattern="^[0-9]+\\s*\$|^[0-9]+\\.?[0-9]+\\s*\$"  name="dni"/>
 <br>
</div>
<div id="divB">
    
<label>Nombre: </label>
<br>
<g:textField  type="text" name="name"/>

</div>

<g:actionSubmit class="buttond" value="Buscar" action="index"/>
<br>
</div>   
</g:form>
    <br>
<div id="mainContainer">
    <div id="divA">
    <a><asset:image src="search.png"  style="width:38px;height:38px;" value="Buscar" onclick="showDiv()"/></a>
    
    </div>
    <div id="divB">
    
     <div align="right"><g:link action="up"><b>Agregar</b><asset:image src="add.png" alt="Editar" style="width:38px;height:38px;"/></g:link></div>
     
     </div>
</div>

<g:if test="${res>0}" >
<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Documento</th>
    <th class="tg-yw4l">Nombre</th>
     <th class="tg-yw4l">Teléfono</th>
    <th class="tg-yw4l">Zona</th>
    <th class="tg-yw4l"><div align="center">Editar</div></th>
    <th class="tg-yw4l"><div align="center">Borrar</div></th>
  </tr>
  
    <g:each in="${sellers}" var="seller" status="i">

  <tr>
    <td class="tg-yw4l"> ${seller.document_number}</td>
    <td class="tg-yw4l">${seller.name}</td>
    <td class="tg-yw4l">${seller.phone}</td>
    <td class="tg-yw4l">${seller.zone}</td>
        <td class="tg-yw4l">
    <div align="center">
    <g:link action="editseller" id="${seller.id}" params="[name: ("${seller.name}") ]" ><asset:image src="edit.png" title="Editar" alt="Editar" style="width:20px;height:20px;"/> </g:link>
    </div></td>
    <td class="tg-yw4l"><div align="center"><g:link action="deleteconfirm" id="${seller.id}" params="[name: ("${seller.name}") ]" ><asset:image src="delete.png" title="Borrar" alt="Borrar" style="width:20px;height:20px;"/></g:link></div></td>
  </tr>
</g:each>


  </table>
</div>
</g:if>

<g:elseif test="${res==-1}">
Debe ingresar algún parámetro de búsqueda.
</g:elseif>


<g:else >
No se encontraron resultados para mostrar.
</g:else>

</body>
</html>
