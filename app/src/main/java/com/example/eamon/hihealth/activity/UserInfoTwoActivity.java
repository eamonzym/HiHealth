package com.example.eamon.hihealth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.util.BaseActivity;
import com.example.eamon.hihealth.util.DateFormatUtil;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class UserInfoTwoActivity extends BaseActivity {

    private static final String TAG = "UserInfoTwoActivity";

    private static final int MAX_MONTH = 12;
    private LoopView loopViewYear,loopViewMonth,loopViewDay;
    private int startYear = 1918;
    private int endYear = 2018;
    private int startMonth = 1;
    private int endMonth = MAX_MONTH;
    private int startDay = 1;
    private int endDay = 31;
    private boolean isChangeMonth = false;

    private List<String> yearList = new ArrayList<>();
    private List<String> monthList = new ArrayList<>();
    private List<String> dayList = new ArrayList<>();
    private List<String> isMonthList = new ArrayList<String>();

    private String yearStr ;
    private String monthStr;
    private String dayStr;

    private TextView birthValue;

    private UserInfo userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_two);
        birthValue = findViewById(R.id.birth_value);
        loopViewYear = findViewById(R.id.loopView_year);
        loopViewMonth = findViewById(R.id.loopView_month);
        loopViewDay = findViewById(R.id.loopView_day);

        // 31天的月份
        isMonthList.add("01");
        isMonthList.add("03");
        isMonthList.add("05");
        isMonthList.add("07");
        isMonthList.add("08");
        isMonthList.add("10");
        isMonthList.add("12");

        for (int i = startYear; i <= endYear; i++) {
            yearList.add(i + "");
        }
        for (int i = startMonth; i <= endMonth; i++) {
            monthList.add(formatTimeUnit(i) + "");
        }
        for (int i = startDay; i <= endDay; i++) {
            dayList.add(formatTimeUnit(i) + "");
        }

        // 分割时间戳数据
        String[] birthStr = birthValue.getText().toString().split("[-]");
        yearStr = birthStr[0];
        monthStr = birthStr[1];
        dayStr = birthStr[2];

        //设置原始数据
        loopViewYear.setItems(yearList);
        loopViewMonth.setItems(monthList);
        loopViewDay.setItems(dayList);

        //设置初始位置
        loopViewYear.setInitPosition(Integer.parseInt(yearStr) - startYear);
        loopViewMonth.setInitPosition(Integer.parseInt(monthStr) - 1);
        loopViewDay.setInitPosition(Integer.parseInt(dayStr) - 1);

        //滚动监听
        loopViewYear.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                yearStr = yearList.get(index);
                birthValue.setText(yearStr + "-" + monthStr + "-" + dayStr);
            }
        });
        //滚动监听
        loopViewMonth.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                monthStr = monthList.get(index);
                // 判断是否是31的月份
                    if (!isMonthList.contains(monthStr)) {
                        Log.d(TAG, "month value is " + monthStr);
                        isChangeMonth = true;
                        } else {
                        isChangeMonth = false;
                    }
                    if (isChangeMonth) {
                        dayList.clear();
                        for (int i = startDay; i <= endDay - 1; i++) {
                            dayList.add(formatTimeUnit(i) + "");
                        }
                        loopViewDay.setItems(dayList);
                } else {
                        dayList.clear();
                        for (int i = startDay; i <= endDay; i++) {
                            dayList.add(formatTimeUnit(i) + "");
                        }
                        loopViewDay.setItems(dayList);

                    }
                Log.d(TAG, "(month)day value is " + loopViewDay.getSelectedItem());
                birthValue.setText(yearStr + "-" + monthStr + "-" + dayStr);
            }
        });
        //滚动监听
        loopViewDay.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                Log.d(TAG, "(day)day value is " + loopViewDay.getSelectedItem());
                dayStr = dayList.get(index);
                birthValue.setText(yearStr + "-" + monthStr + "-" + dayStr);
            }
        });


    }

    public UserInfo setUserInfo () {
        userInfo = (UserInfo) getIntent().getSerializableExtra("user_data");
        try {
            userInfo.setUserbirth(DateFormatUtil.stringToDate(birthValue.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    /**
     * 将“0-9”转换为“00-09”
     */
    private String formatTimeUnit(int unit) {
        return unit < 10 ? "0" + String.valueOf(unit) : String.valueOf(unit);
    }

    public void onClick (View view) {
        switch (view.getId()) {
            case R.id.left_back_view:
                finish();
                break;
            case R.id.two_next_btn:
                UserInfo userInfo_two = setUserInfo();
                Intent intent = new Intent(UserInfoTwoActivity.this, UserInfoThreeActivity.class);
                intent.putExtra("user_data", userInfo_two);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
