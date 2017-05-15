package com.xiaopeng.safemanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.xiaopeng.safemanager.R;
import com.xiaopeng.safemanager.activity.LoginActivity;
import com.xiaopeng.safemanager.activity.MainActivity;
import com.xiaopeng.safemanager.activity.UpdateHealthDataActivity;
import com.xiaopeng.safemanager.bean.GsonObjModel;
import com.xiaopeng.safemanager.bean.HealthDataBean;
import com.xiaopeng.safemanager.bean.UserInfoBean;
import com.xiaopeng.safemanager.config.Config;
import com.xiaopeng.safemanager.utils.HttpPost;
import com.xiaopeng.safemanager.utils.SharedPreferencesUtil;
import com.xiaopeng.safemanager.utils.ToastUtil;

/**
 * Created by liupeng on 2017/3/5.
 */

public class FragmentHealth extends BaseFragment {
    View view;
    TextView healthCode,healthStardardNumber,userSsy,userSzy,userXl;
    Button updateButton;
    @Override
    public View setupView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(context, R.layout.fragment_health, null);
            ViewUtils.inject(this, view);
        }
        return view;
    }

    @Override
    public void initData() {
        queryHealthData();
        healthCode= (TextView) view.findViewById(R.id.fragment_health_code);
        userSsy = (TextView) view.findViewById(R.id.fragment_health_ssy);
        userSzy = (TextView) view.findViewById(R.id.fragment_health_szy);
        userXl = (TextView) view.findViewById(R.id.fragment_health_xl);
        healthStardardNumber= (TextView) view.findViewById(R.id.fragment_health_standard_number);
        updateButton= (Button) view.findViewById(R.id.fragment_health_update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(context,UpdateHealthDataActivity.class),222);
            }
        });
    }

    //查询个人健康数据
    private void queryHealthData() {
        String url = Config.baseUrl+"safe/queryHealthData";
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("username",SharedPreferencesUtil.getString("username","admin"));
        new HttpPost<GsonObjModel<HealthDataBean>>(url,context,params){
            @Override
            public void onParseSuccess(GsonObjModel<HealthDataBean> response, String result) {
                super.onParseSuccess(response, result);
                Log.i("xiaopeng--success", ""+result);
                if (200==response.code){
                    healthCode.setText(response.data.getUserHealthNumber());
                    userSsy.setText(response.data.getUserSsy()+" Pa");
                    userSzy.setText(response.data.getUserSzy()+" Pa");
                    userXl.setText(response.data.getUserXl()+" 次/分");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==222){
            queryHealthData();
        }
    }
}
