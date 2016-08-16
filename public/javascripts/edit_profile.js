/**
 * Control styling
 */
$(document).ready(function () {
    $('.profile-control').hover(function(){
        $(this).css("border","#373BFF");
        $(this).css("box-shadow","0 0 15px #373BFF");
        $(this).css("border-radius", "5px");
    },function () {
        $(this).css("border","none");
        $(this).css("box-shadow","none");

    });
    $('.profile-control').focus(function () {
        if($('#save-button').prop("disabled")) {
            $('#save-button').prop("disabled",false);
        }
    });
});