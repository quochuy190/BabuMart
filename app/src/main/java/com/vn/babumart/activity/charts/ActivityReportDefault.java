package com.vn.babumart.activity.charts;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.shawnlin.numberpicker.NumberPicker;
import com.vn.babumart.R;
import com.vn.babumart.activity.login.ActivityLogin;
import com.vn.babumart.adapter.AdapterReportDefault;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.callback.ClickDialog;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.ReportDefault;
import com.vn.babumart.models.respon_api.ResponGetReportDefault;
import com.vn.babumart.models.respon_api.ResponGetReportListCTV;
import com.vn.babumart.models.respon_api.ResponGetReportProduct;
import com.vn.babumart.untils.SharedPrefs;
import com.vn.babumart.untils.StringUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import butterknife.BindView;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 07-June-2019
 * Time: 09:58
 * Version: 1.0
 */
public class ActivityReportDefault extends BaseActivity implements InterfaceReport.View, View.OnClickListener {
    private List<ReportDefault> mList;
    private AdapterReportDefault adapter;
    @BindView(R.id.rcv_list_report_default)
    RecyclerView recycle_service;
    @BindView(R.id.txt_total_sl)
    TextView txt_total_sl;
    @BindView(R.id.txt_total_doanhso)
    TextView txt_total_doanhso;
    @BindView(R.id.txt_total_hh)
    TextView txt_total_hh;
    @BindView(R.id.txt_total_thucthu)
    TextView txt_total_thucthu;
    @BindView(R.id.ll_choose_option)
    ConstraintLayout ll_choose_option;
    @BindView(R.id.ll_choose_year)
    ConstraintLayout ll_choose_year;
    @BindView(R.id.ll_choose_month)
    ConstraintLayout ll_choose_month;
    @BindView(R.id.img_down_filter_CTV)
    ImageView img_down_filter_CTV;
    @BindView(R.id.txt_option)
    TextView txt_option;
    @BindView(R.id.txt_year)
    TextView txt_year;
    @BindView(R.id.txt_month)
    TextView txt_month;
    @BindView(R.id.img_year)
    ImageView img_year;
    @BindView(R.id.img_month)
    ImageView img_month;
    @BindView(R.id.btn_search_report)
    Button btn_search_report;
    @BindView(R.id.txt_title_year)
    TextView txt_title_year;
    RecyclerView.LayoutManager mLayoutManager;
    private PresenterReport mPresenter;
    String sUserName = "", sYear = "", sMonth = "", sReportType = "1";

    @Override
    public int setContentViewId() {
        return R.layout.activity_report_default;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterReport(this);
        init();
        initAppbar();
        initData();
        initEvent();
    }

    private void initEvent() {
        img_down_filter_CTV.setOnClickListener(this);
        img_month.setOnClickListener(this);
        img_year.setOnClickListener(this);
        btn_search_report.setOnClickListener(this);
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
        txt_title.setText("Báo cáo doanh số chung");
    }
    private void initData() {
        txt_option.setText("Báo cáo theo năm");
        ll_choose_month.setVisibility(View.GONE);
        ll_choose_year.setVisibility(View.GONE);
        sYear = String.valueOf(year);
        sMonth = "" + (month + 1);
        txt_year.setText("");
        txt_month.setText("");
        txt_year.setHint("- Chọn năm");
        txt_month.setHint("- Chọn tháng");
        sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        showDialogLoading();
        mPresenter.api_report_default(sUserName, "", "", sReportType);
    }

