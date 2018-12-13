<g:render template="/commons/header"/>

<%@ page import="java.text.DecimalFormat" %>
<% def v = new Date().format("yyyyMMddHHmmss") %>

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
        <h1 class="page-header">Estad&iacute;sticas</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>

<div class="row">
    <div id="tag_list" class="col-lg-12 col-md-6">

        <div style="margin-bottom: 15px;">
            Ac&aacute; solo se mostrar&aacute;n <strong>gastos personales</strong> y <strong>gastos compartidos</strong>(la parte que te corresponde)
        </div>

        <div id="search_content" style="background-color: #e0e0e0; padding: 10px;margin-bottom: 10px;">
            <form>
                <div id="search_custom_filters">
                    <div style="display: inline-table;">
                        <span>Fecha desde</span><br>
                        <span>
                            <input type="text" format="yyyy-MM-dd" id="dateIni" name="dateIni"
                                   class="form-control datepicker" value="${params.dateIni}" readonly
                                   style="background-color: white;width: 150px; text-align: center;">
                        </span>
                    </div>

                    <div style="display: inline-table;">
                        <span>Fecha hasta</span><br>
                        <span>
                            <input type="text" format="dd/MM/yyyy" id="dateEnd" name="dateEnd"
                                   class="form-control datepicker" value="${params.dateEnd}" readonly
                                   style="background-color: white;width: 150px; text-align: center;">
                        </span>
                    </div>
                </div>
                <br/>

                <div style="text-align: right;border-top: 1px solid grey;padding-top: 10px;">
                    <a id="btn-clear"
                       type="button" class="btn btn-danger"
                       href="/statistics"
                       data-toggle="tooltip" data-placement="bottom"
                       data-original-title="Borrar búsqueda">
                        <i class="fa fa-trash"></i> Borrar
                    </a>
                    <button id="btn-search"
                            type="submit" class="btn btn-primary"
                            data-toggle="tooltip" data-placement="bottom"
                            data-original-title="Buscar">
                        <i class="fa fa-search"></i> Buscar
                    </button>
                </div>
            </form>
        </div>

        <div class="table-responsive">
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>Descripci&oacute;n</th>
                    <th width="110" class="center">Monto</th>
                    <th width="110" class="center">Parte</th>
                </tr>
                </thead>
                <tbody>
                <g:if test="${percentage.tags.size() == 0}">
                    <tr>
                        <td class="center" colspan="3">no hay movimientos</td>
                    </tr>
                </g:if>
                <g:each in="${percentage.tags}" var="tag" status="i">
                    <tr class="row-mov gradeA ${i % 2 ? "odd" : "even"}">
                        <td>
                            <a href="/movement/list?dateIni=${params.dateIni}&dateEnd=${params.dateEnd}&search=&filter_perdiod=thismonth&tags=${tag.tagId}&types=1&types=2">${tag.tagName}</a>
                        </td>
                        <td class="right">
                            $ ${new java.text.DecimalFormat("###,##0.00").format(tag.amount)}
                        </td>
                        <td class="center">
                            ${new java.text.DecimalFormat("###,##0.0").format(tag.parte)} %
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>
        <!-- /.table-responsive -->
    </div>
</div>
<!-- /.row -->

<div class="row">
    <div class="col-lg-12 col-md-6">
        <div class="panel panel-default">
            <div class="panel-heading">
                <i class="fa fa-bar-chart-o fa-fw"></i> Historial de gastos anual
                <div class="pull-right hidden">
                    <div class="btn-group">
                        <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                            Año
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu pull-right" role="menu">
                            <li>
                                <a href="#">2018</a>
                            </li>
                            <li>
                                <a href="#">2019</a>
                            </li>
                            <li>
                                <a href="#">2020</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <div id="morris-area-chart"></div>
            </div>
            <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
    </div>
</div>
<!-- /.row -->

<script src="/js/controllers/tagListController.js?v=${v}"></script>
<g:render template="/commons/footer"/>

<div id="chartValues" class="hidden">${byYear}</div>
<div id="labelValues" class="hidden">${tags}</div>

<script type="text/javascript">
    console.log("tagListController()");
    $(function () {
        Morris.Area({
            element: 'morris-area-chart',
            data: JSON.parse(jQuery("#chartValues").html()),
            lineColors: ['#819C79', '#fc8710', '#FF6541', '#A4ADD3', '#766B56'],
            xkey: 'period',
            ykeys: JSON.parse(jQuery("#labelValues").html()),
            labels: JSON.parse(jQuery("#labelValues").html()),
            xLabels: 'months',
            xLabelAngle: 45,
            hideHover: 'auto',
            behaveLikeLine: true,
            xLabelFormat: function (d) {
                var months = [
                    "Enero",
                    "Febrero",
                    "Marzo",
                    "Abril",
                    "Mayo",
                    "Junio",
                    "Julio",
                    "Agosto",
                    "Septiembre",
                    "Octubre",
                    "Noviembre",
                    "Diciembre"];

                return months[d.getMonth()];
            },
            resize: true
        });
    });
</script>
