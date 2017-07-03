var movementDetailController = {

    init: function () {
        console.log("movementDetailController.init()");
        jQuery("#type").change(function () {
            if(jQuery(this).find(":selected").val() == 2){
                jQuery("div#friends").show();
            } else {
                jQuery("div#friends").hide();
            }
        });
    },
}

jQuery(document).ready(movementDetailController.init);
