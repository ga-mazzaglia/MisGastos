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

    save: function () {
        console.log("save");
        var args = jQuery("#movementDetail").serializeArray();
        console.log(args);
        Rest.doPost("/movement/save", args, function (data) {
            console.log(data);
        })
    }
}

jQuery(document).ready(movementDetailController.init);
