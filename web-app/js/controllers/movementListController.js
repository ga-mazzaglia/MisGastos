var movementListController = {

    init: function () {
        console.log("movementListController.init()");
        jQuery(".row-mov").click(function () {
            var id = jQuery(this).attr("id");
            jQuery("#btns_" + id).toggle();
        })
    },

    delete: function (id) {
        Rest.doPost("/movement/delete", {id: id}, function (data) {
            if (data.status == 200) {
                location = "/movement/list"
            } else {
                jQuery("#errorMessage").html(data.responseJSON.response.message);
            }
            jQuery("#buttons").show();
        })
    },

    showDetails: function (id) {
        Rest.doGet("/movement/" + id, function (data) {
            console.log(data.response);
            if (data.status == 200) {
                var html = "<div style='padding: 5px;'>";
                var quantity = data.response.users.length + 1;
                var amount = parseFloat(data.response.amount / quantity).toFixed(2);

                if(quantity != 0){
                    html += "   <div style='height: 30px;'>";
                    html += "       <div style='float: left;'>" + data.response.user.name + "</div>";
                    html += "       <div style='float: right;'>$ " + amount + "</div>";
                    html += "   </div>";
                    html += "   <div style='clear: both'></div>";
                }
                jQuery(data.response.users).each(function (index, item) {
                    html += "   <div style='height: 30px;'>";
                    html += "       <div style='float: left;'>" + item.name + "</div>";
                    html += "       <div style='float: right;'>$ " + amount + "</div>";
                    html += "   </div>";
                    html += "   <div style='clear: both'></div>";
                });
                if (quantity != 0) {
                    html += "   <div style='height: 30px;border-top: 1px dashed lightgray;font-weight: bold;padding-top: 10px;'>";
                    html += "       <div style='float: left;'>TOTAL</div>";
                    html += "       <div style='float: right;'>$ " + parseFloat(data.response.amount).toFixed(2) + "</div>";
                    html += "   </div>";
                    html += "   <div style='clear: both'></div>";
                    html += "</div>";
                    Utils.showAlert("Amigos", html)
                }
            }
        })
    }
}

jQuery(document).ready(movementListController.init);
