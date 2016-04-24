<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Alta de Clientes</title>
</head>
<body>
            <h1>Ingrese los datos del nuevo cliente</h1>

            <g:form controller="client" action="save">
              <div class="table-responsive">          
  <table class="table table-striped">
  
    <tr>
    <td><label>Nombre: </label></td>
  </tr>
  <tr>
    <td><g:textField type="text" required="" name="name"/></td>
  </tr>
  <tr>
    <td><label>Ciudad</label></td>
  </tr>
  <tr>
    <td><g:textField type="text" required="" name="city"/></td>
  </tr>
  <tr>
    <td><label>Direccion: </label></td>
  </tr>
    <tr>
    <td><g:textField type="text" required="" name="address"/></td>
  </tr>
    <tr>
    <td><label>E-mail: </label></td>
  </tr>
      <tr>
    <td><g:textField type="email" required="" name="mail"/></td>
  </tr>


    </tbody>
           

  </table>
  </div>
   <div align="center"><g:actionSubmit value="Save"/></div>
    </g:form>
        
	</body>

</body>
</html>
