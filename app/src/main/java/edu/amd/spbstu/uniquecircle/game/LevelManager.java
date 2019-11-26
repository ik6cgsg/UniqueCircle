package edu.amd.spbstu.uniquecircle.game;

import android.graphics.Color;
import android.util.Log;
import android.util.SparseIntArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.amd.spbstu.uniquecircle.ViewGame;
import edu.amd.spbstu.uniquecircle.engine.Animation;
import edu.amd.spbstu.uniquecircle.engine.BitmapRenderer;
import edu.amd.spbstu.uniquecircle.engine.BlankAnimation;
import edu.amd.spbstu.uniquecircle.engine.Component;
import edu.amd.spbstu.uniquecircle.engine.GameObject;
import edu.amd.spbstu.uniquecircle.engine.Renderer;
import edu.amd.spbstu.uniquecircle.engine.TextRenderer;
import edu.amd.spbstu.uniquecircle.support.Vector2D;
import edu.amd.spbstu.uniquecircle.support.event.EventListener;

public class LevelManager extends Component {
    private static final String TAG = "LevelManager";
    private static final float CIRCLE_SCALE = 0.69f;
    private static final float TRANSLATE_TIME = 0.69f;
    private static final float FADEIN_TIME = 0.46f;
    private static final float FADEOUT_TIME = 0.69f;
    private static final float FADEOUT_DIST = 1.2f;

    private static final String[] SHAPES = {"circle", "square", "triangle", "moon", "star"};
            //"quarters", "face"};
    private static final int[] COLORS = {Color.RED, Color.GREEN, Color.BLUE,
                                         Color.YELLOW,  Color.CYAN, Color.MAGENTA};
    private static final Random random = new Random();

    private static String getRandomShape() {
        return SHAPES[random.nextInt(SHAPES.length)];
    }
    private static int getRandomColor() {
        return COLORS[random.nextInt(COLORS.length)];
    }

    private boolean needToInit = false;
    private int levelNumber = 1;
    private List<FigureCircle> sideCircles;
    private FigureCircle centerCircle;
    private int correctI;

    private EventListener goodClickListener = new EventListener() {
        @Override
        public void onEvent() {
            // start translation animation and set up next level listener
            TranslateAnimation.addComponent(sideCircles.get(correctI).getCircleObject(),
                    new Vector2D(), TRANSLATE_TIME).getEndEvent().addListener(nextLevelListener);
        }
    };

    private EventListener badClickListener = new EventListener() {
        @Override
        public void onEvent() {
            // do blank animation and reset level
            BlankAnimation.addComponent(getGameObject(),
                    TRANSLATE_TIME).getEndEvent().addListener(resetLevelListener);
        }
    };

    private EventListener removeClickablesListener = new EventListener() {
        @Override
        public void onEvent() {
            removeClickables();
        }
    };

    private EventListener removeAnimationsListener = new EventListener() {
        @Override
        public void onEvent() {
            removeAnimations();
        }
    };

    private EventListener fadeoutAnimationListener = new EventListener() {
        @Override
        public void onEvent() {
            for (int i = 0; i < sideCircles.size(); i++)
                if (i != correctI) {
                    GameObject circleObject = sideCircles.get(i).getCircleObject();

                    FadeoutAnimation.addComponent(circleObject, FADEOUT_TIME);
                    TranslateAnimation.addComponent(circleObject,
                            circleObject.getTransform().getLocalPosition().multiply(FADEOUT_DIST),
                            FADEOUT_TIME);
                }
        }
    };

