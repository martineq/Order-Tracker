<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Agregar descuento al producto ${params.nameproduct}</title>
<style>
    .dropdown-menu-2 {
  left: 50%;
  right: auto;
  text-align: center;
  transform: translate(-50%, 0);
}
input[type=text] {
    display: inline;
    width: 50px;
}
</style>
</head>
<body>
<h1> Introduzca un nuevo descuento </h1>
<p> 
Agregar descuento al producto ${params.nameproduct}, de $ ${params.productcost}
</p>

  
<div  style="form">     
<div  align="center"> <label>¿Cuántos descuentos desea introducir?</label>
  <div class="dropdown">
    <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Rangos...
    <span class="caret"></span></button>
    <ul class="dropdown-menu-2 dropdown-menu center"  align="center">
      <li><g:link action="selectproduct" params="[range: 1 , productid:"${params.productid}", nameproduct: "${params.nameproduct}" , productcost:  "${params.productcost}" ]" >1</g:link></li>
     <li><g:link action="selectproduct" params="[range: 2 ,productid:"${params.productid}", nameproduct: "${params.nameproduct}" , productcost:  "${params.productcost}"]" >2</g:link></li>
      <li><g:link action="selectproduct" params="[range: 3 , productid:"${params.productid}", nameproduct: "${params.nameproduct}" , productcost:  "${params.productcost}"]" >3</g:link></li>
    </ul>
  </div>
  </div>
  <br>
   <g:if test="${params.range != "0"}">
<g:form controller="discount" action="upentryproduct" params="[productid:"${params.productid}", nameproduct: "${params.nameproduct}" , range: "${params.range}" , productcost: "${params.productcost}" ]" >

<g:if test="${params.range == "1"}">

Para cualquier número de productos, el descuento será de <g:textField pattern="^[0-9]+\\s*\$|^[0-9]+\\.?[0-9]+\\s*\$"  required="" name="desc1"/>%
<br>
</g:if>
<g:else>
<br>
Desde 1 hasta <g:textField pattern="^[0-9]+\\s*\$|^[0-9]+\\.?[0-9]+\\s*\$"  required="" name="ran2"/>, descuento del <g:textField type="number" required="" name="desc2"/> %
<br>


<g:if test="${params.range == "3"}">
<br>
Desde ese valor hasta <g:textField pattern="^[0-9]+\\s*\$|^[0-9]+\\.?[0-9]+\\s*\$"  required="" name="ran3"/>, descuento del <g:textField pattern="^[0-9]+\\s*\$|^[0-9]+\\.?[0-9]+\\s*\$"  required="" name="desc3"/> %
<br>
</g:if>

<br>
A partir de ese valor, descuento del <g:textField pattern="^[0-9]+\\s*\$|^[0-9]+\\.?[0-9]+\\s*\$"  required="" name="descfinal"/> %
<br>
</g:else>
<br>
<b>Fecha de inicio: </b>

Dia: <g:select  name="dayinit" from="${01..31}"/>,
Mes:<g:select  name="monthinit"  from="${['Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre']}"/>
              <br><br>
<b>Fecha de fin: </b> Dia: <g:select  name="dayend" from="${01..31}"/>,
Mes:<g:select  name="monthend"  from="${['Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre']}"/>
              <br><br>
<br>
<br>

<br><br>
<g:actionSubmit class="buttona"  value="Guardar" action="upentryproduct" params="[productid:"${params.productid}" ,  nameproduct: "${params.nameproduct}" , range: "${params.range}" , productcost:  "${params.productcost}" ]" />

    </g:form>
    
    </g:if>
        
  </div>
</body>
</html>
