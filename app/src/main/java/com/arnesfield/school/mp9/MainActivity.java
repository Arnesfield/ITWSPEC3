package com.arnesfield.school.mp9;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
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

public class MainActivity extends AppCompatActivity
        implements DialogCreator.DialogActionListener, FetchItemsTask.TaskListener, DeleteItemTask.TaskListener {

    private View rootView, retryView;
    private TextView tv_msg;
    private static int currPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doAddItem();
            }
        });

        rootView = findViewById(R.id.root_view);

        retryView = findViewById(R.id.retry_view);
        tv_msg = (TextView) findViewById(R.id.tv_msg);

        retryView.setVisibility(View.GONE);
        tv_msg.setVisibility(View.GONE);

        FetchItemsTask.execute(this);
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
        final ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(Item.getAdapter(this, android.R.layout.simple_list_item_1));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ViewItemActivity.class);
                intent.putExtra("pos", position);
                MainActivity.this.startActivity(intent);
            }
        });

        listView.setLongClickable(true);
        registerForContextMenu(listView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                currPos = position;
                openContextMenu(listView);
                return true;
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_item_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int pos = currPos;
        currPos = -1;
        switch (item.getItemId()) {
            case R.id.action_view:
                Intent intent = new Intent(this, ViewItemActivity.class);
                intent.putExtra("pos", pos);
                startActivity(intent);
                return true;
            case R.id.action_update:
                doUpdateItem(pos);
                return true;
            case R.id.action_remove:
                doRemoveItem(pos);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    // do methods
    private void doAddItem() {
        Intent intent = new Intent(this, ActionActivity.class);
        intent.putExtra("action", "add");

        startActivity(intent);
    }

    private void doUpdateItem(int position) {
        Intent intent = new Intent(this, ActionActivity.class);

        intent.putExtra("pos", position);
        intent.putExtra("action", "update");

        startActivity(intent);
    }

    private void doRemoveItem(int pos) {
        currPos = pos;
        DialogCreator.create(this, "remove")
                .setTitle(R.string.dialog_title_remove)
                .setMessage(R.string.dialog_msg_remove)
                .setPositiveButton(R.string.action_remove)
                .setNegativeButton(R.string.action_cancel)
                .show();
    }

    // dialog action listener
    @Override
    public void onClickPositiveButton(String actionId) {
        switch (actionId) {
            case "remove":
                SnackBarCreator.set(R.string.snack_removed);
                DeleteItemTask.execute(this);
                break;
        }
    }

    @Override
    public void onClickNegativeButton(String actionId) {

    }

    @Override
    public void onClickNeutralButton(String actionId) {

    }

    @Override
    public void onClickMultiChoiceItem(String actionId, int which, boolean isChecked) {

    }

    @Override
    public void onCreateDialogView(View view) {

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

                Item.addItem(id, name, desc, price, rating, Item.getThumbAtPos(i, true), Item.getThumbAtPos(i, false));
            }

            setListView();
        } catch (Exception e) {
            showRetryView();
        }
    }

    // delete item task listener
    @Override
    public void onDeleteTaskPostExecute() {
        doRefreshActivity();
    }

    @Override
    public String createDeletePostString(ContentValues contentValues) throws UnsupportedEncodingException {
        int id = Item.getItemAtPos(currPos).getId();
        contentValues.put("id", id);

        StringBuilder stringBuilder = new StringBuilder();
        boolean flag = true;

        for (Map.Entry<String, Object> value : contentValues.valueSet()) {
            stringBuilder.append( flag ? "" : "&" );
            flag = false;
            stringBuilder.append(URLEncoder.encode(value.getKey(), "UTF-8"));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(value.getValue().toString(), "UTF-8"));
        }

        return stringBuilder.toString();
    }
}
