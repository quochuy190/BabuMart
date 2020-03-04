package com.vn.babumart.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.vn.babumart.App;
import com.vn.babumart.BuildConfig;
import com.vn.babumart.MainActivity;
import com.vn.babumart.R;
import com.vn.babumart.activity.collaborators.InterfaceCollaborators;
import com.vn.babumart.activity.collaborators.PresenterCTV;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.MessageEvent;
import com.vn.babumart.models.ObjCTV;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.models.ObjShopInfo;
import com.vn.babumart.models.respon_api.ResponGetLisCTV;
import com.vn.babumart.untils.SharedPrefs;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 23-April-2019
 * Time: 08:39
 * Version: 1.0
 */
public class ActivityLogin extends BaseActivity implements
        View.OnClickListener, InterfaceCollaborators.View, InterfaceLogin.View {
    private static final String TAG = "ActivityLogin";
    @BindView(R.id.edt_username)
    EditText edt_username;
    @BindView(R.id.edt_pass)
    EditText edt_pass;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.txt_register)
    TextView txt_register;
    @BindView(R.id.txt_remember_pass)
    TextView txt_remember_pass;
    @BindView(R.id.img_showpass)
    ImageView img_showpass;
    @BindView(R.id.img_logo)
    ImageView img_logo;
    PresenterLogin mPresenter;
    PresenterCTV mPresenterCTV;
    String sUser, sPass;
    String UUID;
    boolean isLoginGuest;

    @Override
    public int setContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterLogin(this);
        mPresenterCTV = new PresenterCTV(this);
        UUID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        get_token_firebase();
        Glide.with(this).load(R.drawable.ic_logo).into(img_logo);
        initData();
        initEvent();
    }

    private void initData() {
        isLoginGuest = getIntent().getBooleanExtra(Constants.KEY_SEND_LOGIN_GUEST, false);
        ObjShopInfo objShop = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJ_SHOP, ObjShopInfo.class);
        boolean isRegister = getIntent().getBooleanExtra(Constants.KEY_SEND_IS_REGISTER, false);
        if (isRegister) {
            sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
            sPass = SharedPrefs.getInstance().get(Constants.KEY_SAVE_PASSWORD, String.class);
            showDialogLoading();
            mPresenter.api_login(sUser, sPass, UUID);
        } else {
            sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
            sPass = SharedPrefs.getInstance().get(Constants.KEY_SAVE_PASSWORD, String.class);
            edt_username.setText(sUser);
           // edt_pass.setText(sPass);
        }
    }

    private void initEvent() {
        btn_login.setOnClickListener(this);
        img_showpass.setOnClickListener(this);
        txt_register.setOnClickListener(this);
        txt_remember_pass.setOnClickListener(this);
    }

    private void start_activity() {
        Intent intent = new Intent(ActivityLogin.this,
               ActivityConfirmOTP.class);
        if (isLoginGuest) {
            intent.putExtra(Constants.KEY_SEND_LOGIN_GUEST, true);
            startActivityForResult(intent, Constants.RequestCode.GET_LOGIN_BUY);
        } else {
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.img_showpass:
                show_pass();
                break;
            case R.id.txt_register:
                start_activity();
                break;
            case R.id.txt_remember_pass:
                showDialogNotify("Thông báo", "Mời bạn liên hệ với chủ shop để lấy lại mật khẩu.");
                break;
        }
    }

    boolean isShowpass = true;

    private void show_pass() {
        if (!isShowpass) {
            img_showpass.setImageDrawable(getResources().getDrawable(R.drawable.icon_hide_pass));
            //Glide.with(ActivityLogin.this).load(R.drawable.ic_eye_hide).into(img_showpass);
            edt_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isShowpass = !isShowpass;
        } else {
            img_showpass.setImageDrawable(getResources().getDrawable(R.drawable.icon_show_pass));
            edt_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isShowpass = !isShowpass;
        }
    }

    private void get_token_firebase() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.d(TAG, token);
                    }
                });
    }

    private void login() {
        if (edt_username.getText().toString().length() > 0) {
            sUser = edt_username.getText().toString();
        } else {
            showAlertDialog("Thông báo", "Bạn chưa nhập vào tên đăng nhập.");
            return;
        }
        if (edt_pass.getText().toString().length() > 0) {
            sPass = edt_pass.getText().toString();
        } else {
            showAlertDialog("Thông báo", "Bạn chưa nhập vào mật khẩu.");
            return;
        }
        showDialogLoading();
        mPresenter.api_login(sUser, sPass, UUID);
    }

    @Override
    public void show_error_api() {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_get_list_ctv(ResponGetLisCTV obj) {

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
    public void show_login(ObjLogin obj) {
        hideDialogLoading();
        if (obj.getsERROR().equals("0000")) {
            App.isLoginHome = true;
            String sTokenKey = SharedPrefs.getInstance().get(Constants.KEY_TOKEN, String.class);
            mPresenter.api_update_device(sUser, BuildConfig.VERSION_NAME, android.os.Build.BRAND + " "
                    + android.os.Build.MODEL, sTokenKey, "1", android.os.Build.VERSION.RELEASE, UUID);
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_IS_LOGIN, true);
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_USER_LOGIN, obj);
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_USERNAME, obj.getUSERNAME());
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_PASSWORD, obj.getPASSWORD());
         /*   Intent intent = new Intent(ActivityLogin.this, ActivityConfirmOTP.class);
            startActivity(intent);*/
            //    finish();
            if (isLoginGuest) {
                EventBus.getDefault().postSticky(new MessageEvent(Constants.EventBus.KEY_LOGIN_GUEST, 1, 0));
                // mPresenterCTV.api_get_ctv_detail(sUser, sUser, Config.ID_SHOP);
                setResult(RESULT_OK, new Intent());
                finish();
            } else
                goToMyLoggedInActivity();


        } else
            showAlertDialog("Thông báo", obj.getsRESULT());
    }

    @Override
    public void show_register(ErrorApi obj) {

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

    private void goToMyLoggedInActivity() {
        Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_LOGIN_BUY:
                if (resultCode == RESULT_OK) {
                    sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
                    sPass = SharedPrefs.getInstance().get(Constants.KEY_SAVE_PASSWORD, String.class);
                    showDialogLoading();
                    mPresenter.api_login(sUser, sPass, UUID);
                }
                break;
        }
    }
}
