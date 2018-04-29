package com.lofo.application.listeners;

import android.view.View;

import com.lofo.application.activities.BaseActivity;

//This is the listener for the click events happening on image.
public class GenericOnClickDialogListener implements View.OnClickListener {
    //This method will be called when the user clicks on the image.
    //And it makes a callback to ItemListActivity.showClaimDialog method
    @Override
    public void onClick(View view) {
        BaseActivity baseActivity = (BaseActivity) view.getContext();
        baseActivity.showDialog(view);
    }
}
