package com.example.eamon.hihealth.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.BodyData;
import com.example.eamon.hihealth.db.BodyDataLog;
import com.example.eamon.hihealth.db.HealthReport;
import com.example.eamon.hihealth.db.UserInfo;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HealthReportActivity extends AppCompatActivity {

    private UserInfo userInfo;

    @Bind(R.id.report_bmi)
    TextView _bmi;

    @Bind(R.id.report_bmr)
    TextView _bmr;

    @Bind(R.id.report_standardweight)
    TextView _standardWeight;

    @Bind(R.id.report_weightrange)
    TextView _weightRange;

    @Bind(R.id.report_score)
    TextView _score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_report);
        ButterKnife.bind(this);
        reportInit();



    }

    public void reportInit() {
        userInfo = (UserInfo) getIntent().getSerializableExtra("user_data");
        List<HealthReport> healthReportList = DataSupport
                .where("userinfo_id = ?",String.valueOf(userInfo.getId()))
                .find(HealthReport.class);
        for (HealthReport healthReport : healthReportList) {
            _score.setText(String.valueOf(healthReport.getReport_Scroe()));
        }
        List<BodyDataLog> bodyDataLogList = userInfo.getBodyDataLogList();
        for (BodyDataLog bodyDataLog : bodyDataLogList) {
            List<BodyData> bodyDataList = DataSupport.select("bodyData_Name")
                    .where("id = ?", String.valueOf(bodyDataLog.getBodyData())).find(BodyData.class);
            for (BodyData bodyData : bodyDataList) {
                if ("BMI".equals(bodyData.getBodyData_Name())) {
                    _bmi.setText(bodyDataLog.getBodyDataLog_Value());

                } else if ("BMR".equals(bodyData.getBodyData_Name())) {
                    _bmr.setText(bodyDataLog.getBodyDataLog_Value());

                } else if ("标准体重".equals(bodyData.getBodyData_Name())) {
                    _standardWeight.setText(bodyDataLog.getBodyDataLog_Value());

                } else if ("健康体重范围".equals(bodyData.getBodyData_Name())) {
                    _weightRange.setText(bodyDataLog.getBodyDataLog_Value());
                }

            }
        }


    }
}
