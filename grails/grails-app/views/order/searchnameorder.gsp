<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Pedidos</title>
<style>

input[pattern="^[0-9]+\\s*\$|^[0-9]+\\.?[0-9]+\\s*\$"] {
    display: inline;
    width: 50px;
}
</style>
</head>
<body>
            <h1>Pedidos</h1>


<div align="left">

<script>
function showDiv() {
   document.getElementById('searchDiv').style.display = "block";
}
</script>



<div id="searchDiv"  style="display:none;" class="answer_list" > 

<h2><b>Búscador</b></h2>

<g:form controller="Order" action="searchnameorder" style="margin: 0;border:0px" >
 
<label>Nombre vendedor: </label>
<br>
<g:textField  type="text" required="" name="nameseller"/>

  <g:actionSubmit class="buttonb" value="Buscar" action="searchnameorder"/>
    </g:form>
    
<br>
<g:form controller="Order" action="searchnameclientorder" style="margin: 0;border:0px" >
 
<label>Nombre cliente: </label>
<br>
<g:textField  type="text" required="" name="nameclient"/>

  <g:actionSubmit class="buttonb" value="Buscar" action="searchnameclientorder"/>
    </g:form>
    
<br>
<g:form controller="Order" action="searchdateorder" style="margin: 0;border:0px">
 
<label>Rango de Fechas: </label>
<br>
 <g:select  name="dayinit" from="${01..31}"/> / <g:select  name="monthinit" from="${01..12}"/> / 
 <g:textField  type="text" required="" name="yearinit" pattern="^[0-9]+\\s*\$|^[0-9]+\\.?[0-9]+\\s*\$" />
-
 <g:select  name="dayend" from="${01..31}"/> / <g:select  name="monthend" from="${01..12}"/> / 
 <g:textField  type="text" required="" name="yearend"  pattern="^[0-9]+\\s*\$|^[0-9]+\\.?[0-9]+\\s*\$" />
 
<g:actionSubmit class="buttonb" value="Buscar" action="searchdateorder"/>
    </g:form>

    <br>
<g:form controller="Order" action="searchstateorder" style="margin: 0;border:0px">
 
<label>Estado de pedido: </label>
<br>
 <g:select  name="orderstate" from="${['Despachado', 'Solicitado', 'Cancelado']}"/>

<g:actionSubmit class="buttonb" value="Buscar" action="searchstateorder"/>
    </g:form>
    
</div>


<div id="mainContainer">
    <div id="divA">
    <a><asset:image src="search.png"  style="width:38px;height:38px;" value="Buscar" onclick="showDiv()"/></a>
    
    </div>
</div>            

<g:if test="${res!=0}" >
<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Fecha</th>
    <th class="tg-yw4l">Vendedor</th>
    <th class="tg-yw4l">Cliente</th>
    <th class="tg-yw4l">Estado</th>
    <th class="tg-yw4l">Total</th>
    <th class="tg-yw4l"><div align="center">Detalles</div></th>
  </tr>
  
    <g:each in="${orders}" var="order" status="i">

  <tr>
    <td class="tg-yw4l">${days[i]}</td>
    <td class="tg-yw4l">
    
    <g:if test="${sellers[i]!=null}"> ${sellers[i].name} </g:if>
    <g:else> - </g:else>
    </td>
    <td class="tg-yw4l">
    
    <g:if test="${clients[i]!=null}"> ${clients[i].name} </g:if>
    <g:else> - </g:else>
    
    </td>
    <td class="tg-yw4l">${order.state}</td>
    <td class="tg-yw4l">$ ${order.total_price}</td>
    <td class="tg-yw4l">
    <div align="center">
    <g:link action="orderdetails" params="[sellerid:"${order.seller_id}", id:"${order.id}", clientid:"${order.client_id}", id:"${order.id}", date:"${days[i]}" ]"><asset:image src="view.png" title="Editar" alt="Editar" style="width:20px;height:20px;"/> </g:link>
    </div>
    </td>
  </tr>
</g:each>


  </table>
  </g:if>

<g:else >
No se encontraron resultados para mostrar.
</g:else>
</div>

</body>
</html>
