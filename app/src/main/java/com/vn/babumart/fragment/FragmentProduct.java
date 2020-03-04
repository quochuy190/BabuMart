package com.vn.babumart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vn.babumart.R;
import com.vn.babumart.activity.products.ActivityListCategoryProduct;
import com.vn.babumart.activity.products.ActivityListProduct;
import com.vn.babumart.activity.products.ActivityProductDetail;
import com.vn.babumart.activity.products.InterfaceProduct;
import com.vn.babumart.activity.products.PresenterProduct;
import com.vn.babumart.adapter.AdapterCategoryProduct;
import com.vn.babumart.adapter.AdapterCategoryProductHome;
import com.vn.babumart.base.BaseFragment;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.callback.OnListenerItemClickObjService;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.CategoryProductHome;
import com.vn.babumart.models.MessageEvent;
import com.vn.babumart.models.ObjCategoryProduct;
import com.vn.babumart.models.ObjSucCategory;
import com.vn.babumart.models.Products;
import com.vn.babumart.models.respon_api.ResponGetCat;
import com.vn.babumart.models.respon_api.ResponGetProduct;
import com.vn.babumart.models.respon_api.ResponSubProduct;
import com.vn.babumart.untils.SharedPrefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Quốc Huy
 * @version 1.0.0
 * @description
 * @desc Developer NEO Company.
 * @created 22/04/2019
 * @updated 22/04/2019
 * @modified by
 * @updated on 22/04/2019
 * @since 1.0
 */
