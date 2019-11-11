package com.katrina.mp8_kat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {

    private Item item;
    private TextView tv_id, tv_name, tv_desc, tv_price;
    private int currentItemPosition;
    private DBHelper db;

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

        tv_id = (TextView) findViewById(R.id.tv_id);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_price = (TextView) findViewById(R.id.tv_price);

        currentItemPosition = getIntent().getIntExtra("pos", -1);

        db = DBHelper.getHelper();

        Button btn_update = (Button) findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewActivity.this, ActionActivity.class);
                intent.putExtra("pos", currentItemPosition);
                intent.putExtra("action", "update");
                startActivity(intent);
            }
        });

        doAction("refresh");
    }

    @Override
    protected void onResume() {
        super.onResume();
        doAction("refresh");
    }

    private void doAction(String action) {
        switch (action) {
            case "refresh":
                if (currentItemPosition != -1)
                    item = db.getItemAtPos(currentItemPosition);

                tv_id.setText(String.valueOf(item.getId()));
                tv_name.setText(item.getName());
                tv_desc.setText(item.getDesc());
                tv_price.setText(item.getFormattedPrice());
                break;
        }
    }

}