<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
<%
    String id = request.getParameter("id");
    try{
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Success loading Mysql Driver!");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/weibo","root","123456");
        if(!conn.isClosed()) {
            System.out.println("数据库连接成功！2");
        }
        String sql="DELETE from user where idcard ='"+id+"'";
        Statement stmt = (Statement)conn.createStatement();
        Boolean rs = stmt.execute(sql);
        if(!rs){
            out.println("删除成功!");
            out.println("<a href = userlist.jsp>返回</a>");
        }else{
            out.println("删除失败!");
            out.println("<a href = userlist.jsp>返回</a>");
        }
    }catch(Exception e){
        e.printStackTrace();
    }
%>
</body>
</html>