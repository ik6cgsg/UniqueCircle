package edu.amd.spbstu.uniquecircle.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;

abstract public class Renderer extends Component {
    protected Paint paint;
    protected Transform transform;
    int color;

    protected Renderer(GameObject gameObject) {
        super(gameObject);

        paint = new Paint();
        paint.setColor(0xFFFFFFFF);
        transform = gameObject.getTransform();
    }

    public void setColor(int color) {
        ColorFilter filter = new LightingColorFilter(color, 0);
        paint.setColorFilter(filter);
        paint.setColor(color);
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setAlpha(int alpha) {
        setColor(Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color)));
    }

    @Override
    abstract protected void render(Canvas canvas);
}
