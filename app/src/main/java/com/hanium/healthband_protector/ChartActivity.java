package com.hanium.healthband_protector;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    private TextView tv_avg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        tv_avg = findViewById(R.id.tv_avg);
        /*
        CandleStickChart candleStickChart = findViewById(R.id.combined_chart);
        candleStickChart.setHighlightPerDragEnabled(true);

        candleStickChart.setDrawBorders(true);

        candleStickChart.setBorderColor(getResources().getColor(R.color.colorPrimary));

        YAxis yAxis = candleStickChart.getAxisLeft();
        YAxis rightAxis = candleStickChart.getAxisRight();
        yAxis.setDrawGridLines(true);
        rightAxis.setDrawGridLines(true);
        candleStickChart.requestDisallowInterceptTouchEvent(true);

        XAxis xAxis = candleStickChart.getXAxis();

        xAxis.setDrawGridLines(true);// disable x axis grid lines
        rightAxis.setTextColor(Color.WHITE);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setAvoidFirstLastClipping(true);

        Legend l = candleStickChart.getLegend();
        l.setEnabled(true);


        float[] dataPair = new float[]{50f, 100f};

        ArrayList<CandleEntry> yValsCandleStick= new ArrayList<CandleEntry>();
        yValsCandleStick.add(new CandleEntry(0, 120f, 100f, 120f, 100f, 200));
        yValsCandleStick.add(new CandleEntry(1, 130f, 90f, 130f, 90f, 100));
        yValsCandleStick.add(new CandleEntry(2, 123f,  80f, 123f,  80f, 40));
        yValsCandleStick.add(new CandleEntry(3, 127f, 75f, 127f, 75f, 50));
        yValsCandleStick.add(new CandleEntry(4, 127f, 75f, 127f, 75f, 60));
        yValsCandleStick.add(new CandleEntry(5, 127f, 75f, 127f, 75f, 70));
        yValsCandleStick.add(new CandleEntry(6, 127f, 75f, 127f, 75f, 80));

        ArrayList<Entry> line= new ArrayList<Entry>();
        line.add(new Entry(0, 100));
        line.add(new Entry(1, 102));
        line.add(new Entry(2, 100));
        line.add(new Entry(3, 105));
        line.add(new Entry(4, 100));
        line.add(new Entry(5, 109));
        line.add(new Entry(6, 100));

        ArrayList<Entry>
        CandleDataSet set1 = new CandleDataSet(yValsCandleStick, "DataSet 1");
        set1.setColor(Color.rgb(80, 80, 80));
        set1.setShadowColor(getResources().getColor(R.color.colorPrimaryDark));
        set1.setShadowWidth(0f);
        set1.setDecreasingColor(getResources().getColor(R.color.colorAccent));
        set1.setDecreasingPaintStyle(Paint.Style.FILL);
        set1.setIncreasingColor(getResources().getColor(R.color.colorAccent));
        set1.setIncreasingPaintStyle(Paint.Style.FILL);
        set1.setNeutralColor(Color.LTGRAY);
        set1.setDrawValues(true);



// create a data object with the datasets
        CandleData data = new CandleData(set1);


// set data
        candleStickChart.setData(data);
        candleStickChart.invalidate();


        candleStickChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                tv_avg.setText(String.valueOf(e.getX()) + " val " + String.valueOf(e.getData()));
            }

            @Override
            public void onNothingSelected() {

            }
        });*/

        CombinedChart chart = findViewById(R.id.combined_chart);
        chart.getDescription().setEnabled(false);
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);

        chart.setDrawOrder(new CombinedChart.DrawOrder[]{
                 CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE
        });

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
//        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
//        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
//        xAxis.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                return months[(int) value % months.length];
//            }
//        });

        CombinedData data = new CombinedData();

        data.setData(generateLineData());
        data.setData(generateCandleData());

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        chart.setData(data);
        chart.invalidate();


    }

    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();


        entries.add(new Entry( 0, 100));
        entries.add(new Entry( 1, 108));
        entries.add(new Entry( 2, 110));
        entries.add(new Entry( 3, 115));
        entries.add(new Entry( 4, 104));
        entries.add(new Entry( 5, 107));
        entries.add(new Entry( 6, 114));


        LineDataSet set = new LineDataSet(entries, "Line DataSet");
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    private CandleData generateCandleData() {

        CandleData d = new CandleData();

        ArrayList<CandleEntry> entries = new ArrayList<>();


        entries.add(new CandleEntry(0, 120f, 100f, 120f, 100f, 200));
        entries.add(new CandleEntry(1, 130f, 90f, 130f, 90f, 100));
        entries.add(new CandleEntry(2, 123f,  80f, 123f,  80f, 40));
        entries.add(new CandleEntry(3, 127f, 75f, 127f, 75f, 50));
        entries.add(new CandleEntry(4, 127f, 75f, 127f, 75f, 60));
        entries.add(new CandleEntry(5, 127f, 75f, 127f, 75f, 70));
        entries.add(new CandleEntry(6, 127f, 75f, 127f, 75f, 80));

        CandleDataSet set = new CandleDataSet(entries, "Candle DataSet");
        set.setShadowWidth(0f);
        set.setDecreasingColor(Color.rgb(142, 150, 175));
        set.setShadowColor(Color.DKGRAY);
        set.setBarSpace(0.3f);
        set.setValueTextSize(10f);
        set.setDrawValues(false);
        d.addDataSet(set);

        return d;
    }
}
