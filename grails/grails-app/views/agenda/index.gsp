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
   document.getElementById('searchDiv').style.display = "block";
}
</script>


<div id="searchDiv"  style="display:none;" class="answer_list" > 

<h2><b>Búscador</b></h2>
<g:form  controller="Agenda" action="searchdniseller" style="margin: 0px;border:0px">

        
<label>DNI vendedor: </label> <g:textField  type="text" pattern="^[0-9]+\\s*\$|^[0-9]+\\.?[0-9]+\\s*\$"  required="" name="dni"/>


<g:actionSubmit class="buttonb" value="Buscar" action="searchdniseller"/>
    </g:form>

    <br>
    <g:form controller="Agenda" action="searchnameseller" style="margin: 0;border:0px">
 
<label>Nombre vendedor: </label>
<br>
<g:textField  type="text" required="" name="name"/>

  <g:actionSubmit class="buttonb" value="Buscar" action="searchnameseller"/>
    </g:form>
    
        <br>
    <g:form controller="Agenda" action="searchnameclient" style="margin: 0;border:0px">
 
<label>Nombre cliente: </label>
<br>
<g:textField  type="text" required="" name="clientname"/>

  <g:actionSubmit class="buttonb" value="Buscar" action="searchnameclient"/>
    </g:form>
</div>


</div>

<div id="mainContainer">
    <div id="divA">
    <a><asset:image src="search.png"  style="width:38px;height:38px;" value="Buscar" onclick="showDiv()"/></a>
    
    </div>
</div>


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

</body>
</html>
