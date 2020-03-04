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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.shawnlin.numberpicker.NumberPicker;
import com.vn.babumart.R;
import com.vn.babumart.adapter.AdapterReportListCTV;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.ReportListCTV;
import com.vn.babumart.models.respon_api.ResponGetReportDefault;
import com.vn.babumart.models.respon_api.ResponGetReportListCTV;
import com.vn.babumart.models.respon_api.ResponGetReportProduct;
import com.vn.babumart.untils.SharedPrefs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 25-June-2019
 * Time: 09:06
 * Version: 1.0
 */
public class ActivityChartListCTV extends BaseActivity implements InterfaceReport.View {
    RecyclerView.LayoutManager mLayoutManager;
    private PresenterReport mPresenter;
    private List<ReportListCTV> mList;
    private AdapterReportListCTV adapter;
    @BindView(R.id.rcv_list_report_ctv)
    RecyclerView rcv_report;
    @BindView(R.id.txt_choose_year)
    TextView txt_choose_year;
    @BindView(R.id.txt_choose_month)
    TextView txt_choose_month;
    @BindView(R.id.img_select_year)
    ImageView img_select_year;
    @BindView(R.id.img_select_month)
    ImageView img_select_month;
    @BindView(R.id.btn_search_report)
    Button btn_search_report;
    private String sUserName;
    private String sYear = "", sMonth = "";
    private boolean isTypeCTV = true;

    @Override
    public int setContentViewId() {
        return R.layout.activity_chart_list_ctv;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterReport(this);
        initAppbar();
        txt_choose_year.setText("" + year);
        sMonth = "";
        txt_choose_month.setText("Cả năm");
        initData();
        init();
        initEvent();
    }
    private void initEvent() {
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTypeCTV = !isTypeCTV;
                if (isTypeCTV) {
                    txt_title.setText("Báo cáo theo CTV");
                } else
                    txt_title.setText("Báo cáo theo đơn hàng");
                initData();
            }
        });
        btn_search_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        img_select_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialog_picker_year();
            }
        });
        img_select_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialog_picker_month();
            }
        });
    }
    private void init() {
        mList = new ArrayList<>();
        adapter = new AdapterReportListCTV(mList, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        rcv_report.setNestedScrollingEnabled(false);
        rcv_report.setHasFixedSize(true);
        rcv_report.setLayoutManager(mLayoutManager);
        rcv_report.setItemAnimator(new DefaultItemAnimator());
        rcv_report.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                if (position!=0){
                    ReportListCTV obj = (ReportListCTV) item;
                    obj.setsYear(sYear);
                    obj.setsMonth(sMonth);
                    Intent intent = new Intent(ActivityChartListCTV.this,
                            ActivityReportCTVDetail.class);
                    intent.putExtra(Constants.KEY_SEND_OBJ_REPORT_CTV, obj);
                    startActivity(intent);
                }
            }
        });
    }

    private void initData() {
        showDialogLoading();
        String sType;
        sYear = txt_choose_year.getText().toString();
        if (isTypeCTV) {
            sType = "1";
        } else
            sType = "2";
        sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenter.api_get_report_ctv(sUserName, sYear, sMonth, sType);
    }

    ImageView img_home;
    TextView txt_title;

    private void initAppbar() {
        img_home = findViewById(R.id.img_home);
        ImageView img_back = findViewById(R.id.img_back);
        txt_title = findViewById(R.id.txt_title);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_title.setText("Báo cáo theo CTV");
        img_home.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.reload).into(img_home);
    }

    @Override
    public void show_error_api() {

    }

    @Override
    public void show_report_item(ResponGetReportProduct objRespon) {

    }

    @Override
    public void show_report_default(ResponGetReportDefault obj) {

    }

    @Override
    public void show_get_sub_product(ResponGetReportDefault obj) {

    }

    @Override
    public void show_get_sub_product_child(ResponGetReportDefault obj) {

    }

    @Override
    public void show_get_report_ctv(ResponGetReportListCTV obj) {
        hideDialogLoading();
        if (obj != null && obj.getsERROR().equals("0000")) {
            mList.clear();
            mList.add(new ReportListCTV());
            mList.addAll(obj.getmList());
            adapter.notifyDataSetChanged();
        } else if (obj != null && obj.getsRESULT() != null) {
            showDialogNotify("Thông báo", obj.getsRESULT());
        }
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
                txt_choose_year.setText(sYear);
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
                txt_choose_month.setText("Tháng " + sMonth);
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
}
