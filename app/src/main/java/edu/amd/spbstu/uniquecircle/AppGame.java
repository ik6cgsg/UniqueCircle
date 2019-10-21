package edu.amd.spbstu.uniquecircle;

import android.graphics.Canvas;
import android.graphics.Color;

import edu.amd.spbstu.uniquecircle.engine.GameObject;
import edu.amd.spbstu.uniquecircle.engine.BitmapRenderer;
import edu.amd.spbstu.uniquecircle.engine.Scene;

public class AppGame implements App {
    private MainActivity mainActivity;
    private int language;

    private Scene scene;

    public void init() {
        scene = new Scene();

        GameObject parent = new GameObject("parent");
        scene.addGameObject(parent);
        parent.addChild(new GameObject("child"));
        BitmapRenderer.addComponent(parent, "circle", Color.RED);
        TestAnimation.addComponent(parent);
    }

    public AppGame(MainActivity mainActivity, int language) {
        this.mainActivity = mainActivity;
        this.language = language;
    }

    public int getLanguage() {
        return language;
    }

    @Override
    public void drawCanvas(Canvas canvas) {
        scene.render(canvas);
    }

    @Override
    public void onOrientation(int orientation) {

    }

    @Override
    public boolean onTouch(int x, int y, int eventType) {
        if (eventType == TOUCH_UP)
            return scene.onTouch(x, y);
        return false;
    }

    @Override
    public void update() {
        scene.update();
    }
}
