<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Marcas</title>
</head>

<body>
            <h1>Editar marca</h1>

<fieldset>


    <g:uploadForm action="saveeditedbrand"  params="[id:"${params.id}"]">
    
    <label>Nombre:</label>
        <br>
        <g:textField type="text" value="${params.name}" required="" name="name"/>
        <br>
          <label>Subir imagen</label>
        
        <input type="file" class="buttonimg" name="image">
  <br>
        <input type="submit" class="buttonb" value="Guardar"  params="[id:"${params.id}"]">


  </g:uploadForm>
</fieldset> 

</body>
</html>
