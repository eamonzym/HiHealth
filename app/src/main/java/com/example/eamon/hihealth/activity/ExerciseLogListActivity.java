package com.example.eamon.hihealth.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.adapter.ExerciseAllLogAdapter;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.ExerciseLogMessage;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Request;

public class ExerciseLogListActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private SharedPreferences pref;

    private RecyclerView recyclerView;

    private ExerciseAllLogAdapter adapter;

    private List<Date> exerciseLogTimeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_log_list);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        progressDialog = ProgressDialog.show(ExerciseLogListActivity.this,"运动记录", "加载中，请稍后...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                postFindAllExerciseLog(gson.fromJson(pref.getString("userinfo", ""), UserInfo.class));
            }
        }).start();

    }

    private void postFindAllExerciseLog (UserInfo userInfo) {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/exercise/alllog", userInfo,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(ExerciseLogListActivity.this, "连接出错" , Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        progressDialog.dismiss();
                        Gson gson = new Gson();
                        ExerciseLogMessage exerciseLogMessage = gson.fromJson(result, ExerciseLogMessage.class);
                        if ("success".equals(exerciseLogMessage.responseMessage.result)) {
                            Toast.makeText(ExerciseLogListActivity.this, exerciseLogMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            exerciseLogTimeList = exerciseLogMessage.exerciseLogTime;
                            recyclerView = (RecyclerView) findViewById(R.id.exerciselog_recycler);
                            GridLayoutManager layoutManager = new GridLayoutManager(ExerciseLogListActivity.this,1);
                            recyclerView.setLayoutManager(layoutManager);
                            adapter = new ExerciseAllLogAdapter(exerciseLogTimeList);
                            recyclerView.setAdapter(adapter);
                        } else if ("fail".equals(exerciseLogMessage.responseMessage.result)) {
                            Toast.makeText(ExerciseLogListActivity.this, exerciseLogMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            exerciseLogTimeList = new ArrayList<>();
                            recyclerView = (RecyclerView) findViewById(R.id.exerciselog_recycler);
                            GridLayoutManager layoutManager = new GridLayoutManager(ExerciseLogListActivity.this,1);
                            recyclerView.setLayoutManager(layoutManager);
                            adapter = new ExerciseAllLogAdapter(exerciseLogTimeList);
                            recyclerView.setAdapter(adapter);
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
