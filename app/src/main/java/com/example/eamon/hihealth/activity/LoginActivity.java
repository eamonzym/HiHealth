package com.example.eamon.hihealth.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

public class LoginActivity extends BaseActivity {


    private static final String TAG = "LoginActivity";

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    @Bind(R.id.input_email)
    EditText _eamilText;

    @Bind(R.id.input_password)
    EditText _passwordText;

    @Bind(R.id.btn_login)
    TextView _loginButton;

    @Bind(R.id.link_signup)
    TextView _signuplink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        if (!pref.getString("userinfo", "").isEmpty()) {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
        // 登录按钮事件监听
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        // 注册按钮事件的监听
        _signuplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击转到注册activity

                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    public void login () {
        Log.d(TAG, "Login");

        if(!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("登录中...");
        progressDialog.show();

        String eamail = _eamilText.getText().toString();

        String password = _passwordText.getText().toString();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        onLoginSuccess();

                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                UserInfo signInfo = (UserInfo) data.getSerializableExtra("user_data");
                _eamilText.setText(signInfo.getUseremail());
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void postSignup(UserInfo userInfo){

        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/users/login",userInfo, new HttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Log.d(TAG,e.toString());
                Toast.makeText(LoginActivity.this, "连接出错" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d(TAG,result);
                Gson gson = new Gson();
                SignLoginMessage signLoginMessage = gson.fromJson(result, SignLoginMessage.class);
                if (("success").equals(signLoginMessage.responseMessage.result)) {
                    Toast.makeText(LoginActivity.this, signLoginMessage.responseMessage.message, Toast.LENGTH_LONG).show();
                    Log.d(TAG,"username:"+ signLoginMessage.userinfo.getUsername());
                    Log.d(TAG,"usereamil:"+ signLoginMessage.userinfo.getUseremail());
                    editor = pref.edit();
                    editor.clear();
                    editor.putString("userinfo", gson.toJson(signLoginMessage.userinfo));
                    Log.d(TAG,"userinfo json bean:"+ signLoginMessage.userinfo);
                    Log.d(TAG,"userinfo json:"+ gson.toJson(signLoginMessage.userinfo));
                    editor.apply();
                    if (("new").equals(signLoginMessage.userinfo.getUsersign())) {
                        Intent intent = new Intent(LoginActivity.this,UserInfoOneActivity.class);
                        startActivity(intent);
                    } else if (("old").equals(signLoginMessage.userinfo.getUsersign())) {
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                } else if (("fail").equals(signLoginMessage.responseMessage.result)) {
                    Toast.makeText(LoginActivity.this, signLoginMessage.responseMessage.message, Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    public UserInfo setUserInfo () {
        UserInfo userInfo = new UserInfo();
        userInfo.setUseremail(_eamilText.getText().toString());
        userInfo.setUserpassword(_passwordText.getText().toString());
        return userInfo;
    }

    public void onLoginSuccess () {
        _loginButton.setEnabled(true);
        UserInfo userInfo = setUserInfo();
        postSignup(userInfo);
    }

    public void onLoginFailed () {
        Toast.makeText(getBaseContext(), "登录失败" , Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _eamilText.getText().toString();
        String password = _passwordText.getText().toString();



        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _eamilText.setError("请输入正确的邮箱地址");
            valid = false;
        } else {
            _eamilText.setError(null);
        }

        if(password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("请控制密码的位数在4~10位");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
            return  valid;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
