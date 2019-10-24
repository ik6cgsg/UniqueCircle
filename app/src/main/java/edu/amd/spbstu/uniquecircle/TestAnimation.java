package edu.amd.spbstu.uniquecircle;

import android.graphics.Color;
import android.util.Log;

import edu.amd.spbstu.uniquecircle.engine.Animation;
import edu.amd.spbstu.uniquecircle.engine.BitmapRenderer;
import edu.amd.spbstu.uniquecircle.engine.GameObject;
import edu.amd.spbstu.uniquecircle.engine.OnTouchEventListener;
import edu.amd.spbstu.uniquecircle.engine.Transform;
import edu.amd.spbstu.uniquecircle.support.event.EventListener;
import edu.amd.spbstu.uniquecircle.support.Vector2D;

public class TestAnimation extends Animation implements OnTouchEventListener {
    private Transform transform;
    private Vector2D pos = new Vector2D();
    private Vector2D scale = new Vector2D();
    private float factor;

    static TestAnimation addComponent(GameObject gameObject, float scale) {
        if (gameObject == null) {
            Log.e("TestAnimation", "null game object");

            return null;
        }

        return new TestAnimation(gameObject, scale);
    }

    private TestAnimation(GameObject gameObject, float scale) {
        super(gameObject, 5, true);
        transform = gameObject.getTransform();
        addListener(new EventListener() {
            @Override
            public void onEvent() {
                Log.d("TestAnimation", "End of loop");
            }
        });
        factor = scale;
        gameObject.getScene().getOnTouchEvent().addListener(this);
    }

    @Override
    protected void animationUpdate(float t) {
//        pos.set(150 + 100 * (float)Math.cos(t * Math.PI * 2),
//                150 + 100 * (float)Math.sin(t * Math.PI * 2));
//        scale.set(0.5f + (float)Math.cos(t * Math.PI * 4) / 3.0f,
//                  0.5f + (float)Math.cos(t * Math.PI * 4) / 3.0f);
//        scale.multiply(factor);
//        transform.setPosition(pos);
//        transform.setScale(scale);
    }

    @Override
    public void onTouchEvent(float x, float y) {
        Log.d("TestAnimation", "on touch event");
        getGameObject().getComponent(BitmapRenderer.class).setColor(Color.BLACK);
    }
}
