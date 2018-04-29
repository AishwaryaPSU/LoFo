package com.lofo.application.models;

import java.util.List;


public class ChatConversation {
    Integer id;
    
    List<ChatMessage> chatMessageList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ChatMessage> getChatMessageList() {
        return chatMessageList;
    }

    public void setChatMessageList(List<ChatMessage> chatMessageList) {
        this.chatMessageList = chatMessageList;
    }

    @Override
    public String toString() {
        return "ChatConversation{" +
                "id=" + id +
                ", chatMessageList=" + chatMessageList +
                '}';
    }
}
