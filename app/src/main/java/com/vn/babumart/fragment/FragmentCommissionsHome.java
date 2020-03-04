package com.vn.babumart.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.babumart.R;
import com.vn.babumart.activity.commission.ActivityGetRequestPay;
import com.vn.babumart.activity.commission.ActivityHhDetail;
import com.vn.babumart.activity.commission.InterfaceCommission;
import com.vn.babumart.activity.commission.PresenterCommission;
import com.vn.babumart.activity.login.ActivityLogin;
import com.vn.babumart.adapter.AdapterListCommissions;
import com.vn.babumart.base.BaseFragment;
import com.vn.babumart.callback.ClickDialog;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.ObjCommissions;
import com.vn.babumart.models.respon_api.ResponGetCommission;
import com.vn.babumart.untils.SharedPrefs;
import com.vn.babumart.untils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author Quốc Huy
 * @version 1.0.0
 * @description
 * @desc Developer NEO Company.
 * @created 8/6/2018
 * @updated 8/6/2018
 * @modified by
 * @updated on 8/6/2018
 * @since 1.0
 */
public class FragmentCommissionsHome extends BaseFragment implements InterfaceCommission.View {
    private static final String TAG = "FragmentOrder";
    public static FragmentCommissionsHome fragment;
    private List<ObjCommissions> mList;
    private List<ObjCommissions> temp;
    private AdapterListCommissions adapterService;
    @BindView(R.id.recycle_list_commissions)
    RecyclerView recycle_list_hh;
    @BindView(R.id.edt_search_hh)
    EditText edt_search_hh;
    @BindView(R.id.txt_total_hh)
    TextView txt_total_hh;
    @BindView(R.id.txt_number_yeucau)
    TextView txt_number_yeucau;
    @BindView(R.id.ll_fragment_commission)
    ConstraintLayout ll_fragment_commission;
    @BindView(R.id.btn_show_thanhtoan)
    TextView btn_show_thanhtoan;
    RecyclerView.LayoutManager mLayoutManager;
    int page = 1, index = 30;
    String sUserName = "", sUserCTV = "";
    private PresenterCommission mPresenter;

    public static FragmentCommissionsHome getInstance() {
        if (fragment == null) {
            synchronized (FragmentCommissionsHome.class) {
                if (fragment == null)
                    fragment = new FragmentCommissionsHome();
            }
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commissions_home, container, false);
        ButterKnife.bind(this, view);
        Log.e(TAG, "onCreateView: Order");
        mPresenter = new PresenterCommission(this);
        init();
        txt_number_yeucau.setText("Có 0 yêu cầu thanh toán mới");
        initEvent();
        initData();
        return view;
    }

    private void initEvent() {
        btn_show_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ActivityGetRequestPay.class));
            }
        });
        ll_fragment_commission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        edt_search_hh.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    @SuppressLint("WrongConstant")
    private void init() {
        mList = new ArrayList<>();
        adapterService = new AdapterListCommissions(mList, getContext());
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycle_list_hh.setNestedScrollingEnabled(false);
        recycle_list_hh.setHasFixedSize(true);
        recycle_list_hh.setLayoutManager(mLayoutManager);
        recycle_list_hh.setItemAnimator(new DefaultItemAnimator());
        recycle_list_hh.setAdapter(adapterService);
        adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                Intent intent = new Intent(getContext(), ActivityHhDetail.class);
                ObjCommissions obj = (ObjCommissions) item;
                intent.putExtra(Constants.KEY_SEND_OBJ_COMMISSION, obj);
                getActivity().startActivity(intent);
            }
        });
    }

    private void initData() {
        showDialogLoading();
        sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenter.api_get_commission(sUserName, sUserCTV, "" + page, "" + index);
    }


    void filter(String text) {
        temp = new ArrayList<>();
        for (ObjCommissions d : mList) {
            String sName = StringUtil.removeAccent(d.getNAMECTV().toLowerCase());
            String sUser = StringUtil.removeAccent(d.getMA_CTV().toLowerCase());
            String sInput = StringUtil.removeAccent(text.toLowerCase());
            if (sName.contains(sInput) || sUser.contains(sInput)) {
                //adding the element to filtered list
                temp.add(d);
            }
        }
        adapterService.updateList(temp);
        adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                Intent intent = new Intent(getContext(), ActivityHhDetail.class);
                ObjCommissions obj = (ObjCommissions) item;
                intent.putExtra(Constants.KEY_SEND_OBJ_COMMISSION, obj);
                getActivity().startActivity(intent);
            }
        });
    }


    @Override
    public void show_error_api() {

    }

    @Override
    public void show_get_commission(ResponGetCommission obj) {
        hideDialogLoading();
        if (obj != null && obj.getsERROR().equals("0000")) {
            // mList.clear();
            mList.addAll(obj.getmList());
            adapterService.notifyDataSetChanged();
            if (obj.getTONGHH() != null) {
                txt_total_hh.setText(StringUtil.conventMonney(obj.getTONGHH()));
            } else
                txt_total_hh.setText("0 đ");
            if (obj.getTONGRUT() != null) {
                txt_number_yeucau.setText("Có " + obj.getTONGRUT() + " yêu cầu thanh toán mới");
            } else
                txt_number_yeucau.setText("Có 0 yêu cầu thanh toán mới");
        } else if (obj.getsERROR().equals("0002")) {
            showDialogComfirm("Thông báo", obj.getsRESULT(), false, new ClickDialog() {
                @Override
                public void onClickYesDialog() {
                    startActivity(new Intent(getContext(), ActivityLogin.class));
                    getActivity().finish();
                }

                @Override
                public void onClickNoDialog() {

                }
            });
        }
    }

    @Override
    public void show_get_withdrawal(ResponGetCommission obj) {

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
    boolean isDoubleClick;
    public void fragmentBackTack() {
        if (isDoubleClick) {
            getActivity().finish();
            return;
        }
        this.isDoubleClick = true;
        Toast.makeText(getContext(), "Chạm lần nữa để thoát", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isDoubleClick = false;
            }
        }, 2000);
    }
}
