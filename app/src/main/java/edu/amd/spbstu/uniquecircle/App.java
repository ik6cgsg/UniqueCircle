package edu.amd.spbstu.uniquecircle;

import android.graphics.Canvas;

public interface App {
    int APP_ORI_LANDSCAPE = 0;
    int APP_ORI_PORTRAIT = 1;

    int LANGUAGE_ENG = 0;
    int LANGUAGE_RUS = 1;
    int LANGUAGE_UNKNOWN = 2;

    int TOUCH_DOWN = 0;
    int TOUCH_MOVE = 1;
    int TOUCH_UP = 2;

    void drawCanvas(Canvas canvas);
    void onOrientation(int orientation);
    boolean onTouch(int x, int y, int evtType);
    void onBackPressed();
    void update();
}
