package com.example.weibo.datahandle;

import com.example.weibo.transitdata.FocusData;
import com.google.gson.annotations.SerializedName;

public class FocusReply {

    private int code;
    @SerializedName("data")
    private FocusData fd;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public FocusData getFd() {
        return fd;
    }

    public void setFd(FocusData fd) {
        this.fd = fd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
