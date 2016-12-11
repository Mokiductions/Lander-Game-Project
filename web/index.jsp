<!doctype html>
<html lang="es-ES">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lander Game</title>

        <!-- Carga de estilos de la página -->
        <link href="css/lunar_styles.css" rel="stylesheet" type="text/css"/>
        <link href="css/styles.css" rel="stylesheet" type="text/css"/>

        <!-- Carga de la librería de jQuery -->
        <script src="js/jquery-3.1.1.min.js" type="text/javascript"></script>

        <!-- Scripts de gestión de la página, peticiones a los Servlets -->
        <script src="js/scripts.js" type="text/javascript"></script>
        <script src="js/lunar.js" type="text/javascript"></script>
    </head>

    <body>
        <div id="top-bar" class="top-bar">
            <span id="user-data-top-bar"></span>
            <input type="button" type="button" id="btn-logout" class="btn-top-bar" value="Salir de la cuenta" />
            <input type="button" type="button" id="btn-scores" class="btn-top-bar" value="Ver puntuaciones" />
            <input type="button" type="button" id="btn-most-played" class="btn-top-bar" value="Ver usuarios con más partidas" />
            <input type="button" type="button" id="btn-act-users" class="btn-top-bar" value="Ver usuarios activos" />
        </div>
        <nav id="settings-menu">
            <div id="expand">Ajustes</div>
            <div id="content-collapsible">
                <div id="dif">Dificultad: Media</div>
                <div id="sound">Sonido: Sí</div>
                <hr/>
                <div id="about">Acerca de...</div>
                <hr/>
                <div id="close-menu">Cerrar menú</div>
            </div>
        </nav>
        <div id="wrapper">
            <div id="state">
                <div class="container">
                    <h1></h1>
                    <h2></h2>
                    <!--<div class="ads">google ads</div>-->
                    <a href="#" onclick="reset();">Play again</a>
                </div>
            </div>
            <div id="start">
                <div class="container">
                    <h1>Bienvenido</h1>
                    <h2>Empecemos a jugar!</h2>
                    <!--<div class="ads">google ads</div>-->
                    <a href="#" onclick="reset();">Play</a>
                </div>
            </div>
            <div id="game">
                <div id="gauge"><div></div></div>
                <div id="ship"></div>
                <div id="explode"></div>
                <div id="moon">
                    <div id="landing-pad"><div id="ms">-</div></div>
                </div>
            </div>
        </div>

        <div id="login-modal-window" class="modal-bottom" style="display: block;">
            <div class="modal-content-bottom">
                <div class="modal-header-bottom">
                    <span id="login-title">Identifícate para continuar!</span>
                </div>
                <div id="login-content" class="modal-body">
                    <form id="login-form">
                        <label for="usr-input">Usuario:</label><br/>
                        <input type="text" id="usr-input" />
                        <label for="pwd-input">Contraseña:</label><br/>
                        <input type="password" id="pwd-input" /><hr/>
                        <input type="button" id="login-submit" value="Identificarse" />
                    </form>
                    <div id="register"><span id="register-link">¿No tienes una cuenta? ¡Regístrate!</span></div>
                </div>
                <div class="modal-footer-bottom">
                    <span id="login-footer"></span>
                </div>
            </div>
        </div>

        <div id="scores-modal-window" class="modal" style="z-index: 999;">
            <div class="modal-content">
                <div class="modal-header">
                    <span class="close">×</span>
                    <h2>Top 10 puntuaciones</h2>
                </div>
                <div id="scores-content" class="modal-body">
                    <p>Cargando...</p>
                </div>
                <!--<div class="modal-footer">
                </div>-->
            </div>
        </div>

        <div id="activeplayers-modal-window" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <span class="close">×</span>
                    <h2>Usuarios activos los últimos 5min</h2>
                </div>
                <div id="activeplayers-content" class="modal-body">
                    <p>Cargando...</p>
                </div>
                <!--<div class="modal-footer">
                </div>-->
            </div>
        </div>
        
        <div id="mostplayed-modal-window" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <span class="close">×</span>
                    <h2>Usuarios con más partidas jugadas</h2>
                </div>
                <div id="mostplayed-content" class="modal-body">
                    <p>Cargando...</p>
                </div>
                <!--<div class="modal-footer">
                </div>-->
            </div>
        </div>
        
        <div id="register-modal-window" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <h2>Registro de nuevo usuario</h2>
                </div>
                <div id="register-content" class="modal-body">
                    <form id="register-form">
                        <label for="usr-input">Usuario:</label><br/>
                        <input type="text" id="usr-input-reg" required/><hr/>
                        <label for="usr-input">Correo electrónico:</label><br/>
                        <input type="text" id="mail-input-reg" required/><hr/>
                        <label for="pwd-input">Contraseña:</label><br/>
                        <input type="password" id="pwd-input-1" required/><br/>
                        <label for="pwd-input">Repita la contraseña:</label><br/>
                        <input type="password" id="pwd-input-2" required/><hr/>
                        <input type="button" id="register-submit" value="Registrarse" />
                        <input type="button" id="register-cancel" value="Cancelar" />
                    </form>
                    <span  id="register-close">Continuar</span>
                </div>
                <!--<div class="modal-footer">
                </div>-->
            </div>
        </div>

    </body>
</html>
