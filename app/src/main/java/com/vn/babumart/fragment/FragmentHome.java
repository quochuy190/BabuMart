package com.vn.babumart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vn.babumart.R;
import com.vn.babumart.activity.collaborators.ActivityCtvDetail;
import com.vn.babumart.activity.commission.InterfaceCommission;
import com.vn.babumart.activity.commission.PresenterCommission;
import com.vn.babumart.activity.home.InterfaceHome;
import com.vn.babumart.activity.home.PresenterHome;
import com.vn.babumart.activity.login.ActivityConfirmOTP;
import com.vn.babumart.activity.login.ActivityLogin;
import com.vn.babumart.activity.order.InterfaceOrder;
import com.vn.babumart.activity.order.PresenterOrder;
import com.vn.babumart.activity.products.ActivityProductDetail;
import com.vn.babumart.activity.products.InterfaceProduct;
import com.vn.babumart.activity.products.PresenterProduct;
import com.vn.babumart.activity.tintuc.ActivityDetailNews;
import com.vn.babumart.activity.tintuc.InterfaceTintuc;
import com.vn.babumart.activity.tintuc.PresenterTintuc;
import com.vn.babumart.adapter.AdapterCategoryProductHome;
import com.vn.babumart.adapter.AdapterListProductHome;
import com.vn.babumart.adapter.AdapterNewsHome;
import com.vn.babumart.base.BaseFragment;
import com.vn.babumart.callback.ClickDialog;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.CategoryProductHome;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.InfomationObj;
import com.vn.babumart.models.MessageEvent;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.models.ObjOrder;
import com.vn.babumart.models.Products;
import com.vn.babumart.models.respon_api.ResponGetCat;
import com.vn.babumart.models.respon_api.ResponGetCommission;
import com.vn.babumart.models.respon_api.ResponGetProduct;
import com.vn.babumart.models.respon_api.ResponHistoryOrder;
import com.vn.babumart.models.respon_api.ResponSubProduct;
import com.vn.babumart.models.respon_api.ResponseInfomation;
import com.vn.babumart.untils.KeyboardUtil;
import com.vn.babumart.untils.SharedPrefs;
import com.vn.babumart.untils.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentHome extends BaseFragment implements InterfaceProduct.View,
        InterfaceOrder.View, InterfaceCommission.View, InterfaceTintuc.View,
        SwipeRefreshLayout.OnRefreshListener, InterfaceHome.View {
    private static final String TAG = "FragmentHome";
    public static FragmentHome fragment;
    private List<Products> mList;
    private AdapterListProductHome adapterService;
    @BindView(R.id.recycle_product)
    RecyclerView recycle_product;
    @BindView(R.id.img_right_home)
    ImageView img_right_home;
    @BindView(R.id.ll_fragment_home)
    ConstraintLayout ll_fragment_home;
    RecyclerView.LayoutManager mLayoutManager;
    PresenterProduct mPresenter;
    private PresenterOrder mPresenterOrder;
    private PresenterCommission mPresenterCommission;
    private PresenterTintuc mPresenterTintuc;
    Calendar myCalendar_to = Calendar.getInstance();
    Calendar myCalendar_from = Calendar.getInstance();
    @BindView(R.id.txt_count_order_home)
    TextView txt_count_order_home;
    @BindView(R.id.txt_title)
    TextView txt_title;
    @BindView(R.id.txt_viewall_product)
    TextView txt_viewall_product;
    @BindView(R.id.img_right_next_home_one)
    ImageView img_right_next_home_one;
    @BindView(R.id.icon_order_home)
    ImageView icon_order_home;
    //Khai báo list sản phẩm theo trend
    List<CategoryProductHome> mLisProductTrend;
    RecyclerView.LayoutManager mLayoutManagerProTrend;
    AdapterCategoryProductHome adapterProductTrend;
    @BindView(R.id.pull_refresh_product)
    SwipeRefreshLayout pull_refresh_product;
    PresenterHome mPresenterHome;
    @BindView(R.id.ll_permission_ctv)
    ConstraintLayout ll_permission_ctv;
    @BindView(R.id.ll_permission_guest)
    ConstraintLayout ll_permission_guest;
    @BindView(R.id.ll_check_guest)
    ConstraintLayout ll_check_guest;
    @BindView(R.id.view_one)
    View view_one;
    @BindView(R.id.group_daotao)
    Group group_daotao;
    @BindView(R.id.btn_register_ctv)
    Button btn_register_ctv;

    public static FragmentHome getInstance() {
        if (fragment == null) {
            synchronized (FragmentHome.class) {
                if (fragment == null)
                    fragment = new FragmentHome();
            }
        }
        return fragment;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        KeyboardUtil.hideSoftKeyboard(getActivity());
        mPresenter = new PresenterProduct(this);
        mPresenterOrder = new PresenterOrder(this);
        mPresenterCommission = new PresenterCommission(this);
        mPresenterTintuc = new PresenterTintuc(this);
        mPresenterHome = new PresenterHome(this);
        initPulltoRefesh();
        get_all_history();
        init();
        initProductTrend();
        initEvent();
        initData();
        initNew();
        initNewTituc();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(Constants.EventBus.KEY_LOGIN_GUEST)) {
            initData();
        }
    }

    private void initEvent() {
        btn_register_ctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mObjLogin != null && mObjLogin.getGROUPS().equals("8")) {
                    Intent intent = new Intent(getContext(), ActivityCtvDetail.class);
                    intent.putExtra(Constants.KEY_SEND_UPDATE_CTV, true);
                    intent.putExtra(Constants.KEY_SEND_UPDATE_USER, true);
                    startActivityForResult(intent, Constants.RequestCode.GET_UPDATE_CTV);
                } else if (mObjLogin == null) {
                    Intent intent = new Intent(getContext(),
                            ActivityConfirmOTP.class);
                    intent.putExtra(Constants.KEY_SEND_LOGIN_GUEST, true);
                    startActivityForResult(intent, Constants.RequestCode.GET_LOGIN_BUY);
                }

            }
        });
        txt_viewall_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("product",
                        Float.parseFloat("1"), 0));
            }
        });
        img_right_next_home_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("product",
                        Float.parseFloat("1"), 0));
            }
        });
        ll_fragment_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        txt_count_order_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("home",
                        Float.parseFloat("1"), 0));
            }
        });
        img_right_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mObjLogin != null && mObjLogin.getGROUPS() != null) {
                    if (mObjLogin.getGROUPS().equals("3")) {
                        showDialogLoading();
                        mPresenterOrder.api_get_order_history(sUsername, sToDate,
                                sFromDate, "", "1", "1", "50");
                    }
                }
            }
        });
    }


    private void initProductTrend() {
        mLisProductTrend = new ArrayList<>();
        adapterProductTrend = new AdapterCategoryProductHome(getContext(), mLisProductTrend,
                new ItemClickListener() {
                    @Override
                    public void onClickItem(int position, Object item) {
                        if (!isClick) {
                            isClick = true;
                            Intent intent = new Intent(getContext(), ActivityProductDetail.class);
                            Products obj = (Products) item;
                            intent.putExtra(Constants.KEY_SEND_OBJ_PRODUCTS, obj);
                            startActivity(intent);
                        }
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isClick = false;
                            }
                        }, 1000);

                    }
                }, false);
        mLayoutManagerProTrend = new GridLayoutManager(getContext(), 1);
        recycle_product.setHasFixedSize(true);
        recycle_product.setLayoutManager(mLayoutManagerProTrend);
        recycle_product.setItemAnimator(new DefaultItemAnimator());
        recycle_product.setAdapter(adapterProductTrend);
        adapterProductTrend.notifyDataSetChanged();
        adapterProductTrend.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                EventBus.getDefault().postSticky(new MessageEvent(Constants.EventBus.KEY_SHOW_PRODUCT, 1, 0));

            }
        });
    }


    private String sFromDate = "", sToDate = "";
    String sUsername;
    ObjLogin mObjLogin;

    public void get_all_history() {
        int dayOfMonth = myCalendar_from.get(Calendar.DAY_OF_MONTH);
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        sFromDate = sdf.format(myCalendar_from.getTime());
        myCalendar_to.add(Calendar.DAY_OF_MONTH, -30);
        sToDate = sdf.format(myCalendar_to.getTime());
    }

    private void initData() {
        txt_count_order_home.setText("0 đơn");
        mObjLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        sUsername = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        showDialogLoading();
        mPresenter.api_get_get_product_trend(sUsername);
        mPresenterHome.api_get_config(sUsername, "");
        mPresenter.api_get_product_cat_detail(sUsername, "", "",
                "1", "150");
        mPresenterTintuc.api_get_infomation(sUsername, "4", "");
        mPresenterTintuc.api_get_infomation(sUsername, "3", "");
        mPresenterTintuc.api_get_infomation(sUsername, "5", "");
        if (mObjLogin != null && mObjLogin.getGROUPS() != null) {
            view_one.setVisibility(View.VISIBLE);
            ll_check_guest.setVisibility(View.VISIBLE);
            group_daotao.setVisibility(View.VISIBLE);
            if (mObjLogin.getGROUPS().equals("3")) {
                ll_permission_ctv.setVisibility(View.VISIBLE);
                ll_permission_guest.setVisibility(View.GONE);
                group_daotao.setVisibility(View.VISIBLE);
                icon_order_home.setImageDrawable(getResources().getDrawable(R.drawable.ic_order_home));
                txt_title.setText("Số đơn hàng đang chờ xử lý");
                mPresenterOrder.api_get_order_history(sUsername, sToDate,
                        sFromDate, "", "1", "1", "150");
            } else if (mObjLogin.getGROUPS().equals("5") || mObjLogin.getGROUPS().equals("8")) {
                if (mObjLogin.getGROUPS().equals("5")) {
                    group_daotao.setVisibility(View.VISIBLE);
                    ll_permission_ctv.setVisibility(View.VISIBLE);
                    ll_permission_guest.setVisibility(View.GONE);
                } else {
                    group_daotao.setVisibility(View.GONE);
                    ll_permission_guest.setVisibility(View.VISIBLE);
                    ll_permission_ctv.setVisibility(View.GONE);
                }

                icon_order_home.setImageDrawable(getResources().getDrawable(R.drawable.ic_bonus_ctvpng));
                txt_title.setText("Số dư hoa hồng hiện tại");
                mPresenterCommission.api_get_withdrawal_history(sUsername, sUsername, sToDate, sFromDate,
                        "1", "150");
            }
        } else {
            group_daotao.setVisibility(View.GONE);
            view_one.setVisibility(View.GONE);
            ll_check_guest.setVisibility(View.VISIBLE);
            ll_permission_guest.setVisibility(View.VISIBLE);
            ll_permission_ctv.setVisibility(View.GONE);
        }
        if (!StringUtil.check_login_customer()) {
            icon_order_home.setImageDrawable(getResources().getDrawable(R.drawable.ic_bonus_ctvpng));
            txt_title.setText("Số dư hoa hồng hiện tại");
            txt_count_order_home.setText("0đ");
        }

    }


    boolean isClick = false;

    private void init() {
        mList = new ArrayList<>();
        adapterService = new AdapterListProductHome(mList, getContext());
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycle_product.setNestedScrollingEnabled(false);
        recycle_product.setHasFixedSize(true);
        recycle_product.setLayoutManager(mLayoutManager);
        recycle_product.setItemAnimator(new DefaultItemAnimator());
        recycle_product.setAdapter(adapterService);

        adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                if (!isClick) {
                    Intent intent = new Intent(getContext(),ActivityProductDetail.class);
                    Products obj = (Products) item;
                    intent.putExtra(Constants.KEY_SEND_OBJ_PRODUCTS, obj);
                    startActivity(intent);
                    isClick = true;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isClick = false;
                    }
                }, 1000);
            }
        });
    }

    AdapterNewsHome adapterNew;
    RecyclerView.LayoutManager mLayoutManagerProduct;
    List<InfomationObj> mLisnew;
    @BindView(R.id.rcv_news_daotao)
    RecyclerView recycle_new_daotao;

    AdapterNewsHome adapterNew_tintuc;
    RecyclerView.LayoutManager mLayoutManagerProduct_tintuc;
    List<InfomationObj> mLisnew_tintuc;
    @BindView(R.id.rcv_news_tintuc)
    RecyclerView rcv_news_tintuc;

    private void initNew() {
        mLisnew = new ArrayList<>();
        adapterNew = new AdapterNewsHome(mLisnew, getContext());
        mLayoutManagerProduct = new GridLayoutManager(getContext(), 1);
        recycle_new_daotao.setHasFixedSize(true);
        recycle_new_daotao.setLayoutManager(mLayoutManagerProduct);
        recycle_new_daotao.setItemAnimator(new DefaultItemAnimator());
        recycle_new_daotao.setAdapter(adapterNew);
        adapterNew.notifyDataSetChanged();
        adapterNew.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                InfomationObj obj = (InfomationObj) item;
                Intent intent = new Intent(getContext(), ActivityDetailNews.class);
                intent.putExtra(Constants.KEY_SEND_NEWS_OBJ, obj);
                startActivity(intent);
            }
        });
    }

    private void initNewTituc() {
        mLisnew_tintuc = new ArrayList<>();
        adapterNew_tintuc = new AdapterNewsHome(mLisnew_tintuc, getContext());
        mLayoutManagerProduct_tintuc = new GridLayoutManager(getContext(), 1);
        rcv_news_tintuc.setHasFixedSize(true);
        rcv_news_tintuc.setLayoutManager(mLayoutManagerProduct_tintuc);
        rcv_news_tintuc.setItemAnimator(new DefaultItemAnimator());
        rcv_news_tintuc.setAdapter(adapterNew_tintuc);
        adapterNew_tintuc.notifyDataSetChanged();
        adapterNew_tintuc.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                InfomationObj obj = (InfomationObj) item;
                Intent intent = new Intent(getContext(), ActivityDetailNews.class);
                intent.putExtra(Constants.KEY_SEND_NEWS_OBJ, obj);
                startActivity(intent);
            }
        });
    }

    @Override
    public void show_error_api() {

    }

    @Override
    public void show_api_infomation(ResponseInfomation objRes) {
        hideDialogLoading();
        if (objRes != null && objRes.getERROR().equals("0000")) {
            mLisnew_tintuc.clear();
            mLisnew_tintuc.addAll(objRes.getmList());
            adapterNew_tintuc.notifyDataSetChanged();
        }
    }

    @Override
    public void show_api_infomation_daotao(ResponseInfomation objRes) {
        hideDialogLoading();
        if (objRes != null && objRes.getERROR().equals("0000")) {
            mLisnew.clear();
            mLisnew.addAll(objRes.getmList());
            adapterNew.notifyDataSetChanged();
        }
    }

    @Override
    public void show_get_commission(ResponGetCommission obj) {

    }

    @Override
    public void show_get_withdrawal(ResponGetCommission obj) {

    }


    @Override
    public void show_get_withdrawal_history(ResponGetCommission obj) {
        if (obj.getsERROR().equals("0000")) {
            if (obj.getmList() != null) {
                if (obj.getmList().get(0) != null) {
                    txt_count_order_home.setText(StringUtil.conventMonney(obj.getmList().get(0).getTOTAL_HH()));
                } else {
                    txt_count_order_home.setText("0 đ");
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

    @Override
    public void show_order_history(ResponHistoryOrder obj) {
        if (obj != null && obj.getsERROR().equals("0000")) {
            if (obj.getTOTAL_ORDER() != null) {
                txt_count_order_home.setText(obj.getTOTAL_ORDER() + " đơn");
            }
        }
    }

    @Override
    public void show_order_history_detail(ObjOrder obj) {

    }

    @Override
    public void show_order_history_detail_pd(ResponGetProduct obj) {

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

    @Override
    public void show_product_cat(ResponGetCat obj) {

    }

    @Override
    public void show_product_sub_product(ResponSubProduct obj) {

    }

    @Override
    public void show_product_sub_product_child(ResponSubProduct obj) {

    }

    @Override
    public void show_product_cat_detail(ResponGetProduct obj) {

    }

    private List<Products> mListProductNoibat = new ArrayList<>();
    private List<Products> mListProductBanchay = new ArrayList<>();
    private List<Products> mListProductMoi = new ArrayList<>();
    private List<Products> mListProductKhuyenmai = new ArrayList<>();
    private List<Products> mListProductBinhthuong = new ArrayList<>();

    @Override
    public void show_product_trend(CategoryProductHome obj) {
        hideDialogLoading();
        mLisProductTrend.clear();
        mListProductNoibat.clear();
        mListProductBanchay.clear();
        mListProductMoi.clear();
        mListProductKhuyenmai.clear();
        mListProductBinhthuong.clear();
        if (obj != null && obj.getsERROR().equals("0000")) {
            if (obj.getmList() != null && obj.getmList().size() > 0) {
                for (Products objPro : obj.getmList()) {
                    if (objPro.getSTATUS_TREND() != null) {
                        switch (objPro.getSTATUS_TREND()) {
                            case "1":
                                mListProductNoibat.add(objPro);
                                break;
                            case "2":
                                mListProductBanchay.add(objPro);
                                break;
                            case "3":
                                mListProductMoi.add(objPro);
                                break;
                            case "4":
                                mListProductKhuyenmai.add(objPro);
                                break;
                            case "5":
                                mListProductBinhthuong.add(objPro);
                                break;
                        }

                    }
                }
                if (mListProductNoibat.size() > 0) {
                    mLisProductTrend.add(new CategoryProductHome("SẢN PHẨM NỔI BẬT", mListProductNoibat));
                }
                if (mListProductBanchay.size() > 0) {
                    mLisProductTrend.add(new CategoryProductHome("SẢN PHẨM BÁN CHẠY", mListProductBanchay));
                }
                if (mListProductMoi.size() > 0) {
                    mLisProductTrend.add(new CategoryProductHome("SẢN PHẨM MỚI", mListProductMoi));
                }
                if (mListProductKhuyenmai.size() > 0) {
                    mLisProductTrend.add(new CategoryProductHome("SẢN PHẨM KHUYẾN MẠI", mListProductKhuyenmai));
                }
                if (mListProductBinhthuong.size() > 0) {
                    mLisProductTrend.add(new CategoryProductHome("SẢN PHẨM CHUNG", mListProductBinhthuong));
                }
            }
        }
        adapterProductTrend.notifyDataSetChanged();
    }

    @Override
    public void show_product_by_listid(ResponGetProduct obj) {

    }

    @Override
    public void show_product_detail(Products objProduct) {

    }

    private void initPulltoRefesh() {
        pull_refresh_product.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                pull_refresh_product.setRefreshing(false);
            }
        }, 500);
    }

    @Override
    public void show_get_config() {

    }


}
