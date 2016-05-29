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
Si no desea especificar ninguna categoría y el descuento debe aplicarse a todos los productos de una marca, elija "ninguna".
<br><br>
            <g:form controller="discount" action="selectbrand">
<div  style="form">          
<br>
<label>Ingrese una categoría: </label>
<br>
<g:select name="category"
          from="${cats.name}" />
<br>
<br>
  </div>
   <g:actionSubmit class="buttonb"  value="Siguiente" action="selectbrand"/>
    </g:form>
        
	</body>

</body>
</html>
