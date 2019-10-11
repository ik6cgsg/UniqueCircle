package edu.amd.spbstu.uniquecircle.engine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Renderer extends Component {
    Paint paint;

    private Renderer(GameObject gameObject) {
        super(gameObject);
        paint = new Paint();
        paint.setColor(0xFFFF0000);
    }

    static public Renderer addComponent(GameObject parentObject) {
        if (parentObject == null) {
            Log.e("Render", "null parent object");

            return null;
        }

        return new Renderer(parentObject);
    }

    @Override
    protected void render(Canvas canvas) {
        canvas.drawCircle(69, 69, 30, paint);
    }
}
