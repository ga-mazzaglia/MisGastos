<%@ page import="com.cuentasclaras.User" %>
<% User userLogged = grailsApplication.mainContext.getBean("loginService").getUserLogged(); %>

<div class="navbar-default sidebar" role="navigation">
    <div class="sidebar-nav navbar-collapse collapse">
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
                <img src="/images/avatar.png" width="100"/>

                <div style="margin-top: 10px; font-weight: bold;">
                    ${userLogged.name}
                </div>
            </li>

            <li>
                <a href="/" id="main">Inicio</a>
            </li>
            <li>
                <a href="/movement/list" id="movements">Movimientos</a>
            </li>
            <li>
                <a href="/statistics" id="statistics">Estad&iacute;sticas</a>
            </li>
            <li>
                <a href="/tag/list" id="tags">Tags</a>
            </li>
            <li>
                <a href="/logout" id="logout" style="color: red;">Salir</a>
            </li>
            <li style="display: none;">
                <a href="/friends" id="friends">Amigos</a>
            </li>
        </ul>
    </div>
    <!-- /.sidebar-collapse -->
</div>
<!-- /.navbar-static-side -->
