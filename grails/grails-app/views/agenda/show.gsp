<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Agenda de vendedores</title>

    <style>
    .dropdown-menu-2 {
  left: 50%;
  right: auto;
  text-align: center;
  transform: translate(-50%, 0);
}
</style>

</head>

<body>

<h1>Agenda del vendedor: 

<g:each in="${sell}" var="se" status="i">

 ${se.name}
 </h1>

</br>
<p>¿Para qué día desea ver el calendario del vendedor?</p> 

  <div class="dropdown" align="center">
    <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Seleccione el dia...
    <span class="caret"></span></button>
    <ul class="dropdown-menu-2 dropdown-menu center"  align="center">
      <li><g:link action="show" params="[day: 1 ]"  id="${se.id}">Domingo</g:link></li>
     <li><g:link action="show" params="[day: 2 ]"  id="${se.id}">Lunes</g:link></li>
      <li><g:link action="show" params="[day: 3 ]"  id="${se.id}">Martes</g:link></li>
       <li><g:link action="show" params="[day: 4 ]"  id="${se.id}">Miércoles</g:link></li>
      <li><g:link action="show" params="[day: 5 ]"  id="${se.id}">Jueves</g:link></li>
      <li><g:link action="show" params="[day: 6 ]"  id="${se.id}">Viernes</g:link></li>
      <li><g:link action="show" params="[day: 7 ]"  id="${se.id}">Sábado</g:link></li>
      <li><g:link action="show" params="[day: 0 ]"  id="${se.id}">Todos</g:link></li>
    </ul>
  </div>
  


 <div align="right"><g:link action="up" params="[sellerid: "${se.id}" , nameseller: "${se.name}" ]"><b>Agregar entrada</b><asset:image src="add.png" alt="Editar" style="width:38px;height:38px;"/></g:link></div>

 </g:each>
 
<g:if test="${resul}">


<table style="border-top: 0px solid #fff; margin:0px">
  <tr style="border-top: 0px solid #fff;  background: #fff ">
    <td style="border-top: 0px solid #fff;  background: #fff ">
    
<div class="table-responsive">
  <table class="table" style="border-top: 0px solid #fff; margin:0px">
     <tr>
    <th class="col-md-4">Cliente</th>
    <th class="col-md-4">Dirección</th>
    <th class="col-md-3">Día y Horario</th>
    <th class="col-md-3">Estado</th>
    <th class="col-md-1">Borrar</th>
    <th class="col-md-1">Delegar</th>
  </tr>
  

<g:each in="${resul}" var="res" status="i">


  <tr style="border-top: 0px solid #fff;  background: #fff ">
    <td class="tg-yw4l">${res[1]} 
    
    
    
    <g:link title="Editar cliente" action="selectnewclient" params="[agendaid: "${res[4]}" , clientid: "${res[5]}" ,  hour: "${res[3]}" , nameclient: "${res[1]}" , day: "${res[2]}" , nameseller: "${sell.name}" ]">
    <asset:image src="edit.png" alt="Editar" style="width:20px;height:20px;"/></g:link>
    
    
    </td>
    
     <td class="tg-yw4l">
     ${res[6]}
     </td>
    <td class="tg-yw4l">
    ${res[3]} - 
    <g:if test="${res[2]==1}" >
Domingo
</g:if>
<g:if test="${res[2]==2}" >
Lunes
</g:if>
<g:if test="${res[2]==3}" >
Martes
</g:if>
<g:if test="${res[2]==4}" >
Miércoles
</g:if>
<g:if test="${res[2]==5}" >
Jueves
</g:if>
<g:if test="${res[2]==6}" >
Viernes
</g:if>
<g:if test="${res[2]==7}" >
Sábado
</g:if>
<g:link title="Editar día y horario" action="editagenda" params="[agendaid: "${res[4]}" , clientid: "${res[5]}" ,  hour: "${res[3]}" , nameclient: "${res[1]}" , day: "${res[2]}" , nameseller: "${sell.name}" ]">
<asset:image src="edit.png" alt="Editar" style="width:20px;height:20px;"/> </g:link>
</td>

   <td class="tg-yw4l">${res[8]}</td>

   <td class="tg-yw4l">
   <g:link title="Borrar entrada" action="deleteconfirm" id="${res[4]}" params="[hour: "${res[3]}" , nameclient: "${res[1]}" , day: "${res[2]}" , nameseller: "${sell.name}" ]" ><asset:image src="delete.png" alt="Borrar" style="width:20px;height:20px;"/></g:link>
   </td>
   
   
   <td class="tg-yw4l">
   <g:link title="Asignar esta visita a otro vendedor" action="changeseller" params="[agendaid: "${res[4]}" , day: "${res[2]}" , hour: "${res[3]}", nameclient: "${res[1]}" , nameseller: "${sell.name}" ]" >
   
   <asset:image src="delegat.png" alt="Delegar" style="width:25px;height:25px;"/></g:link></td>
   
  </tr>


  </g:each>
  </table>
  
  
  </td>
  
  <g:if test="${params.day!='0'}" >
    <td width="35%" >
    <div id="map" style="width:100%; height:500px; border:1px solid black;"></div>
    </td>
    
    </g:if>
    
  </tr>
