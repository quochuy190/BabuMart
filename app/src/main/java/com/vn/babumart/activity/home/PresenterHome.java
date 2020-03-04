package com.vn.babumart.activity.home;

import android.util.Log;
import com.google.gson.Gson;
import com.vn.babumart.activity.home.InterfaceHome;
import com.vn.babumart.apiservice_base.ApiServicePost;
import com.vn.babumart.callback.CallbackData;
import com.vn.babumart.config.Config;
import com.vn.babumart.models.respon_api.ResponseGetConfig;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 16-August-2019
 * Time: 16:52
 * Version: 1.0
 */
public class PresenterHome implements InterfaceHome.Presenter {
    private static final String TAG = "PresenterHome";
    ApiServicePost mApiService;
    InterfaceHome.View mView;

    public PresenterHome(InterfaceHome.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }

    @Override
    public void api_get_config(String sUserName, String sShopId) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_config1";
        mMap.put("USERNAME", sUserName);

        mApiService.getApiPostResfull_ALL(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    ResponseGetConfig obj = new Gson().fromJson(objT, ResponseGetConfig.class);
                    if (obj != null && obj.getmList() != null)
                        Config.mLisConfigCommis = obj.getmList();
                    Log.d(TAG, "onGetDataSuccess: " + obj.getmList());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, sService, mMap);
    }
}
