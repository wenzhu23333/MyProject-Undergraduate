package com.example.weibo.datahandle;

import com.example.weibo.transitdata.WeiboData;
import com.google.gson.annotations.SerializedName;

public class WeiboReply {

    private int code;
    @SerializedName("data")
    private WeiboData wd;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public WeiboData getWd() {
        return wd;
    }

    public void setWd(WeiboData wd) {
        this.wd = wd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
