package com.vn.babumart.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vn.babumart.R;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.config.Config;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.models.ObjShopInfo;
import com.vn.babumart.untils.SharedPrefs;


import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 23-April-2019
 * Time: 10:03
 * Version: 1.0
 */
public class ActivityIntroduce extends BaseActivity implements InterfaceLogin.View {
    @BindView(R.id.btn_register)
    Button btn_register;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.img_logo)
    ImageView img_logo;
    @BindView(R.id.txt_shop_name)
    TextView txt_shop_name;
    @BindView(R.id.txt_shop_description)
    TextView txt_shop_description;
    @BindView(R.id.txt_showmore)
    TextView txt_showmore;
    @BindView(R.id.txt_des_full)
    TextView txt_des_full;
    @BindView(R.id.ll_introduce_full)
    LinearLayout ll_introduce_full;
    @BindView(R.id.ic_gone_full)
    ImageView ic_gone_full;
    @BindView(R.id.ll_introduce)
    LinearLayout ll_introduce;
    PresenterLogin mPresenter;

    @Override
    public int setContentViewId() {
        return R.layout.activity_introduce;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterLogin(this);
        initData();
        initEvent();
    }

    private void initData() {
        showDialogLoading();
        mPresenter.api_getshopinfo(Config.ID_SHOP);

    }

    private void initEvent() {
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityIntroduce.this,
                        ActivityConfirmOTP.class));
                finish();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityIntroduce.this, ActivityLogin.class));
                finish();
            }
        });
        txt_showmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_des_full();
            }
        });
        ic_gone_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gone_des_full();
            }
        });
    }

    private void show_des_full() {
        ll_introduce_full.setVisibility(View.VISIBLE);
        ic_gone_full.setVisibility(View.VISIBLE);
        ll_introduce.setVisibility(View.GONE);
        btn_login.setVisibility(View.GONE);
        btn_register.setVisibility(View.GONE);
        //img_logo.setVisibility(View.GONE);
    }

    private void gone_des_full() {
        ll_introduce_full.setVisibility(View.GONE);
        ic_gone_full.setVisibility(View.GONE);
        ll_introduce.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.VISIBLE);
        btn_register.setVisibility(View.VISIBLE);
        img_logo.setVisibility(View.VISIBLE);
    }

    @Override
    public void show_error_api() {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_login(ObjLogin obj) {

    }

    @Override
    public void show_register(ErrorApi obj) {

    }

    @Override
    public void show_update_device(ErrorApi obj) {

    }

    @Override
    public void show_get_shopinfo(ObjShopInfo objShop) {
        hideDialogLoading();
        if (objShop != null && objShop.getERROR().equals("0000")) {
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_OBJ_SHOP, objShop);
            if (objShop.getAVATAR() != null) {
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.img_defaul)
                        .error(R.drawable.ic_logo);
                Glide.with(this).load(objShop.getAVATAR()).apply(options).into(img_logo);
            } else {
                Glide.with(this).load(R.drawable.ic_logo).into(img_logo);
            }
            if (objShop.getSHOP_NAME() != null)
                txt_shop_name.setText(objShop.getSHOP_NAME());
            if (objShop.getSHOP_DES() != null) {
                txt_shop_description.setText(objShop.getSHOP_DES());
                txt_des_full.setText(Html.fromHtml(objShop.getSHOP_DES()));
            }
        } else
            showDialogNotify("Thông báo", objShop.getRESULT());

    }

    @Override
    public void show_check_device(ErrorApi obj) {

    }
}
