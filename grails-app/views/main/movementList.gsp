<%@ page import="java.text.DecimalFormat" %>
<g:render template="/commons/header"/>
<% def v = new Date().format("yyyyMMddHHmmss"); %>
<%@ page import="com.cuentasclaras.User" %>
<% User userLogged = grailsApplication.mainContext.getBean("loginService").getUserLogged(); %>

<style>
.table_search tr {
    height: auto;
}

.table_search tr td {
    width: 100px;
}
</style>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Movimientos</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>

<!-- /.row -->
<div class="row">
    <div id="movement_list" class="col-lg-12 col-md-6">
        <button type="button" class="btn btn-primary btn-bookin-add"
                style="float: left;margin-top: -10px;margin-left: 10px;display: none">
            <i class="fa fa-search"></i>
        </button>

        <div style="margin-top: -10px;padding-bottom: 12px;text-align: right;">
            <a type="button" class="btn btn-success btn-bookin-add"
               href="/movement/create"
               data-toggle="tooltip" data-placement="bottom"
               data-original-title="Crear un nuevo movimiento">
                <i class="fa fa-plus"></i> Nuevo
            </a>
        </div>

        <div id="search_content" style="background-color: #e0e0e0; padding: 10px;margin-bottom: 10px;">
            <form>
                <div id="search_custom_filters">
                    <div style="display: inline-table;">
                        <span>Fecha desde</span><br>
                        <span>
                            <input type="text" format="dd/MM/yyyy" id="dateIni" name="dateIni" class="form-control datepicker" value="${params.dateIni}" readonly style="background-color: white;width: 100px; text-align: center;">
                        </span>
                    </div>

                    <div style="display: inline-table;">
                        <span>Fecha hasta</span><br>
                        <span>
                            <input type="text" format="dd/MM/yyyy" id="dateEnd" name="dateEnd" class="form-control datepicker" value="${params.dateEnd}" readonly style="background-color: white;width: 100px; text-align: center;" >
                        </span>
                    </div>

                    <div style="display: inline-table;">
                        <span>Filtro</span><br>
                        <span>
                            <input type="text" id="search" name="search" class="form-control" value="${params.search}" style="width: 200px;">
                        </span>
                    </div>
                </div>

                <div id="search_perdiod_btns">
                    <input type="hidden" id="filter_perdiod" name="filter_perdiod" value="${params.filter_perdiod ?: "today"}"/>
                    <a class="btn btn-primary btn-filter-period"
                       id="lastmonth"
                       data-filter="lastmonth"
                       data-toggle="tooltip" data-placement="bottom"
                       data-original-title="Ver movimientos del mes anterior">Mes anterior
                    </a>
                    <a class="btn btn-primary btn-filter-period"
                       id="thismonth"
                       data-filter="thismonth"
                       data-toggle="tooltip" data-placement="bottom"
                       data-original-title="Ver movimientos de este mes">Este mes
                    </a>
                    <a class="btn btn-primary btn-filter-period"
                       id="thisweek"
                       data-filter="thisweek"
                       data-toggle="tooltip" data-placement="bottom"
                       data-original-title="Ver movimientos de esta semana">Esta semana
                    </a>
                    <a class="btn btn-primary btn-filter-period"
                       id="today"
                       data-filter="today"
                       data-toggle="tooltip" data-placement="bottom"
                       data-original-title="Ver movimientos de hoy">Hoy
                    </a>
                </div>

                <div style="text-align: right;border-top: 1px solid grey;padding-top: 10px;">
                    <button id="btn-clear"
                            type="button" class="btn btn-danger"
                            data-toggle="tooltip" data-placement="bottom"
                            data-original-title="Borrar búsqueda">
                        <i class="fa fa-trash"></i> Borrar
                    </button>
                    <button id="btn-search"
                            type="submit" class="btn btn-primary"
                            data-toggle="tooltip" data-placement="bottom"
                            data-original-title="Buscar">
                        <i class="fa fa-search"></i> Buscar
                    </button>
                </div>
            </form>
        </div>

        <div class="panel">
            <div class="table-responsive">
                <table id="tableMovs" class="table table-hover" width="100%" style="margin-top: 0px;margin-bottom: 0px">
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
                                            ${mov.detail}<br/>
                                            ${mov.userToDisplay}
                                        </div>

                                        <div class="buttons">
                                            <g:if test="${mov.type.id == 2}">
                                                <button class="btn btn-primary" type="button"
                                                        onclick="movementListController.showDetails(${mov.id})"
                                                        data-toggle="tooltip" data-placement="bottom"
                                                        data-original-title="Ver detalle">
                                                    <i class="fa fa-gears"></i>
                                                </button>
                                            </g:if>
                                            <g:if test="${mov.user.id == userLogged.id}">
                                                <button class="btn btn-primary" type="button"
                                                        onclick="movementListController.edit(${mov.id})"
                                                        data-toggle="tooltip" data-placement="bottom"
                                                        data-original-title="Editar">
                                                    <i class="fa fa-pencil"></i>
                                                </button>
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
    movementListController.userInfo.id = "${userLogged.id}";
</script>

<g:render template="/commons/footer"/>
