package com.arnesfield.school.machineproblem7;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements RefreshableActivity, DialogCreator.DialogActionListener{

    private EditText et_username, et_password;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_login);
        setContentView(R.layout.activity_login);

        rootView = findViewById(R.id.root_view);

        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCreator.create(LoginActivity.this, "login")
                        .setTitle("Login")
                        .setView(R.layout.dialog_login_activity)
                        .setPositiveButton("Login")
                        .setNegativeButton("Cancel")
                        .show();
            }
        });

        doRefreshActivity();
    }

    @Override
    public void onClickPositiveButton(String actionId) {
        switch (actionId) {
            case "login":
                final String username = et_username.getText().toString();
                final String password = et_password.getText().toString();

                if (username.equals("user") && password.equals("password")) {
                    SnackBarCreator.set("Login successful.");
                    Intent intent = new Intent(this, ItemCartActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    SnackBarCreator.set("Invalid username or password.");
                    doRefreshActivity();
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

    @Override
    public void doRefreshActivity() {
        SnackBarCreator.show(rootView);
    }
}
