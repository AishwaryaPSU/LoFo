package com.lofo.application;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.lofo.application.activities.ItemListActivity;
import com.lofo.application.models.FoundItemDetail;
import com.lofo.application.models.ItemView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetImageFromExternalService extends AsyncTask<FoundItemDetail, String, String> {
    private ItemListActivity.LostItemsListAdapter lostItemsListAdapter;
    private ItemListActivity activity;

    public GetImageFromExternalService(ItemListActivity.LostItemsListAdapter lostItemsListAdapter, ItemListActivity activity){
        this.lostItemsListAdapter = lostItemsListAdapter;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(FoundItemDetail... itemDetails) {
        List<ItemView> itemViewList = new ArrayList<>();
        int count = 0;
       for(FoundItemDetail itemDetail: itemDetails){
           Bitmap bitmap = getBitmapFromURL(itemDetail.getImage(),300,300);
           Bitmap userIconBitmap = getBitmapFromURL(itemDetail.getUserIcon(),50,50);
           ItemView itemView = new ItemView(bitmap,itemDetail.getShortDesc(), itemDetail.getUser(), userIconBitmap);
           itemViewList.add(itemView);
       }
        final List<ItemView> finalItemViewList = itemViewList;
        //Note: doing this because of
        // https://stackoverflow.com/questions/5161951/android-only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-vi
        activity.runOnUiThread(new Runnable() {
           @Override
           public void run() {
               lostItemsListAdapter.setItems(finalItemViewList);
               lostItemsListAdapter.notifyDataSetChanged();
               Log.i("Info","doInBackground complete");
           }
       });
       return null;
    }

    private static Bitmap getBitmapFromURL(String src, int width, int height) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
            return Bitmap.createScaledBitmap(myBitmap, width, height, false);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }
}
