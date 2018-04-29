package com.lofo.application.listeners;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lofo.application.GetImageFromExternalService;
import com.lofo.application.activities.ItemListActivity;
import com.lofo.application.models.FoundItemDetail;
import com.lofo.application.models.ItemView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class FireBaseFoundItemsChangeListener implements ValueEventListener {

    private ItemListActivity.LostItemsListAdapter lostItemsListAdapter;
    private  Gson gson = new GsonBuilder().create();
    private DataSnapshot dataSnapshot;
    private ItemListActivity activity;

    public FireBaseFoundItemsChangeListener(ItemListActivity.LostItemsListAdapter lostItemsListAdapter, ItemListActivity activity) {
        this.lostItemsListAdapter = lostItemsListAdapter;
        this.activity = activity;
    }
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        this.dataSnapshot = dataSnapshot;
        Log.i("Info","items in firebase has changed");
        GenericTypeIndicator<HashMap<String,Object>> hashMapGenericTypeIndicator = new GenericTypeIndicator<HashMap<String,Object>>(){};
        HashMap<String,Object> items = dataSnapshot.getValue(hashMapGenericTypeIndicator);
        Bitmap[] bitmapArray  = new Bitmap[items.entrySet().size()];
        FoundItemDetail[] foundItemDetails = new FoundItemDetail[items.entrySet().size()];
        String[] urls = new String[items.entrySet().size()];
        List<FoundItemDetail> itemDetails = new ArrayList<>();
        int count = 0;
        for(Entry<String, Object> item : items.entrySet()){
            String itemName = item.getKey();
            Object object = item.getValue();
            Log.i("Info" , "FoundItemDetail object " + gson.toJson(object));
            FoundItemDetail itemDetail = gson.fromJson(gson.toJson(object), FoundItemDetail.class);
            foundItemDetails[count++] = itemDetail;
        }
        new GetImageFromExternalService(lostItemsListAdapter, activity).execute(foundItemDetails);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.i("Info","database error while listening to chatData: "+ databaseError.getMessage());
        databaseError.toException().printStackTrace();
    }

    private static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
            return Bitmap.createScaledBitmap(myBitmap, 300, 300, false);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }

//    @Override
//    protected String doInBackground(String... strings) {
////        activity.runOnUiThread(new Runnable() {
////            @Override
////            public void run() {
//                Log.i("Info","items in firebase has changed");
//                GenericTypeIndicator<HashMap<String,Object>> listGenericTypeIndicator = new GenericTypeIndicator<HashMap<String,Object>>(){};
//                HashMap<String,Object> items = dataSnapshot.getValue(listGenericTypeIndicator);
//                Bitmap[] bitmapArray  = new Bitmap[items.entrySet().size()];
//                String[] urls = new String[items.entrySet().size()];
//                int count = 0;
//                for(Entry<String, Object> item : items.entrySet()){
//                    String itemName = item.getKey();
//                    Object object = item.getValue();
//                    Log.i("Info" , "FoundItemDetail object " + gson.toJson(object));
//                    FoundItemDetail itemDetail = gson.fromJson(gson.toJson(object), FoundItemDetail.class);
//                    urls[count++] = itemDetail.getImage();
//                }
//                new GetImageFromExternalService(lostItemsListAdapter).execute(urls);
////            }
////        });
//        return null;
//    }
}
