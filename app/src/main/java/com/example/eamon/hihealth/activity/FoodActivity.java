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
import com.example.eamon.hihealth.adapter.DietLogAdapter;
import com.example.eamon.hihealth.db.DietLog;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.DietLogMessage;
import com.example.eamon.hihealth.util.BaseActivity;
import com.example.eamon.hihealth.util.DateFormatUtil;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Request;

public class FoodActivity extends BaseActivity {

    private ProgressDialog progressDialog;

    private Button foodLog;

    private LinearLayout createView;

    private List<DietLog> breakfastDietLogList = new ArrayList<>();

    private List<DietLog> lunchDietLogList = new ArrayList<>();

    private List<DietLog> dinnerDietLogList = new ArrayList<>();

    private DietLogAdapter breakfastAdapter;

    private DietLogAdapter lunchAdapter;

    private DietLogAdapter dinnerAdapter;

    private RecyclerView breakfastView;

    private RecyclerView lunchView;

    private RecyclerView dinnerView;


    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        foodLog = (Button) findViewById(R.id.food_log);
        foodLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, FoodLogListActivity.class);
                startActivity(intent);
            }
        });
        createView = (LinearLayout) findViewById(R.id.create_view);
        progressDialog = ProgressDialog.show(FoodActivity.this, "今日饮食", "加载中，请稍后...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postFindDiet();
            }
        }).start();
        createView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, FoodInfoActivity.class);
                startActivity(intent);

            }
        });

    }

    private DietLog setDietLog() {
        DietLog dietLog = new DietLog();
        Gson gson = new Gson();
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            dietLog.setDietlogtime(DateFormatUtil.stringToDate(
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        }catch (Exception e) {
            e.printStackTrace();
        }
        dietLog.setUserInfo(gson.fromJson(pref.getString("userinfo", ""), UserInfo.class));
        return dietLog;
    }

    private void postFindDiet() {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/food/finddiet", setDietLog(),
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(FoodActivity.this, "连接出错" , Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        progressDialog.dismiss();
                        Gson gson = new Gson();
                        DietLogMessage dietLogMessage = gson.fromJson(result, DietLogMessage.class);
                        if ("success".equals(dietLogMessage.responseMessage.result)) {
                            Toast.makeText(FoodActivity.this, dietLogMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            for (DietLog dietLog : dietLogMessage.dietLogList) {
                                if ("breakfast".equals(dietLog.getDiettype())) {
                                    breakfastDietLogList.add(dietLog);
                                } else if ("lunch".equals(dietLog.getDiettype())) {
                                    lunchDietLogList.add(dietLog);
                                } else if (("dinner".equals(dietLog.getDiettype()))) {
                                    dinnerDietLogList.add(dietLog);
                                }
                            }
                            initRecyclerView();
                        }
                        else if ("fail".equals(dietLogMessage.responseMessage.result)) {
                            Toast.makeText(FoodActivity.this, dietLogMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            breakfastDietLogList = new ArrayList<>();
                            lunchDietLogList = new ArrayList<>();
                            dinnerDietLogList = new ArrayList<>();
                            initRecyclerView();
                        }
                    }
                });
    }

    private void initRecyclerView() {
        breakfastView = (RecyclerView) findViewById(R.id.recyler_breakfast);
        lunchView = (RecyclerView) findViewById(R.id.recyler_lunch);
        dinnerView = (RecyclerView) findViewById(R.id.recyler_dinner);


        breakfastView.setLayoutManager(new GridLayoutManager(FoodActivity.this,1));
        lunchView.setLayoutManager(new GridLayoutManager(FoodActivity.this,1));
        dinnerView.setLayoutManager(new GridLayoutManager(FoodActivity.this,1));

        breakfastAdapter = new DietLogAdapter(breakfastDietLogList);
        lunchAdapter = new DietLogAdapter(lunchDietLogList);
        dinnerAdapter = new DietLogAdapter(dinnerDietLogList);

        breakfastView.setAdapter(breakfastAdapter);
        lunchView.setAdapter(lunchAdapter);
        dinnerView.setAdapter(dinnerAdapter);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back_view:
                Intent intent = new Intent(FoodActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(FoodActivity.this, MainActivity.class);
            startActivity(intent);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
