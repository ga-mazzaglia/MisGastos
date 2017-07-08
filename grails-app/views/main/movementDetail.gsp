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
        <div id="movement_detail" class="panel panel-default">
            <div class="panel-heading">
                Informaci&oacute;n
            </div>

            <div class="panel-body">
                <div class="row">
                    <div class="col-lg-12">
                        <form id="movementDetail" action="javascript: movementDetailController.save();" role="form" method="POST">
                            <input type="hidden" name="id" value="${mov?.id}">

                            <!-- fecha -->
                            <div class="form-group">
                                <input type="text" format="dd/MM/yyyy" class="form-control datepicker" placeholder="Fecha" name="date" value="${mov.date.format("dd/MM/yyyy")}" readonly style="background-color: white;width: 100%">
                            </div>
                            <!-- descripcion -->
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Descripción" autofocus name="detail" value="${mov.detail}">
                            </div>
                            <!-- monto -->
                            <div class="form-group input-group">
                                <span class="input-group-addon"><i class="fa fa-dollar"></i>
                                </span>
                                <input type="number" class="form-control" placeholder="Importe" name="amount" value="${mov.amount ?: ""}">
                            </div>
                            <!-- tipo -->
                            <div class="form-group" id="type">
                                <label>Tipo</label>
                                <select class="form-control" id="type" name="type">
                                    <g:each in="${movementTypes}" var="type">
                                        <option value="${type.id}" ${type.id == mov?.type?.id ? "selected" : ""}>${type.detail}</option>
                                    </g:each>
                                </select>
                            </div>
                            <!-- amigos -->
                            <div class="form-group" id="friends" style="display: ${mov?.type?.id != 1 ? "" : "none"};">
                                <label>Amigos</label>
                                <br/>
                                <g:each in="${friends}" var="friend">
                                    <div style="margin-bottom: 5px;"
                                         onclick="movementDetailController.addFriend(${friend.id})">
                                        <button id="btn-friend_${friend.id}" type="button"
                                                data-friend_id="${friend.id}"
                                                class="btn btn-${friend.id in mov?.users*.id ? "success" : "default"} btn-circle btn-friend">
                                            <i class="fa fa-check"></i>
                                        </button>
                                        ${friend.name}
                                    </div>
                                </g:each>
                                <div id="friends_added" style="display: none;">
                                    <g:each in="${friends}" var="friend">
                                        <g:if test="${friend.id in mov?.users*.id}">
                                            <input name='friends' value="${friend.id}"/>
                                        </g:if>
                                    </g:each>
                                </div>
                            </div>
                            <g:if test="${tags.size()}">
                                <!-- tags -->
                                <div class="form-group">
                                    <label>Tags</label>
                                    <br/>
                                    <g:each in="${tags}" var="tag">
                                        <a onclick="movementDetailController.clickTag(${tag.id});" id="tag_${tag.id}" class="btn btn-${tag.id in mov?.tags*.id ? "success" : "primary"}"
                                           style="margin-bottom: 5px;">
                                            <i class="fa fa-tag"></i> ${tag.detail}
                                        </a>
                                    </g:each>
                                    <div id="tags_selected" style="display: none"></div>
                                </div>
                            </g:if>

                            <hr/>

                            <div id="successMessage"></div>
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
