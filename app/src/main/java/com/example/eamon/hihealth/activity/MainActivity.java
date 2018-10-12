package com.example.eamon.hihealth.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.HomeMessage;
import com.example.eamon.hihealth.util.BaseActivity;
import com.example.eamon.hihealth.util.DateFormatUtil;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.example.eamon.hihealth.util.LineGraphicView;
import com.google.gson.Gson;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Request;

public class MainActivity extends BaseActivity {


//    @Bind(R.id.health_report)
//    TextView _healthReport;
//
//    @Bind(R.id.user_name)
//    TextView _userName;
//
//    @Bind(R.id.step)
//    TextView _step;
//
//    @Bind(R.id.testdata)
//    TextView _test;
//

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    Handler exitHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };


    private View userInfoView;
    private View stepView;
    private View healthView;
    private View todayRecommend;
    private View exercise;
    private ImageView bingPicImg;
    private LineGraphicView lineGraphicView1, lineGraphicView2;
    private ArrayList yList1, yList2, yList3, yList4;
    private ArrayList xRawDatas;


    private TextView userNameValue;
    private TextView reportValue;
    private TextView exerciseTimeValue;
    private TextView stepValue;
    private TextView nowTimeValue;



    private ResideMenu resideMenu;

    private ResideMenuItem targetItem;
    private ResideMenuItem habitItem;
    private ResideMenuItem diectItem;
    private ResideMenuItem defItem;
    private ResideMenuItem exitItem;

    private static final String TAG = "MainActivity";

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private Gson gson = new Gson();


//    private StepService mService;
//
//    private boolean mIsRunning;
//
//    private SharedPreferences mySharedPreferences;

//    private Handler mHandler = new Handler(){
//        @Override
//        public void handleMessage(ResponseMessage msg) {
//            if (msg.what == 1) {
//                _step.setText(mySharedPreferences.getString("steps", "0"));
//                Log.d("TAG", "step is " + mySharedPreferences.getString("steps", "0"));
//                SimpleDateFormat CurrentTime = new SimpleDateFormat("HH:mm:ss");
//                SimpleDateFormat CurrentDay = new SimpleDateFormat("yyyy年MM月dd日");
//                Date date = new Date(System.currentTimeMillis());
//
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(date);
//                calendar.add(Calendar.DAY_OF_MONTH, -1);
//
//                Log.d("TAG", "step is " + CurrentDay.format(calendar.getTime()));
//                if ("00:00:00".equals(CurrentTime.format(date))) {
//                    try {
//                    StepsLog stepsLog = new StepsLog();
//                    stepsLog.setId(userInfo.getId());
//                    stepsLog.setStepsLog_Value(Integer.parseInt(mySharedPreferences.getString("steps", "0")));
//                    stepsLog.setStepsLog_Time(CurrentDay.parse(CurrentDay.format(calendar.getTime())));
//                    stepsLog.save();
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//            }
//        }
//    };


    private UserInfo userInfo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        initResideMenu();
        userInfo = gson.fromJson(pref.getString("userinfo", ""), UserInfo.class);
        try{
            userInfo.setNowtime(DateFormatUtil.stringToDate(DateFormatUtil.getDateNow()));

        }catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG,"username json:"+ userInfo.getUsername());
        Log.d(TAG,"userinfo:"+ pref.getString("userinfo", ""));
        // 绑定界面元素
        nowTimeValue = (TextView) findViewById(R.id.nowtime_value);
        nowTimeValue.setText(DateFormatUtil.dateChangeUtil(new Date()));
        exercise = findViewById(R.id.exercise_view);
        todayRecommend = findViewById(R.id.today_recommend);
        stepView = findViewById(R.id.step_view);
        healthView = findViewById(R.id.health_view);
        userInfoView = findViewById(R.id.user_info_view);
        userNameValue = findViewById(R.id.user_name);
        reportValue = (TextView) findViewById(R.id.report_value);
        exerciseTimeValue = (TextView) findViewById(R.id.exercisetime_value);
        bingPicImg = (ImageView) findViewById(R.id.bing_pic_img);
        postHomeData();

        // 设置点击事件监听
        userInfoView.setOnClickListener(onClickListener);
        stepView.setOnClickListener(onClickListener);
        healthView.setOnClickListener(onClickListener);
        todayRecommend.setOnClickListener(onClickListener);
        exercise.setOnClickListener(onClickListener);


        // 画图设置
        lineGraphicView1 = (LineGraphicView) findViewById(R.id.line_view1);
        lineGraphicView2 = (LineGraphicView) findViewById(R.id.line_view2);

        // 设置画图颜色
        lineGraphicView1.setLineColor(this.getResources().getColor(R.color.quxian_ju));
        lineGraphicView2.setLineColor(this.getResources().getColor(R.color.quxian_lan));

        // ??
        ArrayList yList1 = new ArrayList<Double>();
        yList1.add((double) 2.103);
        yList1.add(4.05);
        yList1.add(6.60);
        yList1.add(3.08);
        yList1.add(4.32);
        yList1.add(2.0);
        yList1.add(5.0);

        ArrayList yList2 = new ArrayList<Double>();
        yList2.add(1.01);
        yList2.add(7.9);
        yList2.add(1.01);
        yList2.add(7.9);
        yList2.add(1.01);
        yList2.add(7.9);
        yList2.add(1.01);
        yList2.add(7.9);
        yList2.add(1.01);


        // ??
        ArrayList<String> xRawDatas = new ArrayList<String>();
        xRawDatas.add("05-19");
        xRawDatas.add("05-20");
        xRawDatas.add("05-21");
        xRawDatas.add("05-22");
        xRawDatas.add("05-23");
        xRawDatas.add("05-24");
        xRawDatas.add("05-25");
        xRawDatas.add("05-26");
        lineGraphicView1.setData(yList1, xRawDatas, 8, 2);
        lineGraphicView2.setData(yList2, xRawDatas, 8, 2);




