package com.arnesfield.school.mp10;

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

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class ViewItemActivity extends AppCompatActivity {

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

        tv_item_name_main.setText(name);

        // iv_item_img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        // iv_item_img.setBackgroundResource(resource);
        // use picasso for image

        Picasso.with(this)
                .load(item.getImageResource())
                .resize(256, 256)
                .centerCrop()
                .into(iv_item_img);

        tv_item_id.setText(id);
        tv_item_name.setText(name);
        tv_item_desc.setText(desc);
        tv_item_price.setText(price);

        rbar_item.setRating(rating);
    }
}
