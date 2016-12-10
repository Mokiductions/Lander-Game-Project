$(document).ready(function () {

    var loginModalWindow = document.getElementById("login-modal-window");
    var registerModalWindow = document.getElementById("register-modal-window");
    var scoresModalWindow = document.getElementById("scores-modal-window");
    var activeModalWindow = document.getElementById("activeplayers-modal-window");
    var mostplayedModalWindow = document.getElementById("mostplayed-modal-window");
    var close1 = document.getElementsByClassName("close")[0];
    var close2 = document.getElementsByClassName("close")[1];
    var close3 = document.getElementsByClassName("close")[2];

    // Check for storage here.
    checkForStorage();

    window.onclick = function (event) {
        if (event.target === loginModalWindow) {
            alert('Tiene que rellenar los datos de inicio de sesion para continuar.');
        } else if (event.target === scoresModalWindow) {
            scoresModalWindow.style.display = "none";
        } else if (event.target === activeModalWindow) {
            activeModalWindow.style.display = "none";
        } else if (event.target === mostplayedModalWindow) {
            mostplayedModalWindow.style.display = "none";
        } else if (event.target === registerModalWindow) {
            registerModalWindow.style.display = "none";
        }
    };

    close1.onclick = function () {
        if (scoresModalWindow.style.display === "block") {
            scoresModalWindow.style.display = "none";
        }
    };

    close2.onclick = function () {
        if (activeModalWindow.style.display === "block") {
            activeModalWindow.style.display = "none";
        }
    };

    close3.onclick = function () {
        if (mostplayedModalWindow.style.display === "block") {
            mostplayedModalWindow.style.display = "none";
        }
    };

    $("#register-link").click(function () {
        registerModalWindow.style.display = "block";
    });

    $("#register-submit").click(function () {
        var usr = document.getElementById('usr-input-reg').value;
        var pwd1 = document.getElementById('pwd-input-1').value;
        var pwd2 = document.getElementById('pwd-input-2').value;
        if (usr === "" || pwd1 === "" || pwd2 === "") {
            // El usuario no ha rellenado todos los campos
            alert("Debe rellenar los campos del formulario para continuar con el registro.");
        } else if (pwd1 !== pwd2) {
            // Las dos contraseñas no coinciden
            alert("Las dos contraseñas deben ser iguales para continuar con el registro.");
        } else {
            var dataString = "USR=" + capitalizeFirstLetter(usr) + "&PWD=" + pwd2;
            $("#register-content").html("Registrando la cuenta... Por favor, espere.");
            $.ajax({
                type: "POST",
                url: "Register",
                dataType: "text",
                data: dataString,
                success: function (response) {
                    if (response === "1") {
                        var i = 5;
                        $("#register-content").html("Registro realizado correctamente. " + i + "...");
                        //$("#register-close").css("display", "block");
                        var int = setInterval(function (){ 
                            i--;
                            if (i === 0) {
                                registerModalWindow.style.display = "none";
                                location.reload();
                                clearInterval(int);
                            }
                            $("#register-content").html("Registro realizado correctamente. " + i + "...");
                        }, 700);
                    } else {
                        alert("Ha ocurrido un error");
                    }
                }
            });
        }
    });
    
    $("#register-cancel").click(function () {
        registerModalWindow.style.display = "none";
    });

    $("#pwd-input-2").keyup(function (event) {
        if (event.keyCode === 13) {
            $("#register-submit").click();
        }
    });

    $("#pwd-input").keyup(function (event) {
        if (event.keyCode === 13) {
            $("#login-submit").click();
        }
    });

    $("#usr-input").keyup(function (event) {
        if (event.keyCode === 13) {
            $("#login-submit").click();
        }
    });

    $("#expand").click(function () {
        $('#content-collapsible').slideToggle('slow');
    });

    $("#close-menu").click(function () {
        $('#content-collapsible').slideUp('slow');
    });

    $("#btn-act-users").click(function () {
        $.ajax({
            type: "POST",
            url: "LastActiveUsers",
            dataType: "text",
            success: function (response) {
                //alert(response);
                $('#activeplayers-content').html(response);
                activeModalWindow.style.display = "block";
            }
        });
    });

    $("#btn-scores").click(function () {
        $.ajax({
            type: "POST",
            url: "TopScores",
            dataType: "text",
            success: function (response) {
                //alert(response);
                $('#scores-content').html(response);
                scoresModalWindow.style.display = "block";
            }
        });
    });

    $("#btn-most-played").click(function () {
        mostplayedModalWindow.style.display = "block";
        $.ajax({
            type: "POST",
            url: "TopPlayedGames",
            dataType: "text",
            success: function (response) {
                //alert(response);
                $('#mostplayed-content').html(response);
                mostplayedModalWindow.style.display = "block";
            }
        });
    });

    // Escuchador para el botón de cierre de la sesión, vacía los datos de Local
    // Storage de usuario y contraseña y refresca la página para que vuelva a 
    // a aparecer el formulario de inicio de sesión.
    $('#btn-logout').click(function () {
        localStorage.setItem("usr", "");
        localStorage.setItem("pwd", "");
        location.reload();
    });

    $('#login-submit').click(function () {
        var usr = document.getElementById('usr-input').value;
        var pwd = document.getElementById('pwd-input').value;
        // Comprueba que el usuario haya rellenado el formulario
        if (usr === "" || pwd === "") {
            // El usuario no ha rellenado los campos del formulario
            alert('Tiene que rellenar los datos de inicio de sesi&oacute;n para continuar.');
        } else {
            // El usuario sí ha rellenado los campos del formulario
            var dataString = "USR=" + capitalizeFirstLetter(usr) + "&PWD=" + pwd;
            // Llama al servlet pasándole los datos del usuario para comprobar
            // si existe o no como usuario en la base de datos
            $.ajax({
                type: "POST",
                url: "Login",
                dataType: "text",
                data: dataString,
                success: function (response) {
                    if (response === "1") {
                        // El usuario sí que está dado de alta en la Base de datos
                        localStorage.setItem("usr", capitalizeFirstLetter(usr));
                        localStorage.setItem("pwd", pwd);
                        loginModalWindow.style.display = "none";
                        document.getElementById('user-data-top-bar').innerHTML = "Bienvenido, " + capitalizeFirstLetter(usr) + "!";
                        $('#start').show();
                    } else {
                        // El usuario no está dado de alta en la Base de datos
                        alert("El usuario " + capitalizeFirstLetter(usr) + " no esta dado de alta. ");
                    }
                }
            });
        }
    });
});

function saveGameData(score) {
    var usr = localStorage.getItem("usr");
    var dataString = "USR=" + usr + "&SCORE=" + score;
    $.ajax({
        type: "POST",
        url: "SaveGameData",
        dataType: "text",
        data: dataString,
        success: function (response) {
            //alert(response);
        }
    });
}

function capitalizeFirstLetter(str) {
    str = str.toLowerCase();
    str = str.charAt(0).toUpperCase() + str.slice(1);
    return str;
}

function test() {
    alert("Test function.");
}

// Función que comprueba si hay datos de usuario en el localStorage
function checkForStorage() {
    if (localStorage.usr && localStorage.usr !== "") {
        // Hay datos del usuario
        var loginModalWindow = document.getElementById("login-modal-window");
        loginModalWindow.style.display = "none";
        document.getElementById('user-data-top-bar').innerHTML = "Bienvenido, " + localStorage.usr + "!";
        $('#start').show();
    } else {
        // No existen datos del usuario
    }
}


