/**
 * Created by xuehaipeng on 2017/6/25.
 */
var currentShowCity=0;
$(function(){
    $("#province").change(function(){
        $("#province option").each(function(i,o){
            if($(this).attr("selected"))
            {
                $(".city").hide();
                $(".city").eq(i).show();
                currentShowCity=i;
            }
        });
    });
    $("#province").change();
});
function getSelectValue(){
    alert("1级="+$("#province").val());

    $(".city").each(function(i,o){

        if(i == currentShowCity){
            alert("2级="+$(".city").eq(i).val());
        }
    });
}