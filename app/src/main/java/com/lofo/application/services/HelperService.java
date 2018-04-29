package com.lofo.application.services;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class HelperService {
    static HashMap<Integer,String> users = new HashMap<>();
    static HashMap<String,String> chatDBNameMap = new HashMap<>();

    public static void  setUser(Integer userId, String name){
        users.put(userId, name);
    }

    public static Integer getByName(String name) {
        if(users.containsValue(name)){
            for( Map.Entry<Integer, String> user: users.entrySet()){
                if(user.getValue().equals(name)){
                    return user.getKey();
                }
            }
        }
        return 1;
    }

    public static String getById(Integer userId) {
        return users.get(userId);
    }

    public static void clearUsers(){
        users.clear();
    }

    public static void setChatDBName(String chatDBName) {
        Log.i("Info","putting chatDBName "+ chatDBName);
        chatDBNameMap.put("chatDBName", chatDBName);
    }

    public static String getChatDBName() {
        Log.i("Info","getting chatDBName "+ chatDBNameMap.get("chatDBName"));
        return chatDBNameMap.get("chatDBName");
    }
}
