package com.lofo.application.activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lofo.application.R;
import com.lofo.application.listeners.SignInOnClickListener;
import com.lofo.application.services.HelperService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText userName = (EditText)findViewById(R.id.userId);
        EditText passWord = (EditText) findViewById(R.id.userPassword);
        Button signIn = (Button)findViewById(R.id.signInBtn);
        HelperService.clearUsers();
        SignInOnClickListener onClickListener = new SignInOnClickListener(userName, passWord, this);
        signIn.setOnClickListener(onClickListener);
//        Intent preferenceActivity = new Intent(this, LofoPreferencesActivity.class);
//        startActivity(preferenceActivity);
    }
}
