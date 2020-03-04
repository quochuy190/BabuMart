package com.vn.babumart.activity.login;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.babumart.apiservice_base.ApiServicePost;
import com.vn.babumart.callback.CallbackData;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.models.ObjShopInfo;
import com.vn.babumart.untils.SharedPrefs;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 02-May-2019
 * Time: 10:44
 * Version: 1.0
 */
public class PresenterLogin implements InterfaceLogin.Presenter {
    private static final String TAG = "PresenterLogin";
    ApiServicePost mApiService;
    InterfaceLogin.View mView;

    public PresenterLogin(InterfaceLogin.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }

    @Override
    public void api_login(String sUserName, String sPassWord, String sUUID) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "login";
        mMap.put("USERNAME", sUserName);
        mMap.put("PASSWORD", sPassWord);
        mMap.put("UUID", sUUID);
        mApiService.getApiService(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api();
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }
            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    ObjLogin obj = new Gson().fromJson(objT, ObjLogin.class);
                    mView.show_login(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_register(String FULL_NAME, String MOBILE, String EMAIL, String ID_CITY,
                             String ID_DISTRICT, String ADDRESS, String PASSWORD, String INVITE_CODE, String ID_WARD) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "reg_user1";
        mMap.put("FULL_NAME", FULL_NAME);
        mMap.put("MOBILE", MOBILE);
        mMap.put("EMAIL", EMAIL);
        mMap.put("ID_CITY", ID_CITY);
        mMap.put("ID_DISTRICT", ID_DISTRICT);
        mMap.put("ADDRESS", ADDRESS);
        mMap.put("PASSWORD", PASSWORD);
        mMap.put("INVITE_CODE", INVITE_CODE);
        mMap.put("ID_WARD", ID_WARD);
        mApiService.getApiService(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api();
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    ErrorApi obj = new Gson().fromJson(objT, ErrorApi.class);
                    mView.show_register(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_update_device(String USERNAME, String APP_VERSION, String MODEL_NAME, String TOKEN_KEY,
                                  String DEVICE_TYPE, String OS_VERSION, String UUID) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "update_device";
        mMap.put("USERNAME", USERNAME);
        mMap.put("APP_VERSION", APP_VERSION);
        mMap.put("MODEL_NAME", MODEL_NAME);
        mMap.put("TOKEN_KEY", TOKEN_KEY);
        mMap.put("DEVICE_TYPE", DEVICE_TYPE);
        mMap.put("OS_VERSION", OS_VERSION);
        mMap.put("UUID", UUID);
        mApiService.getApiPostResfull_ALL(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api();
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    ErrorApi obj = new Gson().fromJson(objT, ErrorApi.class);
                    mView.show_update_device(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_getshopinfo(String IDSHOP) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "getshopinfo";
        mMap.put("IDSHOP", IDSHOP);

        mApiService.getApiService(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api();
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    ObjShopInfo obj = new Gson().fromJson(objT, ObjShopInfo.class);
                    SharedPrefs.getInstance().put(Constants.KEY_SAVE_OBJ_SHOP, obj);
                    mView.show_get_shopinfo(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_check_device(String APP_VERSION, String MODEL_NAME,
                                 String TOKEN_KEY, String DEVICE_TYPE, String OS_VERSION, String UUID) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "check_device";
        mMap.put("APP_VERSION", APP_VERSION);
        mMap.put("MODEL_NAME", MODEL_NAME);
        mMap.put("TOKEN_KEY", TOKEN_KEY);
        mMap.put("DEVICE_TYPE", DEVICE_TYPE);
        mMap.put("OS_VERSION", OS_VERSION);
        mMap.put("UUID", UUID);

        mApiService.getApiService(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api();
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    ErrorApi obj = new Gson().fromJson(objT, ErrorApi.class);
                    mView.show_check_device(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }
}
