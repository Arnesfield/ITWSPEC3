package com.cayle.mp8_cayle;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActionActivity extends AppCompatActivity {

    private Item item;
    private DBHelper db;
    private EditText et_id, et_name, et_desc, et_price;
    private String currAction;
    private int currPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
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

        et_id = (EditText) findViewById(R.id.et_id);
        et_name = (EditText) findViewById(R.id.et_name);
        et_desc = (EditText) findViewById(R.id.et_desc);
        et_price = (EditText) findViewById(R.id.et_price);

        Button btn_action = (Button) findViewById(R.id.btn_action);
        btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAction("btn-click");
            }
        });

        // get action
        currAction = getIntent().getStringExtra("action");
        currPos = getIntent().getIntExtra("pos", -1);

        btn_action.setText( (currAction.equals("add")) ? R.string.action_add : R.string.action_update);

        db = DBHelper.getHelper();

        String title = String.valueOf(currAction.charAt(0)).toUpperCase() + currAction.substring(1);
        setTitle( title + " Item" );

        if (currPos != -1)
            item = db.getItemAtPos(currPos);

        // doRefreshActivity();
        doAction(currAction + "-set");
    }

    private void doAction(String action) {
        switch (action) {
            case "update-set":
                et_id.setText(String.valueOf(item.getId()));
                et_name.setText(item.getName());
                et_desc.setText(item.getDesc());
                et_price.setText(String.format("%.2f", item.getPrice()));
                break;
            case "btn-click":
                doBtnAction();
                break;
        }
    }

    private void doBtnAction() {
        int id = -1;
        String name = null, desc = null;
        double price = -1;
        try {
            id = Integer.parseInt(et_id.getText().toString());
            name = et_name.getText().toString();
            desc = et_desc.getText().toString();
            price = Double.parseDouble(et_price.getText().toString());
        } catch (Exception e) {}

        switch (currAction) {
            case "add":
                try {
                    if (db.addItem(id, name, desc, price) == -1)
                        throw new Exception();
                    Toast.makeText(this, "Added item.", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    Toast.makeText(this, "Unable to add item.", Toast.LENGTH_SHORT).show();
                }
                break;
            case "update":
                try {
                    if (db.updateItem(item.getId(), id, name, desc, price) == 0)
                        throw new Exception();
                    Toast.makeText(this, "Updated item.", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    Toast.makeText(this, "Unable to update item.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
