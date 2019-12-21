<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>验证页面</title>
</head>
<body>
	<%
	String username = request.getParameter("username");
	String password = request.getParameter("password");
		if(username !=null && password !=null){
			try{
				Class.forName("com.mysql.jdbc.Driver");
				System.out.println("Success loading Mysql Driver!");
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/weibo","root","123456");
				if(!conn.isClosed()) {
					System.out.println("数据库连接成功！2");
				}
				String sql="select password from manager where username="+"'"+username+"'";
				sql +="and password='"+password+"'";
				System.out.println(sql);
				Statement stmt = (Statement)conn.createStatement();
				ResultSet rs = (ResultSet)stmt.executeQuery(sql);
				if(rs.next()){
					session.setAttribute("login","ok");
					session.setAttribute("uname",username);
	%>
	<jsp:forward page="loginSuccess.jsp"></jsp:forward>
	<%
				}
				else{
					out.println("错误的用户名和密码!");
					out.println("<a href = index.jsp>返回</a>");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			out.println("请先登录");
			out.println("<a href = index.jsp>返回</a>");
		}
	%>

</body>
</html>