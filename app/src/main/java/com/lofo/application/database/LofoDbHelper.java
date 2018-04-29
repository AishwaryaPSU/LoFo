package com.lofo.application.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.lofo.application.contracts.ChatMessageContract.ChatMessageEntry;
import com.lofo.application.contracts.UserContract.UserEntry;
import com.lofo.application.models.ChatMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LofoDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "lofo.db";
    private static final String SQL_INITIALIZE = "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
            UserEntry._ID + " INTEGER PRIMARY KEY," +
            UserEntry.COLUMN_NAME_USER_EMAIL + " TEXT," +
            UserEntry.COLUMN_NAME_USER_NAME + " TEXT," +
            UserEntry.COLUMN_NAME_USER_PASSWORD + " TEXT)";
    private static final String SQL_INITIALIZE_CHAT = "CREATE TABLE " + ChatMessageEntry.TABLE_NAME + " (" +
            ChatMessageEntry._ID + " INTEGER PRIMARY KEY," +
            ChatMessageEntry.COLUMN_NAME_FROM + " INTEGER," +
            ChatMessageEntry.COLUMN_NAME_TO + " INTEGER," +
            ChatMessageEntry.COLUMN_NAME_MESSAGE + " TEXT," +
            ChatMessageEntry.COLUMN_NAME_CREATED_AT + " TEXT)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;
    private static final String SQL_ADD_ADMIN = "INSERT INTO user ('userEmail','userName','userPassword') VALUES ('admin@gmail.com','admin','admin');";
    private static  LofoDbHelper lofoDbHelper;

    private LofoDbHelper(Context context) {
            super(context, DATABASE_NAME , null, 1);
            getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("Info","SqliteDb is getting initialized");
       db.execSQL(SQL_INITIALIZE);
       db.execSQL(SQL_ADD_ADMIN);
        db.execSQL(SQL_INITIALIZE_CHAT);
       Log.i("Info" , String.format("Executing sql during initializead.. %s", SQL_INITIALIZE_CHAT));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static LofoDbHelper getInstance(Context context) {
        if(lofoDbHelper == null){
            lofoDbHelper = new LofoDbHelper(context);
        }
        return lofoDbHelper;
    }

    public void addUserChat(ChatMessage message, Integer userId) {
        SQLiteDatabase db  = lofoDbHelper.getWritableDatabase();
        String addChatForUser = String.format("INSERT INTO %s ('%s','%s','%s', '%s') VALUES ('%s','%s','%s', '%s');",
                ChatMessageEntry.TABLE_NAME, ChatMessageEntry.COLUMN_NAME_FROM,ChatMessageEntry.COLUMN_NAME_TO,ChatMessageEntry.COLUMN_NAME_MESSAGE,ChatMessageEntry.COLUMN_NAME_CREATED_AT,
                userId,12, message.getMessage(),new Date());
        Log.i("Info",String.format("executing insert Sql : %s", addChatForUser));
        db.execSQL(addChatForUser);
    }

    public List<ChatMessage> getAllChatsForUser(Integer userId) {
        SQLiteDatabase db  = lofoDbHelper.getWritableDatabase();
        String getChatsForUser = String.format("SELECT %s,%s,%s from %s where %s='%s'", ChatMessageEntry.COLUMN_NAME_FROM, ChatMessageEntry.COLUMN_NAME_TO, ChatMessageEntry.COLUMN_NAME_MESSAGE,
                ChatMessageEntry.TABLE_NAME, ChatMessageEntry.COLUMN_NAME_FROM, userId);
        Log.i("Info",String.format("executing select Sql : %s", getChatsForUser));
        Cursor cursor = db.rawQuery(getChatsForUser,null);
        List<ChatMessage> chats = new ArrayList<>();
        while (cursor.moveToNext()){
            int toUserIndex = cursor.getColumnIndex(ChatMessageEntry.COLUMN_NAME_TO);
            int toUser = cursor.getInt(toUserIndex);
            int messageIndex = cursor.getColumnIndex(ChatMessageEntry.COLUMN_NAME_MESSAGE);
            String message = cursor.getString(messageIndex);
            chats.add(new ChatMessage(userId, toUser, message));
        }
        return chats;
    }
}