</table>



</div>

</g:if>

<g:elseif test="${dayr != '-1'}">
<br>
<p><b>No hay clientes agendados para mostrar</b></p>
</g:elseif>


<script defer
src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDW_OGVqAx3mK-bk4WrdMsu5cLMpi7vatM&callback=addPoints">
    </script>
<script type="text/javascript">
    var markers = [];
    
        
    function initMap () {
        var mapOptions = {
            center: {lat:markers[0].lat, lng: markers[0].lng},
            zoom: 15,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map(document.getElementById("map"), mapOptions);
        var infoWindow = new google.maps.InfoWindow();
        var lat_lng = new Array();
        var latlngbounds = new google.maps.LatLngBounds();
        for (i = 0; i < markers.length; i++) {
            var data = markers[i]
            var myLatlng = new google.maps.LatLng(data.lat, data.lng);
            lat_lng.push(myLatlng);
            var marker = new google.maps.Marker({
                position: myLatlng,
                map: map,
                title: data.title
            });
            latlngbounds.extend(marker.position);
            (function (marker, data) {
                google.maps.event.addListener(marker, "click", function (e) {
                    infoWindow.setContent(data.description);
                    infoWindow.open(map, marker);
                });
            })(marker, data);
        }
        map.setCenter(latlngbounds.getCenter());
        map.fitBounds(latlngbounds);
 
        //***********ROUTING****************//
 
        //Initialize the Path Array
        var path = new google.maps.MVCArray();
 
        //Initialize the Direction Service
        var service = new google.maps.DirectionsService();
 
        //Set the Path Stroke Color
        var poly = new google.maps.Polyline({ map: map, strokeColor: '#4986E7' });
 
        //Loop and Draw Path Route between the Points on MAP
        for (var i = 0; i < (lat_lng.length-1); i=i+1) {

                var src = lat_lng[i];
                var des = lat_lng[i+1];

                //path.push(src);
                poly.setPath(path);
                service.route({
                    origin: src,
                    destination: des,
                    travelMode: google.maps.DirectionsTravelMode.DRIVING
                }, function (result, status) {
                    if (status == google.maps.DirectionsStatus.OK) {
                        for (var i = 0, len = result.routes[0].overview_path.length; i < len; i++) {
                            path.push(result.routes[0].overview_path[i]);
                        }
                    }
                });

        }
    }
    
    function addPoints() {

        <g:applyCodec encodeAs="none">
            var array = ${ilist};
        </g:applyCodec>
        
        if(${params.day} != '0') {
            var arrayLength = array.length;
            for (var i = 0; i < arrayLength; i++) {
                geocodeAddress(array[i],i);
            } 
        }
    }
    
    
    function geocodeAddress(add,i) {
    
        <g:applyCodec encodeAs="none">
            var array = ${ilist};
        </g:applyCodec>
        var address =  add;
        var geocoder = new google.maps.Geocoder();

        geocoder.geocode({'address': address}, function(results, status) {
          if (status === google.maps.GeocoderStatus.OK) {
            var latitude = results[0].geometry.location.lat();
            var longitude = results[0].geometry.location.lng();
            var obj = {title:latitude+" "+longitude,lat:parseFloat(latitude),lng:parseFloat(longitude),description:address}; 
            markers[i]=obj;
            if(markers.length == array.length) initMap();
          } else {
            alert('Hubo un error al intentar localizar la dirección de un cliente: ' + status);
          }
        });
      }
</script>
    
    
    
</body>
</html>
