<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Edición de agenda</title>
</head>
<body>
<h1>Edición de agenda</h1>
<p> Cambie los datos que desee modificar para la siguiente entrada: </p>


<g:form controller="agenda" action="savechanges">
              <div class="table-responsive">          
  <table class="table table-striped">
  
 <tr>
    <td><label>Fecha y horario: </label></td>
  </tr>
  <tr>
    <td>
    
    <g:datePicker name="date" value="${new Date()}" relativeYears="[0..2]" noSelection="['':'-Choose-']"/>
              
              </td>
  </tr>
  
  
  </table>
  </div>
   <div align="center"><g:actionSubmit value="Modificar" id="${params.agendaid}" action="savechanges"/></div>
    </g:form>
        
</body>
</html>
