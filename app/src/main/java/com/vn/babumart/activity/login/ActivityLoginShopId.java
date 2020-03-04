package com.vn.babumart.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.vn.babumart.R;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.models.ObjShopInfo;
import com.vn.babumart.untils.SharedPrefs;
import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-August-2019
 * Time: 15:02
 * Version: 1.0
 */
public class ActivityLoginShopId extends BaseActivity implements InterfaceLogin.View {
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.btn_start)
    Button btn_start;
    @BindView(R.id.edt_shopid)
    EditText edt_shopid;
    PresenterLogin mPresenter;
    String sIdShop = "";

    @Override
    public int setContentViewId() {
        return R.layout.activity_login_shopid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterLogin(this);

        initEvent();
    }

    private void initEvent() {
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sIdShop = edt_shopid.getText().toString().trim();
                if (sIdShop.length() == 0) {
                    showAlertDialog("Thông báo", "Bạn chưa nhập vào shop id");
                    return;
                } else {
                    showDialogLoading();
                    mPresenter.api_getshopinfo(sIdShop);
                }
            }
        });

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
    public void show_get_shopinfo(ObjShopInfo obj) {
        hideDialogLoading();
        if (obj != null && obj.getERROR().equals("0000")) {
           // Config.ID_SHOP = sIdShop;
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_OBJ_SHOP, obj);
            startActivity(new Intent(ActivityLoginShopId.this,
                    ActivityIntroduce.class));
            finish();
        } else
            showAlertDialog("Thông báo", obj.getRESULT());

    }

    @Override
    public void show_check_device(ErrorApi obj) {

    }
}
