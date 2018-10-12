package com.example.eamon.hihealth.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.util.BaseActivity;
import com.example.eamon.hihealth.util.RulerView;
import com.google.gson.Gson;

public class UserInfoOneActivity extends BaseActivity implements RulerView.OnValueChangeListener {

    private static final String TAG = "UserInfoOneActivity";

    private TextView heightValue;

    private RadioButton boyBtn;

    private RadioButton girlBtn;

    private UserInfo userInfo;

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_one);
        ((RulerView) findViewById(R.id.height_ruler)).setOnValueChangeListener(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        heightValue = findViewById(R.id.height_value);
        boyBtn = findViewById(R.id.boy_btn);
        girlBtn = findViewById(R.id.girl_btn);
    }

        public UserInfo setUserInfo () {
        Gson gson = new Gson();
        userInfo = gson.fromJson(pref.getString("userinfo", ""), UserInfo.class);
            Log.d(TAG,"username json:"+ userInfo.getUsername());
            Log.d(TAG,"userinfo:"+ pref.getString("userinfo", ""));
            if (boyBtn.isChecked())
        {
            userInfo.setUsersex("boy");
        }
        else if (girlBtn.isChecked())
        {
            userInfo.setUsersex("girl");

        }
        String[] heightStr = heightValue.getText().toString().split("[厘米]");
        Log.d("TAG", "height data is " + heightStr[0]);
        userInfo.setUserheight(Double.parseDouble(heightStr[0]));
        return userInfo;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back_view:
                finish();
                break;
            case R.id.one_next_btn:
                UserInfo userInfo_one = setUserInfo();
                Intent intent = new Intent(UserInfoOneActivity.this, UserInfoTwoActivity.class);
                intent.putExtra("user_data", userInfo_one);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onChange(RulerView view, float value) {
        switch (view.getId()) {
            case R.id.height_ruler:
                heightValue.setText(+value + "厘米");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
