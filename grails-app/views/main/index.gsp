<g:render template="/commons/header"/>
<% def v = new Date().format("yyyyMMddHHmmss"); %>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Inicio</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>

<!-- /.row -->
<div class="row">
    <g:render template="/commons/items/notifications"/>

    <div class="col-lg-12 col-md-6">
        <div class="panel panel-green">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-3">
                        <i class="fa fa-tasks fa-5x"></i>
                    </div>

                    <div class="col-xs-9 text-right">
                        <div class="huge">$ 12.00</div>

                        <div>Paulo Mazzaglia</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel panel-red">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-3">
                        <i class="fa fa-tasks fa-5x"></i>
                    </div>

                    <div class="col-xs-9 text-right">
                        <div class="huge">$ 23.90</div>

                        <div>Nicolas Mazzaglia</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/js/controllers/indexController.js?v=${v}"></script>
<script type="text/javascript">
    console.log("indexController()");
</script>

<g:render template="/commons/footer"/>
