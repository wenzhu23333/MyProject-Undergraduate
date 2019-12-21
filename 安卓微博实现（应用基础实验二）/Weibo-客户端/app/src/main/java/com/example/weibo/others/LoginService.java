package com.example.weibo.others;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class LoginService {

    public static byte[] getUrlBytes(String urlSpec) throws IOException{
        URL url=new URL(urlSpec);
        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
        connection.setConnectTimeout(8000);
        try{
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            InputStream in=connection.getInputStream();
            if(connection.getResponseCode()!=HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage()+":with "+urlSpec);
            }
            int bytesRead=0;
            byte[] buffer=new byte[1024];
            while((bytesRead=in.read(buffer))>0){
                out.write(buffer,0,bytesRead);
            }
            out.close();
            return out.toByteArray();
        }finally {
            connection.disconnect();
        }
    }

    public static String getUrlString(String urlSpec) throws IOException{
        return new String(getUrlBytes(urlSpec));
    }

    public static String fetchLoginItems(String username,String password){//登录
        try {
            String url = Uri.parse("http://10.0.0.2:8080/test2/loginServlet")
                    .buildUpon()
                    .appendQueryParameter("action", "login")
                    .appendQueryParameter("username",username)
                    .appendQueryParameter("password",password)
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i("LoginService", "Received JSON: " + jsonString);
            return jsonString;
        }catch (IOException ioe){
            Log.e("LoginService","Failed to fetch items",ioe);
        }
        return null;
    }


    public static String fetchRegisterItems(String username,String password){//注册
        try {
            String url = Uri.parse("http://10.0.0.2:8080/test2/registerDateServlet")
                    .buildUpon()
                    .appendQueryParameter("action", "register")
                    .appendQueryParameter("username",username)
                    .appendQueryParameter("password",password)
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i("LoginService", "Received JSON: " + jsonString);
            return jsonString;
        }catch (IOException ioe){
            Log.e("LoginService","Failed to fetch items",ioe);
        }
        return null;
    }

    public static String writeWeiboItems(String username,String message){//写微博
        try {
            String url = Uri.parse("http://10.0.0.2:8080/test2/wmessageServlet")
                    .buildUpon()
                    .appendQueryParameter("action", "writemessage")
                    .appendQueryParameter("u_name", username)
                    .appendQueryParameter("message", message)
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i("LoginService", "Received JSON: " + jsonString);
            return jsonString;
        }catch (IOException ioe){
            Log.e("LoginService","Failed to fetch items",ioe);
        }
        return null;
    }


    public static String fetchWeiboItems(){//获取微博
        try {
            String url = Uri.parse("http://10.0.0.2:8080/test2/messageServlet")
                    .buildUpon()
                    .appendQueryParameter("action", "getmessage")
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i("LoginService", "Received JSON: " + jsonString);
            return jsonString;
        }catch (IOException ioe){
            Log.e("LoginService","Failed to fetch items",ioe);
        }
        return null;
    }

    public static String writeCommentItems(String weiboId,String comName,String comContent){//写评论
        try {
            String url = Uri.parse("http://10.0.0.2:8080/test2/wcommentServlet")
                    .buildUpon()
                    .appendQueryParameter("action", "writecomment")
                    .appendQueryParameter("to",weiboId)
                    .appendQueryParameter("from",comName)
                    .appendQueryParameter("comment",comContent)
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i("LoginService", "Received JSON: " + jsonString);
            return jsonString;
        }catch (IOException ioe){
            Log.e("LoginService","Failed to fetch items",ioe);
        }
        return null;
    }

    public static String fetchCommentItems(String weiboId){//获取评论
        try {
            String url = Uri.parse("http://10.0.0.2:8080/test2/commentServlet")
                    .buildUpon()
                    .appendQueryParameter("action", "getcomment")
                    .appendQueryParameter("to",weiboId)
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i("LoginService", "Received JSON: " + jsonString);
            return jsonString;
        }catch (IOException ioe){
            Log.e("LoginService","Failed to fetch items",ioe);
        }
        return null;
    }


    public static String addFollowedItems(String follower,String followed){//添加关注
        try {
            String url = Uri.parse("http://10.0.0.2:8080/test2/setfollowServlet")
                    .buildUpon()
                    .appendQueryParameter("follower", follower)
                    .appendQueryParameter("followed", followed)
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i("LoginService", "Received JSON: " + jsonString);
            return jsonString;
        }catch (IOException ioe){
            Log.e("LoginService","Failed to fetch items",ioe);
        }
        return null;
    }


    public static String cancelFollowedItems(String follower,String followed){//取消关注
        try {
            String url = Uri.parse("http://10.0.0.2:8080/test2/unFollowServlet")
                    .buildUpon()
                    .appendQueryParameter("follower", follower)
                    .appendQueryParameter("followed", followed)
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i("LoginService", "Received JSON: " + jsonString);
            return jsonString;
        }catch (IOException ioe){
            Log.e("LoginService","Failed to fetch items",ioe);
        }
        return null;
    }


    public static String fetchFollowedWeiboItems(String username){//返回已关注用户的微博
        try {
            String url = Uri.parse("http://10.0.0.2:8080/test2/getFollowServlet")
                    .buildUpon()
                    .appendQueryParameter("username", username)
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i("LoginService", "Received JSON: " + jsonString);
            return jsonString;
        }catch (IOException ioe){
            Log.e("LoginService","Failed to fetch items",ioe);
        }
        return null;
    }

    public static String writePersonalInfoItems(String username,String birth,String hobby,String home){//编辑个人信息
        try {
            String url = Uri.parse("http://10.0.0.2:8080/test2/wuserInfoServlet")
                    .buildUpon()
                    .appendQueryParameter("username", username)
                    .appendQueryParameter("birth", birth)
                    .appendQueryParameter("hobby", hobby)
                    .appendQueryParameter("home", home)
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i("LoginService", "Received JSON: " + jsonString);
            return jsonString;
        }catch (IOException ioe){
            Log.e("LoginService","Failed to fetch items",ioe);
        }
        return null;
    }

    public static String fetchPersonalInfoItems(String username){//获取个人信息
        try {
            String url = Uri.parse("http://10.0.0.2:8080/test2/userInfoServlet")
                    .buildUpon()
                    .appendQueryParameter("name", username)
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i("LoginService", "Received JSON: " + jsonString);
            return jsonString;
        }catch (IOException ioe){
            Log.e("LoginService","Failed to fetch items",ioe);
        }
        return null;
    }

    public static String fetchFollowedUserItems(String username){//获取关注列表
        try {
            String url = Uri.parse("http://10.0.0.2:8080/test2/getFollowListServlet")
                    .buildUpon()
                    .appendQueryParameter("username", username)
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i("LoginService", "Received JSON: " + jsonString);
            return jsonString;
        }catch (IOException ioe){
            Log.e("LoginService","Failed to fetch items",ioe);
        }
        return null;
    }

}
