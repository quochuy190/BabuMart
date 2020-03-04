package com.vn.babumart.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.vn.babumart.App;
import com.vn.babumart.BuildConfig;
import com.vn.babumart.R;
import com.vn.babumart.activity.login.Menu_Search.ActivityDistrict;
import com.vn.babumart.activity.login.Menu_Search.ActivityListCity;
import com.vn.babumart.activity.login.Menu_Search.ActivityListWard;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.City;
import com.vn.babumart.models.Districts;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.MessageEvent;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.models.ObjShopInfo;
import com.vn.babumart.models.Ward;
import com.vn.babumart.untils.KeyboardUtil;
import com.vn.babumart.untils.PhoneNumberUntil;
import com.vn.babumart.untils.SharedPrefs;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 23-April-2019
 * Time: 13:33
 * Version: 1.0
 */
public class ActivityRegister extends BaseActivity
        implements View.OnClickListener, InterfaceLogin.View {
    private static final String TAG = "ActivityRegister";
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
    @BindView(R.id.edt_ward_register)
    EditText edt_ward_register;

    @BindView(R.id.edt_pass_register)
    EditText edt_pass;
    @BindView(R.id.edt_pass_confirm_register)
    EditText edt_pass_confirm;
    @BindView(R.id.txt_change_login)
    TextView txt_change_login;
    @BindView(R.id.edt_code_register)
    EditText edt_code_register;
    City objCity;
    Ward objWard;
    Districts objDistrict;
    PresenterLogin mPresenter;
    String sUser, sPass;

    @Override
    public int setContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterLogin(this);
        initData();
        initEvent();
    }

    String sPhone;
    boolean isLoginGuest;

    private void initData() {
        isLoginGuest = getIntent().getBooleanExtra(Constants.KEY_SEND_LOGIN_GUEST, false);
        sPhone = getIntent().getStringExtra(Constants.KEY_SEND_PHONE_REGISTER);
        if (sPhone != null) {
            edt_phone.setText(sPhone);
            edt_phone.setEnabled(false);
            edt_phone.setFocusable(false);
        }
        boolean isUpdate = getIntent().getBooleanExtra(Constants.KEY_SEND_UPDATE_USER, false);
        if (isUpdate) {
            ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
            if (objLogin != null) {
                if (objLogin.getFULL_NAME() != null)
                    edt_fullname.setText(objLogin.getFULL_NAME());
                if (objLogin.getUSERNAME() != null)
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
                img_done.setVisibility(View.GONE);
            }
        } else {
            edt_pass.setVisibility(View.VISIBLE);
            edt_pass_confirm.setVisibility(View.VISIBLE);
            img_done.setVisibility(View.VISIBLE);
        }
    }

    private void initEvent() {
       /* img_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityRegister.this, ActivityConfirmOTP.class));
            }
        });*/
        img_done.setOnClickListener(this);
        edt_city.setOnClickListener(this);
        edt_district.setOnClickListener(this);
        edt_ward_register.setOnClickListener(this);
        txt_change_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edt_city_register:
                App.mCity = null;
                App.mDistrict = null;
                objCity = null;
                Intent intent_city = new Intent(ActivityRegister.this,
                        ActivityListCity.class);
                startActivityForResult(intent_city, Constants.RequestCode.GET_CITY);
                break;
            case R.id.edt_district_register:
                if (objCity != null) {
                    App.mDistrict = null;
                    Intent intent_district = new Intent(ActivityRegister.this,
                            ActivityDistrict.class);
                    intent_district.putExtra(Constants.KEY_SEND_ID_CITY, objCity.getMATP());
                    startActivityForResult(intent_district, Constants.RequestCode.GET_DISTRICT);
                } else
                    showDialogNotify("Thông báo", "Bạn chưa chọn tỉnh thành phố nào.");
                break;
            case R.id.edt_ward_register:
                if (objDistrict != null) {
                    App.mWard = null;
                    Intent intent_district = new Intent(ActivityRegister.this,
                            ActivityListWard.class);
                    intent_district.putExtra(Constants.KEY_SEND_ID_DISTRICT, objDistrict.getMAQH());
                    startActivityForResult(intent_district, Constants.RequestCode.GET_WARD);
                } else
                    showDialogNotify("Thông báo", "Bạn chưa chọn quận huyện nào.");
                break;
            case R.id.img_done:
                login_api();
                break;
            case R.id.txt_change_login:
                start_activity();
                break;
        }
    }

    private void start_activity() {
        Intent intent = new Intent(ActivityRegister.this,
                ActivityLogin.class);
        startActivity(intent);
        finish();
    }

    String sCode = "";

    private void login_api() {
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
        if (!PhoneNumberUntil.isPhoneNumberNew(edt_phone.getText().toString().trim())) {
            showDialogNotify("Thông báo", "Số điện thoại nhập vào không đúng định dạng");
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
        if (objWard == null) {
            showDialogNotify("Thông báo", "Bạn chưa chọn quận huyện.");
            return;
        }
        if (edt_address.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mời bạn nhập vào địa chỉ của bạn.");
            KeyboardUtil.requestKeyboard(edt_address);
            return;
        }
        if (edt_pass.getText().toString().length() < 6) {
            showDialogNotify("Thông báo", "Mật khẩu phải dài hơn 6 ký tự.");
            KeyboardUtil.requestKeyboard(edt_pass);
            return;
        }
        if (edt_pass_confirm.getText().toString().length() == 0) {
            showDialogNotify("Thông báo", "Mật khẩu phải dài hơn 6 ký tự.");
            KeyboardUtil.requestKeyboard(edt_pass_confirm);
            return;
        }
        if (edt_code_register.getText().toString().length() > 0) {
            sCode = edt_code_register.getText().toString();
        } else {
            sCode = "";
        }
        if (!edt_pass.getText().toString().equals(edt_pass_confirm.getText().toString())) {
            showDialogNotify("Thông báo", "Xác nhận mật khẩu không chính xác.");
            return;
        }
        goToMyLoggedInActivity();
        /*AccessToken accessToken = AccountKit.getCurrentAccessToken();
        // phoneLogin(edt_otp_code);

        if (accessToken != null) {
            goToMyLoggedInActivity();
            //Handle Returning User
        } else {
            //Handle new or logged out user
            login_api();
            //phoneLogin(null);
        }*/
    }

    private void goToMyLoggedInActivity() {
        showDialogLoading();
        mPresenter.api_register(edt_fullname.getText().toString(), edt_phone.getText().toString(),
                edt_email.getText().toString(), objCity.getMATP(), objDistrict.getMAQH(),
                edt_address.getText().toString(), edt_pass.getText().toString(), sCode, objWard.getXAID());
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
            case Constants.RequestCode.GET_WARD:
                if (App.mWard != null) {
                    objWard = App.mWard;
                    edt_ward_register.setText(App.mWard.getNAME());
                } else
                    edt_ward_register.setText("");
                break;
        }

    }


    @Override
    public void show_error_api() {
        hideDialogLoading();
    }

    @Override
    public void show_login(ObjLogin obj) {
        hideDialogLoading();
        String sTokenKey = SharedPrefs.getInstance().get(Constants.KEY_TOKEN, String.class);
        mPresenter.api_update_device(sUser, BuildConfig.VERSION_NAME, android.os.Build.BRAND + " "
                        + android.os.Build.MODEL, sTokenKey, "1", android.os.Build.VERSION.RELEASE,
                Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        SharedPrefs.getInstance().put(Constants.KEY_SAVE_IS_LOGIN, true);
        SharedPrefs.getInstance().put(Constants.KEY_SAVE_USER_LOGIN, obj);
        SharedPrefs.getInstance().put(Constants.KEY_SAVE_USERNAME, obj.getUSERNAME());
        SharedPrefs.getInstance().put(Constants.KEY_SAVE_PASSWORD, obj.getPASSWORD());
         /*   Intent intent = new Intent(ActivityLogin.this, ActivityConfirmOTP.class);
            startActivity(intent);*/
        //    finish();
        EventBus.getDefault().postSticky(new MessageEvent(Constants.EventBus.KEY_LOGIN_GUEST, 1, 0));
        setResult(RESULT_OK, new Intent());
        finish();

    }

    @Override
    public void show_register(ErrorApi obj) {
        hideDialogLoading();
        if (obj.getsERROR().equals("0000")) {
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_USERNAME, edt_phone.getText().toString());
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_PASSWORD, edt_pass.getText().toString());
            sUser = edt_phone.getText().toString().trim();
            sPass = edt_pass.getText().toString().trim();
            if (isLoginGuest) {
                mPresenter.api_login(sUser, sPass,
                        Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
            } else {
                Intent intent = new Intent(ActivityRegister.this,
                        ActivityLogin.class);
                intent.putExtra(Constants.KEY_SEND_IS_REGISTER, true);
                startActivity(intent);
                finish();
            }

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


}
