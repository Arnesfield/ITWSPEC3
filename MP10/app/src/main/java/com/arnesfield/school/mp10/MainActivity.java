package com.arnesfield.school.mp10;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements FetchItemsTask.TaskListener {

    private View rootView, retryView;
    private ListView listView;
    private TextView tv_msg;
    private static int currPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rootView = findViewById(R.id.root_view);
        retryView = findViewById(R.id.retry_view);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        listView = (ListView) findViewById(R.id.list_view);

        retryView.setVisibility(View.GONE);
        tv_msg.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);

        // FetchItemsTask.execute(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_chart:
                Intent intent = new Intent(this, ShowChartActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doRefreshActivity() {
        currPos = -1;
        FetchItemsTask.execute(this);
    }

    private void showRetryView() {
        retryView.setVisibility(View.VISIBLE);

        Button btn_retry = (Button) findViewById(R.id.btn_retry);
        btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryView.setVisibility(View.GONE);
                FetchItemsTask.execute(MainActivity.this);
            }
        });
    }

    private void setListView() {
        // set adapter
        listView.setVisibility(View.VISIBLE);
        listView.setAdapter(Item.getAdapter(this, android.R.layout.simple_list_item_1));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ViewItemActivity.class);
                intent.putExtra("pos", position);
                MainActivity.this.startActivity(intent);
            }
        });

        tv_msg.setVisibility(Item.isListEmpty() ? View.VISIBLE : View.GONE);

        SnackBarCreator.show(rootView);
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
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                String desc = jsonObject.getString("desc");
                double price = jsonObject.getDouble("price");
                float rating = (float) jsonObject.getDouble("rating");
                String imageResource = TaskConfig.DIR_URL + jsonObject.getString("image_resource");

                Item.addItem(id, name, desc, price, rating, imageResource);
            }

            setListView();
        } catch (Exception e) {
            showRetryView();
        }
    }

}
