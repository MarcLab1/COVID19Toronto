package com.covid19toronto.chart;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class PointValueFormatter implements IValueFormatter {

    public PointValueFormatter() {
        // mFormat = new Format("###,###,##0"); // use one decimal
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

        return (int) value + "";
    }
}
