<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Marcas</title>
</head>

<body>
            <h1>Agregar marca</h1>

<fieldset>


    <g:uploadForm action="upload_image">
    
    <label>Nombre:</label>
        <br>
        <g:textField type="text" required="" name="name"/>
        <br>
          <label>Subir imagen</label>
        
        <input type="file" class="buttonimg"  required="" name="image">
  <br>
        <input type="submit" class="buttonb" value="Guardar">


  </g:uploadForm>
</fieldset> 

</body>
</html>
