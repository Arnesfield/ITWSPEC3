package com.cayle.mp10_cayle;

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

        Item item = Item.getItemAtPos(getIntent().getIntExtra("pos", -1));

        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        TextView tv_desc = (TextView) findViewById(R.id.tv_desc);
        TextView tv_price = (TextView) findViewById(R.id.tv_price);
        TextView tv_rating = (TextView) findViewById(R.id.tv_rating);

        ImageView iv_main = (ImageView) findViewById(R.id.iv_main);

        tv_name.setText( item.getName() );
        tv_desc.setText( item.getDesc() );
        tv_price.setText( item.getFormattedPrice() );
        tv_rating.setText( item.getRating()+"" );

        Picasso.with(this)
                .load(item.getImageResource())
                .resize(256, 256)
                .centerCrop()
                .into(iv_main);
    }

}
