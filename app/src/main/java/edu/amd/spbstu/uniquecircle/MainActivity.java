package edu.amd.spbstu.uniquecircle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private ImageView splash;
    public static final int APP_INTRO = 0;
    public static final int APP_GAME = 1;
    int appCur = -1;
    App appActive;
    AppIntro appIntro;
    AppGame appGame;
    ViewGame viewGame;
    int language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Window win = getWindow();
        win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // Intent intent = new Intent(this, AppIntro.class);
        // startActivityForResult(intent, 1);
        // Detect language
        String strLang = Locale.getDefault().getDisplayLanguage();
        if (strLang.equalsIgnoreCase("english")) {
            language = App.LANGUAGE_ENG;
        } else if (strLang.equalsIgnoreCase("русский")) {
            language = App.LANGUAGE_RUS;
        } else {
            language = App.LANGUAGE_UNKNOWN;
        }
        appIntro = new AppIntro(this, language);
        appActive = appIntro;
        setAppActive(APP_INTRO);
    }

    @Override
    public boolean onTouch(View v, MotionEvent evt) {
        int x = (int) evt.getX();
        int y = (int) evt.getY();
        int touchType = App.TOUCH_DOWN;

        if (evt.getAction() == MotionEvent.ACTION_MOVE)
            touchType = App.TOUCH_MOVE;
        if (evt.getAction() == MotionEvent.ACTION_UP)
            touchType = App.TOUCH_UP;

        return viewGame.onTouch(x, y, touchType);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewGame.start();
    }

    @Override
    protected void onPause()
    {
        // stop anims
        viewGame.stop();
        // complete system
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (appCur == APP_GAME) {
            // TODO viewGame.onDestroy();
            splash.setAnimation(null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // start game
            setContentView(R.layout.activity_main);
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            RotateAnimation anim = new RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(Animation.INFINITE);
            anim.setDuration(2000);
            splash = findViewById(R.id.star);
            splash.startAnimation(anim);
        }
    }

    public void setAppActive(int appID) {
        if (appCur == appID) {
            return;
        }

        appCur = appID;
        if (appCur == APP_INTRO) {
            appActive = appIntro;
        }
        if (appCur == APP_GAME) {
            // TODO mainActivity = m_appGame;
        }
    }

    public App getAppActive() {
        return appActive;
    }
}
