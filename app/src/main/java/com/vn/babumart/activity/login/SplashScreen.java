package com.vn.babumart.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.ImageView;



import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vn.babumart.BuildConfig;
import com.vn.babumart.MainActivity;
import com.vn.babumart.R;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.models.ObjShopInfo;
import com.vn.babumart.untils.SharedPrefs;



public class SplashScreen extends BaseActivity implements InterfaceLogin.View {
    private static final String TAG = "SplashScreen";

    ImageView ic_logo;
    // public static Storage storage; // this Preference comes for free from the library
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    Intent mainIntent = new Intent();
    Intent mainIntent_welcom = new Intent();
    String id;
    boolean isCheckDevice;
    private PresenterLogin mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: start lunch");
        boolean isLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_IS_LOGIN, Boolean.class);
        ic_logo = findViewById(R.id.ic_logo);
        mPresenter = new PresenterLogin(this);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo);
        Glide.with(this).load(R.drawable.ic_logo)
                .apply(options).into(ic_logo);
        if (isLogin) {
            mainIntent.setClass(SplashScreen.this, MainActivity.class);
        } else {
            mainIntent.setClass(SplashScreen.this, MainActivity.class);
            //  mainIntent.setClass(SplashScreen.this, ActivityIntroduce.class);
        }
        String UUID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        isCheckDevice = SharedPrefs.getInstance().get(Constants.KEY_SAVE_IS_CHECK_DEVICE, Boolean.class);
        sTokenKey = SharedPrefs.getInstance().get(Constants.KEY_TOKEN, String.class);
        Log.e(TAG, "onCreate: " + sTokenKey);
        if (!isCheckDevice) {
            if (sTokenKey == null) {
                sTokenKey = "";
            }
            mPresenter.api_check_device(BuildConfig.VERSION_NAME, android.os.Build.BRAND + " "
                    + android.os.Build.MODEL, sTokenKey, "1", android.os.Build.VERSION.RELEASE, UUID);
        } else {
            start_activity();
        }
        //start_activity();

    }

    private String sTokenKey = "";


    private void start_activity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_flash;
    }

    @Override
    public void show_error_api() {
        hideDialogLoading();
        start_activity();
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

    }

    @Override
    public void show_check_device(ErrorApi obj) {
        if (obj != null && obj.getsERROR().equals("0000")) {
            Log.e(TAG, "show_update_device: " + obj.getsRESULT());
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_IS_CHECK_DEVICE, true);
        }
        Log.e(TAG, "show_update_device: " + obj.getsRESULT());
        start_activity();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
}