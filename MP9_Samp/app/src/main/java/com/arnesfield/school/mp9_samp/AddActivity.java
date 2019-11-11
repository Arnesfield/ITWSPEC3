package com.arnesfield.school.mp9_samp;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity implements WriteTask.OnWriteTaskListener {

    private EditText et_name, et_desc, et_price;
    private Button btn_add;
    private boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
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

        et_name = (EditText) findViewById(R.id.et_name);
        et_desc = (EditText) findViewById(R.id.et_desc);
        et_price = (EditText) findViewById(R.id.et_price);

        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WriteTask(AddActivity.this, WriteTask.ACTION_ADD).execute();
            }
        });
    }

    @Override
    public void setValues(ContentValues contentValues, String actionId) {
        String name = et_name.getText().toString().trim();

        if (name.isEmpty()) {
            success = false;
            return;
        }

        success = true;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AddActivity.this, "Added item.", Toast.LENGTH_SHORT).show();
            }
        });

        String desc = et_desc.getText().toString();
        double price = 0;
        try {
            price = Double.parseDouble(et_price.getText().toString());
        } catch (Exception ignored) {}

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
                    Toast.makeText(AddActivity.this, "Updated item.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(AddActivity.this, "Name cannot be empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
