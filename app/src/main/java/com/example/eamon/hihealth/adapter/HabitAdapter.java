package com.example.eamon.hihealth.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.activity.HabitInfoActivity;
import com.example.eamon.hihealth.db.Habit;
import com.example.eamon.hihealth.db.HabitLog;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.HabitLogMessage;
import com.example.eamon.hihealth.util.DateFormatUtil;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.google.gson.Gson;
import com.suke.widget.SwitchButton;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Request;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/5/19.
 */

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.ViewHolder> {

    private Context mContext;

    private List<Habit> mHabitList;

    private SharedPreferences pref;

    private boolean isStampe;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        TextView habitName;

        TextView habitDayValue;

        SwitchButton habitMark;

        public ViewHolder(View view) {
            super(view);

            cardView = (CardView) view;

            habitName = (TextView) view.findViewById(R.id.habit_name_view);

            habitDayValue = (TextView) view.findViewById(R.id.keepDay_view);

            habitMark = (SwitchButton) view.findViewById(R.id.switch_button);
        }

    }

    public HabitAdapter(List<Habit> habitList) {
        mHabitList = habitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.habit_item,
                parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Habit habit = mHabitList.get(position);
                Intent intent = new Intent(mContext, HabitInfoActivity.class);
                intent.putExtra("habitinfo", habit);
                mContext.startActivity(intent);
            }
        });
        holder.habitMark.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                Gson gson = new Gson();
                HabitLog habitLog = new HabitLog();
                int position = holder.getAdapterPosition();
                Habit habit = mHabitList.get(position);
                habitLog.setUserInfo((gson.fromJson(pref.getString("userinfo", ""), UserInfo.class)));
                habitLog.setHabit(habit);
                try{
                    habitLog.setHabitstampetime(DateFormatUtil.stringToDate(
                            new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (isChecked) {
                    habitLog.setHabitstampestate("finished");
                    isStampe = true;
                } else {
                    habitLog.setHabitstampestate("unfinished");
                    isStampe = false;
                }
                postCreateHabitLog(habitLog);
                notifyItemChanged(position,1);
                Toast.makeText(mContext, String.valueOf(isChecked), Toast.LENGTH_LONG).show();
            }
        });
        return holder;
    }

    private void postCreateHabitLog(HabitLog habitLog) {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/habit/habitLogCreate", habitLog,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(mContext, "连接错误", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        Gson gson = new Gson();
                        HabitLogMessage habitLogMessage = gson.fromJson(result,HabitLogMessage.class);
                        if ("success".equals(habitLogMessage.responseMessage.result)) {
                            Toast.makeText(mContext, habitLogMessage.responseMessage.message, Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
    private void postFindHabitLog(HabitLog habitLog, final ViewHolder holder) {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/habit/habitInitInfo", habitLog,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(mContext, "连接错误", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        Gson gson = new Gson();
                        HabitLogMessage habitLogMessage = gson.fromJson(result, HabitLogMessage.class);
                        if ("success".equals(habitLogMessage.responseMessage.result)) {
                            holder.habitDayValue.setText("已坚持 " + habitLogMessage.habitLogList.size() + " 天");
                            holder.habitMark.setChecked(habitLogMessage.stampe);
                        } else if ("fail".equals(habitLogMessage.responseMessage.result)) {
                            holder.habitDayValue.setText("已坚持 0 天");
                            holder.habitMark.setChecked(false);
                        }
                    }
                });
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Gson gson = new Gson();
        Habit habit = mHabitList.get(position);
        HabitLog habitLog = new HabitLog();
        habitLog.setHabit(habit);
        try{
            habitLog.setHabitstampetime(DateFormatUtil.stringToDate(
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        habitLog.setUserInfo(gson.fromJson(pref.getString("userinfo", ""), UserInfo.class));
        holder.habitName.setText(habit.getHabitname());
        postFindHabitLog(habitLog, holder);
    }

    @Override
    public int getItemCount() {
        return mHabitList.size();
    }
}
