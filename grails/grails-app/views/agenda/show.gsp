<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Agenda de vendedores</title>
</head>
<body>

<h1>Agenda del vendedor: 

<g:each in="${sell}" var="se" status="i">

 ${se.name}
</br>

</g:each>

</h1>

    <g:each in="${resul}" var="res" status="i">

 ${res[0]}, ${res[1]}, ${res[2]}, ${res[3]}

</br>

</g:each>


  </table>
</div>

</body>
</html>
