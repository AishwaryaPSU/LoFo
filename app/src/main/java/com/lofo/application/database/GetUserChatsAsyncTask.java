package com.lofo.application.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.lofo.application.models.ChatMessage;

import java.util.List;


public class GetUserChatsAsyncTask extends AsyncTask<Integer,String, List<ChatMessage>> {

    private static Context context;

    public GetUserChatsAsyncTask(Context context){
        this.context = context;
    }
    @Override
    protected List<ChatMessage> doInBackground(Integer... integers) {

       LofoDbHelper dbHelper = LofoDbHelper.getInstance(context);
        List<ChatMessage> chatMessages = dbHelper.getAllChatsForUser(integers[0]);
        Log.i("Info", String.format("all chats for user %s : %s", integers[0], chatMessages));
        return chatMessages;
    }
}
