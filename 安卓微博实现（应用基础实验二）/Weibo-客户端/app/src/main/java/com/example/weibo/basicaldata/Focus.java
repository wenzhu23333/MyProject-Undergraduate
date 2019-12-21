package com.example.weibo.basicaldata;

import com.google.gson.annotations.SerializedName;

public class Focus {

    private String followed;
    private String follower;
    @SerializedName("id")
    private String followListId;

    public String getFollowed() {
        return followed;
    }

    public void setFollowed(String followed) {
        this.followed = followed;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowListId() {
        return followListId;
    }

    public void setFollowListId(String followListId) {
        this.followListId = followListId;
    }
}
