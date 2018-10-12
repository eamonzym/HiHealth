package com.example.eamon.hihealth.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.DietLog;
import com.example.eamon.hihealth.db.Food;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.DietLogMessage;
import com.example.eamon.hihealth.util.BaseActivity;
import com.example.eamon.hihealth.util.DateFormatUtil;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.google.gson.Gson;
import com.shawnlin.numberpicker.NumberPicker;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;

public class CreateDietActivity extends BaseActivity {

    private TextView dietTitle;

    private CircleImageView foodImage;

    private TextView foodName;

    private TextView foodCal;

    private TextView calValue;

    private TextView quantityValue;

    private NumberPicker numberPicker;

    private String dietType;

    private Food food;

    private SharedPreferences pref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_diet);
        init();
    }

    private void init() {
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        dietTitle = (TextView) findViewById(R.id.diet_title_name);
        foodImage = (CircleImageView) findViewById(R.id.food_icon);
        foodName = (TextView) findViewById(R.id.food_name);
        foodCal = (TextView) findViewById(R.id.food_cal);
        calValue = (TextView) findViewById(R.id.cal_value);
        quantityValue = (TextView) findViewById(R.id.quantity_value);
        numberPicker = (NumberPicker) findViewById(R.id.number_picker);
        food = (Food) getIntent().getSerializableExtra("foodinfo");
        dietType = getIntent().getStringExtra("diettype");

        if ("breakfast".equals(dietType)) {
            dietTitle.setText("早餐记录");
        } else if ("lunch".equals(dietType)) {
            dietTitle.setText("午餐记录");
        } else if (("dinner").equals(dietType)) {
            dietTitle.setText("晚餐记录");
        }
        Glide.with(CreateDietActivity.this).load(HttpAddress.urlAddress + food.getFoodImag())
                .into(foodImage);
        foodName.setText(food.getFoodname());
        foodCal.setText(food.getFoodcalorie().toString());
        calValue.setText(food.getFoodcalorie().toString() + "千卡");
        quantityValue.setText("100克");
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                quantityValue.setText(newVal + "克");
                calValue.setText(String.valueOf(Math.round(newVal * (food.getFoodcalorie().doubleValue() / 100))) + "千卡");
            }
        });

    }

    private DietLog setDietLog() {
        Gson gson = new Gson();
        DietLog dietLog = new DietLog();
        dietLog.setUserInfo(gson.fromJson(pref.getString("userinfo", ""), UserInfo.class));
        dietLog.setFood(food);
        dietLog.setDiettype(dietType);
        String[] calStr = calValue.getText().toString().split("[千卡]");
        dietLog.setDietcalorie(Double.parseDouble(calStr[0]));
        String[] quantityStr = quantityValue.getText().toString().split("[克]");
        dietLog.setDietquantity(Double.parseDouble(quantityStr[0]));
        try{
            dietLog.setDietlogtime(DateFormatUtil.stringToDate(DateFormatUtil.getDateNow()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dietLog;
    }

    private void  postCreateDiet() {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/food/creatediet", setDietLog(),
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(CreateDietActivity.this, "连接出错" , Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        Gson gson = new Gson();
                        DietLogMessage dietLogMessage = gson.fromJson(result, DietLogMessage.class);
                        if (("success").equals(dietLogMessage.responseMessage.result)) {
                            Toast.makeText(CreateDietActivity.this, dietLogMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(CreateDietActivity.this,FoodActivity.class);
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
            case R.id.next_btn:
                postCreateDiet();
                break;
        }
    }
}
