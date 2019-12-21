package servlet;

import db.DBUtilsAccount;
import db.DBUtilsMessage;
import domain.BaseBean;
import domain.FollowList;
import domain.messageList;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Follow")
public class Follow extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String follower = request.getParameter("username");
        System.out.println("follower = "+follower);
        response.setContentType("text/html;charset=utf-8");
        DBUtilsAccount dbUtils = new DBUtilsAccount();
        dbUtils.openConnect();
        DBUtilsMessage dbUtilm = new DBUtilsMessage();
        dbUtilm.openConnect();
        System.out.println("followed message preparing");
        BaseBean data = new BaseBean(); // 基类对象，回传给客户端的json对象
        FollowList mfollowlist = dbUtils.getfollow(follower);
        System.out.println("followed message doing");
        messageList mmessagelist = dbUtilm.getFollowMessage(mfollowlist,follower);
        System.out.println("followed message done");
        data.setData(mmessagelist);
        data.setMsg("followed message");
        JSONObject json1 = JSONObject.fromObject(data);
        String json = json1.toString();
        System.out.println(json);
        // 将对象转化成json字符串
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


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
