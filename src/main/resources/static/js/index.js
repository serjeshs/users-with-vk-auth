$(document).ready(function () {
    $("#connectButton").on("click", function (event) {
        event.preventDefault();
        handleConnectButton();
    });

    $("#disconnectButton").on("click", function (event) {
        event.preventDefault();
        handleDisconnectButton();
        document.getElementById("connectButton").removeAttribute("disabled");
    });

    $("#logout").on("click", function (event) {
        handleDisconnectButton();
        event.preventDefault();
        window.location.href = '/logout';
    });
});


