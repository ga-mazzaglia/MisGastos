<g:render template="/commons/items/head"/>

<% def v = new Date().format("yyyyMMddHHmmss"); %>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Entrar</h3>
                </div>
                <div class="panel-body">
                    <form role="form" action="javascript:void(0);">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="Usuario" id="username" type="text" value="" autofocus>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Contraseña" id="password" type="password" value="">
                            </div>
                            <div id="errorMessage" style="display: none;color: red;margin-bottom: 10px;"></div>
                            <div id="successMessage" style="display: none;color: darkgreen;margin-bottom: 10px;"></div>
                            <!-- Change this to a button or input when using this as a form -->
                            <button onclick="loginController.signIn()" class="btn btn-lg btn-success btn-block">Entrar</button>
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
