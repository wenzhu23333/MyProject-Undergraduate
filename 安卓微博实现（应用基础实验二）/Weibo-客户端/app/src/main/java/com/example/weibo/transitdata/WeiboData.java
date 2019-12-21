package com.example.weibo.transitdata;

import com.example.weibo.basicaldata.Weibo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeiboData {

    @SerializedName("messageList")
    private List<Weibo> weiboList;
    @SerializedName("num")
    private String weiboNum;

    public List<Weibo> getWeiboList() {
        return weiboList;
    }

    public void setWeiboList(List<Weibo> weiboList) {
        this.weiboList = weiboList;
    }

    public String getWeiboNum() {
        return weiboNum;
    }

    public void setWeiboNum(String weiboNum) {
        this.weiboNum = weiboNum;
    }
}
