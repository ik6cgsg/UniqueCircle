package edu.amd.spbstu.uniquecircle.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;

import java.util.LinkedList;

import edu.amd.spbstu.uniquecircle.support.Vector2D;

public class TextRenderer extends Renderer {
    private String[] lines;
    private float pixelWidth;
    private float pixelHeight;
    private Matrix m = new Matrix();
    private int maxLineLength;

    private void separate2Lines(String text) {
        lines = text.split("\n");

        maxLineLength = 0;
        for (String line : lines)
            maxLineLength = Math.max(maxLineLength, line.length());
    }

    private TextRenderer(GameObject gameObject, String text, float pixelMaxDimension, int color) {
        super(gameObject);

        separate2Lines(text);
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

    public void setPixelMaxDimension(float pixelMaxDimension) {
        float minTextSize = 3059111;
        Rect maxBounds = new Rect();
        final float testTextSize = 48f;

        for (String line : lines) {
            // Get the bounds of the text, using our testTextSize.
            paint.setTextSize(testTextSize);
            Rect bounds = new Rect();
            paint.getTextBounds(line, 0, line.length(), bounds);

            // Calculate the desired size as a proportion of our testTextSize.
            float hSize = testTextSize * pixelMaxDimension / bounds.height();
            float wSize = testTextSize * pixelMaxDimension / bounds.width();
            float lineTextSize = Math.min(hSize, wSize);

            if (lineTextSize < minTextSize) {
                minTextSize = lineTextSize;
                maxBounds = bounds;
            }
        }

        this.pixelWidth = maxBounds.width() * minTextSize / testTextSize;
        this.pixelHeight = maxBounds.height() * minTextSize / testTextSize;

        // Set the paint for that size.
        paint.setTextSize(minTextSize);
    }

    public float getPixelWidth() {
        return pixelWidth;
    }

    public float getPixelHeight() {
        return pixelHeight;
    }

    public void setText(String text) {
        separate2Lines(text);
        setPixelMaxDimension(Math.max(pixelHeight, pixelWidth));
    }

    @Override
    protected void render(Canvas canvas) {
        final float LEFT_SHIFT = 0.1f / maxLineLength;
        final float LINE_SPACING = 5;

        Vector2D pos = transform.getGlobalPosition();
        Vector2D scale = transform.getGlobalScale();

        m.setTranslate(-pixelWidth * (0.5f + LEFT_SHIFT),
                -(pixelHeight * (lines.length - 2) + LINE_SPACING * (lines.length - 1)) / 2.0f);
        m.postScale(scale.x, scale.y);
        m.postRotate(gameObject.getTransform().getGlobalRotation());
        m.postTranslate(pos.x, pos.y);

        canvas.setMatrix(m);
//        setColor(Color.RED);
//        canvas.drawRect(0, 0, pixelWidth, -pixelHeight, paint);
//        setColor(Color.BLACK);
        for (int i = 0; i < lines.length; i++)
            canvas.drawText(lines[i], 0, (pixelHeight + LINE_SPACING) * i, paint);
        m.setScale(1, 1);
        canvas.setMatrix(m);
    }
}
