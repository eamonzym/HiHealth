package com.example.eamon.hihealth.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.UserInfoMessage;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Request;

public class PersonUpdateInfoActivity extends AppCompatActivity {

    private EditText personNameEdit;

    private TextView successView;

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_update_info);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        personNameEdit = (EditText) findViewById(R.id.person_name);
        personNameEdit.setText(getIntent().getStringExtra("personname"));
        successView = (TextView) findViewById(R.id.create_success);
        successView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo userInfo = new Gson().fromJson(pref.getString("userinfo", ""), UserInfo.class);
                userInfo.setUsername(personNameEdit.getText().toString());
                postUpdatePerson(userInfo);
            }
        });

    }

    private void postUpdatePerson(UserInfo userInfo) {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/users/updatename", userInfo,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(PersonUpdateInfoActivity.this, "连接错误", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        Gson gson = new Gson();
                        UserInfoMessage userInfoMessage = gson.fromJson(result, UserInfoMessage.class);
                        if ("success".equals(userInfoMessage.responseMessage.result)) {
                            Toast.makeText(PersonUpdateInfoActivity.this, userInfoMessage.responseMessage.message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PersonUpdateInfoActivity.this, PersonInfoActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back_view:
                finish();
                break;
        }
    }

}
