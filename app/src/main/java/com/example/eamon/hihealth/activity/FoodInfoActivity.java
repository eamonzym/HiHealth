package com.example.eamon.hihealth.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.adapter.FoodAdapter;
import com.example.eamon.hihealth.db.Food;
import com.example.eamon.hihealth.gson.FoodInfoMessage;
import com.example.eamon.hihealth.util.BaseActivity;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class FoodInfoActivity extends BaseActivity {

    private ProgressDialog progressDialog;

    private NavigationTabStrip navigationTabStrip;

    private List<Food> foodList = new ArrayList<>();

    private FoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);
        navigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts);
        initNTS();
        navigationTabStrip.setTabIndex(0,true);
        progressDialog = ProgressDialog.show(FoodInfoActivity.this, "食物信息", "加载中，请稍后...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getFindFood("/food/all");
            }
        }).start();

    }

    private void initNTS() {
        navigationTabStrip.setTitles("全部","碳水", "蛋白质", "脂肪","水果","其他");
        navigationTabStrip.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
            @Override
            public void onStartTabSelected(String title, int index) {
                // 点击开始
                if (index == 0) {
                    Toast.makeText(FoodInfoActivity.this, "全部", Toast.LENGTH_SHORT).show();
                    getFindFood("/food/all");
                }
                else if (index == 1) {
                    Toast.makeText(FoodInfoActivity.this, "碳水", Toast.LENGTH_SHORT).show();
                    getFindFood("/food/carbohydrate");
                } else if (index == 2) {
                    Toast.makeText(FoodInfoActivity.this, "蛋白质", Toast.LENGTH_SHORT).show();
                    getFindFood("/food/protein");

                } else if (index == 3) {
                    Toast.makeText(FoodInfoActivity.this, "脂肪", Toast.LENGTH_SHORT).show();
                    getFindFood("/food/fat");
                } else if (index == 4) {
                    Toast.makeText(FoodInfoActivity.this, "水果", Toast.LENGTH_SHORT).show();
                    getFindFood("/food/fruit");
                }
                else if (index == 5) {
                    Toast.makeText(FoodInfoActivity.this, "其他", Toast.LENGTH_SHORT).show();
                    getFindFood("/food/other");
                }
            }

            @Override
            public void onEndTabSelected(String title, int index) {
                // 点击结束
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

    private void getFindFood(String url) {
        HttpManager.getAsync(HttpAddress.urlAddress + url, new HttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(FoodInfoActivity.this, "连接出错" , Toast.LENGTH_LONG).show();

            }
            @Override
            public void requestSuccess(String result) throws Exception {
                progressDialog.dismiss();
                Gson gson = new Gson();
                FoodInfoMessage foodInfoMessage = gson.fromJson(result, FoodInfoMessage.class);
                if ("success".equals(foodInfoMessage.responseMessage.result)) {
                    Toast.makeText(FoodInfoActivity.this, foodInfoMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                    foodList.clear();
                    foodList = foodInfoMessage.foodList;
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyler);
                    GridLayoutManager layoutManager = new GridLayoutManager(FoodInfoActivity.this,1);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new FoodAdapter(foodList);
                    recyclerView.setAdapter(adapter);
                } else if ("fail".equals(foodInfoMessage.responseMessage.result)) {
                    Toast.makeText(FoodInfoActivity.this, foodInfoMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                    foodList.clear();
                    foodList = new ArrayList<>();
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyler);
                    GridLayoutManager layoutManager = new GridLayoutManager(FoodInfoActivity.this,1);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new FoodAdapter(foodList);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }
}
