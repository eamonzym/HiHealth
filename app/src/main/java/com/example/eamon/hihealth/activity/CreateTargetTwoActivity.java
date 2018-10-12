package com.example.eamon.hihealth.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.Target;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.UserInfoMessage;
import com.example.eamon.hihealth.util.BaseActivity;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.example.eamon.hihealth.util.RulerView;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Request;

public class CreateTargetTwoActivity extends BaseActivity implements RulerView.OnValueChangeListener{

    private TextView weightValue;

    private RadioButton loosWeightBtn;

    private RadioButton keepWeightBtn;

    private RulerView rulerView;

    private Target target;

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_target_two);
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        rulerView = (RulerView) findViewById(R.id.ruler);
        weightValue = (TextView) findViewById(R.id.weight_value);
        loosWeightBtn = (RadioButton) findViewById(R.id.target_loss);
        keepWeightBtn = (RadioButton) findViewById(R.id.target_keep);
        rulerView.setOnValueChangeListener(this);
        Gson gson = new Gson();
        UserInfo userInfo = gson.fromJson(pref.getString("userinfo", ""), UserInfo.class);
        postPersonWeight(userInfo);
    }

    private void postPersonWeight(UserInfo userInfo) {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/users/personinfo", userInfo,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(CreateTargetTwoActivity.this, "连接出错" , Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        Gson gson = new Gson();
                        UserInfoMessage userInfoMessage = gson.fromJson(result, UserInfoMessage.class);
                        if ("success".equals(userInfoMessage.responseMessage.result)) {
                            Toast.makeText(CreateTargetTwoActivity.this, userInfoMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            rulerView.setSelectedValue(userInfoMessage.userInfo.getUserweight().floatValue());
                            weightValue.setText(userInfoMessage.userInfo.getUserweight() + " 公斤");
                        }

                    }
                });
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back_view:
                finish();
                break;
            case R.id.next_btn:
                Intent intent = new Intent(CreateTargetTwoActivity.this,CreateTargetThreeActivity.class);
                intent.putExtra("target_data",setTargetData() );
                startActivity(intent);
                break;
        }
    }
    private Target setTargetData() {
        target = (Target) getIntent().getSerializableExtra("target_data");
        if (loosWeightBtn.isChecked())
        {
           target.setTargettype(loosWeightBtn.getText().toString());
        }
        else if (keepWeightBtn.isChecked())
        {
            target.setTargettype(keepWeightBtn.getText().toString());

        }
        String[] weightStr = weightValue.getText().toString().split("[公斤]");
        target.setInitialweight(Double.parseDouble(weightStr[0]));
        return  target;
    }

    @Override
    public void onChange(RulerView view, float value) {
        switch (view.getId()) {
            case R.id.ruler:
                weightValue.setText(value + " 公斤");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
