package com.vn.babumart.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.vn.babumart.App;
import com.vn.babumart.R;
import com.vn.babumart.activity.collaborators.FragmentListCTV;
import com.vn.babumart.activity.login.ActivityConfirmOTP;
import com.vn.babumart.activity.login.ActivityLogin;
import com.vn.babumart.activity.order.ActivityHistoryOrderDetail;
import com.vn.babumart.activity.order.InterfaceOrder;
import com.vn.babumart.activity.order.PresenterOrder;
import com.vn.babumart.adapter.AdapterDanhsachDathang;
import com.vn.babumart.base.BaseFragment;
import com.vn.babumart.callback.ClickDialog;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.CustomEvent;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.MessageEvent;
import com.vn.babumart.models.ObjCTV;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.models.ObjOrder;
import com.vn.babumart.models.respon_api.ResponGetProduct;
import com.vn.babumart.models.respon_api.ResponHistoryOrder;
import com.vn.babumart.untils.SharedPrefs;
import com.vn.babumart.untils.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import static android.app.Activity.RESULT_OK;

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
public class FragmentOrder extends BaseFragment
        implements View.OnClickListener, InterfaceOrder.View,
        SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "FragmentOrder";
    private PresenterOrder mPresenter;
    public static FragmentOrder fragment;
    private List<ObjOrder> mList;
    private AdapterDanhsachDathang adapterService;
    @BindView(R.id.recycle_list_order)
    RecyclerView recycle_list_order;
    @BindView(R.id.txt_date_end)
    TextView txt_date_end;
    @BindView(R.id.txt_date_start)
    TextView txt_date_start;
    @BindView(R.id.ll_fragment_order)
    ConstraintLayout ll_fragment_order;
    RecyclerView.LayoutManager mLayoutManager;
    Calendar myCalendar_to = Calendar.getInstance();
    Calendar myCalendar_from = Calendar.getInstance();
    @BindView(R.id.img_date_start)
    ImageView img_time_start;
    @BindView(R.id.img_date_end)
    ImageView img_time_end;
    @BindView(R.id.img_down_filter_CTV)
    ImageView img_down_filter_CTV;
    @BindView(R.id.img_down_filter_status)
    ImageView img_down_filter_status;
    @BindView(R.id.btn_filter)
    Button btn_filter;
    @BindView(R.id.txt_ctv)
    TextView txt_ctv;
    @BindView(R.id.txt_total_order)
    TextView txt_total_order;
    @BindView(R.id.txt_trangthai)
    TextView txt_item_order_status;
    @BindView(R.id.ll_filter_CTV)
    ConstraintLayout ll_filter_CTV;
    private String sFromDate = "", sToDate = "";
    private String sUserName;
    private ObjCTV mCTV;
    private int page = 1, index = 30;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean isLoading = true;
    @BindView(R.id.pull_refresh_product)
    SwipeRefreshLayout pull_refresh_product;
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
        Date strDate = myCalendar_from.getTime();
        Date sDateEnd = myCalendar_to.getTime();
        if (System.currentTimeMillis() > strDate.getTime()) {
            if (strDate.getTime() < sDateEnd.getTime()) {
                txt_date_start.setText(sdf.format(myCalendar_from.getTime()));
            } else
                showAlertDialog("Thông báo", "Thời gian không hợp lệ, mời chọn lại ");
        } else
            showAlertDialog("Thông báo", "Thời gian không hợp lệ, mời chọn lại ");
    }

    private void updateTodate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date strDate = myCalendar_to.getTime();
        Date strDateStart = myCalendar_from.getTime();
        if (strDate.getTime() < System.currentTimeMillis()) {
            if (strDate.getTime() >= strDateStart.getTime()) {
                txt_date_end.setText(sdf.format(myCalendar_to.getTime()));
            } else
                showAlertDialog("Thông báo", "Thời gian không hợp lệ, mời chọn lại ");
        } else {
            showAlertDialog("Thông báo", "Thời gian không hợp lệ, mời chọn lại ");
        }
    }

    public static FragmentOrder getInstance() {
        if (fragment == null) {
            synchronized (FragmentOrder.class) {
                if (fragment == null)
                    fragment = new FragmentOrder();
            }
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new PresenterOrder(this);
        init();
        get_all_history();
        initData();
        initEvent();
        initPulltoRefesh();
        return view;
    }

    private void initEvent() {
        img_down_filter_CTV.setOnClickListener(this);
        img_down_filter_status.setOnClickListener(this);
        img_time_end.setOnClickListener(this);
        img_time_start.setOnClickListener(this);
        ll_fragment_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin) {
                    page = 1;
                    showDialogLoading();
                    get_api();
                }else {
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
        });
    }

    public void get_all_history() {
        int dayOfMonth = myCalendar_from.get(Calendar.DAY_OF_MONTH);
        myCalendar_from.add(Calendar.DAY_OF_MONTH, -(dayOfMonth - 1));
        updateTodate();
        updateFromdate();
        sFromDate = txt_date_start.getText().toString();
        sToDate = txt_date_end.getText().toString();
    }

    private void init() {
        mList = new ArrayList<>();
        adapterService = new AdapterDanhsachDathang(mList, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        recycle_list_order.setNestedScrollingEnabled(false);
        recycle_list_order.setHasFixedSize(true);
        recycle_list_order.setLayoutManager(mLayoutManager);
        recycle_list_order.setItemAnimator(new DefaultItemAnimator());
        recycle_list_order.setAdapter(adapterService);
        adapterService.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                Intent intent = new Intent(getContext(), ActivityHistoryOrderDetail.class);
                ObjOrder obj = (ObjOrder) item;
                intent.putExtra(Constants.KEY_SAND_OBJ_ORDER, obj);
                startActivityForResult(intent, Constants.RequestCode.GET_ADD_ORDER);
            }
        });

        // loadmore
        recycle_list_order.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isRefresh) {
                    if (dy > 0) {
                        GridLayoutManager layoutmanager = (GridLayoutManager) recyclerView
                                .getLayoutManager();
                        visibleItemCount = layoutmanager.getChildCount();
                        totalItemCount = layoutmanager.getItemCount();
                        pastVisiblesItems = layoutmanager.findFirstVisibleItemPosition();
                        //Log.i(TAG, visibleItemCount + " " + totalItemCount + " " + presenter_detail_ringtunes);
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            if (!isLoading) {
                                isLoading = true;
                                page++;
                                mList.add(null);
                                adapterService.notifyDataSetChanged();
                                //  key = ed_key_search_fragment.getText().toString();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        get_api();
                                    }
                                }, 1000);
                            }
                        }
                    }
                }
            }
        });
    }

    @Subscribe
    public void OnCustomEvent(CustomEvent event) {
        CustomEvent eventItem = event;
        if (eventItem.equals("0")) {
            get_api();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_LIST_CTV:
                if (resultCode == RESULT_OK) {
                    if (App.mObjCTV != null) {
                        mCTV = App.mObjCTV;
                        sUserCTV = mCTV.getUSERNAME();
                        txt_ctv.setText(mCTV.getFULL_NAME());
                    }
                } else {
                    sUserCTV = "";
                    txt_ctv.setText("Tất cả CTV");
                }
                break;
            case Constants.RequestCode.GET_ADD_ORDER:
                if (resultCode == RESULT_OK) {
                    showDialogLoading();
                    get_api();
                }
                break;
        }
    }


    String sUserCTV = "", sStatus = "";
    private ObjLogin objLogin;
    boolean isLogin;

    private void initData() {
        isLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_IS_LOGIN, Boolean.class);
        txt_total_order.setText("Tổng số đơn hàng: 0");
        objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        if (objLogin != null && objLogin.getGROUPS() != null) {
            if (objLogin.getGROUPS().equals("5") || objLogin.getGROUPS().equals("8")) {
                sStatus = "";
                txt_item_order_status.setText("Tất cả trạng thái");
                ll_filter_CTV.setVisibility(View.GONE);
            } else {
                txt_item_order_status.setText("Đã tiếp nhận");
                sStatus = "1";
                ll_filter_CTV.setVisibility(View.VISIBLE);
            }
        }
        sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mList.clear();
     /*   if (App.isLoadOrder) {
            sStatus = "1";
            txt_item_order_status.setText("Đang xử lý");
        }*/
        if (isLogin) {
            showDialogLoading();
            get_api();
        }else {
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

    private void get_api() {
        if (page == 1) {
            mList.clear();
            adapterService.notifyDataSetChanged();
            txt_total_order.setText("Tổng số đơn hàng: 0");
        }
        sFromDate = txt_date_start.getText().toString();
        sToDate = txt_date_end.getText().toString();
        if (TimeUtils.compare_two_date(sFromDate, sToDate,
                "dd/MM/yyyy", "dd/MM/yyyy")) {
            mPresenter.api_get_order_history(sUserName, sFromDate, sToDate, sUserCTV, sStatus,
                    "" + page, "" + index);
        } else {
            hideDialogLoading();
            showAlertDialog("Thông báo", "Thời gian nhập vào không hợp lệ");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_down_filter_CTV:
                Intent intent = new Intent(getContext(), FragmentListCTV.class);
                intent.putExtra(Constants.KEY_SEND_SELECTED_CTV, true);
                startActivityForResult(intent, Constants.RequestCode.GET_LIST_CTV);
                break;
            case R.id.img_down_filter_status:
                showDialogStatus();
                break;
            case R.id.img_date_end:
                new DatePickerDialog(getContext(), R.style.MyDatePickerStyle, to_date, myCalendar_to
                        .get(Calendar.YEAR), myCalendar_to.get(Calendar.MONTH),
                        myCalendar_to.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.img_date_start:
                new DatePickerDialog(getContext(), R.style.MyDatePickerStyle, from_date, myCalendar_from
                        .get(Calendar.YEAR), myCalendar_from.get(Calendar.MONTH),
                        myCalendar_from.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.txt_dangchoduyet:
                txt_item_order_status.setText("Đang xử lý");
                dialog_yes.dismiss();
                sStatus = "2";
                break;
            case R.id.txt_datiepnhan:
                txt_item_order_status.setText("Đã tiếp nhận");
                sStatus = "1";
                dialog_yes.dismiss();
                break;
            case R.id.txt_dangchuyen:
                txt_item_order_status.setText("Đang vận chuyển");
                sStatus = "3";
                dialog_yes.dismiss();
                break;
            case R.id.txt_hoanthanh:
                sStatus = "0";
                txt_item_order_status.setText("Đã hoàn thành");
                dialog_yes.dismiss();
                break;
            case R.id.txt_huy:
                sStatus = "4";
                txt_item_order_status.setText("Đã huỷ");
                dialog_yes.dismiss();
                break;
            case R.id.txt_back:
                sStatus = "";
                txt_item_order_status.setText("Tất cả trạng thái");
                dialog_yes.dismiss();
                break;

        }
    }

    @Override
    public void show_error_api() {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals("load_order")) {
            txt_item_order_status.setText("Đang xử lý");
            sStatus = "1";
            page = 1;
            mList.clear();
            get_api();
        }
        if (event.message.equals(Constants.EventBus.KEY_LOGIN_GUEST)) {
            txt_item_order_status.setText("Đang xử lý");
            sStatus = "1";
            page = 1;
            mList.clear();
            get_api();
        }
    }

    @Override
    public void show_order_history(ResponHistoryOrder obj) {
        hideDialogLoading_delay();
        if (obj.getsERROR().equals("0000")) {
            if (page == 1) {
                mList.clear();
            } else {
                if (page > 1) {
                    mList.remove(mList.size() - 1);
                }
            }
            App.isLoadOrder = false;
            isLoading = false;
            if (obj.getmList() != null && obj.getmList().size() > 0) {
                mList.addAll(obj.getmList());
                txt_total_order.setText("Tổng số đơn hàng: " + mList.size());
            }
            adapterService.notifyDataSetChanged();
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
        } else {
            if (page == 1) {
                String sVersionCode = "<font color='#c30a12'>" + obj.getsRESULT() + " </font>";
                txt_total_order.setText(Html.fromHtml(sVersionCode), TextView.BufferType.SPANNABLE);
            }
            //showAlertDialog("Thông báo", obj.getsRESULT());
            else if (page > 1) {
                mList.remove(mList.size() - 1);
                adapterService.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void show_order_history_detail(ObjOrder obj) {

    }

    @Override
    public void show_order_history_detail_pd(ResponGetProduct obj) {
        hideDialogLoading();
    }

    @Override
    public void show_edit_order_product(ErrorApi obj) {

    }

    @Override
    public void show_order_product(ErrorApi obj) {

    }

    @Override
    public void show_config_commission(ErrorApi obj) {

    }

    private void initPulltoRefesh() {
        pull_refresh_product.setOnRefreshListener(this);
    }

    boolean isRefresh = false;

    @Override
    public void onRefresh() {
        recycle_list_order.setNestedScrollingEnabled(false);
        isRefresh = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recycle_list_order.setNestedScrollingEnabled(true);
                isRefresh = false;
                mList.clear();
                page = 1;
                get_api();
                pull_refresh_product.setRefreshing(false);
            }
        }, 1000);
    }

    Dialog dialog_yes;

    public void showDialogStatus() {
        dialog_yes = new Dialog(getContext(), R.style.Theme_Dialog);
        dialog_yes.setCancelable(false);
        dialog_yes.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_yes.setContentView(R.layout.dialog_selected_status);
        dialog_yes.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView txt_title = (TextView) dialog_yes.findViewById(R.id.txt_warning_title);
        TextView txt_dangchoduyet = (TextView) dialog_yes.findViewById(R.id.txt_dangchoduyet);
        TextView txt_dangchuyen = (TextView) dialog_yes.findViewById(R.id.txt_dangchuyen);
        TextView txt_hoanthanh = (TextView) dialog_yes.findViewById(R.id.txt_hoanthanh);
        TextView txt_huy = (TextView) dialog_yes.findViewById(R.id.txt_huy);
        TextView txt_back = (TextView) dialog_yes.findViewById(R.id.txt_back);
        TextView txt_datiepnhan = (TextView) dialog_yes.findViewById(R.id.txt_datiepnhan);
        txt_huy.setText("Huỷ đơn");
        txt_back.setText("Tất cả các trạng thái");
        txt_back.setTextColor(getResources().getColor(R.color.black));
        txt_title.setOnClickListener(this);
        txt_dangchoduyet.setOnClickListener(this);
        txt_dangchuyen.setOnClickListener(this);
        txt_hoanthanh.setOnClickListener(this);
        txt_huy.setOnClickListener(this);
        txt_back.setOnClickListener(this);
        txt_datiepnhan.setOnClickListener(this);
        dialog_yes.show();

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
