<%@ page import="com.cuentasclaras.User" %>
<% User userLogged = grailsApplication.mainContext.getBean("loginService").getUserLogged(); %>

<div class="navbar-default sidebar" role="navigation">
    <div class="sidebar-nav navbar-collapse">
        <ul class="nav" id="side-menu">
            <li class="sidebar-search" style="display: none;">
                <div class="input-group custom-search-form">
                    <input type="text" class="form-control" placeholder="Search...">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button">
                            <i class="fa fa-search"></i>
                        </button>
                    </span>
                </div>
                <!-- /input-group -->
            </li>
            <li class="center" style="padding: 10px;">
                <img src="http://www.megatec.org.pe/wp-content/uploads/2016/03/Generic_Avatar-e1458146412169.png" width="100"/>

                <div style="margin-top: 10px; font-weight: bold;">
                    ${userLogged.name}
                </div>
            </li>

            <li>
                <a href="/main" id="main">Inicio</a>
            </li>
            <li>
                <a href="/movement/list" id="movements">Movimientos</a>
            </li>
            <li style="display: none;">
                <a href="/friends" id="friends">Amigos</a>
            </li>
        </ul>
    </div>
    <!-- /.sidebar-collapse -->
</div>
<!-- /.navbar-static-side -->
