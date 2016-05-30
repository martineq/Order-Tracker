<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Edición de agenda</title>
</head>
<body>
<h1>Edición de agenda</h1>
<p> Cambie el horario y el día para la visita del vendedor ${params.nameseller} al cliente ${params.nameclient} </p>


<g:form controller="agenda" action="savechanges" id="${params.agendaid}" params="[nameclient: "${params.nameclient}" , nameseller: "${params.nameseller}" ]" >


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

<g:actionSubmit class="buttona"  value="Guardar" id="${params.agendaid}"  params="[nameclient: "${params.nameclient}" , nameseller: "${params.nameseller}" ]"  action="savechanges"/>
    </g:form>
        
</body>
</html>
