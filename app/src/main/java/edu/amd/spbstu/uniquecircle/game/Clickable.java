package edu.amd.spbstu.uniquecircle.game;

import android.util.Log;

import edu.amd.spbstu.uniquecircle.engine.Component;
import edu.amd.spbstu.uniquecircle.engine.GameObject;
import edu.amd.spbstu.uniquecircle.engine.OnTouchEventListener;

public class Clickable extends Component implements OnTouchEventListener {
    private static final String TAG = "Clickable";

    private Clickable(GameObject gameObject) {
        super(gameObject);
        gameObject.getScene().getOnTouchEvent().addListener(this);
    }

    public static Clickable addComponent(GameObject gameObject) {
        if (gameObject == null) {
            Log.e(TAG, "null game object");
            return null;
        }

        return new Clickable(gameObject);
    }

    @Override
    public void onTouchEvent(float x, float y) {
        ClickAnimation.addComponent(getGameObject());
    }

    @Override
    public void remove() {
        getGameObject().getScene().getOnTouchEvent().removeListener(this);

        super.remove();
    }
}
