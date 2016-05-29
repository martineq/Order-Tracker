<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Edición de agenda</title>
</head>
<body>
<h1>Horario y día de la visita</h1>
<p> Seleccione el horario y el día para la visita del vendedor ${params.seller} al cliente ${params.nameclient} </p>


<g:form controller="agenda" action="upentry" params="[nameclient: "${params.nameclient}" , nameseller: "${params.seller}" , sellerid:"${params.sellerid}" , clientid:"${params.clientid}" ]" >


<div  style="form">          
<br>
<label>Horario: </label>
<br>
<g:select  name="hourr" from="${00..23}" value="${hours}"/> : 
<g:select  name="minutr" from="${00..59}" value="${minutes}"/>
<br>
<label>Día: </label>
<br>
<g:select  name="dayy" value="${day}" from="${['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado']}"/>
<br>

  </div>
<br><br><br>

<g:actionSubmit class="buttona"  value="Agregar" params="[nameclient: "${params.nameclient}" , nameseller: "${params.seller}" , sellerid:"${params.sellerid}" , clientid:"${params.clientid}" ]"  action="upentry"/>
    </g:form>
        
</body>
</html>
