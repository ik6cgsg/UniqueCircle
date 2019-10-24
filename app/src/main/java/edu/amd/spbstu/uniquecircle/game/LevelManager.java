package edu.amd.spbstu.uniquecircle.game;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.amd.spbstu.uniquecircle.ViewGame;
import edu.amd.spbstu.uniquecircle.engine.Component;
import edu.amd.spbstu.uniquecircle.engine.GameObject;
import edu.amd.spbstu.uniquecircle.engine.TextRenderer;
import edu.amd.spbstu.uniquecircle.support.Vector2D;

public class LevelManager extends Component {
    private int levelNumber = 1;
    private List<FigureCircle> sideCircles = new ArrayList<>();
    private FigureCircle centerCircle;
    private static final String TAG = "LevelManager";
    private static final float CIRCLE_SCALE = 0.69f;

    private enum DistinctFeature {
        COLOR, SHAPE
    }

    private LevelManager(GameObject gameObject, List<FigureCircle> sideCircles,
                         FigureCircle centerCircle) {
        super(gameObject);
        this.sideCircles = sideCircles;
        this.centerCircle = centerCircle;
    }

    public static LevelManager addComponent(GameObject gameObject) {
        if (gameObject == null) {
            Log.e(TAG, "null parent object");
            return null;
        }

        // create center circle
        FigureCircle centerCircle = FigureCircle.create(gameObject, "centerCircle", Color.BLACK);
        if (centerCircle == null) {
            Log.e(TAG, "null center circle");
            return null;
        }
        centerCircle.setTextFigureRenderer(String.valueOf(0), Color.WHITE);
        centerCircle.getCircle().getComponent(Clickable.class).remove();

        // create side circles
        List<FigureCircle> sideCircles = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            String name = "sideCircle".concat(String.valueOf(i));
            FigureCircle sideCircle = FigureCircle.create(centerCircle.getCircle(), name, Color.GRAY);
            if (sideCircle == null) {
                Log.e(TAG, "null ".concat(name));
                return null;
            }
            sideCircles.add(sideCircle);
            sideCircle.setBitmapFigureRenderer("circle");
            //sideCircle.setTextFigureRenderer(name);
        }
        LevelManager levelManager = new LevelManager(gameObject, sideCircles, centerCircle);
        levelManager.initLevel();

        return levelManager;
    }

    private void initLevel() {
        centerCircle.getCircle().getTransform().setLocalScale(CIRCLE_SCALE);

        // set positions
        Vector2D center = new Vector2D(ViewGame.getScreenWidth() / 2,
                ViewGame.getScreenHeight() / 2);
        centerCircle.getCircle().getTransform().setLocalPosition(center);

        Vector2D shift = new Vector2D(0, -ViewGame.getScreenWidth() / 2);
        float delta = 2.0f * (float)Math.PI / 6.0f;
        for (int i = 0; i < 6; i++) {
            sideCircles.get(i).getCircle().getTransform().setLocalPosition(shift);
            shift.rotateBy(delta);
        }

        // update level number
        ((TextRenderer)centerCircle.getFigureRenderer()).setText(String.valueOf(levelNumber));

        // determine distinct feature



        // determine correct answer

        // set up touch event



        // eat ass
    }

    private void nextLevel() {
        levelNumber++;
        initLevel();
    }
}
