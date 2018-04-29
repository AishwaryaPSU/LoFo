package com.lofo.application.contracts;

import android.provider.BaseColumns;

public class UserContract {

    private UserContract() {}
        /* Inner class that defines the table contents */
        public static class UserEntry implements BaseColumns {
            public static final String TABLE_NAME = "user";
            public static final String COLUMN_NAME_USER_NAME = "userName";
            public static final String COLUMN_NAME_USER_EMAIL = "userEmail";
            public static final String COLUMN_NAME_USER_PASSWORD = "userPassword";
            public static final String COLUMN_NAME_CHAT_HISTORY= "chatHistory";
        }
}
