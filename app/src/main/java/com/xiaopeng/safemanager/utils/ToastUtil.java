package com.xiaopeng.safemanager.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author xiaopeng
 * 吐司工具
 * @date 2016/12/23
 */

public class ToastUtil {
    //短时间吐司
    public static void show(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }
}
