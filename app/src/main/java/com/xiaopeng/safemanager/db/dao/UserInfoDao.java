package com.xiaopeng.safemanager.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xiaopeng.safemanager.bean.UserInfoBean;
import com.xiaopeng.safemanager.db.UserInfoDBOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupeng on 2017/4/14.
 */

public class UserInfoDao {
    private UserInfoDBOpenHelper helper;

    public UserInfoDao(Context context) {
        helper = new UserInfoDBOpenHelper(context);
    }

    /**
     * 添加用户信息
     * @param user_name
     * @param user_password
     * @param user_height
     * @param user_weight
     * @return
     */
    public boolean add(String user_name,String user_password,String user_height,String user_weight,String user_sex){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_name",user_name);
        values.put("user_password",user_password);
        values.put("user_height",user_height);
        values.put("user_sex",user_sex);
        values.put("user_weight",user_weight);
        long id = db.insert("user_info", null, values);
        db.close();// 用完之后一定要将数据库关闭 避免不必要的错误
        if (id!=-1){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 删除用户信息
     */
    public boolean delete(String user_name){
        SQLiteDatabase db = helper.getWritableDatabase();
        int id = db.delete("user_info", "user_name=?", new String[]{user_name});
        db.close();
        return id==1?true:false;
    }

    /**
     * 更新用户信息
     */
    public boolean update(String user_name,String user_height,String user_weight,String user_password,String user_sex){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_name",user_name);
        values.put("user_password",user_password);
        values.put("user_sex",user_sex);
        values.put("user_height",user_height);
        values.put("user_weight",user_weight);
        int id = db.update("user_info", values, "user_name=?", new String[]{user_name});
        db.close();
        return  id==1?true:false;
    }

    /**
     * 根据user_name查询用户信息
     */
    public UserInfoBean queryByUserName(String user_name){
        UserInfoBean userInfoBean=null;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("user_info", null, "user_name=?",
                new String[] { user_name }, null, null, null);
        if (cursor.moveToNext()){
            userInfoBean=new UserInfoBean();
            userInfoBean.setUsername(cursor.getString(cursor.getColumnIndex("user_name")));
            userInfoBean.setUserpwd(cursor.getString(cursor.getColumnIndex("user_password")));
            userInfoBean.setUsersex(cursor.getString(cursor.getColumnIndex("user_sex")));
            userInfoBean.setUserheight(cursor.getString(cursor.getColumnIndex("user_height")));
            userInfoBean.setUserweight(cursor.getString(cursor.getColumnIndex("user_weight")));
            return userInfoBean;
        }
        db.close();
        cursor.close();
        return userInfoBean;
    }

    /**
     * 查询所有用户的个人信息
     * @return
     */
    public List<UserInfoBean> queryAllUserInfo(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("user_info", null, null, null, null, null, "user_id desc");
        List<UserInfoBean> userInfoBeanList = new ArrayList<>();
        while (cursor.moveToNext()){
            UserInfoBean userInfoBean = new UserInfoBean();
            userInfoBean.setUsername(cursor.getString(cursor.getColumnIndex("user_name")));
            userInfoBean.setUserpwd(cursor.getString(cursor.getColumnIndex("user_password")));
            userInfoBean.setUserheight(cursor.getString(cursor.getColumnIndex("user_height")));
            userInfoBean.setUserweight(cursor.getString(cursor.getColumnIndex("user_weight")));
            userInfoBean.setUsersex(cursor.getString(cursor.getColumnIndex("user_sex")));
            userInfoBeanList.add(userInfoBean);
        }
        cursor.close();
        db.close();
        return userInfoBeanList;
    }
}
