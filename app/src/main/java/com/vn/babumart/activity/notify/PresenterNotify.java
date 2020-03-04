package com.vn.babumart.activity.notify;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.babumart.apiservice_base.ApiServicePost;
import com.vn.babumart.callback.CallbackData;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.respon_api.ResponGetnotify;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 02-May-2019
 * Time: 10:44
 * Version: 1.0
 */
public class PresenterNotify implements InterfaceNotify.Presenter {
    private static final String TAG = "PresenterProduct";
    ApiServicePost mApiService;
    InterfaceNotify.View mView;

    public PresenterNotify(InterfaceNotify.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }


    @Override
    public void  api_get_list_notify(String USERNAME, String PAGE, String NUMOFPAGE) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_list_notify";
        mMap.put("USERNAME", USERNAME);
        mMap.put("PAGE", PAGE);
        mMap.put("NUMOFPAGE", NUMOFPAGE);

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
                    ResponGetnotify obj = new Gson().fromJson(objT, ResponGetnotify.class);
                    mView.show_get_notify(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_update_notify(String USERNAME, String ID_NOTIFY) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "update_notify";
        mMap.put("USERNAME", USERNAME);
        mMap.put("ID_NOTIFY", ID_NOTIFY);

        mApiService.getApiPostResfull_ALL(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api();
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                ErrorApi objError = new Gson().fromJson(objT, ErrorApi.class);
                mView.show_update_notify(objError);
            }
        }, sService, mMap);
    }
}
