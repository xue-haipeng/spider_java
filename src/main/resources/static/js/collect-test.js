/**
 * Created by xuehaipeng on 2017/7/2.
 */
$(document).ready(function() {
    $('select').material_select();

    $('#test').click(function(){
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
        var content = $("#content").val();
        var exclude1 = $("#excluded1").val();
        var exclude2 = $("#excluded2").val();
        var data = {"url": url, "startIndex": startIndex, "endIndex": endIndex, "extractArea": extractArea,
            "linkPosition": linkPosition, "menuId": menuId, "title": title, "pubDate1": pubDate1, "pubDate2": pubDate2,
            "pubDate3": pubDate3, "keyword": keyword, "content": content, "exclude1": exclude1, "exclude2": exclude2, "data": data};

        $.post("/testFetch", data, function(data,status){
            // alert(data.toString());
            $("#modal_title").text(data.title);
            $("#modal_date").text(data.originDate);
            $("#modal_keyword").text(data.keyword);
            $("#modal_content").html(data.content);
            $('#testModal').modal('show')
        });
    });
});