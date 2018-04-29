package com.lofo.application.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lofo.application.R;
import com.lofo.application.activities.ChatActivity.ChatMessageHolder;
import com.lofo.application.models.ChatMessage;

import java.util.ArrayList;
import java.util.List;

import static com.lofo.application.configuration.AppConfiguration.LOFO_APP;


public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageHolder> {

    private static List<ChatMessage> models = new ArrayList<>();
    private static Context context;
    private ViewGroup parent;

    public ChatMessageAdapter(List<ChatMessage> chatMessageList, Context context){
        this.models.addAll(chatMessageList);
        this.context = context;
        Log.i("Info", String.format("after Constructor models %s", models));
        setHasStableIds(true);
    }
    @Override
    public ChatMessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("Info ", String.format("onCreateViewHolder parent %s viewType %s ", parent, viewType));
        final View fromView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_from_view, parent, false);
        //return new ChatMessageHolder(view, fromView);
        this.parent = parent;
        return new ChatMessageHolder(fromView);
    }

    @Override
    public void onBindViewHolder(ChatMessageHolder holder, int position) {
        Log.i("Info ", String.format("onBindViewHolder message %s", models.get(position)));
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOFO_APP, Context.MODE_PRIVATE);
        Integer userId = sharedPreferences.getInt("userId", 0);
        ChatMessage message = models.get(position);
        View toView = LayoutInflater.from(context).inflate(R.layout.chat_message_to_view, parent, false);
        TextView toChatView = toView.findViewById(R.id.simple_chat);
        if(!message.getFromUser().equals(userId)) {
            Log.i("Info","setting to chat view because sender is not current user");
            //holder.setChatView(toChatView);
        }
        Log.i("Info",String.format("message.getFromUser() %s userId %s", message.getFromUser(), userId));
        TextView view = holder.getSimpleFromChatView(message.getFromUser().equals(userId));
        Log.i("Info", "view == toChatView " + toChatView.equals(view));
        Log.i("Info", "getSimpleFromChatView  view " + view.getId());
        view.setText(message.getMessage());
        holder.bindData(models.get(position));
    }

    @Override
    public int getItemCount() {
        Log.i("Info","getItemCount : " + models.size());
        return models.size();
    }

    @Override
    public int getItemViewType(int integer) {
        Log.i("Info","getItemViewType R.layout.chat_message_from_view : " + R.layout.chat_message_from_view);
        //return R.layout.chat_message_from_view;
        return integer;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clear() {
        models.clear();
    }
}
