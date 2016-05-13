<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Alta de Vendedores</title>
<style>


</style>
</head>

<body>
            <h1>Ingrese los datos del nuevo vendedor</h1>

            <g:form controller="seller" action="save">
<div  style="form">          
<br>
<label>DNI: </label>
<br>
<g:textField type="number" required="" name="dni"/>
<br>
<label>Nombre: </label>
<br>
<g:textField type="text" required="" name="name"/>
<br>
<label>Teléfono: </label>
<br>
<g:textField type="number" required="" name="phone"/>
<br>
<label>Zona: </label>
<br>
<g:textField type="text" required="" name="zone"/>
<br>
<label>Usuario: </label>
<br>
<g:textField type="text" required="" name="user"/>
<br>
<label>Contraseña: </label>
<br>
<g:textField type="text" required="" name="password"/>
<br>
<br>
  </div>
  <br>
  <br>
   <g:actionSubmit class="buttona"  value="Guardar" action="save"/>
    </g:form>
        
	</body>

</body>
</html>
