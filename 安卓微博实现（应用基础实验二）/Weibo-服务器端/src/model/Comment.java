package model;

import java.io.Serializable;

public class Comment implements Serializable {
    private int id;
    private String to;
    private String from;
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getfrom() {
        return from;
    }

    public void setfrom(String from) {
        this.from = from;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
