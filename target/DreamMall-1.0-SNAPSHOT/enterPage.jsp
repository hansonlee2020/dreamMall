<%--
  Created by IntelliJ IDEA.
  User: Hanson
  Date: 2020/3/16
  Time: 18:23
  Using for test
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>首页</title>
  <style>
    a{
      text-decoration: none;
      font-size: 18px;
      height: 18px;
      color: black;
    }
    h3{
      width:180px;
      height: 38px;
      margin: 100px auto;
      border-radius: 5px;
      line-height: 38px;
      background: antiquewhite;
      text-align: center;
    }
  </style>
</head>
<body>
<h3>
  <a href="#">测试通道1</a>
</h3>
<h3>
  <a href="#">测试通道2</a>
</h3>
<h3>
  <a href="${pageContext.request.contextPath}/toHome">进入到管理系统</a>
</h3>
</body>
</html>

