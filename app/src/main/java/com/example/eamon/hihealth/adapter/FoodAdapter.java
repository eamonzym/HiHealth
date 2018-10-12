package com.example.eamon.hihealth.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.activity.CreateDietActivity;
import com.example.eamon.hihealth.activity.FoodDescripActivity;
import com.example.eamon.hihealth.db.Food;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/5/21.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private Context mContext;

    private List<Food> mFoodList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        SwipeMenuLayout swipeMenuLayout;

        RelativeLayout relativeLayout;

        TextView foodName;

        TextView foodCal;

        TextView foodSuggest;

        Button btnBreakfast;

        Button btnLunch;

        Button btnDinner;

        public ViewHolder(View view) {
            super(view);
            swipeMenuLayout = (SwipeMenuLayout) view;
            relativeLayout = (RelativeLayout) view.findViewById(R.id.layout_content);
            foodName = (TextView) view.findViewById(R.id.food_name);
            foodCal = (TextView) view.findViewById(R.id.food_cal);
            foodSuggest = (TextView) view.findViewById(R.id.food_suggest);
            btnBreakfast = (Button) view.findViewById(R.id.btnBreakfast);
            btnLunch = (Button) view.findViewById(R.id.btnLunch);
            btnDinner = (Button) view.findViewById(R.id.btnDinner);

        }
    }

    public FoodAdapter(List<Food> foodList) {
        mFoodList = foodList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.foodinfo_item,
                parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Food food = mFoodList.get(position);
                Intent intent = new Intent(mContext, FoodDescripActivity.class);
                intent.putExtra("foodinfo", food);
                mContext.startActivity(intent);
            }
        });
        holder.btnBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Food food = mFoodList.get(position);
                Intent intent = new Intent(mContext, CreateDietActivity.class);
                intent.putExtra("foodinfo", food);
                intent.putExtra("diettype", "breakfast");
                mContext.startActivity(intent);
            }
        });
        holder.btnLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Food food = mFoodList.get(position);
                Intent intent = new Intent(mContext, CreateDietActivity.class);
                intent.putExtra("foodinfo", food);
                intent.putExtra("diettype", "lunch");
                mContext.startActivity(intent);
            }
        });
        holder.btnDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Food food = mFoodList.get(position);
                Intent intent = new Intent(mContext, CreateDietActivity.class);
                intent.putExtra("foodinfo", food);
                intent.putExtra("diettype", "dinner");
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Food food = mFoodList.get(position);
        holder.foodName.setText(food.getFoodname());
        holder.foodCal.setText(String.valueOf(food.getFoodcalorie()) + "Kcal");
        if ("recommend".equals(food.getFoodsuggest())) {
            holder.foodSuggest.setText("推荐");
            holder.foodSuggest.setTextColor(Color.parseColor("#00a483"));
        } else if ("appropriate".equals(food.getFoodsuggest())) {
            holder.foodSuggest.setText("可适量");
            holder.foodSuggest.setTextColor(Color.parseColor("#d1ab57"));
        } else if ("few".equals(food.getFoodsuggest())) {
            holder.foodSuggest.setText("需少量");
            holder.foodSuggest.setTextColor(Color.parseColor("#ff4a57"));
        }

    }

    @Override
    public int getItemCount() {
        return mFoodList.size();
    }
}
