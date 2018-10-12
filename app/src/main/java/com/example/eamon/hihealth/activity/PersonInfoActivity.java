package com.example.eamon.hihealth.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.UserInfoMessage;
import com.example.eamon.hihealth.util.BaseActivity;
import com.example.eamon.hihealth.util.DateFormatUtil;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Request;

public class PersonInfoActivity extends BaseActivity {

    private ProgressDialog progressDialog;

    private LinearLayout name_view;

    private TextView nameValue;

    private TextView emailValue;

    private TextView sexValue;

    private TextView heightValue;

    private TextView weightValue;

    private TextView birthValue;

    private TextView targetValue;

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        name_view = (LinearLayout) findViewById(R.id.name_view);
        name_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonInfoActivity.this, PersonUpdateInfoActivity.class);
                intent.putExtra("personname", nameValue.getText());
                startActivity(intent);
            }
        });
        nameValue = (TextView) findViewById(R.id.name_value);
        emailValue = (TextView) findViewById(R.id.email_value);
        sexValue = (TextView) findViewById(R.id.sex_value);
        heightValue = (TextView) findViewById(R.id.height_value);
        weightValue = (TextView) findViewById(R.id.weight_value);
        birthValue = (TextView) findViewById(R.id.birth_value);
        targetValue = (TextView) findViewById(R.id.target_value);
        progressDialog = ProgressDialog.show(PersonInfoActivity.this, "个人信息", "加载中，请稍后...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postPersonInfo();
            }
        }).start();

    }

    private void postPersonInfo() {
        final Gson gson = new Gson();
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        final UserInfo userInfo = gson.fromJson(pref.getString("userinfo", ""), UserInfo.class);
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/users/personinfo", userInfo,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(PersonInfoActivity.this, "连接错误", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        progressDialog.dismiss();
                        UserInfoMessage userInfoMessage = gson.fromJson(result, UserInfoMessage.class);
                        if ("success".equals(userInfoMessage.responseMessage.result)) {
                            Toast.makeText(PersonInfoActivity.this, userInfoMessage.responseMessage.message, Toast.LENGTH_SHORT).show();
                            nameValue.setText(userInfoMessage.userInfo.getUsername());
                            emailValue.setText(userInfoMessage.userInfo.getUseremail());
                            if ("boy".equals(userInfoMessage.userInfo.getUsersex())) {
                                sexValue.setText("男");
                            } else if ("girl".equals(userInfoMessage.userInfo.getUsersex())) {
                                sexValue.setText("女");
                            }
                            heightValue.setText(userInfoMessage.userInfo.getUserheight().toString() + "厘米");
                            weightValue.setText(userInfoMessage.userInfo.getUserweight().toString() + "公斤");
                            birthValue.setText(DateFormatUtil.dateChangeUtil(userInfoMessage.userInfo.getUserbirth()));
                            targetValue.setText(userInfoMessage.userInfo.getUsertarget());
                        }

                    }
                });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back_view:
                Intent intent = new Intent(PersonInfoActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(PersonInfoActivity.this, MainActivity.class);
            startActivity(intent);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
