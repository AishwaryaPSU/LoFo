package com.lofo.application.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.lofo.application.models.ChatMessage;

import java.lang.reflect.Type;


public class ChatMessageDeSerializer implements JsonDeserializer<ChatMessage> {

    @Override
    public ChatMessage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String createdAt = jsonObject.get("createdAt").getAsString();
        String message = jsonObject.get("message").getAsString();
        int fromUser = jsonObject.get("fromUser").getAsInt();
        int toUser = jsonObject.get("toUser").getAsInt();
        ChatMessage chatMessage = new ChatMessage(fromUser,toUser, message);
        chatMessage.setCreatedAt(createdAt);
        return chatMessage;
    }
}
