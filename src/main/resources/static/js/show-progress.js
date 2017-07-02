var stompClient = null;

function setConnected(connected) {
    $("#show").prop("disabled", connected);
    // $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#progress").show();
    }
    else {
        $("#progress").hide();
    }
    $("#cur_count").html("");
}

function connect() {
    var socket = new SockJS("/ws");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/progress', function (count) {
            showGreeting(JSON.parse(count.body));
        });
    });
}

function showGreeting(message) {
    $("#cur_count").text(message)
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

$(function () {
    $( "#show" ).click(function() { connect(); });
    // $( "#disconnect" ).click(function() { disconnect(); });
    // $( "#send" ).click(function() { sendName(); });
});

