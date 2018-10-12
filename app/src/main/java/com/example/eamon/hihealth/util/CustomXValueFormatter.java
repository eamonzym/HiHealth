package com.example.eamon.hihealth.util;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/5/17.
 */

public class CustomXValueFormatter implements IAxisValueFormatter {

    private List<String> labels;

    public CustomXValueFormatter(List<String> labels) {
        this.labels = labels;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return labels.get((int) value % labels.size());
    }
}
