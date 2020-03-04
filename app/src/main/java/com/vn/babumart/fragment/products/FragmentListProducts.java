package com.vn.babumart.fragment.products;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vn.babumart.R;
import com.vn.babumart.activity.products.ActivityProductDetail;
import com.vn.babumart.activity.products.InterfaceProduct;
import com.vn.babumart.activity.products.PresenterProduct;
import com.vn.babumart.adapter.AdapterProducts;
import com.vn.babumart.base.BaseFragment;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.CategoryProductHome;
import com.vn.babumart.models.ObjCategoryProduct;
import com.vn.babumart.models.Products;
import com.vn.babumart.models.respon_api.ResponGetCat;
import com.vn.babumart.models.respon_api.ResponGetProduct;
import com.vn.babumart.models.respon_api.ResponSubProduct;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 10-May-2019
 * Time: 16:20
 * Version: 1.0
 */
public class FragmentListProducts extends BaseFragment implements InterfaceProduct.View,
        SwipeRefreshLayout.OnRefreshListener {
    AdapterProducts adapterProduct;
    RecyclerView.LayoutManager mLayoutManagerProduct;
    List<Products> mLisCateProduct;
    @BindView(R.id.recycle_product)
    RecyclerView recycle_lis_product;
    @BindView(R.id.img_back)
    ImageView img_back;
    private ObjCategoryProduct mCat;
    private PresenterProduct mPresenter;
    private String sUser;
    @BindView(R.id.pull_refresh_product)
    SwipeRefreshLayout pull_refresh_product;
    public static FragmentListProducts fragment;

    public static FragmentListProducts getInstance() {
        if (fragment == null) {
            synchronized (FragmentListProducts.class) {
                if (fragment == null)
                    fragment = new FragmentListProducts();
            }
        }
        return fragment;
    }
   /* @Override
    public int setContentViewId() {
        return R.layout.activity_list_product;
    }*/

  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterProduct(this);
        initData();
        initAppbar();
        initPulltoRefesh();
        initProduct();
        initDataProduct();
        initEvent();
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_product, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initEvent() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initData() {
        /*mCat = (ObjCategoryProduct) getIntent().getSerializableExtra(Constants.KEY_SEND_OBJ_CATEGORY_SUB);
        if (mCat != null) {
            sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
            showDialogLoading();
            mPresenter.api_get_product_cat_detail(sUser, mCat.getIDD(), mCat.getSUB_ID(),
                    "" + page, "" + index);
        }*/
    }

    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean isLoading = true;
    int page = 1;
    int index = 30;

    private void initProduct() {
        mLisCateProduct = new ArrayList<>();
        adapterProduct = new AdapterProducts(mLisCateProduct, getContext());
        mLayoutManagerProduct = new GridLayoutManager(getContext(), 2);
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
                Intent intent = new Intent(getContext(), ActivityProductDetail.class);
                Products obj = (Products) item;
                intent.putExtra(Constants.KEY_SEND_OBJ_PRODUCTS, obj);
                startActivity(intent);
            }
        });

        // loadmore
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
                                    mPresenter.api_get_product_cat_detail(sUser, mCat.getIDD(),
                                            mCat.getSUB_ID(), "" + page, "" + index);
                                }
                            }, 1000);
                        }
                    }
                }
            }
        });
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
                mPresenter.api_get_product_cat_detail(sUser, mCat.getIDD(), mCat.getSUB_ID(),
                        "" + page, "" + index);
                pull_refresh_product.setRefreshing(false);
            }
        }, 500);
    }
}
