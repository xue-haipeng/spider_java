/**
 * Created by xuehaipeng on 2017/7/14.
 */
function transToMenuId(menuNum) {
    switch (menuNum) {
        case 1:
            return 8;
            break;
        case 2:
            return 9;
            break;
        case 3:
            return 10;
            break;
        case 4:
            return 11;
            break;
        case 5:
            return 12;
            break;
        case 6:
            return 13;
            break;
        case 7:
            return 14;
            break;
        case 8:
            return 15;
            break;
        case 9:
            return 40;
            break;
        case 10:
            return 42;
            break;
        case 11:
            return 16;
            break;
        case 12:
            return 17;
            break;
        case 13:
            return 18;
            break;
        case 14:
            return 19;
            break;
        case 15:
            return 20;
            break;
        case 16:
            return 41;
            break;
        case 17:
            return 43;
            break;
        case 18:
            return 44;
            break;
        case 19:
            return 21;
            break;
        case 20:
            return 22;
            break;
        case 21:
            return 23;
            break;
        case 22:
            return 24;
            break;
        case 23:
            return 25;
            break;
        case 24:
            return 26;
            break;
        case 25:
            return 27;
            break;
        case 26:
            return 28;
            break;
        case 27:
            return 29;
            break;
        case 28:
            return 30;
            break;
        case 29:
            return 31;
            break;
        case 30:
            return 32;
            break;
        case 31:
            return 33;
            break;
        case 32:
            return 34;
            break;
        case 33:
            return 35;
            break;
        case 34:
            return 36;
            break;
        case 35:
            return 37;
            break;
        case 36:
            return 38;
            break;
        case 37:
            return 39;
            break;
        default:
            console.log("");
            return "";
    }
}

$(function () {
    $("#submit").click(function () {
        var url = $("#url").val();
        var menuNum = $("ul:eq(1) li.selected").index()==-1 ? $("ul:eq(1) li.active").index() : $("ul:eq(1) li.selected").index();
        var menuId = transToMenuId(menuNum);
        var startIndex = parseInt($("#startIndex").val());
        var endIndex = parseInt($("#endIndex").val());
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
        $.post("/article", {"url": url, "menuId": menuId, "startIndex": startIndex, "endIndex": endIndex, "extractArea": extractArea,
        "linkPosition": linkPosition, "title": title, "title2": title2, "pubDate1": pubDate1, "pubDate2": pubDate2, "pubDate3": pubDate3,
        "keyword": keyword, "content": content, "content2": content2, "exFirst": exFirst, "exLast": exLast, "excluded2": excluded2}, function (data) {
            console.log(data);
        })
    })
});