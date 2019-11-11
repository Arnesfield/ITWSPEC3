package com.katrina.mp7_kat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DialogCreator.DialogActionListener {

    private EditText et_username, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show dialog
                DialogCreator.create(MainActivity.this, "login")
                        .setTitle("Login")
                        .setView(R.layout.dialog_login)
                        .setPositiveButton("Login")
                        .setNegativeButton("Cancel")
                        .show();
            }
        });
    }

    @Override
    public void onClickPositiveButton(String actionId) {
        switch (actionId) {
            case "login":
                String username, password;
                username = et_username.getText().toString();
                password = et_password.getText().toString();

                if (username.equals("kat") && password.equals("kat")) {
                    Intent intent = new Intent(this, ListViewActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(this, "You have logged in.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Incorrect username or password.", Toast.LENGTH_SHORT).show();
                }
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
        et_username = (EditText) view.findViewById(R.id.et_username);
        et_password = (EditText) view.findViewById(R.id.et_password);
    }
}