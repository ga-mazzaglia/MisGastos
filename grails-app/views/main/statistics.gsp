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

<!-- /.row -->
<div class="row">
    <div id="tag_list" class="col-lg-12 col-md-6">
        <div id="search_content" style="background-color: #e0e0e0; padding: 10px;margin-bottom: 10px;display: ">
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
                <g:if test="${tags.size() == 0}">
                    <tr>
                        <td class="center" colspan="3">no hay movimientos</td>
                    </tr>
                </g:if>
                <g:each in="${tags}" var="tag" status="i">
                    <tr class="row-mov gradeA ${i % 2 ? "odd" : "even"}">
                        <td>
                            <a href="/movement/list?dateIni=${params.dateIni}&dateEnd=${params.dateEnd}&search=&filter_perdiod=thismonth&tags=${tag.tagId}">${tag.tagName}</a>
                        </td>
                        <td class="right">
                            $ ${new java.text.DecimalFormat("###,##0.00").format(tag.amount)}
                        </td>
                        <td class="center">
                            ${new java.text.DecimalFormat("###,##0.0").format(tag.parte)} %
                        </td>
                    </tr>
                </g:each>
                <tr class="row-mov gradeA" style="font-weight: bold;background-color: lightgray;">
                    <td>
                        TOTAL
                    </td>
                    <td class="center" colspan="2">
                        $ ${new java.text.DecimalFormat("###,##0.00").format(total)}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <!-- /.table-responsive -->
    </div>
</div>
</div>

<script src="/js/controllers/tagListController.js?v=${v}"></script>
<script type="text/javascript">
    console.log("tagListController()");
</script>

<g:render template="/commons/footer"/>
