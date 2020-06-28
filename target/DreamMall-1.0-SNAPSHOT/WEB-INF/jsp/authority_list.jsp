<%--
  Created by IntelliJ IDEA.
  User: HoosinLee
  Date: 2020/4/19
  Time: 13:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>权限列表</title>
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
                    <h1><small>权限列表</small></h1>
                </div>
                <div class="row">
                    <!--nav-->
                    <div class="col-md-4 column" style="padding-top: 10px">
                        <button class="btn btn-info" type="button" data-toggle="collapse" data-target="#authority-info" aria-expanded="false" aria-controls="authority-info" onclick="recovery()" style="outline: none;border: 0 solid">
                            <span class="glyphicon glyphicon-plus" style="font-size: 14px"></span>
                            添加权限
                        </button>
                    </div>
                </div>
                <!--添加权限-->
                <div class="collapse" id="authority-info">
                    <div class="well" style="background-color: lightgoldenrodyellow;border-color: lightgoldenrodyellow">
                        <ul style="list-style: none" class="edit-list">
                            <li><label>权限id：</label><input style="margin-left: 2px" type="text" id="authority-id" name="authority-id" readonly="readonly" value="9999"></li>
                            <li><label><strong style="color: red">*</strong>权限名：</label><input type="text" class="authority" id="authority-name" name="authority-name" spellcheck="false"><p class="help-block" id="authority-name-msg">1~50个英文字符</p></li>
                            <li><label><strong style="color: red">*</strong>资源字段名：</label><input type="text" class="authority" id="authority-field" name="authority-field" spellcheck="false"><p class="help-block" id="authority-field-msg">1~50英文字符</p></li>
                            <li><label style="display: none">提示：</label><span id="help-tips">提示消失，请检查服务器！</span></li>
                            <li><label>权限组id：</label><input type="text" class="authority" id="authority-groupId" name="authority-groupId"><p class="help-block" id="authority-groupId-msg">1~3位数字,首位数字不能为0！</p></li>
                            <li style="position: relative"><label style="position: absolute"><strong style="color: red">*</strong>权限描述：</label>
                                <textarea class="input-xlarge" id="authority-details" name="authority-details" spellcheck="false"></textarea>
                                <p class="help-block" id="authority-details-msg">1~50字</p>
                            </li>
                            <li style="width: 600px;height: 42px">
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
                            <input type="text" id="search-key" name="authorityName" class="form-control" spellcheck="false" placeholder="请输入要查询的权限" onblur="initToOriTips()" onkeyup="searchAuthority()"><%--添加onkeyup="searchMember()"即可模糊查询--%>
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
                    <table class="table table-hover table-condensed table-bordered" id="authority-table">
                        <!--表头-->
                        <thead>
                        <tr>
                            <th style="width: 10px"><input type="checkbox" id="select-all" style="width: 20px"></th>
                            <th style="width: 70px">权限ID</th>
                            <th style="width: 150px">资源字段名</th>
                            <th style="width: 150px">权限名</th>
                            <th style="width: 150px">权限描述</th>
                            <th style="width: 70px">权限组ID</th>
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
                <input type="hidden" value="" id="id"><%--保存删除的id--%>
                <input type="hidden" value="" id="info"><%--保存删除的ids--%>
            </div>
        </div>
    </div>
</div>
<div class="md-overlay"></div>
<!-- 以下负责处理分页查询商品业务-->
<!--导入公共模板js-->
<script type="text/javascript" src="/js/notice.js"></script>
<script type="text/javascript" src="/js/page_split.js"></script>
<script type="text/javascript" src="/js/select_all.js"></script>
<script type="text/javascript" src="/js/operation_increase.js"></script>
<script type="text/javascript" src="/js/operation_edit.js"></script>
<script type="text/javascript" src="/js/operation_delete.js"></script>
<script type="text/javascript" src="/js/operation_batch.js"></script>
<script type="text/javascript" src="/js/search.js"></script>
<!--导入处理业务js-->
<script type="text/javascript" src="/js/authority_list.js"></script>
</body>
</html>
