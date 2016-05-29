<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Nuevo vendedor registrado</title>
</head>
<body>
<g:if test="${okpass==true}" >
            <h1>Nuevo vendedor registrado</h1>

<div align="center"><p>Se registró el vendedor ${sellern}</p></div>
</br>
<div align="center">
<a href="/seller/index"><button type="button" class="btn btn-default">Volver</button></a>
</div>

  </table>
</div>
</g:if>
<g:else >
<h1>Contraseña incorrecta</h1>
<div align="center">
Las contraseñas ingresadas no concuerdan.<br>
<button type="button" class="btn btn-default" onclick="history.go(-1);">Volver</button></div>
</g:else>

</body>
</html>
