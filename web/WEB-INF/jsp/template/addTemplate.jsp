<%--
  Created by IntelliJ IDEA.
  User: HoosinLee
  Date: 2020/4/3
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>test</title>
    <link href="/css/addProduct.css" rel="stylesheet">
</head>
<body>
<form class="form-horizontal">
    <fieldset>
        <legend>Bootstrap 支持的控件</legend>
        <div class="control-group">
            <label class="control-label" for="input01">文本输入</label>
            <div class="controls">
                <input type="text" class="input-xlarge" id="input01">
                <p class="help-block">除了自由格式文本，一些HTML5基于文本的输入像这样呈现</p>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="optionsCheckbox">确认框</label>
            <div class="controls">
                <label class="checkbox">
                    <input type="checkbox" id="optionsCheckbox" value="option1">
                    选中该项，确认此项正确。
                </label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="select01">选择列表</label>
            <div class="controls">
                <select id="select01">
                    <option>something</option>
                    <option>2</option>
                    <option>3</option>
                    <option>4</option>
                    <option>5</option>
                </select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="multiSelect">多选</label>
            <div class="controls">
                <select multiple="multiple" id="multiSelect">
                    <option>1</option>
                    <option>2</option>
                    <option>3</option>
                    <option>4</option>
                    <option>5</option>
                </select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="fileInput">文件上传</label>
            <div class="controls">
                <input class="input-file" id="fileInput" type="file">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="textarea">文本区域</label>
            <div class="controls">
                <textarea class="input-xlarge" id="textarea" rows="3"></textarea>
            </div>
        </div>
        <div class="form-actions">
            <button type="submit" class="btn btn-primary">保存修改</button>
            <button class="btn">取消</button>
        </div>
    </fieldset>
</form>

</body>
</html>
