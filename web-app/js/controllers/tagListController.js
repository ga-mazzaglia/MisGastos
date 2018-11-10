var tagListController = {

    init: function () {
        console.log("tagListController.init()");
        jQuery(".btn-delete-tag").click(function () {
            if (!confirm("Esta seguro de borrar el tag?")) {
                return false
            }
        })
    },

}

jQuery(document).ready(tagListController.init);
