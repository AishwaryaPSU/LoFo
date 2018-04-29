package com.lofo.application.contracts;

import android.provider.BaseColumns;


public class ChatConversationContract {
    private ChatConversationContract(){}

    public static class ChatConversationEntry implements BaseColumns {
        public static final String TABLE_NAME = "chatconversations";
        public static final String COLUMN_NAME_FROM = "from";
        public static final String COLUMN_NAME_TO = "to";
        public static final String COLUMN_NAME_MESSAGE = "message";
        public static final String COLUMN_NAME_CREATED_AT = "createdAt";
        public static final String COLUMN_NAME_UPDATED_AT = "updatedAt";
    }
}
