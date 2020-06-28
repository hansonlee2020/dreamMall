<%--
  Created by IntelliJ IDEA.
  User: HoosinLee
  Date: 2020/3/19
  Time: 12:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>梦想购物中心后台管理系统</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <!--引入自定义css样式-->
    <link href="css/my.css" rel="stylesheet">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!--引入自定义js文件-->
    <style>

    </style>
</head>
<body>
<!--顶部导航栏-->
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <span>梦想购物中心后台管理系统</span>
        </div>
        <div class="collapse navbar-collapse" id="operation-bar">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#" id="message">消息</a></li>
                <li class="dropdown">
                    <a href="#" id="user-info" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">用户中心 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/logout" id="sign-out">退出登陆</a></li>
                        <li role="separator" class="divider"></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<div class="home-container">
    <!--左侧栏-->
    <div class="aside">
        <div class="aside-bar">
            <div class="panel-group" id="menu">
                <dl class="panel panel-default op-menu" id="menu-product">
                    <dt>
                        <span class="glyphicon glyphicon-shopping-cart" ></span>
                        <a data-toggle="collapse" data-parent="#menu" href="#product" class="menu-items style-col">
                            商品管理
                            <span class="glyphicon glyphicon-chevron-down"></span>
                        </a>
                    </dt>
                    <dd id="product" class="panel-collapse collapse item">
                        <ul>
                            <li><a class="items style-col" id="listProduct" href="/product/goods/toList">查看商品</a></li>
                            <li><a class="items style-col" id="addProduct" href="/product/goods/toAdd">添加商品</a></li>
                        </ul>
                    </dd>
                </dl>
                <dl class="panel panel-default op-menu" id="menu-category">
                    <dt>
                        <span class="glyphicon glyphicon-sort" ></span>
                        <a data-toggle="collapse" data-parent="#menu" href="#category" class="menu-items style-col">
                            分类管理
                            <span class="glyphicon glyphicon-chevron-down"></span>
                        </a>
                    </dt>
                    <dd id="category" class="panel-collapse collapse item">
                        <ul>
                            <li><a class="items style-col" id="listCategory" href="/category/toList">分类列表</a></li>
                        </ul>
                    </dd>
                </dl>
                <dl class="panel panel-default op-menu" id="menu-order">
                    <dt>
                        <span class="glyphicon glyphicon-list-alt" ></span>
                        <a data-toggle="collapse" data-parent="#menu" href="#order" class="menu-items style-col">
                            订单管理
                            <span class="glyphicon glyphicon-chevron-down"></span>
                        </a>
                    </dt>
                    <dd id="order" class="panel-collapse collapse item">
                        <ul>
                            <li><a class="items style-col" id="listOrder" href="/order/toList">订单列表</a></li>
                        </ul>
                    </dd>
                </dl>
                <dl class="panel panel-default op-menu" id="menu-authority">
                    <dt>
                        <span class="glyphicon glyphicon-lock" ></span>
                        <a data-toggle="collapse" data-parent="#menu" href="#authority" class="menu-items style-col">
                            权限管理
                            <span class="glyphicon glyphicon-chevron-down"></span>
                        </a>
                    </dt>
                    <dd id="authority" class="panel-collapse collapse item">
                        <ul>
                            <li><a class="items style-col" id="listAuthority" href="/authority/toList">权限列表</a></li>
                        </ul>
                    </dd>
                </dl>
                <dl class="panel panel-default op-menu" id="menu-system">
                    <dt>
                        <span class="glyphicon glyphicon-wrench" ></span>
                        <a data-toggle="collapse" data-parent="#menu" href="#system" class="menu-items style-col">
                            系统管理
                            <span class="glyphicon glyphicon-chevron-down"></span>
                        </a>
                    </dt>
                    <dd id="system" class="panel-collapse collapse item">
                        <ul>
                            <li><a class="items style-col" id="listUser" href="/member/toList">用户管理</a></li>
                            <li><a class="items style-col" id="sysUser" href="/sys/user/toList">管理员管理</a></li>
                        </ul>
                    </dd>
                </dl>
            </div>
        </div>
    </div>
</div>
<!--版权及备案信息-->
<div class="footer">
    <span style="line-height: 50px;color: grey;font-size: 15px">© 2020 Hanson
        <a style="text-decoration: none;font-size: 15px" href="http://www.beian.miit.gov.cn/">
            &nbsp;&nbsp;粤ICP备20043119号
        </a>
    </span>
</div>
<!--首页初始化业务处理-->
<script type="text/javascript" src="js/home.js"></script>
</body>
</html>
