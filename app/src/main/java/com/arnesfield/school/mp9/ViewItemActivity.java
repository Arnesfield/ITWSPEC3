package com.arnesfield.school.mp9;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class ViewItemActivity extends AppCompatActivity
        implements DialogCreator.DialogActionListener, DeleteItemTask.TaskListener, FetchItemsTask.TaskListener {

    private int position;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rootView = findViewById(R.id.root_view);

        setupView();
    }

    private void setupView() {
        final TextView tv_item_name_main = (TextView) findViewById(R.id.tv_item_name_main);

        final TextView tv_item_id = (TextView) findViewById(R.id.tv_item_id);
        final TextView tv_item_name = (TextView) findViewById(R.id.tv_item_name);
        final TextView tv_item_desc = (TextView) findViewById(R.id.tv_item_desc);
        final TextView tv_item_price = (TextView) findViewById(R.id.tv_item_price);

        final ImageView iv_item_img = (ImageView) findViewById(R.id.iv_item_img);

        final ImageView iv_id = (ImageView) findViewById(R.id.iv_id);
        final ImageView iv_name = (ImageView) findViewById(R.id.iv_name);
        final ImageView iv_desc = (ImageView) findViewById(R.id.iv_desc);
        final ImageView iv_price = (ImageView) findViewById(R.id.iv_price);

        final RatingBar rbar_item = (RatingBar) findViewById(R.id.rbar_item);

        position = getIntent().getIntExtra("pos", -1);
        final Item item = Item.getItemAtPos(position);

        String id = String.valueOf(item.getId());
        String name = item.getName();
        String desc = item.getDesc();
        String price = item.getFormattedPrice();

        float rating = item.getRating();
        int resource = item.getLargeImageResource();

        tv_item_name_main.setText(name);

        iv_item_img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        iv_item_img.setBackgroundResource(resource);

        tv_item_id.setText(id);
        tv_item_name.setText(name);
        tv_item_desc.setText(desc);
        tv_item_price.setText(price);

        rbar_item.setRating(rating);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FetchItemsTask.execute(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_item_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update:
                Intent intent = new Intent(this, ActionActivity.class);
                intent.putExtra("pos", position);
                intent.putExtra("action", "update");
                startActivity(intent);
                return true;
            case R.id.action_remove:
                DialogCreator.create(this, "remove")
                        .setTitle(R.string.dialog_title_remove)
                        .setMessage(R.string.dialog_msg_remove)
                        .setPositiveButton(R.string.action_remove)
                        .setNegativeButton(R.string.action_cancel)
                        .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

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

    // delete task
    @Override
    public void onDeleteTaskPostExecute() {
        finish();
    }

    @Override
    public String createDeletePostString(ContentValues contentValues) throws UnsupportedEncodingException {
        int id = Item.getItemAtPos(position).getId();
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

            setupView();
            SnackBarCreator.show(rootView);
        } catch (Exception e) {
            finish();
        }
    }
}
