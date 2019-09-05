package com.example.mycontacts.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mycontacts.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[] { "Nitin Singh", "Will Smith", "Warren Sheen",
                "Charlie Buffet", "Ross Geller", "Rachael Green", "Donald Bump", "Grocery shop",
                "Laundary Mart", "Chinese takeout", "Car shop", "Movie theatre"};

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                shareContact(sharingIntent);
            }
        });
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    private void shareContact(Intent intent) {
        List<ResolveInfo> possibleActivitiesList =  getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);

        if (possibleActivitiesList.size() > 1) {
            String title = "Share Contact";
            Intent chooser = Intent.createChooser(intent, title);
            startActivity(chooser);
        } else if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
