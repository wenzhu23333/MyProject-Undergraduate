package com.example.weibo.basicaldata;

import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("from")
    private String commentator;
    @SerializedName("comment")
    private String commentText;
    @SerializedName("to")
    private String conmmentId;

    public String getCommentator() {
        return commentator;
    }

    public void setCommentator(String commentator) {
        this.commentator = commentator;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getconmmentId() {
        return conmmentId;
    }

    public void setconmmentId(String conmmentId) {
        this.conmmentId = conmmentId;
    }
}
