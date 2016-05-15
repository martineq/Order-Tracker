<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Selección de marca</title>

</style>
</head>
<body>
<h1>Agregar descuento para una marca</h1>
<p> Seleccione la marca para la cual quiere agregar un descuento </p>

<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Nombre</th>
    <th class="tg-yw4l"><div align="center">Seleccionar</div></th>
  </tr>
  <g:if test="${category != ''}">
  <tr>
        <td class="tg-yw4l">TODAS</td>
    <td class="tg-yw4l"><div align="center"><g:link action="adddiscount"  params="[range:"0", brandid:"-1", namebrand: "none" , category:"${category}"]"><asset:image src="ok.png" title="Seleccionar" alt="Seleccionar" style="width:20px;height:20px;"/> </g:link> </div> </td>
  
      
</tr>
</g:if>
    <g:each in="${brands}" var="brand" status="i">
      <tr>

    <td class="tg-yw4l">${brand.name}</td>
    <td class="tg-yw4l"><div align="center"><g:link action="adddiscount"  params="[range:"0", brandid:"${brand.id}", namebrand: "${brand.name}" , category:"${category}"]"><asset:image src="ok.png" title="Seleccionar" alt="Seleccionar" style="width:20px;height:20px;"/> </g:link> </div> </td>
  
</tr>
</g:each>
  



  </table>
</div>

</body>
</html>
