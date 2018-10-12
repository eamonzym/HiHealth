package com.example.eamon.hihealth.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.Target;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.util.BaseActivity;
import com.google.gson.Gson;

public class CreateTargetOneActivity extends BaseActivity {

    private Target target;

    private EditText targetName;

    private SharedPreferences pref;

    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_target_one);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        targetName = (EditText) findViewById(R.id.target_name);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back_view:
                finish();
                break;
            case R.id.next_btn:
                UserInfo userInfo = gson.fromJson(pref.getString("userinfo",""), UserInfo.class);
                target = new Target();
                target.setUsername(userInfo.getUsername());
                target.setTargetname(targetName.getText().toString());
                Intent intent = new Intent(CreateTargetOneActivity.this,CreateTargetTwoActivity.class);
                intent.putExtra("target_data",target );
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
