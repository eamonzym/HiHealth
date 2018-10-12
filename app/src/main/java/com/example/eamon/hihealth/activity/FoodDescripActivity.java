package com.example.eamon.hihealth.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.Food;
import com.example.eamon.hihealth.util.BaseActivity;
import com.example.eamon.hihealth.util.HttpAddress;

public class FoodDescripActivity extends BaseActivity {

    private ImageView imageView;

    private Food food;

    private TextView foodType;

    private TextView foodTitleName;

    private TextView foodName;

    private TextView foodSuggest;

    private TextView foodDescrip;

    private TextView foodCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_descrip);
        initFoodInfo();

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back_view:
                finish();
                break;
        }
    }
    private void initFoodInfo() {
        foodTitleName = (TextView) findViewById(R.id.food_title_name);
        foodName = (TextView) findViewById(R.id.food_name);
        foodCal = (TextView) findViewById(R.id.food_cal);
        foodType = (TextView) findViewById(R.id.food_type);
        foodSuggest = (TextView) findViewById(R.id.food_suggest);
        foodDescrip = (TextView) findViewById(R.id.food_descrip);
        imageView = (ImageView) findViewById(R.id.image);
        food = (Food) getIntent().getSerializableExtra("foodinfo");
        foodTitleName.setText(food.getFoodname());
        foodName.setText(food.getFoodname());
        foodCal.setText(food.getFoodcalorie() + "Kcal");
        if ("carbohydrate".equals(food.getFoodtype())) {
            foodType.setText("高碳水");
        } else if ("protein".equals(food.getFoodtype())) {
            foodType.setText("高蛋白");
        } else if ("fat".equals(food.getFoodtype())) {
            foodType.setText("高脂肪");
        } else if ("fruit".equals(food.getFoodtype())) {
            foodType.setText("富含维生素");
        } else if ("other".equals(food.getFoodtype())) {
            foodType.setText("无");
        }
        if ("recommend".equals(food.getFoodsuggest())) {
            foodSuggest.setText("推荐");
            foodSuggest.setTextColor(Color.parseColor("#00a483"));
        } else if ("appropriate".equals(food.getFoodsuggest())) {
            foodSuggest.setText("可适量");
            foodSuggest.setTextColor(Color.parseColor("#d1ab57"));
        } else if ("few".equals(food.getFoodsuggest())) {
            foodSuggest.setText("需少量");
            foodSuggest.setTextColor(Color.parseColor("#ff4a57"));
        }
        foodDescrip.setText(food.getFooddescription());
        Glide.with(FoodDescripActivity.this).load(HttpAddress.urlAddress + food.getFoodImag())
                .into(imageView);
    }

}
