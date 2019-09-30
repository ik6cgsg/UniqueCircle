package edu.amd.spbstu.uniquecircle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
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
    public static final int VIEW_INTRO = 0;
    public static final int VIEW_GAME = 1;
    int m_viewCur = -1;
    AppIntro m_app;
    ViewIntro m_viewIntro;
    //ViewGame m_viewGame;

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
        int language;
        if (strLang.equalsIgnoreCase("english")) {
            language = AppIntro.LANGUAGE_ENG;
        } else if (strLang.equalsIgnoreCase("русский")) {
            language = AppIntro.LANGUAGE_RUS;
        } else {
            language = AppIntro.LANGUAGE_UNKNOWN;
        }
        m_app = new AppIntro(this, language);
        setView(VIEW_INTRO);
    }

    @Override
    public boolean onTouch(View v, MotionEvent evt) {
        int x = (int) evt.getX();
        int y = (int) evt.getY();
        int touchType = AppIntro.TOUCH_DOWN;

        if (evt.getAction() == MotionEvent.ACTION_MOVE)
            touchType = AppIntro.TOUCH_MOVE;
        if (evt.getAction() == MotionEvent.ACTION_UP)
            touchType = AppIntro.TOUCH_UP;

        if (m_viewCur == VIEW_INTRO)
            return m_viewIntro.onTouch(x, y, touchType);
        if (m_viewCur == VIEW_GAME)
        //return m_viewGame.onTouch(x, y, touchType);
        {
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (m_viewCur == VIEW_INTRO) {
            m_viewIntro.start();
        }
        if (m_viewCur == VIEW_GAME) {
            //m_viewGame.start();
        }
    }

    @Override
    protected void onPause()
    {
        // stop anims
        if (m_viewCur == VIEW_INTRO) {
            m_viewIntro.stop();
        }
        if (m_viewCur == VIEW_GAME) {
            //m_viewGame.onPause();
        }
        // complete system
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (m_viewCur == VIEW_GAME) {
            //m_viewGame.onDestroy();
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

    public void setView(int viewID) {
        if (m_viewCur == viewID) {
            return;
        }

        m_viewCur = viewID;
        if (m_viewCur == VIEW_INTRO) {
            m_viewIntro = new ViewIntro(this);
            setContentView(m_viewIntro);
        }
        if (m_viewCur == VIEW_GAME) {
            //m_viewGame = new ViewGame(this);
            //setContentView(m_viewGame);
            //m_viewGame.start();
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

    public AppIntro getApp() {
        return m_app;
    }
}
