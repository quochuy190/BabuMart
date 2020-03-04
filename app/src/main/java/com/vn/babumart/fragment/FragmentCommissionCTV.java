package com.vn.babumart.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.babumart.R;
import com.vn.babumart.activity.commission.InterfaceCommission;
import com.vn.babumart.activity.commission.PresenterCommission;
import com.vn.babumart.activity.login.ActivityConfirmOTP;
import com.vn.babumart.activity.login.ActivityLogin;
import com.vn.babumart.adapter.AdapterHistoryCommissions;
import com.vn.babumart.base.BaseFragment;
import com.vn.babumart.callback.ClickDialog;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.CurrencyEditText;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.MessageEvent;
import com.vn.babumart.models.ObjCommissions;
import com.vn.babumart.models.respon_api.ResponGetCommission;
import com.vn.babumart.untils.SharedPrefs;
import com.vn.babumart.untils.StringUtil;
import com.vn.babumart.untils.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 22-April-2019
 * Time: 10:30
 * Version: 1.0
 */
public class FragmentCommissionCTV extends BaseFragment implements InterfaceCommission.View {
    private static final String TAG = "FragmentSetup";
    private static String prefix = "VND ";
    public static FragmentCommissionCTV fragment;
    @BindView(R.id.txt_date_end)
    TextView txt_date_end;
    @BindView(R.id.txt_date_start)
    TextView txt_date_start;
    @BindView(R.id.btn_get_money)
    TextView btn_get_money;
    @BindView(R.id.ll_all)
    ConstraintLayout ll_all;
    Calendar myCalendar_to = Calendar.getInstance();
    Calendar myCalendar_from = Calendar.getInstance();
    String sUserName, sUserCTV, sStartTime, sEndTime;
    private List<ObjCommissions> mList;
    private AdapterHistoryCommissions adapterService;
    @BindView(R.id.recycle_list_hh)
    RecyclerView recycle_product;
    @BindView(R.id.edt_currency)
    CurrencyEditText edt_search_hh;
    @BindView(R.id.txt_total_hh)
    TextView txt_total_hh;
    @BindView(R.id.btn_search)
    Button btn_search;
    @BindView(R.id.ic_date_end)
    ImageView ic_date_end;
    @BindView(R.id.ic_date_start)
    ImageView ic_date_start;
    RecyclerView.LayoutManager mLayoutManager;
    int page = 1, numberpage = 50;
    private PresenterCommission mPresenter;
    boolean isLogin;

