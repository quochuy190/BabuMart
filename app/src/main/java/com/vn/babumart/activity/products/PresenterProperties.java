package com.vn.babumart.activity.products;

import android.util.Log;
import com.google.gson.Gson;
import com.vn.babumart.activity.products.InterfaceProperties;
import com.vn.babumart.apiservice_base.ApiServicePost;
import com.vn.babumart.callback.CallbackData;
import com.vn.babumart.models.respon_api.ResponGetPropeti;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 02-May-2019
 * Time: 10:44
 * Version: 1.0
 */
public class PresenterProperties implements InterfaceProperties.Presenter {
    private static final String TAG = "PresenterProduct";
    ApiServicePost mApiService;
    InterfaceProperties.View mView;

    public PresenterProperties(InterfaceProperties.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }

    @Override
    public void api_get_properties(String USERNAME, String LIST_PROPERTIES) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_properties";
        mMap.put("USERNAME", USERNAME);
        mMap.put("LIST_PROPERTIES", LIST_PROPERTIES);

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
                    ResponGetPropeti obj = new Gson().fromJson(objT, ResponGetPropeti.class);
                    mView.show_get_properties(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }
}
