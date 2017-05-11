package com.xiaopeng.safemanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liupeng on 2017/4/14.
 */

public class UserInfoDBOpenHelper extends SQLiteOpenHelper {
    public UserInfoDBOpenHelper(Context context) {
        super(context, "xiaopeng.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //user_id 自增长的主键
        sqLiteDatabase.execSQL("create table user_info (user_id integer primary key autoincrement ,user_name varchar(20) ,user_password varchar(20) ,user_height varchar(20) ,user_sex varchar(20) ,user_weight varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
