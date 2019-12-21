package servlet;

import db.DBUtilsUserInfo;
import domain.BaseBean;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "userInfo")
public class userInfo extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String name = request.getParameter("name");
        DBUtilsUserInfo dbUtils = new DBUtilsUserInfo();
        dbUtils.openConnect();
        BaseBean data = new BaseBean(); // 基类对象，回传给客户端的json对象
        data.setData(dbUtils.getUserInfo(name));
        data.setMsg("查询成功");
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


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);

    }
}
