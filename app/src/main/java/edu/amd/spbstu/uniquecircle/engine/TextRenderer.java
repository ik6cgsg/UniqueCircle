package edu.amd.spbstu.uniquecircle.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import edu.amd.spbstu.uniquecircle.support.Vector2D;

public class TextRenderer extends Renderer {
    private String text;
    private float pixelWidth;
    private float pixelHeight;

    private TextRenderer(GameObject gameObject, String text, int pixelWidth, int color) {
        super(gameObject);

        this.text = text;
        setColor(color);
        setPixelWidth(pixelWidth);
    }

    static public TextRenderer addComponent(GameObject gameObject, String text, int pixelWidth) {
        return addComponent(gameObject, text, pixelWidth, Color.BLACK);
    }

    static public TextRenderer addComponent(GameObject gameObject, String text,
                                            int pixelWidth, int color) {
        if (gameObject == null) {
            Log.e("TextRenderer", "null game object");

            return null;
        }

        return new TextRenderer(gameObject, text, pixelWidth, color);
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

    public float getPixelWidth() {
        return pixelWidth;
    }

    public float getPixelHeight() {
        return pixelHeight;
    }

    public void setText(String text) {
        this.text = text;
        setPixelWidth(pixelWidth);
    }

    @Override
    protected void render(Canvas canvas) {
        Vector2D pos = transform.getGlobalPosition();

        canvas.drawText(text, pos.x - pixelWidth / 2, pos.y + pixelHeight / 2, paint);
    }
}
