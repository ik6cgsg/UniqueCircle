package edu.amd.spbstu.uniquecircle;

import android.graphics.Canvas;
import android.graphics.Color;

import edu.amd.spbstu.uniquecircle.engine.CircleCollider;
import edu.amd.spbstu.uniquecircle.engine.GameObject;
import edu.amd.spbstu.uniquecircle.engine.BitmapRenderer;
import edu.amd.spbstu.uniquecircle.engine.Scene;
import edu.amd.spbstu.uniquecircle.support.Vector2D;

public class AppGame implements App {
    private MainActivity mainActivity;
    private int language;

    private Scene scene;

    public void init() {
        scene = new Scene();

        GameObject parent = GameObject.addToScene("parent", scene);
        GameObject child = parent.addChild("child");

        parent.getTransform().setPosition(new Vector2D(168, 168));
        child.getTransform().setPosition(new Vector2D(336, 0));

        BitmapRenderer.addComponent(parent, "circle", Color.RED);
        TestAnimation.addComponent(parent, 1);
        CircleCollider.addComponent(parent, 336);

        BitmapRenderer.addComponent(child, "circle", Color.GREEN);
        TestAnimation.addComponent(child, 1);
        CircleCollider.addComponent(child, 336);
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
        if (eventType == TOUCH_DOWN)
            return scene.onTouch(x, y);
        return false;
    }

    @Override
    public void update() {
        scene.update();
    }
}
