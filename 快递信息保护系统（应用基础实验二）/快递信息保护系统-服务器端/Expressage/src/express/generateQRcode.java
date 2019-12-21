package express;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class generateQRcode {
    public static String getpublicKey(String courier){
        String publicKey = "";
        DBManager sql = DBManager.createInstance();
        sql.connectDB();
        String getPublicKeySql = "select publicKey from user where username='" + courier + "'";
        ResultSet rs2 = sql.executeQuery(getPublicKeySql);
        try {
            if (rs2.next()) {
                publicKey = rs2.getString("publicKey");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publicKey;
    }
    public static void generateQRcode(packageInfo packageInfo) {

      String  content = "姓名:"+packageInfo.getSend()+"(寄件人)\n"
                +"电话:"+packageInfo.getPhone_send()+"\n"
                +"姓名:"+packageInfo.getClient()+"(收件人)\n"
                +"电话:"+packageInfo.getPhone()+"\n"
                +"住址:"+packageInfo.getAddress();
        File file = new File("C:/Users/杨文卓/Desktop/QRcode/No." + packageInfo.getId() + ".png");
        String encrypted = null;
        try {
            encrypted = RSAUtils.EncryptToString(content, getpublicKey(packageInfo.getCourier()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(encrypted);
        try {
            QRCodeUtil.qrCodeEncode(encrypted, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

