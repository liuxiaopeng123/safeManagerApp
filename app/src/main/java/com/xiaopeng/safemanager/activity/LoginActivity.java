package com.xiaopeng.safemanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.xiaopeng.safemanager.R;
import com.xiaopeng.safemanager.bean.GsonObjModel;
import com.xiaopeng.safemanager.bean.UserInfoBean;
import com.xiaopeng.safemanager.config.Config;
import com.xiaopeng.safemanager.db.dao.UserInfoDao;
import com.xiaopeng.safemanager.utils.HttpPost;
import com.xiaopeng.safemanager.utils.SharedPreferencesUtil;
import com.xiaopeng.safemanager.utils.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends Activity {
    private Button loginOk;
    private EditText loginUser, loginPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //测试github
        loginUser = (EditText) findViewById(R.id.activity_login_user);
        loginUser.setText(SharedPreferencesUtil.getString("theLastUserId",""));
        loginPassword = (EditText) findViewById(R.id.activity_login_password);
        loginPassword.setText(SharedPreferencesUtil.getString("theLastUserPassword",""));
        loginOk = (Button) findViewById(R.id.activity_login_ok);
        TextView toRegister = (TextView) findViewById(R.id.loginin_activity__to_register);
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        loginOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
//                if ("admain".equals(loginUser.getText().toString().trim())&&"123456".equals(loginPassword.getText().toString())){
//                    startActivity(new Intent(LoginActivity.this,BackTableActivity.class));
//                } else if(TextUtils.isEmpty(loginUser.getText().toString())){
//                    ToastUtil.show(getApplicationContext(), "账号不能为空");
//                }else if (loginPassword.getText().toString().length() < 6) {
//                    ToastUtil.show(getApplicationContext(), "密码最少6位");
//                } else if(userNameIsRegister(loginUser.getText().toString().trim())){
//                    ToastUtil.show(getApplicationContext(), "账号密码验证成功");
//                }
            }
        });

    }


    private void login() {
        String url = Config.baseUrl+"safe/QueryUserInfo";
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("user_name",loginUser.getText().toString().trim());
        params.addQueryStringParameter("user_pwd",loginPassword.getText().toString().trim());
        new HttpPost<GsonObjModel<UserInfoBean>>(url,this,params){
            @Override
            public void onParseSuccess(GsonObjModel<UserInfoBean> response, String result) {
                super.onParseSuccess(response, result);
                Log.i("xiaopeng--success", ""+result);
                if (200==response.code){
                    SharedPreferencesUtil.putString("username",response.data.getUsername());
                    SharedPreferencesUtil.putString("userpwd",response.data.getUserpwd());
                    SharedPreferencesUtil.putString("usersex",response.data.getUsersex());
                    SharedPreferencesUtil.putString("userweight",response.data.getUserweight());
                    SharedPreferencesUtil.putString("userheight",response.data.getUserheight());
                    SharedPreferencesUtil.putString("userstatus",response.data.getUserstatus());
                    SharedPreferencesUtil.putBool("userIsExit",false);
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
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

    /**
     * 登录验证接口
     */
    private boolean userNameIsRegister(String userName) {
        UserInfoDao userInfoDao = new UserInfoDao(this);
        UserInfoBean userInfoBean = userInfoDao.queryByUserName(userName);
        if (userInfoBean!=null){
            if (loginPassword.getText().toString().equals(userInfoBean.getUserpwd())){
                return true;
            }else {
                ToastUtil.show(getApplicationContext(),"密码错误！");
                return false;
            }
        }else {
            ToastUtil.show(getApplicationContext(),"账户不存在！");
            return false;
        }
    }

    /**
     * 正则表达式判断是否为手机号
     */
    public boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^1\\d{10}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
