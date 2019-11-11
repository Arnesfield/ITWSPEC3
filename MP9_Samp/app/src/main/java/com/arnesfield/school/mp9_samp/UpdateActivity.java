package com.arnesfield.school.mp9_samp;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity implements WriteTask.OnWriteTaskListener {

    private EditText et_name, et_desc, et_price;
    private Button btn_update;
    private Item currItem;
    private boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
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

        success = false;

        int pos = getIntent().getIntExtra("pos", -1);
        currItem = Item.LIST_OF_ITEMS.get(pos);

        et_name = (EditText) findViewById(R.id.et_name);
        et_desc = (EditText) findViewById(R.id.et_desc);
        et_price = (EditText) findViewById(R.id.et_price);

        btn_update = (Button) findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WriteTask(UpdateActivity.this, WriteTask.ACTION_UPDATE).execute();
            }
        });


        et_name.setText(currItem.getName());
        et_desc.setText(currItem.getDesc());
        et_price.setText(String.valueOf(currItem.getPrice()));
    }

    @Override
    public void setValues(ContentValues contentValues, String actionId) throws Exception {
        String name = et_name.getText().toString().trim();

        if (name.isEmpty()) {
            success = false;
            throw new Exception();
        }

        success = true;

        String desc = et_desc.getText().toString();
        double price = currItem.getPrice();
        try {
            price = Double.parseDouble(et_price.getText().toString());
        } catch (Exception ignored) {}

        contentValues.put("id", currItem.getId());
        contentValues.put("name", name);
        contentValues.put("desc", desc);
        contentValues.put("price", price);
    }

    @Override
    public void onDone(String actionId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (success) {
                    Toast.makeText(UpdateActivity.this, "Updated item.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(UpdateActivity.this, "Name cannot be empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
