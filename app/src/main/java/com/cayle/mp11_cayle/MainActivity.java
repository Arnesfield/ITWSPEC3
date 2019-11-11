package com.cayle.mp11_cayle;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final Random RANDOM = new Random();
    private ImageView iv1, iv2, iv3;
    private ImageView[] wheels;
    private Button btnAction;
    private boolean started;
    private static View.OnClickListener onClickListener;
    private View rootView;
    private TextView tvTotal;
    private AnimationDrawable[] anim;
    private static int[] PRICES = {
            100, 120, 150, 200, 500, 1000
    };
    private static int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAction = (Button) findViewById(R.id.btn_action);
        tvTotal = (TextView) findViewById(R.id.tv_total);

        iv1 = (ImageView) findViewById(R.id.iv_1);
        iv2 = (ImageView) findViewById(R.id.iv_2);
        iv3 = (ImageView) findViewById(R.id.iv_3);

        iv1.setBackgroundResource(R.drawable.diamond);
        iv2.setBackgroundResource(R.drawable.diamond);
        iv3.setBackgroundResource(R.drawable.diamond);

        rootView = findViewById(R.id.root_view);
        wheels = new ImageView[]{iv1, iv2, iv3};
        started = true;

        anim = new AnimationDrawable[3];

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // removeListener();

                if (started) {
                    started = false;

                    for (int i = 0; i < wheels.length; i++) {
                        btnAction.setOnClickListener(null);
                        // animate images
                        ImageView iv = wheels[i];
                        iv.setBackgroundResource(R.drawable.anim);
                        anim[i] = (AnimationDrawable) iv.getBackground();
                        anim[i].start();
                        btnAction.setOnClickListener(MainActivity.onClickListener);
                        btnAction.setText(R.string.stop);
                    }

                } else {
                    started = true;
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            btnAction.setOnClickListener(null);
                            for (AnimationDrawable a : anim) {
                                try {
                                    sleep(RANDOM.nextInt(500) + 200);
                                    a.stop();
                                    sleep(100);
                                }
                                catch (InterruptedException e) {}
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    btnAction.setOnClickListener(MainActivity.onClickListener);
                                    Toast.makeText(MainActivity.this, getMessage(), Toast.LENGTH_SHORT).show();
                                    tvTotal.setText("Points: " + total);
                                    btnAction.setText(R.string.roll);
                                }
                            });

                            interrupt();
                        }
                    }.start();

                }
            }
        };

        btnAction.setOnClickListener(onClickListener);
    }

    private String getMessage() {
        String message = "";
        int add = 0;

        if (compare(0, 1) && compare(1, 2)) {
            add = addTotalFrom(1, 3);
            if (getCurrentFrameOf(0) == 5) {
                message = "WAW! JACKPOT!";
            }
            else {
                message = "Wowziee!!";
            }
        }
        else if (compare(0, 1) || compare(0, 2) || compare(1, 2)) {

            if (compare(1, 2)) {
                add = addTotalFrom(1, 2);
                message = "Awesome!";
            }
            else {
                add = addTotalFrom(0, 2);
                message = "Great!";
            }

        }
        else {
            return "Too bad! Roll again!";
        }

        return message;
    }

    private int addTotalFrom(int pos, int multiplier) {
        int prize = getPointsOf(pos) * multiplier;
        total += prize;
        return prize;
    }

    private int getPointsOf(int pos) {
        return PRICES[getCurrentFrameOf(pos)];
    }

    private boolean compare(int n1, int n2) {
        return getCurrentFrameOf(n1) == getCurrentFrameOf(n2);
    }

    private int getCurrentFrameOf(int pos) {
        AnimationDrawable ad = anim[pos];
        Drawable d = ad.getCurrent();
        for (int i = 0; i < ad.getNumberOfFrames(); i++) {
            if (ad.getFrame(i) == d)
                return i;
        }
        return -1;
    }
}
