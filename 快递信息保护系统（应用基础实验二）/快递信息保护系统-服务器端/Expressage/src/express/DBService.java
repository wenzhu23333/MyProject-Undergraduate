package express;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBService {
//    public Boolean managerLogin(String username,String password){
//        String logSql = "select * from manager where name ='" + username
//                + "' and password ='" + password + "'";
//        express.DBManager sql = express.DBManager.createInstance();
//        sql.connectDB();
//        // 操作DB对象
//        try {
//            ResultSet rs = sql.executeQuery(logSql);
//            if (rs.next()) {
//                sql.closeDB();
//                return true;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        sql.closeDB();
//        return false;
//    }
    public String login(String username, String password) {

        // 获取Sql查询语句
        String logSql = "select * from user where username ='" + username
                + "' and password ='" + password + "'";

        // 获取DB对象
        DBManager sql = DBManager.createInstance();
        sql.connectDB();

        // 操作DB对象
        try {
            ResultSet rs = sql.executeQuery(logSql);
            if (rs.next()) {
                String pr = rs.getString("privateKey");

                sql.closeDB();
                return pr;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql.closeDB();
        return null;
    }

    public Boolean register(String username, String password,String mailbox,String idcard) {

        // 获取Sql查询语句
        String RepitiveSql = "select * from user where username='"+username+"'";
        RSAUtils rsaUtils = new RSAUtils();
        rsaUtils.generateKey();
        String pb = RSAUtils.getKeyString(rsaUtils.getPbkey());
        String pr = RSAUtils.getKeyString(rsaUtils.getPrkey());
        //注册时产生公钥和私钥
        String regSql = "insert into user(username,password,mailbox,publicKey,privateKey,idcard) " +
                "values('"+ username+ "','"+ password+"','"+mailbox+"','"+pb+"','"+pr+"','"+idcard+"')";

        // 获取DB对象
        DBManager sql = DBManager.createInstance();
        sql.connectDB();
        ResultSet rs = sql.executeQuery(RepitiveSql);
        try {
            if (!rs.next()){
                int ret = sql.executeUpdate(regSql);
                System.out.println(ret);
                if (ret != 0) {
                    sql.closeDB();
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        sql.closeDB();
        return false;
    }



}
