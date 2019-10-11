package edu.amd.spbstu.uniquecircle.engine;

import android.graphics.Canvas;

public class Scene {
    private GameObject rootObject;

    public Scene() {
        rootObject = new GameObject("");
    }

    public void update() {
        rootObject.update();
    }

    public void render(Canvas canvas) {
        rootObject.render(canvas);
    }

    public boolean onTouch(int x, int y) {
        return false;
    }

    public void addGameObject(GameObject gameObject) {
        rootObject.addChild(gameObject);
    }

    public GameObject getGameObject(String name) {
        return rootObject.getChild(name);
    }
}
