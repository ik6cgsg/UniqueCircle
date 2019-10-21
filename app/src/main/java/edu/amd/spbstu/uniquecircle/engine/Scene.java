package edu.amd.spbstu.uniquecircle.engine;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Scene {
    private GameObject rootObject;
    List<Collider> physicsScene = new LinkedList<>();
    private OnTouchEvent onTouchEvent = new OnTouchEvent();

    public Scene() {
        rootObject = new GameObject("", this);
    }

    public void update() {
        rootObject.update();
    }

    public void render(Canvas canvas) {
        rootObject.render(canvas);
    }

    public List<GameObject> raycast(int x, int y) {
        List<GameObject> output = new LinkedList<>();

        // find all collided objects
        for (Collider collider : physicsScene)
            if (collider.checkCollision(x, y)) {
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

    void addGameObject(GameObject gameObject) {
        rootObject.addChild(gameObject);
    }

    public GameObject getGameObject(String name) {
        return rootObject.getChild(name);
    }

    public OnTouchEvent getOnTouchEvent() {
        return onTouchEvent;
    }
}
