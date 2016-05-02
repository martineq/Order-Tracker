<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Detalles de cliente</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <style>
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }

    </style>
  </head>
  <body>
  
  <h1>Detalles del cliente ${params.name}</h1>
  <div class="table-responsive">
  <table border="1px" style="border-top: 1px solid #000">
  <tr style="border-top: 0px solid #fff;  background: #fff ">
    <td style="border-top: 0px solid #fff; background: #fff ">
<table class="table" >
  <tr style="hover { background: #fff }">
    <td><b>Nombre:</b> ${params.name}</td>
  </tr>
  <tr>
    <td><b>Dirección:</b> ${params.address}</td>
  </tr>
  <tr>
    <td><b>Ciudad:</b> ${params.city}</td>
  </tr>
  <tr>
    <td><b>E-mail:</b> ${params.mail}</td>
  </tr>
</table>
</td>
    <td width="100%">
    
    
      <table style="border-top: 0px solid #fff"> 
  <tr>  
    <td width="100%" height="500px">
      <div id="map" style="width:100%; height:100%"></div>
    </td>
  </tr>
</table>



</td>
  </tr>
</table>
</div>

    <script>
      function initMap() {
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 15,
          center: {lat: -34.397, lng: 150.644}
        });
        var geocoder = new google.maps.Geocoder();
         geocodeAddress(geocoder, map);
 
      }

      function geocodeAddress(geocoder, resultsMap) {
        var address =  "${address}";
        geocoder.geocode({'address': address}, function(results, status) {
          if (status === google.maps.GeocoderStatus.OK) {
            resultsMap.setCenter(results[0].geometry.location);
            var marker = new google.maps.Marker({
              map: resultsMap,
              position: results[0].geometry.location
            });
          } else {
            alert('Hubo un error al intentar localizar la dirección de este cliente: ' + status);
          }
        });
      }
      </script>
    
  
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDW_OGVqAx3mK-bk4WrdMsu5cLMpi7vatM&callback=initMap">
    </script>


    
  </body>
</html>

