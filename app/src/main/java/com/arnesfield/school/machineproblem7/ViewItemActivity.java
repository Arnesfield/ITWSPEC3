package com.arnesfield.school.machineproblem7;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ViewItemActivity extends AppCompatActivity {

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

        String id = getIntent().getStringExtra("itemId");
        String name = getIntent().getStringExtra("itemName");
        String desc = getIntent().getStringExtra("itemDesc");
        String price = getIntent().getStringExtra("itemPrice");

        float rating = getIntent().getFloatExtra("itemRating", -1);
        int resource = getIntent().getIntExtra("itemImageResource", -1);

        tv_item_name_main.setText(name);

        iv_item_img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        iv_item_img.setBackgroundResource(resource);

        tv_item_id.setText(id);
        tv_item_name.setText(name);
        tv_item_desc.setText(desc);
        tv_item_price.setText(price);

        rbar_item.setRating(rating);
    }

}
