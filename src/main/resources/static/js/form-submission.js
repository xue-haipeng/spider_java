/**
 * Created by xuehaipeng on 2017/7/14.
 */
function transToMenuId(menuNum) {
    switch (menuNum) {
        case menuNum >0:
    }
}

$(function () {
    var url = $("#url").val();
    var menuNum = $("ul:eq(1) li.selected").index();
    var startIndex = $("#startIndex").val();
    var endIndex = $("#endIndex").val();
    var extractArea = $("#extractArea").val();
    var linkPosition = $("#linkPosition").val();
    var title = $("#title").val();
    var title2 = $("#title2").val();
    var pubDate1 = $("#pubDate1").val();
    var pubDate2 = $("#pubDate2").val();
    var pubDate3 = $("#pubDate3").val();
    var keyword = $("#keyword").val();
    var content = $("#content").val();
    var content2 = $("#content2").val();
    var exFirst = $("#exFirst").prop("checked");
    var exLast = $("#exLast").prop("checked");
    var excluded2 = $("#excluded2").val();
    $("#submit").click(function () {
        $.post("/article", {"url": url, ""})
    })
});