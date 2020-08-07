package com.covid19toronto.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import android.widget.LinearLayout;

import com.covid19toronto.helpers.HelperDate;
import com.covid19toronto.helpers.HelperFormatting;
import com.covid19toronto.chart.DayAxisValueFormatter;
import com.covid19toronto.helpers.HelperPixel;
import com.covid19toronto.chart.PointValueFormatter;
import com.covid19toronto.R;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LayoutChart extends LinearLayout {
    private LineChart chart;
    private HelperPixel myPixelHelper;
    private HelperFormatting myHelperFormatting;

    public LayoutChart(final Context context) {
        super(context);

        myPixelHelper = new HelperPixel(context);
        myHelperFormatting = new HelperFormatting(context);

        this.setOrientation(LinearLayout.VERTICAL);
        this.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(myPixelHelper.convertDptoPx(5), 0, myPixelHelper.convertDptoPx(5), 0);
        this.setLayoutParams(layoutParams);

        chart = new LineChart(context);
        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 0, 0);
        chart.setLayoutParams(layoutParams);
        chart.setPadding(0, 0, 0, 0);

        this.addView(chart);
    }

    public void drawChart(Map<Long, Integer> recordHashmap, String FSA) {

        if (recordHashmap == null || recordHashmap.size() == 0) {
            Paint p = chart.getPaint(Chart.PAINT_INFO);
            p.setColor(Color.RED);

            chart.setNoDataText(myHelperFormatting.getNoDataToDisplay());

            chart.setData(null);

            chart.invalidate();

            return;
        }

        DayAxisValueFormatter dayAxisValueFormatter = new DayAxisValueFormatter();
        List<Entry> entries = new ArrayList<Entry>();

        for (Map.Entry<Long, Integer> entry : recordHashmap.entrySet()) {
            entries.add(new Entry(TimeUnit.MILLISECONDS.toMinutes(entry.getKey()), entry.getValue()));
        }
        LineDataSet dataSet = new LineDataSet(entries, myHelperFormatting.getLineDataSetLabel(FSA)); // add entries to dataset

        dataSet.setColor(Color.RED);
        dataSet.setCircleColor(Color.RED);
        dataSet.setLineWidth(1.5f);
        dataSet.setCircleRadius(1);
        dataSet.setCircleColorHole(R.color.red);

        LineData lineData = new LineData(dataSet);
        lineData.setHighlightEnabled(false);

        PointValueFormatter pointValueFormatter = new PointValueFormatter();
        lineData.setValueFormatter(pointValueFormatter);
        chart.setData(lineData);

        YAxis left = chart.getAxisLeft();
        YAxis right = chart.getAxisRight();

        left.setDrawLabels(false); // no axis labels

        left.setDrawZeroLine(true); // draw a zero line
        right.setDrawZeroLine(true);

        int max = HelperDate.getHighestNumberInMap(recordHashmap);

        if(max <10) {
            left.setAxisMaximum(10);
            right.setAxisMaximum(10);
        }
        else if (max <50)
        {
            left.setAxisMaximum(50);
            right.setAxisMaximum(50);
        }
        else
        {
            left.setAxisMaximum(100);
            right.setAxisMaximum(100);
        }

        left.setAxisMinimum(0);
        right.setAxisMinimum(0);
        left.mDecimals = 0;
        right.mDecimals = 0;

        XAxis xAxis = chart.getXAxis();

        xAxis.setValueFormatter(dayAxisValueFormatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setLabelCount(6); //force is false by default
        xAxis.setAvoidFirstLastClipping(true); //???????????????????
        xAxis.setSpaceMin(5f);
        xAxis.setTextSize(8);
        xAxis.setGranularity(1440f); // restrict interval to 1 day

        chart.setScaleXEnabled(true);
        chart.setScaleYEnabled(false);

        chart.getDescription().setEnabled(false);
        chart.invalidate(); // refresh
    }
}

