package com.example.weibo.transitdata;

import com.example.weibo.basicaldata.Focus;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FocusData {

    @SerializedName("followList")
    private List<Focus> followedList;
    @SerializedName("num")
    private String currentUserFansNum;

    public List<Focus> getFollowedList() {
        return followedList;
    }

    public void setFollowedList(List<Focus> followedList) {
        this.followedList = followedList;
    }

    public String getCurrentUserFansNum() {
        return currentUserFansNum;
    }

    public void setCurrentUserFansNum(String currentUserFansNum) {
        this.currentUserFansNum = currentUserFansNum;
    }
}
