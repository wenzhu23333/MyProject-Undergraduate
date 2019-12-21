package servlet;

import db.DBUtilsComment;
import domain.BaseBean;
import domain.commentList;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "comment")
public class comment extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!request.getParameter("action").equalsIgnoreCase("getcomment"))
            return;
        String to = request.getParameter("to"); // 获取客户端传过来的参数
        response.setContentType("text/html;charset=utf-8");
        if (to.equals("")) {
            System.out.println("to为空");
            return;
        }
        DBUtilsComment dbUtils = new DBUtilsComment();
        dbUtils.openConnect();
        // 打开数据库连接
        BaseBean data = new BaseBean(); // 基类对象，回传给客户端的json对象
        commentList clist = new commentList();
        clist = dbUtils.getcList(to);
        data.setData(clist);
        data.setCode(215);
        data.setMsg("comment");
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





    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
