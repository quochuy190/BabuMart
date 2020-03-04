package com.vn.babumart.activity.commission;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vn.babumart.R;
import com.vn.babumart.activity.commission.InterfaceCommission;
import com.vn.babumart.activity.commission.PresenterCommission;
import com.vn.babumart.activity.login.ActivityLogin;
import com.vn.babumart.adapter.AdapterHistoryCommissions;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.callback.ClickDialog;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.ObjCommissions;
import com.vn.babumart.models.respon_api.ResponGetCommission;
import com.vn.babumart.untils.SharedPrefs;
import com.vn.babumart.untils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 23-May-2019
 * Time: 18:02
 * Version: 1.0
 */
public class ActivityHhDetail extends BaseActivity implements InterfaceCommission.View {
    @BindView(R.id.imageView2)
    ImageView ic_date_start;
    @BindView(R.id.imageView3)
    ImageView ic_date_end;
    @BindView(R.id.txt_date_end)
    TextView txt_date_end;
    @BindView(R.id.txt_date_start)
    TextView txt_date_start;
    @BindView(R.id.txt_name_hh_detail)
    TextView txt_name_hh_detail;
    @BindView(R.id.txt_user_hh_detail)
    TextView txt_user_hh_detail;
    @BindView(R.id.txt_phone_hh_detail)
    TextView txt_phone_hh_detail;
    @BindView(R.id.txt_email_hh_detail)
    TextView txt_email_hh_detail;
    @BindView(R.id.txt_total_hh)
    TextView txt_total_hh;
    @BindView(R.id.btn_update_sodu)
    TextView btn_update_sodu;
    @BindView(R.id.btn_search)
    Button btn_search;
    Calendar myCalendar_to = Calendar.getInstance();
    Calendar myCalendar_from = Calendar.getInstance();
    private String sFromDate = "", sToDate = "";
    ObjCommissions objCom;
    String sUserName, sUserCTV, sStartTime, sEndTime;
    int page = 1, numberpage = 50;
    private PresenterCommission mPresenter;

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
        txt_date_start.setText(sdf.format(myCalendar_from.getTime()));
    }

    private void updateTodate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_date_end.setText(sdf.format(myCalendar_to.getTime()));
    }

    public void get_all_history() {
        updateTodate();
        int dayOfMonth = myCalendar_from.get(Calendar.DAY_OF_MONTH);
        myCalendar_from.add(Calendar.DAY_OF_MONTH, -(dayOfMonth - 1));
        updateFromdate();
        sStartTime = txt_date_start.getText().toString();
        sEndTime = txt_date_end.getText().toString();
    }

    @Override
    public int setContentViewId() {
        return R.layout.fragment_commissions_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        mPresenter = new PresenterCommission(this);
        get_all_history();
        init();
        initData();
        initEvent();

    }

    private void initEvent() {
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        btn_update_sodu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mPresenter.api_update_comission(sUserName, sUserCTV, "");
                showDialog_Input_Money();
            }
        });
        ic_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ActivityHhDetail.this, R.style.MyDatePickerStyle, to_date, myCalendar_to
                        .get(Calendar.YEAR), myCalendar_to.get(Calendar.MONTH),
                        myCalendar_to.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ic_date_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ActivityHhDetail.this, R.style.MyDatePickerStyle, from_date, myCalendar_from
                        .get(Calendar.YEAR), myCalendar_from.get(Calendar.MONTH),
                        myCalendar_from.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private List<ObjCommissions> mList;
    private AdapterHistoryCommissions adapterService;
    @BindView(R.id.rcv_history_commission)
    RecyclerView recycle_product;
    RecyclerView.LayoutManager mLayoutManager;

    @SuppressLint("WrongConstant")
    private void init() {
        mList = new ArrayList<>();
        adapterService = new AdapterHistoryCommissions(mList, this);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycle_product.setNestedScrollingEnabled(false);
        recycle_product.setHasFixedSize(true);
        recycle_product.setLayoutManager(mLayoutManager);
        recycle_product.setItemAnimator(new DefaultItemAnimator());
        recycle_product.setAdapter(adapterService);
        adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {

            }
        });
    }

    private void initData() {
        showDialogLoading();
        sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        objCom = (ObjCommissions) getIntent().getSerializableExtra(Constants.KEY_SEND_OBJ_COMMISSION);
        if (objCom != null) {
            sUserCTV = objCom.getMA_CTV();
            set_infor_ctv();
        }
        sStartTime = txt_date_start.getText().toString();
        sEndTime = txt_date_end.getText().toString();
        mPresenter.api_get_withdrawal_history(sUserName, sUserCTV, sStartTime, sEndTime,
                "" + page, "" + numberpage);
    }

    private void set_infor_ctv() {
        if (objCom.getNAMECTV() != null) {
            txt_name_hh_detail.setText("Tên CTV: " + objCom.getNAMECTV());
        } else {
            txt_name_hh_detail.setText("Tên CTV: ...");
        }
        if (objCom.getUSER_CODE() != null) {
            txt_user_hh_detail.setText("Mã CTV: " + objCom.getUSER_CODE());
        } else {
            txt_user_hh_detail.setText("Mã CTV: ...");
        }
        if (objCom.getUSER_CODE() != null) {
            txt_user_hh_detail.setText("Mã CTV: " + objCom.getUSER_CODE());
        } else {
            txt_user_hh_detail.setText("Mã CTV: ...");
        }
        if (objCom.getMA_CTV() != null) {
            txt_phone_hh_detail.setText("SĐT: " + objCom.getMA_CTV());
        } else {
            txt_phone_hh_detail.setText("SĐT: ...");
        }
        txt_total_hh.setText("0 đ");
        txt_email_hh_detail.setText("Email: ...");
    }

    @Override
    public void show_error_api() {

    }

    @Override
    public void show_get_commission(ResponGetCommission obj) {

    }

    @Override
    public void show_get_withdrawal(ResponGetCommission obj) {

    }


    @Override
    public void show_get_withdrawal_history(ResponGetCommission obj) {
        hideDialogLoading();
        if (obj.getsERROR().equals("0000")) {
            if (obj.getmList() != null) {
                mList.clear();
                mList.addAll(obj.getmList());
                ObjCommissions objCommis = mList.get(0);
                if (objCommis != null && objCommis.getEMAIL() != null) {
                    txt_email_hh_detail.setText("Email: " + objCommis.getEMAIL());
                } else
                    txt_email_hh_detail.setText("Email: ...");
                if (objCommis.getTOTAL_HH() != null) {
                    txt_total_hh.setText(StringUtil.conventMonney(objCommis.getTOTAL_HH()));
                } else {
                    txt_total_hh.setText("0 đ");
                }
            }
            adapterService.notifyDataSetChanged();
        } else if (obj.getsERROR().equals("0002")) {
            showDialogComfirm("Thông báo", obj.getsRESULT(), false, new ClickDialog() {
                @Override
                public void onClickYesDialog() {
                    startActivity(new Intent(ActivityHhDetail.this, ActivityLogin.class));
                }

                @Override
                public void onClickNoDialog() {

                }
            });
        } else showDialogNotify("Thông báo", obj.getsRESULT());
    }

    @Override
    public void show_get_request_withdrawal(ErrorApi obj) {

    }

    @Override
    public void show_update_comission(ErrorApi obj) {
        hideDialogLoading();
        if (obj != null && obj.getsERROR() != null && obj.getsERROR().equals("0000")) {
            initData();

        } else showDialogNotify("Thông báo", obj.getsRESULT());
    }

    @Override
    public void show_update_withdrawal(ErrorApi obj) {

    }
    Dialog dialog;
    EditText edt_input;
    public void showDialog_Input_Money() {
        dialog = new Dialog(this, R.style.Theme_Dialog);
        // dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edt_input_money);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView txt_title = (TextView) dialog.findViewById(R.id.txt_title);
        edt_input = (EditText) dialog.findViewById(R.id.edt_input_money);
        TextView btn_dongy = (TextView) dialog.findViewById(R.id.btn_dongy);
        btn_dongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                get_api_update_commission();
            }
        });

        dialog.show();

    }
    private static String prefix = "VND ";
    private void get_api_update_commission() {
        String amount = edt_input.getText().toString();
        String cleanString = amount.replace(prefix, "").replaceAll("[,]", "");
        if (cleanString.length() > 0 && (Integer.parseInt(cleanString) >= 100)) {
            showDialogLoading();
            mPresenter.api_update_comission(sUserName, sUserCTV, cleanString);
        } else {
            showAlertDialog("Thông báo", "Bạn phải trả tối thiểu là 50.000 VNĐ");
        }
    }
}
