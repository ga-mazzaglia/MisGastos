<g:render template="/commons/header"/>

<% def v = new Date().format("yyyyMMddHHmmss"); %>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Movimiento</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                Informaci&oacute;n
            </div>

            <div class="panel-body">
                <div class="row">
                    <div class="col-lg-6">
                        <form id="movementDetail" action="javascript: movementDetailController.save();" role="form" method="POST">
                            <!-- fecha -->
                            <div class="form-group">
                                <input class="form-control datepicker" placeholder="Fecha" name="date">
                            </div>
                            <!-- descripcion -->
                            <div class="form-group">
                                <input class="form-control" placeholder="Descripción" autofocus name="detail" value="">
                            </div>
                            <!-- monto -->
                            <div class="form-group input-group">
                                <span class="input-group-addon"><i class="fa fa-dollar"></i>
                                </span>
                                <input type="text" class="form-control" placeholder="Importe" name="amount" value="">
                            </div>
                            <!-- tipo -->
                            <div class="form-group" id="type">
                                <label>Tipo</label>
                                <select class="form-control" name="type">
                                    <g:each in="${movementTypes}" var="type">
                                        <option value="${type.id}">${type.detail}</option>
                                    </g:each>
                                </select>
                            </div>
                            <!-- amigos -->
                            <div class="form-group" id="friends" style="display: none;">
                                <label>Amigos</label>
                            <br />
                                <g:each in="${friends}" var="friend">
                                    <label class="checkbox-inline">
                                        <input type="checkbox" value="${friend.id}" name="friends"> ${friend.name}
                                    </label>
                                    <br />
                                </g:each>
                            </div>

                            <div id="errorMessage"></div>

                            <div id="buttons">
                                <button type="submit" class="btn btn-success">Guardar</button>
                                <a href="/movement/list" class="btn btn-danger">Cancelar</a>
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

<script src="/js/controllers/movementDetailController.js?v=${v}"></script>
<script type="text/javascript">
    console.log("movementDetailController()");
</script>

<g:render template="/commons/footer"/>