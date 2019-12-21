<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="java.sql.*" %>
<%@page import="express.packageInfo" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>物流信息列表</title>
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
        <h3>快递信息表</h3>
        <table border="1">
            <caption>快递表</caption>
            <tr>
                <td>编号</td>
                <td>寄件人</td>
                <td>寄件人联系方式</td>
                <td>收件人</td>
                <td>收件人联系方式</td>
                <td>收件人地址</td>
                <td>快递员</td>
                <td>快递编号</td>
            </tr>
            <%
                List<packageInfo> packageAll = new ArrayList<>();
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/weibo","root","123456");
                    if(!conn.isClosed()) {
                        System.out.println("数据库连接成功！2");
                    }
                    String sql="SELECT * from package";
                    System.out.println(sql);
                    Statement stmt = (Statement)conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        packageInfo packageInfo= new packageInfo();
                        packageInfo.setClient(rs.getString("client"));
                        packageInfo.setAddress(rs.getString("address"));
                        packageInfo.setCourier(rs.getString("courier"));
                        packageInfo.setId(rs.getString("id"));
                        packageInfo.setSend(rs.getString("send"));
                        packageInfo.setPhone_send(rs.getString("phone_send"));
                        packageInfo.setPhone(rs.getString("phone"));
                        packageAll.add(packageInfo);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                int i=1;
                for(packageInfo stu:packageAll){
            %>
            <tr>
                <td>
                    <%=i++ %>
                </td>
                <td><%=stu.getSend()%></td>
                <td><%=stu.getPhone_send()%></td>
                <td><%=stu.getClient()%></td>
                <td><%=stu.getPhone() %></td>
                <td><%=stu.getAddress() %></td>
                <td><%=stu.getCourier() %></td>
                <td><%=stu.getId() %></td>
                <td><a href="modifyexpress.jsp?id=<%=stu.getId()%>">修改</a>
                    <a href="doDelete.jsp?id=<%=stu.getId()%>">删除</a>
                </td>
            </tr>
            <%}
            %>

        </table>
    </div>
</body>
</html>