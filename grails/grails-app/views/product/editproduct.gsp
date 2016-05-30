<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Editar producto</title>
</head>

<body>
            <h1>Editar producto</h1>

<fieldset>


    <g:uploadForm action="editedproduct" params="[id:"${params.id}"]">
    
    <label>Nombre:</label>
        <br>
        <g:textField type="text" required="" name="name" value="${params.name}" />
        <br>
        <label>Marca: </label>
        <br>
        <g:select name="brand"
                from="${brands.name}" value="${params.brand}" />
        <br><br>
        <label>Categoría: </label>
        <br>
        <g:select name="category"
                from="${cats.name}" value="${params.cat}" />
                <br><br>
          
        <label>Características:</label>
        <br>
        <g:textField type="text" required="" name="charac"  value="${params.car}" />
        <br>
        <label>Precio:</label>
        <br>
         <g:textField  type="text" name="price" value="${params.price}" required=""  pattern="^[0-9]+\\s*\$|^[0-9]+\\.?[0-9]+\\s*\$" />
         <br>
         <label>Stock:</label>
        <br>
         <g:textField  type="text" name="stock" required="" value="${params.stock}"  pattern="^[0-9]+\\s*\$|^[0-9]+\\.?[0-9]+\\s*\$" />
         <br>

        <label>Subir imagen</label>
        <input type="file" class="buttonimg" name="image">
  <br>
        <input type="submit" class="buttonb" params="[id:"${params.id}"]" value="Guardar"  >


  </g:uploadForm>
</fieldset> 

</body>
</html>
