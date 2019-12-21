package com.example.weibo.basicaldata;

import com.example.weibo.datahandle.CommentReply;
import com.example.weibo.datahandle.FocusReply;
import com.example.weibo.datahandle.UserReply;
import com.example.weibo.datahandle.WeiboReply;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Public {
    public static List<Weibo> currentFollowedWeiboList = new ArrayList<>();
    public static List<Focus> currentFollowedNameList = new ArrayList<>();
    public static boolean isFirstWeiboList = true;
    public static boolean isFirstWeiboDetail = true;
    public static ExecutorService singleThreadExecutorWeibo=Executors.newSingleThreadExecutor();
    public static ExecutorService singleThreadExecutorPersonal=Executors.newSingleThreadExecutor();
    public static boolean isFirstFollowedList = true;
    public static boolean isFirstPersonal = true;
    public static boolean isFirstInit = true;
    public static User currentUser = new User();
    public static List<Weibo> currentWeiboList = new ArrayList<>();
    public static List<Comment> currentCommentList = new ArrayList<>();
    public static Weibo clickedWeibo = new Weibo();
    public static List<Focus> handleFollowedListResponse(String response){//解析关注列表的json数据
        try{
            FocusReply fr = new Gson().fromJson(response,FocusReply.class);
            Public.currentUser.setFansNum(fr.getFd().getCurrentUserFansNum());
            return fr.getFd().getFollowedList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static User handleUserResponse(String response){//解析当前用户的json数据
        try{
            UserReply userResponse = new Gson().fromJson(response,UserReply.class);
            //currentUser = userResponse.getUser();
            //String name = currentUser.getUserName();
            return userResponse.getUser();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }



    public static List<Comment> handleCommentResponse(String response){//解析评论列表的json数据
        try{
            CommentReply cr = new Gson().fromJson(response,CommentReply.class);
            return cr.getCd().getCommentList();
         //   currentCommentList = cr.getCd().getCommentList();
         //   return currentCommentList;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static List<Weibo> handleWeiboResponse(String response){//解析微博列表的json数据
        try{
            WeiboReply wr = new Gson().fromJson(response,WeiboReply.class);
            //currentWeiboList = wr.getWd().getWeiboList();
            return wr.getWd().getWeiboList();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }



}
