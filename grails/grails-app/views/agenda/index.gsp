<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Agenda de vendedores</title>
</head>
<body>
            <h1>Agenda de vendedores</h1>
<p> Seleccione un vendedor para ver su agenda semanal. Están ordenados por número de documento.</p>

            

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
<g:form  controller="Agenda" action="index" style="margin: 0px;border:0px;width:90%">

        
<div id="mainContainer">
    <div id="divA">
<label>DNI vendedor: </label> <g:textField  type="text" pattern="^[0-9]+\\s*\$|^[0-9]+\\.?[0-9]+\\s*\$"  name="dni"/>
  </div>
    <div id="divB">
<label>Nombre vendedor: </label>
<br>
<g:textField  type="text" name="name"/>
     </div>
</div>

<g:actionSubmit class="buttond" value="Buscar" action="index"/><br>
    </g:form>


        <br>
    <g:form controller="Agenda" action="searchnameclient" style="margin: 0;border:0px;width:90%">
 
<label>Nombre cliente: </label>
<br>
<g:textField  type="text" required="" name="clientname"/>
<br>
  <g:actionSubmit class="buttond" value="Buscar" action="searchnameclient"/><br>
    </g:form>
</div>


</div>

<div id="mainContainer">
    <div id="divA">
    <a><asset:image src="search.png"  style="width:38px;height:38px;" value="Buscar" onclick="showDiv()"/></a>
    
    </div>
</div>

<g:if test="${res>0}" >
<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Documento</th>
    <th class="tg-yw4l">Nombre</th>
    <th class="tg-yw4l"><div align="center">Ver agenda</div></th>
  </tr>
  
    <g:each in="${sellers}" var="seller" status="i">

  <tr>
    <td class="tg-yw4l"> ${seller.document_number}</td>
    <td class="tg-yw4l">${seller.name}</td>
    <td class="tg-yw4l"><div align="center"><g:link action="show" id="${seller.id}" params="[day: -1 ]"> <asset:image  src="view.png" alt="Editar" style="width:20px;height:20px;"/> </g:link></div></td>
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
