package com.example.eamon.hihealth.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.HealthReport;
import com.example.eamon.hihealth.db.UserInfo;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.health_report)
    TextView _healthReport;


    private static final String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;

    private UserInfo userInfo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        userInfo = (UserInfo) getIntent().getSerializableExtra("user_data");
        TextView textView = (TextView) findViewById(R.id.user_name);
        TextView healthIndex = (TextView) findViewById(R.id.health_report);
        Log.d("TAG", "news id is " + userInfo.getUser_name());
        Log.d("TAG", "news id is " + userInfo.getUser_weight());
        Log.d("TAG", "news id is " + userInfo.getUser_target());
        textView.setText(userInfo.getUser_name());

        List<HealthReport> healthReportList = DataSupport
                .where("userinfo_id = ?",String.valueOf(userInfo.getId()))
                .find(HealthReport.class);
        for (HealthReport healthReport : healthReportList) {
            healthIndex.setText(String.valueOf(healthReport.getReport_Scroe()));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CardView cardView =(CardView) findViewById(R.id.reort_index);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HealthReportActivity.class);
                intent.putExtra("user_data",userInfo);
                startActivity(intent);
            }
        });
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }



        Log.d("TAG", "news id is " + userInfo.getUser_password());
//        XGPushConfig.enableDebug(this,true);
//        XGPushManager.registerPush(this, new XGIOperateCallback() {
//            @Override
//            public void onSuccess(Object data, int flag) {
////token在设备卸载重装的时候有可能会变
//                Log.d("TPush", "注册成功，设备token为：" + data);
//            }
//            @Override
//            public void onFail(Object data, int errCode, String msg) {
//                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
//            }
//        });
        SQLiteDatabase db = Connector.getDatabase();
//        Stetho.initializeWithDefaults(this);
//        new OkHttpClient.Builder()
//                .addNetworkInterceptor(new StethoInterceptor())
//                .build();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return  true;
    }
}
