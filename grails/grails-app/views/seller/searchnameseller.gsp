<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Vendedores</title>
</head>

<body>
            <h1>Resultado de búsqueda de vendedores por nombre</h1>

<div align="left">

<script>
function showDiv() {
   document.getElementById('searchDiv').style.display = "block";
}
</script>


<div id="searchDiv"  style="display:none;" class="answer_list" > 

<h2><b>Búscador</b></h2>
<g:form  controller="Seller" action="searchdniseller" style="margin: 0px;border:0px">

        
<label>DNI: </label> <g:textField  type="text" pattern="^[0-9]+\\s*\$|^[0-9]+\\.?[0-9]+\\s*\$"  required="" name="dni"/>


<g:actionSubmit class="buttonb" value="Buscar" action="searchdniseller"/>
    </g:form>

    <br>
    <g:form controller="Seller" action="searchnameseller" style="margin: 0;border:0px">
 
<label>Nombre: </label>
<br>
<g:textField  type="text" required="" name="name"/>

  <g:actionSubmit class="buttonb" value="Buscar" action="searchnameseller"/>
    </g:form>
</div>

<div id="mainContainer">
    <div id="divA">
    <a><asset:image src="search.png"  style="width:38px;height:38px;" value="Buscar" onclick="showDiv()"/></a>
    
    </div>
    <div id="divB">
    
     <div align="right"><g:link action="up"><b>Agregar</b><asset:image src="add.png" alt="Editar" style="width:38px;height:38px;"/></g:link></div>
     
     </div>
</div>

<g:if test="${res!=0}" >

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

<g:else >
No se encontraron resultados para mostrar.
</g:else>

</body>
</html>
