package com.xiaopeng.safemanager.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.xiaopeng.safemanager.R;
import com.xiaopeng.safemanager.bean.FoodDataBean;
import com.xiaopeng.safemanager.bean.GsonObjModel;
import com.xiaopeng.safemanager.config.Config;
import com.xiaopeng.safemanager.utils.HttpPost;
import com.xiaopeng.safemanager.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupeng on 2017/3/5.
 */

public class FragmentFood extends BaseFragment {
    ListView listView;
    View view;
    MyAdapter myAdapter ;
    List<FoodDataBean> foodDataBeanList = new ArrayList<>();
    @Override
    public View setupView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(context, R.layout.fragment_food, null);
            ViewUtils.inject(this, view);
        }
        listView= (ListView) view.findViewById(R.id.listView);
        return view;
    }

    @Override
    public void initData() {
        myAdapter=new MyAdapter();
        listView.setAdapter(myAdapter);
        queryFoodDatas();
    }

    //查询饮食信息
    private void queryFoodDatas() {
        String url = Config.baseUrl+"safe/queryFoodData";
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("foodType", "主食");
        params.addQueryStringParameter("foodStatus", "0");
        Log.i("xiaopeng", "url----:" + url + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpPost<GsonObjModel<List<FoodDataBean>>>(url,context,params){
            @Override
            public void onParseSuccess(GsonObjModel<List<FoodDataBean>> response, String result) {
                super.onParseSuccess(response, result);
                Log.i("xiaopeng--success", ""+result+response.data.size());
                if (response.code==200){
                    foodDataBeanList=response.data;
                    myAdapter.notifyDataSetChanged();
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

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return foodDataBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_food, null);
            }
            ImageView foodImage= (ImageView) convertView.findViewById(R.id.item_food_image);
            TextView foodName = (TextView) convertView.findViewById(R.id.item_food_name);
            TextView foodEnergy = (TextView) convertView.findViewById(R.id.item_food_energy);
            if (position%3==0){
                foodImage.setBackgroundColor(Color.parseColor("#FF4081"));
            }else if (position%3==1){
                foodImage.setBackgroundColor(Color.parseColor("#dc143c"));
            }else {
                foodImage.setBackgroundColor(Color.parseColor("#4169e1"));
            }
            foodName.setText(foodDataBeanList.get(position).getFoodName());
            foodEnergy.setText(foodDataBeanList.get(position).getFoodEnergy()+" 千焦/ "+foodDataBeanList.get(position).getFoodCount()+" 克");
            return convertView;
        }
    }
}
