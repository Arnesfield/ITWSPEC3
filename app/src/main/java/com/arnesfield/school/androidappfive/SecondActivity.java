package com.arnesfield.school.androidappfive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private Button btn_back;
    private TextView tv_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btn_back = (Button) findViewById(R.id.btn_back);
        tv_msg = (TextView) findViewById(R.id.tv_msg);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String msg = getIntent().getStringExtra("msg");

        tv_msg.setText(msg);
    }
}