    private void init() {
        mList = new ArrayList<>();
        adapter = new AdapterReportDefault(mList, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        recycle_service.setNestedScrollingEnabled(false);
        recycle_service.setHasFixedSize(true);
        recycle_service.setLayoutManager(mLayoutManager);
        recycle_service.setItemAnimator(new DefaultItemAnimator());
        recycle_service.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void show_error_api() {

    }

    @Override
    public void show_report_item(ResponGetReportProduct objRespon) {

    }


    @Override
    public void show_report_default(ResponGetReportDefault obj) {
        hideDialogLoading();
        switch (sReportType) {
            case "1":
                txt_title_year.setText("Năm");
                break;
            case "2":
                txt_title_year.setText("Tháng");
                break;
            case "3":
                txt_title_year.setText("Ngày");
                break;
        }
        if (obj != null && obj.getsERROR().equals("0000")) {
            mList.clear();
            mList.addAll(obj.getmList());
            int total_quantity = 0;
            int total_doanhso = 0;
            int total_hh = 0;
            int total_thucthu = 0;
            for (ReportDefault objReport : mList) {
                objReport.setREPORT_TYPE(sReportType);
                if (objReport.getTOTAL_ORDER() != null && objReport.getTOTAL_ORDER().length() > 0) {
                    total_quantity = total_quantity + Integer.parseInt(objReport.getTOTAL_ORDER());
                }
                if (objReport.getTOTAL_MONEY() != null && objReport.getTOTAL_MONEY().length() > 0) {
                    total_doanhso = total_doanhso + Integer.parseInt(objReport.getTOTAL_MONEY());
                }
                if (objReport.getTOTAL_TT() != null && objReport.getTOTAL_TT().length() > 0) {
                    total_thucthu = total_thucthu + Integer.parseInt(objReport.getTOTAL_TT());
                }
                if (objReport.getTOTAL_COMMISSION() != null && objReport.getTOTAL_COMMISSION().length() > 0) {
                    total_hh = total_hh + Integer.parseInt(objReport.getTOTAL_COMMISSION());
                }
            }
            txt_total_doanhso.setText(StringUtil.conventMonney_Long("" + total_doanhso));
            txt_total_hh.setText(StringUtil.conventMonney_Long("" + total_hh));
            txt_total_thucthu.setText(StringUtil.conventMonney_Long("" + total_thucthu));
            txt_total_sl.setText("" + total_quantity);
            adapter.notifyDataSetChanged();
        } else if (obj.getsERROR().equals("0002")) {
            showDialogComfirm("Thông báo", obj.getsRESULT(), false, new ClickDialog() {
                @Override
                public void onClickYesDialog() {
                    startActivity(new Intent(ActivityReportDefault.this, ActivityLogin.class));
                    finish();
                }

                @Override
                public void onClickNoDialog() {

                }
            });
        } else showAlertDialog("Thông báo", obj.getsRESULT());
    }

    @Override
    public void show_get_sub_product(ResponGetReportDefault obj) {

    }

    @Override
    public void show_get_sub_product_child(ResponGetReportDefault obj) {

    }

    @Override
    public void show_get_report_ctv(ResponGetReportListCTV obj) {

    }

    @Override
    public void show_get_report_ctv_detail(ResponGetReportListCTV obj) {

    }

    @Override
    public void show_get_report_fluctuations(ResponGetReportListCTV obj) {

    }

    int month = Calendar.getInstance().get(Calendar.MONTH);
    int year = Calendar.getInstance().get(Calendar.YEAR);

    private void show_dialog_picker_year() {
        final Dialog dialog = new Dialog(this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_number_picker);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView btn_okie = dialog.findViewById(R.id.btn_ok);
        TextView btn_cancel = dialog.findViewById(R.id.btn_cancel);
        dialog.setCancelable(true);
        final NumberPicker numberPicker = dialog.findViewById(R.id.number_picker);
    /*    numberPicker.setMaxValue(year + 1);
        numberPicker.setMinValue(year - 10);*/
        numberPicker.setMinValue(year - 10);
        numberPicker.setMaxValue(year + 2);
        numberPicker.setValue(year);
        btn_okie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sYear = "" + numberPicker.getValue();
                txt_year.setText(sYear);
                dialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void show_dialog_picker_month() {
        final Dialog dialog = new Dialog(this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_number_picker);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        TextView btn_okie = dialog.findViewById(R.id.btn_ok);
        TextView btn_cancel = dialog.findViewById(R.id.btn_cancel);
        final NumberPicker numberPicker = dialog.findViewById(R.id.number_picker);
    /*    numberPicker.setMaxValue(year + 1);
        numberPicker.setMinValue(year - 10);*/

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(11);
        numberPicker.setValue(month);
        numberPicker.setDisplayedValues(new String[]{"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
                "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"});
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            }
        });
        btn_okie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sMonth = "" + (numberPicker.getValue() + 1);
                txt_month.setText("Tháng " + sMonth);
                dialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.show();
    }

