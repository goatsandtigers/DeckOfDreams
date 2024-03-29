package com.goatsandtigers.deckofdreams.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BitmapUtils {

    /**
     * Code taken from Stack Overflow.
     * @see <a href="https://stackoverflow.com/questions/33671196/floatingactionbutton-with-text-instead-of-image">
     *     https://stackoverflow.com/questions/33671196/floatingactionbutton-with-text-instead-of-image</a>
     */
    public static Bitmap textAsBitmap(String text) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }
}
