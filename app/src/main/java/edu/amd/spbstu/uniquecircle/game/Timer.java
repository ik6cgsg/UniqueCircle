package edu.amd.spbstu.uniquecircle.game;

import android.util.Log;

import edu.amd.spbstu.uniquecircle.ViewGame;
import edu.amd.spbstu.uniquecircle.engine.BitmapRenderer;
import edu.amd.spbstu.uniquecircle.engine.GameObject;
import edu.amd.spbstu.uniquecircle.engine.TextRenderer;
import edu.amd.spbstu.uniquecircle.support.event.Event;

public class Timer {
    private static final String TAG = "Timer";

    private GameObject back;
    private GameObject text;

    private TimerController timerController;

    private static final int BACK_SPRITE_WIDTH = 128;
    private static final String BACK_SPRITE_NAME = "circle";

    private Timer(GameObject back, GameObject text, TimerController timerController) {
        this.back = back;
        this.text = text;
        this.timerController = timerController;
    }

    public static Timer create(GameObject parent, String name, float endTime) {
        if (parent == null) {
            Log.e(TAG, "null game object");
            return null;
        }

        GameObject back = parent.addChild(name);
        GameObject text = back.addChild("timer_text");

        BitmapRenderer.addComponent(back, BACK_SPRITE_NAME);
        TextRenderer.addComponent(text, "timer", ViewGame.dp2Px(BACK_SPRITE_WIDTH) * 0.30f);
        TimerController timerController = TimerController.addComponent(text, endTime);

        return new Timer(back, text, timerController);
    }

    public void start() {
        timerController.start();
    }

    public void stop() {
        timerController.stop();
    }

    public void reset() {
        timerController.reset();
    }

    public Event getOnTimerEnd() {
        return timerController.getOnTimerEnd();
    }

    public GameObject getGameObject() {
        return back;
    }
}
