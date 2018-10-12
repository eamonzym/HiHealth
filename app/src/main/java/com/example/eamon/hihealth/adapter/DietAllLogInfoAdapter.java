package com.example.eamon.hihealth.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.DietLog;
import com.example.eamon.hihealth.util.HttpAddress;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/6/4.
 */

public class DietAllLogInfoAdapter extends RecyclerView.Adapter<DietAllLogInfoAdapter.ViewHolder> {

    private Context mContext;

    private List<DietLog> mDietLogList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayout;

        CircleImageView foodCircleImage;

        TextView foodName;

        TextView foodQuantity;

        TextView foodAllCal;

        TextView foodType;

        public ViewHolder (View view) {
            super(view);
            relativeLayout = (RelativeLayout) view;
            relativeLayout = (RelativeLayout) view;
            foodCircleImage = (CircleImageView) view.findViewById(R.id.food_icon);
            foodName = (TextView) view.findViewById(R.id.food_name);
            foodQuantity = (TextView) view.findViewById(R.id.food_quantity);
            foodAllCal = (TextView) view.findViewById(R.id.food_allcal);
            foodType = (TextView) view.findViewById(R.id.food_type);
        }
    }

    public DietAllLogInfoAdapter(List<DietLog> dietLogList) {
        mDietLogList = dietLogList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.foodloginfo_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DietLog dietLog = mDietLogList.get(position);
        holder.foodName.setText(dietLog.getFood().getFoodname());
        Glide.with(mContext).load(HttpAddress.urlAddress + dietLog.getFood().getFoodImag())
                .into(holder.foodCircleImage);
        holder.foodAllCal.setText(dietLog.getDietcalorie().toString() + "千卡");
        holder.foodQuantity.setText(dietLog.getDietquantity().toString() + "克");
        if ("breakfast".equals(dietLog.getDiettype())) {
            holder.foodType.setText("早餐");
        } else if ("lunch".equals(dietLog.getDiettype())) {
            holder.foodType.setText("午餐");
        } else if ("dinner".equals(dietLog.getDiettype())) {
            holder.foodType.setText("晚餐");
        }
    }

    @Override
    public int getItemCount() {
        return mDietLogList.size();
    }
}
