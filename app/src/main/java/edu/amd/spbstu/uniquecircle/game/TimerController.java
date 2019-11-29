package edu.amd.spbstu.uniquecircle.game;

import android.graphics.Canvas;
import android.util.Log;

import java.text.DecimalFormat;

import edu.amd.spbstu.uniquecircle.ViewGame;
import edu.amd.spbstu.uniquecircle.engine.Component;
import edu.amd.spbstu.uniquecircle.engine.GameObject;
import edu.amd.spbstu.uniquecircle.engine.TextRenderer;
import edu.amd.spbstu.uniquecircle.support.event.Event;

public class TimerController extends Component {
    private static final String TAG = "TimerController";

    private long startTime;
    private long endTime;
    private Event onTimerEnd = new Event();
    private boolean isRunning;

    private TextRenderer textRenderer;
    private static DecimalFormat decimalFormat = new DecimalFormat("#");

    private TimerController(GameObject gameObject, float endTime) {
        super(gameObject);

        this.endTime = (long)(endTime * 1000);
        textRenderer = gameObject.getComponent(TextRenderer.class);
    }

    public static TimerController addComponent(GameObject gameObject, float endTime) {
        if (gameObject == null) {
            Log.e(TAG, "null game object");
            return null;
        }
    
        return new TimerController(gameObject, endTime);
    }

    @Override
    protected void update() {
        if (textRenderer == null)
            textRenderer = getGameObject().getComponent(TextRenderer.class);
        if (textRenderer == null) {
            Log.e(TAG, "textRenderer is null!");
            return;
        }

        if (isRunning) {
            long curTime = ViewGame.getCurTime() - startTime;

            if (curTime >= endTime) {
                textRenderer.setText("0");
                onTimerEnd.fireEvent();
                isRunning = false;
                return;
            }

            textRenderer.setText(decimalFormat.format(Math.ceil((endTime - curTime) / 1000f)));
        }
    }

    @Override
    protected void render(Canvas canvas) {
        super.render(canvas);
    }

    public void start() {
        startTime = ViewGame.getCurTime();
        isRunning = true;
    }

    public void stop() {
        isRunning = false;
    }

    public void reset() {
        textRenderer.setText(decimalFormat.format((endTime) / 1000f));
    }

    public Event getOnTimerEnd() {
        return onTimerEnd;
    }
}
