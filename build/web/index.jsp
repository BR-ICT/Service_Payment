<%-- 
    Document   : index
    Created on : Jun 14, 2019, 9:32:52 AM
    Author     : Wattana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <%
        if (session.getAttribute("cono") == null) {
            response.sendRedirect("./login.jsp");
        }
    %>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="assets/jQuery-3.3.1/jquery-ui.min.css">
        <link rel="stylesheet" type="text/css" href="assets/Bootstrap-3.3.7/css/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="assets/jsgrid-1.5.3/dist/jsgrid.min.css" />
        <link rel="stylesheet" type="text/css" href="assets/jsgrid-1.5.3/dist/jsgrid-theme.min.css">
        <link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="fonts/iconic/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" type="text/css" href="assets/animate/animate.css">
        <link rel="stylesheet" type="text/css" href="assets/css-hamburgers/hamburgers.min.css">
        <link rel="stylesheet" type="text/css" href="assets/animsition/css/animsition.min.css">
        <link rel="stylesheet" type="text/css" href="assets/select2/select2.min.css">
        <link rel="stylesheet" type="text/css" href="assets/util.css">
        <link rel="stylesheet" type="text/css" href="assets/main.css">
        <script type="text/javascript" src="assets/jQuery-3.3.1/jquery-3.3.1.min.js"></script>
        <script type="text/javascript" src="assets/Bootstrap-3.3.7/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="assets/jQuery-3.3.1/jquery-ui.min.js"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
        <script type="text/javascript" src="assets/jsgrid-1.5.3/dist/jsgrid.min.js"></script>
        <script src="assets/jsgrid-1.5.3/src/jsgrid.core.js"></script>
        <script src="assets/jsgrid-1.5.3/src/jsgrid.validation.js"></script>
        <script src="assets/jsgrid-1.5.3/src/jsgrid.load-indicator.js"></script>
        <script src="assets/jsgrid-1.5.3/src/jsgrid.load-strategies.js"></script>
        <script src="assets/jsgrid-1.5.3/src/jsgrid.sort-strategies.js"></script>
        <script src="assets/jsgrid-1.5.3/src/jsgrid.field.js"></script>
        <script src="assets/jsgrid-1.5.3/src/fields/jsgrid.field.text.js"></script>
        <script src="assets/jsgrid-1.5.3/src/fields/jsgrid.field.number.js"></script>
        <script src="assets/jsgrid-1.5.3/src/fields/jsgrid.field.select.js"></script>
        <script src="assets/jsgrid-1.5.3/src/fields/jsgrid.field.checkbox.js"></script>
        <script src="assets/jsgrid-1.5.3/src/fields/jsgrid.field.control.js"></script>
        <link rel="shortcut icon" href="images/icons/favicon.ico"/>
        <title>Service Payment: <%out.print(session.getAttribute("comp"));%></title> 
        
        
        
         <script src='assets/select2/select2.min.js' type='text/javascript'></script>
        <link href='assets/select2/select2.min.css' rel='stylesheet' type='text/css'>
    </head>

    <style>

        .btnsignout {
            display: inline-block;
            padding: 0px 12px;
            margin-bottom: 0;
            font-size: 14px;
            font-weight: 400;
            line-height: 1.42857143;
            text-align: center;
            white-space: nowrap;
            vertical-align: middle;
            -ms-touch-action: manipulation;
            touch-action: manipulation;
            cursor: pointer;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
            background-image: none;
            border: 1px solid transparent;
            border-radius: 4px;
        }
        .li {
            display: none;
        }

    </style>

    <body style="background-color: #bababa;">

        <div>
            <nav id="vNavbar" class="navbar navbar-default navigation-clean" >
                <div class="container">
                    <div class="navbar-header"><a class="navbar-brand" href="./">Service Payment</a><button data-toggle="collapse" class="navbar-toggle collapsed" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button></div>
                    <div class="navbar-header"><a class="navbar-brand" href="./" id="compname">Service Payment</a><button data-toggle="collapse" class="navbar-toggle collapsed" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button></div>
                    <div class="collapse navbar-collapse" id="navcol-1">
                        <ul class="nav navbar-nav navbar-right">
                            <li role="presentation"></li>
                            <li class="dropdown"><a id="rollbacklink" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false" href="\.">Rollback<span class="caret"></span></a>
                                <ul class="dropdown-menu" role="menu">
                                    <li role="presentation"><a class='dropdown-item' name="app" href="?page=rollback_services">Rollback Services</a></li>
                                    <li role="presentation"><a class='dropdown-item' name="app" href="?page=rollback_payment">Rollback Payment</a></li>
                                </ul>
                            </li>
                            <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false" href="\.">Services<span class="caret"></span></a>
                                <ul class="dropdown-menu" role="menu">
                                    <li role="presentation"><a class='dropdown-item' name="app" href="?page=Services&app=ERN">Expense Receipt Note(ERN)</a></li>
                                    <li role="presentation"><a class='dropdown-item' name="app" href="?page=Services&app=NEN">Non Expense Note(NEN)</a></li>
                                </ul>
                            </li>
                            <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false" href="\.">Payment<span class="caret"></span></a>
                                <ul class="dropdown-menu" role="menu">
                                    <li role="presentation"><a class='dropdown-item' name="app" href="?page=PRV&app=ERN">Payment Request for ERN</a></li>
                                    <li role="presentation"><a class='dropdown-item' name="app" href="?page=PRV&app=NEN">Payment Request for NEN</a></li>
                                    <li role="presentation"><a class='dropdown-item' name="app" href="?page=PRV&app=GRN">Payment Request System (GRN) </a></li>
                                </ul>
                            </li>
                            <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false" href="\.">Monitoring<span class="caret"></span></a>
                                <ul class="dropdown-menu" role="menu">
                                    <li role="presentation"><a class='dropdown-item' name="app" href="?page=monitoring_services">History Services</a></li>
                                    <li role="presentation"><a class='dropdown-item' name="app" href="?page=monitoring_payment">History Payment</a></li>
                                </ul>
                            </li>
                             <li><a aria-expanded="false" href="?page=report">Report</a>
<!--                                <ul class="dropdown-menu" role="menu">
                                    <li role="presentation"><a class='dropdown-item' name="app" href="?page=monitoring_services">History Services</a></li>
                                    <li role="presentation"><a class='dropdown-item' name="app" href="?page=monitoring_payment">History Payment</a></li>
                                </ul>-->
                            </li>

                            <li class="dropdown">
                                <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                    <form action="Logout" method="POST">
                                        <input class="btnsignout btn-danger mb-2" type="submit" value="Sign Out">
                                    </form>
                                </a>
                            </li>
                        </ul>
                    </div><link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css" rel='stylesheet' type='text/css'>
                </div>
            </nav>
        </div>

        <section id="pages">
            <div class='centerDiv' id="vSection"></div>
        </section>

    </body>
</html>

<script type="text/javascript">

    <% if (request.getParameter("page") != null) {%>
    $("#vSection").load("./pages/<%=request.getParameter("page").toString()%>.jsp");
    <% }%>
    var comp = "<%out.print(session.getAttribute("comp"));%>";
    $("#compname").text(comp);
    <% if (request.getParameter("report") != null) {%>
    $("#vSection").load("./reports/<%=request.getParameter("report").toString()%>.jsp");
    <% }%>

    $('.dropdown-item').on('click', function () {
        $("#vSection").load("./pages/" + this.name + ".jsp");
    });
    var auth = "<%out.print(session.getAttribute("auth"));%>";
    
    if(auth === "Users"){
        $("#rollbacklink").hide();
    }
</script>