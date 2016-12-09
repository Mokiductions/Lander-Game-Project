// Moon
var moon = {};
moon.mtop = 50;
moon.g = 0.04; // Gravity

// Ship
var ship = {};
ship.y = 0; // Position
ship.v = 0; // Speed
ship.thrust = -moon.g; // Thrust

ship.maxSpeed = 20;
ship.safeSpeed = 2; // Maximum speed 'til it explodes
var dif = 2;

ship.maxFuel = 175; // Fuel/FPS
ship.fuel = ship.maxFuel; // Restore fuel

var pause = true;
//Cambiar est
var sound = false;
var spacePressed = 0;

// Sound efects
var audio = {};
audio.puh = document.createElement("AUDIO");
audio.puh.src = './sound/puh.mp3';
$(audio.puh).prop('volume', 0);

function map(x, in_min, in_max, out_min, out_max) {
    return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
}

function reset() {
    $('#content-collapsible').slideUp('slow');
    $('#state').hide();
    $('#start').hide();
    $('#state h1').removeClass();
    ship.fuel = ship.maxFuel;
    ship.y = 0;
    ship.v = 0;
    spacePressed = false;
    $('#ms').css('color', '#000');
    $('#explode').removeClass();
    $('#explode').css('background', 'transparent');
    pause = false;
}

function getSpeed() {
    return parseFloat(Math.round(-ship.v * 100) / 100).toFixed(2);
}

function resize() {
    moon.base = $('#landing-pad').height() + -($('#landing-pad').height() - 150) * 0.4 + 30;
}

function doEvent(kind, ms) {
    pause = true;
    var score = calcScore();
    saveGameData(score);
    $('#state h1').addClass(kind);
    $('#state h1').html((kind === 'win') ? '&iexcl;HAS GANADO!' : '&iexcl;HAS PERDIDO!');
    $('#state h2').html((kind === 'win') ? 'Velocidad: ' + ms + ' m/s <br/> Puntos: ' + score + '/100' : 'Maybe next time...');
    $('#ms').css('color', (kind === 'win') ? '#0a0' : '#f00');
    if (kind === 'lose') {
        $(audio.puh).prop('volume', 1);
        if (sound) {
            audio.puh.play();
        }
        $('#explode').addClass('exploded');
        $('#explode').css('background', 'url(\'./img/explode.gif?p=' + new Date().getTime() + '\')');
    }
    // Comentar la siguiente línea para deshabilitar el replay
    $('#state').delay((kind === 'win') ? 0 : 1000).show(0);
}

function calcScore() {
    var sc = 0;
    if (ship.v < ship.safeSpeed) {
        sc = 100 - ((ship.v * 100) / ship.safeSpeed);
    }
    return Math.round(sc);
}

$(document).ready(function () {

    $('#dif').click(function () {
        if (pause) {
            switch (dif) {
                case 1:
                    dif = 2;
                    ship.safeSpeed = 2;
                    $('#dif').html("Dificultad: Media");
                    break;
                case 2:
                    dif = 3;
                    ship.safeSpeed = 1;
                    $('#dif').html("Dificultad: Dif&iacute;cil");
                    break;
                case 3:
                    dif = 1;
                    ship.safeSpeed = 4;
                    $('#dif').html("Dificultad: F&aacute;cil");
                    break;
            }
        }
    });

    $('#sound').click(function () {
        sound = !sound;
        if (sound) {
            $('#sound').html("Sonido: S&iacute;");
        } else {
            $('#sound').html("Sonido: No");
        }
    });

    $('body').keydown(function (e) {
        //e.preventDefault();
        if (e.keyCode === 32)
            if (pause && !spacePressed && $('#state').css('display') === 'block') {
                //reset(); // Space while alert is shown to restart game
            } else {
                spacePressed = 1;
            }
    });
    $('body').keyup(function (e) {
        //e.preventDefault();
        if (e.keyCode === 32)
            spacePressed = 0;
    });

    $('#game').bind('touchstart', function (e) {
        e.preventDefault();
        audio.puh.play();
        spacePressed = 1;
    }).bind('touchend', function (e) {
        e.preventDefault();
        spacePressed = 0;
    });

    // Update
    window.setInterval(function () {
        if (!pause) {
            $(audio.puh).delay(1000).prop('volume', 0);
            ship.v += (spacePressed) ? ((ship.fuel > 0) ? ship.thrust : moon.g) : moon.g; // Aceleración
            ship.v = (ship.v > ship.maxSpeed) ? ship.maxSpeed : ((ship.v < -ship.maxSpeed) ? -ship.maxSpeed : ship.v); // Velocidad

            if ((ship.v > 0 && ship.y < 500) || (ship.v < 0 && ship.y > 0))
                ship.y += ship.v;
            else
            {
                if (ship.y >= 500)
                {
                    ship.y = 500;
                    $('#ms').html(getSpeed() + ' m/s');
                    doEvent((ship.v > ship.safeSpeed) ? 'lose' : 'win', getSpeed());
                }
                if (ship.y < 0)
                    ship.y = 0;
                if (!pause)
                    ship.v = 0;
            }

            // Quitamos fuel
            if (ship.fuel > 0 && spacePressed)
                ship.fuel--;

            // Dibujamos el juego
            resize();
            $('#gauge div').css('width', ship.fuel / ship.maxFuel * 100 + '%');
            $('#ship').css('top', map(ship.y, 0, 500, moon.mtop, $('body').height() - moon.base));
            $('#explode').css('top', map(ship.y, 0, 500, moon.mtop, $('body').height() - moon.base) - 100);
            $('#ship').css('background', (spacePressed & ship.fuel > 0) ? 'url(\'./img/ship.png\')' : 'url(\'./img/shipOff.png\')');
            $('#ms').html(getSpeed() + ' m/s');
        }
    }, 16.6666667);
});