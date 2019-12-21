<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*" %>
<%@ page import="express.packageInfo" %>
<%@ page import="static express.generateQRcode.generateQRcode" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>验证页面</title>
</head>
<body>
<%
	request.setCharacterEncoding("UTF-8");
	String express_sendman = request.getParameter("express_sendman");
	String express_phone_sendman = request.getParameter("express_phone_sendman");
	String express_receiver = request.getParameter("express_receiver");
	String express_phone = request.getParameter("express_phone");
	String express_address = request.getParameter("express_address");
	String express_courier = request.getParameter("courier");

	//System.out.println(express_sendman+'\t'+express_receiver+'\t'+express_phone+'\t'+express_address+'\t');
	if(express_sendman !="" && express_receiver !="" && express_phone !="" &&express_address !=""
			&&express_phone_sendman!=""){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Success loading Mysql Driver!");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/weibo","root","123456");

			if(!conn.isClosed()) {
				System.out.println("数据库连接成功！2");
			}
			String sql="INSERT INTO package(send,phone_send,client,phone,address,courier) values('"+
					express_sendman+"','"+express_phone_sendman+"','"+express_receiver+"','"+express_phone+"','"+express_address+"','"+
					express_courier+"')";

            Statement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			((PreparedStatement) stmt).executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next()){
			    int id = rs.getInt(1);
                packageInfo packageInfo = new packageInfo(express_receiver,express_address,express_phone,express_courier,
                        String.valueOf(id), express_sendman,express_phone_sendman);
                generateQRcode(packageInfo);
				session.setAttribute("login","ok");
                session.setAttribute("addexpress","添加成功!");
         %>
         <jsp:forward page="createexpress.jsp"></jsp:forward>
         <%
			}
			else{
				out.println("添加失败!请重新输入");
				out.println("<a href = createexpress.jsp>返回</a>");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}else{
		out.println("不能有任何一项为空!");
		out.println("<a href = createexpress.jsp>返回</a>");
	}
	%>


</body>
</html>