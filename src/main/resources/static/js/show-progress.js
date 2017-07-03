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

function connect(topicId) {
    var socket = new SockJS("/ws");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/progress/'+topicId, function (count) {
            // showProgress(JSON.parse(count.body));
            showProgress(count.body);
        });
    });

    var socket2 = new SockJS("/ws");
    stompClient2 = Stomp.over(socket2);
    stompClient2.connect({}, function (frame) {
        stompClient2.subscribe('/topic/pages/'+topicId, function (count) {
            showPagesProcessed(count.body);
        });
    });
}

function showProgress(message) {
    var count = message.split(":");
    $("#cur_count").text(count[0]);
    $("#all_sessions").text(count[1]);
}

function showPagesProcessed(message) {
    var count = message.split(":");
    $("#cur_page").text(count[0]);
    $("#total_page").text(count[1]);
    var percent = count[0] * 100 / count[1] + "%";
    $(".progress-bar").width(percent);
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
    $( "#show" ).click(function() {
        var md5key = $.md5($("#url").val());
        connect(md5key);
    });
    // $( "#disconnect" ).click(function() { disconnect(); });
    // $( "#send" ).click(function() { sendName(); });
/*    var total_cur = $("#")
    $("#submit").click(function () {

    })*/
});