    private EventListener resetLevelListener = new EventListener() {
        @Override
        public void onEvent() {
            // reset to level 1
            levelNumber = 1;
            needToInit = true;
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

    private DistinctFeature distinctFeature;
    private String correctShape;
    private String wrongShape;
    private int correctColor;
    private int wrongColor;

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
        FigureCircle centerCircle = FigureCircle.create(gameObject, "centerCircle", Color.GRAY);
        if (centerCircle == null) {
            Log.e(TAG, "null center circle");
            return null;
        }
        centerCircle.setTextFigureRenderer(String.valueOf(0), Color.WHITE);
        centerCircle.getFigureObject().getTransform().setLocalScale(0.58f);

        // create side circles
        List<FigureCircle> sideCircles = new ArrayList<>();
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

    private void removeAnimations() {
        for (FigureCircle circle : sideCircles) {
            List<Animation> animations = circle.getFigureObject().getComponents(Animation.class);

            for (Animation animation : animations)
                animation.remove();
        }
    }

    private void setFigure(FigureCircle circle, boolean correct)
    {
        switch (distinctFeature) {
            case COLOR:
                circle.getFigureRenderer().setColor(correct ? correctColor : wrongColor);
                ((BitmapRenderer)circle.getFigureRenderer()).setBitmap(getRandomShape());
                break;
            case SHAPE:
                circle.getFigureRenderer().setColor(getRandomColor());
                ((BitmapRenderer)circle.getFigureRenderer()).setBitmap(correct ? correctShape : wrongShape);
                break;
        }
    }

    private boolean checkUnsolvable() {
        Map<String, Integer> shapes = new HashMap<>();
        SparseIntArray colors = new SparseIntArray();
        int variations = 0;

        for (int i = 0; i < sideCircles.size(); i++) {
            if (i == correctI)
            continue;

            Renderer renderer = sideCircles.get(i).getFigureRenderer();
            Integer cnt;

            switch (distinctFeature) {
                case COLOR:
                    String shape = ((BitmapRenderer) renderer).getBitmapName();
                    cnt = shapes.get(shape);
                    if (cnt == null) {
                        shapes.put(shape, 1);
                        variations++;
                    } else
                        shapes.put(shape, cnt + 1);

                    break;
                case SHAPE:
                    int color = renderer.getColor();
                    cnt = colors.get(color);
                    if (cnt == 0)
                        variations++;
                    colors.put(color, cnt + 1);

                    break;
            }

            if (variations >= 3)
                return false;
        }

        switch (distinctFeature) {
            case COLOR:
                for (Map.Entry<String, Integer> entry : shapes.entrySet())
                    if (entry.getValue() == 1)
                        return true;
                break;
            case SHAPE:
                for (int color : COLORS)
                    if (colors.get(color) == 1)
                        return true;
                break;
        }

        return false;
    }

    private void fixUnsolvable()
    {
        if (!checkUnsolvable())
            return;

        for (int i = 0; i < sideCircles.size(); i++) {
            if (i == correctI)
                continue;

            switch (distinctFeature) {
                case COLOR:
                    BitmapRenderer bm_renderer = (BitmapRenderer)sideCircles.get(i).getFigureRenderer();
                    String name = bm_renderer.getBitmapName();
                    for (int j = 0; j < SHAPES.length; j++)
                        if (SHAPES[j].equals(name))
                            if (j == 0)
                                bm_renderer.setBitmap(SHAPES[j + 1]);
                            else
                                bm_renderer.setBitmap(SHAPES[j - 1]);
                    break;
                case SHAPE:
                    Renderer renderer = sideCircles.get(i).getFigureRenderer();
                    int color = renderer.getColor();
                    for (int j = 0; j < COLORS.length; j++)
                        if (COLORS[j] == color)
                            if (j == 0)
                                renderer.setColor(COLORS[j + 1]);
                            else
                                renderer.setColor(COLORS[j - 1]);
                    break;
            }

            break;
        }
    }

    private void determineDistinctFeature() {
        int index = random.nextInt(DistinctFeature.values().length);
        distinctFeature = DistinctFeature.values()[index];

        switch (distinctFeature) {
            case COLOR:
                do {
                    correctColor = getRandomColor();
                    wrongColor = getRandomColor();
                } while (correctColor == wrongColor);
                break;
            case SHAPE:
                do {
                    correctShape = getRandomShape();
                    wrongShape = getRandomShape();
                } while (correctShape.equals(wrongShape));
                break;
        }
    }

    private int getCircleNumber() {
        return Math.min(10, 4 + levelNumber / 5);
    }

    private void addRotationAnimation() {
        for (FigureCircle circle : sideCircles)
            RotationAnimation.addComponent(circle.getFigureObject(),
                    360f * (float)(Math.pow(-1, random.nextInt(2))),
                    4.71f, true);
    }

    private void addHueAnimation() {
        for (FigureCircle circle : sideCircles)
            HueAnimation.addComponent(circle.getFigureObject(), 360f,
                    4.71f, true);
    }

    private void addDifficultyModifiers() {
        if (levelNumber < 10)
            return;

        int modifier;
        if (levelNumber < 20)
            modifier = random.nextInt(2);
        else if (levelNumber < 30)
            modifier = random.nextInt(3);
        else
            modifier = random.nextInt(4);

        switch (modifier) {
            case 1:
                addHueAnimation();
                break;
            case 2:
                addRotationAnimation();
                break;
            case 3:
                addRotationAnimation();
                addHueAnimation();
                break;
        }

    }

    private void initLevel() {
        // delete old side circles
        for (int i = 0; i < sideCircles.size(); i++)
            sideCircles.get(i).remove();
        sideCircles.clear();

        // create new side circles
        int circle_number = getCircleNumber();
        for (int i = 0; i < circle_number; i++) {
            String name = "sideCircle".concat(String.valueOf(i));
            FigureCircle sideCircle = FigureCircle.create(centerCircle.getCircleObject(), name, Color.WHITE);
            if (sideCircle == null) {
                Log.e(TAG, "null ".concat(name));
                continue;
            }
            sideCircles.add(sideCircle);
            sideCircle.setBitmapFigureRenderer("base");
            //sideCircle.setTextFigureRenderer(name);
        }

        // update level number in the center
        ((TextRenderer)centerCircle.getFigureRenderer()).setText(String.valueOf(levelNumber));

        // set level scale
        centerCircle.getCircleObject().getTransform().setLocalScale(CIRCLE_SCALE);

        // set positions
        Vector2D center = new Vector2D(ViewGame.getScreenWidth() / 2,
                ViewGame.getScreenHeight() / 2);
        centerCircle.getCircleObject().getTransform().setLocalPosition(center);

        Vector2D shift = new Vector2D(0, -ViewGame.getScreenWidth() / 2);
        float delta = 2.0f * (float)Math.PI / sideCircles.size();
        for (FigureCircle circle : sideCircles) {
            circle.getCircleObject().getTransform().setLocalPosition(shift);
            shift.rotateBy(delta);
        }

        // determine distinct feature
        determineDistinctFeature();

        // determine correct answer
        correctI = Math.abs(random.nextInt(sideCircles.size()));

        // set up figures and click events
        for (int i = 0; i < sideCircles.size(); i++) {
            FigureCircle sideCircle = sideCircles.get(i);

            // set correct and wrong figures
            setFigure(sideCircle, i == correctI);

            // restore alpha
            sideCircle.getCircleRenderer().setAlpha(255);
            //sideCircle.getFigureRenderer().setAlpha(255);

            //FadeinAnimation.addComponent(sideCircle.getCircleObject(), FADEIN_TIME);
            FadeinAnimation.addComponent(sideCircle.getFigureObject(), FADEIN_TIME);

            Clickable clickable = Clickable.addComponent(sideCircle.getCircleObject(), true);
            clickable.getOnClickEvent().addListener(removeClickablesListener);
            clickable.getOnClickEvent().addListener(removeAnimationsListener);
            clickable.getAfterClickEvent().addListener(fadeoutAnimationListener);

            if (i == correctI)
                clickable.getAfterClickEvent().addListener(goodClickListener);
            else
                clickable.getAfterClickEvent().addListener(badClickListener);
        }

        // add difficulty modifiers
        addDifficultyModifiers();

        // fix unsolvable situations
        fixUnsolvable();

        // eat ass
    }

    private void nextLevel() {
        levelNumber++;
        needToInit = true;
    }

    @Override
    protected void update() {
        super.update();

        if (needToInit) {
            // check that no animations are running
            List<Animation> anims;
            anims = centerCircle.getCircleObject().getComponentsInChildren(Animation.class);
            if (anims.size() == 0) {
                initLevel();
                needToInit = false;
            }
        }
    }
}
