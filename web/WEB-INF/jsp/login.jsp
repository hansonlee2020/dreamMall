<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>梦想购物中心后台登陆</title>
    <!--引入bootstrap.css-->
<%--    <link type="text/css" href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <!--引入字体库-->
    <link rel="stylesheet" type="text/css" href="../../font/fontawesome-free-5.13.0/css/fontawesome.css">
    <link rel="stylesheet" type="text/css" href="../../font/iconic/css/material-design-iconic-font.css">
<%--    <link type="text/css" href="http://apps.bdimg.com/libs/fontawesome/4.7.0/css/font-awesome.min.css">--%>
<%--    <link type="text/css" href="https://cdn.bootcdn.net/ajax/libs/material-design-iconic-font/2.2.0/css/material-design-iconic-font.css">--%>
    <link rel="stylesheet" type="text/css" href="css/util.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <!--引入自定义css-->
    <link rel="stylesheet" type="text/css" href="css/login.css">
    <!--验证-->
    <script type="text/javascript">
        function validate_input(field) {
            with (field) {
                if(value==null||value==""){
                    return false
                }else {
                    return true
                }
            }
        }
        function validate_form(thisform) {
            with (thisform) {
                if(validate_input(userName)==false){
                    userName.focus();
                    return false;
                }
                if(validate_input(userPwd)==false){
                    userPwd.focus();
                    return false
                }
            }
        }
    </script>
</head>
<body>
<div class="limiter">
    <div class="container-login100" style="background-image: url('/images/bg-login-02.jpg')">
        <div class="header">
            <span class="login100-form-title p-b-49">网上商城后台信息管理系统</span>
        </div>

        <div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-54">
            <form class="login100-form validate-form" action="${pageContext.request.contextPath}/login" method="post" onsubmit="validate_form(this);encryptToMD5()" id="form1">
                <span class="login100-form-title p-b-49" style="font-size: 30px">登&nbsp;&nbsp;录</span>

                <div class="wrap-input100 validate-input m-b-23" data-validate="请输入用户名">
                    <span class="label-input100">用户名</span>
                    <input class="input100" type="text" name="userName" id="name" placeholder="请输入用户名" autocomplete="off">
                    <span class="focus-input100" data-symbol="&#xf206;"></span>
                </div>

                <div class="wrap-input100 validate-input" data-validate="请输入密码">
                    <span class="label-input100">密&nbsp;&nbsp;码</span>
                    <input class="input100" type="password" name="userPwd" id="pwd" placeholder="请输入密码">
                    <span class="focus-input100" data-symbol="&#xf190;"></span>
                </div>

                <div class="text-right p-t-8 p-b-31" style="padding-bottom: 0">
                    <a href="javascript:" id="forget" disabled="disabled">忘记密码？</a>
                </div>

                <div class="flex-col-c p-t-25 error-msg">
                    <span class="txt2">${errorMsg}</span>
                </div>

                <div class="container-login100-form-btn">
                    <div class="wrap-login100-form-btn">
                        <div class="login100-form-bgbtn"></div>
                        <button class="login100-form-btn" style="background-color: lightgreen" id="login">登&nbsp;&nbsp;陆</button>
                    </div>
                </div>

                <div class="flex-col-c p-t-25">
                    <a href="javascript:" class="txt2" id="test-account">体验账号</a>
                </div>
            </form>
        </div>
        <!--版权及备案信息-->
        <div class="footer">
            <span style="line-height: 50px;color: grey">© 2020 Hanson
                <a style="text-decoration: none" href="http://www.beian.miit.gov.cn/">
                    &nbsp;&nbsp;粤ICP备20043119号
                </a>
            </span>
        </div>
    </div>
</div>
<!--引入js库-->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!--登陆业务处理-->
<script src="js/main.js"></script>
<script type="text/javascript" src="js/md5.js"></script>
<script type="text/javascript" src="js/login.js"></script>
</body>
</html>
