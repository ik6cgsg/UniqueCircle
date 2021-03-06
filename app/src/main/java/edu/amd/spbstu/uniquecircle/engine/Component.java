package edu.amd.spbstu.uniquecircle.engine;

import android.graphics.Canvas;

public abstract class Component {
    GameObject gameObject;

    public final GameObject getGameObject() {
        return gameObject;
    }

    public final Transform getTransform() {
        return gameObject.getTransform();
    }

    protected Component(GameObject gameObject) {
        this.gameObject = gameObject;
        gameObject.components.add(this);
    }

    protected void update() {}
    protected void render(Canvas canvas) {}

    public void remove() {
        gameObject.components.remove(this);
    }
}
