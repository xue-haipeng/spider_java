/**
 * Created by xuehaipeng on 2017/7/14.
 */
var currCategory=0;
$(function(){
    $("select").click(function () {
        $("li>span").css("font-size", "1rem");
    });

    $("#topMenu").change(function(){
        $("li>span").css("font-size", "1rem");
        var category = $(this).val();
        $("ul:eq(1) li:gt(0)").addClass("hide");
        switch (category) {
            case "1":
                for (var i=1; i<11; i++) {
                    $("ul:eq(1) li:eq("+i+")").removeClass("hide");
                }
                break;
            case "2":
                for (var i=11; i<19; i++) {
                    $("ul:eq(1) li:eq("+i+")").removeClass("hide");
                }
                break;
            case "3":
                $("ul:eq(1) li:eq("+19+")").removeClass("hide");
                $("ul:eq(1) li:eq("+20+")").removeClass("hide");
                break;
            case "4":
                for (var i=21; i<24; i++) {
                    $("ul:eq(1) li:eq("+i+")").removeClass("hide");
                }
                break;
            case "5":
                for (var i=24; i<28; i++) {
                    $("ul:eq(1) li:eq("+i+")").removeClass("hide");
                }
                break;
            case "6":
                for (var i=28; i<32; i++) {
                    $("ul:eq(1) li:eq("+i+")").removeClass("hide");
                }
                break;
            case "7":
                for (var i=32; i<38; i++) {
                    $("ul:eq(1) li:eq("+i+")").removeClass("hide");
                }
                break;
            default:
                console.log("failed to judge category");
        }
    });
    $("#topMenu").change();
});