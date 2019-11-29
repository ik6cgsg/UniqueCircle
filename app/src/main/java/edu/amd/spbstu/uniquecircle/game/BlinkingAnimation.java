package edu.amd.spbstu.uniquecircle.game;

import android.util.Log;

import edu.amd.spbstu.uniquecircle.engine.Animation;
import edu.amd.spbstu.uniquecircle.engine.GameObject;
import edu.amd.spbstu.uniquecircle.engine.Renderer;

public class BlinkingAnimation extends Animation {
    private static final String TAG = "BlinkingAnimation";

    private int blinkNumber;
    private Renderer renderer;

    private BlinkingAnimation(GameObject gameObject, int blinkNumber,
                              float lifeTime, boolean isLooping) {
        super(gameObject, lifeTime, isLooping);

        this.blinkNumber = blinkNumber;
        renderer = gameObject.getComponent(Renderer.class);
    }

    public static BlinkingAnimation addComponent(GameObject gameObject, int blinkNumber,
                                                 float lifeTime, boolean isLooping) {
        if (gameObject == null) {
            Log.e(TAG, "null game object");
            return null;
        }
    
        return new BlinkingAnimation(gameObject, blinkNumber, lifeTime, isLooping);
    }

    @Override
    protected void animationUpdate(float t) {
        renderer.setAlpha((int)(255 * Math.abs(Math.cos(t * Math.PI * blinkNumber))));
    }
}
