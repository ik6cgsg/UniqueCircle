package edu.amd.spbstu.uniquecircle.engine;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.amd.spbstu.uniquecircle.support.Vector2D;
import edu.amd.spbstu.uniquecircle.support.event.BaseEvent;

public class Scene {
    private GameObject rootObject;
    List<Collider> physicsScene = new CopyOnWriteArrayList<>();
    private OnTouchEvent onTouchEvent = new OnTouchEvent();

    public Scene() {
        rootObject = new GameObject("", this);
    }

    public GameObject addGameObject(String name) {
        if (name == null) {
            Log.e("Scene", "null name");
            return null;
        }

        GameObject output = new GameObject(name, this);
        rootObject.addChild(output);
        return output;
    }


    public void update() {
        rootObject.update();
    }

    public void render(Canvas canvas) {
        rootObject.render(canvas);
    }

    public List<GameObject> raycast(int x, int y) {
        List<GameObject> output = new LinkedList<>();
        Vector2D point = new Vector2D(x, y);

        // find all collided objects
        for (Collider collider : physicsScene)
            if (collider.checkCollision(point)) {
                output.add(collider.gameObject);

                // add parent objects as collided
                GameObject parent = collider.gameObject.getParent();
                while (parent != null) {
                    output.add(parent);
                    parent = parent.getParent();
                }
            }

        // remove duplicates and return
        return new ArrayList<>(new HashSet<>(output));
    }

    public boolean onTouch(int x, int y) {
        List<GameObject> collidedObjects = raycast(x, y);
        if (collidedObjects.size() == 0)
            return false;

        onTouchEvent.fireOnTouchEvent(collidedObjects, x, y);

        return true;
    }

    public GameObject getGameObject(String name) {
        return rootObject.getChild(name);
    }

    public BaseEvent<OnTouchEventListener> getOnTouchEvent() {
        return onTouchEvent;
    }
}
