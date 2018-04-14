package com.example.eamon.hihealth.db.datainit;

import android.util.Log;

import com.example.eamon.hihealth.calculation.BodyDataCaluation;
import com.example.eamon.hihealth.calculation.HealthIndexCaluation;
import com.example.eamon.hihealth.db.BodyData;
import com.example.eamon.hihealth.db.BodyDataLog;
import com.example.eamon.hihealth.db.HealthReport;
import com.example.eamon.hihealth.db.UserInfo;

import org.litepal.crud.DataSupport;

import java.util.Date;
import java.util.List;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/4/14.
 */

public class BodyDataLogInit {

    private static final String TAG = "BodyDataLogInit";

    /**
     * 用户注册或登录时候 初始化数据库数据
     * @param userInfo
     */
    public void BodyDataLogDefault(UserInfo userInfo) {
        BodyDataCaluation bodyDataCaluation = new BodyDataCaluation(userInfo);
        HealthIndexCaluation healthIndexCaluation = new HealthIndexCaluation();

        HealthReport healthReport = new HealthReport();
        healthReport.setUserinfo_id(userInfo);
        healthReport.setReport_Scroe(healthIndexCaluation.getHealthIndex(bodyDataCaluation));
        healthReport.setReport_Time(new Date());
        healthReport.save();

        BodyDataLog bmiDataLog = new BodyDataLog();
        bmiDataLog.setBodyDataLog_Value(Double.toString(bodyDataCaluation.getBodyMassIndex()));
        bmiDataLog.setBodyDataLog_Time(new Date());
        bmiDataLog.setUserInfo(userInfo);
        bmiDataLog.setHealthReport(healthReport);
        bmiDataLog.save();

        Log.d("TAG", "bmiDataLog is " + bmiDataLog.getBodyDataLog_Value() );
        Log.d("TAG", "bmiDataLog is " + bmiDataLog.getBodyDataLog_Time() );

        BodyDataLog bmrDataLog = new BodyDataLog();
        bmrDataLog.setBodyDataLog_Value((Double.toString(bodyDataCaluation.getBasalMetabolicRate())));
        bmrDataLog.setBodyDataLog_Time(new Date());
        bmrDataLog.setUserInfo(userInfo);
        bmrDataLog.setHealthReport(healthReport);
        bmrDataLog.save();

        Log.d("TAG", "bmrDataLog is " + bmrDataLog.getBodyDataLog_Value() );
        Log.d("TAG", "bmrDataLog is " + bmrDataLog.getBodyDataLog_Time() );

        BodyDataLog standardWeight = new BodyDataLog();
        standardWeight.setBodyDataLog_Value(Double.toString(bodyDataCaluation.getStandardweight()));
        standardWeight.setBodyDataLog_Time(new Date());
        standardWeight.setUserInfo(userInfo);
        standardWeight.setHealthReport(healthReport);
        standardWeight.save();

        Log.d("TAG", "standardWeight is " + standardWeight.getBodyDataLog_Value() );
        Log.d("TAG", "standardWeight is " + standardWeight.getBodyDataLog_Time() );

        BodyDataLog weightRange = new BodyDataLog();
        weightRange.setBodyDataLog_Value(Double.toString(bodyDataCaluation.getWeightRange("lower"))
            + "~" + Double.toString(bodyDataCaluation.getWeightRange("high")));
        weightRange.setBodyDataLog_Time(new Date());
        weightRange.setUserInfo(userInfo);
        weightRange.setHealthReport(healthReport);
        weightRange.save();

        Log.d("TAG", "weightRange is " + weightRange.getBodyDataLog_Value() );
        Log.d("TAG", "weightRange is " + weightRange.getBodyDataLog_Time() );



        List<BodyData> bmi = DataSupport
                .where("bodyData_Name = ?", "BMI").find(BodyData.class);
        for ( BodyData bmiBodyData : bmi) {
            bmiBodyData.getBodyDataLogList().add(bmiDataLog);
            bmiBodyData.save();
        }

        List<BodyData> bmr = DataSupport
                .where("bodyData_Name = ?", "BMR").find(BodyData.class);
        for ( BodyData bmrBodyData : bmr) {
            bmrBodyData.getBodyDataLogList().add(bmrDataLog);
            bmrBodyData.save();
        }

        List<BodyData> staWeight = DataSupport
                .where("bodyData_Name = ?", "标准体重").find(BodyData.class);
        for ( BodyData staWeightBodyData : staWeight) {
            staWeightBodyData.getBodyDataLogList().add(standardWeight);
            staWeightBodyData.save();
        }

        List<BodyData> weiRange = DataSupport
                .where("bodyData_Name = ?", "健康体重范围").find(BodyData.class);
        for ( BodyData weiRangeBodyData : weiRange) {
            weiRangeBodyData.getBodyDataLogList().add(weightRange);
            weiRangeBodyData.save();
        }

    }
}
