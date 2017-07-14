/**
 * Created by xuehaipeng on 2017/7/7.
 */
function save2Es(type) {
    var url = $("#url").val();
    var startIndex = $("#startIndex").val();
    var endIndex = $("#endIndex").val();
    var extractArea = $("#extractArea").val();
    var linkPosition = $("#linkPosition").val();
    var menuId = $("#menuId").val();
    var title = $("#title").val();
    var pubDate1 = $("#pubDate1").val();
    var pubDate2 = $("#pubDate2").val();
    var pubDate3 = $("#pubDate3").val();
    var keyword = $("#keyword").val();
    var exclude1 = $("#excluded1").val();
    var exclude2 = $("#excluded2").val();
    if (type == 0) {
        var content = $("#content").val();
        var content2 = $("#content2").val();
        var data = {"url": url, "startIndex": startIndex, "endIndex": endIndex, "extractArea": extractArea, "linkPosition": linkPosition,
            "menuId": menuId, "title": title, "pubDate1": pubDate1, "pubDate2": pubDate2, "pubDate3": pubDate3, "keyword": keyword,
            "content": content, "content2": content2, "exclude1": exclude1, "exclude2": exclude2, "type": type};
        $.getJSON("/es/insertArticleForm", data);
    } else {
        var question = $("#question").val();
        var answer = $("#answer").val();
        var data = {"url": url, "startIndex": startIndex, "endIndex": endIndex, "extractArea": extractArea, "linkPosition": linkPosition,
            "menuId": menuId, "title": title, "pubDate1": pubDate1, "pubDate2": pubDate2, "pubDate3": pubDate3, "keyword": keyword,
            "question": question, "answer": answer, "exclude1": exclude1, "exclude2": exclude2, "type": type};
        $.getJSON("/testQuestionFetch", data);
    }
}

function domainURI(str){
    var durl=/http:\/\/([^\/]+)\//i;
    domain = str.match(durl);
    return domain[1];
}

function applyForm(form_id) {
    var queryUrl = form_id.getAttribute("id");
    $.get("/es/queryArticleFormById/" + queryUrl, function (data) {
        Materialize.updateTextFields();
        $("#url").val(data.url);
        $("#startIndex").val(data.startIndex);
        $("#endIndex").val(data.endIndex);
        $("#extractArea").val(data.extractArea);
        $("#linkPosition").val(data.linkPosition);
        $("#menuId").val(data.menuId);
        $("#title").val(data.title);
        $("#pubDate1").val(data.pubDate1);
        $("#pubDate2").val(data.pubDate2);
        $("#pubDate3").val(data.pubDate3);
        $("#keyword").val(data.keyword);
        $("#excluded1").val(data.exclude1);
        $("#excluded2").val(data.exclude2);
        if (data.type == 0) {
            $("#content").val(data.content);
            $("#content2").val(data.content2);
        } else if (data.type == 1) {
            $("#question").val(data.question);
            $("#answer").val(data.answer);
        }
    })
}

function checkForm() {
    var url = domainURI($("#check_url").val());
    var href = window.location.href;
    if (href.endsWith("/article")) {
        $.get("/es/queryArticleForm", {"url": url}, function (data) {
            $("#result").html("");
            $.each(data, function (i, form) {
                $("#result").append("<tr><td>" + form.url + "</td><td><a href='javascript:void(0)' id='"
                    + form.id + "' onclick='applyForm(" + form.id + ")'>Apply</a> | <a href='/es/deleteArticleForm?id='" + form.id + "'>Delete</a>" +"</td></tr>")
            })
        })
    } else if (href.endsWith("/question")) {
        $.get("/es/queryQuestionForm", {"url": url}, function (data) {
            $("#result").html("");
            $.each(data, function (i, form) {
                $("#result").append("<tr><td>" + form.url + "</td><td><a href='javascript:void(0)' id='"
                    + form.id + "' onclick='applyForm(" + form.id + ")'>Apply</a> | <a href='/es/deleteQuestionForm?id='" + form.id + "'>Delete</a></td></tr>")
            })
        })
    }

}

$(function() {
    $('#save').click(function(){
        var href = window.location.href;
        if (href.endsWith("/article")) {
            save2Es(0);
        } else if (href.endsWith("/question")) {
            save2Es(1);
        }
    });

});