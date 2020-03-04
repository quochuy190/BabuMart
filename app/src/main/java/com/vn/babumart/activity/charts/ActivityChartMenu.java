package com.vn.babumart.activity.charts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.vn.babumart.R;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.untils.SharedPrefs;

import butterknife.BindView;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 29-May-2019
 * Time: 15:53
 * Version: 1.0
 */
public class ActivityChartMenu extends BaseActivity
        implements View.OnClickListener {
    @BindView(R.id.llct_chart_time)
    ConstraintLayout llct_chart_time;
    @BindView(R.id.llct_chart_all)
    ConstraintLayout llct_chart_all;
    @BindView(R.id.llct_chart_biendong)
    ConstraintLayout llct_chart_biendong;
    @BindView(R.id.llct_chart_ctv)
    ConstraintLayout llct_chart_ctv;
    @BindView(R.id.icon_left_2)
    ImageView icon_left_2;
    @BindView(R.id.ll_chart_2)
    ConstraintLayout ll_chart_2;

    @Override
    public int setContentViewId() {
        return R.layout.activity_chart_menu;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAppbar();
        initData();
        initEvent();

    }

    ObjLogin mLogin;

    private void initData() {
        mLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        if (mLogin.getGROUPS().equals("5")) {
            llct_chart_all.setVisibility(View.GONE);
            ll_chart_2.setVisibility(View.GONE);
            llct_chart_time.setVisibility(View.GONE);
            llct_chart_biendong.setVisibility(View.GONE);
        } else {
            llct_chart_all.setVisibility(View.VISIBLE);
            ll_chart_2.setVisibility(View.VISIBLE);
            llct_chart_time.setVisibility(View.GONE);
            llct_chart_biendong.setVisibility(View.GONE);
        }

    }

    private void initAppbar() {
        ImageView img_back = findViewById(R.id.img_back);
        TextView txt_title = findViewById(R.id.txt_title);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_title.setText("Danh mục thông kê");
    }

    private void initEvent() {
        llct_chart_all.setOnClickListener(this);
        llct_chart_biendong.setOnClickListener(this);
        ll_chart_2.setOnClickListener(this);
        llct_chart_ctv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.llct_chart_all:
                intent = new Intent(ActivityChartMenu.this,
                        ActivityReportDefault.class);
                break;
            case R.id.ll_chart_2:
                intent = new Intent(ActivityChartMenu.this,
                        Activity_Chart_By_Products.class);
                break;
            case R.id.llct_chart_biendong:
                intent = new Intent(ActivityChartMenu.this,
                        Activity_Fluctuations.class);
                break;
            case R.id.llct_chart_ctv:
                if (mLogin.getGROUPS().equals("5")) {
                    intent = new Intent(ActivityChartMenu.this,
                            ActivityReportCTVDetail.class);
                } else {
                    intent = new Intent(ActivityChartMenu.this, ActivityChartListCTV.class);
                }

                break;
        }
        if (intent != null) {
            startActivity(intent);
        } else
            return;
    }

}
