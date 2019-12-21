package Skey;

import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class skey  {
     private String r; //用户给服务器的r
     private  int n; //n次登陆
     private String[] hashlink = new String[n+1];

     public skey(int n,String r)
     {
         this.n = n;
         this.r = r;
         hashlink = new String[n+1];

     }
     public void CalHashlinkByMD5()
     {

        try {

            hashlink[0] = this.MD5(r);
            for (int i = 1; i < n + 1; i++) {
                hashlink[i] = this.MD5(hashlink[i-1]);
            }
        }
        catch (Exception e)
        {}
     }
     public String MD5(String src) throws Exception{
         byte[] input = src.getBytes();
             MessageDigest digest = MessageDigest.getInstance("MD5");
             digest.update(input);
             input = digest.digest();
             int length = input.length;
             StringBuffer strBuff = new StringBuffer();
             for (int i = 0; i < length; i++) {
                 int val = ((int) input[i]) & 0xff;
                 if (val < 16) {
                     strBuff.append("0");
                 }
                 strBuff.append(Integer.toHexString(val));
             }
             return strBuff.toString();

     }


    public void setN(int n) {
        this.n = n;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String[] getHashlink() {
        return hashlink;
    }

    public int getN() {
        return n;
    }


    public static void main(String[] args) throws Exception
    {
        String msg = JOptionPane.showInputDialog("请输入初始r");
        String s = JOptionPane.showInputDialog("请输入使用次数");
       int  n = Integer.parseInt(s);
        skey k = new skey(n,msg);
        k.CalHashlinkByMD5();
        String[] output = k.getHashlink();
        for (String e:output)
            System.out.println(e);
        int flag = 0;
        String newStr = output[n-1];
        int i = n;
        while(true)
        {
            newStr = output[i];
            String m = JOptionPane.showInputDialog("请输入口令");
            if(newStr.equals(k.MD5(m)))
                JOptionPane.showMessageDialog(null, "第" + (flag+1) + "次登陆成功!");
			else
           {
            JOptionPane.showMessageDialog(null,  "登陆失败!");
            continue;
           }
        if (flag == n-1) {
            JOptionPane.showMessageDialog(null, "动态口令已使用完毕");
            break;
        }

          flag++;
            i--;
        }
    }

}
