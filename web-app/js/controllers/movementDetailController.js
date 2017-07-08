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
            jQuery("#friends_added").html("");
            jQuery(".btn-friend").removeClass("btn-success");
            jQuery(".btn-friend").addClass("btn-default");
        });
        if (movementDetailController.MOV_INFO.date != "") {
            if(!Utils.isPhone()) {
                jQuery("[name=date]").datepicker({
                    date: movementDetailController.MOV_INFO.date
                });
            }
        }
        //movementDetailController.initFriends();
    },

    addFriend: function (friendId) {
        var selector = "#btn-friend_" + friendId;
        console.log(jQuery("#type").find(":selected").val());
        console.log(selector);
        if (jQuery("#type").find(":selected").val() == 2) {
            if (jQuery(selector).hasClass("btn-success")) {
                jQuery(selector).removeClass("btn-success");
                jQuery(selector).addClass("btn-default");
            } else {
                console.log("selected");
                jQuery(selector).removeClass("btn-default");
                jQuery(selector).addClass("btn-success");
            }
        } else {
            jQuery(".btn-friend").removeClass("btn-success");
            jQuery(".btn-friend").addClass("btn-default");
            jQuery(selector).removeClass("btn-default");
            jQuery(selector).addClass("btn-success");
        }

        jQuery("#friends_added").html("");
        jQuery(".btn-friend.btn-success").each(function (index, item) {
            var friendAdded = jQuery(item).attr("data-friend_id");
            jQuery("#friends_added").append("<input name='friends' value='"+friendAdded+"' />");
        });
    },

    initFriends: function () {
        jQuery(".btn-friend").click(function () {
            var friendId = jQuery(this).attr("data-friend_id");
            if (jQuery("#type").find(":selected").val() == 2) {
                if (jQuery(this).hasClass("btn-success")) {
                    jQuery(this).removeClass("btn-success");
                    jQuery(this).addClass("btn-default");
                } else {
                    jQuery(this).removeClass("btn-default");
                    jQuery(this).addClass("btn-success");
                }
            } else {
                jQuery(".btn-friend").removeClass("btn-success");
                jQuery(".btn-friend").addClass("btn-default");
                jQuery(this).removeClass("btn-default");
                jQuery(this).addClass("btn-success");
            }

            jQuery("#friends_added").html("");
            jQuery(".btn-friend.btn-success").each(function (index, item) {
                var friendAdded = jQuery(item).attr("data-friend_id");
                jQuery("#friends_added").append("<input name='friends' value='"+friendAdded+"' />");
            });
        });
    },

    save: function () {
        var args = jQuery("#movementDetail").serializeArray();
        jQuery(args).each(function (index, item) {
            if (item.name == "amount") {
                item.value = item.value.replace(".", ",")
            }
        });

        jQuery("#buttons").hide();
        jQuery("#errorMessage").html("").hide();
        jQuery("#successMessage").html("guardando..").show();

        Rest.doPost("/movement/save", args, function (data) {
            if (data.status == 200) {
                location = "/movement/list"
            } else {
                jQuery("#successMessage").html("").hide();
                jQuery("#errorMessage").html(data.responseJSON.response.message).show();
                jQuery("#buttons").show();
            }
        })
    },

    clickTag: function (id) {
        var tag = "#tag_" + id;
        if (jQuery(tag).hasClass("btn-primary")) {
            jQuery(tag).removeClass("btn-primary").addClass("btn-success");
            jQuery("#tags_selected").append("<input name='tags' value='" + id + "' id='" + id + "'/>");
        } else {
            jQuery(tag).removeClass("btn-success").addClass("btn-primary");
            jQuery("#tags_selected #" + id).remove();
        }
    },
}

jQuery(document).ready(movementDetailController.init);
