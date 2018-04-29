package com.lofo.application.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Message;

public class SimpleAlertDialog extends AlertDialog {
    public SimpleAlertDialog(Context context) {
        super(context);
    }
    @Override
    public void setTitle(CharSequence charSequence){
        super.setTitle(charSequence);
    }
    @Override
    public void setMessage(CharSequence charSequence){
        super.setMessage(charSequence);
    }
    @Override
    public void setButton(int whichButton, CharSequence charSequence, Message msg){
        super.setButton(whichButton,charSequence,msg);
    }
}
