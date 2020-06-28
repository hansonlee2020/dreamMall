<%--
  Created by IntelliJ IDEA.
  User: HoosinLee
  Date: 2020/4/15
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>编辑商品</title>
    <%--bootstrap--%>
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <%--引入自定义样式--%>
    <link href="/css/my.css" rel="stylesheet" type="text/css">
    <link href="/css/index.css" rel="stylesheet" type="text/css">
    <link href="/css/addProduct.css" rel="stylesheet" type="text/css">
    <link href="/css/notice.css" rel="stylesheet" type="text/css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script type="text/javascript" src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script type="text/javascript" src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!--引入第三方动态插件js-->
    <script type="text/javascript" src="/js/other/jquery.niftymodals.js"></script>
    <style>
    </style>
</head>
<body>
<!--主体-->
<div class="section">
    <div class="right-back">
        <a class="btn btn-link" style="display: inline-block" href="${pageContext.request.contextPath}/toHome">回到首页</a>
    </div>
    <div class="context-box">
        <div class="container">
            <div class="form-div">
                <form class="form-horizontal" id="add-product-form">
                    <fieldset>
                        <legend>
                            <strong>编辑商品:</strong>
                        </legend>
                        <div class="control-group">
                            <label class="control-label" for="product-name"><label class="symbol">*</label>商品名称:</label>
                            <div class="controls">
                                <input type="text" class="input-xlarge" id="product-name" name="product-name" required spellcheck="false" value="${goods.productName}">
                                <p class="help-block" id="name-msg">可输入1~50个中文字符</p>
                                <input type="hidden" value="${goods.productId}" id="product-id">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="brief-info"><label class="symbol">*</label>商品简介:</label>
                            <div class="controls">
                                <textarea class="input-xlarge" id="brief-info" name="brief-info" rows="3" required spellcheck="false"></textarea>
                                <input type="hidden" id="brief" value="${goods.briefInfo}">
                                <p class="help-block" id="info-msg">最多可输入50个字</p>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="price"><label class="symbol">*</label>商品价格:</label>
                            <div class="controls">
                                <input type="text" class="input-xlarge" id="price" name="price" required spellcheck="false" value="${goods.price}">
                                <p class="help-block" id="price-msg">最大可设置为：9,999,999,999,999.99</p>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="stock"><label class="symbol">*</label>库存数量:</label>
                            <div class="controls">
                                <input type="text" class="input-xlarge" id="stock" name="stock" required spellcheck="false" value="${goods.stock}">
                                <p class="help-block" id="stock-msg">最大可设置为：9,999,999,999,999</p>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="limit-num"><label class="symbol">*</label>购买限制数量:</label>
                            <div class="controls">
                                <input type="text" class="input-xlarge" id="limit-num" name="limit-num" required spellcheck="false" value="${goods.limitNum}">
                                <p class="help-block" id="limit-num-msg">最大可设置为：9,999,999,999,999</p>
                            </div>
                        </div>
                        <div class="category">
                            <div class="control-group"><!--单选-->
                                <label class="control-label" for="high-level-category"><label class="symbol">*</label>选择一级分类:</label>
                                <div class="controls">
                                    <select id="high-level-category" name="first-category" required>
                                    </select>
                                </div>
                                <input type="hidden" value="${goods.category1}" id="category-1">
                            </div>
                            <div class="control-group" style="float: right;margin-left: 20px"><!--单选-->
                                <label class="control-label" for="lower-level-category"><label class="symbol">*</label>选择二级分类:</label>
                                <div class="controls">
                                    <select id="lower-level-category" name="second-category" required>
                                    </select>
                                </div>
                                <input type="hidden" value="${goods.category2}" id="category-2">
                            </div>
                            <div class="control-group" style="float: right;margin-left: 20px;margin-top: 15px"><!--单选-->
                                <label class="control-label" for="low-level-category"><label class="symbol">*</label>选择子分类:</label>
                                <div class="controls">
                                    <select id="low-level-category" name="third-category" required>
                                    </select>
                                </div>
                                <input type="hidden" value="${goods.category3}" id="category-3">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="release-checkbox">是否立即发布:</label>
                            <div class="controls">
                                <label class="checkbox" onclick="selected()">
                                    <input type="checkbox" id="release-checkbox" value="1" spellcheck="false">
                                    选中该项，将直接通过审核并立即发布商品，需要管理员权限！
                                    <input type="hidden" id="release" name="release-state" value="0" spellcheck="false">
                                </label>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="show-image"><label class="symbol">*</label>展示图片上传:</label>
                            <div class="controls">
                                <input class="input-file" id="show-image" name="image-src" type="file" spellcheck="false" disabled>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="product-details"><label class="symbol">*</label>商品详情:</label>
                            <div class="controls">
                                <textarea class="input-xlarge" id="product-details" name="product-details" rows="3" required spellcheck="false"></textarea>
                                <input type="hidden" value="${goods.productDetails}" id="details">
                                <p class="help-block" id="details-msg">最多可输入200个字</p>
                            </div>
                        </div>
                        <div class="form-actions">
                            <button type="button" class="btn btn-primary" id="put" data-modal="message-window" onclick="confirmEdit(this)" disabled>保存修改</button>
                            <a class="btn btn-primary" id="back-link" href="/product/goods/toList">取消</a>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
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
                    <span id="msg">你能看到这条信息，说明.......后台在偷懒！</span>
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
<!-- 以下负责处理分页查询商品业务-->
<script type="text/javascript" src="/js/notice.js"></script>
<script type="text/javascript" src="/js/checkProduct.js"></script>
<script type="text/javascript" src="/js/editProduct.js"></script>
</body>
</html>

