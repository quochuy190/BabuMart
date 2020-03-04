package com.vn.babumart.activity.tintuc;

import android.util.Log;
import com.google.gson.Gson;
import com.vn.babumart.apiservice_base.ApiServicePost;
import com.vn.babumart.callback.CallbackData;
import com.vn.babumart.config.Config;
import com.vn.babumart.models.respon_api.ResponseInfomation;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 02-May-2019
 * Time: 10:44
 * Version: 1.0
 */
public class PresenterTintuc implements InterfaceTintuc.Presenter {
    private static final String TAG = "PresenterProduct";
    ApiServicePost mApiService;
    InterfaceTintuc.View mView;

    public PresenterTintuc(InterfaceTintuc.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }

    @Override
    public void api_get_infomation(String USERNAME, final String TYPES, String CATEGORY) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_infomation";
        mMap.put("USERNAME", USERNAME);
        mMap.put("TYPES", TYPES);
        mMap.put("CATEGORY", CATEGORY);
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
                    ResponseInfomation obj = new Gson().fromJson(objT, ResponseInfomation.class);
                    if (TYPES.equals("3")) {
                        mView.show_api_infomation_daotao(obj);
                    } else if (TYPES.equals("4"))
                        mView.show_api_infomation(obj);
                    else if (TYPES.equals("5")) {
                        if (obj != null && obj.getmList() != null && obj.getmList().size() > 0) {
                            Config.mShipPolicy = obj.getmList().get(0);
                        }
                    } else {
                        mView.show_api_infomation(obj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }
}
