package com.xiaopeng.safemanager.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.xiaopeng.safemanager.R;
import com.xiaopeng.safemanager.activity.BackTableActivity;
import com.xiaopeng.safemanager.activity.SportDataActivity;
import com.xiaopeng.safemanager.activity.LoginActivity;
import com.xiaopeng.safemanager.activity.RegisterActivity;
import com.xiaopeng.safemanager.utils.SharedPreferencesUtil;
import com.xiaopeng.safemanager.views.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by liupeng on 2017/3/5.
 */

public class FragmentMyInfo extends BaseFragment {
    View view;
    @BindView(R.id.fragment_info_user_head_img)
    CircleImageView fragmentInfoUserHeadImg;
    @BindView(R.id.fragment_myinfo_setting_user)
    RelativeLayout fragmentMyinfoSettingUser;
    Unbinder unbinder;
    @BindView(R.id.fragment_myinfo_users)
    RelativeLayout fragmentMyinfoUsers;
    @BindView(R.id.fragment_myinfo_design)
    RelativeLayout fragmentMyinfoDesign;
    @BindView(R.id.fragment_myinfo_back_table)
    RelativeLayout fragmentMyinfoBackTable;

    @Override
    public void initData() {
    }

    @Override
    public View setupView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(context, R.layout.fragment_myinfo, null);
        }
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fragment_myinfo_back_table,R.id.fragment_info_user_head_img, R.id.fragment_myinfo_setting_user, R.id.fragment_myinfo_users ,R.id.fragment_myinfo_design})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_info_user_head_img:
                break;
            case R.id.fragment_myinfo_design:
                startActivity(new Intent(context, LoginActivity.class));
                break;
            case R.id.fragment_myinfo_users:
                SharedPreferencesUtil.putBool("userIsExit",true);
                startActivity(new Intent(context, LoginActivity.class));
                ((Activity)context).finish();
                break;
            case R.id.fragment_myinfo_setting_user:
                startActivity(new Intent(context, RegisterActivity.class));
                break;
            case R.id.fragment_myinfo_back_table:
                startActivity(new Intent(context, BackTableActivity.class));
                break;
        }
    }
}
