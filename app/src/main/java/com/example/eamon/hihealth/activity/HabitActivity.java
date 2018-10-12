package com.example.eamon.hihealth.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.adapter.HabitAdapter;
import com.example.eamon.hihealth.db.Habit;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.HabitMessage;
import com.example.eamon.hihealth.util.BaseActivity;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class HabitActivity extends BaseActivity {

    private ProgressDialog progressDialog;

    private HabitAdapter habitAdapter;

    private UserInfo userInfo;

    private Gson gson = new Gson();

    private SharedPreferences pref;

    private LinearLayout createView;

    private List<Habit> habitList = new ArrayList<>();

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        userInfo = gson.fromJson(pref.getString("userinfo", ""), UserInfo.class);
        progressDialog = ProgressDialog.show(HabitActivity.this, "健康习惯", "加载中，请稍后...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postHabitInfo(userInfo);
            }
        }).start();

        createView = (LinearLayout) findViewById(R.id.create_view) ;
        createView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HabitActivity.this, CreateHabitActivity.class);
                startActivity(intent);
            }
        });

    }


    private void postHabitInfo(UserInfo userInfo) {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/habit/habitInfo", userInfo,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(HabitActivity.this, "连接出错" , Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        progressDialog.dismiss();
                        HabitMessage habitMessage = gson.fromJson(result, HabitMessage.class);
                        if (("success").equals(habitMessage.responseMessage.result)) {
                            Toast.makeText(HabitActivity.this, habitMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            habitList = habitMessage.habitList;
                            recyclerView = (RecyclerView) findViewById(R.id.habit_recycler);
                            GridLayoutManager layoutManager = new GridLayoutManager(HabitActivity.this,1);
                            recyclerView.setLayoutManager(layoutManager);
                            habitAdapter = new HabitAdapter(habitList);
                            recyclerView.setAdapter(habitAdapter);
                        } else if (("fail").equals(habitMessage.responseMessage.result)) {
                            Toast.makeText(HabitActivity.this, habitMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            recyclerView = (RecyclerView) findViewById(R.id.habit_recycler);
                            GridLayoutManager layoutManager = new GridLayoutManager(HabitActivity.this,1);
                            recyclerView.setLayoutManager(layoutManager);
                            habitAdapter = new HabitAdapter(habitList);
                            recyclerView.setAdapter(habitAdapter);
                        }
                    }
                });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back_view:
                Intent intent = new Intent(HabitActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(HabitActivity.this, MainActivity.class);
            startActivity(intent);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
