package com.gavin.gavinchart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private LineChart mLineChart;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mLineChart = (LineChart) findViewById(R.id.line_chart);

        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        initUI();
    }

    /**
     * 绘制UI
     */
    private void initUI() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        LineData mLineData = getLineData(36, 20);

        showChart(mLineChart, mLineData);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 设置显示的样式
     *
     * @param lineChart
     * @param lineData
     */
    private void showChart(LineChart lineChart, LineData lineData) {
        lineChart.setDrawBorders(false); // 是否在折线图上添加边框

        // no description txt
        lineChart.setDescription(""); // 数据描述
        // 如果没有数据的时候，会显示这个，类似listView的emptyView
        lineChart.setNoDataTextDescription("You need to provide data for the charts.");

        // enable . disable grid background
        lineChart.setDrawGridBackground(false); // 是否显示表格颜色
        lineChart.setGridBackgroundColor(Color.BLACK & 0x70FFFFFF); // 表格的颜色，在这里是给颜色设置一个透明度

        // enable touch gestures
        lineChart.setTouchEnabled(true); // 设置时候可以触摸

        lineChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        lineChart.setDragEnabled(true); // 是否可以拖拽
        lineChart.setScaleEnabled(false); // 是否可以缩放
        lineChart.setHighlightPerDragEnabled(false);

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);

        lineChart.setBackgroundColor(Color.WHITE);

        // add data
        lineChart.setData(lineData);

        // get the legend (only possible after setting data)
        Legend mLegend = lineChart.getLegend(); // 设置比例图样式，就是那个一组Y的value

        // modify the legend ...
        // mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
        mLegend.setForm(Legend.LegendForm.LINE); // 样式
        mLegend.setFormSize(12f); // 字号
        mLegend.setTextSize(11f);
        mLegend.setTextColor(Color.BLACK); // 折线图下方标注的颜色颜色
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        // mLegend.setTypeface(mlf); // 字体

        // x轴坐标轴
        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setTypeface(tf);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.BLUE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceBetweenLabels(1);

        // y轴左侧坐标轴
        YAxis leftAxis = lineChart.getAxisLeft();
//        leftAxis.setTypeface(tf);
        leftAxis.setTextSize(12f);
        leftAxis.setTextColor(Color.BLUE);
        leftAxis.setAxisMaxValue(30f); // 最大坐标
        leftAxis.setDrawGridLines(true);

        // y轴右侧坐标轴
        YAxis rightAxis = lineChart.getAxisRight();
//        rightAxis.setTypeface(tf);
        rightAxis.setEnabled(false);

        /**
         * 显示折线图下方的颜色区域
         */
        List<LineDataSet> sets = lineChart.getData()
                .getDataSets();

        for (LineDataSet iSet : sets) {

            LineDataSet set = (LineDataSet) iSet;
            if (set.isDrawFilledEnabled())
                set.setDrawFilled(false);
            else
                set.setDrawFilled(true);
        }
        lineChart.invalidate();

        lineChart.animateX(3000); // 立即执行的动画，x轴
    }


    /**
     * 生成模拟数据
     *
     * @param count
     * @param range
     * @return
     */
    private LineData getLineData(int count, float range) {
        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            // x轴显示的数据
            xValues.add("" + i);
        }

        // y轴数据
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            float value = (float) (Math.random() * range) + 3;
            yValues.add(new Entry(value, i));
        }

        // create a dataset and give it a type
        // y轴的数据集合
        LineDataSet lineDataset = new LineDataSet(yValues, "测试折线图" /* 显示在比例图上 */);
        // mLineDataSet.setFillAlpha(110);
        // mLineDataSet.setFillColoc(Color.RED);

        // 用y轴的集合来设置参数
        lineDataset.setValueTextColor(R.color.colorPrimary); // 坐标上文字的颜色
        lineDataset.setValueTextSize(6f);
        lineDataset.setLineWidth(2f); // 线宽
        lineDataset.setCircleSize(4f); // 显示的圆形大小
        lineDataset.setFillAlpha(65);
        lineDataset.setFillColor(ColorTemplate.getHoloBlue());
        lineDataset.setColor(Color.CYAN); // 线的显示颜色
        lineDataset.setCircleColor(Color.BLUE); // 圆形的颜色
        lineDataset.setHighLightColor(Color.TRANSPARENT); // 点击后显示的高亮的线的颜色
        lineDataset.setDrawCircleHole(false);

        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        lineDataSets.add(lineDataset);

        // create a data object with the datasets
        LineData lineData = new LineData(xValues, lineDataSets);

        return lineData;
    }

}
