package edu.amd.spbstu.uniquecircle.engine;

import java.util.List;

import edu.amd.spbstu.uniquecircle.support.Vector2D;
import edu.amd.spbstu.uniquecircle.support.event.BaseEvent;

public class OnTouchEvent extends BaseEvent<OnTouchEventListener> {
    void fireOnTouchEvent(List<GameObject> collidedObjects, int x, int y) {
        for (OnTouchEventListener listener : listeners) {
            GameObject gameObject = listener.getGameObject();
            if (collidedObjects.contains(gameObject)) {
                Vector2D pos = gameObject.getTransform().getGlobalPosition();

                listener.onTouchEvent(x - pos.x, y - pos.y);
            }
        }
    }
}
