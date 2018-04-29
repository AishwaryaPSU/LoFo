package com.lofo.application.models;

import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class ChatMessage {
    // use integer for fromUSer and toUSer
    Integer fromUser;
    Integer toUser;
    String message;
    String createdAt;

    public ChatMessage(Integer fromUser,Integer toUser, String message){
        setFromUser(fromUser);
        setToUser(toUser);
        setMessage(message);
        setCreatedAt(new Date().toString());
        Log.i("info", String.format("created ChatMessage object %s", this.toString()));
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getFromUser() {
        return fromUser;
    }

    public void setFromUser(Integer fromUser) {
        this.fromUser = fromUser;
    }

    public Integer getToUser() {
        return toUser;
    }

    public void setToUser(Integer toUser) {
        this.toUser = toUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "fromUser=" + fromUser +
                ", toUser=" + toUser +
                ", message='" + message + '\'' +
                '}';
    }
}
