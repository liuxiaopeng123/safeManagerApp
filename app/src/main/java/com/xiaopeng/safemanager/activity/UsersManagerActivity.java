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

public class UsersManagerActivity extends Activity {

    @BindView(R.id.activity_user_manager_add)
    RelativeLayout activityUserManagerAdd;
    @BindView(R.id.activity_user_manager_delete)
    RelativeLayout activityUserManagerDelete;
    @BindView(R.id.activity_user_manager_updata)
    RelativeLayout activityUserManagerUpdata;
    @BindView(R.id.activity_back_table_query)
    RelativeLayout activityBackTableQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_manager);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.activity_user_manager_add, R.id.activity_user_manager_delete, R.id.activity_back_table_query, R.id.activity_user_manager_updata})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_user_manager_add:
                startActivity(new Intent(UsersManagerActivity.this, RegisterActivity.class));
                break;
            case R.id.activity_user_manager_delete:
                break;
            case R.id.activity_back_table_query:
                startActivity(new Intent(UsersManagerActivity.this, SportDataActivity.class));
                break;
            case R.id.activity_user_manager_updata:
                break;
        }
    }
}
