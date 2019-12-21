package servlet;


import db.DBUtilsAccount;
import db.DBUtilsUserInfo;
import domain.BaseBean;
import domain.UserBean;
import net.sf.json.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

// import com.google.gson.Gson;


@WebServlet(name = "register")

public class register extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("request--->"+request.getRequestURL()+"===="+request.getParameterMap().toString());

        String username = request.getParameter("username"); // 获取客户端传过来的参数
        String password = request.getParameter("password");
        response.setContentType("text/html;charset=utf-8");
        if (username == null || username.equals("") || password == null || password.equals("")) {
            System.out.println("用户名或密码为空");
            return;
        } // 请求数据库
        DBUtilsAccount dbUtils = new DBUtilsAccount();
        DBUtilsUserInfo dbinfo = new DBUtilsUserInfo();
        dbUtils.openConnect();
        dbinfo.openConnect();
        // 打开数据库连接
        BaseBean data = new BaseBean(); // 基类对象，回传给客户端的json对象
        UserBean userBean = new UserBean(); // user的对象
        if (dbUtils.isExistInDBA(username)) {
            // 判断账号是否存在
            data.setCode(-1);
            data.setData(userBean);
            data.setMsg("该账号已存在");
        } else if (!dbUtils.insertDataToDB(username, password)) {
            // 注册成功
            data.setCode(0);
            data.setMsg("注册成功!!");
            dbinfo.iniviteUserInfo(username);
            ResultSet rs = dbUtils.getUser();
            int id = -1;
            if (rs != null) {
                try {
                    while (rs.next()) {
                        if (rs.getString("username").equals(username) && rs.getString("password").equals(password)) {
                            id = rs.getInt("id");
                        }
                    }
                    userBean.setId(id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            data.setData(dbinfo.getUserInfo(username));
        } else {
            // 注册不成功，这里错误没有细分，都归为数据库错误
            data.setCode(500);
            data.setData(userBean);
            data.setMsg("数据库错误");
        }
        if(data.getCode()==-1||data.getCode()==500){
            response.getWriter().close(); // 关闭这个流，不然会发生错误的
            dbUtils.closeConnect(); // 关闭数据库连接}
            dbinfo.closeConnect();
            return;
        }
   //     Gson gson = new Gson();
       // String json = gson.toJson(data);
        JSONObject json1 = JSONObject.fromObject(data);
        String json = json1.toString();
        // 将对象转化成json字符串
        try {
            response.getWriter().println(json);
            // 将json数据传给客户端
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.getWriter().close(); // 关闭这个流，不然会发生错误的
        }
        dbUtils.closeConnect(); // 关闭数据库连接}
        dbinfo.closeConnect();
    }


}
