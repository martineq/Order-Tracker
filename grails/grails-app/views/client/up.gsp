<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Alta de Clientes</title>
</head>
<body>
            <h1>Ingrese los datos del nuevo cliente</h1>

        <g:form controller="client" action="save">
            <label>Nombre: </label>
            <g:textField name="name"/><br/>
            <label>Ciudad: </label>
            <g:textField name="city"/><br/>
            <label>Direccion: </label>
            <g:textField name="address"/><br/>
            <label>E-mail: </label>
            <g:textField name="mail"/><br/>
            <g:actionSubmit value="Save"/>
        </g:form>
	</body>

</body>
</html>
