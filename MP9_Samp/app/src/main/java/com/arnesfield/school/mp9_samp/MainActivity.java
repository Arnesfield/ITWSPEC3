package com.arnesfield.school.mp9_samp;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements WriteTask.OnWriteTaskListener {

    private ListView itemListView;
    private ArrayAdapter<Item> itemListAdapter;
    private static int currPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemListView = (ListView) findViewById(R.id.main_item_list_view);
        itemListAdapter = new Item.ItemListAdapter(this, android.R.layout.simple_list_item_1);
        itemListView.setAdapter(itemListAdapter);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                intent.putExtra("pos", position);
                startActivity(intent);
            }
        });

        registerForContextMenu(itemListView);
        itemListView.setLongClickable(true);
        itemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                currPos = position;
                openContextMenu(itemListView);
                return true;
            }
        });

        // new FetchTask(this).execute();
    }

    public void parseJSON(String s) {
        try {
            // clear list first
            Item.LIST_OF_ITEMS.clear();

            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                String desc = jsonObject.getString("desc");
                double price = jsonObject.getDouble("price");

                // add item to list
                Item.LIST_OF_ITEMS.add( new Item(id, name, desc, price) );
            }

            itemListAdapter.notifyDataSetChanged();
        } catch (Exception ignored) {}
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.view:

                intent = new Intent(this, ViewActivity.class);
                intent.putExtra("pos", currPos);
                startActivity(intent);

                return true;

            case R.id.action_update:
                intent = new Intent(this, UpdateActivity.class);
                intent.putExtra("pos", currPos);
                startActivity(intent);
                break;

            case R.id.action_delete:
                new WriteTask(this, WriteTask.ACTION_DELETE).execute();
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setValues(ContentValues contentValues, String actionId) throws Exception {
        // delete
        switch (actionId) {
            case WriteTask.ACTION_DELETE:
                Log.i("pin", String.valueOf(Item.LIST_OF_ITEMS.get(currPos).getId()));
                contentValues.put("id", String.valueOf(Item.LIST_OF_ITEMS.get(currPos).getId()));
                break;
        }
    }

    @Override
    public void onDone(String actionId) {
        switch (actionId) {
            case WriteTask.ACTION_DELETE:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Deleted item.", Toast.LENGTH_SHORT).show();
                        refreshSomeStuff();
                    }
                });
                break;
        }
    }

    private void refreshSomeStuff() {
        new FetchTask(this).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshSomeStuff();
    }
}
