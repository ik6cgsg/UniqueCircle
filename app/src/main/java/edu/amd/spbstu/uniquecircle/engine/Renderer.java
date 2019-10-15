package edu.amd.spbstu.uniquecircle.engine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import edu.amd.spbstu.uniquecircle.support.Vector2D;

public class Renderer extends Component {
    Paint paint;

    private Renderer(GameObject gameObject) {
        super(gameObject);
        paint = new Paint();
        paint.setColor(0xFFFF0000);
    }

    static public Renderer addComponent(GameObject gameObject) {
        if (gameObject == null) {
            Log.e("Render", "null game object");

            return null;
        }

        return new Renderer(gameObject);
    }

    @Override
    protected void render(Canvas canvas) {
        Vector2D pos = gameObject.getTransform().getGlobalPosition();
        Vector2D scale = gameObject.getTransform().getGlobalScale();
        canvas.drawCircle(pos.x, pos.y, scale.x * 69, paint);
    }
}
