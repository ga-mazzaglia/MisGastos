<%@ page import="java.text.DecimalFormat" %>
<g:render template="/commons/header"/>
<% def v = new Date().format("yyyyMMddHHmmss"); %>
<%@ page import="com.cuentasclaras.User" %>
<% User userLogged = grailsApplication.mainContext.getBean("loginService").getUserLogged(); %>

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
                        <g:if test="${movements.size() == 0}">
                            <tr>
                                <td class="center">no hay movimientos</td>
                            </tr>
                        </g:if>
                        <g:each in="${movements}" var="mov">
                            <tr class="row-mov" id="${mov.id}">
                                <td style="color: ${mov.color};">
                                    <div>
                                        <div style="float: left">
                                            ${mov.date.format("dd-MM-yyyy")}
                                        </div>

                                        <div style="float: right">
                                            $ ${new java.text.DecimalFormat("###,##0.00").format(mov.amount)}
                                            <g:if test="${mov.users.size()}">(*)</g:if>
                                        </div>

                                        <div style="clear: both"></div>

                                        <div style="float: left;margin-top: 5px;">
                                            ${mov.detail}<br />
                                            ${mov.userToDisplay}
                                        </div>
                                        <div style="text-align: right;height: 40px;margin-top: 5px;padding-top: 2px;">
                                            <g:if test="${mov.type.id == 1}">
                                                <button class="btn btn-primary" type="button"
                                                        onclick="movementListController.showDetails(${mov.id})"
                                                        data-toggle="tooltip" data-placement="bottom"
                                                        data-original-title="Ver detalle">
                                                    <i class="fa fa-gears"></i>
                                                </button>
                                            </g:if>
                                            <g:if test="${mov.user.id == userLogged.id}">
                                                <button class="btn btn-danger" type="button"
                                                        onclick="movementListController.delete(${mov.id})"
                                                        data-toggle="tooltip" data-placement="bottom"
                                                        data-original-title="Eliminar">
                                                    <i class="fa fa-minus"></i>
                                                </button>
                                            </g:if>
                                        </div>
                                    </div>

                                    <div style="clear: both"></div>
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
