package com.example.eamon.hihealth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.db.datainit.BodyDataLogInit;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserTargetActivity extends AppCompatActivity {

    private static final String TAG = "UserTargetActivity";

    private int user_sign = 1;

    private UserInfo userInfo = new UserInfo();

    @Bind(R.id.btn_lose)
    RadioButton _btnLose;

    @Bind(R.id.btn_shaping)
    RadioButton _btnShaping;

    @Bind(R.id.btn_ok)
    Button _btnOk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_target);
        ButterKnife.bind(this);

        // 点击完成
        _btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetEntry();

            }
        });
    }

    public void targetEntry () {
        Log.d(TAG, "TargetEntry");

        if(!validate()) {
            return;
        }
        UserInfo userInfo = setUserInfo();
        BodyDataLogInit bodyDataLogInit = new BodyDataLogInit();
        bodyDataLogInit.BodyDataLogDefault(userInfo);

        Intent intent = new Intent(UserTargetActivity.this,MainActivity.class);
        intent.putExtra("user_data", userInfo);
        Log.d("TAG", "news id is " + userInfo.getUser_name());
        Log.d("TAG", "news id is " + userInfo.getUser_target());
        startActivity(intent);
    }

    public boolean validate () {
        boolean valid = true;

        boolean loseChecked = _btnLose.isChecked();
        boolean shapingChecked = _btnShaping.isChecked();
        if (loseChecked == false && shapingChecked == false) {
            valid = false;
            Toast.makeText(getBaseContext(), "请选择健康目标" , Toast.LENGTH_LONG).show();
        }
        return valid;
    }

    public UserInfo setUserInfo () {
        userInfo = (UserInfo) getIntent().getSerializableExtra("user_data");
        Log.d("TAG", "news id is " + userInfo.getUser_name());
        if (_btnLose.isChecked())
        {
            userInfo.setUser_target(_btnLose.getText().toString());
            userInfo.update(userInfo.getId());

        }
        else if (_btnShaping.isChecked())
        {
            userInfo.setUser_target(_btnShaping.getText().toString());
            userInfo.update(userInfo.getId());

        }
        userInfo.setSign(user_sign);
        userInfo.update(userInfo.getId());
        Log.d("TAG", "User_target is " + userInfo.getUser_target());
        return userInfo;
    }

    public void setBodyData () {

    }

}
