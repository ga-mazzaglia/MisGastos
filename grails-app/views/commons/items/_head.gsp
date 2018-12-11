<head>
    <% def v = new Date().format("yyyyMMddHHmmss"); %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Mis Gastos 2.0</title>

    <!-- Favicon -->
    <link rel="shortcut icon" href="/images/favicon.ico?v=${v}" type="image/x-icon">

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css?v=${v}" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/resources/dist/css/sb-admin-2.css?v=${v}" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="/resources/vendor/morrisjs/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- Main Style -->
    <link href="/css/main.css?v=${v}" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <script src="/js/jquery-3.1.0.js"></script>
    <style>
    .datepicker-days table tbody tr {
        height: 30px;
    }
    .datepicker-days .day, .datepicker-switch {
        text-align: center;
    }
    .datepicker-days .day:hover {
        background-color: #82cfe9;
        cursor: pointer;
    }
    </style>
</head>
