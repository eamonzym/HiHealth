package com.example.eamon.hihealth.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.Habit;
import com.example.eamon.hihealth.db.HabitLog;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.HabitLogMessage;
import com.example.eamon.hihealth.util.Action;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.google.gson.Gson;
import com.sch.calendar.CalendarView;
import com.sch.calendar.adapter.VagueAdapter;
import com.sch.calendar.annotation.DayOfMonth;
import com.sch.calendar.annotation.Month;
import com.sch.calendar.entity.Date;
import com.sch.calendar.util.DateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class HabitInfoActivity extends AppCompatActivity {

    private TextView habitName;

    private TextView allDayValue;

    private TextView keepDayValue;

    private Habit habit;

    private CalendarView mCalendarView;

    private final String MONTH_FORMAT = "yyyy-MM";

    private final String DAY_OF_MONTH_FORMAT = "yyyy-MM-dd";

    private VagueAdapter<Map<String, Map<String, Action>>> vagueAdapter;

    private List<java.util.Date> stampeDate = new ArrayList<>();

    private SharedPreferences pref;

    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_info);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        habit = (Habit) getIntent().getSerializableExtra("habitinfo");
        habit.setUsername((gson.fromJson(pref.getString("userinfo", ""), UserInfo.class)).getUsername());
        mCalendarView = (CalendarView) findViewById(R.id.calendar_view);
        allDayValue = (TextView) findViewById(R.id.all_day_view);
        keepDayValue = (TextView) findViewById(R.id.keep_day_view);
        habitName = (TextView) findViewById(R.id.habit_info_name);
        habitName.setText(habit.getHabitname());
        initCalendarView();
        postHabitLogInfo(habit);
    }

    private void postHabitLogInfo(final Habit habit) {

        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/habit/habitLogInfo", habit,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(HabitInfoActivity.this, "连接出错" , Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        int keepValue = 0;
                        HabitLogMessage habitLogMessage = gson.fromJson(result, HabitLogMessage.class);
                        Log.d("HabitInfoActivity","result data"+ result);
                        if ("success".equals(habitLogMessage.responseMessage.result)) {
                            Toast.makeText(HabitInfoActivity.this, habitLogMessage.responseMessage.message ,
                                    Toast.LENGTH_LONG).show();
                            for (HabitLog habitLog : habitLogMessage.habitLogList) {
//                                java.util.Date date = habitLog.getHabitstampetime();

                                stampeDate.add(habitLog.getHabitstampetime());
                                for (HabitLog habitLog1 : habitLogMessage.habitLogList) {
                                    if ((habitLog.getHabitstampetime().getTime() - habitLog1.getHabitstampetime().getTime())
                                           / (1000 * 60 * 60 * 24) == 1) {
                                        keepValue = keepValue + 1;
                                        break;
                                    }
                                }
                            }
                            keepDayValue.setText(keepValue + "");
                            allDayValue.setText(stampeDate.size() + "");
                            vagueAdapter.setData(createCheckinData());
                            vagueAdapter.notifyDataSetChanged();
                            Log.d("HabitInfoActivity","stampedate data "+ stampeDate);
                        } else if ("fail".equals(habitLogMessage.responseMessage.result)) {
                            Toast.makeText(HabitInfoActivity.this, habitLogMessage.responseMessage.message ,
                                    Toast.LENGTH_LONG).show();
                            stampeDate = new ArrayList<>();
                            allDayValue.setText(stampeDate.size() + "");
                            vagueAdapter.setData(createCheckinData());
                            vagueAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    private void initCalendarView() {
        // Create adapter
        vagueAdapter = new MyVagueAdapter(R.layout.layout_action_calendar_item);
        vagueAdapter.setData(new HashMap<String, Map<String, Action>>());
        mCalendarView.setVagueAdapter(vagueAdapter);
    }
    private Map<String, Map<String, Action>> createCheckinData() {

        Map<String, Map<String, Action>> checkinMap = new HashMap<>();
        Map<String, Action> monthCheckinMap = new HashMap<>();

        for (java.util.Date today : stampeDate) {
            Log.d("HabitInfoActivity","year data "+ today);
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(today);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            Log.d("HabitInfoActivity","year data "+ year);
            Log.d("HabitInfoActivity","month data "+ month);
            Log.d("HabitInfoActivity","day data "+ dayOfMonth);
            checkinMap.put(DateUtil.formatDate(year, month, dayOfMonth, MONTH_FORMAT), monthCheckinMap);
            monthCheckinMap.put(DateUtil.formatDate(year, month, dayOfMonth, DAY_OF_MONTH_FORMAT), new Action());

        }

//        for (int i = 1, N = today.getDayOfMonth() - 1; i < N; i++) {
//            if (i % 3 == 0) {
//                monthCheckinMap.put(DateUtil.formatDate(year, month, i + 1, DAY_OF_MONTH_FORMAT), new Action());
//            }
//        }
        return checkinMap;
    }

    private class MyVagueAdapter extends VagueAdapter<Map<String, Map<String, Action>>> {

        MyVagueAdapter(@LayoutRes int dayLayout) {
            super(dayLayout);
        }

        @Override
        public void onBindVague(View itemView, int year, @Month int month, @DayOfMonth int dayOfMonth) {
            ImageView ivActionFinished = itemView.findViewById(R.id.iv_action_finished);
            if (data == null) return;
            // Get the data of current month.
            Map<String, Action> monthMap = data.get(DateUtil.formatDate(year, month, dayOfMonth, MONTH_FORMAT));
            // No data of current month.
            if (monthMap == null) {
                ivActionFinished.setVisibility(View.GONE);
                return;
            }
            // Get the data of today.
            Action history = monthMap.get(DateUtil.formatDate(year, month, dayOfMonth, DAY_OF_MONTH_FORMAT));
            // Show action finished or not.
            ivActionFinished.setVisibility(history == null ? View.GONE : View.VISIBLE);
        }

        @Override
        public void flagToday(View todayView) {
            // Highlight today.
            TextView tvDayView = todayView.findViewById(R.id.tv_day_of_month);
            tvDayView.setTextColor(Color.WHITE);
            todayView.setBackgroundColor(getResources().getColor(R.color.blue_light));
        }

        @Override
        public void flagNotToday(View dayView, Date date) {
            // Reset the view of not today.
            TextView tvDayView = dayView.findViewById(R.id.tv_day_of_month);
            tvDayView.setBackgroundColor(Color.TRANSPARENT);
            tvDayView.setTextColor(getResources().getColor(R.color.contentTextHintColor));
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back_view:
                finish();
                break;
        }
    }
}
