<%--
  Created by IntelliJ IDEA.
  User: HoosinLee
  Date: 2020/4/12
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>商品列表</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <!--自定义css-->
    <link href="/css/my.css" rel="stylesheet" type="text/css">
    <link href="/css/list.css" rel="stylesheet" type="text/css">
    <link href="/css/qunit-1.11.0.css" rel="stylesheet" type="text/css">
    <link href="/css/notice.css" rel="stylesheet" type="text/css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!--引入第三方动态插件js-->
    <script type="text/javascript" src="/js/other/jquery.niftymodals.js"></script>
    <!--分页插件js-->
    <script type="text/javascript" src="/js/other/bootstrap-paginator.js"></script>
    <script type="text/javascript" src="/js/other/qunit-1.11.0.js"></script>
    <style>

    </style>
</head>
<body>
<!--右侧内容展示栏-->
<div class="section">
    <div class="context-box">
        <div class="container">
            <!--商品列表顶栏-->
            <div class="row clearfix">
                <div class="title">
                    <a class="btn btn-link right-back" href="/toHome">返回首页</a>
                    <h1><small>商品列表</small></h1>
                </div>
                <div class="row">
                    <!--nav-->
                    <div class="col-md-4 column" style="padding-top: 10px">
                        <!--添加商品-->
                        <a class="btn btn-danger" id="batch-delete" data-modal="message-window" onclick="confirmOperation(this)">
                            <span class="glyphicon glyphicon-trash" style="font-size: 14px"></span>
                            批量删除
                        </a>
                        <a class="btn btn-info" href="/product/goods/toAdd">
                            <span class="glyphicon glyphicon-plus" style="font-size: 14px"></span>
                            添加商品
                        </a>
                        <!--<a class="btn btn-warning" data-modal="message-window" onclick="reStart()">初始化系统</a>-->
                    </div>
                    <div class="col-md-8 column" style="padding-top: 8px">
                        <!--搜索商品-->
                        <form class="form-inline" action="#" method="post" onsubmit="return false">
                            <span id="error-msg" style="font-size: 12px"></span>
                            <input type="text" id="search-key" name="search-key" class="form-control" placeholder="请输入要查询的商品关键字" onblur="initSearchTips()">
                            <a class="btn btn-info" onclick="searchGoods()"><span class="glyphicon glyphicon-search" style="font-size: 14px"></span>搜索商品</a>
                        </form>
                    </div>
                </div>
                <!--paging select-->
                <div class="row" style="padding-top: 10px">
                    <div class="col-md-3 column page-control">
                        <span>显示</span>
                        <select class="page-select" id="page-select">
                            <option value="10" selected>10</option>
                            <option value="25">25</option>
                            <option value="50">50</option>
                            <option value="100">100</option>
                        </select>
                        <span>条</span>
                        <span style="font-size: 10px;color: yellowgreen;display: none;padding-left: 10px;line-height: 15px" id="loading-data-tips">正在加载数据...</span>
                    </div>
                </div>
            </div>
            <!--商品列表-->
            <div class="row clearfix product-list">
                <div class="col-md-24 column">
                    <input type="hidden" value="" id="ids-to-do">
                    <table class="table table-hover table-condensed table-bordered" id="product-table">
                        <!--表头-->
                        <thead>
                        <tr>
                            <th style="width: 10px"><input type="checkbox" id="select-all" style="width: 20px"></th>
                            <th style="width: 75px">商品ID</th>
                            <th style="width: 50px">商品名称</th>
                            <th style="width: 205px">商品简介</th>
                            <th style="width: 70px">单价</th>
                            <th style="width: 60px">库存</th>
                            <th style="width: 60px">限购数量</th>
                            <th style="width: 40px">分类</th>
                            <th style="width: 60px">缩略图</th>
                            <th style="width: 60px">发布状态</th>
                            <th style="width: 80px">操作</th>
                        </tr>
                        </thead>

                        <tbody>
                        <!--一定要在顶部引入c命名空间，否则不能使用c：forEach遍历数据-->
                        <!--数据-->
                        </tbody>
                    </table>
                </div>
            </div>
            <!--分页-->
            <div class="row">
                <div class="col-md-24 column" id="data-bottom-tips" style="height: 15px"></div>
                <div class="col-md-24 column">
                    <nav aria-label="Page navigation">
                        <ul class="pagination" id="product-split">
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>
<!--弹框消息-->
<!--弹框消息-->
<div class="md-container md-effect-1 message-box" id="message-window">
    <div class="md-content">
        <div class="close-btn">
            <input type="button" id="window-close" class="md-close" value="x" onmouseover="changeBtn(this)" onmouseout="initBtn(this)">
        </div>
        <div class="context">
            <div class="message">
                <div class="header-box"><span id="header">系统消息</span></div>
                <div class="msg-box">
                    <span id="msg">处理中......</span>
                </div>
                <div class="confirm-btn">
                    <input type="button" id="confirm" class="put" value="确认" onmouseover="changeBtn(this)" onmouseout="initBtn(this)">
                </div>
                <input type="hidden" value="" id="id">
            </div>
        </div>
    </div>
</div>
<div class="md-overlay"></div>
<!-- 以下负责弹框消息-->
<script>
    $(document).ready(function() {
        $('.md-trigger').on('click',function(){
            let modal = $(this).data('modal');
            $("#" + modal).niftyModal();
        });
    });
    function changeBtn(obj) {
        obj.style.backgroundColor = "#c9ffd1";
    }
    function initBtn(obj) {
        obj.style.backgroundColor = "";
    }
</script>
<!-- 以下负责处理分页查询商品业务-->
<!--导入公共模板js-->
<script type="text/javascript" src="/js/notice.js"></script>
<script type="text/javascript" src="/js/page_split.js"></script>
<script type="text/javascript" src="/js/select_all.js"></script>
<script type="text/javascript" src="/js/operation_batch.js"></script>
<script type="text/javascript" src="/js/search.js"></script>
<!--导入处理业务js-->
<script type="text/javascript" src="/js/listProduct.js"></script>
</body>
</html>
