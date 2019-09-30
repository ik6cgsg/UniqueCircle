package edu.amd.spbstu.uniquecircle;

import android.content.res.Configuration;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.view.View;

class RedrawHandler extends Handler {
    ViewIntro m_viewm_app;

    public RedrawHandler(ViewIntro v) {
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

public class ViewIntro extends View {
    // CONST
    private static final int UPDATE_TIME_MS = 30;

    // DATA
    MainActivity m_app;
    RedrawHandler handler;
    long startTime;
    int lineLen;
    boolean active;

    // METHODS
    public ViewIntro(MainActivity app) {
        super(app);
        m_app = app;

        handler = new RedrawHandler(this);
        startTime = 0;
        lineLen = 0;
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
        if (active)
            handler.sleep(UPDATE_TIME_MS);
    }

    public boolean onTouch(int x, int y, int evtType) {
        AppIntro app = m_app.getApp();
        return app.onTouch(x, y, evtType);
    }

    public void onConfigurationChanged(Configuration confNew) {
        AppIntro app = m_app.getApp();
        if (confNew.orientation == Configuration.ORIENTATION_LANDSCAPE)
            app.onOrientation(AppIntro.APP_ORI_LANDSCAPE);
        if (confNew.orientation == Configuration.ORIENTATION_PORTRAIT)
            app.onOrientation(AppIntro.APP_ORI_PORTRAIT);
    }

    public void onDraw(Canvas canvas) {
        AppIntro app = m_app.getApp();
        app.drawCanvas(canvas);
    }
}
