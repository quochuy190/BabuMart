package com.vn.babumart.activity.charts;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.babumart.R;
import com.vn.babumart.adapter.AdapterReportListCTVDetail;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.models.ReportListCTV;
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
 * Date: 25-June-2019
 * Time: 14:27
 * Version: 1.0
 */
public class ActivityReportCTVDetail extends BaseActivity implements InterfaceReport.View {
    RecyclerView.LayoutManager mLayoutManager;
    private PresenterReport mPresenter;
    private List<ReportListCTV> mList;
    private AdapterReportListCTVDetail adapter;
    @BindView(R.id.rcv_report_ctv_detail)
    RecyclerView rcv_report;
    @BindView(R.id.txt_name_CTV)
    TextView txt_name_CTV;
    ReportListCTV objRoportCTV;
    private String sUserName;
    private int page = 1, number = 30;
    @BindView(R.id.txt_user_CTV)
    TextView txt_user_CTV;
    @BindView(R.id.txt_phone_CTV)
    TextView txt_phone_CTV;
    @BindView(R.id.txt_email)
    TextView txt_email;
    int year = Calendar.getInstance().get(Calendar.YEAR);

    @Override
    public int setContentViewId() {
        return R.layout.activity_report_ctv_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterReport(this);
        initAppbar();
        init();
        initData();
    }

    private ObjLogin mLogin;

    private void initData() {
        if (StringUtil.check_login_group()) {
            mLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
            sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
            if (mLogin.getGROUPS().equals("5")) {
                txt_name_CTV.setText("Tên CTV: " + mLogin.getFULL_NAME());
                txt_user_CTV.setText("Mã CTV: " + mLogin.getUSERNAME());
                showDialogLoading();
                mPresenter.api_get_report_ctv_detail(sUserName, sUserName, "" + year,
                        "", "" + page, "" + number);
            } else {
                objRoportCTV = (ReportListCTV) getIntent().getSerializableExtra(Constants.KEY_SEND_OBJ_REPORT_CTV);
                if (objRoportCTV != null) {
                    if (objRoportCTV.getFULL_NAME() != null)
                        txt_name_CTV.setText("Tên CTV: " + objRoportCTV.getFULL_NAME());
                    if (objRoportCTV.getCREATE_BY() != null)
                        txt_user_CTV.setText("Mã CTV: " + objRoportCTV.getCREATE_BY());
                }
                showDialogLoading();
                if (objRoportCTV != null && objRoportCTV.getCREATE_BY() != null) {
                    mPresenter.api_get_report_ctv_detail(sUserName, objRoportCTV.getCREATE_BY(), objRoportCTV.getsYear(),
                            objRoportCTV.getsMonth(), "" + page, "" + number);
                }
            }
            mPresenter.api_get_report_fluctuations(sUserName, "" + year,
                    "", "", "1", "1");

        } else {
            showDialogNotify("Thông báo", "Mời bạn đăng nhập để tiếp tục sử dụng chương trình");
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
        txt_title.setText("Báo cáo theo CTV");
    }

    private void init() {
        mList = new ArrayList<>();
        adapter = new AdapterReportListCTVDetail(mList, this);
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

            }
        });
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

    }

    @Override
    public void show_get_report_ctv_detail(ResponGetReportListCTV obj) {
        hideDialogLoading();

        if (obj != null && obj.getsERROR() != null && obj.getsERROR().equals("0000")) {
            if (obj.getEMAIL() != null)
                txt_email.setText("Email: " + obj.getEMAIL());
            if (obj.getMOBILE() != null)
                txt_phone_CTV.setText("Số điện thoại: " + obj.getMOBILE());
            mList.clear();
            mList.add(new ReportListCTV());
            mList.addAll(obj.getmList());
            adapter.notifyDataSetChanged();
        } else if (obj != null && obj.getsRESULT() != null) {
            showDialogNotify("Thông báo", obj.getsRESULT());
        }
    }

    @Override
    public void show_get_report_fluctuations(ResponGetReportListCTV obj) {

    }
}
