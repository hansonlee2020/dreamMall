<%--
  Created by IntelliJ IDEA.
  User: HoosinLee
  Date: 2020/4/24
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>订单列表</title>
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
        td > span{
            font-size: 10px;
        }
        li{
            position: relative;
        }
        li > label{
            position: absolute;
        }
        li > input , li > select , li > div{
            margin-left: 170px;
        }
    </style>
</head>
<body>
<!--右侧内容展示栏-->
<div class="section">
    <div class="context-box">
        <div class="container">
            <!--列表顶栏-->
            <div class="row clearfix">
                <div class="title">
                    <a class="btn btn-link right-back" href="/toHome">返回首页</a>
                    <h1><small>订单列表</small></h1>
                </div>
                <div class="row">
                    <!--nav-->
                    <div class="col-md-4 column" style="padding-top: 10px">
                        <a class="btn btn-info" href="/order/toTrash">
                            <span class="glyphicon glyphicon-trash" style="font-size: 14px"></span>
                            回收站
                        </a>
                        <a class="btn btn-info" data-toggle="collapse" data-target="#remark-info" aria-expanded="false" aria-controls="remark-info" onclick="addLogistics()" style="outline: none;border: 0 solid">
                            <span class="glyphicon glyphicon-plus" style="font-size: 14px"></span>
                            添加物流
                        </a>
                        <a class="btn btn-info" href="#" title="订单打印" data-modal="message-window" onclick="printThis(this)">
                            <span class="glyphicon glyphicon-print" style="font-size: 14px"></span>
                            订单打印
                        </a>
                    </div>
                </div>
                <!--修改备注、发货-->
                <div class="collapse" id="remark-info">
                    <div class="well" style="background-color: lightgoldenrodyellow;border-color: lightgoldenrodyellow">
                        <ul style="list-style: none" class="edit-list">
                            <li class="order-info">
                                <label>订单id：</label>
                                <input type="text" id="order-id" name="order-id" readonly="readonly" value="9999">
                            </li>
                            <li class="order-info">
                                <label>用户账号：</label>
                                <input type="text" id="user-id" name="user-id" spellcheck="false" readonly="readonly">
                            </li>
                            <li style="position: relative" class="remark">
                                <label style="position: absolute"><strong style="color: red">*</strong>备注：</label>
                                <textarea class="input-xlarge" id="order-remark" name="order-remark" spellcheck="false"></textarea>
                                <p class="help-block" id="order-remark-msg">1~50字</p>
                            </li>
                            <li class="logistics" hidden>
                                <label><strong style="color: red">*</strong>物流选择：</label>
                                <select id="logistics-choice" name="logistics-choice" required style="width: 280px;background-color: lightgoldenrodyellow">
                                </select>
                            </li>
                            <li class="logistics" hidden>
                                <label><strong style="color: red">*</strong>物流号：</label>
                                <input type="text" id="logistics-id" name="logistics-id" spellcheck="false" required>
                                <p class="help-block" id="logistics-id-msg">1~20数字</p>
                            </li>
                            <li class="add-logistics" hidden>
                                <label><strong style="color: red">*</strong>物流公司：</label>
                                <input type="text" id="logistics-name" name="logistics-name" spellcheck="false" required>
                                <p class="help-block" id="logistics-name-msg">1~20字符</p>
                            </li>
                            <li style="width: 600px;height: 42px">
                                <label></label>
                                <div style="display: inline-block;left: 100px;font-size: 16px">
                                    <input type="button" style="float: right" id="reset-btn" value="重置" onmouseover="changeBtn(this)" onmouseout="initBtn(this)" onclick="reset()">
                                    <input type="button" id="put-btn" value="发货" data-modal="message-window" onmouseover="changeBtn(this)" onmouseout="initBtn(this)" onclick="send(this)">
                                    <input type="hidden" id="edit-btn" value="修改" data-modal="message-window" onmouseover="changeBtn(this)" onmouseout="initBtn(this)" onclick="edit(this)">
                                    <input type="hidden" id="add-btn" value="添加" data-modal="message-window" onmouseover="changeBtn(this)" onmouseout="initBtn(this)" onclick="newAdd(this)">
                                </div>
                            </li>
                            <li style="position: unset"><label style="position: unset">处理结果：</label><span id="result-details"></span></li>
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
                        <!--搜索-->
                        <form class="form-inline" action="#" method="post" onsubmit="return false">
                            <span id="error-msg" style="font-size: 12px"></span>
                            <input type="text" id="search-key" name="orderId" class="form-control" spellcheck="false" placeholder="请输入要查询的订单号" onblur="initToOriTips()"><%--添加onkeyup="searchMember()"即可模糊查询--%>
                            <a class="btn btn-info" onclick="searchOrder()">
                                <span class="glyphicon glyphicon-search" style="font-size: 14px"></span>
                                搜索订单
                            </a>
                        </form>
                    </div>
                </div>
            </div>
            <!--列表-->
            <div class="row clearfix list">
                <div class="col-md-24 column">
                    <input type="hidden" value="" id="ids-to-do">
                    <table class="table table-hover table-condensed table-bordered" id="order-table">
                        <!--表头-->
                        <thead>
                        <tr>
                            <th style="width: 10px"><input type="checkbox" id="select-all" style="width: 20px"></th>
                            <th style="width: 150px">订单号</th>
                            <th style="width: 80px">用户账号</th>
                            <th style="width: 150px">物流号</th>
                            <th style="width: 150px">备注</th>
                            <th style="width: 80px">创建时间</th>
                            <th style="width: 80px">支付时间</th>
                            <th style="width: 80px">关闭时间</th>
                            <th style="width: 80px">完成时间</th>
                            <th style="width: 80px">支付金额</th>
                            <th style="width: 50px">订单状态</th>
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
                        <ul class="pagination" id="order-split">
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
                <input type="hidden" value="" id="id"><%--保存数据的id--%>
                <input type="hidden" value="" id="info"><%--保存数据的ids--%>
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
<script type="text/javascript" src="/js/operation_transfer.js"></script>
<script type="text/javascript" src="/js/operation_edit.js"></script>
<script type="text/javascript" src="/js/operation_increase.js"></script>
<script type="text/javascript" src="/js/search.js"></script>
<!--导入处理业务js-->
<script type="text/javascript" src="/js/order_list.js"></script>

</body>
</html>
