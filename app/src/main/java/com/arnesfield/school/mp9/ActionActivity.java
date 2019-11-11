package com.arnesfield.school.mp9;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class ActionActivity extends AppCompatActivity
        implements AddItemTask.TaskListener, UpdateItemTask.TaskListener, DialogCreator.DialogActionListener {

    private EditText et_name, et_desc, et_price;
    private Button btn_action;
    private RatingBar rbar_item;
    private String action;
    private Item currItem;

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
                doFinish();
            }
        });

        et_name = (EditText) findViewById(R.id.et_name);
        et_desc = (EditText) findViewById(R.id.et_desc);
        et_price = (EditText) findViewById(R.id.et_price);

        rbar_item = (RatingBar) findViewById(R.id.rbar_rating);

        btn_action = (Button) findViewById(R.id.btn_action);

        // get action
        action = getIntent().getStringExtra("action");

        if (action.equals("add")) {
            setTitle(R.string.title_activity_add_item);
            setupAdd();
        }
        else if (action.equals("update")) {
            setTitle(R.string.title_activity_update_item);
            int pos = getIntent().getIntExtra("pos", -1);
            currItem = Item.getItemAtPos(pos);
            setupUpdate();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            doFinish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void doFinish() {
        if (!(
            et_name.getText().toString().isEmpty() &&
            et_desc.getText().toString().isEmpty() &&
            et_price.getText().toString().isEmpty() &&
            rbar_item.getRating() == 0 &&
            action.equals("add")
        )) {
            DialogCreator.create(this, "cancel")
                    .setTitle(R.string.dialog_title_cancel)
                    .setMessage(R.string.dialog_msg_cancel)
                    .setPositiveButton(R.string.action_yes)
                    .setNegativeButton(R.string.action_no)
                    .show();
        }
        else finish();
    }

    // setup
    private void setupAdd() {
        btn_action.setText(R.string.action_add);
        btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddItemTask.execute(ActionActivity.this);
            }
        });
    }

    private void setupUpdate() {
        btn_action.setText(R.string.action_update);
        btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateItemTask.execute(ActionActivity.this);
            }
        });

        et_name.setText(currItem.getName());
        et_desc.setText(currItem.getDesc());
        et_price.setText(String.valueOf(currItem.getPrice()));

        rbar_item.setRating(currItem.getRating());
    }

    @Override
    public void onAddTaskPostExecute() {
        finish();
    }

    @Override
    public String createAddPostString(ContentValues contentValues) throws UnsupportedEncodingException {
        String name = et_name.getText().toString();
        SnackBarCreator.set(R.string.snack_added);

        if (name.isEmpty() || name.matches("[\\s]+")) {
            SnackBarCreator.set(R.string.msg_warn_retry);
            return null;
        }

        String desc = et_desc.getText().toString();
        float rating = rbar_item.getRating();
        double price = 0;
        try {
            price = Double.parseDouble(et_price.getText().toString());
        } catch (Exception ignored) {}

        contentValues.put("name", name);
        contentValues.put("desc", desc);
        contentValues.put("price", price);
        contentValues.put("rating", rating);

        // try for update
        try {
            int id = currItem.getId();
            contentValues.put("id", id);
            SnackBarCreator.set(R.string.snack_updated);
        } catch (Exception ignored) {}

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
    public void onUpdateTaskPostExecute() {
        finish();
    }

    @Override
    public String createUpdatePostString(ContentValues contentValues) throws UnsupportedEncodingException {
        return createAddPostString(contentValues);
    }

    // dialog
    @Override
    public void onClickPositiveButton(String actionId) {
        switch (actionId) {
            case "cancel":
                finish();
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
}
