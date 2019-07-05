$(document).ready(function () {

    $("#logout").on("click", function (event) {
        handleDisconnectButton();
        event.preventDefault();
        window.location.href = '/logout';
    });
});


