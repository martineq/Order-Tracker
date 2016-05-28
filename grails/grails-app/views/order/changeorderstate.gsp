<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Cambio de estado de pedido</title>
</head>
<body>
<h1>Cambio de estado de pedido</h1>
<p> Seleccione el nuevo estado para el pedido del ${params.day}, del cliente ${params.clientname} atendido por el vendedor ${params.sellername} </p>


<g:form controller="order" action="statechanged" params="[id:"${params.id}",day:"${params.day}"]" >


<div  style="form">          

<label>Día: </label>
<br>
<g:select  name="orderstate" value="${params.orderstate}" from="${["${state1}", "${state2}", "${state3}"]}"/>
<br>

  </div>
<br><br>

<g:actionSubmit class="buttona"  value="Guardar" action="statechanged" params="[id:"${params.id}",day:"${params.day}"]"/>
    </g:form>
        
</body>
</html>
 
