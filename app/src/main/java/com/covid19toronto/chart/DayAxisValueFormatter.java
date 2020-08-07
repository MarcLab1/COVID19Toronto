package com.covid19toronto.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class DayAxisValueFormatter implements IAxisValueFormatter {
    private SimpleDateFormat sdf;

    public DayAxisValueFormatter() {

    }

    /**
     * Called when a value from an axis is to be formatted
     * before being drawn.
     *
     * @param value the value to be formatted
     * @param axis  the axis the value belongs to
     * @return
     */
    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        long date = TimeUnit.MINUTES.toMillis((long) value);

            return getFormattedDateMMMyyyy(date);
    }

    private String getFormattedDateMMMyyyy(long date) {
        sdf = new SimpleDateFormat("MMM yyyy");
        return sdf.format(date);
    }
}
