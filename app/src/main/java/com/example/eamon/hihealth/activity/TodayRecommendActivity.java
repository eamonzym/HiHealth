package com.example.eamon.hihealth.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.util.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.weather.Weather;
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.LifestyleBase;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class TodayRecommendActivity extends BaseActivity{


    private ProgressDialog progressDialog;

    public LocationClient mLocationClient;
    

    private TextView cityValue;

    private TextView condValue;

    private TextView tmpValue;

    private TextView sportValue;

    private TextView fluValue;

    private TextView drsgValue;

    private TextView comfValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_recommend);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        setContentView(R.layout.activity_today_recommend);
        cityValue = (TextView) findViewById(R.id.city_Value);
        condValue = (TextView) findViewById(R.id.cond_Value);
        tmpValue = (TextView) findViewById(R.id.tmp_value);
        sportValue = (TextView) findViewById(R.id.sport_Value);
        fluValue = (TextView) findViewById(R.id.flu_Value);
        drsgValue = (TextView) findViewById(R.id.drsg_Value);
        comfValue = (TextView) findViewById(R.id.comf_Value);
        List<String> permissionList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(TodayRecommendActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(TodayRecommendActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (ContextCompat.checkSelfPermission(TodayRecommendActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!permissionList.isEmpty()) {
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(TodayRecommendActivity.this,
                    permissions, 1);
        } else {
            progressDialog = ProgressDialog.show(TodayRecommendActivity.this, "今日推荐" , "加载中，请稍后...");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    requestLocation();
                }
            }).start();

        }
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option =  new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this,"必须同意所有权限才能使用本程序",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation location) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    postWeather(location);
                }
            });
        }

    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back_view:
                Intent intent = new Intent(TodayRecommendActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
    private void postWeather(final BDLocation location) {
        HeConfig.init("HE1805291638381849","f50c2be47ce4474d87544b29455c8408");
        HeConfig.switchToFreeServerNode();
        HeWeather.getWeather(this, location.getLongitude() + "," + location.getLatitude(), Lang.CHINESE_SIMPLIFIED, Unit.METRIC,
                new HeWeather.OnResultWeatherDataListBeansListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        Toast.makeText(TodayRecommendActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
                        Log.i("Log", "onError: ", throwable);
                    }

                    @Override
                    public void onSuccess(List<Weather> list) {
                        progressDialog.dismiss();
                        Toast.makeText(TodayRecommendActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                        for (Weather weather : list) {
                             cityValue.setText(weather.getBasic().getParent_city() + "市");
                             tmpValue.setText(weather.getNow().getTmp() + "°");
                             condValue.setText(weather.getNow().getCond_txt());
                            for (LifestyleBase lifestyle : weather.getLifestyle()) {
                                if ("sport".equals(lifestyle.getType())) {
                                    sportValue.setText("运动建议：" + lifestyle.getTxt());
                                } else if ("flu".equals(lifestyle.getType())) {
                                    fluValue.setText("感冒预防：" + lifestyle.getTxt());
                                } else if ("drsg".equals(lifestyle.getType())) {
                                    drsgValue.setText("穿衣建议：" + lifestyle.getTxt());
                                } else if ("comf".equals(lifestyle.getType())) {
                                    comfValue.setText("舒适度：" + lifestyle.getTxt());
                                }
                            }
                        }
                    }
                });
    }

}
