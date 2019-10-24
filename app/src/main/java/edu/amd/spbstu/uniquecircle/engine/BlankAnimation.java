package edu.amd.spbstu.uniquecircle.engine;

import android.util.Log;

public class BlankAnimation extends Animation {
    private static final String TAG = "BlankAnimation";
    
    private BlankAnimation(GameObject gameObject, float lifeTime) {
        super(gameObject, lifeTime, false);
    }
    
    public static BlankAnimation addComponent(GameObject gameObject, float lifeTime) {
        if (gameObject == null) {
            Log.e(TAG, "null game object");
            return null;
        }
    
        return new BlankAnimation(gameObject, lifeTime);
    }

    @Override
    protected void animationUpdate(float t) {
    }
}
