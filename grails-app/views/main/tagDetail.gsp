<g:render template="/commons/header"/>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Tag</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>

<div class="row">
    <div class="col-lg-12">
        <div id="tag_detail" class="panel panel-default">
            <div class="panel-heading">
                Informaci&oacute;n
            </div>

            <div class="panel-body">
                <div class="row">
                    <div class="col-lg-12">
                        <form id="tagDetail" action="/tag/save" role="form"
                              method="POST">
                            <input type="hidden" name="id" value="${tag?.id}">

                            <!-- Detail -->
                            <div class="form-group">
                                <label>Detalle</label>
                                <input type="text" class="form-control"
                                       name="detail" value="${tag?.detail}"
                                       style="background-color: white;width: 100%">
                            </div>
                            <!-- Position -->
                            <div class="form-group">
                                <label>Posici&oacute;n</label>
                                <input type="text" class="form-control"
                                       name="position" value="${tag?.position}"
                                       style="background-color: white;width: 100%">
                            </div>

                            <!-- Enabled -->
                            <input type="hidden" name="enabled" value="${tag?.enabled ?: 'true'}"/>

                            <hr/>

                            <div id="successMessage"></div>

                            <div id="errorMessage"></div>

                            <div id="buttons">
                                <button type="submit" class="btn btn-success">Guardar</button>
                                <a href="/tag/list" class="btn btn-danger">Cancelar</a>
                            </div>
                        </form>
                    </div>
                </div>
                <!-- /.row (nested) -->
            </div>
            <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
    </div>
    <!-- /.col-lg-12 -->
</div>

<script src="/js/controllers/tagDetailController.js?v=${v}"></script>
<script type="text/javascript">
    console.log("tagDetailController()");
</script>

<g:render template="/commons/footer"/>
