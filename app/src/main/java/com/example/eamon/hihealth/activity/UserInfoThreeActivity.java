package com.example.eamon.hihealth.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.HabitMessage;
import com.example.eamon.hihealth.gson.UserInfoMessage;
import com.example.eamon.hihealth.util.BaseActivity;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.example.eamon.hihealth.util.RulerView;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Request;

public class UserInfoThreeActivity extends BaseActivity implements RulerView.OnValueChangeListener{

    private static final String TAG = "UserInfoThreeActivity";

    private TextView weightValue;

    private RadioButton lossWeight;

    private RadioButton keepWeight;

    private UserInfo userInfo;

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_three);
        ((RulerView) findViewById(R.id.ruler)).setOnValueChangeListener(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        weightValue =  findViewById(R.id.weight_value);
        lossWeight = findViewById(R.id.target_loss);
        keepWeight = findViewById(R.id.target_keep);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back_view:
                finish();
                break;
            case R.id.three_next_btn:
                UserInfo userInfo_three = setUserInfo();
                Log.d("TAG", "Usersex data is " + userInfo_three.getUsersex());
                Log.d("TAG", "Userheight data is " + userInfo_three.getUserheight());
                Log.d("TAG", "Userbirth data is " + userInfo_three.getUserbirth());
                Log.d("TAG", "Userweight data is " + userInfo_three.getUserweight());
                Log.d("TAG", "Usertarget data is " + userInfo_three.getUsertarget());
                postSignup(userInfo_three);
                break;
        }
    }

    public UserInfo setUserInfo () {
        userInfo = (UserInfo) getIntent().getSerializableExtra("user_data");
        String[] weightStr = weightValue.getText().toString().split("[公斤]");
        Log.d("TAG", "weight data is " + weightStr[0]);
        userInfo.setUserweight(Double.parseDouble(weightStr[0]));
        if (lossWeight.isChecked()) {
            userInfo.setUsertarget("减肥");
        } else if (keepWeight.isChecked()) {
            userInfo.setUsertarget("塑形");
        }
        return userInfo;
    }

    public void postSignup(final UserInfo userInfo){

        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/habit/habitDefault", userInfo,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(UserInfoThreeActivity.this, "连接出错" , Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        Log.d(TAG,result);
                        Gson gson = new Gson();
                        HabitMessage habitMessage = gson.fromJson(result, HabitMessage.class);
                        if (("success").equals(habitMessage.responseMessage.result)) {
                            Toast.makeText(UserInfoThreeActivity.this, habitMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                        }
                    }
                });
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/users/bodydata",userInfo, new HttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Log.d(TAG,e.toString());
                Toast.makeText(UserInfoThreeActivity.this, "连接出错" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d(TAG,result);
                Gson gson = new Gson();
                UserInfoMessage userInfoMessage = gson.fromJson(result, UserInfoMessage.class);
                if (("success").equals(userInfoMessage.responseMessage.result)) {
                    editor = pref.edit();
                    editor.remove("userinfo");
                    editor.putString("userinfo", gson.toJson(userInfoMessage.userInfo));
                    editor.apply();
                    Toast.makeText(UserInfoThreeActivity.this, userInfoMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(UserInfoThreeActivity.this, HealthReportActivity.class);
                    startActivity(intent);
                }
            }
        });



    }
    @Override
    public void onChange(RulerView view, float value) {
        switch (view.getId()) {
            case R.id.ruler:
                weightValue.setText( + value + " 公斤");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
