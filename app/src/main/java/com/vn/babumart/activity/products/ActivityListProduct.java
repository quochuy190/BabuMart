package com.vn.babumart.activity.products;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.vn.babumart.R;
import com.vn.babumart.adapter.AdapterProducts;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.callback.ClickDialog;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.config.Config;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.CategoryProductHome;
import com.vn.babumart.models.ObjCategoryProduct;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.models.Products;
import com.vn.babumart.models.respon_api.ResponGetCat;
import com.vn.babumart.models.respon_api.ResponGetProduct;
import com.vn.babumart.models.respon_api.ResponSubProduct;
import com.vn.babumart.untils.KeyboardUtil;
import com.vn.babumart.untils.SharedPrefs;
import com.vn.babumart.untils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 10-May-2019
 * Time: 16:20
 * Version: 1.0
 */
public class ActivityListProduct extends BaseActivity implements InterfaceProduct.View,
        SwipeRefreshLayout.OnRefreshListener {
    AdapterProducts adapterProduct;
    RecyclerView.LayoutManager mLayoutManagerProduct;
    List<Products> mLisCateProduct;
    List<Products> mLisSearch;
    @BindView(R.id.recycle_product)
    RecyclerView recycle_lis_product;
    @BindView(R.id.img_back)
    ImageView img_back;
    private ObjCategoryProduct mCat;
    private PresenterProduct mPresenter;
    private String sUser;
    @BindView(R.id.pull_refresh_product)
    SwipeRefreshLayout pull_refresh_product;
    @BindView(R.id.edt_search_appbar)
    EditText edt_search_service;
    @BindView(R.id.img_search)
    ImageView img_search;
    @BindView(R.id.img_edt_search)
    ImageView ic_search_appbar;
    @BindView(R.id.txt_title)
    TextView txt_title;
    @BindView(R.id.txt_copy_link)
    TextView txt_copy_link;
    boolean isSearch = false;

    @Override
    public int setContentViewId() {
        return R.layout.activity_list_product;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterProduct(this);
        KeyboardUtil.hideSoftKeyboard(this);
        img_search.setVisibility(View.VISIBLE);
        txt_copy_link.setVisibility(View.VISIBLE);
        hide_search();
        showDialogLoading();
        initData();
        initAppbar();
        initPulltoRefesh();
        initProduct();
        initDataProduct();
        initEvent();
    }

    private void hide_title() {
        txt_title.setVisibility(View.GONE);
        edt_search_service.setVisibility(View.VISIBLE);
        img_search.setVisibility(View.VISIBLE);
        ic_search_appbar.setVisibility(View.VISIBLE);
    }

    private void hide_search() {
        txt_title.setVisibility(View.VISIBLE);
        edt_search_service.setVisibility(View.GONE);
        img_search.setVisibility(View.VISIBLE);
        ic_search_appbar.setVisibility(View.GONE);
    }

    private void initEvent() {
        txt_copy_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogComfirm_Two_Button("Copy link catalog",
                        "Bạn muốn link catalog sản phẩm hiện thị có giá hay không có giá?",
                        "Hiển thị giá",
                        "Không có giá",
                        true,
                        new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
                                String sLink = "https://f5sell.com/catalog?s=";
                                sLink = sLink + Config.ID_SHOP + "&c=" + sSubid + "&ctvid=" + objLogin.getUSER_CODE();
                                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText(getString(R.string.app_name), sLink);
                                clipboard.setPrimaryClip(clip);
                                Toast.makeText(ActivityListProduct.this, "Link đã được copy vào Clipboard, " +
                                        "bạn có thể gửi cho khách hàng qua Zalo, Messenger, ...", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onClickNoDialog() {
                                ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
                                String sLink = "https://f5sell.com/catalog?v=568&s=";
                                sLink = sLink + Config.ID_SHOP + "&c=" + sSubid + "&ctvid=" + objLogin.getUSER_CODE();
                                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText(getString(R.string.app_name), sLink);
                                clipboard.setPrimaryClip(clip);
                                Toast.makeText(ActivityListProduct.this, "Link đã được copy vào Clipboard, " +
                                        "bạn có thể gửi cho khách hàng qua Zalo, Messenger, ...", Toast.LENGTH_LONG).show();

                            }
                        }
                );


            }
        });
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSearch) {
                    hide_title();
                    isSearch = !isSearch;
                } else {
                    hide_search();
                    isSearch = !isSearch;
                }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edt_search_service.addTextChangedListener(new TextWatcher() {
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

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });


    }

    void filter(String text) {
        mLisSearch.clear();
        for (Products d : mLisCateProduct) {
            if (d.getsName() != null) {
                String sName = StringUtil.removeAccent(d.getsName().toLowerCase());
                String sInput = StringUtil.removeAccent(text.toLowerCase());
                if (sName.contains(sInput)) {
                    //adding the element to filtered list
                    mLisSearch.add(d);
                }
            }
        }
        adapterProduct.updateList(mLisSearch);

    }

    String sIdParent = null, sSubid = "", sTitle = "";

    private void initData() {
        sTitle = getIntent().getStringExtra(Constants.KEY_SEND_ID_PRODUCT_TITLE);
        if (sTitle != null) {
            txt_title.setText(sTitle);
        } else
            txt_title.setText("Danh sách sản phẩm");
        sIdParent = getIntent().getStringExtra(Constants.KEY_SEND_ID_PRODUCT_PARENT);
        sSubid = getIntent().getStringExtra(Constants.KEY_SEND_ID_PRODUCT_SUB);
        sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        if (sIdParent != null) {
            if (sSubid != null) {
                mPresenter.api_get_product_cat_detail(sUser, sIdParent, sSubid,
                        "" + page, "" + index);
            } else {
                sSubid = "";
                mPresenter.api_get_product_cat_detail(sUser, sIdParent, sSubid,
                        "" + page, "" + index);
            }

        } else {
            mCat = (ObjCategoryProduct) getIntent().getSerializableExtra(Constants.KEY_SEND_OBJ_CATEGORY_SUB);
            if (mCat != null) {
                if (mCat.getsName() != null)
                    txt_title.setText(mCat.getsName());
                else if (mCat.getSUB_NAME() != null)
                    txt_title.setText(mCat.getSUB_NAME());
                else txt_title.setText("Danh sách đơn hàng");
                sIdParent = mCat.getSUB_ID_PARENT();
                sSubid = mCat.getSUB_ID();
                mPresenter.api_get_product_cat_detail(sUser, sIdParent, sSubid,
                        "" + page, "" + index);
            }
        }

    }

    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean isLoading = true;
    int page = 1;
    int index = 300;


    private void initAppbar() {
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initProduct() {
        mLisCateProduct = new ArrayList<>();
        mLisSearch = new ArrayList<>();
        adapterProduct = new AdapterProducts(mLisCateProduct, this);
        mLayoutManagerProduct = new GridLayoutManager(this, 2);
        recycle_lis_product.setHasFixedSize(true);
        recycle_lis_product.setLayoutManager(mLayoutManagerProduct);
        recycle_lis_product.setItemAnimator(new DefaultItemAnimator());
        recycle_lis_product.setAdapter(adapterProduct);
        adapterProduct.notifyDataSetChanged();
        adapterProduct.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
              /*  mLisShop.get(position).setHideSub(!mLisShop.get(position).isHideSub());
                adapter.notifyDataSetChanged();*/
                Intent intent = new Intent(ActivityListProduct.this,
                        ActivityProductDetail.class);
                Products obj = (Products) item;
                intent.putExtra(Constants.KEY_SEND_OBJ_PRODUCTS, obj);
                startActivity(intent);
            }
        });

       /* // loadmore
        recycle_lis_product.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
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
                            showDialogLoading();
                            //  key = ed_key_search_fragment.getText().toString();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mPresenter.api_get_product_cat_detail(sUser, sIdParent,
                                            sSubid, "" + page, "" + index);
                                }
                            }, 1000);
                        }
                    }
                }
            }
        });*/
    }

    private void initDataProduct() {
       /* for (int i = 0; i < 10; i++) {
            mLisCateProduct.add(new Products("Tẩy tế bào chết Arrahan Lemon Peeling Gel hương chanh 180ml",
                    "3500 đ", "https://naototnhat.com/wp-content/uploads/2018/08/my-pham-duong-trang-da.jpg"));

        }
        adapterProduct.notifyDataSetChanged();*/
    }

    @Override
    public void show_error_api() {
        hideDialogLoading();
        showAlertErrorNetwork();
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
        hideDialogLoading();
        if (obj != null) {
            if (obj.getsERROR() != null && obj.getsERROR().equals("0000")) {
                if (obj.getmList() != null && obj.getmList().size() > 0) {
                    isLoading = false;
                    mLisCateProduct.addAll(obj.getmList());
                    adapterProduct.notifyDataSetChanged();
                }
            } else {
                if (page == 1)
                    showAlertDialog("Thông báo", obj.getsRESULT());
            }
        }
    }

    @Override
    public void show_product_trend(CategoryProductHome obj) {

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
                page = 1;
                showDialogLoading();
                mLisCateProduct.clear();
                initData();
                pull_refresh_product.setRefreshing(false);
            }
        }, 500);
    }
}
