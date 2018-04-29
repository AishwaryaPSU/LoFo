package com.lofo.application.models;


public class FoundItemDetail {
    String user;
    String image;
    String shortDesc;
    String userIcon;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    @Override
    public String toString() {
        return "FoundItemDetail{" +
                "user='" + user + '\'' +
                ", image='" + image + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", userIcon='" + userIcon + '\'' +
                '}';
    }
}
