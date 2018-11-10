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

<div style="display: none">
    <%
        Date date = new Date();
        use(groovy.time.TimeCategory) {
            date -= 3.hours;
        }
        Double total = 0;
    %>
</div>

<!-- /.row -->
<div class="row">
    <div id="movement_list" class="col-lg-12 col-md-6">
        <button type="button" class="btn btn-primary btn-bookin-add"
                style="float: left;margin-top: -10px;margin-left: 10px;display: none">
            <i class="fa fa-search"></i>
        </button>

        <div class="row" style="margin-left: 10px;margin-right: 10px;">
            <div style="margin-top: -10px;padding-bottom: 12px;float: left;">
                <a type="button" class="btn btn-primary"
                   onclick="jQuery('#search_content').toggle()"
                   data-toggle="tooltip" data-placement="bottom"
                   data-original-title="Crear un nuevo movimiento">
                    <i class="fa fa-search"></i> Buscar
                </a>
            </div>

            <div style="margin-top: -10px;padding-bottom: 12px;float: right;">
                <a type="button" class="btn btn-success btn-bookin-add"
                   href="/movement/create"
                   data-toggle="tooltip" data-placement="bottom"
                   data-original-title="Crear un nuevo movimiento">
                    <i class="fa fa-plus"></i> Nuevo
                </a>
            </div>
        </div>

        <div id="search_content" style="background-color: #e0e0e0; padding: 10px;margin-bottom: 10px;display: none">
            <form>
                <div id="search_custom_filters">
                    <div style="display: inline-table;">
                        <span>Fecha desde</span><br>
                        <span>
                            <input type="text" format="dd/MM/yyyy" id="dateIni" name="dateIni"
                                   class="form-control datepicker" value="${params.dateIni}" readonly
                                   style="background-color: white;width: 100px; text-align: center;">
                        </span>
                    </div>

                    <div style="display: inline-table;">
                        <span>Fecha hasta</span><br>
                        <span>
                            <input type="text" format="dd/MM/yyyy" id="dateEnd" name="dateEnd"
                                   class="form-control datepicker" value="${params.dateEnd}" readonly
                                   style="background-color: white;width: 100px; text-align: center;">
                        </span>
                    </div>

                    <div style="display: inline-table;">
                        <span>Filtro</span><br>
                        <span>
                            <input type="text" id="search" name="search" class="form-control" value="${params.search}"
                                   style="width: 200px;">
                        </span>
                    </div>
                </div>

                <div id="search_perdiod_btns">
                    <input type="hidden" id="filter_perdiod" name="filter_perdiod"
                           value="${params.filter_perdiod ?: com.cuentasclaras.commands.MovementListCommand.PERIOD_THISMONTH}"/>
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

                <div id="search_tags">
                    <%
                        List tagsFilter = [];
                        params.tags.each { tagsFilter << it.toLong() }
                    %>
                    <g:each in="${tags}" var="tag">
                        <a onclick="movementListController.clickTag(${tag.id});" id="tag_${tag.id}"
                           class="btn btn-${(tag.id in mov?.tags*.id) || (tag.id in tagsFilter) ? "success" : "primary"} btn-tag"
                           style="margin-bottom: 5px;"
                           tag-id="${tag.id}"
                           tag-detail="${tag.detail}">
                            <i class="fa fa-tag"></i> ${tag.detail}
                        </a>
                    </g:each>
                    <div id="tags_filters">
                        <g:each in="${params['tags']}" var="tag">
                            <input type="hidden" name="tags" value="${tag}">
                        </g:each>
                    </div>
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
                <table id="tableMovs"
                       class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline"
                       width="100%" style="margin-top: 0px;margin-bottom: 0px">
                    <tbody>
                    <g:if test="${movements.size() == 0}">
                        <tr>
                            <td class="center">no hay movimientos</td>
                        </tr>
                    </g:if>
                    <g:each in="${movements}" var="mov" status="i">
                        <%
                            Double amount = 0;
                            if (mov.users.size() == 0) {
                                amount += mov.amount
                            } else {
                                amount += (mov.amount / (mov.users.size() + 1))
                            }
                            total += amount
                        %>
                        <tr class="row-mov gradeA ${i % 2 ? "odd" : "even"} ${mov.tags*.id.join('_tag ')}_tag"
                            mov-id="${mov.id}"
                            mov-amount="${amount}">
                            <td style="color: ${mov.color};">
                                <div>
                                    <div style="float: left">
                                        ${mov.date.format("dd-MM-yyyy")}
                                    </div>

                                    <div style="float: right">
                                        $ ${new java.text.DecimalFormat("###,##0.00").format(mov.amount)}
                                    </div>

                                    <div style="clear: both"></div>

                                    <div style="float: left;margin-top: 5px;">
                                        <span title="#${mov.id}">${mov.detail}</span><br/>
                                        <g:if test="${mov.userToDisplay}">
                                            ${mov.userToDisplay}<br/>
                                        </g:if>
                                        <g:each in="${mov.tags}" var="tag">
                                            <span style="padding-right: 10px;color: grey;">
                                                <i style="color: grey" class="fa fa-tag"></i> ${tag.detail}
                                            </span>
                                        </g:each>
                                    </div>

                                    <div class="buttons">
                                        <g:if test="${mov.type.id == 2}">
                                            <button class="btn btn-primary" type="button"
                                                    onclick="movementListController.showDetails(${mov.id})">
                                                <i class="fa fa-list"></i>
                                            </button>
                                        </g:if>
                                        <g:if test="${mov.user.id == userLogged.id}">
                                            <button class="btn btn-danger" type="button"
                                                    onclick="movementListController.delete(${mov.id})"
                                                    data-toggle="tooltip" data-placement="bottom"
                                                    data-original-title="Eliminar">
                                                <i class="fa fa-minus"></i>
                                            </button>
                                            <button class="btn btn-primary" type="button"
                                                    onclick="movementListController.edit(${mov.id})"
                                                    data-toggle="tooltip" data-placement="bottom"
                                                    data-original-title="Editar">
                                                <i class="fa fa-pencil"></i>
                                            </button>
                                        </g:if>
                                        <button class="btn btn-default" type="button"
                                                onclick="movementListController.showModalAddTags(${mov.id}, ${mov.tags*.id})"
                                                data-toggle="tooltip" data-placement="bottom"
                                                data-original-title="Agregar tags">
                                            <i class="fa fa-tag"></i>
                                        </button>
                                    </div>
                                </div>

                                <div style="clear: both"></div>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>

                <table width="100%" class="table table-striped dataTable no-footer">
                    <tbody>
                    <tr>
                        <td width="200" style="text-align: left">
                            <strong>TOTAL:</strong>
                        </td>

                        <td style="text-align: right">
                            <strong>$ <span
                                    id="table-amount-total">${new java.text.DecimalFormat("###,##0.00").format(total)}</span>
                            </strong>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <!-- /.table-responsive -->
        </div>
    </div>
</div>

<g:render template="/commons/items/movementDetailTagModal"/>

<script src="/js/controllers/movementListController.js?v=${v}"></script>
<script type="text/javascript">
    console.log("movementListController()");
    movementListController.userInfo.id = "${userLogged.id}";
</script>

<g:render template="/commons/footer"/>
