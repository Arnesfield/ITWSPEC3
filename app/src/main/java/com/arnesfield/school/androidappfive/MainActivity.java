package com.arnesfield.school.androidappfive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn_next;
    private EditText et_msg;
    private View toast_layout_root;
    private TextView tv_click, toast_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_next = (Button) findViewById(R.id.btn_next);
        et_msg = (EditText) findViewById(R.id.et_msg);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNext();
            }
        });

        tv_click = (TextView) findViewById(R.id.tv_click);
        registerForContextMenu(tv_click);

        LayoutInflater inflater = getLayoutInflater();
        toast_layout_root = inflater.inflate(R.layout.layout_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        toast_text = (TextView) toast_layout_root.findViewById(R.id.toast_text);
    }

    private void gotoNext() {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("msg", et_msg.getText().toString());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String str = "";
        switch (item.getItemId()) {
            case R.id.action_info:
                str = "Information";
                break;
            case R.id.action_email:
                str = "Email";
                break;
        }

        Toast toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                showToast("Add");
                return true;
            case R.id.action_edit:
                showToast("Edit");
                return true;
            case R.id.action_delete:
                showToast("Delete");
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    private void showToast(String msg) {
        toast_text.setText(msg);

        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

        toast.setView(toast_layout_root);
        toast.show();
    }
}
