package com.example.eamon.hihealth.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.adapter.DietAllLogAdapter;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.DietLogMessage;
import com.example.eamon.hihealth.util.BaseActivity;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Request;

public class FoodLogListActivity extends BaseActivity {

    private ProgressDialog progressDialog;

    private DietAllLogAdapter adapter;

    private List<Date> dietLogTimeList = new ArrayList<>();

    private RecyclerView recyclerView;

    private SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_log_list);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        progressDialog = ProgressDialog.show(FoodLogListActivity.this, "饮食记录", "加载中，请稍后...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                postFindAllDiet(gson.fromJson(pref.getString("userinfo", ""), UserInfo.class));
            }
        }).start();
    }

    private void postFindAllDiet(UserInfo userInfo) {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/food/findalldiet", userInfo,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(FoodLogListActivity.this, "连接出错" , Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        progressDialog.dismiss();
                        Gson gson = new Gson();
                        DietLogMessage dietLogMessage = gson.fromJson(result, DietLogMessage.class);
                        if ("success".equals(dietLogMessage.responseMessage.result)) {
                            Toast.makeText(FoodLogListActivity.this, dietLogMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            dietLogTimeList = dietLogMessage.dietLogTime;
                            recyclerView = (RecyclerView) findViewById(R.id.foodlog_recycler);
                            GridLayoutManager layoutManager = new GridLayoutManager(FoodLogListActivity.this,1);
                            recyclerView.setLayoutManager(layoutManager);
                            adapter = new DietAllLogAdapter(dietLogTimeList);
                            recyclerView.setAdapter(adapter);
                        } else if ("fail".equals(dietLogMessage.responseMessage.result)) {
                            Toast.makeText(FoodLogListActivity.this, dietLogMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            dietLogTimeList = new ArrayList<>();
                            recyclerView = (RecyclerView) findViewById(R.id.foodlog_recycler);
                            GridLayoutManager layoutManager = new GridLayoutManager(FoodLogListActivity.this,1);
                            recyclerView.setLayoutManager(layoutManager);
                            adapter = new DietAllLogAdapter(dietLogTimeList);
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
