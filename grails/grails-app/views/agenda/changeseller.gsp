<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Cambiar vendedor</title>
</head>
<body>
            <h1>Cambio de vendedor</h1>
<p> Seleccione el vendedor al que desea asignarle la visita actualmente programada para ${params.nameseller}, al cliente ${params.nameclient} el día ${day} a las ${params.hour} hs.  </p>

            

<div class="table-responsive">
  <table class="table">
     <tr>
    <th class="tg-yw4l">Documento</th>
    <th class="tg-yw4l">Nombre</th>
    <th class="tg-yw4l"><div align="center">Seleccionar</div></th>
  </tr>
  
    <g:each in="${sellers}" var="seller" status="i">

  <tr>
    <td class="tg-yw4l"> ${seller.document_number}</td>
    <td class="tg-yw4l">${seller.name}</td>
    <td class="tg-yw4l"><div align="center">
    
    <g:link action="selectnewseller" params="[agendaid: "${params.agendaid}" , day: "${params.day}" , hour: "${params.hour}", nameclient: "${params.nameclient}" , newnameseller: "${seller.name}", newsellerid: "${seller.id}" ]"> 
    
    <asset:image  src="ok.png" alt="Seleccionar" style="width:20px;height:20px;"/> </g:link></div></td>
  </tr>
</g:each>


  </table>
</div>

</body>
</html>
