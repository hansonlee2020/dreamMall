<%--
  Created by IntelliJ IDEA.
  User: HoosinLee
  Date: 2020/4/25
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>用于测试订单信息</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <!--自定义css-->
    <link href="../../css/my.css" rel="stylesheet" type="text/css">
    <link href="../../css/list.css" rel="stylesheet" type="text/css">
    <link href="../../css/notice.css" rel="stylesheet" type="text/css">
    <link href="../../css/edit.css" rel="stylesheet" type="text/css">
    <!--分页插件css-->
    <link href="../../css/qunit-1.11.0.css" rel="stylesheet" type="text/css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!--引入第三方动态插件js-->
    <script type="text/javascript" src="../../js/other/jquery.niftymodals.js"></script>
    <!--分页插件js-->
    <script type="text/javascript" src="../../js/other/bootstrap-paginator.js"></script>
    <script type="text/javascript" src="../../js/other/qunit-1.11.0.js"></script>
    <style>
        tr > th{
            font-size: 16px;
        }
        ul{
            list-style: none;
        }
        li{
            display: inline-block;
            width: 30%;
            margin-right: 35px;
            height: 32px;
            margin-bottom: 5px;
        }
        li > span{
            line-height: 30px;
            font-size: 16px;
        }
        li > label{
            width: 80px;
        }
        div > span{
            line-height: unset;
            font-size: 16px;
        }
        .print-order{
            text-align: center;
            margin-top: 20px;
        }
        .print-order > input{
            outline: none;
            border: 0 solid;
        }
    </style>
</head>
<body>
<!--右侧内容展示栏-->
<div class="section" style="background-color: whitesmoke">
    <div class="context-box">
        <div class="container">
            <!--列表顶栏-->
            <div class="row clearfix">
                <div class="title">
                    <a class="btn btn-link right-back" id="back-off" onclick="backOff()">返回</a>
                    <h1><small>打印预览</small></h1>
                </div>
            </div>
            <!--订单详情-->
            <div class="row clearfix list">
                <div class="col-md-24 column">
                    <h2 style="text-align: center;margin-bottom: 40px">DREAMMALL订单信息</h2>
                    <div style="background-color: whitesmoke" id="order-info">
                        <ul>
                            <!--订单数据-->
                        </ul>
                    </div>
                </div>
            </div>
            <!--订单商品列表-->
            <div class="row clearfix list">
                <div class="col-md-24 column">
                    <input type="hidden" id="order-id" value="${orderId}">
                    <table class="table table-hover table-condensed table-bordered" id="order-table">
                        <!--表头-->
                        <thead>
                        <tr>
                            <th style="width: 150px">商品名称</th>
                            <th style="width: 150px">商品ID</th>
                            <th style="width: 80px">价格</th>
                            <th style="width: 80px">数量</th>
                            <th style="width: 80px">小计</th>
                        </tr>
                        </thead>

                        <tbody>
                        <!--商品列表数据-->
                        </tbody>
                    </table>
                </div>
            </div>
            <!--底部信息-->
            <div class="row clearfix list">
                <div class="col-md-24 column">
                    <!--统计-->
                    <div style="height: 50px">
                        <div style="float: right" id="total-price">
                        </div>
                    </div>
                    <!--备注-->
                    <div style="word-wrap: break-word" id="notes">
                    </div>
                </div>
            </div>
        </div>
        <div class="print-order">
            <input class="btn btn-primary radius" value="打印" onclick="print()">
        </div>
    </div>
</div>
<!-- 以下负责处理分页查询商品业务-->
<!--导入处理业务js-->
<script type="text/javascript" src="../../js/order_print.js"></script>

</body>
</html>
