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
import com.example.eamon.hihealth.db.TargetLog;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.TargetLogMessage;
import com.example.eamon.hihealth.util.BaseActivity;
import com.example.eamon.hihealth.util.DateFormatUtil;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.example.eamon.hihealth.util.RulerView;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Request;

public class TargetLogActivity extends BaseActivity implements RulerView.OnValueChangeListener {

    private TextView weightValue;

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_log);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        ((RulerView) findViewById(R.id.ruler)).setOnValueChangeListener(this);
        weightValue = (TextView) findViewById(R.id.weight_value);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back_view:
                finish();
                break;
            case R.id.next_btn:
                Target target = (Target) getIntent().getSerializableExtra("targetinfo_data");
                TargetLog targetLog = new TargetLog();
                targetLog.setTargetid(target.get_id());
                try {
                    targetLog.setTargetlogtime(DateFormatUtil.stringToDate(
                            new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
                }catch (Exception e) {
                        e.printStackTrace();
                    }
                String[] weightStr = weightValue.getText().toString().split("[公斤]");
                targetLog.setCurrentweight(Double.parseDouble(weightStr[0]));
                postCreateTargetLog(targetLog);
                break;
        }
    }

    private void postCreateTargetLog(TargetLog targetLog) {
        UserInfo userInfo = new Gson().fromJson(pref.getString("userinfo", ""), UserInfo.class);
        userInfo.setUserweight(targetLog.getCurrentweight());
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/users/updateWeight", userInfo,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(TargetLogActivity.this, "连接出错" , Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        Gson gson = new Gson();
                        TargetLogMessage targetLogMessage = gson.fromJson(result, TargetLogMessage.class);
                        if ("success".equals(targetLogMessage.responseMessage.result)) {
                            Toast.makeText(TargetLogActivity.this, targetLogMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                        }

                    }
                });

        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/target/createLog", targetLog,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(TargetLogActivity.this, "连接出错" , Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        Gson gson = new Gson();
                        TargetLogMessage targetLogMessage = gson.fromJson(result, TargetLogMessage.class);
                        if ("success".equals(targetLogMessage.responseMessage.result)) {
                            editor = pref.edit();
                            editor.putBoolean("logFragment", true);
                            editor.apply();
                            Toast.makeText(TargetLogActivity.this, targetLogMessage.responseMessage.message, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(TargetLogActivity.this, TargetActivity.class);
                            startActivity(intent);
                        }
                    }
                });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onChange(RulerView view, float value) {
        switch (view.getId()) {
            case R.id.ruler:
                weightValue.setText(+value + "公斤");
                break;
        }
    }
}
