package com.example.eamon.hihealth.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.fragment.TargetFirstFragment;
import com.example.eamon.hihealth.fragment.TargetLogFragment;
import com.example.eamon.hihealth.util.BaseActivity;

public class TargetActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{

    private static final String TAG = "TargetActivity";

    private BottomNavigationBar bottomNavigationBar;

    private Fragment currentFragment = new Fragment();

    private SharedPreferences pref;


    private LinearLayout left_back_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        left_back_view = (LinearLayout) findViewById(R.id.left_back_view);
        left_back_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TargetActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        Log.d(TAG,String.valueOf(pref.getBoolean("logFragment", false)));
        if (pref.getBoolean("logFragment", false)) {
            switchFragment(new TargetLogFragment()).commit();
            initNavigationBar(1);

        } else {
            switchFragment(new TargetFirstFragment()).commit();
            initNavigationBar(0);
        }
    }


    private void initNavigationBar(int position) {
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.main_target, "目标"))
                .addItem(new BottomNavigationItem(R.drawable.target_log, "记录"))
                .setTabSelectedListener(this)
                .setFirstSelectedPosition(position)
                .initialise();
    }

    private FragmentTransaction switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.target_frame_layout, fragment, fragment.getClass().getName());

        } else {
            transaction
                    .hide(currentFragment)
                    .show(fragment);
        }
        currentFragment = fragment;
        return transaction;
    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                Toast.makeText(TargetActivity.this, "目标", Toast.LENGTH_SHORT).show();
                switchFragment(new TargetFirstFragment()).commit();
                break;
            case 1:
                Toast.makeText(TargetActivity.this, "记录", Toast.LENGTH_SHORT).show();
                switchFragment(new TargetLogFragment()).commit();

        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(TargetActivity.this, MainActivity.class);
            startActivity(intent);
            return false;
        }
        return super.onKeyDown(keyCode, event);

    }
}
