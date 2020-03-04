package com.vn.babumart.activity.charts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.vn.babumart.App;
import com.vn.babumart.R;
import com.vn.babumart.adapter.AdapterListPieChart;
import com.vn.babumart.base.BaseFragment;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.models.ObjPieChart;
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
public class FragmentListPieChart extends BaseFragment {
    private static final String TAG = "FragmentSetup";
    public static FragmentListPieChart fragment;
    private List<ObjPieChart> mListPieChart;
    private AdapterListPieChart adrapter;
    @BindView(R.id.rcv_list)
    RecyclerView rcv_list_table;
    RecyclerView.LayoutManager mLayoutManager;

    public static FragmentListPieChart getInstance() {
        if (fragment == null) {
            synchronized (FragmentListPieChart.class) {
                if (fragment == null)
                    fragment = new FragmentListPieChart();
            }
        }
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        ButterKnife.bind(this, view);
        Log.e(TAG, "onCreateView: Setup");
        init();
        initData();
        return view;
    }

    private void initData() {
        mListPieChart.clear();
        List<DataEntry> data = new ArrayList<>();
        List<DataEntry> data_soluong = new ArrayList<>();
        List<DataEntry> data_sanluong = new ArrayList<>();
        if (App.mLisReportProduct.size() > 0) {
            for (ReportProduct obj : App.mLisReportProduct) {
                if (obj.getsTypeReport().equals("4")) {
                    data.add(new ValueDataEntry(obj.getPRODUCT_NAME(), Long.parseLong(obj.getTOTAL_REVENUE())));
                    data_sanluong.add(new ValueDataEntry(obj.getPRODUCT_NAME(), Long.parseLong(obj.getTOTAL_QUANTITY())));
                    data_soluong.add(new ValueDataEntry(obj.getPRODUCT_NAME(), Long.parseLong(obj.getTOTAL_ORDER())));
                } else {
                    data.add(new ValueDataEntry(obj.getNAME(), Long.parseLong(obj.getTOTAL_REVENUE())));
                    data_sanluong.add(new ValueDataEntry(obj.getNAME(), Long.parseLong(obj.getTOTAL_QUANTITY())));
                    data_soluong.add(new ValueDataEntry(obj.getNAME(), Long.parseLong(obj.getTOTAL_ORDER())));
                }

            }
        }
        mListPieChart.add(new ObjPieChart(data, "Thống kê theo doanh số"));
        mListPieChart.add(new ObjPieChart(data_soluong, "Thống kê theo số lượng"));
        mListPieChart.add(new ObjPieChart(data_sanluong, "Thống kê theo sản lượng"));
        adrapter.notifyDataSetChanged();
    }

    private void init() {
        mListPieChart = new ArrayList<>();
        adrapter = new AdapterListPieChart(mListPieChart, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        rcv_list_table.setNestedScrollingEnabled(false);
        rcv_list_table.setHasFixedSize(true);
        rcv_list_table.setLayoutManager(mLayoutManager);
        rcv_list_table.setItemAnimator(new DefaultItemAnimator());
        rcv_list_table.setAdapter(adrapter);

        adrapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {

            }
        });

    }

}
