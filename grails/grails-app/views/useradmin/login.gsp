<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Ingresar</title>
        <style>
                .outer {
            display: table;
            position: absolute;
            height: 100%;
            width: 100%;
            background-color: #022747;
            }

                .middle {
            display: table-cell;
            vertical-align: middle;
            }

                .inner {
            margin-left: auto;
            margin-right: auto; 
            width: 400px;
            }

        </style>
    </head>
    <body>
    
        <div class="outer">
        <div class="middle">
        <div class="inner">

         <div style="color: #fff;font-size:20px;" align="center"><asset:image  src="maplogoexample.png" alt="Order" style="width:256px;height:256px;"/>
         <br><b>OrderTracker</b><br><br></div>
            <g:form action="doLogin" method="post" style="

                border:solid black 1px;
                border-radius:6px;
                background-color: #fff;
                -moz-border-radius:6px;">
    
    
                <div  align="center" >
                <p>Introduzca su e-mail y contraseña:</p>
                <table class="userForm"   align="center">
                    <tr class='prop'>
                    <td valign='top' style='text-align:left;'>
                    <label for='email'>E-mail:</label>
                    </td>
                    <td valign='top' style='text-align:left;'>
                    <input id="email" type='text' name='email' value='${user?.email}' />
                    </td>
                    </tr>
                    <tr class='prop'>
                    <td valign='top' style='text-align:left;' >
                    <label for='password'>Contraseña:</label>
                    </td>
                    <td valign='top' style='text-align:left;'>
                    <input id="password" type='password' name='password'
                    value='${user?.password}' />
                    </td>
                    </tr>
                </table>
                </div>
                <div class="buttons">
                <span class="formButton">
                <center><br><input type="submit" value="Login" action="doLogin" ></input><br></center>
                </span>
                </div>
                <br>
            </g:form>
        </div>
        </div>
        </div>
    </body>
</html>
