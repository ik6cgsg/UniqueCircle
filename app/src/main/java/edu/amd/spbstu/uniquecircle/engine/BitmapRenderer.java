package edu.amd.spbstu.uniquecircle.engine;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Log;

import edu.amd.spbstu.uniquecircle.ViewGame;
import edu.amd.spbstu.uniquecircle.support.Vector2D;

public class BitmapRenderer extends Renderer {
    private Bitmap bitmap;
    private Activity mainActivity;
    private Matrix m = new Matrix();
    private String bitmapName;

    private BitmapRenderer(GameObject gameObject, int bitmapId, String bitmapName, int color) {
        super(gameObject);

        setColor(color);

        mainActivity = ViewGame.getMainActivity();
        Resources res = mainActivity.getResources();
        bitmap = BitmapFactory.decodeResource(res, bitmapId);

        this.bitmapName = bitmapName;
    }

    public void setBitmap(String bitmapName) {
        int id = mainActivity.getResources().getIdentifier(bitmapName,
                "drawable", mainActivity.getPackageName());

        if (id == 0) {
            Log.e("BitmapRenderer", "bitmap doesn't exist");

            return;
        }

        this.bitmapName = bitmapName;
        Resources res = mainActivity.getResources();
        bitmap = BitmapFactory.decodeResource(res, id);
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

        return new BitmapRenderer(gameObject, id, bitmapName, color);
    }

    @Override
    protected void render(Canvas canvas) {
        Vector2D pos = transform.getGlobalPosition();
        Vector2D scale = transform.getGlobalScale();

        m.setTranslate(-bitmap.getWidth() / 2.0f, -bitmap.getHeight() / 2.0f);
        m.postScale(scale.x, scale.y);
        m.postRotate(gameObject.getTransform().getGlobalRotation());
        m.postTranslate(pos.x, pos.y);

        canvas.drawBitmap(bitmap, m, paint);
    }

    public String getBitmapName() {
        return bitmapName;
    }
}
