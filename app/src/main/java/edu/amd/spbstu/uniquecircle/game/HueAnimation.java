package edu.amd.spbstu.uniquecircle.game;

import android.graphics.Color;
import android.util.Log;

import edu.amd.spbstu.uniquecircle.engine.Animation;
import edu.amd.spbstu.uniquecircle.engine.GameObject;
import edu.amd.spbstu.uniquecircle.engine.Renderer;

public class HueAnimation extends Animation {
    private static final String TAG = "HueAnimation";

    private float hueAngle;
    private Renderer renderer;
    private float[] hsv = {0, 0, 0};
    private float startHue;
    private float endHue;

    private HueAnimation(GameObject gameObject, float hueAngle, float lifeTime,
                         boolean isLooping, Renderer renderer) {
        super(gameObject, lifeTime, isLooping);

        this.hueAngle = hueAngle;
        this.renderer = renderer;
        Color.colorToHSV(renderer.getColor(), hsv);
        startHue = hsv[0];
        endHue = hsv[0] + hueAngle;
    }

    public static HueAnimation addComponent(GameObject gameObject, float hueAngle,
                                            float lifeTime, boolean isLooping) {
        if (gameObject == null) {
            Log.e(TAG, "null game object");
            return null;
        }

        Renderer renderer = gameObject.getComponent(Renderer.class);

        if (renderer == null) {
            Log.e(TAG, "object has no renderer");
            return null;
        }

        return new HueAnimation(gameObject, hueAngle, lifeTime, isLooping, renderer);
    }

    @Override
    protected void animationUpdate(float t) {
        hsv[0] = startHue * (1 - t) + endHue * t;

        while (hsv[0] < 0)
            hsv[0] += 360;
        while (hsv[0] >= 360)
            hsv[0] -= 360;

        renderer.setColor(Color.HSVToColor(Color.alpha(renderer.getColor()), hsv));
    }
}
