<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.*" %>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
<link href="public/css/loginSuccess.css" rel="stylesheet" type="text/css">
<style>
#round
{	position: absolute;
	right:1000px;
	bottom:800px;
	border:15px solid transparent;
	width:250px;
	padding:10px 20px;
}
</style>
</head>
<body>
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
	<div id="round">
	管理员，欢迎你!<br>
	你的用户名是:<%=session.getAttribute("uname") %><br>
	<%
	Date d = new Date();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String now = df.format(d);%>
	当前时间:<%=now %>
	</div>
	<img src="public/images/border.png"/>
</body>
</html>