    public static FragmentCommissionCTV getInstance() {
        if (fragment == null) {
            synchronized (FragmentCommissionCTV.class) {
                if (fragment == null)
                    fragment = new FragmentCommissionCTV();
            }
        }
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commissions_ctv, container, false);
        ButterKnife.bind(this, view);
        Log.e(TAG, "onCreateView: Setup");
        mPresenter = new PresenterCommission(this);
        ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        init();
        get_all_history();
        initData();
        initEvent();
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(Constants.EventBus.KEY_LOGIN_GUEST)) {
            initData();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }
    private String current = "";

    private void initEvent() {
        ic_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), R.style.MyDatePickerStyle, to_date, myCalendar_to
                        .get(Calendar.YEAR), myCalendar_to.get(Calendar.MONTH),
                        myCalendar_to.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ic_date_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), R.style.MyDatePickerStyle, from_date, myCalendar_from
                        .get(Calendar.YEAR), myCalendar_from.get(Calendar.MONTH),
                        myCalendar_from.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        btn_get_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = edt_search_hh.getText().toString();
                Log.i(TAG, "onClick: " + amount);
                String cleanString = amount.replace(prefix, "").replaceAll("[,]", "");
                Log.i(TAG, "onClick: " + cleanString);
                if (cleanString.length() > 0 && (Integer.parseInt(cleanString) >= 500000)) {
                    showDialogLoading();
                    mPresenter.api_get_request_withdrawal(sUserName, cleanString);
                } else {
                    showAlertDialog("Thông báo", "Bạn phải yêu cầu rút tối thiểu là 500.000 VNĐ");
                }

            }
        });
    }

    public void get_all_history() {
        updateTodate();
        int dayOfMonth = myCalendar_from.get(Calendar.DAY_OF_MONTH);
        myCalendar_from.add(Calendar.DAY_OF_MONTH, -(dayOfMonth - 1));
        updateFromdate();
        sStartTime = txt_date_start.getText().toString();
        sEndTime = txt_date_end.getText().toString();
    }

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

    @SuppressLint("WrongConstant")
    private void init() {
        mList = new ArrayList<>();
        adapterService = new AdapterHistoryCommissions(mList, getContext());
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
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
        sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        sStartTime = txt_date_start.getText().toString();
        sEndTime = txt_date_end.getText().toString();
        isLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_IS_LOGIN, Boolean.class);
        if (isLogin) {
            if (TimeUtils.compare_two_date(sStartTime, sEndTime,
                    "dd/MM/yyyy", "dd/MM/yyyy")) {
                showDialogLoading();
                mPresenter.api_get_withdrawal_history(sUserName, sUserName, sStartTime, sEndTime,
                        "" + page, "" + numberpage);
            } else {
                hideDialogLoading();
                showAlertDialog("Thông báo", "Thời gian nhập vào không hợp lệ");
            }
        } else {
            showDialogComfirm("Thông báo",
                    getString(R.string.txt_guild_guest_product_detail),
                    true, new ClickDialog() {
                        @Override
                        public void onClickYesDialog() {
                            Intent intent = new Intent(getContext(),
                                    ActivityConfirmOTP.class);
                            intent.putExtra(Constants.KEY_SEND_LOGIN_GUEST, true);
                            startActivityForResult(intent, Constants.RequestCode.GET_LOGIN_BUY);
                        }

                        @Override
                        public void onClickNoDialog() {

                        }
                    });
        }

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
        mList.clear();
        if (obj.getsERROR().equals("0000")) {
            if (obj.getmList() != null) {
                mList.addAll(obj.getmList());
                if (obj.getmList().get(0) != null) {
                    txt_total_hh.setText(StringUtil.conventMonney(obj.getmList().get(0).getTOTAL_HH()));
                } else {
                    txt_total_hh.setText("0 đ");
                }
            }
        } else if (obj.getsERROR().equals("0002")) {
            showDialogComfirm("Thông báo", obj.getsRESULT(), false, new ClickDialog() {
                @Override
                public void onClickYesDialog() {
                    startActivity(new Intent(getActivity(), ActivityLogin.class));
                }

                @Override
                public void onClickNoDialog() {

                }
            });
        }
        adapterService.notifyDataSetChanged();
    }

    @Override
    public void show_get_request_withdrawal(ErrorApi obj) {
        hideDialogLoading();
        if (obj != null && obj.getsERROR().equals("0000")) {
            edt_search_hh.setText("0");
            showAlertDialog("Thông báo", "Yêu cầu rút hoa hồng của bạn đã được gửi đến shop thành công.");

        } else {
            showAlertDialog("Thông báo", obj.getsRESULT());
        }
    }

    @Override
    public void show_update_comission(ErrorApi obj) {

    }

    @Override
    public void show_update_withdrawal(ErrorApi obj) {

    }


    private static class CurrencyTextWatcher implements TextWatcher {
        private static String prefix = "VND ";
        private static final int MAX_LENGTH = 20;
        private static final int MAX_DECIMAL = 3;
        private final EditText editText;
        private String previousCleanString;

        CurrencyTextWatcher(EditText editText, String prefix) {
            this.editText = editText;
            this.prefix = prefix;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // do nothing
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String str = editable.toString();
            if (str.length() < prefix.length()) {
                editText.setText(prefix);
                editText.setSelection(prefix.length());
                return;
            }
            if (str.equals(prefix)) {
                return;
            }
            // cleanString this the string which not contain prefix and ,
            String cleanString = str.replace(prefix, "").replaceAll("[,]", "");
            // for prevent afterTextChanged recursive call
            if (cleanString.equals(previousCleanString) || cleanString.isEmpty()) {
                return;
            }
            previousCleanString = cleanString;

            String formattedString;
            if (cleanString.contains(".")) {
                formattedString = formatDecimal(cleanString);
            } else {
                formattedString = formatInteger(cleanString);
            }
            editText.removeTextChangedListener(this); // Remove listener
            editText.setText(formattedString);
            handleSelection();
            editText.addTextChangedListener(this); // Add back the listener
        }

        private String formatInteger(String str) {
            BigDecimal parsed = new BigDecimal(str);
            DecimalFormat formatter =
                    new DecimalFormat(prefix + "#,###", new DecimalFormatSymbols(Locale.US));
            return formatter.format(parsed);
        }

        private String formatDecimal(String str) {
            if (str.equals(".")) {
                return prefix + ".";
            }
            BigDecimal parsed = new BigDecimal(str);
            // example pattern VND #,###.00
            DecimalFormat formatter = new DecimalFormat(prefix + "#,###." + getDecimalPattern(str),
                    new DecimalFormatSymbols(Locale.US));
            formatter.setRoundingMode(RoundingMode.DOWN);
            return formatter.format(parsed);
        }

        /**
         * It will return suitable pattern for format decimal
         * For example: 10.2 -> return 0 | 10.23 -> return 00, | 10.235 -> return 000
         */
        private String getDecimalPattern(String str) {
            int decimalCount = str.length() - str.indexOf(".") - 1;
            StringBuilder decimalPattern = new StringBuilder();
            for (int i = 0; i < decimalCount && i < MAX_DECIMAL; i++) {
                decimalPattern.append("0");
            }
            return decimalPattern.toString();
        }

        private void handleSelection() {
            if (editText.getText().length() <= MAX_LENGTH) {
                editText.setSelection(editText.getText().length());
            } else {
                editText.setSelection(MAX_LENGTH);
            }
        }
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
