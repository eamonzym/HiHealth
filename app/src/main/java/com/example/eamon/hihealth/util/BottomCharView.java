package com.example.eamon.hihealth.util;

import android.content.Context;
import android.graphics.DashPathEffect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.eamon.hihealth.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/5/5.
 */

public class BottomCharView extends LinearLayout {
    private LineChart mChart;

    public BottomCharView(Context context) {
        super(context);
        init(context);
    }

    public BottomCharView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(final Context context) {
        LayoutInflater.from(context).inflate(R.layout.main_bottom_view, this);

        // bottom_view 的chart1
        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setDrawGridBackground(false);

        // 禁止绘制图表边框的线
        mChart.setDrawBorders(false);

        // 没有描述信息
        mChart.getDescription().setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        // 设置X轴禁用
        xAxis.setEnabled(false);
        // 设置网格线以虚线模式绘制
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        // 禁用X轴的标签
        xAxis.setDrawLabels(false);
        // 禁用垂直于X轴的网格线
        xAxis.setDrawGridLines(false);

        LimitLine ll1 = new LimitLine(150, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f,10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);

        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f,10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        YAxis leftAxis = mChart.getAxisLeft();

        // 禁用Y轴的绘制
        leftAxis.setEnabled(false);
        // 删除所有限制线
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        // 为Y轴设置自定义的最大值
        leftAxis.setAxisMaximum(200f);
        // 为Y轴设置自定义的最小值
        leftAxis.setAxisMinimum(-50f);
        // 网格线虚拟设置， 控制线条的长度，控制线条之间的空间，控制起点
        leftAxis.enableGridDashedLine(10f, 10f, 0);
        // 禁用零线
        leftAxis.setDrawZeroLine(false);

        // 控制控制线和实际数据之间的位置关系，
        // true 为控制线在下，false在上
        leftAxis.setDrawLimitLinesBehindData(true);

        // 禁用右轴
        mChart.getAxisRight().setEnabled(false);

        // 禁用图表缩放比例
        mChart.setScaleEnabled(false);

        // 添加数据

        setData();

        // 这里禁用图列，在界面上已经默认设置
        Legend l = mChart.getLegend();
        l.setEnabled(false);

        // 设置图列的显示样式，这里是线形
        l.setForm(Legend.LegendForm.LINE);


        // 设置图片点击
        mChart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "图表被点击", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData() {
        // 创建线性图数据列表
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(getDataSet1(35, 100));
        dataSets.add(getDataSet2(35,100));

        // 构造线性图表数据
        LineData data = new LineData(dataSets);

        // 绘制图表数据
        mChart.setData(data);
    }

    private LineDataSet getDataSet1(int count, float range) {
        // 创建Entry列表 values 用来保存data类型值列表
        ArrayList<Entry> values = new ArrayList<Entry>();

        // 循环设置values的值(new Entry(x,y))
        for (int i = 0; i < count; i++) {
            // 这里先设置y轴的值，为90到190的随机数
            float val = (float) (Math.random() * range) + 90;
            values.add(new Entry(i, val));
        }
        LineDataSet set1;
        // 首先判断是否为空
        // 当不为空的时候,通过notifyDataSetChanged方法来刷新当前的图表信息
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        // 当为空的时候，开始画线
        } else {
            // 设置线条基本属性
            set1 = new LineDataSet(values, "DataSet 1");
            // 设置颜色
            set1.setColor(this.getResources().getColor(R.color.quxian_lan));
            // 设置数据圆圈指示符的颜色
            set1.setCircleColor(this.getResources().getColor(R.color.quxian_lan));
            // 设置该DataSet的线宽
            set1.setLineWidth(1f);
            // 设置圆形指示器的大小(半径)
            set1.setCircleRadius(0f);

            //设置是否画圆形指示器
            set1.setDrawCircles(false);
            //设置是否圆圈绘制小孔
            set1.setDrawCircleHole(false);
            //设置数据值的文本的大小
            set1.setValueTextSize(9f);
            // 设置线条是否绘制覆盖区域
            set1.setDrawFilled(true);
            // 设置覆盖区域的填充色
            set1.setFillColor(this.getResources().getColor(R.color.quxian_lan_light));
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            // 设置图例形式的大小
            set1.setFormSize(15.F);
            // 不显示坐标点数据
            set1.setDrawValues(false);
            // 设置是否通过触摸突出显示特定内容
            set1.setHighlightEnabled(false);
            // 设置平滑曲线
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        }
        return set1;
    }

    private LineDataSet getDataSet2(int count, float range) {
        // 创建Entry列表 values 用来保存data类型值列表
        ArrayList<Entry> values = new ArrayList<Entry>();

        // 循环设置values的值(new Entry(x,y))
        for (int i = 0; i < count; i++) {
            // 这里先设置y轴的值，为15到115的随机数
            float val = (float) (Math.random() * range) + 15;
            values.add(new Entry(i, val));
        }
        LineDataSet set1;
        // 首先判断是否为空
        // 当不为空的时候,通过notifyDataSetChanged方法来刷新当前的图表信息
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
            // 当为空的时候，开始画线
        } else {
            // 设置线条基本属性
            set1 = new LineDataSet(values, "DataSet 1");
            // 设置颜色
            set1.setColor(this.getResources().getColor(R.color.quxian_zi_light));
            // 设置数据圆圈指示符的颜色
            set1.setCircleColor(this.getResources().getColor(R.color.quxian_zi_light));
            // 设置该DataSet的线宽
            set1.setLineWidth(1f);
            // 设置圆形指示器的大小(半径)
            set1.setCircleRadius(0f);

            //设置是否画圆形指示器
            set1.setDrawCircles(false);
            //设置是否圆圈绘制小孔
            set1.setDrawCircleHole(false);
            //设置数据值的文本的大小
            set1.setValueTextSize(9f);
            // 设置线条是否绘制覆盖区域
            set1.setDrawFilled(false);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            // 设置图例形式的大小
            set1.setFormSize(15.F);
            // 不显示坐标点数据
            set1.setDrawValues(false);
            // 设置是否通过触摸突出显示特定内容
            set1.setHighlightEnabled(false);
            // 设置平滑曲线
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        }
        return set1;
    }


}
