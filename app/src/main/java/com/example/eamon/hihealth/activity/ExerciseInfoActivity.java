package com.example.eamon.hihealth.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.adapter.ExerciseAdapter;
import com.example.eamon.hihealth.db.Exercise;
import com.example.eamon.hihealth.gson.ExerciseMessage;
import com.example.eamon.hihealth.util.BaseActivity;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class ExerciseInfoActivity extends BaseActivity {

    private ProgressDialog progressDialog;


    private RecyclerView recyclerView;

    private ExerciseAdapter exerciseAdapter;

    private List<Exercise> exerciseList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_info);
        progressDialog = ProgressDialog.show(ExerciseInfoActivity.this, "运动信息", "加载中，请稍后...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getFindExerciseInfo();
            }
        }).start();


    }

    private void getFindExerciseInfo() {
        HttpManager.getAsync(HttpAddress.urlAddress + "/exercise/info",
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(ExerciseInfoActivity.this, "连接出错" , Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        progressDialog.dismiss();
                        Gson gson = new Gson();
                        ExerciseMessage exerciseMessage = gson.fromJson(result, ExerciseMessage.class);
                        if ("success".equals(exerciseMessage.responseMessage.result)) {
                            Toast.makeText(ExerciseInfoActivity.this, exerciseMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            exerciseList = exerciseMessage.exerciseList;
                            recyclerView = (RecyclerView) findViewById(R.id.recyler);
                            GridLayoutManager layoutManager = new GridLayoutManager(ExerciseInfoActivity.this, 1);
                            recyclerView.setLayoutManager(layoutManager);
                            exerciseAdapter = new ExerciseAdapter(exerciseList);
                            recyclerView.setAdapter(exerciseAdapter);
                        } else if ("fail".equals(exerciseMessage.responseMessage.result)) {
                            Toast.makeText(ExerciseInfoActivity.this, exerciseMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            recyclerView = (RecyclerView) findViewById(R.id.recyler);
                            GridLayoutManager layoutManager = new GridLayoutManager(ExerciseInfoActivity.this, 1);
                            recyclerView.setLayoutManager(layoutManager);
                            exerciseAdapter = new ExerciseAdapter(exerciseList);
                            recyclerView.setAdapter(exerciseAdapter);
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
