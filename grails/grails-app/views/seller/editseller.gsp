<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Edición del vendedor: ${params.name}</title>
</head>
<body>

<h1>Edición del vendedor: ${params.name}</h1>

<g:form  id="${seller.id}" controller="Seller" action="updateseller">

        <div  style="form">  
        <br>
<label>DNI: </label>
<br>
<g:textField  type="text" pattern="^[0-9]+\\s*\$|^[0-9]+\\.?[0-9]+\\s*\$" value="${seller.document_number}" required="" name="dni"/>
<br>
<label>Nombre: </label>
<br>
<g:textField type="text" value="${seller.name}" required="" name="name"/>
<br>
<label>Teléfono: </label>
<br>
<g:textField type="number" pattern="^[0-9]+\\s*\$|^[0-9]+\\.?[0-9]+\\s*\$"  value="${seller.phone}" required="" name="phone"/>
<br>
<label>Zona: </label>
<br>
<g:textField type="text" required="" value="${seller.zone}" name="zone"/>
<br>
<label>Usuario: </label>
<br>
<g:textField type="text" required="" value="${user}" name="user"/>
<br>
<label>Contraseña: </label>
<br>
<g:passwordField name="pass" value="${pass}" />
<br>
<label>Repetir Contraseña: </label>
<br>
<g:passwordField name="passok" value="${pass}" />
<br>
  </div>
  <br>

  <g:actionSubmit class="buttona" value="Actualizar" id="${seller.id}" action="updateseller"/>
    </g:form>
    

</body>
</html>
