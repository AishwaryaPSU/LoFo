package com.lofo.application.listeners;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.lofo.application.activities.ItemListActivity;
import com.lofo.application.contracts.UserContract;
import com.lofo.application.database.LofoDbHelper;
import com.lofo.application.services.HelperService;

import java.util.ArrayList;

import static com.lofo.application.configuration.AppConfiguration.LOFO_APP;

public class SignInOnClickListener implements View.OnClickListener, ValueEventListener {
    private EditText userId;
    private EditText userPassword;
    private LofoDbHelper lofoDbHelper;
    private Context context;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private final DatabaseReference userDatabaseReference = databaseReference.child("users");
    public SignInOnClickListener(EditText userId, EditText userPassword, Context context) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.lofoDbHelper = LofoDbHelper.getInstance(context);
        this.context = context;
    }
    //following onClick method is responsible for creating a Toast notification
    @Override
    public void onClick(final View view) {
        final String userName = userId.getText().toString();
        final String password = userPassword.getText().toString();
        userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<Object>> objectListGenericTypeIndicator = new GenericTypeIndicator<ArrayList<Object>>(){};
                ArrayList<Object> users = dataSnapshot.getValue(objectListGenericTypeIndicator);
                final String finalUserName = userName;
                final String finalPassword = password;
                final View finalView = view;
                Log.i("Info", "all Users "+ users);
                if(users.contains(finalUserName)) {
                    Log.i("FireDBInfo","User exists");
                    Toast.makeText(finalView.getContext(), "Signed in as " + finalUserName, Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(finalView.getContext(), ItemListActivity.class);
                    finalView.getContext().startActivity(myIntent);
                    SharedPreferences sharedPreferences = context.getSharedPreferences(LOFO_APP,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    int countBasedId = -1;
                    int userId = 0;
                    for(Object user: users){
                        countBasedId++;
                        if(user!=null) {
                            HelperService.setUser(countBasedId, user.toString());
                        }
                        Log.i("Info", " user " + user + " finalUserName " + finalUserName +  " countBasedId "+ countBasedId);
                        if(user!=null && user.toString().equals(finalUserName)){
                            userId = countBasedId;
                            break;
                        }
                    }
                    editor.putInt("userId", userId);
                    Log.i("Info","userId set into : "+ userId);
                    editor.commit();
                    return;
                }
                SQLiteDatabase database = lofoDbHelper.getReadableDatabase();
                String selection2 = String.format("SELECT * FROM %s WHERE %s='%s'", UserContract.UserEntry.TABLE_NAME,UserContract.UserEntry.COLUMN_NAME_USER_NAME, userName);
                Log.i("Info","selection2 query " + selection2);
                Cursor cursor = database.rawQuery(selection2, null);
                Log.i("Info", " cursor.getCount " + cursor.getCount());
                Log.i("Info",String.format("userName %s, password %s ",finalUserName, finalPassword));
                if(cursor.getCount() == 1) {
                    cursor.moveToNext();
                    int passwordColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_USER_PASSWORD);
                    String storedPassword = cursor.getString(passwordColumnIndex);
                    if(finalPassword.equals(storedPassword)) {
                        Toast.makeText(finalView.getContext(), "Signed in as " + finalUserName, Toast.LENGTH_LONG).show();
                        SharedPreferences sharedPreferences = context.getSharedPreferences(LOFO_APP,Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Integer userId = cursor.getInt(cursor.getColumnIndex(UserContract.UserEntry._ID));
                        editor.putInt("userId", userId);
                        Log.i("Info","userId set into : "+ userId);
                        editor.commit();
                        Intent myIntent = new Intent(finalView.getContext(), ItemListActivity.class);
                        finalView.getContext().startActivity(myIntent);
                        return;
                    }
                    Toast.makeText(view.getContext(),"invalid credentials, please try again", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(view.getContext(),"invalid credentials, please try again", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
