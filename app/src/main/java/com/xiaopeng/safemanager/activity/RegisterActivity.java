package com.xiaopeng.safemanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.xiaopeng.safemanager.R;
import com.xiaopeng.safemanager.bean.GsonObjModel;
import com.xiaopeng.safemanager.bean.SportDataBean;
import com.xiaopeng.safemanager.bean.UserInfoBean;
import com.xiaopeng.safemanager.config.Config;
import com.xiaopeng.safemanager.db.dao.UserInfoDao;
import com.xiaopeng.safemanager.utils.HttpPost;
import com.xiaopeng.safemanager.utils.ToastUtil;
import com.xiaopeng.safemanager.views.RulerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends Activity {
    @BindView(R.id.activity_register_username)
    EditText activityRegisterUsername;
    @BindView(R.id.btn_register_info_sex)
    CheckBox btnRegisterInfoSex;
    @BindView(R.id.tv_register_info_height_value)
    TextView tvRegisterInfoHeightValue;
    @BindView(R.id.ruler_height)
    RulerView rulerHeight;
    @BindView(R.id.tv_register_info_weight_value)
    TextView tvRegisterInfoWeightValue;
    @BindView(R.id.ruler_weight)
    RulerView rulerWeight;
    @BindView(R.id.activity_register_back)
    ImageView activityRegisterBack;
    @BindView(R.id.activity_register_ok)
    Button activityRegisterOk;
    UserInfoDao userInfoDao;
    @BindView(R.id.activity_register_password)
    EditText activityRegisterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        userInfoDao = new UserInfoDao(this);


        rulerHeight.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tvRegisterInfoHeightValue.setText(value + "");
            }
        });


        rulerWeight.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tvRegisterInfoWeightValue.setText(value + "");
            }
        });

        rulerHeight.setValue(175, 50, 250, 1);
        rulerWeight.setValue(60, 10, 300, 0.1f);
    }

    @OnClick({R.id.activity_register_back, R.id.activity_register_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_register_back:
                finish();
                break;
            case R.id.activity_register_ok:
                if (TextUtils.isEmpty(activityRegisterUsername.getText().toString().trim())) {
                    ToastUtil.show(getApplicationContext(), "名字不能为空");
                    return;
                }
                if (activityRegisterPassword.getText().toString().length()<6) {
                    ToastUtil.show(getApplicationContext(), "密码不能小于6位");
                    return;
                }
                registerUser();

                if (registerSuccess()) {
                    ToastUtil.show(getApplicationContext(), "注册成功");
                    finish();
                } else {
                    ToastUtil.show(getApplicationContext(), "注册失败");
                }
                break;
        }
    }
    private boolean registerSuccess(){
        String userName=activityRegisterUsername.getText().toString().trim();
        String userPassword=activityRegisterPassword.getText().toString().trim();
        String userHeight=tvRegisterInfoHeightValue.getText().toString().trim();
        String userWeight=tvRegisterInfoWeightValue.getText().toString().trim();
        String userSex=btnRegisterInfoSex.isChecked()?"女":"男";
        return userInfoDao.add(userName,userPassword, userHeight, userWeight, userSex);
    }


    private void registerUser() {
        String url = Config.baseUrl+"safe/registerUser";
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("user_name",activityRegisterUsername.getText().toString().trim());
        params.addQueryStringParameter("user_pwd",activityRegisterPassword.getText().toString().trim());
        params.addQueryStringParameter("user_sex",btnRegisterInfoSex.isChecked()?"女":"男");
        params.addQueryStringParameter("user_height",tvRegisterInfoHeightValue.getText().toString().trim());
        params.addQueryStringParameter("user_weight",tvRegisterInfoWeightValue.getText().toString().trim());
        params.addQueryStringParameter("user_status","0");
        Log.i("xiaopeng", "url----:" + url + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpPost<GsonObjModel<String>>(url,this,params){
            @Override
            public void onParseSuccess(GsonObjModel<String> response, String result) {
                super.onParseSuccess(response, result);
                Log.i("xiaopeng--success", ""+result);
                if (200==response.code){
                    finish();
                }else {
                    ToastUtil.show(getApplicationContext(),response.msg);
                }

            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                super.onParseError(response, result);
                Log.i("xiaopeng--error", ""+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };

    }


}
