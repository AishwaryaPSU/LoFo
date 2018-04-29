package com.lofo.application.listeners;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.lofo.application.activities.ChatActivity;
import com.lofo.application.models.ItemView;


public class OnclickOpenChatListener implements View.OnClickListener {
    private ItemView itemView;
    public OnclickOpenChatListener(ItemView itemView){
        this.itemView = itemView;
    }
    @Override
    public void onClick(View view) {
        Intent myIntent = new Intent(view.getContext(), ChatActivity.class);
        Log.i("Info","itemView username " + itemView.getUserName());
        myIntent.putExtra("user", itemView.getUserName());
        view.getContext().startActivity(myIntent);
    }
}
