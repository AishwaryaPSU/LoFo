package com.lofo.application.listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.lofo.application.activities.ChatActivity;
import com.lofo.application.services.HelperService;

import java.util.HashMap;

public class UpdateUsersListener implements ValueEventListener {
    private ChatActivity context;
    private DatabaseReference databaseReference;
    private int userId;
    private String chatWithUser;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    public UpdateUsersListener(ChatActivity context, DatabaseReference databaseReference, int userId,
                               String chatWithUser, RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        this.context = context;
        this.databaseReference = databaseReference;
        this.userId = userId;
        this.chatWithUser = chatWithUser;
        this.recyclerView = recyclerView;
        this.adapter = adapter;
    }
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.i("Info", "adapter.getItemCount" + adapter.getItemCount());
        HashMap<String, Object> usersMap = new HashMap<>();
        String userName = "";
        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
            HelperService.setUser(Integer.parseInt(dataSnapshot1.getKey()), dataSnapshot1.getValue().toString());
            usersMap.put(dataSnapshot1.getKey(), dataSnapshot1.getValue());
            if(userId == Integer.parseInt(dataSnapshot1.getKey())){
                userName = dataSnapshot1.getValue().toString();
            }
        }
        context.setUsers(usersMap);
        if(!userName.isEmpty()){
            String chatDbName = "";
            if(userName.compareTo(chatWithUser) >= 0){
                chatDbName = userName+":"+chatWithUser;
            } else {
                chatDbName = chatWithUser+":"+userName;
            }
            Log.i("Info","setting chatDbname " +  chatDbName);
            context.setChatWithUser(chatDbName);
            DatabaseReference chatReference = databaseReference.child(chatDbName);
            chatReference.addValueEventListener(new ChatEventListener(context, recyclerView, adapter));
            Log.i("Info","Setting chat event Listener for chatDbName : "+ chatDbName);
        }

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
