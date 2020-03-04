package com.vn.babumart.activity.charts;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.vn.babumart.App;
import com.vn.babumart.R;
import com.vn.babumart.base.BaseFragment;
import com.vn.babumart.models.ReportProduct;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 22-April-2019
 * Time: 10:30
 * Version: 1.0
 */
public class FragmentChartCircle extends BaseFragment {
    private static final String TAG = "FragmentSetup";
    public static FragmentChartCircle fragment;
    @BindView(R.id.pieChart)
    AnyChartView anyChartView;
    @BindView(R.id.ll_chart_pie)
    LinearLayout ll_chart_pie;

/*        @BindView(R.id.chart_sanluong)
        AnyChartView chart_sanluong;
        @BindView(R.id.chart_quantity)
        AnyChartView chart_quantity;*/
    public static FragmentChartCircle getInstance() {
        if (fragment == null) {
            synchronized (FragmentChartCircle.class) {
                if (fragment == null)
                    fragment = new FragmentChartCircle();
            }
        }
        return fragment;
    }

    Pie pie, pie_sanluong, pie_soluong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_circle, container, false);
        ButterKnife.bind(this, view);
     //   set_height();
        Log.e(TAG, "onCreateView: Setup");
        pie = AnyChart.pie();
        pie_sanluong = AnyChart.pie();
        pie_soluong = AnyChart.pie();
/*
        //  pieChart.setDescription("Sales by employee (In Thousands $) ");
        pieChart.setRotationEnabled(false);
        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(10);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("");
        pieChart.setCenterTextSize(10);
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);
        //More options just check out the documentation!*/
        initData();
        addDataPie();

        return view;
    }

    private void set_height() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
// Gets the layout params that will allow you to resize the layout
        ViewGroup.LayoutParams params = anyChartView.getLayoutParams();
// Changes the height and width to the specified *pixels*
        params.height = width;
        params.width = width;
        anyChartView.setLayoutParams(params);

    }

    private void addDataPie() {
        List<DataEntry> data = new ArrayList<>();
        List<DataEntry> data_soluong = new ArrayList<>();
        List<DataEntry> data_sanluong = new ArrayList<>();
        if (App.mLisReportProduct.size() > 0) {
            for (ReportProduct obj : App.mLisReportProduct) {
                data.add(new ValueDataEntry(obj.getNAME(), Long.parseLong(obj.getTOTAL_REVENUE())));
                data_sanluong.add(new ValueDataEntry(obj.getNAME(), Long.parseLong(obj.getTOTAL_QUANTITY())));
                data_soluong.add(new ValueDataEntry(obj.getNAME(), Long.parseLong(obj.getTOTAL_ORDER())));
            }
        }

        //   pie.labels().position("outside");
        // pie.legend().title().enabled(false);

    /*    pie.legend().title()
                .text("Retail channels")
                .padding(0d, 0d, 3d, 0d);*/
        pie.title("Thống kê theo Doanh số");
        pie.animation(true, 100);
        pie.data(data);
        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);
        anyChartView.setChart(pie);
        // Chart san lượng
       /* pie_sanluong.title("Thống kê theo sản lượng");
        pie_sanluong.animation(true, 300);
        pie_sanluong.data(data_sanluong);
        pie_sanluong.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);
        chart_sanluong.setChart(pie_sanluong);
        // Chart số lượng
        pie_soluong.title("Thống kê theo số lượng");
        pie_soluong.animation(true, 300);
        pie_soluong.data(data_soluong);
        pie_soluong.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);
        chart_sanluong.setChart(pie_soluong);*/
    }

    private void initData() {

    }

/*    private void addDataSet() {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for (int i = 0; i < yData.size(); i++) {
            yEntrys.add(new PieEntry(yData.get(i), i));
        }

        for (int i = 1; i < xData.size(); i++) {
            xEntrys.add(xData.get(i));
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Employee Sales");
        pieDataSet.setSliceSpace(0.5f);
        pieDataSet.setValueTextSize(12);

      *//*  //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);*//*

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        //    legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }*/

}
