package com.xiaopeng.safemanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.xiaopeng.safemanager.R;

public class UpdateHealthDataActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_health_data);
        findViewById(R.id.activity_update_health_data_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
