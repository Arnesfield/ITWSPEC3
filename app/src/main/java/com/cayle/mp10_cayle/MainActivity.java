package com.cayle.mp10_cayle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements MyTask.TaskListener {

    private ListView listView;
    private TextView tv_msg;
    private static int currPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_msg = (TextView) findViewById(R.id.tv_msg);
        listView = (ListView) findViewById(R.id.list_view);

        tv_msg.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_chart:
                Intent intent = new Intent(this, ChartActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doRefreshActivity() {
        currPos = -1;
        MyTask.execute(this);
    }

    private void setListView() {
        // set adapter
        listView.setVisibility(View.VISIBLE);
        listView.setAdapter(Item.getAdapter(this, android.R.layout.simple_list_item_1));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                intent.putExtra("pos", position);
                MainActivity.this.startActivity(intent);
            }
        });

        tv_msg.setVisibility(Item.isListEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        doRefreshActivity();
    }

    // fetch task listener
    @Override
    public void onTaskPostExecute(String jsonString) {
        try {
            Item.clearList();

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("foods");

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                String name = jsonObject.getString("name");
                String desc = jsonObject.getString("desc");
                double price = jsonObject.getDouble("price");
                float rating = (float) jsonObject.getDouble("rating");

                String imageResource = MyConfig.DIR_URL + jsonObject.getString("image_resource");

                Item.addItem(name, desc, price, rating, imageResource);
            }

            setListView();
        } catch (Exception e) {}
    }

}
