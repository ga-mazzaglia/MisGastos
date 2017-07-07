var movementListController = {

    userInfo: {
        id: undefined
    },

    init: function () {
        console.log("movementListController.init()");
        jQuery(".row-mov").click(function () {
            var id = jQuery(this).attr("id");
            jQuery("#btns_" + id).toggle();
        });
        movementListController.initSearchBox();
    },

    edit: function (id) {
        location = "/movement/edit/" + id;
    },

    delete: function (id) {
        Utils.showConfirm("Eliminar", "Está seguro de continuar?", "success", function () {
            Rest.doPost("/movement/delete", {id: id}, function (data) {
                if (data.status == 200) {
                    location = "/movement/list"
                } else {
                    jQuery("#errorMessage").html(data.responseJSON.response.message);
                }
                jQuery("#buttons").show();
            });
        });
    },

    showDetails: function (id) {
        Rest.doGet("/movement/" + id, function (data) {
            console.log(data.response);
            var userEquals = "";
            if (data.status == 200) {
                var html = "<div>";
                var quantity = data.response.users.length + 1;
                var amount = parseFloat(data.response.amount / quantity).toFixed(2);

                if (quantity != 0) {
                    userEquals = movementListController.userInfo.id == data.response.user.id ? "my_part" : "";
                    html += "   <div class='row_mov_part "+userEquals+"'>";
                    html += "       <div style='float: left;'>" + data.response.user.name + "</div>";
                    html += "       <div style='float: right;'>$ " + amount + "</div>";
                    html += "   </div>";
                    html += "   <div style='clear: both'></div>";
                }
                jQuery(data.response.users).each(function (index, item) {
                    userEquals = movementListController.userInfo.id == item.id ? "my_part" : "";
                    html += "   <div class='row_mov_part "+userEquals+"'>";
                    html += "       <div style='float: left;'>" + item.name + "</div>";
                    html += "       <div style='float: right;'>$ " + amount + "</div>";
                    html += "   </div>";
                    html += "   <div style='clear: both'></div>";
                });
                if (quantity != 0) {
                    html += "   <div class='row_mov_part row_mov_part_total'>";
                    html += "       <div style='float: left;'>TOTAL</div>";
                    html += "       <div style='float: right;'>$ " + parseFloat(data.response.amount).toFixed(2) + "</div>";
                    html += "   </div>";
                    html += "   <div style='clear: both'></div>";
                    html += "</div>";
                    Utils.showAlert("Detalle", html)
                }
            }
        })
    },

    showSearchBox: function () {
        jQuery("#search_content").toggle();
        jQuery("#search").focus();
    },

    initSearchBox: function () {
        var showSearchBox = false;

        var searchValue = jQuery("#search").val();
        var dateIni = jQuery("#dateIni").val();
        var dateEnd = jQuery("#dateEnd").val();

        if (searchValue != "") {
            showSearchBox = true;
        }
        jQuery(".btn-filter-period").removeClass("btn-success");
        jQuery(".btn-filter-period").addClass("btn-primary");
        jQuery(".btn-filter-period").click(function () {
            jQuery("#dateIni").val("");
            jQuery("#dateEnd").val("");
            jQuery("#filter_perdiod").val(jQuery(this).attr("data-filter"));
            jQuery("#btn-search").click();
        });
        jQuery("#btn-clear").click(function () {
            location = "/movement/list"
        });
        var filterPerdiod = Utils.getUrlParam("filter_perdiod");
        if (filterPerdiod != null && filterPerdiod && dateIni == "" && dateEnd == "") {
            jQuery(".btn-filter-period").removeClass("btn-success");
            jQuery(".btn-filter-period").addClass("btn-primary");
            jQuery(".btn-filter-period[data-filter=" + filterPerdiod + "]").addClass("btn-success");
            showSearchBox = true;
        }
        if (jQuery("#dateIni").val() || jQuery("#dateEnd").val()) {
            showSearchBox = true;
        }
        if (!showSearchBox) {
            jQuery(".btn-filter-period[data-filter=today]").addClass("btn-success");
        }
    },

}

jQuery(document).ready(movementListController.init);
