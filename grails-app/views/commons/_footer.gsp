                <form id="sendData"></form>
            </div>
            <!-- /#page-wrapper -->

        </div>
        <!-- /#wrapper -->

            <g:render template="/commons/items/foot"/>
            <g:render template="/commons/items/modals"/>

        <script>
            jQuery(document).ready(function () {
                // tooltip
                $('[data-toggle=tooltip]').tooltip({
                    container: "body"
                })
                // popover
                $("[data-toggle=popover]").popover();

                if(Utils.isPhone()){
                    jQuery(".datepicker").attr("type", "date");
                } else {
                    // datepicker
                    jQuery(".datepicker").datepicker({
                        format: "yyyy-mm-dd",
                        orientation: "bottom",
                        autoclose: true,
                    });
                }

                jQuery(".alert .close").click(function () {
                    jQuery(this).parent(".alert").fadeOut();
                });

                setInterval(function () {
                    var messages = jQuery(".alert .close");
                    jQuery.each(messages, function (index, item) {
                        var sleep = index * 2000;
                        setTimeout(function () {
                            //jQuery(item).click();
                        }, sleep)
                    });
                }, 5000);
            });

            // MANEJO DEL MENU
            jQuery(document).ready(function () {
                var payroll = "${turnDate?.typePayroll?.id}";
                var menu = "${itemMenuActive?.replace("-", "")}";

                jQuery(".sidebar-nav ul#ul-payroll-type-" + payroll).parent().addClass("active");
                if(!jQuery(".sidebar-nav a#payroll-" + payroll + menu).hasClass("active")){
                    jQuery(".sidebar-nav ul#ul-payroll-type-" + payroll).attr("class", "nav nav-second-level collapse in");
                    jQuery(".sidebar-nav ul#ul-payroll-type-" + payroll).attr("aria-expanded", "true");
                    jQuery(".sidebar-nav a#payroll-" + payroll + menu).addClass("active");
                }
            })
        </script>
    </body>
</html>
