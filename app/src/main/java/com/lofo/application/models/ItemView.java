package com.lofo.application.models;


import android.graphics.Bitmap;

public class ItemView {
    Bitmap bitmap;
    String shortDesc;
    String userName;
    Bitmap userIconBitMap;

    public ItemView(Bitmap bitmap, String shortDesc, String userName,Bitmap userIconBitMap) {
        this.bitmap = bitmap;
        this.shortDesc = shortDesc;
        this.userName = userName;
        this.userIconBitMap = userIconBitMap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Bitmap getUserIconBitMap() {
        return userIconBitMap;
    }

    public void setUserIconBitMap(Bitmap userIconBitMap) {
        this.userIconBitMap = userIconBitMap;
    }

    @Override
    public String toString() {
        return "ItemView{" +
                "bitmap=" + bitmap +
                ", shortDesc='" + shortDesc + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
