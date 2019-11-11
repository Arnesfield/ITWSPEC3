package com.miko.mp10_samp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

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

        new FetchTask(this).execute();
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
                String imagePath = jsonObject.getString("image_path");

                // add item to list
                Item.LIST_OF_ITEMS.add( new Item(id, name, desc, price, MyConfig.HOST + MyConfig.DIR + imagePath) );
            }

            itemListAdapter.notifyDataSetChanged();
        } catch (Exception ignored) {}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.show_chart:
                Intent intent = new Intent(this, ChartActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
