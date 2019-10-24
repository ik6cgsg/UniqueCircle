package edu.amd.spbstu.uniquecircle.game;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.amd.spbstu.uniquecircle.ViewGame;
import edu.amd.spbstu.uniquecircle.engine.Component;
import edu.amd.spbstu.uniquecircle.engine.GameObject;
import edu.amd.spbstu.uniquecircle.engine.TextRenderer;
import edu.amd.spbstu.uniquecircle.support.Vector2D;
import edu.amd.spbstu.uniquecircle.support.event.EventListener;

public class LevelManager extends Component {
    private static final String TAG = "LevelManager";
    private static final float CIRCLE_SCALE = 0.69f;
    private static final int CIRCLE_NUMBER = 6;

    private int levelNumber = 1;
    private List<FigureCircle> sideCircles = new ArrayList<>();
    private FigureCircle centerCircle;
    private Random random = new Random();

    class GoodClickListener implements EventListener {
        private static final float TRANSLATE_TIME = 0.30f;
        private GameObject translatedObject;

        public void setTranslatedObject(GameObject translatedObject) {
            this.translatedObject = translatedObject;
        }

        @Override
        public void onEvent() {
            // start translation animation and set up next level listener
            TranslateAnimation.addComponent(translatedObject, new Vector2D(),
                    TRANSLATE_TIME).getEndEvent().addListener(nextLevelListener);
        }
    }

    private GoodClickListener goodClickListener = new GoodClickListener();
    private EventListener badClickListener = new EventListener() {
        @Override
        public void onEvent() {
            // reset to level 1
            levelNumber = 1;
            initLevel();
        }
    };

    private EventListener nextLevelListener = new EventListener() {
        @Override
        public void onEvent() {
            nextLevel();
        }
    };

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

        // create side circles
        List<FigureCircle> sideCircles = new ArrayList<>();
        for (int i = 0; i < CIRCLE_NUMBER; i++) {
            String name = "sideCircle".concat(String.valueOf(i));
            FigureCircle sideCircle = FigureCircle.create(centerCircle.getCircleObject(), name, Color.GRAY);
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

    private void removeClickables() {
        for (FigureCircle circle : sideCircles) {
            Clickable clickable = circle.getCircleObject().getComponent(Clickable.class);
            if (clickable != null)
                clickable.remove();
        }
    }

    private void initLevel() {
        // update level number in the center
        ((TextRenderer)centerCircle.getFigureRenderer()).setText(String.valueOf(levelNumber));

        // set level scale
        centerCircle.getCircleObject().getTransform().setLocalScale(CIRCLE_SCALE);

        // set positions
        Vector2D center = new Vector2D(ViewGame.getScreenWidth() / 2,
                ViewGame.getScreenHeight() / 2);
        centerCircle.getCircleObject().getTransform().setLocalPosition(center);

        Vector2D shift = new Vector2D(0, -ViewGame.getScreenWidth() / 2);
        float delta = 2.0f * (float)Math.PI / CIRCLE_NUMBER;
        for (FigureCircle circle : sideCircles) {
            circle.getCircleObject().getTransform().setLocalPosition(shift);
            shift.rotateBy(delta);
        }

        // determine distinct feature
        // TODO

        // determine correct answer
        int correct_i = Math.abs(random.nextInt()) % CIRCLE_NUMBER;

        // set up click event
        for (int i = 0; i < CIRCLE_NUMBER; i++) {
            FigureCircle sideCircle = sideCircles.get(i);
            Clickable clickable = Clickable.addComponent(sideCircle.getCircleObject(), true);
            clickable.getOnClickEvent().addListener(new EventListener() {
                @Override
                public void onEvent() {
                    removeClickables();
                }
            });
            if (i == correct_i) {
                goodClickListener.setTranslatedObject(sideCircle.getCircleObject());
                clickable.getOnClickEvent().addListener(goodClickListener);
                sideCircles.get(i).getFigureRenderer().setColor(Color.GREEN);
            }
            else {
                clickable.getOnClickEvent().addListener(badClickListener);
                sideCircles.get(i).getFigureRenderer().setColor(Color.WHITE);
            }

        }


        // eat ass
    }

    private void nextLevel() {
        levelNumber++;
        initLevel();
    }
}
