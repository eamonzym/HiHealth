package com.example.eamon.hihealth.db.datainit;

import android.util.Log;

import com.example.eamon.hihealth.db.BodyData;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/4/14.
 */

public class BodyDataInit {

    private static final String TAG = "BodyDataInit";

    /**
     * 初始化Bodydata表的数据
     */
    public void BodyDataDefault(){
        BodyData bmi = new BodyData("BMI", "身体质量指数");
        bmi.save();

        Log.d("TAG", "bmi is " + bmi.getBodyData_Name() );

        BodyData bmr = new BodyData("BMR", "基础代谢率");
        bmr.save();

        Log.d("TAG", "bmr is " + bmr.getBodyData_Name() );

        BodyData standardWeight = new BodyData("标准体重", "标准体重");
        standardWeight.save();

        Log.d("TAG", "standardWeight is " +standardWeight.getBodyData_Name() );

        BodyData weightRange = new BodyData("健康体重范围", "健康体重范围");
        weightRange.save();

        Log.d("TAG", "weightRange is " +weightRange.getBodyData_Name() );
    }
}
