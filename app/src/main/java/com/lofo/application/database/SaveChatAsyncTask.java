package com.lofo.application.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.lofo.application.models.ChatMessage;

import static com.lofo.application.configuration.AppConfiguration.LOFO_APP;


public class SaveChatAsyncTask extends AsyncTask<String ,String,String> {

    private static Context context;

    public SaveChatAsyncTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String[] objects) {
        Log.i("Info", String.format("string objects recieved %s", objects));
        LofoDbHelper lofoDbHelper = LofoDbHelper.getInstance(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOFO_APP,Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId",0);
        lofoDbHelper.addUserChat(new ChatMessage(userId, 12, objects[0]),userId);
        return null;
    }

}

