package edu.amd.spbstu.uniquecircle.game;

import android.util.Log;

import edu.amd.spbstu.uniquecircle.engine.Animation;
import edu.amd.spbstu.uniquecircle.engine.GameObject;

public class ClickAnimation extends Animation {
    private static final String TAG = "ClickAnimation";
    private static final float LIFE_TIME = 0.20f;
    private static final float EXPANSION = 0.20f;

    private ClickAnimation(GameObject gameObject) {
        super(gameObject, LIFE_TIME, false);
    }

    public static ClickAnimation addComponent(GameObject gameObject) {
        if (gameObject == null) {
            Log.e(TAG, "null game object");
            return null;
        }

        return new ClickAnimation(gameObject);
    }

    @Override
    protected void animationUpdate(float t) {
        transform.setLocalScale(1.0f + EXPANSION * (float)Math.sin(Math.PI * t));
    }
}
