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
            // showProgress(JSON.parse(count.body));
            showProgress(count.body);
        });
    });
}

function showProgress(message) {
    var count = message.split(":");
    $("#cur_count").text(count[0]);
    $("#all_sessions").text(count[1]);
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
    var total_cur = $("#")
    $("#submit").click(function () {

    })
});

