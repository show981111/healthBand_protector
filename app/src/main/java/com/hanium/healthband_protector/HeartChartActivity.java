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

public class HeartChartActivity extends AppCompatActivity {

    private TextView tv_min;
    private TextView tv_max;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        tv_min = findViewById(R.id.tv_min);
        tv_max = findViewById(R.id.tv_max);

        final ArrayList<String> dateList = new ArrayList<>();
        dateList.add("8/1");
        dateList.add("8/2");
        dateList.add("8/3");
        dateList.add("8/4");
        dateList.add("8/5");
        dateList.add("8/6");
        dateList.add("8/7");

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
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setAxisMinimum(-0.25f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return dateList.get((int) value);

            }
        });

        CombinedData data = new CombinedData();

        data.setData(generateLineData());
        data.setData(generateCandleData());

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        chart.setData(data);
        chart.invalidate();

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if(e instanceof CandleEntry){
                    CandleEntry candleEntry = (CandleEntry) e;
                    tv_max.setText(String.valueOf(candleEntry.getHigh()));
                    tv_min.setText(String.valueOf(candleEntry.getLow()));

                    //tv_avg.setText(candleEntry.getData() + " 최대 : " + candleEntry.ge);
                }
                //if(e.getClass())

            }

            @Override
            public void onNothingSelected() {

            }
        });

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