//        startStepService();
    }

    /**
     * 初始化每日一图
     */
    private void postHomeData() {
        HttpManager.getAsync("http://guolin.tech/api/bing_pic", new HttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Glide.with(MainActivity.this).load(result).into(bingPicImg);
            }
        });
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/users/home", userInfo,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(MainActivity.this, "连接错误", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        Gson gson = new Gson();
                        HomeMessage homeMessage = gson.fromJson(result, HomeMessage.class);
                        if ("success".equals(homeMessage.responseMessage.result)) {
                            Toast.makeText(MainActivity.this, homeMessage.responseMessage.message, Toast.LENGTH_SHORT).show();
                            userNameValue.setText(homeMessage.userName);
                            reportValue.setText(homeMessage.report);
                            exerciseTimeValue.setText(homeMessage.exerciseTime);
                        }
                    }
                });
    }

    /**
     *  初始化菜单
     */
    private void initResideMenu() {
        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.color.quxian_lan);
        resideMenu.attachToActivity(this);
        //禁止右边菜单
//        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
//        // create menu items;
        String titles[] = { "健康目标", "健康习惯", "饮食记录", "排便记录", "退出账号" };
        int icon[] = { R.drawable.main_menu_target, R.drawable.main_menu_habit,
                R.drawable.main_menu_diect, R.drawable.main_menu_def, R.drawable.main_menu_target };

        // 健康目标
            targetItem = new ResideMenuItem(this, icon[0], titles[0]);
            targetItem.setOnClickListener(onClickListener);
            resideMenu.addMenuItem(targetItem,  ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
        // 健康习惯
            habitItem = new ResideMenuItem(this, icon[1], titles[1]);
            habitItem.setOnClickListener(onClickListener);
            resideMenu.addMenuItem(habitItem,  ResideMenu.DIRECTION_LEFT);
        // 饮食记录
            diectItem = new ResideMenuItem(this, icon[2], titles[2]);
            diectItem .setOnClickListener(onClickListener);
            resideMenu.addMenuItem(diectItem ,  ResideMenu.DIRECTION_LEFT);
        // 排便记录
            defItem = new ResideMenuItem(this, icon[3], titles[3]);
            defItem .setOnClickListener(onClickListener);
            resideMenu.addMenuItem(defItem ,  ResideMenu.DIRECTION_LEFT);
        // 退出当前账号
            exitItem = new ResideMenuItem(this, icon[4], titles[4]);
            exitItem .setOnClickListener(onClickListener);
            resideMenu.addMenuItem(exitItem ,  ResideMenu.DIRECTION_RIGHT);

    }

    // 筛选点击事件
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == targetItem) {
                Toast.makeText(MainActivity.this, "健康目标", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, TargetActivity.class);
                startActivity(intent);
            }
            if (view == habitItem) {
                Toast.makeText(MainActivity.this, "健康习惯", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, HabitActivity.class);
                startActivity(intent);
            }
            if (view == diectItem) {
                Toast.makeText(MainActivity.this, "饮食记录", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, FoodActivity.class);
                startActivity(intent);
            }
            if (view == defItem) {
                Toast.makeText(MainActivity.this, "排便记录", Toast.LENGTH_SHORT).show();
            }
            if (view == exitItem) {
                Toast.makeText(MainActivity.this, "退出当前账号", Toast.LENGTH_SHORT).show();
                editor = pref.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
            switch (view.getId()) {
                case R.id.exercise_view:
                    Intent intent1 = new Intent(MainActivity.this, ExerciseActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.today_recommend:
                    Intent intent2 = new Intent(MainActivity.this, TodayRecommendActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.user_info_view:
                    Intent intent3 = new Intent(MainActivity.this, PersonInfoActivity.class);
                    startActivity(intent3);
                    break;
                case R.id.step_view:
//                    startActivity(new Intent(MainActivity.this, YunDongActivity.class));
                    break;
                case R.id.health_view:
                    Intent intent4 = new Intent(MainActivity.this, HealthReportActivity.class);
                    startActivity(intent4);
                    break;
            }
        }
    };

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            exit();
//            return false;
//        }
//        else {
//            return super.onKeyDown(keyCode, event);
//        }
//    }

//    private void exit() {
//        if (!isExit) {
//            isExit = true;
//            editor = pref.edit();
//            editor.clear();
//            editor.apply();
//            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//            // 利用 handler延迟发送更改状态信息
//            exitHandler.sendEmptyMessageDelayed(0, 2000);
//        } else {
//            ActivityCollector.finishAll();
//        }
//    }

    //    @Override
//    protected void onDestroy() {
//        super.onDestroy();
////        stopStepService();
//    }
//
//    @Override
//    protected void onPause() {
//        unbindStepService();
//        super.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        _step.setText(mySharedPreferences.getString("steps", "0"));
//        if (this.mIsRunning) {
//            bindStepService();
//        }
//    }
//
//    private UpdateUiCallBack mUiCallback = new UpdateUiCallBack() {
//        @Override
//        public void updateUi() {
//            ResponseMessage responseMessage = mHandler.obtainMessage();
//            responseMessage.what = 1;
//            mHandler.sendMessage(responseMessage);
//        }
//    };
//
//    private ServiceConnection mConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            StepService.StepBinder binder = (StepService.StepBinder) service;
//            mService = binder.getService();
//            mService.registerCallback(mUiCallback);
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//
//        }
//    };
//
//    private void bindStepService() {
//        bindService(new Intent(this, StepService.class), this.mConnection, Context.BIND_AUTO_CREATE);
//
//    }
//
//    private void unbindStepService() {
//        unbindService(this.mConnection);
//    }
//
//    private void startStepService() {
//        this.mIsRunning = true;
//        startService(new Intent(this, StepService.class));
//
//    }
//
//    private void stopStepService() {
//        this.mIsRunning = false;
//        if (this.mService != null)
//            stopService(new Intent(this, StepService.class));
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