public class FragmentProduct extends BaseFragment implements InterfaceProduct.View,
        SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "FragmentProduct";
    public static FragmentProduct fragment;
    @BindView(R.id.txt_title)
    TextView txt_title;
    @BindView(R.id.ll_fragment_product)
    ConstraintLayout ll_fragment_product;
    private PresenterProduct mPresenter;
    private String mUser;
    @BindView(R.id.refresh_product)
    SwipeRefreshLayout refresh_product;
    @BindView(R.id.edt_search_appbar)
    EditText edt_search_appbar;

    public static FragmentProduct getInstance() {
        if (fragment == null) {
            synchronized (FragmentProduct.class) {
                if (fragment == null)
                    fragment = new FragmentProduct();
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
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new PresenterProduct(this);
        initPulltoRefesh();
        Log.e(TAG, "onCreateView: Product");
        init();
        initProduct();
        initDataCat();
        initDataProduct();
        initEvent();
        return view;
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
            initDataCat();
        }
    }

    private void initDataProduct() {
     /*   for (int i = 0; i < 5; i++) {
            List<Products> mLis = new ArrayList<>();
            mLis.add(new Products("Tẩy da chết", "3500 đ", "https://naototnhat.com/wp-content/uploads/2018/08/my-pham-duong-trang-da.jpg"));
            mLis.add(new Products("Tẩy da chết", "3500", "https://naototnhat.com/wp-content/uploads/2018/08/my-pham-duong-trang-da.jpg"));
            mLis.add(new Products("Tẩy da chết", "3500", "https://naototnhat.com/wp-content/uploads/2018/08/my-pham-duong-trang-da.jpg"));
            mLisCateProduct.add(new CategoryProductHome("Mỹ phẩm làm sạch", mLis));
        }
        adapter.notifyDataSetChanged();*/

    }

    boolean isHideCategory = true;

    private void initEvent() {
        edt_search_appbar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mLisProductSub.clear();
                    adapterProductSub.notifyDataSetChanged();
                    initDataCat();
                    return true;
                }
                return false;
            }
        });
        ll_fragment_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        txt_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isHideCategory) {
                    show_category();
                } else {
                    hide_category();
                /*    list_menu_untility.setVisibility(View.VISIBLE);
                    recycle_lis_product.setVisibility(View.GONE);
                    isHideCategory = !isHideCategory;*/
                }

            }
        });
    }

    private void hide_category() {
        list_menu_untility.setVisibility(View.VISIBLE);
        recycle_lis_product_sub.setVisibility(View.GONE);
        isHideCategory = !isHideCategory;
    }

    private void show_category() {
        list_menu_untility.setVisibility(View.GONE);
        recycle_lis_product_sub.setVisibility(View.VISIBLE);
        isHideCategory = !isHideCategory;
    }

    private void initDataCat() {
        showDialogLoading();
        mUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenter.api_get_product_cat(mUser, "0");
        if (edt_search_appbar.getText().toString().length() > 0) {
            mPresenter.api_get_get_sub_product(mUser, "", edt_search_appbar.getText().toString());
        } else {
            mPresenter.api_get_get_sub_product(mUser, "", "");
        }


    }

    AdapterCategoryProduct adapterCategory;
    AdapterCategoryProductHome adapterProductSub;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.LayoutManager mLayoutManagerProductSub;
    List<ObjCategoryProduct> mLisShop;
    List<CategoryProductHome> mLisProductSub;
    ObjSucCategory mObj;
    @BindView(R.id.recycle_lis_category_product)
    RecyclerView list_menu_untility;
    @BindView(R.id.recycle_lis_product_sub)
    RecyclerView recycle_lis_product_sub;

    private void init() {
        mLisShop = new ArrayList<>();
        adapterCategory = new AdapterCategoryProduct(getContext(),
                mLisShop, new OnListenerItemClickObjService() {
            @Override
            public void onClickListener(ObjCategoryProduct item) {
                Intent intent = new Intent(getContext(), ActivityListProduct.class);
                ObjCategoryProduct obj = (ObjCategoryProduct) item;
                String stitle = "";
                if (obj.getsName() != null)
                    stitle = obj.getsName();
                else if (obj.getSUB_NAME() != null)
                    stitle = obj.getSUB_NAME();
                intent.putExtra(Constants.KEY_SEND_ID_PRODUCT_TITLE, stitle);
                intent.putExtra(Constants.KEY_SEND_OBJ_CATEGORY_SUB, obj);
                startActivity(intent);
                show_category();
            }

            @Override
            public void onItemXemthemClick(ObjCategoryProduct objService) {

            }
        });
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        list_menu_untility.setHasFixedSize(true);
        list_menu_untility.setLayoutManager(mLayoutManager);
        list_menu_untility.setItemAnimator(new DefaultItemAnimator());
        list_menu_untility.setAdapter(adapterCategory);
        adapterCategory.notifyDataSetChanged();
        adapterCategory.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                mLisShop.get(position).setHideSub(!mLisShop.get(position).isHideSub());
                adapterCategory.notifyDataSetChanged();
            }
        });
        adapterCategory.setOnIListener_Title(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {

                Intent intent = new Intent(getContext(), ActivityListCategoryProduct.class);
                ObjCategoryProduct obj = (ObjCategoryProduct) item;
                intent.putExtra(Constants.KEY_SEND_OBJ_CATEGORY, obj);
                startActivity(intent);
                show_category();
            }
        });
    }

    private void initProduct() {
        mLisProductSub = new ArrayList<>();
        adapterProductSub = new AdapterCategoryProductHome(getContext(), mLisProductSub,
                new ItemClickListener() {
                    @Override
                    public void onClickItem(int position, Object item) {
                        Intent intent = new Intent(getContext(), ActivityProductDetail.class);
                        Products obj = (Products) item;
                        intent.putExtra(Constants.KEY_SEND_OBJ_PRODUCTS, obj);
                        startActivity(intent);
                    }
                }, true);
        mLayoutManagerProductSub = new GridLayoutManager(getContext(), 1);
        recycle_lis_product_sub.setHasFixedSize(true);
        recycle_lis_product_sub.setLayoutManager(mLayoutManagerProductSub);
        recycle_lis_product_sub.setItemAnimator(new DefaultItemAnimator());
        recycle_lis_product_sub.setAdapter(adapterProductSub);
        adapterProductSub.notifyDataSetChanged();
        adapterProductSub.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                CategoryProductHome obj = (CategoryProductHome) item;
                Intent intent = new Intent(getContext(),ActivityListCategoryProduct.class);
                ObjCategoryProduct objCat = new ObjCategoryProduct(obj.getsName(), obj.getID());
                intent.putExtra(Constants.KEY_SEND_OBJ_CATEGORY, objCat);
                startActivity(intent);
                show_category();
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
        //   hideDialogLoading();
        if (obj != null) {
            if (obj.getsERROR() != null && obj.getsERROR().equals("0000")) {
                mLisShop.clear();
                mLisShop.addAll(obj.getmList());
                adapterCategory.notifyDataSetChanged();
            } else showAlertDialog("Thông báo", obj.getsRESULT());
        }
    }

    @Override
    public void show_product_sub_product(ResponSubProduct obj) {
        hideDialogLoading();
        if (obj.getsERROR().equals("0000")) {
            mLisProductSub.clear();
            mLisProductSub.addAll(obj.getmList());
            adapterProductSub.notifyDataSetChanged();
        } else
            showAlertDialog("Thông báo", obj.getsRESULT());
    }

    @Override
    public void show_product_sub_product_child(ResponSubProduct obj) {

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

    private void initPulltoRefesh() {
        refresh_product.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initDataCat();
                refresh_product.setRefreshing(false);
            }
        }, 500);
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
