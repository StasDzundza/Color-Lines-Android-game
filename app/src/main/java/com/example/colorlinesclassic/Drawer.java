package com.example.colorlinesclassic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Drawer {
    public static Bitmap getBallBitmap(int color, int width, int height, int radius) {
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);

        Canvas c = new Canvas(bmp);
        c.drawCircle(width / 2, height / 2, radius, paint);
        return bmp;
    }
}
