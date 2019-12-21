<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="java.sql.*" %>
<%@page import="express.packageInfo" %>
<%@ page import="express.userCourier" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>快递员列表</title>
    <link href="public/css/loginSuccess.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        #round{
            position: absolute;
            left:400px;
            top:60px;
            border:15px solid transparent;
            width:1050px;
            height:600px;
            padding:10px 20px;
        }
        #express_form{
            position: absolute;
            left:0px;
            top:10px;
            border:15px solid transparent;
            width:1050px;
            height:580px;
        }
    </style>
</head>
<body>
<%

%>
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
    <div id="express_form">
        <h3>快递员信息表</h3>
        <table border="1">
            <caption>快递员信息</caption>
            <tr>
                <td>编号</td>
                <td>姓名</td>
                <td>密码</td>
                <td>电话</td>
                <td>身份证号</td>
            </tr>
            <%
                List<userCourier> packageAll = new ArrayList<>();
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/weibo","root","123456");
                    if(!conn.isClosed()) {
                        System.out.println("数据库连接成功！2");
                    }
                    String sql="SELECT * from user";
                    Statement stmt = (Statement)conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        userCourier userCourier = new userCourier();
                        userCourier.setIdcard(rs.getString("idcard"));
                        userCourier.setPassword(rs.getString("password"));
                        userCourier.setPhone(rs.getString("mailbox"));
                        userCourier.setUsername(rs.getString("username"));
                        packageAll.add(userCourier);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                int i=1;
                for(userCourier stu:packageAll){
            %>
            <tr>
                <td>
                    <%=i++ %>
                </td>
                <td><%=stu.getUsername()%></td>
                <td><%=stu.getPassword()%></td>
                <td><%=stu.getPhone()%></td>
                <td><%=stu.getIdcard() %></td>
                <td>
                    <a href="doDelete2.jsp?id=<%=stu.getIdcard()%>">删除</a>
                </td>
            </tr>
            <%}
            %>
        </table>
    </div>
</body>
</html>