package edu.amd.spbstu.uniquecircle;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.logging.Level;

import edu.amd.spbstu.uniquecircle.engine.CircleCollider;
import edu.amd.spbstu.uniquecircle.engine.GameObject;
import edu.amd.spbstu.uniquecircle.engine.BitmapRenderer;
import edu.amd.spbstu.uniquecircle.engine.RectangularCollider;
import edu.amd.spbstu.uniquecircle.engine.Scene;
import edu.amd.spbstu.uniquecircle.engine.TextRenderer;
import edu.amd.spbstu.uniquecircle.game.BackgroundRenderer;
import edu.amd.spbstu.uniquecircle.game.Clickable;
import edu.amd.spbstu.uniquecircle.game.LevelManager;
import edu.amd.spbstu.uniquecircle.support.Vector2D;
import edu.amd.spbstu.uniquecircle.support.event.EventListener;

public class AppGame implements App {
    private MainActivity mainActivity;
    private int language;

    private Scene scene;
    private GameObject level;
    private GameObject tutorial;

    public void init() {
        scene = new Scene();

        level = scene.addGameObject("level");

        // add tutorial
        tutorial = level.addChild("tutorial");
        tutorial.getTransform().setLocalPosition(new Vector2D(ViewGame.getScreenWidth() / 2,
                ViewGame.getScreenHeight() / 2));
        final float textScale = 0.9f;
        RectangularCollider.addComponent(tutorial, ViewGame.getScreenWidth() / textScale,
                ViewGame.getScreenHeight() / textScale);
        Clickable.addComponent(tutorial, true).getAfterClickEvent().addListener(new EventListener() {
            @Override
            public void onEvent() {
                LevelManager.addComponent(level);
                tutorial.remove();
            }
        });
        String text = "";
        switch (mainActivity.language) {
            case App.LANGUAGE_ENG:
                text = "Welcome to \"Unique Circle\"!\n\n" +
                        "You have 5 seconds to figure out\n" +
                        "which circle is unique\n" +
                        "from the others and tap it.\n\n" +
                        "Be careful, tap the wrong one\n" +
                        "and you will lose!\n\n" +
                        "How far can you get?\n\n" +
                        "Tap to start!";
                break;
            case App.LANGUAGE_RUS:
                text = "Добро пожаловать\n" +
                        "в \"Уникальный Круг\"!\n\n" +
                        "У вас есть 5 секунд, чтобы понять\n" +
                        "какой из кругов отличается от\n" +
                        "остальных и выбрать его.\n\n" +
                        "Осторожно, выберите неправильный,\n" +
                        "и вы проиграете!\n\n" +
                        "Как далеко вы сможете дойти?\n\n" +
                        "Нажмите чтобы начать!";
                break;
            case App.LANGUAGE_UNKNOWN:
                text = "Language is unknown";
                break;
        }
        TextRenderer.addComponent(tutorial, text, ViewGame.getScreenWidth(), Color.WHITE);
        tutorial.getTransform().setLocalScale(textScale);

        // add level
        //LevelManager.addComponent(level);

        // add back button
        GameObject back_button = level.addChild("back_button");
        back_button.getTransform().setLocalPosition(new Vector2D(ViewGame.dp2Px(64), ViewGame.dp2Px(32)));
        BitmapRenderer.addComponent(back_button, "back_button");
        RectangularCollider.addComponent(back_button, ViewGame.dp2Px(128), ViewGame.dp2Px(64));
        Clickable.addComponent(back_button, false).getAfterClickEvent().addListener(new EventListener() {
            @Override
            public void onEvent() {
                onBackPressed();
            }
        });
        GameObject text_obj = back_button.addChild("text");
        switch (mainActivity.language) {
            case App.LANGUAGE_ENG:
                text = "Back";
                break;
            case App.LANGUAGE_RUS:
                text = "Назад";
                break;
            case App.LANGUAGE_UNKNOWN:
                text = "Language is unknown";
                break;
        }
        TextRenderer.addComponent(text_obj, text, ViewGame.dp2Px(128));
        text_obj.getTransform().setLocalScale(0.69f);

        // add background
        BackgroundRenderer.addComponent(level, "background");
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
    public void onBackPressed() {
        mainActivity.setAppActive(MainActivity.APP_INTRO);
    }

    @Override
    public void update() {
        scene.update();
    }
}
