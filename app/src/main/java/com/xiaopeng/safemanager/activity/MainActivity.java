package com.xiaopeng.safemanager.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.xiaopeng.safemanager.R;
import com.xiaopeng.safemanager.fragment.FragmentFood;
import com.xiaopeng.safemanager.fragment.FragmentSport;
import com.xiaopeng.safemanager.fragment.FragmentMyInfo;
import com.xiaopeng.safemanager.fragment.FragmentData;
import com.xiaopeng.safemanager.fragment.FragmentHealth;
import com.xiaopeng.safemanager.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.frameLayout_content)
    private FrameLayout contentFramelayout;
    @ViewInject(R.id.activity_main_rg)
    private RadioGroup mainActivityRadioGroup;
    @ViewInject(R.id.activity_main_rg_address_book)
    private RadioButton activity_main_rg_addressBook;
    @ViewInject(R.id.activity_main_rg_wait_work)
    private RadioButton activity_main_rg_waitWork;
    @ViewInject(R.id.activity_main_rg_business)
    private RadioButton activity_main_rg_business;
    @ViewInject(R.id.activity_main_rg_setting)
    private RadioButton activity_main_rg_message;
    @ViewInject(R.id.activity_main_rg_my_info)
    private RadioButton activity_main_rg_my_info;
    private List<Fragment> fragmentPagers = new ArrayList<>();
    private static boolean isExit = false;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
//        setupWindowAnimations();
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
    }

    /**
     * 初始化界面
     */
    private void initView() {
        this.fragmentPagers.add(new FragmentHealth());
        this.fragmentPagers.add(new FragmentFood());
        this.fragmentPagers.add(new FragmentSport());
        this.fragmentPagers.add(new FragmentData());
        this.fragmentPagers.add(new FragmentMyInfo());

        MainActivity.super.getFragmentManager().beginTransaction().replace(R.id.frameLayout_content,
                fragmentPagers.get(2)).commit();
        mainActivityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    //代办
                    case R.id.activity_main_rg_wait_work:
                        if (fragmentPagers.get(0).isAdded()) {
                            return;
                        }
                        MainActivity.super.getFragmentManager().beginTransaction().replace(R.id.frameLayout_content,
                                fragmentPagers.get(0)).commit();
                        break;
                    //通讯录
                    case R.id.activity_main_rg_address_book:
                        if (fragmentPagers.get(1).isAdded()) {
                            return;
                        }
                        MainActivity.super.getFragmentManager().beginTransaction().replace(R.id.frameLayout_content,
                                fragmentPagers.get(1)).commit();
                        break;
                    //业务
                    case R.id.activity_main_rg_business:
                        if (fragmentPagers.get(2).isAdded()) {
                            return;
                        }
                        MainActivity.super.getFragmentManager().beginTransaction().replace(R.id.frameLayout_content,
                                fragmentPagers.get(2)).commit();
                        break;
                    //设置
                    case R.id.activity_main_rg_setting:
                        if (fragmentPagers.get(3).isAdded()) {
                            return;
                        }
                        MainActivity.super.getFragmentManager().beginTransaction().replace(R.id.frameLayout_content,
                                fragmentPagers.get(3)).commit();
                        break;
                    //我的
                    case R.id.activity_main_rg_my_info:
                        if (fragmentPagers.get(4).isAdded()) {
                            return;
                        }
                        MainActivity.super.getFragmentManager().beginTransaction().replace(R.id.frameLayout_content,
                                fragmentPagers.get(4)).commit();
                        break;
                }
            }
        });
    }

    /**
     * 退出程序的逻辑
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            ToastUtil.show(getApplicationContext(), "再按一次退出程序");
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
        }

    }

    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setSlideEdge(Gravity.RIGHT);
        slide.setDuration(2000);
        getWindow().setEnterTransition(slide);


        getWindow().setReenterTransition(slide);

        Fade fade = new Fade();
        fade.setDuration(2000);
        getWindow().setExitTransition(fade);

    }
}
