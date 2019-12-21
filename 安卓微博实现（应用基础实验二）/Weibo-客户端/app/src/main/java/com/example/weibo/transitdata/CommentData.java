package com.example.weibo.transitdata;

import com.example.weibo.basicaldata.Comment;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentData {

    @SerializedName("commentList")
    private List<Comment> commentList;
    @SerializedName("num")
    private String commentNum;

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }
}
