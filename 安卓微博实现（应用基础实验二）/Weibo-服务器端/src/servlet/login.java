package servlet;

import db.DBUtilsAccount;
import db.DBUtilsUserInfo;
import domain.BaseBean;
import domain.UserInfo;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "login")
public class login extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username"); // 获取客户端传过来的参数
        String password = request.getParameter("password");
        response.setContentType("text/html;charset=utf-8");
        System.out.println("user + pass " + username +" "+password);
        if (username == null || username.equals("") || password == null || password.equals("")) {
            System.out.println("null account or password");
            return;
        } // 请求数据库
        DBUtilsAccount dbUtils = new DBUtilsAccount();
        DBUtilsUserInfo dbUtilsInfo = new DBUtilsUserInfo();
        dbUtils.openConnect();
        dbUtilsInfo.openConnect();
        // 打开数据库连接
        BaseBean data = new BaseBean(); // 基类对象，回传给客户端的json对象

        if (dbUtils.isExistInDB(username, password)) {
            // 判断账号密码是否正确
            data.setCode(20);
            UserInfo muserinfo = dbUtilsInfo.getUserInfo(username);
            muserinfo.setFollowednum(dbUtils.getFollowedNum(username));
            data.setData(muserinfo);
            data.setMsg("登陆成功");
        } else {
            data.setCode(-2);
            data.setMsg("incorrect account or password");
        }
        JSONObject json1 = JSONObject.fromObject(data);
        String json = json1.toString();
        // 将对象转化成json字符串
        if(data.getCode()==-2){
            response.getWriter().close(); // 关闭这个流，不然会发生错误的
            dbUtils.closeConnect(); // 关闭数据库连接}
            return;
        }
        else{
            try {
                response.getWriter().println(json);
                // 将json数据传给客户端
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.getWriter().close(); // 关闭这个流，不然会发生错误的
                dbUtils.closeConnect(); // 关闭数据库连接}}

            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
