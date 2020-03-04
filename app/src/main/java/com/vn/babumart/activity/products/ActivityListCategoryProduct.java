package com.vn.babumart.activity.products;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.vn.babumart.R;
import com.vn.babumart.adapter.AdapterCategoryProductHome;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.CategoryProductHome;
import com.vn.babumart.models.ObjCategoryProduct;
import com.vn.babumart.models.Products;
import com.vn.babumart.models.respon_api.ResponGetCat;
import com.vn.babumart.models.respon_api.ResponGetProduct;
import com.vn.babumart.models.respon_api.ResponSubProduct;
import com.vn.babumart.untils.KeyboardUtil;
import com.vn.babumart.untils.SharedPrefs;

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
public class ActivityListCategoryProduct extends BaseActivity implements InterfaceProduct.View,
        SwipeRefreshLayout.OnRefreshListener {
    AdapterCategoryProductHome adapterProduct;
    RecyclerView.LayoutManager mLayoutManagerProduct;
    List<CategoryProductHome> mLisCateProduct;
    @BindView(R.id.recycle_product)
    RecyclerView recycle_lis_product;
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

    @Override
    public int setContentViewId() {
        return R.layout.activity_list_product;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterProduct(this);
        KeyboardUtil.hideSoftKeyboard(this);
        hide_search();
        initAppbar();
        initData();
        initPulltoRefesh();
        initProduct();
        initEvent();
        //initDataProduct();
    }

    private void hide_search() {
        txt_title.setVisibility(View.VISIBLE);
        edt_search_service.setVisibility(View.GONE);
        img_search.setVisibility(View.INVISIBLE);
        ic_search_appbar.setVisibility(View.GONE);
    }

    private void initEvent() {


    }

    private void initAppbar() {
        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initPulltoRefesh() {
        pull_refresh_product.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pull_refresh_product.setRefreshing(false);
            }
        }, 500);
    }

    private void initData() {
        mCat = (ObjCategoryProduct) getIntent().getSerializableExtra(Constants.KEY_SEND_OBJ_CATEGORY);
        if (mCat != null) {
            if (mCat.getsName() != null)
                txt_title.setText(mCat.getsName());
            sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
            showDialogLoading();
            mPresenter.api_get_get_sub_product_child(sUser, mCat.getIDD());
        }
    }

    private void initProduct() {
        mLisCateProduct = new ArrayList<>();
        adapterProduct = new AdapterCategoryProductHome(this, mLisCateProduct, new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                Intent intent = new Intent(ActivityListCategoryProduct.this,
                        ActivityProductDetail.class);
                Products obj = (Products) item;
                intent.putExtra(Constants.KEY_SEND_OBJ_PRODUCTS, obj);
                startActivity(intent);
            }
        }, true);
        mLayoutManagerProduct = new GridLayoutManager(this, 1);
        recycle_lis_product.setHasFixedSize(true);
        recycle_lis_product.setLayoutManager(mLayoutManagerProduct);
        recycle_lis_product.setItemAnimator(new DefaultItemAnimator());
        recycle_lis_product.setAdapter(adapterProduct);
        adapterProduct.notifyDataSetChanged();
        adapterProduct.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                CategoryProductHome obj = (CategoryProductHome) item;
                Intent intent = new Intent(ActivityListCategoryProduct.this,
                       ActivityListProduct.class);
                String stitle = "";
                if (obj.getsName() != null)
                    stitle = obj.getsName();
                else if (obj.getSUB_NAME() != null)
                    stitle = obj.getSUB_NAME();
                intent.putExtra(Constants.KEY_SEND_ID_PRODUCT_TITLE, stitle);
                intent.putExtra(Constants.KEY_SEND_ID_PRODUCT_SUB, obj.getSUB_ID());
                intent.putExtra(Constants.KEY_SEND_ID_PRODUCT_PARENT, mCat.getIDD());
                startActivity(intent);
            }
        });
    }

    @Override
    public void show_error_api() {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_product_cat(ResponGetCat obj) {
        hideDialogLoading();
    }

    @Override
    public void show_product_sub_product(ResponSubProduct obj) {
        hideDialogLoading();
    }

    @Override
    public void show_product_sub_product_child(ResponSubProduct obj) {
        hideDialogLoading();
        if (obj.getsERROR().equals("0000")) {
            mLisCateProduct.clear();
            mLisCateProduct.addAll(obj.getmList());
            adapterProduct.notifyDataSetChanged();
        } else
            showAlertDialog("Thông báo", obj.getsRESULT());
    }

    @Override
    public void show_product_cat_detail(ResponGetProduct obj) {

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
}
