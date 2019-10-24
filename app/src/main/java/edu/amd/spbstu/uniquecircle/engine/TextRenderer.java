package edu.amd.spbstu.uniquecircle.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;

import edu.amd.spbstu.uniquecircle.support.Vector2D;

public class TextRenderer extends Renderer {
    private String text;
    private float pixelWidth;
    private float pixelHeight;
    private Matrix m = new Matrix();

    private TextRenderer(GameObject gameObject, String text, float pixelMaxDimension, int color) {
        super(gameObject);

        this.text = text;
        setColor(color);
        setPixelMaxDimension(pixelMaxDimension);
    }

    static public TextRenderer addComponent(GameObject gameObject, String text,
                                            float pixelMaxDimension) {
        return addComponent(gameObject, text, pixelMaxDimension, Color.BLACK);
    }

    static public TextRenderer addComponent(GameObject gameObject, String text,
                                            float pixelMaxDimension, int color) {
        if (gameObject == null) {
            Log.e("TextRenderer", "null game object");

            return null;
        }

        return new TextRenderer(gameObject, text, pixelMaxDimension, color);
    }

    public void setPixelWidth(float pixelWidth) {
        final float testTextSize = 48f;

        // Get the bounds of the text, using our testTextSize.
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        // Calculate the desired size as a proportion of our testTextSize.
        float desiredTextSize = testTextSize * pixelWidth / bounds.width();

        // Set the paint for that size.
        paint.setTextSize(desiredTextSize);

        this.pixelWidth = pixelWidth;
        this.pixelHeight = bounds.height() * desiredTextSize / testTextSize;
    }

    public void setPixelHeight(float pixelHeight) {
        final float testTextSize = 48f;

        // Get the bounds of the text, using our testTextSize.
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        // Calculate the desired size as a proportion of our testTextSize.
        float desiredTextSize = testTextSize * pixelHeight / bounds.height();

        // Set the paint for that size.
        paint.setTextSize(desiredTextSize);

        this.pixelHeight = pixelHeight;
        this.pixelWidth = bounds.width() * desiredTextSize / testTextSize;
    }

    public void setPixelMaxDimension(float pixelMaxDimension) {
        final float testTextSize = 48f;

        // Get the bounds of the text, using our testTextSize.
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        // Calculate the desired size as a proportion of our testTextSize.
        float hSize = testTextSize * pixelMaxDimension / bounds.height();
        float wSize = testTextSize * pixelMaxDimension / bounds.width();

        float desiredTextSize;
        if (hSize < wSize) {
            desiredTextSize = hSize;
            this.pixelWidth = bounds.width() * desiredTextSize / testTextSize;
            this.pixelHeight = pixelMaxDimension;
        }
        else {
            desiredTextSize = wSize;
            this.pixelWidth = pixelMaxDimension;
            this.pixelHeight = bounds.height() * desiredTextSize / testTextSize;
        }

        // Set the paint for that size.
        paint.setTextSize(desiredTextSize);
    }

    public float getPixelWidth() {
        return pixelWidth;
    }

    public float getPixelHeight() {
        return pixelHeight;
    }

    public void setText(String text) {
        this.text = text;
        setPixelMaxDimension(Math.max(pixelHeight, pixelWidth));
    }

    @Override
    protected void render(Canvas canvas) {
        Vector2D pos = transform.getGlobalPosition();
        Vector2D scale = transform.getGlobalScale();

        m.setTranslate(-pixelWidth / 2.0f, pixelHeight / 2.0f);
        m.postScale(scale.x, scale.y);
        m.postRotate(gameObject.getTransform().getGlobalRotation());
        m.postTranslate(pos.x, pos.y);

        canvas.setMatrix(m);
        canvas.drawText(text, 0, 0, paint);
        m.setScale(1, 1);
        canvas.setMatrix(m);
    }
}
