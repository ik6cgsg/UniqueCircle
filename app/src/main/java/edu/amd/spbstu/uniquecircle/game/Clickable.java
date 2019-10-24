package edu.amd.spbstu.uniquecircle.game;

import android.util.Log;

import edu.amd.spbstu.uniquecircle.engine.Component;
import edu.amd.spbstu.uniquecircle.engine.GameObject;
import edu.amd.spbstu.uniquecircle.engine.OnTouchEventListener;
import edu.amd.spbstu.uniquecircle.support.event.BaseEvent;
import edu.amd.spbstu.uniquecircle.support.event.Event;
import edu.amd.spbstu.uniquecircle.support.event.EventListener;

public class Clickable extends Component implements OnTouchEventListener {
    private static final String TAG = "Clickable";
    private Event onClickEvent = new Event();
    private boolean clickableOnce;

    private Clickable(GameObject gameObject, boolean clickableOnce) {
        super(gameObject);
        gameObject.getScene().getOnTouchEvent().addListener(this);
        this.clickableOnce = clickableOnce;
    }

    public static Clickable addComponent(GameObject gameObject, boolean clickableOnce) {
        if (gameObject == null) {
            Log.e(TAG, "null game object");
            return null;
        }

        return new Clickable(gameObject, clickableOnce);
    }

    @Override
    public void onTouchEvent(float x, float y) {
        ClickAnimation.addComponent(getGameObject()).getEndEvent().addListeners(onClickEvent);
        if (clickableOnce)
            remove();
    }

    @Override
    public void remove() {
        getGameObject().getScene().getOnTouchEvent().removeListener(this);

        super.remove();
    }

    public BaseEvent<EventListener> getOnClickEvent() {
        return onClickEvent;
    }

    public void setClickableOnce(boolean clickableOnce) {
        this.clickableOnce = clickableOnce;
    }
}
