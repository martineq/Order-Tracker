<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Edición del cliente: ${params.name}</title>
</head>
<body>

<h1>Edición del cliente: ${params.name}</h1>

<g:form  id="${client.id}" controller="client" action="updateclient">

        <div  style="form">  
        <br>
<label>Nombre: </label>
<br>
<g:textField value="${client.name}" size="100%" type="text" required="" name="name"/>
<br>
<label>Ciudad: </label>
<br>
<g:textField type="text" value="${client.city}" size="100%" required="" name="city"/>
<br>
<label>Direccion: </label>
<br>
<g:textField type="text"  value="${client.address}" size="100%" required="" name="address"/>
<br>
<label>E-mail: </label>
<br>

<g:textField type="email" value="${client.email}" size="100%" required="" name="mail"/>
  <br>
  </div>
  <br>

  <g:actionSubmit class="buttona" value="Actualizar" id="${client.id}" action="updateclient"/>
    </g:form>
    

</body>
</html>
