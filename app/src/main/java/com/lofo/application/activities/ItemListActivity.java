package com.lofo.application.activities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lofo.application.R;
import com.lofo.application.dialogs.SimpleAlertDialog;
import com.lofo.application.listeners.FireBaseFoundItemsChangeListener;
import com.lofo.application.listeners.OnclickOpenChatListener;
import com.lofo.application.models.ItemView;

import java.util.ArrayList;
import java.util.List;



public class ItemListActivity extends BaseActivity {
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private final DatabaseReference foundItemsFbDbReference = databaseReference.child("foundItems");
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list_activity);
        setTitle("Found Items");
        GridView gridView = findViewById(R.id.gridView);
        LostItemsListAdapter lostItemsListAdapter = new ItemListActivity.LostItemsListAdapter(this);
        gridView.setAdapter(lostItemsListAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ItemListActivity.this, "you clicked " + position , Toast.LENGTH_LONG).show();
            }
        });
        foundItemsFbDbReference.addValueEventListener(new FireBaseFoundItemsChangeListener(lostItemsListAdapter, this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.simple_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Log.i("Info", "item "+item + " selected !!");
        String title = item.getTitle().toString();
        ActionBar actionbar = getSupportActionBar();
        Log.i("Info ", "color "+ title);
        switch (title){
            case "BLOODY_RED":
                actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bloody_red)));
                break;
            case "FRESH_GREEN":
                actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.fresh_green)));
                break;
            case "DIRTY_YELLOW":
                actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dirty_yellow)));
                break;
            case "NIGHT_BLACK":
                actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.nightly_black)));
                break;
        }
        return true;
    }

    //This method is responsible for showing the simple confirmation dialog
    @Override
    public void showDialog(View view) {
        Message positiveAlert = new Message();
        Message negativeAlert = new Message();
        SimpleAlertDialog simpleAlert = new SimpleAlertDialog(view.getContext());
        simpleAlert.setTitle("Are you freaking Serious!!!");
        simpleAlert.setMessage("Do you want to steal that item ??");
        simpleAlert.setButton(Dialog.BUTTON_NEGATIVE, "NO", negativeAlert);
        simpleAlert.setButton(Dialog.BUTTON_POSITIVE, "YES", positiveAlert);
        simpleAlert.show();
    }



    public static class LostItemsListAdapter extends BaseAdapter {
        private Context itemsContext;
        private List<ItemView> items = new ArrayList<>();




        public void setItems(List<ItemView> items) {
            this.items = items;
            for(ItemView itemView: this.items){
                Log.i("Info","finalItemView "+ itemView.toString());
            }
            this.notifyDataSetChanged();
        }

        public LostItemsListAdapter(Context context){
            this.itemsContext = context;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                LayoutInflater inflater = (LayoutInflater) itemsContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = inflater.inflate(R.layout.grid_layout_image_view, parent, false);
                ImageView imageView = convertView.findViewById(R.id.foundItemImageView);
                TextView userName = convertView.findViewById(R.id.userNameTextView);
                ItemView itemView = items.get(position);
                ImageView userIcon = convertView.findViewById(R.id.userImageView);
                userIcon.setImageBitmap(itemView.getUserIconBitMap());
                userName.setText(itemView.getUserName());
                TextView shortDesc = convertView.findViewById(R.id.shortDescTextView);
                shortDesc.setText(itemView.getShortDesc());
                imageView.setLayoutParams(new RelativeLayout.LayoutParams(400, 400));
                Log.i("Info", "itemView.getBitmap() " + itemView.getBitmap() + " position " + position);
                imageView.setImageBitmap(itemView.getBitmap());
                //Registering the onClickListener for the image view
                convertView.setOnClickListener(new OnclickOpenChatListener(itemView));
                convertView.setLayoutParams(new RelativeLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT));
                return convertView;
            } catch (Exception exception) {
                Log.e("Error", "Exception occurred during getView " + exception.getMessage());
                exception.printStackTrace();
            }
            return null;
        }
    }
}
