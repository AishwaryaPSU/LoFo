package com.lofo.application.contracts;

import android.provider.BaseColumns;


public class ChatMessageContract {

    private ChatMessageContract(){}

    public static class ChatMessageEntry implements BaseColumns {
        public static final String TABLE_NAME = "chatmessages";
        public static final String COLUMN_NAME_FROM = "fromUser";
        public static final String COLUMN_NAME_TO = "toUser";
        public static final String COLUMN_NAME_MESSAGE = "message";
        public static final String COLUMN_NAME_CREATED_AT = "createdAt";
    }
}
