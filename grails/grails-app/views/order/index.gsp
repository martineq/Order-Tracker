<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Pedidos</title>
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

    <g:form controller="Client" action="searchnameorder" style="margin: 0;border:0px">
 
<label>Nombre: </label>
<br>
<g:textField  type="text" required="" name="name"/>

  <g:actionSubmit class="buttonb" value="Buscar" action="searchnameorder"/>
    </g:form>
</div>

<div id="mainContainer">
    <div id="divA">
    <a><asset:image src="search.png"  style="width:38px;height:38px;" value="Buscar" onclick="showDiv()"/></a>
    
    </div>
</div>            

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
    <g:link action="orderdetails" id="${order.id}" params="[sellerid:"${order.seller_id}", clientid:"${order.client_id}", id:"${order.id}", date:"${days[i]}" ]"><asset:image src="view.png" title="Editar" alt="Editar" style="width:20px;height:20px;"/> </g:link>
    </div>
    </td>
  </tr>
</g:each>


  </table>
</div>

</body>
</html>
