$(document).ready(function () {
    $("#data").html("Params: " + getUrlParams());
});

function getUrlParams() {
    var pageUrl = decodeURIComponent(window.location);
    var urlParams = pageUrl.split('?');
    return urlParams[1];
}