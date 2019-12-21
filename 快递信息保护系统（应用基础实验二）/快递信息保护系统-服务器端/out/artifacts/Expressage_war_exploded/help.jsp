<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Help</title>
<link href="public/css/loginSuccess.css" rel="stylesheet" type="text/css">
<style>
#round
{	position: absolute;
	right:400px;
	bottom:400px;
	border:15px solid transparent;
	width:250px;
	padding:10px 20px;
}
</style>
</head>
<body>
	<!-- <h1>管理员，欢迎你!</h1>
	<h2>你的用户名是:<%=session.getAttribute("uname") %></h2> -->
	<!--下面是顶部导航栏的代码-->
	<div class="head">
		<head_ul>
		  <head_li><a href="loginSuccess.jsp">首页</a></head_li>
		  <head_li style="float:right;margin-right:10px;"><a href="index.jsp">注销</a></head_li>
		</head_ul>                   
	</div>
	<!--下面是左边导航栏的代码-->
	<div class="left">
		<left_ul>
			<left_li><a href="createexpress.jsp">添加快递单</a></left_li>
			<left_li><a href="expresslist.jsp">快递单列表</a></left_li>
			<left_li><a href="userlist.jsp">快递员列表</a></left_li>
			<left_li><a href="help.jsp">帮助</a></left_li>
		</left_ul>
	</div> 
	<!--下面是右边导航栏的代码-->
	<div id="round">联系我：769586919（QQ）</div>
</body>
</html>