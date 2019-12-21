package com.example.weibo.datahandle;

import com.example.weibo.basicaldata.User;
import com.google.gson.annotations.SerializedName;

public class UserReply {

    private int code;
    @SerializedName("data")
    private User user;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
