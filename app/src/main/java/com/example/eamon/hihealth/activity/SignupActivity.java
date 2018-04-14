package com.example.eamon.hihealth.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.UserInfo;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    private int user_sign = 0;



    @Bind(R.id.input_name)
    EditText _nameText;

    @Bind(R.id.input_email)
    EditText _emailText;

    @Bind(R.id.input_password)
    EditText _passwordText;

    @Bind(R.id.btn_signup)
    TextView _signupButton;

    @Bind(R.id.link_login)
    TextView _loginLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        // 监听注册按钮 方便处理事务
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG,"Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("正在注册...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        onSignupSuccess();

                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onSignupSuccess () {
        _signupButton.setEnabled(true);
        UserInfo userInfo = setUserInfo();
        Log.d("TAG", "(singup succ)user name is " + userInfo.getUser_name());
        Intent intent = new Intent();
        intent.putExtra("user_data", userInfo);
        setResult(RESULT_OK, intent);
        finish();
    }
    public UserInfo setUserInfo () {
        UserInfo userInfo = new UserInfo();
        String userId = UUID.randomUUID().toString();
        userInfo.setUser_Id(userId);
        userInfo.setUser_Email(_emailText.getText().toString());
        userInfo.setUser_password(_passwordText.getText().toString());
        userInfo.setUser_name(_nameText.getText().toString());
        userInfo.setSign(user_sign);
        Log.d("TAG", "news id is " + userInfo.getUser_Id());
        userInfo.save();
        Log.d("TAG", "news id is " + userInfo.getUser_Id());

        return userInfo;
    }
    public void onSignupFailed () {
        Toast.makeText(getBaseContext(), "注册失败",
                Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate () {

        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        List<UserInfo> userInfo_name = DataSupport
                .where("user_name = ?", name).find(UserInfo.class);

        List<UserInfo> userInfo_email = DataSupport
                .where("user_email = ?", email).find(UserInfo.class);


        if (userInfo_name.isEmpty()) {
            _nameText.setError(null);
        } else {
            _nameText.setError("该用户名已存在");
            valid = false;
        }


        if (userInfo_email.isEmpty()) {
            _nameText.setError(null);
        } else {
            _emailText.setError("该邮箱已注册");
            valid = false;
        }

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("名字不能长度太短");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("请输入正确的邮箱地址");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("请控制密码的位数在4~10位");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;


    }

}
