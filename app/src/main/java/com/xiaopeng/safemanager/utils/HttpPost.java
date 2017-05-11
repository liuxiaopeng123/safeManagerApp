package com.xiaopeng.safemanager.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xiaopeng.safemanager.bean.GsonObjModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;

/**
 * @author xiaopeng
 * @date 2016/12/27
 * <p>
 * POST 网络封装的xUtils 方法
 */

public class HttpPost<T> extends RequestCallBack<String> {

    protected HttpUtils httpUtils = new HttpUtils();

    protected String mUrl;

    protected RequestParams mParams = null;

    protected Context mContext;

    public HttpPost(String URL, Context context) {
        mUrl = URL;
        mContext = context;
        onInit();
    }

    public HttpPost(String URL, Context context, RequestParams params) {
        mUrl = URL;
        mContext = context;
        mParams = params;
        onInit();
    }

    /**
     * 初始化调用的方法
     */
    public void onInit() {
        httpUtils.send(HttpRequest.HttpMethod.POST, mUrl, mParams, this);
    }


    @Override
    public void onSuccess(ResponseInfo<String> responseInfo) {

        Gson gson = new Gson();
        String responseResult = responseInfo.result;
        try {
            ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
            T mResponse = gson.fromJson(responseResult, parameterizedType.getActualTypeArguments()[0]);
            onParseSuccess(mResponse, responseResult);
        } catch (JsonSyntaxException e) {
            GsonObjModel<String> response = null;

            try {
                response = gson.fromJson(responseResult, new TypeToken<GsonObjModel<String>>() {
                }.getType());
            } catch (Exception e1) {
                response = new GsonObjModel<>();
                response.code = 500;
                response.msg = "数据转换错误" + e1.getMessage();
            }
            onParseError(response, responseResult);
        }

    }

    /**
     * 解析出错的方法
     */
    public void onParseError(GsonObjModel<String> response, String result) {
        if ("server error".equals(result)) {
            Log.i("xiaopeng parseError", "服务失败,请重新加载" + result + "22222" + response);
            return;
        } else {
            Log.i("xiaopeng parseError", "111111" + result + "22222" + response);
        }
    }

    /**
     * 解析成功的方法
     */
    public void onParseSuccess(T response, String result) {

        try {
            JSONObject jsonObject = new JSONObject(result);
            String code = jsonObject.getString("code");
            String msg = jsonObject.getString("msg");
            if ("500".equals(code)) {
//                ToastUtil.show(mContext, "xiaopeng 500---" + code);
                return;
            } else if ("400".equals(code)) {
//                ToastUtil.show(mContext, "xiaopeng 400---" + code);
            }
//            ToastUtil.show(mContext, "xiaopeng 200---" + code);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFailure(HttpException e, String s) {
        ToastUtil.show(mContext, "网络不好，请稍后重试");
    }
}
