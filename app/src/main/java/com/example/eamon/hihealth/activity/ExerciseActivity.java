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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.adapter.ExerciseLogAdapter;
import com.example.eamon.hihealth.db.ExerciseLog;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.ExerciseLogMessage;
import com.example.eamon.hihealth.util.BaseActivity;
import com.example.eamon.hihealth.util.DateFormatUtil;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class ExerciseActivity extends BaseActivity {


    private ProgressDialog progressDialog;

    private Button exerciseLog;

    private SharedPreferences pref;

    private ExerciseLogAdapter exerciseLogAdapter;

    private LinearLayout createView;

    private RecyclerView recyclerView;

    private List<ExerciseLog> exerciseLogList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        exerciseLog = (Button) findViewById(R.id.data_exercise);
        exerciseLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExerciseActivity.this, ExerciseLogListActivity.class));

            }
        });
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        final ExerciseLog exerciseLog = new ExerciseLog();
        exerciseLog.setUserInfo(gson.fromJson(pref.getString("userinfo", ""), UserInfo.class));
        try{
            exerciseLog.setExerciselogtime(DateFormatUtil.stringToDate(DateFormatUtil.getDateNow()));

        }catch (Exception e) {
            e.printStackTrace();
        }
        progressDialog = ProgressDialog.show(ExerciseActivity.this, "健康运动", "加载中，请稍后...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postExerciseLog(exerciseLog);
            }
        }).start();

        createView = (LinearLayout) findViewById(R.id.create_view);
        createView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExerciseActivity.this, ExerciseInfoActivity.class));
            }
        });
    }

    private void postExerciseLog(ExerciseLog exerciseLog) {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/exercise/todaylog", exerciseLog,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(ExerciseActivity.this, "连接出错" , Toast.LENGTH_LONG).show();

                    }
                    @Override
                    public void requestSuccess(String result) throws Exception {
                        progressDialog.dismiss();
                        Gson gson = new Gson();
                        ExerciseLogMessage exerciseLogMessage = gson.fromJson(result, ExerciseLogMessage.class);
                        if ("success".equals(exerciseLogMessage.responseMessage.result)) {
                            Toast.makeText(ExerciseActivity.this, exerciseLogMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            exerciseLogList = exerciseLogMessage.exerciseLogList;
                            recyclerView = (RecyclerView) findViewById(R.id.recyler_exercise);
                            GridLayoutManager layoutManager = new GridLayoutManager(ExerciseActivity.this, 1);
                            recyclerView.setLayoutManager(layoutManager);
                            exerciseLogAdapter = new ExerciseLogAdapter(exerciseLogList);
                            recyclerView.setAdapter(exerciseLogAdapter);
                        } else if ("fail".equals(exerciseLogMessage.responseMessage.result)) {
                            exerciseLogList = new ArrayList<>();
                            Toast.makeText(ExerciseActivity.this, exerciseLogMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            recyclerView = (RecyclerView) findViewById(R.id.recyler_exercise);
                            GridLayoutManager layoutManager = new GridLayoutManager(ExerciseActivity.this, 1);
                            recyclerView.setLayoutManager(layoutManager);
                            exerciseLogAdapter = new ExerciseLogAdapter(exerciseLogList);
                            recyclerView.setAdapter(exerciseLogAdapter);
                        }
                    }
                });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back_view:
                Intent intent = new Intent(ExerciseActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(ExerciseActivity.this, MainActivity.class);
            startActivity(intent);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
