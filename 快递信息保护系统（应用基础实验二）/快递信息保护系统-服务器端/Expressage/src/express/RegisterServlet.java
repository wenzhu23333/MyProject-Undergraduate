package express;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置客户端的解码方式为utf-8
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        boolean b = false;
        String id=request.getParameter("id");
        String password=request.getParameter("password");
        String email = request.getParameter("email");
        String idcard = request.getParameter("idcard");

        String result = "";

        b = new DBService().register(id,password,email,idcard);//待修改

        //new express.DBService().generateQRcode("1");
        PrintWriter out = response.getWriter();//回应请求

        if(b){
            result = "success";
        }
        else{
            result = "fail";
        }
        System.out.println(result+"123412");
        out.write(result);
        out.flush();
        out.close();
    }
}
