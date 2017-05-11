package com.xiaopeng.safemanager.fragment;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.xiaopeng.safemanager.R;
import com.xiaopeng.safemanager.activity.SportDataActivity;
import com.xiaopeng.safemanager.bean.GsonObjModel;
import com.xiaopeng.safemanager.bean.SportDataBean;
import com.xiaopeng.safemanager.config.Config;
import com.xiaopeng.safemanager.utils.HttpPost;
import com.xiaopeng.safemanager.utils.SharedPreferencesUtil;
import com.xiaopeng.safemanager.utils.ToastUtil;
import com.xiaopeng.safemanager.views.cardswipelayout.CircleProgressView;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by liupeng on 2017/3/5.
 */

public class FragmentSport extends BaseFragment implements SensorEventListener {
    View view;
    public float stepTotalCount = 0;
    public float mDetector = 0;
    @BindView(R.id.circleProgressView)
    CircleProgressView circleProgressView;
    Unbinder unbinder;

    //5000步行走3500米
    //一步消耗0.04大卡 5000*0.04=200大卡
    float distance,energy;
    String month;
    String today;
    Date date;
    @BindView(R.id.fragment_record_sport_data)
    TextView fragmentRecordSportData;
    @BindView(R.id.setting_activity_title_bar)
    RelativeLayout settingActivityTitleBar;
    @BindView(R.id.fragment_sport_data_distance)
    TextView fragmentSportDataDistance;
    @BindView(R.id.fragment_sport_data_energy)
    TextView fragmentSportDataEnergy;

    private boolean flag_destory_fragment = false;
    SimpleDateFormat format;
    @Override
    public View setupView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(context, R.layout.fragment_sport, null);
            ViewUtils.inject(this, view);
        }
        return view;
    }

    /**
     * 业务的数据
     */
    @Override
    public void initData() {
        System.currentTimeMillis();
//        DateFormat format = new DateFormat("yyyyy");?
        format = new SimpleDateFormat("yyyy年MM月dd日");
        date = new Date(System.currentTimeMillis());
        month = format.format(date).substring(5, 7);
        today = format.format(date).substring(8, 10);

        if (today.equals(SharedPreferencesUtil.getString("today", "")) && month.equals(SharedPreferencesUtil.getString("month", ""))) {
            mDetector = SharedPreferencesUtil.getFloat("mDetector", 0);
            circleProgressView.setProgress((int) mDetector);
            String distanceText = ""+((int)(mDetector*0.7))/1000+"."+(((int) (mDetector*0.7))%1000)/100;
            fragmentSportDataDistance.setText(distanceText+"公里");
            fragmentSportDataEnergy.setText(""+(int) (mDetector*0.04)+"大卡");

        } else {
            addSportData();


        }
        SensorManager mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        //系统计步累加值
        Sensor mStepCount = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        //单次有效计步
        Sensor mStepDetector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        mSensorManager.registerListener(this, mStepCount, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, mStepDetector, SensorManager.SENSOR_DELAY_FASTEST);

    }

    private void addSportData() {
        mDetector = SharedPreferencesUtil.getFloat("mDetector", 0);
        String url = Config.baseUrl+"safe/addSportData";
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("username", SharedPreferencesUtil.getString("username","admin"));
        params.addQueryStringParameter("date", format.format(date));
        params.addQueryStringParameter("step", ""+(int)mDetector);
        params.addQueryStringParameter("energy", ""+(int) (mDetector*0.04));
        params.addQueryStringParameter("distance", ""+((int)(mDetector*0.7))/1000+"."+(((int) (mDetector*0.7))%1000)/100);
        Log.i("xiaopeng", "url----:" + url + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpPost<GsonObjModel<String>>(url,context,params){
            @Override
            public void onParseSuccess(GsonObjModel<String> response, String result) {
                super.onParseSuccess(response, result);
                Log.i("xiaopeng--success", ""+result);
                if (response.code==200){
                    mDetector = 0;
                    circleProgressView.setProgress((int) mDetector);
                    String distanceText = ""+((int)(mDetector*0.7))/1000+"."+(((int) (mDetector*0.7))%1000)/100;
                    fragmentSportDataDistance.setText(distanceText+"公里");
                    fragmentSportDataEnergy.setText(""+(int) (mDetector*0.04)+"大卡");
                }
                if (response.code==400){
                    ToastUtil.show(context,response.msg);
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
                Log.i("xiaopeng--success", ""+s+e);
            }
        };
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            if (sensorEvent.values[0] == 1.0) {
                mDetector++;
            }
            Log.i("xiaopeng-------", "历史总步数: " + sensorEvent.values[0] + "步");
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            if (sensorEvent.values[0] == 1.0) {
                mDetector++;
                //event.values[0]一次有效计步数据
                if (flag_destory_fragment) {

                } else {
                    String distanceText = ""+((int)(mDetector*0.7))/1000+"."+(((int) (mDetector*0.7))%1000)/100;
                   fragmentSportDataDistance.setText(distanceText+"公里");
                   fragmentSportDataEnergy.setText(""+(int) (mDetector*0.04)+"大卡");
                    circleProgressView.setProgress((int) mDetector);
                }
                SharedPreferencesUtil.putFloat("mDetector", mDetector);
                SharedPreferencesUtil.putString("month", month);
                SharedPreferencesUtil.putString("today", today);
                Log.i("xiaopeng-------", month + "月" + today + "日 一共走了" + mDetector + "步");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag_destory_fragment = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        flag_destory_fragment = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        flag_destory_fragment = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        flag_destory_fragment = false;
    }

    @OnClick(R.id.fragment_record_sport_data)
    public void onViewClicked() {
        startActivity(new Intent(context, SportDataActivity.class));
    }
}
