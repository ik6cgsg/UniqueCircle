package edu.amd.spbstu.uniquecircle.game;

import android.graphics.Color;
import android.util.Log;

import edu.amd.spbstu.uniquecircle.ViewGame;
import edu.amd.spbstu.uniquecircle.engine.BitmapRenderer;
import edu.amd.spbstu.uniquecircle.engine.CircleCollider;
import edu.amd.spbstu.uniquecircle.engine.GameObject;
import edu.amd.spbstu.uniquecircle.engine.Renderer;
import edu.amd.spbstu.uniquecircle.engine.TextRenderer;

public class FigureCircle {
    private static final float DIAMETER = ViewGame.dp2Px(128);
    private static final String TAG = "FigureCircle";

    private GameObject circle;
    private Renderer circleRenderer;
    private GameObject figureContainer;
    private GameObject figure;
    private Renderer figureRenderer;
    private static final float FIGURE_SCALE = 0.59f;

    private FigureCircle(GameObject circle, Renderer circleRenderer,
                        GameObject figureContainer, GameObject figure) {
        this.circle = circle;
        this.circleRenderer = circleRenderer;
        this.figureContainer = figureContainer;
        this.figure = figure;
    }

    public static FigureCircle create(GameObject parent, String name, int color) {
        if (parent == null) {
            Log.e(TAG, "null parent object");
            return null;
        }

        // create main circle
        GameObject circle = parent.addChild(name);
        CircleCollider.addComponent(circle, DIAMETER);
        Renderer circleRenderer = BitmapRenderer.addComponent(circle, "circle", color);

        // create scaled figure container circle
        GameObject figureContainer = circle.addChild("figureContainer");
        figureContainer.getTransform().setLocalScale(FIGURE_SCALE);

        // create figure
        GameObject figure = figureContainer.addChild("figure");

        return new FigureCircle(circle, circleRenderer, figureContainer, figure);
    }

    public void setBitmapFigureRenderer(String bitmapName) {
        if (figureRenderer != null)
            figureRenderer.remove();

        figureRenderer = BitmapRenderer.addComponent(figure, bitmapName);
    }

    public void setTextFigureRenderer(String text, int color) {
        if (figureRenderer != null)
            figureRenderer.remove();

        figureRenderer = TextRenderer.addComponent(figure, text, DIAMETER, color);
    }

    public GameObject getCircleObject() {
        return circle;
    }

    public GameObject getFigureContainerObject() {
        return figureContainer;
    }

    public GameObject getFigureObject() {
        return figure;
    }

    public Renderer getCircleRenderer() {
        return circleRenderer;
    }

    public Renderer getFigureRenderer() {
        return figureRenderer;
    }
}
