$(document).ready(function () {
    $("#data").html("Activando la cuenta... Por favor, espere.");
    var dataString = getUrlParams();
    $.ajax({
        type: "POST",
        url: "Activate",
        dataType: "text",
        data: dataString,
        success: function (response) {
            if (response === "1") {
                $("#data").html("Cuenta activada correctamente.");
            } else {
                $("#data").html("Error en la activaci&oacute; de la cuenta.");
            }
        }
    });
});

function getUrlParams() {
    var pageUrl = decodeURIComponent(window.location);
    var urlParams = pageUrl.split('?');
    return urlParams[1];
}