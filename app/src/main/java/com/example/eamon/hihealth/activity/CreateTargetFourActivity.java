package com.example.eamon.hihealth.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.Target;
import com.example.eamon.hihealth.gson.TargetInfoMessage;
import com.example.eamon.hihealth.util.BaseActivity;
import com.example.eamon.hihealth.util.DateFormatUtil;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.example.eamon.hihealth.util.RulerView;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Request;

public class CreateTargetFourActivity extends BaseActivity implements RulerView.OnValueChangeListener{

    private static final String TAG = "CreateTargetFourActivity";

    private Target target;

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private TextView dayLossValue;
    private TextView lossweightValue;
    private TextView targetFinishTime;
    private TextView targetDate;
    private TextView targetStartTime;
    private Double week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_target_four);
        ((RulerView) findViewById(R.id.ruler)).setOnValueChangeListener(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        dayLossValue = (TextView) findViewById(R.id.dayloss_title);
        targetStartTime = (TextView) findViewById(R.id.starttime_value);
        lossweightValue = (TextView) findViewById(R.id.weight_value);
        targetFinishTime = (TextView) findViewById(R.id.finishtime_value);
        targetDate = (TextView) findViewById(R.id.target_date);
        target = (Target) getIntent().getSerializableExtra("targetinfo_data");
        String[] weightStr = lossweightValue.getText().toString().split("[公斤]");
        week = (target.getInitialweight().doubleValue() - target.getTargetweight().doubleValue()) /
                Double.parseDouble(weightStr[0]);
        if (week > 0) {
            dayLossValue.setText("每周减重");
        } else if (week < 0) {
            dayLossValue.setText("每周增重");
        }
        targetStartTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        targetFinishTime.setText(String.valueOf(Math.abs(Math.round(week))) + "周");
        try {
            targetDate.setText(DateFormatUtil.getDateStr(targetStartTime.getText().toString(),Math.round(week * 7)));
        }catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back_view:
                finish();
                break;
            case R.id.next_btn:
                postStartTarget();
                break;
        }
    }


    private void postStartTarget() {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/target/start", setTargetData(),
                new HttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(CreateTargetFourActivity.this, "连接出错" , Toast.LENGTH_LONG).show();

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                TargetInfoMessage targetInfoMessage = gson.fromJson(result, TargetInfoMessage.class);
                if ("success".equals(targetInfoMessage.responseMessage.result)){
                    editor = pref.edit();
                    editor.remove("target");
                    editor.putString("target",gson.toJson(target));
                    editor.apply();
                    Toast.makeText(CreateTargetFourActivity.this, targetInfoMessage.responseMessage.message,
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CreateTargetFourActivity.this,TargetActivity.class);
                    startActivity(intent);
                } else if ("fail".equals(targetInfoMessage.responseMessage.result)) {
                    Toast.makeText(CreateTargetFourActivity.this, targetInfoMessage.responseMessage.message,
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    private Target setTargetData() {
        String[] weightStr = lossweightValue.getText().toString().split("[公斤]");
        try{
            target.setTargetstarttime(DateFormatUtil.stringToDate(targetStartTime.getText().toString()));
            target.setTargetfinishtime(DateFormatUtil.stringToDate(targetDate.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        target.setDaylossweight(Double.parseDouble(weightStr[0]));
        target.setTargetstate("进行中");
        return  target;
    }

    @Override
    public void onChange(RulerView view, float value) {
        switch (view.getId()) {
            case R.id.ruler:
                lossweightValue.setText( + value + " 公斤");
                String[] weightStr = lossweightValue.getText().toString().split("[公斤]");
                week = Math.abs((target.getInitialweight().doubleValue() - target.getTargetweight().doubleValue()) /
                        Double.parseDouble(weightStr[0]));
                targetStartTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                targetFinishTime.setText(String.valueOf(Math.round(week)) + "周");
                try {
                    targetDate.setText(DateFormatUtil.getDateStr(targetStartTime.getText().toString(),Math.round(week * 7)));
                }catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