    Dialog dialog_yes;

    public void showDialogOption() {
        dialog_yes = new Dialog(this, R.style.Theme_Dialog);
        dialog_yes.setCancelable(false);
        dialog_yes.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_yes.setContentView(R.layout.dialog_selected_status);
        dialog_yes.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView txt_title = (TextView) dialog_yes.findViewById(R.id.txt_warning_title);
        TextView txt_report_year = (TextView) dialog_yes.findViewById(R.id.txt_dangchoduyet);
        TextView txt_report_month = (TextView) dialog_yes.findViewById(R.id.txt_datiepnhan);
        TextView txt_report_day = (TextView) dialog_yes.findViewById(R.id.txt_dangchuyen);
        TextView txt_hoanthanh = (TextView) dialog_yes.findViewById(R.id.txt_hoanthanh);
        TextView txt_huy = (TextView) dialog_yes.findViewById(R.id.txt_huy);
        TextView txt_back = (TextView) dialog_yes.findViewById(R.id.txt_back);
        txt_title.setOnClickListener(this);
        txt_report_year.setOnClickListener(this);
        txt_report_month.setOnClickListener(this);
        txt_report_day.setOnClickListener(this);
        txt_hoanthanh.setVisibility(View.GONE);
        txt_huy.setVisibility(View.GONE);
        txt_back.setVisibility(View.GONE);
        txt_title.setText("Chọn hình thức báo cáo");
        txt_report_year.setText("Báo cáo theo năm");
        txt_report_month.setText("Báo cáo theo tháng");
        txt_report_day.setText("Báo cáo theo ngày");
        dialog_yes.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_down_filter_CTV:
                showDialogOption();
                break;
            case R.id.txt_dangchoduyet:
                txt_option.setText("Báo cáo theo năm");
                dialog_yes.dismiss();
                sReportType = "1";
                ll_choose_month.setVisibility(View.GONE);
                ll_choose_year.setVisibility(View.GONE);
                mList.clear();
                adapter.notifyDataSetChanged();
                break;
            case R.id.txt_datiepnhan:
                txt_option.setText("Báo cáo theo tháng");
                dialog_yes.dismiss();
                sReportType = "2";
                ll_choose_month.setVisibility(View.GONE);
                ll_choose_year.setVisibility(View.VISIBLE);
                mList.clear();
                adapter.notifyDataSetChanged();
                break;
            case R.id.txt_dangchuyen:
                ll_choose_year.setVisibility(View.VISIBLE);
                ll_choose_month.setVisibility(View.VISIBLE);
                txt_option.setText("Báo cáo theo ngày");
                dialog_yes.dismiss();
                sReportType = "3";
                mList.clear();
                adapter.notifyDataSetChanged();
                break;
            case R.id.img_year:
                show_dialog_picker_year();
                break;
            case R.id.img_month:
                show_dialog_picker_month();
                break;
            case R.id.btn_search_report:
                get_api_report();
                break;
        }

    }

    private void get_api_report() {
        showDialogLoading();
        switch (sReportType) {
            case "1":

                break;
            case "2":
                if (sYear == null)
                    return;
                break;
            case "3":
                if (sYear == null || sMonth == null)
                    return;
                break;
        }
        txt_month.setText("Tháng " + sMonth);
        txt_year.setText(sYear);
        sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenter.api_report_default(sUserName, sYear, sMonth, sReportType);
    }
}
