<#assign siteLocation="">
<#assign staticLocation="">
<#macro head>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${staticLocation}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${staticLocation}/condition/css/style.css"/>
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
                <li class='dropdown ${(curNav=="create")?string("active","")}'>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        新建<b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="${siteLocation}/test/create">价格</a></li>
                        <li><a href="${siteLocation}/test/create">拐点买入</a></li>
                    </ul>
                </li>
                <li class='${(curNav=="list")?string("active","")}'><a href="${siteLocation}/test/list">监控中</a></li>
                <li class='${(curNav=="entrusts")?string("active","")}'><a href="${siteLocation}/test/entrusts">已委托</a></li>
            </ul>
        </div>
    </div>
</nav>
</#macro>