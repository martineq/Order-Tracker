<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Agregar producto</title>
</head>

<body>
            <h1>Agregar producto</h1>

<fieldset>


    <g:uploadForm action="uploadproduct">
    
    <label>Nombre:</label>
        <br>
        <g:textField type="text" required="" name="name"/>
        <br>
        <label>Marca: </label>
        <br>
        <g:select name="brand"
                from="${brands.name}" />
        <br><br>
        <label>Categoría: </label>
        <br>
        <g:select name="category"
                from="${cats.name}" />
                <br><br>
          
        <label>Características:</label>
        <br>
        <g:textField type="text" required="" name="charac"/>
        <br>
        <label>Precio:</label>
        <br>
         <g:textField  type="text" name="price" required=""  pattern="^[0-9]+\\s*\$|^[0-9]+\\.?[0-9]+\\s*\$" />
         <br>
         <label>Stock:</label>
        <br>
         <g:textField  type="text" name="stock" required=""  pattern="^[0-9]+\\s*\$|^[0-9]+\\.?[0-9]+\\s*\$" />

        <br>
        <label>Subir imagen</label>
        <input type="file" class="buttonimg" name="image" required="">
  <br>
        <input type="submit" class="buttonb" value="Guardar">


  </g:uploadForm>
</fieldset> 

</body>
</html>
