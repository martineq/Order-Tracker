<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="OrderTracker"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <asset:stylesheet src="application.css"/>

    <g:layoutHead/>
</head>
<body>

    <div class="navbar navbar-default navbar-static-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/#">
                    <i class="fa grails-icon">
                        <asset:image src="maplogoexample.png"/>
                    </i> OrderTracker
                </a>
            </div>
            <div class="navbar-collapse collapse" aria-expanded="false" style="height: 0.8px;">
                <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Vendedores <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="/seller/index">Listado</a></li>
                <li><a href="/agenda/index">Agenda</a></li>
                <li role="separator" class="divider"></li>
            </ul>
        </li>
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Clientes <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="/client/index">Listado</a></li>
                <li><a href="/client/up">Alta</a></li>
                <li><a href="#">Modificación</a></li>
                <li><a href="#">Baja</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Productos<span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="#">Listado</a></li>
                <li><a href="#">Editar listado</a></li>
            </ul>
        </li>
                <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Descuentos</a>

        </li>
    
                </ul>
            </div>
        </div>
    </div>
        <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>
 

    <div class="svg" role="presentation">
        <div class="grails-logo-container">
            <asset:image src="iconmap.png" class="grails-logo"/>
        </div>
    </div>

    <div id="content" role="main">
        <section class="row colset-2-its">
        

    <g:layoutBody/>

    <div class="footer" role="contentinfo"></div>

    <div id="spinner" class="spinner" style="display:none;">
        <g:message code="spinner.alt" default="Loading&hellip;"/>
    </div>

    <asset:javascript src="application.js"/>

</body>
</html>
