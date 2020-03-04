package com.vn.babumart.activity.collaborators;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.vn.babumart.App;
import com.vn.babumart.R;
import com.vn.babumart.activity.login.ActivityLogin;
import com.vn.babumart.activity.login.InterfaceLogin;
import com.vn.babumart.activity.login.Menu_Search.ActivityDistrict;
import com.vn.babumart.activity.login.Menu_Search.ActivityListCity;
import com.vn.babumart.activity.login.PresenterLogin;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.City;
import com.vn.babumart.models.Districts;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.ObjCTV;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.models.ObjShopInfo;
import com.vn.babumart.models.respon_api.ResponGetLisCTV;
import com.vn.babumart.untils.KeyboardUtil;
import com.vn.babumart.untils.SharedPrefs;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 23-April-2019
 * Time: 13:33
 * Version: 1.0
 */
public class ActivityUpdateInfoCTV extends BaseActivity
        implements View.OnClickListener, InterfaceLogin.View, InterfaceCollaborators.View {
    @BindView(R.id.img_done)
    ImageView img_done;
    @BindView(R.id.edt_fullname_register)
    EditText edt_fullname;
    @BindView(R.id.edt_email_register)
    EditText edt_email;
    @BindView(R.id.edt_phone_register)
    EditText edt_phone;
    @BindView(R.id.edt_address_register)
    EditText edt_address;
    @BindView(R.id.edt_city_register)
    EditText edt_city;
    @BindView(R.id.edt_district_register)
    EditText edt_district;
    @BindView(R.id.edt_pass_register)
    EditText edt_pass;
    @BindView(R.id.edt_pass_confirm_register)
    EditText edt_pass_confirm;
    @BindView(R.id.txt_change_login)
    TextView txt_change_login;
    City objCity;
    Districts objDistrict;
    PresenterLogin mPresenter;
    PresenterCTV mPresenterCTV;
    @BindView(R.id.btn_update)
    Button btn_update;

    @Override
    public int setContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterLogin(this);
        mPresenterCTV = new PresenterCTV(this);
        txt_change_login.setVisibility(View.INVISIBLE);
        initData();
        initEvent();
    }

    ObjLogin objLogin;

    private void initData() {
        boolean isUpdate = true;
        if (isUpdate) {
            objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);

            if (objLogin != null) {
                if (objLogin.getFULL_NAME() != null)
                    edt_fullname.setText(objLogin.getFULL_NAME());
                if (objLogin.getUSERNAME() != null) {
                    showDialogLoading();
                    mPresenterCTV.api_get_lis_ctv(objLogin.getUSERNAME(), "",
                            "", "1", "10");
                }
                edt_phone.setText(objLogin.getUSERNAME());
                if (objLogin.getEMAIL() != null)
                    edt_email.setText(objLogin.getEMAIL());
                if (objLogin.getCITY() != null) {
                    edt_city.setText(objLogin.getCITY());
                }

                if (objLogin.getDISTRICT() != null)
                    edt_district.setText(objLogin.getDISTRICT());
                if (objLogin.getADDRESS() != null)
                    edt_address.setText(objLogin.getADDRESS());
                edt_pass.setVisibility(View.GONE);
                edt_pass_confirm.setVisibility(View.GONE);
                btn_update.setVisibility(View.VISIBLE);
                img_done.setVisibility(View.GONE);
            }
        } else {
            edt_pass.setVisibility(View.VISIBLE);
            edt_pass_confirm.setVisibility(View.VISIBLE);
            btn_update.setVisibility(View.GONE);
            img_done.setVisibility(View.VISIBLE);
        }
    }

    private void initEvent() {
        btn_update.setOnClickListener(this);
        img_done.setOnClickListener(this);
        edt_city.setOnClickListener(this);
        edt_district.setOnClickListener(this);
        txt_change_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edt_city_register:
                App.mCity = null;
                App.mDistrict = null;
                objCity = null;
                Intent intent_city = new Intent(ActivityUpdateInfoCTV.this,
                        ActivityListCity.class);
                startActivityForResult(intent_city, Constants.RequestCode.GET_CITY);
                break;
            case R.id.edt_district_register:
                if (objCity != null) {
                    App.mDistrict = null;
                    Intent intent_district = new Intent(ActivityUpdateInfoCTV.this, ActivityDistrict.class);
                    intent_district.putExtra(Constants.KEY_SEND_ID_CITY, objCity.getMATP());
                    startActivityForResult(intent_district, Constants.RequestCode.GET_DISTRICT);
                } else
                    showDialogNotify("Thông báo", "Bạn chưa chọn tỉnh thành phố nào.");
                break;
            case R.id.img_done:

                break;
            case R.id.btn_update:
                login_api_update();
                break;
            case R.id.txt_change_login:
                break;
        }
    }

    private void start_activity() {
        finish();
    }

    private void login_api_update() {
        if (edt_fullname.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào họ và tên.");
            KeyboardUtil.requestKeyboard(edt_fullname);
            return;
        }
        if (edt_phone.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào số điện thoại.");
            KeyboardUtil.requestKeyboard(edt_phone);
            return;
        }
        if (edt_email.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào email của bạn.");
            KeyboardUtil.requestKeyboard(edt_email);
            return;
        }
        if (objCity == null) {
            showDialogNotify("Thông báo", "Bạn chưa chọn tỉnh thành phố.");
            return;
        }
        if (objDistrict == null) {
            showDialogNotify("Thông báo", "Bạn chưa chọn quận huyện.");
            return;
        }
        if (edt_address.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào địa chỉ của bạn.");
            KeyboardUtil.requestKeyboard(edt_address);
            return;
        }
      /*  if (edt_pass.getText().toString().length() < 6) {
            showDialogNotify("Thông báo", "Mật khẩu phải dài hơn 6 ký tự.");
            KeyboardUtil.requestKeyboard(edt_pass);
            return;
        }
        if (edt_pass_confirm.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mật khẩu phải dài hơn 6 ký tự.");
            KeyboardUtil.requestKeyboard(edt_pass_confirm);
            return;
        }
        if (!edt_pass.getText().toString().equals(edt_pass_confirm.getText().toString())) {
            showDialogNotify("Thông báo", "Xác nhận mật khẩu không chính xác.");
            return;
        }*/
        api_update();
    }

    private void api_update() {
        showDialogLoading();
        mPresenterCTV.api_update_ctv(objLogin.getUSERNAME(), objLogin.getUSERNAME(), edt_fullname.getText().toString(),
                objCTV.getDOB(), objCTV.getGENDER(), edt_email.getText().toString(),
                objCity.getNAME(), objDistrict.getNAME(), edt_address.getText().toString()
                , "", "", "", "", "", "", "", "", "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_CITY:
                if (App.mCity != null) {
                    objCity = App.mCity;
                    edt_city.setText(App.mCity.getNAME());
                    if (App.mDistrict == null)
                        edt_district.setText("");
                } else {
                    edt_city.setText("");
                    edt_district.setText("");
                }
                break;
            case Constants.RequestCode.GET_DISTRICT:
                if (App.mDistrict != null) {
                    objDistrict = App.mDistrict;
                    edt_district.setText(App.mDistrict.getNAME());
                } else
                    edt_district.setText("");
                break;
        }
    }

    @Override
    public void show_error_api() {
        hideDialogLoading();
    }

    ObjCTV objCTV;

    @Override
    public void show_get_list_ctv(ResponGetLisCTV obj) {
        hideDialogLoading();
        if (obj != null && obj.getsERROR().equals("0000")) {
            if (obj.getLisDistrict() != null && obj.getLisDistrict().size() > 0) {
                objCTV = obj.getLisDistrict().get(0);
                objCity = new City(objCTV.getCITY_ID(), objCTV.getCITY());
                objDistrict = new Districts(objCTV.getDISTRICT_ID(), objCTV.getDISTRICT(), objCTV.getCITY_ID());
            }
        }
    }

    @Override
    public void show_update_ctv(ErrorApi obj) {
        hideDialogLoading();
        if (obj != null && obj.getsERROR().equals("0000"))
            finish();
        else showDialogNotify("Thông báo", obj.getsRESULT());
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
    public void show_login(ObjLogin obj) {
        hideDialogLoading();
    }

    @Override
    public void show_register(ErrorApi obj) {
        hideDialogLoading();
        if (obj.getsERROR().equals("0000")) {
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_USERNAME, edt_phone.getText().toString());
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_PASSWORD, edt_pass.getText().toString());
            Intent intent = new Intent(ActivityUpdateInfoCTV.this, ActivityLogin.class);
            intent.putExtra(Constants.KEY_SEND_IS_REGISTER, true);
            startActivity(intent);
            finish();
        } else
            showDialogNotify("Thông báo", obj.getsRESULT());
    }

    @Override
    public void show_update_device(ErrorApi obj) {

    }

    @Override
    public void show_get_shopinfo(ObjShopInfo obj) {

    }

    @Override
    public void show_check_device(ErrorApi obj) {

    }

    public static int APP_REQUEST_CODE = 99;

}
