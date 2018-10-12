package com.example.eamon.hihealth.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.activity.FoodLogInfoActivity;
import com.example.eamon.hihealth.db.DietLog;
import com.example.eamon.hihealth.util.DateFormatUtil;

import java.util.Date;
import java.util.List;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/6/4.
 */

public class DietAllLogAdapter extends RecyclerView.Adapter<DietAllLogAdapter.ViewHolder> {

    private Context mContext;

    private List<Date> mDietLogList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;

        TextView timeTitle;

        public ViewHolder(View view) {
            super(view);
            linearLayout = (LinearLayout) view;
            timeTitle = (TextView) view.findViewById(R.id.foodtime_title);
        }
    }

    public DietAllLogAdapter(List<Date> dietLogList) {
        mDietLogList = dietLogList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.foodlog_item,
                parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Date logtime = mDietLogList.get(position);
                DietLog dietLog =new DietLog();
                dietLog.setDietlogtime(logtime);
                Intent intent = new Intent(mContext, FoodLogInfoActivity.class);
                intent.putExtra("foodlog", dietLog);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Date logtime = mDietLogList.get(position);
        holder.timeTitle.setText(DateFormatUtil.dateChangeUtil(logtime));
    }

    @Override
    public int getItemCount() {
        return mDietLogList.size();
    }
}
