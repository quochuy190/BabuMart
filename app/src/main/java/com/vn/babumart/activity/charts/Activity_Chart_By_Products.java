package com.vn.babumart.activity.charts;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;


import com.vn.babumart.App;
import com.vn.babumart.R;
import com.vn.babumart.adapter.AdapterViewpager;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.ReportProduct;
import com.vn.babumart.models.respon_api.ResponGetReportDefault;
import com.vn.babumart.models.respon_api.ResponGetReportListCTV;
import com.vn.babumart.models.respon_api.ResponGetReportProduct;
import com.vn.babumart.untils.SharedPrefs;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import butterknife.BindView;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 30-May-2019
 * Time: 09:44
 * Version: 1.0
 */
public class Activity_Chart_By_Products extends BaseActivity implements
        View.OnClickListener, InterfaceReport.View {

    @BindView(R.id.ll_tab_layout_content)
    ConstraintLayout ll_tab_layout_content;
    @BindView(R.id.ll_tab_layout_face)
    ConstraintLayout ll_tab_layout_face;
    @BindView(R.id.vp_chart)
    ViewPager view_pager;
    @BindView(R.id.txt_start_time)
    TextView txt_start_time;
    @BindView(R.id.txt_end_time)
    TextView txt_end_time;
    @BindView(R.id.img_start_time)
    ImageView img_start_time;
    @BindView(R.id.img_end_time)
    ImageView img_end_time;
    @BindView(R.id.txt_choose_option)
    TextView txt_choose_option;
    @BindView(R.id.img_down)
    ImageView img_down;
    @BindView(R.id.icon_content_share)
    ImageView icon_content_share;
    @BindView(R.id.icon_face_share)
    ImageView icon_face_share;
    @BindView(R.id.view_share)
    View view_share;
    @BindView(R.id.view_face)
    View view_face;
    AdapterViewpager adapter;
    PresenterReport mPresenter;
    private String sFromDate = "", sToDate = "";
    private String sUserName;
    int page = 1, index = 30;
    String sReportType = "1";

    @Override
    public int setContentViewId() {
        return R.layout.activity_chart_by_product;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_tab_layout_content.setSelected(true);
        mPresenter = new PresenterReport(this);
        initAppbar();
        get_all_history();
        txt_choose_option.setText("Danh mục sản phẩm lớn");
        initData();
        //   setupViewPager(view_pager);
        initEvent();
    }

    public void get_all_history() {
        updateTodate();
        int dayOfMonth = myCalendar_from.get(Calendar.DAY_OF_MONTH);
        myCalendar_from.add(Calendar.DAY_OF_MONTH, -(dayOfMonth - 1));
        updateFromdate();
        sFromDate = txt_start_time.getText().toString();
        sToDate = txt_end_time.getText().toString();
    }

    private void initData() {
        sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        showDialogLoading();
        mPresenter.api_report_item(sUserName, sFromDate, sToDate, sReportType,
                "", "" + page, "" + index);
    /*    mPresenter.api_get_report_fluctuations(sUserName,  "" + year,
                "", "" , "1" , "1");*/
    }

    private void initEvent() {
        ll_tab_layout_content.setOnClickListener(this);
        ll_tab_layout_face.setOnClickListener(this);
        img_end_time.setOnClickListener(this);
        img_start_time.setOnClickListener(this);
        img_down.setOnClickListener(this);
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
        txt_title.setText("Thống kê theo khoảng thời gian");
    }

  /*  private void initSpiner() {
        Spinner spinner = findViewById(R.id.spiner_type_product);
        String[] years = {"Danh mục sản phẩm to", "Danh mục sản phẩm con", "Thuộc tính sản phẩm", "Tất cả sản phẩm"};
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, years);
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner.setAdapter(langAdapter);
    }*/


    private void setupViewPager(ViewPager viewPager) {
        adapter = new AdapterViewpager(getSupportFragmentManager());
        adapter.addFragment(FragmentListTable.getInstance(), "Báo cáo");
        adapter.addFragment(FragmentListPieChart.getInstance(), "Cá nhân");
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        hideDialogLoading_delay();
        ll_tab_layout_content.setSelected(true);
        ll_tab_layout_face.setSelected(false);
        //   setUpTablayout();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                if (position == 0) {
                    icon_content_share.setColorFilter(getResources().getColor(R.color.button));
                    icon_face_share.setColorFilter(getResources().getColor(R.color.app_main));
                    view_share.setBackgroundColor(getResources().getColor(R.color.button));
                    view_face.setBackgroundColor(getResources().getColor(R.color.app_main));
                    ll_tab_layout_content.setSelected(true);
                    ll_tab_layout_face.setSelected(false);
                } else if (position == 1) {
                    icon_content_share.setColorFilter(getResources().getColor(R.color.app_main));
                    icon_face_share.setColorFilter(getResources().getColor(R.color.button));
                    view_share.setBackgroundColor(getResources().getColor(R.color.app_main));
                    view_face.setBackgroundColor(getResources().getColor(R.color.button));
                    ll_tab_layout_content.setSelected(false);
                    ll_tab_layout_face.setSelected(true);
                }
                // Check if this is the page you want.
            }
        });
    }

    Calendar myCalendar_to = Calendar.getInstance();
    Calendar myCalendar_from = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener to_date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar_to.set(Calendar.YEAR, year);
            myCalendar_to.set(Calendar.MONTH, monthOfYear);
            myCalendar_to.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateTodate();
        }

    };

    DatePickerDialog.OnDateSetListener from_date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar_from.set(Calendar.YEAR, year);
            myCalendar_from.set(Calendar.MONTH, monthOfYear);
            myCalendar_from.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateFromdate();
        }

    };

    private void updateFromdate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_start_time.setText(sdf.format(myCalendar_from.getTime()));
    }

    private void updateTodate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_end_time.setText(sdf.format(myCalendar_to.getTime()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_tab_layout_content:
                view_pager.setCurrentItem(0);
                break;
            case R.id.ll_tab_layout_face:
                view_pager.setCurrentItem(1);
                break;
            case R.id.img_start_time:
                new DatePickerDialog(this, R.style.MyDatePickerStyle, from_date, myCalendar_from
                        .get(Calendar.YEAR), myCalendar_from.get(Calendar.MONTH),
                        myCalendar_from.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.img_end_time:
                new DatePickerDialog(this, R.style.MyDatePickerStyle, to_date, myCalendar_to
                        .get(Calendar.YEAR), myCalendar_to.get(Calendar.MONTH),
                        myCalendar_to.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.img_down:
                showDialogOption();
                break;
            case R.id.txt_dangchoduyet:
                sReportType = "1";
                txt_choose_option.setText("Danh mục sản phẩm lớn");
                dialog_yes.dismiss();
                initData();
                break;
            case R.id.txt_datiepnhan:
                sReportType = "2";
                txt_choose_option.setText("Danh mục sản phẩm nhỏ");
                dialog_yes.dismiss();
                initData();
                break;
            case R.id.txt_dangchuyen:
                sReportType = "3";
                txt_choose_option.setText("Thuộc tính sản phẩm");
                dialog_yes.dismiss();
                initData();
                break;
            case R.id.txt_hoanthanh:
                sReportType = "4";
                txt_choose_option.setText("Danh sách sản phẩm");
                dialog_yes.dismiss();
                initData();
                break;
        }
    }

    @Override
    public void show_error_api() {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_report_item(ResponGetReportProduct objRespon) {
        if (objRespon != null && objRespon.getsERROR().equals("0000")) {
            App.mReportProduct = objRespon;
            if (objRespon.getmList() != null) {
                App.mLisReportProduct.clear();
                for (ReportProduct obj : objRespon.getmList()) {
                    obj.setsTypeReport(sReportType);
                    App.mLisReportProduct.add(obj);
                }
                //App.mLisReportProduct.addAll(objRespon.getmList());
                setupViewPager(view_pager);
            }
        } else {
            App.mLisReportProduct.clear();
            setupViewPager(view_pager);
            hideDialogLoading();
            showDialogNotify("Thông báo", objRespon.getsRESULT());
        }
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

    }

    @Override
    public void show_get_report_ctv_detail(ResponGetReportListCTV obj) {

    }

    @Override
    public void show_get_report_fluctuations(ResponGetReportListCTV obj) {

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
        txt_hoanthanh.setOnClickListener(this);
        txt_huy.setVisibility(View.GONE);
        txt_back.setVisibility(View.GONE);
        txt_title.setText("Chọn hình thức báo cáo");
        txt_report_year.setText("Danh mục sản phẩm lớn");
        txt_report_month.setText("Danh mục sản phẩm nhỏ");
        txt_report_day.setText("Thuộc tính sản phẩm");
        txt_hoanthanh.setText("Danh sách sản phẩm");
        dialog_yes.show();

    }

}
