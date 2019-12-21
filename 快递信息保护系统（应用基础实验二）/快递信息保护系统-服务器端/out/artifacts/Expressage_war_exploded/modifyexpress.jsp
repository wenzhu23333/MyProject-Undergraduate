<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="express.packageInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加快递单</title>
    <link href="public/css/loginSuccess.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        #express_form{
            position: absolute;
            left:300px;
            top:100px;
            border:15px solid transparent;
            width:500px;
            height:580px;
            background:#FFFFFF;
        }
    </style>
</head>
<body>
<%
    packageInfo packageInfo= new packageInfo();
    String id = request.getParameter("id");
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/weibo","root","123456");
    String sql="select * from package where id ='"+id+"'";

    Statement stmt = (Statement)conn.createStatement();
    if(!conn.isClosed()) {
        System.out.println("数据库连接成功！2");
    }
    ResultSet rs = stmt.executeQuery(sql);

    while(rs.next()){
        packageInfo.setClient(rs.getString("client"));
        packageInfo.setAddress(rs.getString("address"));
        packageInfo.setCourier(rs.getString("courier"));
        packageInfo.setId(rs.getString("id"));
        packageInfo.setSend(rs.getString("send"));
        packageInfo.setPhone_send(rs.getString("phone_send"));
        packageInfo.setPhone(rs.getString("phone"));
    }

%>
<%
    String sql2 = "select username from user";
    List<String> usernameList = new ArrayList<>();
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/weibo","root","123456");
    Statement stmt2 = (Statement)conn2.createStatement();
    ResultSet rs2 = stmt2.executeQuery(sql2);
    while (rs2.next()){
        usernameList.add(rs2.getString("username"));
    }
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
    <form action="modifyAction.jsp" method="post" id="form">
        <div id="express_form">
            <div class="input_control">
                寄件人：
                <input class="text_value" value="<%=packageInfo.getSend()%>" name="express_sendman" type="text" id="express_sendman">
            </div>
            <div class="input_control">
                寄件人联系方式：
                <input class="text_value" value="<%=packageInfo.getPhone_send()%>" name="express_phone_sendman" type="text" id="express_phone_sendman">
            </div>
            <div class="input_control">
                收件人：
                <input class="text_value" value="<%=packageInfo.getClient()%>" name="express_receiver" type="text" id="express_receiver">
            </div>
            <div class="input_control">
                收件人联系方式:
                <input class="text_value" value="<%=packageInfo.getPhone()%>" name="express_phone" type="text" id="express_phone">
            </div>
            <div class="input_control">
                联系地址:
                <input class="text_value" value="<%=packageInfo.getAddress()%>" name="express_address" type="text" id="express_address">
            </div>
            <div class="input_control">
                快递员：
                <select name="courier" id="courier">
                    <% int i=1;
                        for(String stu:usernameList){
                            System.out.println(stu);
                    %>
                    <%=i++ %>
                    <option value="<%=stu%>"><%=stu%></option>
                    <%}
                    %>
                </select>
            </div>
            <div style="text-align:right">
                <button class="button" value="<%=id%>" name="submit" id="submit" type="submit">修改</button><br>
            </div>
            <div style="text-align:right">
                快递单的状态:<%=session.getAttribute("modifyAction")%>
            </div>

        </div>
    </form>
</div>
</body>
</html>