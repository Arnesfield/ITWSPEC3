package com.cayle.mp12_cayle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

public class MyCanvas extends View {

    private boolean down, up, left, right;
    private boolean initial;
    private int xPos, yPos;

    public MyCanvas(Context context) {
        super(context);
        down = true;
        up = left = right = false;
        initial = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.YELLOW);

        Bitmap ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);

        int speed = 8;
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        int ballWidth = ball.getWidth();
        int ballHeight = ball.getHeight();


        if (initial) {
            initial = false;

            int centerX = canvasWidth/2 - ballWidth/2;
            int centerY = canvasHeight/2 - ballHeight/2;

            xPos = centerX;
            yPos = centerY;
        }

        canvas.drawBitmap(ball, xPos, yPos, null);

        // if hit down
        if (ballHeight + yPos >= canvasHeight) {
            up = true;
            down = false;

            if (right == left)
                right = !right;
        }

        if (yPos <= 0) {
            up = false;
            down = true;
        }

        if (xPos <= 0) {
            right = true;
            left = false;
        }

        if (xPos + ballWidth >= canvasWidth) {
            right = false;
            left = true;
        }


        if (up) yPos -= speed;
        if (down) yPos += speed;
        if (left) xPos -= speed;
        if (right) xPos += speed;

        invalidate();
    }
}
