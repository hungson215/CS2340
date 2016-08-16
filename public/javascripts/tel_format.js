$(document).ready(function(){
    $("[data-toggle='tooltip']").tooltip({title:"Digit only",delay:{show:50,hide:50},trigger:"focus"});
    $("#profile-phone").focus(function () {
        $(this).val("");
    });
    $('#profile-phone').keyup(function (event) {
        if(event.which < 96 || event.which > 105 || $(this).val().length > 13) {
            var val = $(this).val();
            $(this).val(val.substring(0,val.length - 1));
        } else {
            var str = $(this).val();
            switch(str.length) {
                case 1:
                    str = "(" + str;
                    break;
                case 4:
                    str = str + ")";
                    break;
                case 8:
                    str = str + "-";
                    break;
                default:
                    return;
            }
            $(this).val(str);
        }
    });
});
