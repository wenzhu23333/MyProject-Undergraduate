package servlet;

import db.DBUtilsMessage;
import domain.BaseBean;
import domain.messageList;
import net.sf.json.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// import com.google.gson.Gson;

@WebServlet(name = "message")

public class message extends HttpServlet  {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(!request.getParameter("action").equalsIgnoreCase("getmessage"))
            return;
        response.setContentType("text/html;charset=utf-8");
        DBUtilsMessage dbUtils = new DBUtilsMessage();
        dbUtils.openConnect();
        // 打开数据库连接
        BaseBean data = new BaseBean(); // 基类对象，回传给客户端的json对象
        messageList mlist = new messageList();
        mlist = dbUtils.mlist();
        data.setData(mlist);
        data.setCode(125);
        data.setMsg("message shows");
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
    }

}
