package edu.amd.spbstu.uniquecircle.game;

import android.util.Log;

import edu.amd.spbstu.uniquecircle.engine.Animation;
import edu.amd.spbstu.uniquecircle.engine.GameObject;

public class RotationAnimation extends Animation {
    private static final String TAG = "RotationAnimation";

    private float angle;

    public static RotationAnimation addComponent(GameObject gameObject, float angle,
                                                 float lifeTime, boolean isLooping) {
        if (gameObject == null) {
            Log.e(TAG, "null game object");
            return null;
        }
    
        return new RotationAnimation(gameObject, angle, lifeTime, isLooping);
    }

    private RotationAnimation(GameObject gameObject, float angle,
                              float lifeTime, boolean isLooping) {
        super(gameObject, lifeTime, isLooping);

        this.angle = angle;
    }

    @Override
    protected void animationUpdate(float t) {
        getTransform().setLocalRotation(angle * t);
    }
}
