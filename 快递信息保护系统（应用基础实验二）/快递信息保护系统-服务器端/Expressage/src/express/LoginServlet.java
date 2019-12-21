package express;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
}
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {

        //设置客户端的解码方式为utf-8
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //
        response.setCharacterEncoding("UTF-8");



        String b = null;
        Boolean a =false;
        //根据标示名获取JSP文件中表单所包含的参数
        String id=request.getParameter("id");
        String password=request.getParameter("password");
        String result = "";
        DBService dbService = new DBService();
        //..................测试
        //dbService.generateQRcode("2");
        //..................测试

        b = dbService.login(id,password);
        PrintWriter out = response.getWriter();
        if(!(b==null)){
            result = "success"+","+b;
        }
        else {
            result = "fail";
        }
        out.write(result);
        out.flush();
        out.close();
    }

}
