<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Alta de Descuentos</title>
<style>


</style>
</head>

<body>
<h1>Ingrese una categoría</h1>
Si no desea especificar ninguna categoría y el descuento debe aplicarse a todos los productos de una marca, deje el campo en blanco.
<br><br>
            <g:form controller="discount" action="selectbrand">
<div  style="form">          
<br>
<label>Ingrese una categoría: </label>
<br>
<g:textField type="text" name="category"/>
<br>
<br>
  </div>
   <g:actionSubmit class="buttona"  value="Siguiente" action="selectbrand"/>
    </g:form>
        
	</body>

</body>
</html>
