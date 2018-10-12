package com.example.eamon.hihealth.fragment;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.db.Target;
import com.example.eamon.hihealth.db.TargetLog;
import com.example.eamon.hihealth.gson.TargetLogMessage;
import com.example.eamon.hihealth.util.CustomXValueFormatter;
import com.example.eamon.hihealth.util.DateFormatUtil;
import com.example.eamon.hihealth.util.HttpAddress;
import com.example.eamon.hihealth.util.HttpManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class TargetLogFragment extends Fragment {


    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private TextView minValue;

    private TextView avgValue;

    private TextView maxValue;

    private Target target;
    private Gson gson = new Gson();
    private LineChart mChart;
    private View view;

    private ProgressDialog progressDialog;

    private ArrayList<Entry> weightValues = new ArrayList<>();
    private ArrayList<String> xVals = new ArrayList<>();

    public TargetLogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_target_log, container, false);
        mChart =view.findViewById(R.id.chart1);
        minValue = view.findViewById(R.id.minValue_view);
        avgValue = view.findViewById(R.id.avgValue_view);
        maxValue = view.findViewById(R.id.maxValue_view);
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        target = gson.fromJson(pref.getString("target", ""), Target.class);
        editor = pref.edit();
        editor.remove("logFragment");
        editor.apply();
        progressDialog = ProgressDialog.show(getActivity(), "目标记录", "加载中，请稍后...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postFindTargetLog(target);
            }
        }).start();

        // Inflate the layout for this fragment
        return view;
    }

    private void postFindTargetLog(final Target target) {
        HttpManager.postJSONAsync(HttpAddress.urlAddress + "/target/findlog", target,
                new HttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(getActivity(), "连接出错" , Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void requestSuccess(String result) throws Exception {
                        progressDialog.dismiss();
                        TargetLogMessage targetLogMessage = gson.fromJson(result, TargetLogMessage.class);
                        if ("success".equals(targetLogMessage.responseMessage.result)) {
                            Toast.makeText(getActivity(), targetLogMessage.responseMessage.message ,
                                    Toast.LENGTH_LONG).show();
                            for (TargetLog targetLog : targetLogMessage.targetLogs) {
                                Log.d("targetlogFragment","indexof"+ targetLogMessage.targetLogs.indexOf(targetLog));
                                Entry weight = new Entry(targetLogMessage.targetLogs.indexOf(targetLog),targetLog.getCurrentweight().floatValue());
                                weightValues.add(weight);
                                xVals.add(DateFormatUtil.dateChangeUtil(targetLog.getTargetlogtime()));
                            }
                            Log.d("targetlogFragment","xvals data "+ xVals);
                            Log.d("targetlogFragment","weightValues size is "+ weightValues.size());
                            initChart();
                            initData(weightValues);
                            minValue.setText(targetLogMessage.statisValue.min);
                            avgValue.setText(targetLogMessage.statisValue.avg);
                            maxValue.setText(targetLogMessage.statisValue.max);


                        } else if ("fail".equals(targetLogMessage.responseMessage.result)) {
                            Toast.makeText(getActivity(), targetLogMessage.responseMessage.message ,
                                    Toast.LENGTH_LONG).show();
                            weightValues = new ArrayList<>();
                            xVals = new ArrayList<>();
                            initChart();
                            initData(weightValues);
                            minValue.setText("未知");
                            avgValue.setText("未知");
                            maxValue.setText("未知");

                        }
                    }
                });
    }
    //设置chart基本属性
    private void initChart() {
//        // 设置描述文本不显示
//        mChart.getDescription().setEnabled(false);
//
//        mChart.setDrawBorders(false);
//        //设置chart边框线颜色
//        mChart.setBorderColor(Color.GRAY);
//        //设置chart边框线宽度
//        mChart.setBorderWidth(1f);
//        //设置chart是否可以触摸
//        mChart.setTouchEnabled(true);
//        //设置是否可以拖拽
//        mChart.setDragEnabled(true);
//        //设置是否可以缩放 x和y，默认true
//        mChart.setScaleEnabled(false);
//        //设置是否可以通过双击屏幕放大图表。默认是true
//        mChart.setDoubleTapToZoomEnabled(false);
//        // 一个界面显示多少个点，其他的点可以通过滑动看到
//        float ratio = (float) xVal.size() / (float) 5;
//        mChart.zoom(ratio, 1f , 0, 0);
//        //设置chart动画
//        mChart.animateXY(1000, 1000);

        // 取消描述文字
        mChart.getDescription().setEnabled(false);
        // 设置没有数据的时候显示的文字
        mChart.setNoDataText("没有数据");
        // 设置没有数据的时候显示文字的颜色
        mChart.setNoDataTextColor(getResources().getColor(R.color.quxian_lan));
        // 网格背景
        mChart.setDrawGridBackground(false);
        // 是否绘制图表边框的线
        mChart.setDrawBorders(false);
        //  边框线颜色
        mChart.setBorderColor(getResources().getColor(R.color.quxian_lan));
        // 边界线宽度  单位dp
        mChart.setBorderWidth(3f);
        // 能否点击
        mChart.setTouchEnabled(true);
        // 一个界面显示多少个点，其他的点可以通过滑动看到
        float ratio = (float) xVals.size() / (float) 5;
        mChart.zoom(ratio, 1f , 0, 0);
        // 能否拖拽
        mChart.setDragEnabled(true);
        // 能否缩放
        mChart.setScaleEnabled(false);
        // 绘制动画 从左到右
        mChart.animateX(1000);
        // 能否通过双击屏幕放大图表
        mChart.setDoubleTapToZoomEnabled(false);
        // 能否拖拽高亮线
        mChart.setHighlightPerDragEnabled(false);
        // 拖拽滚动时，手放开能否持续滚动
        mChart.setDragDecelerationEnabled(false);

        //设置 marker

//        MyMarkerView

        //设置X轴

        // 获取x轴线
        XAxis xAxis = mChart.getXAxis();
        // 是否绘制轴线
        xAxis.setDrawAxisLine(true);
        // 是否绘制x轴上每个点的线
        xAxis.setDrawGridLines(true);
        // 是否绘制X轴标签
        xAxis.setDrawLabels(true);
        // 设置x轴显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 设置文字大小
        xAxis.setTextSize(10f);
        // 设置x轴的最小值
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(xVals.size());
        // 设置X轴显示的个数
        xAxis.setLabelCount(xVals.size(), true);
        // 标签旋转
        xAxis.setLabelRotationAngle(20);
        // 设置图表将避免第一个和最后一个标签条目被剪掉在图表和屏幕的边缘
        xAxis.setAvoidFirstLastClipping(true);
        // 设置x轴线条颜色
        xAxis.setAxisLineColor(getResources().getColor(R.color.quxian_lan));
        // 设置x轴线宽度
//        xAxis.setAxisLineWidth(0.5f);
        // 设置适配
        xAxis.setValueFormatter(new CustomXValueFormatter(xVals));


        //设置Y轴线

        // 获取左边y轴
        YAxis leftAxis = mChart.getAxisLeft();
        // 获取右边Y轴
        YAxis axisRight = mChart.getAxisRight();
        // 设置Y轴网格线条的虚线
        // 实线长度， 虚线长度， 周期
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        // 设置网格线条间距
        leftAxis.setGranularity(20f);
        // 设置能否使用右边Y轴
        axisRight.setEnabled(false);
        // 设置能否使用左边Y轴
        leftAxis.setEnabled(true);
        // 设置网格线条的颜色
        leftAxis.setGridColor(getResources().getColor(R.color.quxian_lan));
        // 能否显示Y轴刻度
        leftAxis.setDrawLabels(true);
        // 设置显示标签个数
        leftAxis.setLabelCount(3, true);
        // 设置Y轴数值 从零开始
        leftAxis.setAxisMinimum(25);
        leftAxis.setAxisMaximum(200);
        // 能否使用Y轴网格线条
        leftAxis.setDrawGridLines(true);
        // 设置Y轴刻度字体大小
        leftAxis.setTextSize(12f);
        // 设置字体颜色
        leftAxis.setTextColor(getResources().getColor(R.color.quxian_lan));
        // 设置Y轴颜色
        leftAxis.setAxisLineColor(getResources().getColor(R.color.quxian_lan));
        // 设置Y轴线条宽度
        leftAxis.setAxisLineWidth(0.5f);
        // 是否绘制轴线
        leftAxis.setDrawGridLines(true);
        //
        leftAxis.setMinWidth(0f);
        leftAxis.setMaxWidth(200f);
        // 设置轴上每个点对应的线
        leftAxis.setDrawGridLines(false);

        // 图列
        Legend l =mChart.getLegend();
        // 设置是否使用图例
        l.setEnabled(false);



//        //=======================设置标准体重限制线===============//
//        LimitLine yLimitLine = new LimitLine(50f,"标准体重");
//        yLimitLine.setLineColor(Color.RED);
//        yLimitLine.setTextColor(Color.RED);
//
//        //=======================设置X轴显示效果==================
//        XAxis xAxis = mChart.getXAxis();
//        //是否启用X轴
//        xAxis.setEnabled(true);
//        //是否绘制X轴线
//        xAxis.setDrawAxisLine(true);
//        //设置X轴上每个竖线是否显示
//        xAxis.setDrawGridLines(false);
//        xAxis.setGranularityEnabled(true);
//        //设置是否绘制X轴上的对应值(标签)
//        xAxis.setDrawLabels(true);
//        xAxis.setAxisMinimum(0);
//        xAxis.setLabelCount(1,false);
//        //设置X轴显示位置
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTextSize(12);
//        xAxis.setTextColor(getResources().getColor(R.color.quxian_lan));
//        //格式化数据
////        xAxis.setValueFormatter(new CustomXValueFormatter(xVal));
//
//
//
//        //=================设置左边Y轴===============
//        YAxis axisLeft = mChart.getAxisLeft();
//        //是否启用左边Y轴
//        axisLeft.setEnabled(true);
//        //设置最小值（这里就按demo里固死的写）
//        axisLeft.setAxisMinimum(25);
//        axisLeft.setGranularityEnabled(true);
//        //设置最大值（这里就按demo里固死的写了）
////        axisLeft.setAxisMaximum(200);
//        //强制显Y轴3个坐标
//        axisLeft.setLabelCount(3,false);
//        //设置横向的线为虚线
//        axisLeft.enableGridDashedLine(10f, 10f, 0f);
//        //axisLeft.setDrawLimitLinesBehindData(true);
//        axisLeft.addLimitLine(yLimitLine);
//        //====================设置右边的Y轴===============
//        YAxis axisRight = mChart.getAxisRight();
//        //是否启用右边Y轴
//        axisRight.setEnabled(false);

    }

    //设置数据
    private void initData(ArrayList<Entry> weightValues) {

        if (weightValues.size() == 0) return;

        LineDataSet lineDataSet;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(weightValues);

            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            //点构成的某条线
            lineDataSet = new LineDataSet(weightValues, "运动量");
            //设置该线的颜色
            lineDataSet.setColor(getResources().getColor(R.color.quxian_lan));
            //设置每个点的颜色
            lineDataSet.setCircleColor(getResources().getColor(R.color.quxian_lan));
            lineDataSet.setCircleColorHole(Color.WHITE);
            //设置该线的宽度
            lineDataSet.setLineWidth(1f);
            //设置每个坐标点的圆大小
            lineDataSet.setCircleRadius(3.5f);
            //设置是否画圆
            lineDataSet.setDrawCircles(true);
            // 设置平滑曲线模式
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            //设置线一面部分是否填充颜色
            lineDataSet.setDrawFilled(true);
            //设置填充的颜色
            lineDataSet.setFillColor(getResources().getColor(R.color.quxian_lan_light));
            //设置是否显示点的坐标值
            lineDataSet.setDrawValues(true);
            lineDataSet.setValueTextSize(12f);
            lineDataSet.setValueTextColor(getResources().getColor(R.color.quxian_lan));
            //线的集合（可单条或多条线）
            List<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(lineDataSet);
            //把要画的所有线(线的集合)添加到LineData里
            LineData lineData = new LineData(dataSets);
            //把最终的数据setData
            mChart.setData(lineData);
            mChart.invalidate();
        }

    }


}
