var movementDetailController = {

    MOV_INFO: {
        date: ""
    },

    init: function () {
        console.log("movementDetailController.init()");
        jQuery("#type").change(function () {
            if (jQuery(this).find(":selected").val() != 1) {
                jQuery("div#friends").show();
            } else {
                jQuery("div#friends").hide();
            }
        });
        if(movementDetailController.MOV_INFO.date != ""){
            jQuery("[name=date]").datepicker({
                date: movementDetailController.MOV_INFO.date
            });
        }
    },

    save: function () {
        var args = jQuery("#movementDetail").serializeArray();
        jQuery(args).each(function (index, item) {
            if (item.name == "amount") {
                item.value = item.value.replace(".", ",")
            }
        });
        jQuery("#buttons").hide();
        Rest.doPost("/movement/save", args, function (data) {
            if (data.status == 200) {
                location = "/movement/list"
            } else {
                jQuery("#errorMessage").html(data.responseJSON.response.message);
            }
            jQuery("#buttons").show();
        })
    },

    clickTag: function (id) {
        var tag = "#tag_" + id;
        if(jQuery(tag).hasClass("btn-primary")){
            jQuery(tag).removeClass("btn-primary").addClass("btn-success");
            jQuery("#tags_selected").append("<input name='tags' value='"+id+"' id='"+id+"'/>");
        } else {
            jQuery(tag).removeClass("btn-success").addClass("btn-primary");
            jQuery("#tags_selected #"+id).remove();
        }
    },
}

jQuery(document).ready(movementDetailController.init);
