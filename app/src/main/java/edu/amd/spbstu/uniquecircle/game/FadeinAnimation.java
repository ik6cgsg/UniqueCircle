package edu.amd.spbstu.uniquecircle.game;

import android.graphics.Color;
import android.util.Log;

import java.util.List;

import edu.amd.spbstu.uniquecircle.engine.Animation;
import edu.amd.spbstu.uniquecircle.engine.GameObject;
import edu.amd.spbstu.uniquecircle.engine.Renderer;

public class FadeinAnimation extends Animation {
    private static final String TAG = "FadeinAnimation";
    private List<Renderer> renderers;


    private FadeinAnimation(GameObject gameObject, float lifeTime) {
        super(gameObject, lifeTime, false);

        renderers = getGameObject().getComponentsInChildren(Renderer.class);
    }

    public static FadeinAnimation addComponent(GameObject gameObject, float fadeTime) {
        if (gameObject == null) {
            Log.e(TAG, "null game object");
            return null;
        }

        return new FadeinAnimation(gameObject, fadeTime);
    }

    @Override
    protected void animationUpdate(float t) {
        for (Renderer renderer : renderers)
            renderer.setAlpha((int)(255 * t * t));
    }
}
