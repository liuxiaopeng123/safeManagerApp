package com.xiaopeng.safemanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.xiaopeng.safemanager.R;
import com.xiaopeng.safemanager.activity.UpdateHealthDataActivity;

/**
 * Created by liupeng on 2017/3/5.
 */

public class FragmentHealth extends BaseFragment {
    View view;
    TextView healthCode,healthStardardNumber;
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
        healthCode= (TextView) view.findViewById(R.id.fragment_health_code);
        healthStardardNumber= (TextView) view.findViewById(R.id.fragment_health_standard_number);
        updateButton= (Button) view.findViewById(R.id.fragment_health_update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,UpdateHealthDataActivity.class));
            }
        });
    }
}
