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
        var args = jQuery("#movementDetail").serializeArray();
        jQuery(args).each(function (index, item) {
            console.log(index, item);
            if(item.name == "amount"){
                item.value = item.value.replace(".", ",")
            }
        });
        jQuery("#buttons").hide();
        Rest.doPost("/movement/save", args, function (data) {
            if(data.status == 200){
                location = "/movement/list"
            } else {
                jQuery("#errorMessage").html(data.responseJSON.response.message);
            }
            jQuery("#buttons").show();
        })
    }
}

jQuery(document).ready(movementDetailController.init);
