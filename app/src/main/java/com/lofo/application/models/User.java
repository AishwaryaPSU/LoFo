package com.lofo.application.models;


import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class User {
    String userName;
    Integer userId;
    String password;
    String userEmail;
    List<Integer> chatConversationIds;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<Integer> getChatConversationIds() {
        return chatConversationIds;
    }

    public void setChatConversationIds(List<Integer> chatConversationIds) {
        this.chatConversationIds = chatConversationIds;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userId=" + userId +
                ", password='" + password + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", chatConversationIds=" + chatConversationIds +
                '}';
    }
}
