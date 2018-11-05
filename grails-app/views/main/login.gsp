<g:render template="/commons/items/head"/>

<% def v = new Date().format("yyyyMMddHHmmss"); %>

<div class="container">
    <div class="row" style="margin-top: 10%">
        <div class="col-md-4 col-md-offset-4 center">
            <h1>Mis Gastos 2.0</h1>
            <br />
        </div>

        <div class="col-md-4 col-md-offset-4">
            <div class=" panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Entrar</h3>
                </div>

                <div class="panel-body">
                    <form role="form" action="javascript:void(0);">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="Usuario" id="username" type="text" value=""
                                       autofocus>
                            </div>

                            <div class="form-group">
                                <input class="form-control" placeholder="Contraseña" id="password" type="password"
                                       value="">
                            </div>

                            <div id="verifyMessage" class="alert alert-info"
                                 style="display: none;margin-bottom: 10px;"></div>

                            <div id="errorMessage" class="alert alert-danger"
                                 style="display: none;color: red;margin-bottom: 10px;"></div>

                            <div id="successMessage" class="alert alert-success"
                                 style="display: none;color: darkgreen;"></div>
                            <!-- Change this to a button or input when using this as a form -->
                            <button id="btn-login" onclick="loginController.signIn()"
                                    class="btn btn-lg btn-success btn-block">Entrar</button>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/js/controllers/loginController.js?v=${v}"></script>
<script type="text/javascript">
    console.log("loginController()");
</script>

<g:render template="/commons/footer"/>
