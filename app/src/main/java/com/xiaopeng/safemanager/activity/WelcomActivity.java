package com.xiaopeng.safemanager.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Window;
import android.view.WindowManager;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.xiaopeng.safemanager.fragment.FragmentWelcom1;
import com.xiaopeng.safemanager.fragment.FragmentWelcom2;
import com.xiaopeng.safemanager.fragment.FragmentWelcom3;
import com.xiaopeng.safemanager.utils.SharedPreferencesUtil;

public class WelcomActivity extends AppIntro implements ISlideBackgroundColorHolder {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
//        setupWindowAnimations();
        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        addSlide(new FragmentWelcom1());
        addSlide(new FragmentWelcom2());
        addSlide(new FragmentWelcom3());
//        addSlide(AppIntroFragment.newInstance("生活", "如此耀眼", R.drawable.welcom1, Color.parseColor("#3F51B5")));
//        addSlide(AppIntroFragment.newInstance("运动", "坚持每一秒，每一分", R.drawable.welcom2, Color.parseColor("#2196F3")));
//        addSlide(AppIntroFragment.newInstance("生命", "点缀我们的世界", R.drawable.welcom3, Color.parseColor("#6d2564")));

        // OPTIONAL METHODS
        // Override bar/separator color.
//        setBarColor(Color.parseColor("#3F51B5"));
//        setSeparatorColor(Color.parseColor("#3F51B5"));
        //设置小圆点的颜色
        setIndicatorColor(Color.parseColor("#6495ed"),Color.parseColor("#FF222222"));

        // Hide Skip/Done button.
        showSkipButton(false);
//        setSkipText("提示");
        //提示按钮  必须显示
        setProgressButtonEnabled(true);
        setDoneText("START");
        setColorDoneText(Color.parseColor("#6495ed"));
        setBackButtonVisibilityWithDone(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setColorSkipButton(Color.parseColor("#6495ed"));
        //设置下一个按钮的颜色
        setNextArrowColor(Color.parseColor("#6495ed"));
        setVibrateIntensity(30);
//        setFadeAnimation() setZoomAnimation() setFlowAnimation() setSlideOverAnimation() setDepthAnimation()
        setSlideOverAnimation();


    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        getSlides().indexOf(currentFragment);
        SharedPreferencesUtil.putBool("userIsUsedApp", true);
        startActivity(new Intent(WelcomActivity.this, LoginActivity.class));
        // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    @Override
    public int getDefaultBackgroundColor() {
        return Color.parseColor("#000000");
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        setBackgroundColor(backgroundColor);
    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(2000);
        getWindow().setEnterTransition(fade);

        Slide slide = new Slide();
        slide.setDuration(2000);
        getWindow().setExitTransition(slide);
    }
}
