package com.xiaopeng.safemanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.xiaopeng.safemanager.R;
import com.xiaopeng.safemanager.bean.GsonObjModel;
import com.xiaopeng.safemanager.bean.HealthDataBean;
import com.xiaopeng.safemanager.config.Config;
import com.xiaopeng.safemanager.utils.HttpPost;
import com.xiaopeng.safemanager.utils.SharedPreferencesUtil;
import com.xiaopeng.safemanager.utils.ToastUtil;

public class UpdateHealthDataActivity extends Activity {

    EditText ssy,szy,xl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_health_data);
        ssy= (EditText) findViewById(R.id.activity_update_health_ssy);
        szy= (EditText) findViewById(R.id.activity_update_health_szy);
        xl= (EditText) findViewById(R.id.activity_update_health_xl);
        findViewById(R.id.activity_update_health_data_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.activity_update_health_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(ssy.getText().toString().trim())){
                    ToastUtil.show(getApplicationContext(),"收缩压不能为空！");
                }else if ("".equals(szy.getText().toString().trim())){
                    ToastUtil.show(getApplicationContext(),"舒张压不能为空！");
                }else if ("".equals(xl.getText().toString().trim())){
                    ToastUtil.show(getApplicationContext(),"心率不能为空！");
                }else {
                    updateHealthData();
                }
            }
        });
    }

    //网络接口
    private void updateHealthData() {
        String url = Config.baseUrl+"safe/updateHealthData";
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("username", SharedPreferencesUtil.getString("username","admin"));
        params.addQueryStringParameter("userhealthnumber", "99");
        params.addQueryStringParameter("userssy", ssy.getText().toString().trim());
        params.addQueryStringParameter("userszy", szy.getText().toString().trim());
        params.addQueryStringParameter("userxl", xl.getText().toString().trim());
        new HttpPost<GsonObjModel<HealthDataBean>>(url,this,params){
            @Override
            public void onParseSuccess(GsonObjModel<HealthDataBean> response, String result) {
                super.onParseSuccess(response, result);
                Log.i("xiaopeng--success", ""+result);
                if (200==response.code){
                    ToastUtil.show(getApplicationContext(),"修改成功！");
                    finish();
                }else {
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
