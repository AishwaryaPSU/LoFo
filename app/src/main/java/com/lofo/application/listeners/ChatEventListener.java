package com.lofo.application.listeners;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.lofo.application.activities.ChatActivity;
import com.lofo.application.adapters.ChatMessageAdapter;
import com.lofo.application.models.ChatMessage;
import com.lofo.application.serializers.ChatMessageDeSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ChatEventListener implements ValueEventListener {
    private ChatActivity chatActivity;
    private RecyclerView recyclerView;
    private GsonBuilder gsonBuilder = new GsonBuilder();
    private RecyclerView.Adapter adapter;
    private JsonDeserializer<ChatMessage> chatMessageJsonDeserializer = new ChatMessageDeSerializer();

    public ChatEventListener(ChatActivity chatActivity, RecyclerView recyclerView, RecyclerView.Adapter adapter){
        this.chatActivity = chatActivity;
        this.recyclerView = recyclerView;
        this.adapter = adapter;
    }
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.i("Info","Chat updated");
        Log.i("Info", dataSnapshot.toString());
        gsonBuilder.registerTypeAdapter(ChatMessage.class, chatMessageJsonDeserializer);
        Gson gson = gsonBuilder.create();
        GenericTypeIndicator<HashMap<String,Object>> hashMapGenericTypeIndicator = new GenericTypeIndicator<HashMap<String,Object>>(){};
        GenericTypeIndicator<ArrayList<Object>> objectListGenericTypeIndicator = new GenericTypeIndicator<ArrayList<Object>>(){};
        HashMap<String,Object> chatList = dataSnapshot.getValue(hashMapGenericTypeIndicator);
        Log.i("Info" , "chatList "+ chatList);
        List<ChatMessage> chatMessageList = new ArrayList<>();
        chatMessageList.clear();
        if(chatList!=null) {
            for (Map.Entry<String,Object> chat : chatList.entrySet()) {
                if (chat != null) {
                    Log.i("Info", "chatMessage Object " + chat.getValue());
                    Log.i("Info", "chatMessage json Object " + gson.toJson(chat.getValue()));
                    Log.i("Info", "POJO chatMessage " + gson.fromJson(gson.toJson(chat.getValue()), ChatMessage.class));
                    ChatMessage chatMessage = gson.fromJson(gson.toJson(chat.getValue()), ChatMessage.class);
                    chatMessageList.add(chatMessage);
                }
            }
        }
        Log.i("Info", "chatMessageList " + chatMessageList);
        adapter = new ChatMessageAdapter(chatMessageList, chatActivity);
        recyclerView.setAdapter(adapter);
        //ui updating should happen in ui thread.
        chatActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setLayoutManager(new LinearLayoutManager(chatActivity));
                Log.i("Info","updated the chat ui with new Info");
            }
        });

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
