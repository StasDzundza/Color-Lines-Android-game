package com.example.colorlinesclassic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Drawer {
    private static Paint paint = new Paint();

    static {
        paint.setAntiAlias(true);
    }

    public static Bitmap getBallBitmap(int color, int width, int height, int radius) {
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        paint.setColor(color);
        Canvas c = new Canvas(bmp);
        c.drawCircle(width / 2, height / 2, radius, paint);
        return bmp;
    }
}
