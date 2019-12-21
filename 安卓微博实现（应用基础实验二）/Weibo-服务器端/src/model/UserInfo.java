package model;

public class UserInfo {
    private int id;
    private String name;
    private String birth;
    private String home;
    private String hobby;
    private int followednum;

    public int getFollowednum() {
        return followednum;
    }

    public void setFollowednum(int followednum) {
        this.followednum = followednum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String gethobby() {
        return hobby;
    }

    public void sethobby(String hobit) {
        this.hobby = hobit;
    }
}
