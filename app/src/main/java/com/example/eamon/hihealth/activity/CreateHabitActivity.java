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
import com.example.eamon.hihealth.db.Habit;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.HabitMessage;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Request;

public class CreateHabitActivity extends AppCompatActivity {

    private SharedPreferences pref;

    private EditText habitName;

    private TextView successCreate;

    private Habit habit;

    private UserInfo userInfo;

    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit);
        habitName = (EditText) findViewById(R.id.habit_name);
        successCreate = (TextView) findViewById(R.id.create_success);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        userInfo = gson.fromJson(pref.getString("userinfo", ""), UserInfo.class);
        successCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habit = new Habit();
                habit.setHabitname(habitName.getText().toString());
                habit.setUsername(userInfo.getUsername());
                posetHabitCreate(habit);
            }
        });
    }

    private void posetHabitCreate(final Habit habit) {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/habit/habitCreate", habit,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(CreateHabitActivity.this, "连接出错" , Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        HabitMessage habitMessage = gson.fromJson(result, HabitMessage.class);
                        if ("success".equals(habitMessage.responseMessage.result)) {
                            Toast.makeText(CreateHabitActivity.this, habitMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(CreateHabitActivity.this, HabitActivity.class);
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
