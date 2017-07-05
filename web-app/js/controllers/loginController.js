var loginController = {

    init: function () {

    },

    signIn: function () {
        var args = {
            username: jQuery("#username").val(),
            password: jQuery("#password").val(),
        };
        jQuery("#errorMessage").hide().html("");
        jQuery("#successMessage").html("verificando...").show();
        Rest.doPost("/signin", args, function (data) {
            jQuery("#errorMessage").hide().html("");
            jQuery("#successMessage").hide().html("");
            if(data.status != 200){
                jQuery("#errorMessage").html(data.responseJSON.response.message).show();
            } else {
                jQuery("#successMessage").html("entrando...").show();
                var url = new URL(location.href);
                var c = url.searchParams.get("back");
                location = c ? c : "/";
            }
        })
    },

}

jQuery(document).ready(loginController.init);
