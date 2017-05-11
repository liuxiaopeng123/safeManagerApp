package com.xiaopeng.safemanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.xiaopeng.safemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BackTableActivity extends Activity {

    @BindView(R.id.activity_back_table_manager1)
    RelativeLayout activityBackTableManager1;
    @BindView(R.id.activity_back_table_manager2)
    RelativeLayout activityBackTableManager2;
    @BindView(R.id.activity_back_table_manager3)
    RelativeLayout activityBackTableManager3;
    @BindView(R.id.activity_back_table_manager14)
    RelativeLayout activityBackTableManager14;
    @BindView(R.id.activity_back_table_manager15)
    RelativeLayout activityBackTableManager15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_table);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.activity_back_table_manager1, R.id.activity_back_table_manager2, R.id.activity_back_table_manager3, R.id.activity_back_table_manager14, R.id.activity_back_table_manager15})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_back_table_manager1:
                startActivity(new Intent(BackTableActivity.this,UsersManagerActivity.class));
                break;
            case R.id.activity_back_table_manager2:
                break;
            case R.id.activity_back_table_manager3:
                break;
            case R.id.activity_back_table_manager14:
                break;
            case R.id.activity_back_table_manager15:
                break;
        }
    }
}
