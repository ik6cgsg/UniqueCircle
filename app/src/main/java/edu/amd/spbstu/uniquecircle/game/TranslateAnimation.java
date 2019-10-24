package edu.amd.spbstu.uniquecircle.game;

import android.util.Log;

import edu.amd.spbstu.uniquecircle.engine.Animation;
import edu.amd.spbstu.uniquecircle.engine.GameObject;
import edu.amd.spbstu.uniquecircle.support.Vector2D;

public class TranslateAnimation extends Animation {
    private static final String TAG = "TranslateAnimation";
    private Vector2D endPos;

    private static float easing(float t) {
        return (float)Math.sqrt(t);
    }

    private TranslateAnimation(GameObject gameObject, Vector2D endPos, float lifeTime) {
        super(gameObject, lifeTime, false);
        this.endPos = endPos;
    }

    public static TranslateAnimation addComponent(GameObject gameObject, Vector2D endPos,
                                                  float lifeTime) {
        if (gameObject == null) {
            Log.e(TAG, "null game object");
            return null;
        }

        return new TranslateAnimation(gameObject, endPos, lifeTime);
    }

    @Override
    protected void animationUpdate(float t) {
        transform.setLocalPosition(transform.getLocalPosition().lerp(endPos, easing(t)));
    }
}
