package com.lofo.application.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lofo.application.R;
import com.lofo.application.adapters.ChatMessageAdapter;
import com.lofo.application.listeners.UpdateUsersListener;
import com.lofo.application.models.ChatMessage;
import com.lofo.application.services.HelperService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.lofo.application.configuration.AppConfiguration.LOFO_APP;

public class ChatActivity extends AppCompatActivity {
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference chatReference = reference.child("chats");
    private DatabaseReference usersReference = reference.child("users");
    private HashMap<String, Object> users;
    private HashMap<String,Object> currentChat;
    private String currentUser = "";
    private String chatWithUser = "";

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getChatWithUser() {
        return chatWithUser;
    }

    public void setChatWithUser(String chatWithUser) {
        this.chatWithUser = chatWithUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.chat_activity);
            SharedPreferences sharedPreferences = this.getSharedPreferences(LOFO_APP, Context.MODE_PRIVATE);
            Integer userId = sharedPreferences.getInt("userId", 0);
            Integer[] userIds = {userId};
            Log.i("Info", "userId : " + Integer.toString(userId));
            Intent intent =  this.getIntent();
            String user = intent.getStringExtra("user");
            Log.i("Info","Chat with "+ user);
            String chatDbName = "";
            String userName = HelperService.getById(userId);
            Log.i("Info", "userName "+ userName);
            if(userName.compareTo(user) >= 0){
                chatDbName = userName+":"+user;
            } else {
                chatDbName = user+":"+userName;
            }
            setChatWithUser(chatDbName);
            Log.i("Info","HelperService chatDbName "+ chatDbName);
            HelperService.setChatDBName(chatDbName);
            setTitle("Chat with " + user);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.chatMessageList);
            recyclerView.removeAllViews();
            ChatMessageAdapter adapter = new ChatMessageAdapter(new ArrayList<ChatMessage>(), this);
            adapter.clear();
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            usersReference.addValueEventListener(new UpdateUsersListener(this, chatReference, userId, user, recyclerView, adapter));
        } catch (Exception exception) {
            Log.e("Error", exception.toString());
        }
    }

    public void setUsers(HashMap<String, Object> users) {
        this.users = users;
    }

    public void sendMessage(View view) {
        EditText chat = (EditText) findViewById(R.id.edittext_chatbox);
        String chatText = chat.getText().toString();
        String[] chatTexts = {chatText};
        Log.i("Info", String.format("chat text %s", chatText));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.chatMessageList);
        SharedPreferences sharedPreferences = this.getSharedPreferences(LOFO_APP, Context.MODE_PRIVATE);
        Integer userId = sharedPreferences.getInt("userId", 0);
        String chatWithUser = HelperService.getChatDBName()!= null ? HelperService.getChatDBName() : getChatWithUser();
        Log.i("Info","chatWithUser "+ chatWithUser);
        String user = HelperService.getById(userId);
        Integer chatWithUserId = HelperService.getByName(chatWithUser);
        ChatMessage message = new ChatMessage(userId, chatWithUserId, chatText);
        List<ChatMessage> chatList = Arrays.asList(message);
        Log.i("Info", String.format("chatList %s", chatList));
        RecyclerView.Adapter adapter = new ChatMessageAdapter(chatList, this);
        //recyclerView.setHasFixedSize(true);
        Log.i("Info", String.format("view.getContext() : %s", view.getContext()));
        Log.i("Info", String.format("setting adapter %s", adapter.toString()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        DatabaseReference listRef = chatReference.child(chatWithUser).push();
        listRef.setValue(message);
        //new SaveChatAsyncTask(view.getContext()).execute(chatTexts);
        Log.i("Info", String.format("recyclerView adapter itemCount %s", recyclerView.getAdapter().getItemCount()));
    }

    public static class ChatMessageHolder extends RecyclerView.ViewHolder {
        //TextView simpleFromChatView;
        //TextView simpleToChatView;
        TextView chatView;
        View view;

        public void setChatView(TextView chatView) {
            this.chatView = chatView;
        }

        //        public ChatMessageHolder(View itemView, View fromView) {
//            super(itemView);
//            simpleFromChatView = (TextView) fromView.findViewById(R.id.simple_chat_from);
//            simpleToChatView = (TextView) itemView.findViewById(R.id.simple_chat_to);
//        }
        public ChatMessageHolder(View view) {
            super(view);
            this.view = view;
            chatView = (TextView) view.findViewById(R.id.simple_chat);
        }

        public TextView getSimpleFromChatView(Boolean isCurrentUser) {
            if(!isCurrentUser){
                chatView.setBackgroundResource(R.drawable.com_facebook_button_icon_white);
                chatView = view.findViewById(R.id.simple_chat_to);
                chatView.setBackgroundResource(R.color.holo_orange_light);
            }
            return chatView;
        }

        public void setSimpleFromChatView(TextView simpleFromChatView) {
            //this.simpleFromChatView = simpleFromChatView;
            this.chatView = simpleFromChatView;
        }

        public void bindData(ChatMessage viewModel) {
            Log.i("Info", String.format("bindData to chatView , message : %s", viewModel.getMessage()));
            //simpleFromChatView.setText(viewModel.getMessage());
            chatView.setText(viewModel.getMessage());
        }
    }
}


