package com.example.weibo.datahandle;

import com.example.weibo.transitdata.CommentData;
import com.google.gson.annotations.SerializedName;

public class CommentReply {

    private int code;
    @SerializedName("data")
    private CommentData cd;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public CommentData getCd() {
        return cd;
    }

    public void setCd(CommentData cd) {
        this.cd = cd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
