<%-- 
    Document   : activation
    Created on : 10-dic-2016, 18:29:23
    Author     : gines
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lander Game</title>

        <!-- Carga de estilos de la página -->
        <link href="css/activation.css" rel="stylesheet" type="text/css"/>

        <!-- Carga de la librería de jQuery -->
        <script src="js/jquery-3.1.1.min.js" type="text/javascript"></script>

        <!-- Scripts de gestión de la página, peticiones a los Servlets -->
        <script src="js/activation.js" type="text/javascript"></script>
    </head>
    <body>
        <div id="activation-modal-window" class="modal" style="display: block;">
            <div class="modal-content">
                <div class="modal-header">
                    <h2>Activación de la cuenta de usuario</h2>
                </div>
                <div id="activation-content" class="modal-body">
                    <div id="data"></div>
                    <p>Activando la cuenta... Por favor, espere.</p>
                </div>
                <!--<div class="modal-footer">
                </div>-->
            </div>
        </div>
    </body>
</html>
