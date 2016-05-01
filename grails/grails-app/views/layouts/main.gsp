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
            <a href="/seller/index" class="dropdown-toggle" role="button" aria-haspopup="true" aria-expanded="false">Vendedores</a>
        </li>
        <li class="dropdown">
            <a href="/agenda/index" class="dropdown-toggle" role="button" aria-haspopup="true" aria-expanded="false">Agenda</a>

        </li>
        <li class="dropdown">
            <a href="/client/index" class="dropdown-toggle" role="button" aria-haspopup="true" aria-expanded="false">Clientes</a>
        </li>
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" role="button" aria-haspopup="true" aria-expanded="false">Productos</a>
        </li>
                <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Descuentos</a>

        </li>
            </div>
        </div>
    </div>
        <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>
 


    <div id="content" role="main">
        <section class="row colset-2-its">
        

    <g:layoutBody/>

    <div class="footer" role="contentinfo"></div>

    <div id="spinner" class="spinner" style="display:none;">
        <g:message code="spinner.alt" default="Loading&hellip;"/>
    </div></div>

    <asset:javascript src="application.js"/>

</body>
</html>
