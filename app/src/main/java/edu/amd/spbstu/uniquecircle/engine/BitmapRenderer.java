package edu.amd.spbstu.uniquecircle.engine;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import edu.amd.spbstu.uniquecircle.ViewGame;
import edu.amd.spbstu.uniquecircle.support.Vector2D;

public class BitmapRenderer extends Renderer {
    private Bitmap bitmap;

    private BitmapRenderer(GameObject gameObject, int bitmapId, int color) {
        super(gameObject);

        setColor(color);

        Resources res = ViewGame.getMainActivity().getResources();
        bitmap = BitmapFactory.decodeResource(res, bitmapId);
    }

    static public BitmapRenderer addComponent(GameObject gameObject, String bitmapName) {
        return addComponent(gameObject, bitmapName, Color.WHITE);
    }

    static public BitmapRenderer addComponent(GameObject gameObject, String bitmapName, int color) {
        if (gameObject == null) {
            Log.e("BitmapRenderer", "null game object");

            return null;
        }

        Activity mainActivity = ViewGame.getMainActivity();
        int id = mainActivity.getResources().getIdentifier(bitmapName,
                "drawable", mainActivity.getPackageName());

        if (id == 0) {
            Log.e("BitmapRenderer", "bitmap doesn't exist");

            return null;
        }

        return new BitmapRenderer(gameObject, id, color);
    }

    @Override
    protected void render(Canvas canvas) {
        Vector2D pos = transform.getGlobalPosition();
        Vector2D scale = transform.getGlobalScale();

        Matrix m = new Matrix();
        m.setTranslate(-bitmap.getWidth() / 2.0f, -bitmap.getHeight() / 2.0f);
        m.postScale(scale.x, scale.y);
        m.postRotate(gameObject.getTransform().getGlobalRotation());
        m.postTranslate(pos.x, pos.y);

        canvas.drawBitmap(bitmap, m, paint);
    }
}
