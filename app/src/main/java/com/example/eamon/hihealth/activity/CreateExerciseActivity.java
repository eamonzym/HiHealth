package com.example.eamon.hihealth.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.Exercise;
import com.example.eamon.hihealth.db.ExerciseLog;
import com.example.eamon.hihealth.db.UserInfo;
import com.example.eamon.hihealth.gson.ExerciseLogMessage;
import com.example.eamon.hihealth.util.DateFormatUtil;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.google.gson.Gson;
import com.shawnlin.numberpicker.NumberPicker;

import java.io.IOException;

import okhttp3.Request;

public class CreateExerciseActivity extends AppCompatActivity {

    private TextView exerciseName;

    private TextView exerciseCal;

    private TextView exerciseTime;

    private TextView exerciseSuggest;

    private TextView calValue;

    private TextView timeValue;

    private NumberPicker numberPicker;

    private Exercise exercise;

    private ExerciseLog exerciseLog;

    private SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercise);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        exerciseName = (TextView) findViewById(R.id.exercise_name);
        exerciseCal = (TextView) findViewById(R.id.exercise_cal);
        exerciseTime = (TextView) findViewById(R.id.exercise_time);
        exerciseSuggest = (TextView) findViewById(R.id.exercise_suggest);
        calValue = (TextView) findViewById(R.id.cal_value);
        timeValue = (TextView) findViewById(R.id.time_value);
        numberPicker = (NumberPicker) findViewById(R.id.number_picker);
        exercise = (Exercise) getIntent().getSerializableExtra("exercise");
        calValue.setText(exercise.getExercisecalorie().toString() + "千卡");
        timeValue.setText(exercise.getExerciseeffecttime().toString() + "分钟");
        exerciseName.setText(exercise.getExercisename());
        exerciseCal.setText(exercise.getExercisecalorie().toString() + "千卡");
        exerciseTime.setText(" / " + exercise.getExerciseeffecttime().toString() + "分钟");
        exerciseSuggest.setText("运动小提示： " + exercise.getExercisesuggest());
        numberPicker.setValue(exercise.getExerciseeffecttime().intValue());
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                timeValue.setText(newVal + "分钟");
                calValue.setText(String.valueOf(Math.round(newVal / exercise.getExerciseeffecttime().doubleValue() *
                        exercise.getExercisecalorie().doubleValue())) + "千卡");
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back_view:
                finish();
                break;
            case R.id.next_btn:
                Toast.makeText(CreateExerciseActivity.this, "6666666" , Toast.LENGTH_LONG).show();

                Gson gson = new Gson();
                exerciseLog = new ExerciseLog();
                try{
                    exerciseLog.setExerciselogtime(DateFormatUtil.stringToDate(DateFormatUtil.getDateNow()));

                }catch (Exception e) {
                    e.printStackTrace();
                }
                exerciseLog.setUserInfo(gson.fromJson(pref.getString("userinfo", ""), UserInfo.class));
                exerciseLog.setExercise(exercise);
                String[] timeStr = timeValue.getText().toString().split("[分钟]");
                exerciseLog.setExerciseduration(Double.parseDouble(timeStr[0]));
                String[] calStr = calValue.getText().toString().split("[千卡]");
                exerciseLog.setExercisetotalcalorie(Double.parseDouble(calStr[0]));
                postCreateExercise(exerciseLog);
                break;
        }
    }

    private void postCreateExercise(ExerciseLog exercise) {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/exercise/create", exercise,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(CreateExerciseActivity.this, "连接出错" , Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        Gson gson = new Gson();
                        ExerciseLogMessage exerciseLogMessage = gson.fromJson(result, ExerciseLogMessage.class);
                        if ("success".equals(exerciseLogMessage.responseMessage.result)) {
                            Toast.makeText(CreateExerciseActivity.this, exerciseLogMessage.responseMessage.message , Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(CreateExerciseActivity.this, ExerciseActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}
