<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Alta de Clientes</title>
<style>


</style>
</head>

<body>
            <h1>Ingrese los datos del nuevo cliente</h1>

            <g:form controller="client" action="save">
<div  style="form">          
<br>
<label>Nombre: </label>
<br>
<g:textField type="text" required="" name="name"/>
<br>
<label>Ciudad: </label>
<br>
<g:textField type="text" required="" name="city"/>
<br>
<label>Direccion: </label>
<br>
<g:textField type="text" required="" name="address"/>
<br>

<label>E-mail: </label>

<br>
<g:textField type="email" required="" name="mail"/>

<br>
  </div>
  <br>
   <g:actionSubmit class="buttona"  value="Guardar" action="save"/>
    </g:form>
        
	</body>

</body>
</html>
