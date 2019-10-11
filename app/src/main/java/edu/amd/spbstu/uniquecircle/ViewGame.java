package edu.amd.spbstu.uniquecircle;

import android.content.res.Configuration;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.view.View;

class RedrawHandler extends Handler {
    private ViewGame m_viewm_app;

    public RedrawHandler(ViewGame v) {
        m_viewm_app = v;
    }

    public void handleMessage(Message msg) {
        m_viewm_app.update();
        m_viewm_app.invalidate();
    }

    public void sleep(long delayMillis) {
        this.removeMessages(0);
        sendMessageDelayed(obtainMessage(0), delayMillis);
    }
}

public class ViewGame extends View {
    // CONST
    private static final int UPDATE_TIME_MS = 30;

    // DATA
    MainActivity mainActivity;
    RedrawHandler handler;
    long startTime;
    long curTime;
    private long prevTime;
    long deltaTimeMs;
    float deltaTime;

    boolean active;

    // METHODS
    public ViewGame(MainActivity app) {
        super(app);
        mainActivity = app;

        handler = new RedrawHandler(this);
        startTime = 0;
        active = false;
        setOnTouchListener(app);
    }

    public boolean performClick() {
        boolean b = super.performClick();
        return b;
    }

    public void start() {
        active = true;
        handler.sleep(UPDATE_TIME_MS);
    }

    public void stop() {
        active = false;
        //handler.sleep(UPDATE_TIME_MS);
    }

    public void update() {
        if (!active)
            return;
        // send next update to game
        if (active) {
            mainActivity.getAppActive().update();
            handler.sleep(UPDATE_TIME_MS);
        }
    }

    public boolean onTouch(int x, int y, int evtType) {
        App app = mainActivity.getAppActive();
        return app.onTouch(x, y, evtType);
    }

    public void onConfigurationChanged(Configuration confNew) {
        App app = mainActivity.getAppActive();
        if (confNew.orientation == Configuration.ORIENTATION_LANDSCAPE)
            app.onOrientation(App.APP_ORI_LANDSCAPE);
        if (confNew.orientation == Configuration.ORIENTATION_PORTRAIT)
            app.onOrientation(App.APP_ORI_PORTRAIT);
    }

    public void onDraw(Canvas canvas) {
        App app = mainActivity.getAppActive();
        prevTime = curTime;
        curTime = System.currentTimeMillis();
        deltaTimeMs = curTime - prevTime;
        deltaTime = deltaTimeMs / 1000.0f;
        app.drawCanvas(canvas);
    }
}
