package com.cayle.fetch_activity_cayle;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {

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

        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        TextView tv_desc = (TextView) findViewById(R.id.tv_desc);
        TextView tv_price = (TextView) findViewById(R.id.tv_price);

        ImageView iv_main = (ImageView) findViewById(R.id.iv_main);

        String name = getIntent().getStringExtra("name");
        String desc = getIntent().getStringExtra("desc");
        String price = getIntent().getStringExtra("price");
        int largeImageResource = getIntent().getIntExtra("imageResource", -1);

        tv_name.setText(name);
        tv_desc.setText(desc);
        tv_price.setText(price);
        iv_main.setImageResource(largeImageResource);
    }

}
