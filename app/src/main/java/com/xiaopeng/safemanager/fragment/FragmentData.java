package com.xiaopeng.safemanager.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.xiaopeng.safemanager.R;
import com.xiaopeng.safemanager.bean.FoodDataBean;
import com.xiaopeng.safemanager.bean.GsonObjModel;
import com.xiaopeng.safemanager.bean.SportDataBean;
import com.xiaopeng.safemanager.config.Config;
import com.xiaopeng.safemanager.utils.HttpPost;
import com.xiaopeng.safemanager.utils.SharedPreferencesUtil;
import com.xiaopeng.safemanager.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by liupeng on 2017/3/5.
 */

public class FragmentData extends BaseFragment {
    View view;
    private RadioGroup radioGroup;
    private ColumnChartView columnChartView;
    private ColumnChartData columnData;
    public String[] months = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    @Override
    public View setupView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(context, R.layout.fragment_data, null);
            radioGroup= (RadioGroup) view.findViewById(R.id.activity_sale_order_finish_form_rg);
            columnChartView = (ColumnChartView) view.findViewById(R.id.activity_sale_order_finish_column_chart_view);
            ViewUtils.inject(this, view);
        }
        return view;
    }
    List<SportDataBean> sportDatas;
    @Override
    public void initData() {
        querySportDatas();

//        queryWeather();
//        generateColumnData();
        setListener();
    }
    private void setListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rg_sale_order_finish_form_activity_week:
                        months = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
//                        generateColumnData();
                        columnChartView.setOnValueTouchListener(new ColumnChartOnValueSelectListener() {
                            @Override
                            public void onValueSelected(int i, int i1, SubcolumnValue subcolumnValue) {
                            }

                            @Override
                            public void onValueDeselected() {

                            }
                        });
                        break;
                    case R.id.rg_sale_order_finish_form_activity_mouth:
                        months = new String[]{"第一周", "第二周", "第三周", "第四周"};
//                        generateColumnData();
                        columnChartView.setOnValueTouchListener(new ColumnChartOnValueSelectListener() {
                            @Override
                            public void onValueSelected(int i, int i1, SubcolumnValue subcolumnValue) {
                            }

                            @Override
                            public void onValueDeselected() {

                            }
                        });
                        break;
                    case R.id.rg_sale_order_finish_form_activity_year:
                        months = new String[]{"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
//                        generateColumnData();
                        columnChartView.setOnValueTouchListener(new ColumnChartOnValueSelectListener() {
                            @Override
                            public void onValueSelected(int i, int i1, SubcolumnValue subcolumnValue) {
                            }
                            @Override
                            public void onValueDeselected() {

                            }
                        });
                        break;
                }

            }
        });
//        radioGroup.check(R.id.rg_sale_order_finish_form_activity_week);
    }


    private void generateColumnData() {
        int numSubcolumns = 1;//设置每个柱状图显示的颜色数量(每个柱状图显示多少块
//        月份集合的长度
        int numColumns = months.length;//柱状图的数量
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < (sportDatas.size()>7?7:sportDatas.size()); ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                float a= Float.parseFloat(sportDatas.get(i).getStep());
                values.add(new SubcolumnValue(a, ChartUtils
                        .pickColor()).setLabel((int)a+"步"));//第一个值是数值(值>0 方向朝上，值<0，方向朝下)，第二个值是颜色
            }
            axisValues.add(new AxisValue(i).setLabel(sportDatas.get(i).getDate().substring(8,10)));
//            柱状图实例     是否点击柱状图才显示数据
            columns.add(new Column(values).setHasLabelsOnlyForSelected(false).setHasLabels(true));
        }
        columnData = new ColumnChartData(columns);
//            setname方法设置名称
        columnData.setAxisXBottom(new Axis(axisValues).setName("日期").setHasLines(true));
        columnData.setAxisYLeft(new Axis().setName("步数").setHasLines(true).setMaxLabelChars(2));

        columnChartView.setColumnChartData(columnData);

        // Set value touch listener that will trigger changes for chartTop.
        columnChartView.setOnValueTouchListener(new ValueTouchListener());

        // Set selection mode to keep selected month column highlighted.
        columnChartView.setValueSelectionEnabled(true);

        columnChartView.setZoomType(ZoomType.HORIZONTAL);

        // columnChartView.setOnClickListener(new View.OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // SelectedValue sv = columnChartView.getSelectedValue();
        // if (!sv.isSet()) {
        // generateInitialLineData();
        // }
        //
        // }
        // });

    }
    private class ValueTouchListener implements ColumnChartOnValueSelectListener {
        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
//            generateLineData(value.getColor(), 100);
        }

        @Override
        public void onValueDeselected() {
//            generateLineData(ChartUtils.COLOR_GREEN, 0);

        }
    }



    //查询运动信息
    private void querySportDatas() {
        String url = Config.baseUrl+"safe/querySportData";
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("username", SharedPreferencesUtil.getString("username","admin"));
        Log.i("xiaopeng", "url----:" + url + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpPost<GsonObjModel<List<SportDataBean>>>(url,context,params){
            @Override
            public void onParseSuccess(GsonObjModel<List<SportDataBean>> response, String result) {
                super.onParseSuccess(response, result);
                Log.i("xiaopeng--success", ""+result);
                if (response.code==200){
                    sportDatas=response.data.subList(response.data.size()-7,response.data.size());
                    months = new String[]{"1", "2", "3", "4", "5", "6", "7"};
                    generateColumnData();
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

    //查询天气
    private void queryWeather() {
        String url = "http://apis.haoservice.com/weather";
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("key", "8c60fea1293f49868d2c3f117b7c819c");
        params.addQueryStringParameter("cityname","长春");
        Log.i("xiaopeng", "url----:" + url + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpPost<GsonObjModel<List<SportDataBean>>>(url,context,params){
            @Override
            public void onParseSuccess(GsonObjModel<List<SportDataBean>> response, String result) {
                super.onParseSuccess(response, result);
                Log.i("xiaopeng--wheather", ""+result);
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                super.onParseError(response, result);
                Log.i("xiaopeng--wheather", ""+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
                Log.i("xiaopeng--wheather", ""+s+e);
            }
        };
    }


}
