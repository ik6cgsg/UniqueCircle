package edu.amd.spbstu.uniquecircle.engine;

import java.util.List;

import edu.amd.spbstu.uniquecircle.ViewGame;
import edu.amd.spbstu.uniquecircle.support.event.Event;
import edu.amd.spbstu.uniquecircle.support.event.EventListener;

public abstract class Animation extends Component {
    private long lifeTimeMs;
    private long startTimeMs;
    private float t;
    private Event endEvent;
    private boolean isLooping;

    protected Renderer renderer;
    protected Transform transform;

    protected Animation(GameObject gameObject, float lifeTime, boolean isLooping) {
        super(gameObject);
        startTimeMs = ViewGame.getCurTime();
        this.lifeTimeMs = (long)(1000 * lifeTime);
        this.isLooping = isLooping;
        endEvent = new Event();

        transform = getGameObject().getTransform();
        renderer = getGameObject().getComponent(Renderer.class);
    }

    public final void addListener(EventListener newListener) {
        endEvent.addListener(newListener);
    }

    public final void addListener(List<EventListener> newListeners) {
        endEvent.addListener(newListeners);
    }

    protected abstract void animationUpdate(float t);

    @Override
    protected final void update() {
        long curTimeMs = ViewGame.getCurTime() - startTimeMs;

        if (curTimeMs >= lifeTimeMs) {
            animationUpdate(1);

            endEvent.fireEvent();
            if (!isLooping)
                remove();
            else
                startTimeMs = ViewGame.getCurTime();
        }
        else
            animationUpdate((float)curTimeMs / lifeTimeMs);
    }
}
