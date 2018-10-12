package com.example.eamon.hihealth.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.SignLoginMessage;
import com.example.eamon.hihealth.util.BaseActivity;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

public class SignupActivity extends BaseActivity {

    private static final String TAG = "SignupActivity";


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


    public void postSignup(UserInfo userInfo){

        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/users/signup",userInfo, new HttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Log.d(TAG,e.toString());
                Toast.makeText(SignupActivity.this, "连接出错" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d(TAG,result);
                Gson gson = new Gson();
                SignLoginMessage signLoginMessage = gson.fromJson(result, SignLoginMessage.class);
                if (("success").equals(signLoginMessage.responseMessage.result)) {
                    Toast.makeText(SignupActivity.this, signLoginMessage.responseMessage.message, Toast.LENGTH_LONG).show();
                    Log.d(TAG,"usereamil:"+ signLoginMessage.userinfo.getUseremail());
                    Intent intent = new Intent();
                    intent.putExtra("user_data", signLoginMessage.userinfo);
                    setResult(RESULT_OK, intent);
                    finish();
                } else if (("fail").equals(signLoginMessage.responseMessage.result)) {
                    Toast.makeText(SignupActivity.this, signLoginMessage.responseMessage.message, Toast.LENGTH_LONG).show();
                }

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
        postSignup(userInfo);
        Log.d("TAG", "(singup succ)user name is " + userInfo.getUsername());
    }
    public UserInfo setUserInfo () {
        UserInfo userInfo = new UserInfo();
        userInfo.setUseremail(_emailText.getText().toString());
        userInfo.setUserpassword(_passwordText.getText().toString());
        userInfo.setUsername(_nameText.getText().toString());

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
