package com.example.eamon.hihealth.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.BodyData;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.db.datainit.BodyDataInit;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "LoginActivity";





    List<UserInfo> userInfoList;

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
//        DataSupport.deleteAll(UserInfo.class);
//        DataSupport.deleteAll(BodyData.class);
//        DataSupport.deleteAll(BodyDataLog.class);
        ButterKnife.bind(this);
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
                _eamilText.setText(signInfo.getUser_Email());
                Log.d("TAG", "onActiviytResult user name  is " + signInfo.getUser_name());
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onLoginSuccess () {
        _loginButton.setEnabled(true);
        List<BodyData> bodyDataList = DataSupport.findAll(BodyData.class);
        if (bodyDataList.isEmpty()){
            BodyDataInit bodyDataInit = new BodyDataInit();
            bodyDataInit.BodyDataDefault();
        }
        for ( UserInfo loginInfo : userInfoList)
        {
            Log.d("TAG", "Login succ user name is " + loginInfo.getUser_name());
            Log.d("TAG", "Login succ user name is " + loginInfo.getUser_height());
        if (loginInfo.getSign() == 0) {
            Intent intent = new Intent(LoginActivity.this, UserInfoActivity.class);
            intent.putExtra("user_data",loginInfo);
            startActivity(intent);
        } else if (loginInfo.getSign() == 1) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("user_data",loginInfo);
            startActivity(intent);
        }

        }
    }

    public void onLoginFailed () {
        Toast.makeText(getBaseContext(), "登录失败" , Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _eamilText.getText().toString();
        String password = _passwordText.getText().toString();

        userInfoList = DataSupport.where("user_email = ? and user_password = ?", email, password).find(UserInfo.class);


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _eamilText.setError("请输入正确的邮箱地址");
            valid = false;
        } else {
            _eamilText.setError(null);
            if (userInfoList.isEmpty()) {
                Toast.makeText(getBaseContext(), "输入的邮箱或密码有误，请重新输入" , Toast.LENGTH_LONG).show();
                valid = false;
            }
        }

        if(password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("请控制密码的位数在4~10位");
            valid = false;
        } else {
            _passwordText.setError(null);
            if (userInfoList.isEmpty()) {
                Toast.makeText(getBaseContext(), "输入的邮箱或密码有误，请重新输入" , Toast.LENGTH_LONG).show();
                valid = false;
            }
        }
            return  valid;
    }
}
