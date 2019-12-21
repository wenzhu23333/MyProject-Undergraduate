package model;

import java.io.Serializable;

public class Message implements Serializable {
    private int id;
    private String m_name;
    private String message;
    private int cnum;

    public int getCnum() {
        return cnum;
    }

    public void setCnum(int cnum) {
        this.cnum = cnum;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
