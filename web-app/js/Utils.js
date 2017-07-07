/**
 * Created by gmazzaglia on 21/7/16.
 */
var Utils = {
    showAlert: function (title, message) {
        jQuery("#alertModal #myModalLabel").html(title);
        jQuery("#alertModal #myModalText").html(message);

        jQuery("#alertModal").on('shown.bs.modal', function () {
            $('#alertModal #btn_acept').focus();
        });
        jQuery("#alertModal").modal("show").on('hide.bs.modal', function (e) {

        })
    },

    showConfirm: function (title, message, type, callback) {
        jQuery("#confirmModal #fa-icon").addClass("fa-" + type);
        jQuery("#confirmModal #myModalLabel").html(title);
        jQuery("#confirmModal #myModalText").html(message);
        jQuery("#confirmModal").on('shown.bs.modal', function () {
            $('#confirmModal #btn-modal-confirm-ok').focus();
        });
        jQuery("#confirmModal").modal("show");
        jQuery("#confirmModal #btn-modal-confirm-ok").click(function (e) {
            e.preventDefault()
            if (callback != undefined) {
                e.preventDefault()
                callback(e);
                jQuery("#confirmModal").modal("hide");
            }
        })
    },

    replaceAll: function (text, search, repl) {
        return text.replace(new RegExp(search, "g"), repl)
    },

    addMinutesToDate: function (date, minutes) {
        return new Date(date.getTime() + (minutes * 60000));
    },

    getUrlParam: function (param) {
        var url = new URL(location.href);
        return url.searchParams.get(param);
    },

    isPhone: function () {
        if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
            return true;
        }
        return false;
    }
};