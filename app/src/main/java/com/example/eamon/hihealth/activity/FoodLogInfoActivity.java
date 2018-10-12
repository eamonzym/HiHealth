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
import com.example.eamon.hihealth.adapter.DietAllLogInfoAdapter;
import com.example.eamon.hihealth.db.DietLog;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.DietLogMessage;
import com.example.eamon.hihealth.util.DateFormatUtil;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class FoodLogInfoActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    private TextView breakfastValue;

    private TextView lunchValue;

    private TextView dinnerValue;

    private TextView logTime;

    private DietLog dietLog;

    private SharedPreferences pref;

    private List<DietLog> dietLogList;

    private RecyclerView recyclerView;

    private DietAllLogInfoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_log_info);
        breakfastValue = (TextView) findViewById(R.id.breakfast_view);
        lunchValue = (TextView) findViewById(R.id.lunch_view);
        dinnerValue = (TextView) findViewById(R.id.dinner_view);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        dietLog = (DietLog) getIntent().getSerializableExtra("foodlog");
        dietLog.setUserInfo(new Gson().fromJson(pref.getString("userinfo", ""), UserInfo.class));
        logTime = (TextView) findViewById(R.id.foodlog_title);
        logTime.setText(DateFormatUtil.dateChangeUtil(dietLog.getDietlogtime()));
        progressDialog = ProgressDialog.show(FoodLogInfoActivity.this, DateFormatUtil.dateChangeUtil(dietLog.getDietlogtime()), "加载中，请稍后...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postFindTimeDiet(dietLog);
            }
        }).start();

    }

    private void postFindTimeDiet(final DietLog dietLog) {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/food/finddietdata", dietLog,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(FoodLogInfoActivity.this, "连接出错" , Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        progressDialog.dismiss();
                        int breakfastCal = 0;
                        int lunchCal = 0;
                        int dinnerCal = 0;
                        Gson gson = new Gson();
                        DietLogMessage dietLogMessage = gson.fromJson(result, DietLogMessage.class);
                        if ("success".equals(dietLogMessage.responseMessage.result)) {
                            Toast.makeText(FoodLogInfoActivity.this, dietLogMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            dietLogList = dietLogMessage.dietLogList;
                            recyclerView = (RecyclerView) findViewById(R.id.foodlog_recycler);
                            GridLayoutManager layoutManager = new GridLayoutManager(FoodLogInfoActivity.this,1);
                            recyclerView.setLayoutManager(layoutManager);
                            adapter = new DietAllLogInfoAdapter(dietLogList);
                            recyclerView.setAdapter(adapter);
                            for (DietLog dietLog1 : dietLogMessage.dietLogList) {
                                if ("breakfast".equals(dietLog1.getDiettype())) {
                                    breakfastCal = breakfastCal + dietLog1.getDietcalorie().intValue();
                                } else if ("lunch".equals(dietLog1.getDiettype())) {
                                    lunchCal = lunchCal + dietLog1.getDietcalorie().intValue();
                                } else if ("dinner".equals(dietLog1.getDiettype())) {
                                    dinnerCal = dinnerCal + dietLog1.getDietcalorie().intValue();
                                }
                            }
                            breakfastValue.setText(breakfastCal + "");
                            lunchValue.setText(lunchCal + "");
                            dinnerValue.setText(dinnerCal + "");
                        } else if ("fail".equals(dietLogMessage.responseMessage.result)) {
                            Toast.makeText(FoodLogInfoActivity.this, dietLogMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            dietLogList = new ArrayList<>();
                            recyclerView = (RecyclerView) findViewById(R.id.foodlog_recycler);
                            GridLayoutManager layoutManager = new GridLayoutManager(FoodLogInfoActivity.this,1);
                            recyclerView.setLayoutManager(layoutManager);
                            adapter = new DietAllLogInfoAdapter(dietLogList);
                            recyclerView.setAdapter(adapter);
                            breakfastValue.setText("未知");
                            lunchValue.setText("未知");
                            dinnerValue.setText("未知");
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
