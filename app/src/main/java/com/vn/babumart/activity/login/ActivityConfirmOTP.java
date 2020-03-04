package com.vn.babumart.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.bumptech.glide.Glide;
import com.vn.babumart.App;
import com.vn.babumart.BuildConfig;
import com.vn.babumart.R;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.models.ObjShopInfo;
import com.vn.babumart.untils.PhoneNumberUntil;
import com.vn.babumart.untils.SharedPrefs;

import butterknife.BindView;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 23-April-2019
 * Time: 14:40
 * Version: 1.0
 */
public class ActivityConfirmOTP extends BaseActivity implements InterfaceOTP.View, InterfaceLogin.View {
    private static final String TAG = "ActivityConfirmOTP";
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.edt_otp_code)
    EditText edt_otp_code;
    @BindView(R.id.txt_register)
    TextView txt_register;
    @BindView(R.id.img_logo)
    ImageView img_logo;
    @BindView(R.id.img_background)
    ImageView img_background;
    PresenterOTP mPesenterOTP;
    String sType = "0";
    String MSISDN = "";
    PresenterLogin mPresenterLogin;

    @Override
    public int setContentViewId() {
        return R.layout.activity_confirm_otp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPesenterOTP = new PresenterOTP(this);
        mPresenterLogin = new PresenterLogin(this);
        Glide.with(this).load(R.drawable.ic_logo).into(img_logo);
        initData();
        initEvent();

    }

    boolean isLoginGuest;

    private void initData() {
        ObjShopInfo objShop = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJ_SHOP, ObjShopInfo.class);
        isLoginGuest = getIntent().getBooleanExtra(Constants.KEY_SEND_LOGIN_GUEST, false);
    }

    private void initEvent() {
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityConfirmOTP.this, ActivityLogin.class);
                if (isLoginGuest) {
                    intent.putExtra(Constants.KEY_SEND_LOGIN_GUEST, true);
                    startActivityForResult(intent, Constants.RequestCode.GET_LOGIN_BUY);
                } else
                    startActivity(intent);
                finish();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   startActivity(new Intent(ActivityConfirmOTP.this, MainActivity.class));
                if (edt_otp_code.getText().toString().trim().length() > 0) {
                    phoneLogin();
                } else {
                    showDialogNotify("Thông báo", "Bạn chưa nhập vào số điện thoại.");

                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RequestCode.GET_LOGIN_BUY
                && resultCode == RESULT_OK) {
            setResult(RESULT_OK, new Intent());
            finish();
        }
    }

    public void phoneLogin() {

        if (sType.equals("0")) {
            if (PhoneNumberUntil.isPhoneNumberNew(edt_otp_code.getText().toString().trim())) {
                showDialogLoading();
                MSISDN = PhoneNumberUntil.convertToVnPhoneNumber(edt_otp_code.getText().toString().trim());
                mPesenterOTP.api_get_code(edt_otp_code.getText().toString().trim());
            } else showDialogNotify("Thông báo", "Số điện thoại không đúng định dạng.");

        } else {
              mPesenterOTP.api_active(edt_otp_code.getText().toString().trim(), MSISDN);
            //goToMyLoggedInActivity();
        }

    }

    @Override
    public void show_error_api() {
        hideDialogLoading();
    }

    @Override
    public void show_login(ObjLogin obj) {
        hideDialogLoading();
        if (obj.getsERROR().equals("0000")) {
            String UUID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            App.isLoginHome = true;
            String sTokenKey = SharedPrefs.getInstance().get(Constants.KEY_TOKEN, String.class);
            mPresenterLogin.api_update_device(MSISDN, BuildConfig.VERSION_NAME, android.os.Build.BRAND + " "
                    + android.os.Build.MODEL, sTokenKey, "1", android.os.Build.VERSION.RELEASE, UUID);
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_IS_LOGIN, true);
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_USER_LOGIN, obj);
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_USERNAME, obj.getUSERNAME());
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_PASSWORD, obj.getPASSWORD());
         /*   Intent intent = new Intent(ActivityLogin.this, ActivityConfirmOTP.class);
            startActivity(intent);*/
            //    finish();
            goToMyLoggedInActivity();

        } else
            showAlertDialog("Thông báo", obj.getsRESULT());
    }

    private void goToMyLoggedInActivity() {
        Intent intent = new Intent(
                ActivityConfirmOTP.this, ActivityRegister.class);
        intent.putExtra(Constants.KEY_SEND_PHONE_REGISTER, MSISDN);
        if (isLoginGuest) {
            intent.putExtra(Constants.KEY_SEND_LOGIN_GUEST, true);
            startActivityForResult(intent, Constants.RequestCode.GET_LOGIN_BUY);
        } else {
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void show_register(ErrorApi obj) {
        if (obj != null && obj.getsERROR().equals("0000")) {
            // mPresenterLogin.api_login(MSISDN, "123456");
        } else {
            hideDialogLoading();
            showDialogNotify("Thông báo", obj.getsRESULT());
        }
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

    @Override
    public void show_get_code(ErrorApi obj) {
        hideDialogLoading();
        if (obj.getsERROR().equals("0000")) {
            sType = "1";
            edt_otp_code.setText("");
            edt_otp_code.setHint("Nhập vào mã OTP");
            btn_login.setText("Đăng ký");
        } else {
            showDialogNotify("Thông báo", obj.getsRESULT());
        }
    }

    @Override
    public void show_active_otp(ErrorApi obj) {
        //hideDialogLoading();
        if (obj.getsERROR().equals("0000")) {
            goToMyLoggedInActivity();
        } else {
            hideDialogLoading();
            showDialogNotify("Thông báo", obj.getsRESULT());
        }

    }
}
