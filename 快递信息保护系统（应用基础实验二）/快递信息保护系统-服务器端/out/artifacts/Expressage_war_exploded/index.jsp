<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>快递信息后台安全管理系统</title>
    <link href="public/css/style.css" rel="stylesheet" type="text/css" media="all" />
</head>
</head>
<body>
<div class="message warning">
    <div class="inset">
        <div class="login-head">
            <h1>快递信息后台管理系统</h1>
            <div class="alert-close"> </div>
        </div>
        <form action="logincheck.jsp" method="post" id="form">
            <li>
                <input type="text" class="text" value="" name="username" id="username"><a href="#" class=" icon user"></a>
            </li>
            <div class="clear"> </div>
            <li>
                <input type="password" value=""name="password"id="password"> <a href="#" class="icon lock"></a>
            </li>
            <div class="clear"> </div>
            <div class="submit">
                <%--<button class="button" id="submit" type="submit">登录</button>--%>
                <input type="submit" onclick="myFunction()" value="登陆" >
                <div class="clear">  </div>
            </div>

        </form>
    </div>
</div>
</div>
<div class="clear"> </div>
<div class="footer">
    <p>Copyright &copy; 杨文卓.</p>
</div>
</body>
</html>