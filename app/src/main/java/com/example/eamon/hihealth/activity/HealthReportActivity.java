package com.example.eamon.hihealth.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.BodyValueMessage;
import com.example.eamon.hihealth.gson.ReportMessage;
import com.example.eamon.hihealth.util.BaseActivity;
import com.example.eamon.hihealth.util.DpOrPxUtils;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.google.gson.Gson;
import com.littlejie.circleprogress.CircleProgress;

import java.io.IOException;

import okhttp3.Request;

public class HealthReportActivity extends BaseActivity {

    private ProgressDialog progressDialog;

    private CircleProgress mCircleProgress;

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private Gson gson = new Gson();

    private TextView bmiValue;
    private TextView bmiisValue;
    private TextView bmrValue;
    private TextView standarweightValue;
    private TextView weightRangeValue;
    private TextView sValue;
    private TextView aValue;
    private TextView bValue;
    private TextView cValue;
    private TextView dValue;

    private BodyValueMessage bodydataValue;
    private UserInfo userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_report);
        bmiValue = findViewById(R.id.bmi_value);
        bmiisValue = findViewById(R.id.isBmi_value);
        bmrValue = findViewById(R.id.bmr_value);
        standarweightValue =  findViewById(R.id.standardWeight_value);
        weightRangeValue = findViewById(R.id.standardWeightRange_value);
        mCircleProgress =  findViewById(R.id.circle_progress_bar1);
        sValue = findViewById(R.id.s_value);
        aValue = findViewById(R.id.a_value);
        bValue = findViewById(R.id.b_value);
        cValue = findViewById(R.id.c_value);
        dValue = findViewById(R.id.d_value);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        userInfo = gson.fromJson(pref.getString("userinfo", ""), UserInfo.class);
        postReportValue(userInfo);
//        progressDialog = ProgressDialog.show(HealthReportActivity.this, "健康报告", "加载中，请稍后...");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                postReportValue(userInfo);
//            }
//        }).start();



//        ButterKnife.bind(this);
//        reportInit();

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back_view:
                Intent intent = new Intent(HealthReportActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void postReportValue(UserInfo userInfo) {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/users/report", userInfo, new HttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(HealthReportActivity.this, "连接出错" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void requestSuccess(String result) throws Exception {

                ReportMessage reportMessage = gson.fromJson(result, ReportMessage.class);
                if (("success").equals(reportMessage.responseMessage.result)) {
                    int report = Integer.parseInt(reportMessage.report);
                    Toast.makeText(HealthReportActivity.this, reportMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                    mCircleProgress.setValue(report);
                    if (report >= 95 && report <= 100) {
                        sValue.getLayoutParams().width = DpOrPxUtils.dip2px(HealthReportActivity.this, 40);
                        sValue.getLayoutParams().height = DpOrPxUtils.dip2px(HealthReportActivity.this, 40);
                    } else if (report >= 86 && report <= 94) {
                        aValue.getLayoutParams().width = DpOrPxUtils.dip2px(HealthReportActivity.this, 40);
                        aValue.getLayoutParams().height = DpOrPxUtils.dip2px(HealthReportActivity.this, 40);
                    } else if (report >= 76 && report <= 85) {
                        bValue.getLayoutParams().width = DpOrPxUtils.dip2px(HealthReportActivity.this, 40);
                        bValue.getLayoutParams().height = DpOrPxUtils.dip2px(HealthReportActivity.this, 40);
                    } else if (report >= 61 && report <= 75) {
                        cValue.getLayoutParams().width = DpOrPxUtils.dip2px(HealthReportActivity.this, 40);
                        cValue.getLayoutParams().height = DpOrPxUtils.dip2px(HealthReportActivity.this, 40);
                    } else if (report >= 0 && report <= 60) {
                        dValue.getLayoutParams().width = DpOrPxUtils.dip2px(HealthReportActivity.this, 40);
                        dValue.getLayoutParams().height = DpOrPxUtils.dip2px(HealthReportActivity.this, 40);
                    }
                    bmiValue.setText(reportMessage.bodyDataValue.bmi);
                    if (Double.parseDouble(reportMessage.bodyDataValue.bmi) < 18.5) {
                        bmiisValue.setText("(轻体重)");
                    } else if (Double.parseDouble(reportMessage.bodyDataValue.bmi) >= 18.5 &&
                            Double.parseDouble(reportMessage.bodyDataValue.bmi) < 24 ) {
                        bmiisValue.setText("(标准)");
                    } else if (Double.parseDouble(reportMessage.bodyDataValue.bmi) >= 24 &&
                            Double.parseDouble(reportMessage.bodyDataValue.bmi) < 28) {
                        bmiisValue.setText("(超重)");
                    } else if (Double.parseDouble(reportMessage.bodyDataValue.bmi) >= 28) {
                        bmiisValue.setText("(肥胖)");
                    }
                    bmrValue.setText(reportMessage.bodyDataValue.bmr);
                    standarweightValue.setText(reportMessage.bodyDataValue.weight);
                    weightRangeValue.setText(reportMessage.bodyDataValue.weightRange);
//                    progressDialog.dismiss();
                }
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(HealthReportActivity.this, MainActivity.class);
            startActivity(intent);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
