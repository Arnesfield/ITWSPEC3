package com.miko.mp10_samp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewActivity extends AppCompatActivity {

    private Item currItem;
    private TextView tv_name, tv_desc, tv_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
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

        int pos = getIntent().getIntExtra("pos", -1);
        currItem = Item.LIST_OF_ITEMS.get(pos);

        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_price = (TextView) findViewById(R.id.tv_price);

        tv_name.setText(currItem.getName());
        tv_desc.setText(currItem.getDesc());
        tv_price.setText(currItem.getFormattedPrice());

        ImageView iv_large = (ImageView) findViewById(R.id.iv_large);

        Picasso.with(this)
                .load(currItem.getImagePath())
                .resize(256, 256)
                .centerCrop()
                .into(iv_large);
    }

}
