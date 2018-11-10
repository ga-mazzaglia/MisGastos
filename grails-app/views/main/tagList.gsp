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
        <h1 class="page-header">Tags</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>

<!-- /.row -->
<div class="row">
    <div id="tag_list" class="col-lg-12 col-md-6">
        <button type="button" class="btn btn-primary btn-bookin-add"
                style="float: left;margin-top: -10px;margin-left: 10px;display: none">
            <i class="fa fa-search"></i>
        </button>

        <div style="margin-top: -10px;padding-bottom: 12px;text-align: right;">
            <a type="button" class="btn btn-success btn-bookin-add"
               href="/tag/create"
               data-toggle="tooltip" data-placement="bottom"
               data-original-title="Crear un nuevo tag">
                <i class="fa fa-plus"></i> Nueva
            </a>
        </div>


        <div class="table-responsive">
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>Descripci&oacute;n</th>
                    <th width="50" class="center">Orden</th>
                    <th width="100" class="center">Opciones</th>
                </tr>
                </thead>
                <tbody>
                <g:if test="${tags.size() == 0}">
                    <tr>
                        <td class="center" colspan="3">no hay tag</td>
                    </tr>
                </g:if>
                <g:each in="${tags}" var="tag" status="i">
                    <tr class="row-tag gradeA ${i % 2 ? "odd" : "even"}" tag-id="${tag.id}">
                        <td>
                            ${tag.detail}
                        </td>
                        <td class="center">
                            ${tag.position}
                        </td>
                        <td class="center">
                            <a class="btn btn-danger btn-delete-tag" type="button"
                               href="/tag/remove/${tag.id}"
                               data-toggle="tooltip" data-placement="bottom"
                               data-original-title="Eliminar">
                                <i class="fa fa-minus"></i>
                            </a>
                            <a class="btn btn-primary" type="button"
                               href="/tag/edit/${tag.id}"
                               data-toggle="tooltip" data-placement="bottom"
                               data-original-title="Editar">
                                <i class="fa fa-pencil"></i>
                            </a>
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

<script src="/js/controllers/tagListController.js?v=${v}"></script>
<script type="text/javascript">
    console.log("tagListController()");
</script>

<g:render template="/commons/footer"/>
