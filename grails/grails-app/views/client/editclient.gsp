<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Edición del cliente: ${params.name}</title>
</head>
<body>

<h1>Edición del cliente: ${params.name}</h1>

<div>Modifique los campos que desee</div>


<g:form  id="${client.id}" controller="client" action="updateclient">
    <div class="table-responsive">          
  <table class="table table-striped">
  
    <tr>
    <td><label>Nombre: </label></td>
  </tr>
  <tr>
    <td><g:textField value="${client.name}" size="100%" type="text" required="" name="name"/></td>
  </tr>
  <tr>
    <td><label>Ciudad: </label></td>
  </tr>
  <tr>
    <td><g:textField type="text" value="${client.city}" size="100%" required="" name="city"/></td>
  </tr>
  <tr>
    <td><label>Direccion: </label></td>
  </tr>
    <tr>
    <td><g:textField type="text"  value="${client.address}" size="100%" required="" name="address"/></td>
  </tr>
    <tr>
    <td><label>E-mail: </label></td>
  </tr>
      <tr>
    <td><g:textField type="email" value="${client.email}" size="100%" required="" name="mail"/></td>
  </tr>


    </tbody>
           
  </table>
  </div>
  
   <div align="center"><g:actionSubmit value="Actualizar" id="${client.id}" action="updateclient"/></div>
    </g:form>
    

</body>
</html>
