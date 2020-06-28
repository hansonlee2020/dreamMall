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
    <title>分类列表</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <!--自定义css-->
    <link href="/css/my.css" rel="stylesheet" type="text/css">
    <link href="/css/list.css" rel="stylesheet" type="text/css">
    <link href="/css/notice.css" rel="stylesheet" type="text/css">
    <link href="/css/edit.css" rel="stylesheet" type="text/css">
    <!--分页插件css-->
    <link href="/css/qunit-1.11.0.css" rel="stylesheet" type="text/css">
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
                    <h1><small>分类列表</small></h1>
                </div>
                <div class="row">
                    <!--nav-->
                    <div class="col-md-4 column" style="padding-top: 10px">
                        <!--批量删除-->
<%--                        <a class="btn btn-danger" id="batch-delete" data-modal="message-window" onclick="confirmOperation(this)">批量删除</a>--%>
                        <button class="btn btn-info" type="button" data-toggle="collapse" data-target="#category-info" aria-expanded="false" aria-controls="category-info" onclick="recovery()" style="outline: none;border: 0 solid">
                            <span class="glyphicon glyphicon-plus" style="font-size: 14px"></span>
                            添加分类
                        </button>
                    </div>
                </div>
                <!--添加分类-->
                <div class="collapse" id="category-info">
                    <div class="well" style="background-color: lightgoldenrodyellow;border-color: lightgoldenrodyellow">
                        <ul style="list-style: none" class="edit-list">
                            <li><label style="position: absolute">分类规则提示：</label><span id="rule-tips">提示消失了~请检查服务器！</span></li>
                            <li><label>分&nbsp;&nbsp;类&nbsp;&nbsp;id：</label><input style="margin-left: 2px" type="text" id="category-id" name="category-id" spellcheck="false"><p class="help-block" id="id-msg">可以输入1~9999之间的任何数字</p></li>
                            <li><label>一级分类：</label><input type="text" class="category" id="category1" name="category1" spellcheck="false"><p class="help-block" id="category1-msg">1~20个中文、英文或数字字符</p></li>
                            <li><label>二级分类：</label><input type="text" class="category" id="category2" name="category2" spellcheck="false"><p class="help-block" id="category2-msg">1~20个中文、英文或数字字符</p></li>
                            <li><label>三级分类：</label><input type="text" class="category" id="category3" name="category3" spellcheck="false"><p class="help-block" id="category3-msg">1~20个中文、英文或数字字符</p></li>
                            <li style="width: 380px;height: 42px">
                                <label></label>
                                <div style="display: inline-block;left: 100px;font-size: 16px">
                                    <input type="button" style="float: right" id="reset-btn" value="重置" onmouseover="changeBtn(this)" onmouseout="initBtn(this)" onclick="reset()">
                                    <input type="button" id="put-btn" value="新增" data-modal="message-window" onmouseover="changeBtn(this)" onmouseout="initBtn(this)" onclick="increaseThis(this)">
                                    <input type="hidden" id="edit-btn" value="修改" data-modal="message-window" onmouseover="changeBtn(this)" onmouseout="initBtn(this)" onclick="edit(this)">
                                </div>
                            </li>
                            <li><label>处理结果：</label><span id="result-details"></span></li>
                        </ul>
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
                    <div class="col-md-9 column" style="padding-top: 8px">
                        <!--搜索用户-->
                        <form class="form-inline" action="#" method="post" onsubmit="return false">
                            <span id="error-msg" style="font-size: 12px"></span>
                            <input type="text" id="search-key" name="categoryName" class="form-control" spellcheck="false" placeholder="请输入要查询的分类" onblur="initToOriTips()" onkeyup="searchCategory()">
                            <a class="ban" disabled="disabled" style="font-size: 15px;text-decoration: none" readonly="readonly">
                                <span class="glyphicon glyphicon-search" style="font-size: 14px"></span>
                                当前数据检索
                            </a>
                        </form>
                    </div>
                </div>
            </div>
            <!--商品列表-->
            <div class="row clearfix product-list">
                <div class="col-md-24 column">
                    <input type="hidden" value="" id="ids-to-do">
                    <table class="table table-hover table-condensed table-bordered" id="category-table">
                        <!--表头-->
                        <thead>
                        <tr>
                            <th style="width: 10px"><input type="checkbox" id="select-all" style="width: 20px"></th>
                            <th style="width: 150px">分类ID</th>
                            <th style="width: 150px">一级分类</th>
                            <th style="width: 150px">二级分类</th>
                            <th style="width: 150px">三级分类</th>
                            <th style="width: 80px">操作</th>
                        </tr>
                        </thead>

                        <tbody>
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
                <input type="hidden" value="" id="info">
            </div>
        </div>
    </div>
</div>
<div class="md-overlay"></div>
<!-- 以下负责处理分页查询商品业务-->
<!--导入公共通知js-->
<script type="text/javascript" src="/js/notice.js"></script>
<script type="text/javascript" src="/js/page_split.js"></script>
<script type="text/javascript" src="/js/select_all.js"></script>
<script type="text/javascript" src="/js/operation_batch.js"></script>
<script type="text/javascript" src="/js/search.js"></script>
<!--导入处理业务js-->
<script type="text/javascript" src="/js/category_list.js"></script>
</body>
</html>
