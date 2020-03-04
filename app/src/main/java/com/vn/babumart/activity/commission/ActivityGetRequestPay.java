package com.vn.babumart.activity.commission;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vn.babumart.R;
import com.vn.babumart.adapter.AdapterRequestPay;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.ObjCommissions;
import com.vn.babumart.models.respon_api.ResponGetCommission;
import com.vn.babumart.untils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 17-June-2019
 * Time: 15:58
 * Version: 1.0
 */
public class ActivityGetRequestPay extends BaseActivity implements InterfaceCommission.View {
    private List<ObjCommissions> mList;
    private List<ObjCommissions> mListRequest;
    private AdapterRequestPay adapter;
    @BindView(R.id.rcv_list_report_pay)
    RecyclerView recycleview;
    RecyclerView.LayoutManager mLayoutManager;
    PresenterCommission mPresenter;
    String sTrangthai = "0";

    @Override
    public int setContentViewId() {
        return R.layout.activity_request_commission;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterCommission(this);
        initAppbar();
        init();
        initData();
        set_data_spinner();
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
        txt_title.setText("Yêu cầu rút hoa hồng");
    }

    private void initData() {
        showDialogLoading();
        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenter.api_get_withdrawal(sUserName, "1", "150");
    }

    @BindView(R.id.spinner)
    Spinner spiner_maKH;
    List<String> mLisMaKH = new ArrayList<>();

    private void set_data_spinner() {
        mLisMaKH.add("Chưa xử lý");
        mLisMaKH.add("Đã đồng ý");
        mLisMaKH.add("Đã từ chối");
        mLisMaKH.add("Tất cả trạng thái");
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_spinner, mLisMaKH);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spiner_maKH.setAdapter(adapter);
        spiner_maKH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        sTrangthai = "0";
                        set_status_all();
                        break;
                    case 1:
                        sTrangthai = "1";
                        set_status_all();
                        break;
                    case 2:
                        sTrangthai = "2";
                        set_status_all();
                        break;
                    case 3:
                        sTrangthai = "3";
                        set_status_all();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @SuppressLint("WrongConstant")
    private void init() {
        mListRequest = new ArrayList<>();
        mList = new ArrayList<>();
        adapter = new AdapterRequestPay(mListRequest, this);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleview.setNestedScrollingEnabled(false);
        recycleview.setHasFixedSize(true);
        recycleview.setLayoutManager(mLayoutManager);
        recycleview.setItemAnimator(new DefaultItemAnimator());
        recycleview.setAdapter(adapter);
        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                Intent intent = new Intent(ActivityGetRequestPay.this, ActivityHhDetail.class);
                ObjCommissions obj = (ObjCommissions) item;
                intent.putExtra(Constants.KEY_SEND_OBJ_COMMISSION, obj);
                startActivity(intent);
            }
        });
    }

    @Override
    public void show_error_api() {
        hideDialogLoading();
    }

    @Override
    public void show_get_commission(ResponGetCommission obj) {

    }

    @Override
    public void show_get_withdrawal(ResponGetCommission obj) {
        hideDialogLoading();
        mList.clear();
        if (obj.getsERROR().equals("0000")) {
            if (obj.getmList() != null) {
                mList.addAll(obj.getmList());
                set_status_all();
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void set_status_all() {
        mListRequest.clear();
        if (sTrangthai.equals("0")) {
            for (ObjCommissions obj : mList) {
                if (obj.getIS_PROCESS().equals("0"))
                    mListRequest.add(obj);
            }
        } else if (sTrangthai.equals("1")) {
            for (ObjCommissions obj : mList) {
                if (obj.getIS_PROCESS().equals("1"))
                    mListRequest.add(obj);
            }
        } else if (sTrangthai.equals("3")) {
            for (ObjCommissions obj : mList) {
                mListRequest.add(obj);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void show_get_withdrawal_history(ResponGetCommission obj) {

    }

    @Override
    public void show_get_request_withdrawal(ErrorApi obj) {

    }

    @Override
    public void show_update_comission(ErrorApi obj) {

    }

    @Override
    public void show_update_withdrawal(ErrorApi obj) {

    }
}
