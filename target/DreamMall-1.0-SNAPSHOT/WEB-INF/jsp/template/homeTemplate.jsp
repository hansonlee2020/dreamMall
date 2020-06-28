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
    <link href="../../../css/my.css" rel="stylesheet">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!--引入自定义js文件-->
    <script src="/js/home.js"></script>
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
                        <li><a href="#" id="info">用户中心</a></li>
                        <li><a href="#" id="switch">切换用户</a></li>
                        <li><a href="${pageContext.request.contextPath}/logout" id="sign-out">退出登陆</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">其他</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<!--左侧栏-->
<div class="aside">
    <div class="aside-bar">
        <div class="panel-group" id="menu">
            <dl class="panel panel-default" id="menu-product">
                <dt><a data-toggle="collapse" data-parent="#menu" href="#product" class="menu-items">商品管理<span class="glyphicon glyphicon-chevron-down"></span></a></dt>
                <dd id="product" class="panel-collapse collapse">
                    <ul>
                        <li><a class="product-items" id="listProduct" href="#">查看商品</a></li>
                        <li><a class="product-items" id="addProduct" href="#">发布商品</a></li>
                        <li><a class="product-items" id="updateProduct" href="#">修改商品</a></li>
                        <li><a class="product-items" id="deleteProduct" href="#">删除商品</a></li>
                    </ul>
                </dd>
            </dl>
            <dl class="panel panel-default" id="menu-order">
                <dt><a data-toggle="collapse" data-parent="#menu" href="#order" class="menu-items">订单管理<span class="glyphicon glyphicon-chevron-down"></span></a></dt>
                <dd id="order" class="panel-collapse collapse">
                    <ul>
                        <li>1</li>
                        <li>2</li>
                        <li>3</li>
                    </ul>
                </dd>
            </dl>
            <dl class="panel panel-default" id="menu-system">
                <dt><a data-toggle="collapse" data-parent="#menu" href="#system" class="menu-items">系统管理<span class="glyphicon glyphicon-chevron-down"></span></a></dt>
                <dd id="system" class="panel-collapse collapse">
                    <ul>
                        <li>1</li>
                        <li>2</li>
                        <li>3</li>
                    </ul>
                </dd>
            </dl>
        </div>
    </div>
</div>
<!--右侧内容展示栏-->
<div class="section">
    <div class="iframe-box">
        <div class="show-iframe" id="show-iframe">
            <div class="loading" style="display: none"></div>
            <iframe scrolling="yes" frameborder="0" id="iframe" src=""></iframe>
        </div>
    </div>
</div>
<%--<div class="section">
    <div class="context-box">
        <div class="container">
            <div class="row clearfix">
                <div class="title">
                    <h1><small>商品列表</small></h1>
                    <span>欢迎，${wellcomeMsg}</span>
                </div>
                <div class="row">
                    <div class="col-md-4 column">
                        <!--添加商品-->
                        <a class="btn btn-primary" href="#">新增商品</a>
                        <a class="btn btn-primary" href="#">返回上一页</a>
                        <a class="btn btn-primary" href="/product/goods/list">商品列表</a>
                        <a class="btn btn-primary" href="/system/toInfo">系统信息</a>
                    </div>
                    <div class="col-md-8 column">
                        <!--搜索商品-->
                        <form class="form-inline" action="#" method="post">
                            <span class="error-msg">${搜索错误信息}</span>
                            <input type="text" name="goodsName" class="form-control" placeholder="请输入要查询的商品名称">
                            <input class="btn btn-primary" type="submit" value="查询">
                        </form>
                    </div>
                </div>
            </div>

            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-hover table-striped">
                        <thead>
                        <tr>
                            <th>商品ID</th>
                            <th>商品名称</th>
                            <th>商品描述</th>
                            <th>单价</th>
                            <th>创建日期</th>
                            <th>更新日期</th>
                            <th>发布状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>

                        <tbody>
                        <!--一定要在顶部引入c命名空间，否则不能使用c：forEach遍历数据-->
                        <c:forEach var="goods" items="${goodsList}">
                            <tr>
                                <td>${goods.goodsId}</td>
                                <td>${goods.goodsName}</td>
                                <td>${goods.goodsDetail}</td>
                                <td>${goods.price}</td>
                                <td>${goods.createDate}</td>
                                <td>${goods.updateDate}</td>
                                <td>${goods.releaseState}</td>
                                <td>
                                    <a href="#">修改</a>
                                    &nbsp;|&nbsp;
                                    <a href="#">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>--%>
</body>
</html>
