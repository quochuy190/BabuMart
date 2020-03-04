package com.vn.babumart.activity.login;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.babumart.apiservice_base.ApiServicePost;
import com.vn.babumart.callback.CallbackData;
import com.vn.babumart.models.ErrorApi;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 02-May-2019
 * Time: 10:44
 * Version: 1.0
 */
public class PresenterOTP implements InterfaceOTP.Presenter {
    private static final String TAG = "PresenterLogin";
    ApiServicePost mApiService;
    InterfaceOTP.View mView;

    public PresenterOTP(InterfaceOTP.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }

    @Override
    public void api_get_code(String MSISDN) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "sendcode";
        mMap.put("MSISDN", MSISDN);

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
                    mView.show_get_code(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_active(String ICODE, String MSISDN) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "active";
        mMap.put("ICODE", ICODE);
        mMap.put("MSISDN", MSISDN);

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
                    mView.show_active_otp(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }
}
