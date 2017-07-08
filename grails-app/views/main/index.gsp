<g:render template="/commons/header"/>
<% def v = new Date().format("yyyyMMddHHmmss"); %>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Inicio</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>

<!-- /.row -->
<div class="row">
    <g:render template="/commons/items/notifications"/>

    <div class="col-lg-12 col-md-6">
        <g:if test="${items.status == org.apache.http.HttpStatus.SC_OK}">
            <g:each in="${items.response}" var="item">

                <div class="panel panel-${item.amount >= 0 ? "green" : "red"}">
                    <div class="panel-heading">
                        <div class="row"
                             data-toggle="tooltip" data-placement="bottom"
                             data-original-title="<g:if test="${item.amount != 0}">${item.amount >= 0 ? "le debo" : "me debe"}</g:if>">
                            <div class="col-xs-3">
                                <i class="fa fa-signal fa-5x"></i>
                            </div>

                            <div class="col-xs-9 text-right">
                                <div class="huge">
                                    $ ${new java.text.DecimalFormat("###,##0.00").format(item.amount)}
                                </div>

                                <div>
                                    ${item.user.name}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </g:each>
        </g:if>
        <g:else>
            <div>
                No posee deudores ni acredores
            </div>
        </g:else>
    </div>
</div>

<script src="/js/controllers/indexController.js?v=${v}"></script>
<script type="text/javascript">
    console.log("indexController()");
</script>

<g:render template="/commons/footer"/>
