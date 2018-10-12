package com.example.eamon.hihealth.activity;

import android.content.Intent;
import android.os.Bundle;
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
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Request;

public class CreateTargetThreeActivity extends BaseActivity implements RulerView.OnValueChangeListener {

    private static final String TAG = "CreateTargetThreeActivity";

    private Target target;

    private TextView targetWeightValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_target_three);
        ((RulerView) findViewById(R.id.ruler)).setOnValueChangeListener(this);
        targetWeightValue = findViewById(R.id.weight_value);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back_view:
                finish();
                break;
            case R.id.next_btn:
                postCreateTarget(setTargetDate());
                break;
        }
    }

    private Target setTargetDate() {
        target = (Target) getIntent().getSerializableExtra("target_data");
        String[] weightStr = targetWeightValue.getText().toString().split("[公斤]");
        target.setTargetweight(Double.parseDouble(weightStr[0]));
        try {
            target.setTargetcreatetime(DateFormatUtil.stringToDate(
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return  target;
    }

    @Override
    public void onChange(RulerView view, float value) {
        switch (view.getId()) {
            case R.id.ruler:
                targetWeightValue.setText( + value + " 公斤");
                break;
        }
    }

    private void postCreateTarget(final Target target) {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/target/create", target, new HttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(CreateTargetThreeActivity.this, "连接出错" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                TargetInfoMessage targetInfoMessage = gson.fromJson(result, TargetInfoMessage.class);
                if (("success").equals(targetInfoMessage.responseMessage.result)) {
                    Toast.makeText(CreateTargetThreeActivity.this, targetInfoMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CreateTargetThreeActivity.this,TargetActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
