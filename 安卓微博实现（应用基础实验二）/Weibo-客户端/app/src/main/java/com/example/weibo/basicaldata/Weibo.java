package com.example.weibo.basicaldata;

import com.google.gson.annotations.SerializedName;

public class Weibo {

    @SerializedName("message")
    private String weiboText;
    @SerializedName("cnum")
    private String commentNum;
    @SerializedName("id")
    private String weiboId;
    @SerializedName("m_name")
    private String poster;


    public String getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getWeiboText() {
        return weiboText;
    }

    public void setWeiboText(String weiboText) {
        this.weiboText = weiboText;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }
}
