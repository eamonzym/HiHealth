package com.example.eamon.hihealth.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.Target;
import com.example.eamon.hihealth.util.BaseActivity;
import com.example.eamon.hihealth.util.DateFormatUtil;

public class TargetInfoActivity extends BaseActivity {

    private CollapsingToolbarLayout collapsingToolbar;

    private TextView targetCreateTime;

    private TextView targetState;

    private TextView targetStartTime;

    private TextView finishTime;

    private TextView initialWeight;

    private TextView targetWeight;

    private TextView targetDayloss;

    private FloatingActionButton targetlogBtn;


    private Toolbar toolbar;

    private ActionBar actionBar;

    private Target target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_info);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbar = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);
        targetCreateTime = (TextView) findViewById(R.id.targetinfo_createtime);
        targetState = (TextView) findViewById(R.id.targetinfo_state);
        initialWeight = (TextView) findViewById(R.id.targetinfo_initweight);
        targetWeight = (TextView) findViewById(R.id.targetinfo_targetweight);
        targetDayloss = (TextView) findViewById(R.id.targetinfo_dayloss);
        finishTime = (TextView) findViewById(R.id.targetinfo_finishtime);
        targetStartTime = (TextView) findViewById(R.id.targetinfo_starttime);
        targetlogBtn = (FloatingActionButton) findViewById(R.id.targetlog_btn);
        targetlogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("未激活".equals(targetState.getText().toString())) {
                    Toast.makeText(TargetInfoActivity.this, "当前目标还未激活", Toast.LENGTH_LONG).show();

                } else if ("已放弃".equals(targetState.getText().toString())) {
                    Toast.makeText(TargetInfoActivity.this, "当前目标还未激活", Toast.LENGTH_LONG).show();
                }
                else if ("进行中".equals((targetState.getText().toString()))) {
                    Intent intent = new Intent(TargetInfoActivity.this, TargetLogActivity.class);
                    intent.putExtra("targetinfo_data", target);
                    startActivity(intent);
                }
            }
        });
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initTargetInfo();

    }

    private void initTargetInfo() {
        target = (Target) getIntent().getSerializableExtra("targetinfo_data");
        collapsingToolbar.setTitle(target.getTargetname());
        targetCreateTime.setText(DateFormatUtil.dateChangeUtil(target.getTargetcreatetime()));
        initialWeight.setText(String.valueOf(target.getInitialweight()) + "kg");
        targetWeight.setText(String.valueOf(target.getTargetweight()) + "kg");
        if ("ongoing".equals(target.getTargetstate())) {
            targetState.setText("进行中");
            targetState.setTextColor(Color.parseColor("#00a483"));
            finishTime.setText(DateFormatUtil.dateChangeUtil(target.getTargetfinishtime()));
            targetDayloss.setText(String.valueOf(target.getDaylossweight()));
            targetStartTime.setText(DateFormatUtil.dateChangeUtil(target.getTargetstarttime()));
        } else if ("disongoing".equals(target.getTargetstate())) {
            targetState.setText("未激活");
            targetState.setTextColor(Color.parseColor("#d1ab57"));
            finishTime.setText("未知");
            targetDayloss.setText("未知");
            targetStartTime.setText("未知");
        } else if ("abandon".equals(target.getTargetstate())) {
            targetState.setText("已放弃");
            targetState.setTextColor(Color.parseColor("#ff4a57"));
            finishTime.setText(DateFormatUtil.dateChangeUtil(target.getTargetfinishtime()));
            targetDayloss.setText(String.valueOf(target.getDaylossweight()));
            targetStartTime.setText(DateFormatUtil.dateChangeUtil(target.getTargetstarttime()));
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
