package com.example.eamon.hihealth.util;

import android.content.Context;

/**
 * dp 和 px 互相转换工具类
 * 作者：Created by eamon
 * 时间：  on 2018/5/10.
 */

public class DpOrPxUtils {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
