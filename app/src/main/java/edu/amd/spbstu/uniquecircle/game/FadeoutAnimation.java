package edu.amd.spbstu.uniquecircle.game;

import android.graphics.Color;
import android.util.Log;

import java.util.List;

import edu.amd.spbstu.uniquecircle.engine.Animation;
import edu.amd.spbstu.uniquecircle.engine.GameObject;
import edu.amd.spbstu.uniquecircle.engine.Renderer;

public class FadeoutAnimation extends Animation {
    private static final String TAG = "FadeoutAnimation";
    private List<Renderer> renderers;


    private FadeoutAnimation(GameObject gameObject, float lifeTime) {
        super(gameObject, lifeTime, false);

        renderers = getGameObject().getComponentsInChildren(Renderer.class);
    }
    
    public static FadeoutAnimation addComponent(GameObject gameObject, float fadeTime) {
        if (gameObject == null) {
            Log.e(TAG, "null game object");
            return null;
        }
    
        return new FadeoutAnimation(gameObject, fadeTime);
    }

    @Override
    protected void animationUpdate(float t) {
        for (Renderer renderer : renderers)
            renderer.setAlpha((int)(255 * (1 - t) * (1 - t) * (1 - t)));
    }
}
