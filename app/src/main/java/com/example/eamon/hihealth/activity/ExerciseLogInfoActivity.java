package com.example.eamon.hihealth.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.adapter.ExerciseLogAdapter;
import com.example.eamon.hihealth.db.ExerciseLog;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.ExerciseLogMessage;
import com.example.eamon.hihealth.util.DateFormatUtil;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class ExerciseLogInfoActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    private TextView logTimeView;

    private TextView minValueView;

    private TextView avgValueView;

    private TextView maxValueView;

    private SharedPreferences pref;

    private RecyclerView recyclerView;

    private ExerciseLogAdapter adapter;

    private ExerciseLog exerciseLog;

    private List<ExerciseLog> exerciseLogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_log_info);
        logTimeView = (TextView) findViewById(R.id.exerciselog_title);
        minValueView = (TextView) findViewById(R.id.min_view);
        avgValueView = (TextView) findViewById(R.id.avg_view);
        maxValueView = (TextView) findViewById(R.id.max_view);
        exerciseLog = (ExerciseLog) getIntent().getSerializableExtra("exerciselog");
        logTimeView.setText(DateFormatUtil.dateChangeUtil(exerciseLog.getExerciselogtime()));
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        exerciseLog.setUserInfo(new Gson().fromJson(pref.getString("userinfo", ""), UserInfo.class));
        progressDialog = ProgressDialog.show(ExerciseLogInfoActivity.this,DateFormatUtil.dateChangeUtil(exerciseLog.getExerciselogtime()) , "加载中，请稍后...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postFindTimeExercise(exerciseLog);
            }
        }).start();

    }

    private void postFindTimeExercise (final ExerciseLog exerciseLog) {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/exercise/todaylogdata", exerciseLog,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(ExerciseLogInfoActivity.this, "连接出错" , Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        progressDialog.dismiss();
                        Gson gson = new Gson();
                        ExerciseLogMessage exerciseLogMessage = gson.fromJson(result, ExerciseLogMessage.class);
                        if ("success".equals(exerciseLogMessage.responseMessage.result)) {
                            Toast.makeText(ExerciseLogInfoActivity.this, exerciseLogMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            exerciseLogList = exerciseLogMessage.exerciseLogList;
                            recyclerView = (RecyclerView) findViewById(R.id.exerciselog_recycler);
                            GridLayoutManager layoutManager = new GridLayoutManager(ExerciseLogInfoActivity.this, 1);
                            recyclerView.setLayoutManager(layoutManager);
                            adapter = new ExerciseLogAdapter(exerciseLogList);
                            recyclerView.setAdapter(adapter);
                            minValueView.setText(exerciseLogMessage.statisValue.min);
                            avgValueView.setText(exerciseLogMessage.statisValue.avg);
                            maxValueView.setText(exerciseLogMessage.statisValue.max);
                        } else if ("fail".equals(exerciseLogMessage.responseMessage.result)) {
                            Toast.makeText(ExerciseLogInfoActivity.this, exerciseLogMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            exerciseLogList = new ArrayList<>();
                            recyclerView = (RecyclerView) findViewById(R.id.exerciselog_recycler);
                            GridLayoutManager layoutManager = new GridLayoutManager(ExerciseLogInfoActivity.this, 1);
                            recyclerView.setLayoutManager(layoutManager);
                            adapter = new ExerciseLogAdapter(exerciseLogList);
                            recyclerView.setAdapter(adapter);
                            minValueView.setText("未知");
                            avgValueView.setText("未知");
                            maxValueView.setText("未知");

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
