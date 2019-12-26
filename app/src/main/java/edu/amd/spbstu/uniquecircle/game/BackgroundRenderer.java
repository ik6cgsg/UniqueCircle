package edu.amd.spbstu.uniquecircle.game;

import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.Log;

import edu.amd.spbstu.uniquecircle.ViewGame;
import edu.amd.spbstu.uniquecircle.engine.BitmapRenderer;
import edu.amd.spbstu.uniquecircle.engine.GameObject;

public class BackgroundRenderer extends BitmapRenderer {
    private static final String TAG = "BackgroundRenderer";
    protected Paint gradient_paint;
    protected Rect rect;

    private BackgroundRenderer(GameObject gameObject, int id, String bitmapName, int color) {
        super(gameObject, id, bitmapName, color);

        rect = new Rect(0, 0, ViewGame.getScreenWidth(), ViewGame.getScreenHeight());

        gradient_paint = new Paint();
        int start_color = Color.argb(69, 255, 255, 255);
        int end_color = Color.argb(69, 5, 0, 10);
        gradient_paint.setShader(new LinearGradient(rect.left, rect.top, rect.right,
                rect.bottom, start_color, end_color, Shader.TileMode.CLAMP));

        BitmapShader patternBMPshader = new BitmapShader(bitmap,
                Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        paint.setShader(patternBMPshader);
    }

    static public BackgroundRenderer addComponent(GameObject gameObject, String bitmapName) {
        return addComponent(gameObject, bitmapName, Color.WHITE);
    }

    public static BackgroundRenderer addComponent(GameObject gameObject,
                                                  String bitmapName, int color) {
        if (gameObject == null) {
            Log.e(TAG, "null game object");

            return null;
        }

        int id = getBitmapId(bitmapName);
        if (id == 0) {
            Log.e(TAG, "bitmap doesn't exist");

            return null;
        }

        return new BackgroundRenderer(gameObject, id, bitmapName, color);
    }

    @Override
    protected void render(Canvas canvas) {
        super.render(canvas);
        canvas.drawRect(rect, paint);
        canvas.drawRect(rect, gradient_paint);
    }
}
