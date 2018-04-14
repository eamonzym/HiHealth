package com.example.eamon.hihealth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.UserInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserInfoActivity extends AppCompatActivity {

    private static final String TAG = "UserInfoActivity";




    @Bind(R.id.btn_boy)
    RadioButton _btnBoy;

    @Bind(R.id.btn_girl)
    RadioButton _btngirl;

    @Bind(R.id.input_height)
    EditText _inputHeight;

    @Bind(R.id.input_weight)
    EditText _inputWeight;

    @Bind(R.id.input_birth)
    EditText _inputBirth;

    @Bind(R.id.btn_next)
    Button _btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);

        _btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoEntry();
            }
        });
    }

    public void infoEntry () {
        Log.d(TAG, "InfoEntry");

        if(!validate()) {
            onInfoEntryFailed();
            return;
        }

        Intent intent = new Intent(UserInfoActivity.this,UserTargetActivity.class);
        intent.putExtra("user_data", setUserInfo());
        startActivity(intent);

    }

    public UserInfo setUserInfo () {
        UserInfo userInfo = new UserInfo();
        userInfo = (UserInfo) getIntent().getSerializableExtra("user_data");
        if (_btnBoy.isChecked())
        {
            userInfo.setUser_sex(_btnBoy.getText().toString());
            userInfo.update(userInfo.getId());
        }
        else if (_btngirl.isChecked())
        {
            userInfo.setUser_sex(_btngirl.getText().toString());
            userInfo.update(userInfo.getId());
        }
        userInfo.setUser_height(Double.parseDouble(_inputHeight.getText().toString()));
        userInfo.update(userInfo.getId());
        userInfo.setUser_weight(Double.parseDouble(_inputWeight.getText().toString()));
        userInfo.update(userInfo.getId());
        Log.d("TAG", "id is " + userInfo.getId());
        Log.d("TAG", "User_Id is " + userInfo.getUser_Id());
        Log.d("TAG", "User_name is " + userInfo.getUser_name());
        Log.d("TAG", "User_weight is " + userInfo.getUser_weight());
        Log.d("TAG", "User_height is " + userInfo.getUser_height());
        Log.d("TAG", "User_sex is " + userInfo.getUser_sex());
        return userInfo;
    }
    public void onInfoEntryFailed () {
        Toast.makeText(getBaseContext(), "请填写所有信息" , Toast.LENGTH_LONG).show();

    }

    public boolean validate () {
        boolean valid = true;

        String height = _inputHeight.getText().toString();
        String weight = _inputWeight.getText().toString();
        String birth = _inputBirth.getText().toString();

        if (height.isEmpty()) {
            _inputHeight.setError("身高不能为空");
            valid = false;
        } else {
            _inputHeight.setError(null);
        }

        if(weight.isEmpty()) {
            _inputWeight.setError("体重不能为空");
            valid = false;
        } else {
            _inputWeight.setError(null);
            }

        if(birth.isEmpty()) {
            _inputBirth.setError("日期不能为空");
            valid = false;
        } else {
            _inputBirth.setError(null);
        }


        return  valid;
    }
}
