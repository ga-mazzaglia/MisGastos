<g:render template="/commons/header"/>
<% def v = new Date().format("yyyyMMddHHmmss"); %>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Movimientos</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>

<!-- /.row -->
<div class="row">
    <div class="col-lg-12 col-md-6">
        <div style="margin-top: -10px;padding-bottom: 12px;padding-right: 10px;text-align: right;">
            <a type="button" class="btn btn-success btn-bookin-add"
               href="/movement/create"
               data-toggle="tooltip" data-placement="bottom"
               data-original-title="Crear un nuevo movimiento">
                <i class="fa fa-plus"></i> Nuevo
            </a>
        </div>

        <div class="panel">
            <div class="table-responsive">
                <table id="tableTurns" class="table table-hover" width="100%" style="margin-top: 0px;margin-bottom: 0px">
                    <tbody>
                        <g:each in="${[1, 2]}" var="turn">
                            <tr>
                                <td>
                                    <div style="float: left">
                                        <div style="float: left">
                                            NAFTA
                                        </div>
                                        <div style="float: right">
                                            $ 120.50
                                        </div>
                                        <div style="clear: both"></div>
                                        <div style="float: left">
                                            02-06-2017
                                        </div>
                                        <div style="float: right">

                                        </div>
                                    </div>
                                    <div style="float: right">
                                        <a type="button"
                                           href="/movement/create"
                                           data-toggle="tooltip" data-placement="bottom"
                                           data-original-title="Eliminar">
                                            <i class="fa fa-minus"></i>
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        </g:each>
                    </tbody>
                </table>
            </div>
            <!-- /.table-responsive -->
        </div>
    </div>
</div>

<script src="/js/controllers/movementListController.js?v=${v}"></script>
<script type="text/javascript">
    console.log("movementListController()");
</script>

<g:render template="/commons/footer"/>
