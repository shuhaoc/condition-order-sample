<#assign staticLocation="">
<#macro head>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${staticLocation}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <#nested/>
</head>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#example-navbar-collapse">
                <span class="sr-only">切换导航</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">条件单</a>
        </div>
        <div class="collapse navbar-collapse" id="example-navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="#">新建</a></li>
                <li class="active"><a href="index">监控中</a></li>
                <li><a href="#">已委托</a></li>
                <li><a href="#">历史记录</a></li>
            </ul>
        </div>
    </div>
</nav>
</#macro>