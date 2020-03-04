package com.vn.babumart.activity.collaborators;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.vn.babumart.App;
import com.vn.babumart.R;
import com.vn.babumart.adapter.AdapterListCTV;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.ObjCTV;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.models.respon_api.ResponGetLisCTV;
import com.vn.babumart.untils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 22-April-2019
 * Time: 10:30
 * Version: 1.0
 */
public class FragmentListCTV extends BaseActivity implements InterfaceCollaborators.View {
    private static final String TAG = "FragmentListCTV";
    public static FragmentListCTV fragment;
    private List<ObjCTV> mList;
    private AdapterListCTV adapterService;
    @BindView(R.id.recycle_list_CTV)
    RecyclerView recycle_product;
    @BindView(R.id.txt_title_hh)
    TextView txt_title_hh;
    RecyclerView.LayoutManager mLayoutManager;
    PresenterCTV mPresenter;
    private boolean isSelectMenu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterCTV(this);
        initAppbar();
        init();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        txt_title.setText("Danh sách CTV");
    }

    private void initData() {
        showDialogLoading();
        //isSelectMenu = SharedPrefs.getInstance().get(Constants.KEY_SEND_SELECTED_CTV, Boolean.class);
        isSelectMenu = getIntent().getBooleanExtra(Constants.KEY_SEND_SELECTED_CTV, false);
        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        if (objLogin != null && objLogin.getGROUPS() != null
                && objLogin.getGROUPS().equals(Constants.User_Group_Type.CTV)) {
            txt_title_hh.setText("HH");
            mPresenter.api_get_list_ctv_child(sUserName, "", "", "1", "200");
        } else {
            txt_title_hh.setText("TK");
            mPresenter.api_get_lis_ctv(sUserName, "", "", "1", "200");
        }


    }

    private void init() {
        mList = new ArrayList<>();
        adapterService = new AdapterListCTV(mList, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        recycle_product.setNestedScrollingEnabled(false);
        recycle_product.setHasFixedSize(true);
        recycle_product.setLayoutManager(mLayoutManager);
        recycle_product.setItemAnimator(new DefaultItemAnimator());
        recycle_product.setAdapter(adapterService);
      /*  adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                setResult(RESULT_OK, new Intent());
                App.mCity = (City) item;
            }
        });*/
        adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                ObjCTV obj = (ObjCTV) item;
                if (obj != null) {
                    if (isSelectMenu) {
                        setResult(RESULT_OK, new Intent());
                        App.mObjCTV = obj;
                        finish();
                    } else {
                        Intent intent = new Intent(FragmentListCTV.this,
                                ActivityCtvDetail.class);
                        intent.putExtra(Constants.KEY_SEND_OBJ_CTV, obj);
                        startActivity(intent);
                    }

                }
            }
        });

    }

    @Override
    public void show_error_api() {

    }

    @Override
    public void show_get_list_ctv(ResponGetLisCTV obj) {
        hideDialogLoading();
        if (obj.getsERROR().equals("0000")) {
            if (obj.getLisDistrict() != null) {
                mList.addAll(obj.getLisDistrict());
                adapterService.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void show_update_ctv(ErrorApi obj) {

    }

    @Override
    public void show_reset_pass_ctv(ErrorApi obj) {

    }

    @Override
    public void show_ctv_detail(ObjCTV objLogin) {

    }

    @Override
    public void show_list_ctv_child(ResponGetLisCTV objLogin) {

    }

    @Override
    public int setContentViewId() {
        return R.layout.fragment_list_ctv;
    }
}
