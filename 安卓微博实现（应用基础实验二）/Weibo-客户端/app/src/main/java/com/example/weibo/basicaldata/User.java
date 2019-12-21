package com.example.weibo.basicaldata;

import com.google.gson.annotations.SerializedName;

public class User {
    private String hobby;
    private String birth;
    private String religion;
    @SerializedName("id")
    private String userId;
    @SerializedName("name")
    private String userName;
    @SerializedName("focusnum")
    private String fansNum;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getreligion() {
        return religion;
    }

    public void setreligion(String religion) {
        this.religion = religion;
    }

    public String getFansNum() {
        return fansNum;
    }

    public void setFansNum(String fansNum) {
        this.fansNum = fansNum;
    }
}
