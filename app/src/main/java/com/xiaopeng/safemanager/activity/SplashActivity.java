package com.xiaopeng.safemanager.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.Visibility;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xiaopeng.safemanager.R;
import com.xiaopeng.safemanager.utils.SharedPreferencesUtil;

public class SplashActivity extends Activity {

    private ImageView welcomImageView;
    private SharedPreferencesUtil spUtil = new SharedPreferencesUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
//        setupWindowAnimations();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        spUtil.init(getApplicationContext(), "data_cache");

        welcomImageView = (ImageView) findViewById(R.id.activity_splash_image);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1);
        alphaAnimation.setDuration(2000);
        welcomImageView.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if (SharedPreferencesUtil.getBool("userIsUsedApp", false)) {
                    if (SharedPreferencesUtil.getBool("userIsExit",true)){
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    Intent intent = new Intent(SplashActivity.this, WelcomActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
