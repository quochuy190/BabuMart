package com.vn.babumart.activity.login.Menu_Search;

import android.util.Log;
import com.google.gson.Gson;
import com.vn.babumart.apiservice_base.ApiServicePost;
import com.vn.babumart.callback.CallbackData;
import com.vn.babumart.models.respon_api.ResponGetDistrict;
import com.vn.babumart.models.respon_api.ResponGetProvince;
import com.vn.babumart.models.respon_api.ResponGetWard;


import java.util.LinkedHashMap;
import java.util.Map;
/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 02-May-2019
 * Time: 11:46
 * Version: 1.0
 */
public class PresenterSearchMenu implements InterfaceSearchMenu.Presenter {
    private static final String TAG = "PresenterSearchMenu";
    ApiServicePost mApiService;
    InterfaceSearchMenu.View mView;

    public PresenterSearchMenu(InterfaceSearchMenu.View mView) {
        mApiService = new ApiServicePost();
        this.mView = mView;
    }

    @Override
    public void api_get_citys() {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_city";
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
                    //jArray = new JSONArray(c);
                    ResponGetProvince obj = new Gson().fromJson(objT, ResponGetProvince.class);
                    //   List<Baitap_Tuan> mLis = Baitap_Tuan.getList(objT);
                    mView.show_api_get_city(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_district(String sCity_Id) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_district";
        mMap.put("ID_CITY", sCity_Id);
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
                    //jArray = new JSONArray(c);
                    ResponGetDistrict obj = new Gson().fromJson(objT, ResponGetDistrict.class);
                    //   List<Baitap_Tuan> mLis = Baitap_Tuan.getList(objT);
                    mView.show_api_get_district(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_ward(String ID_DISTRICT) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_ward";
        mMap.put("ID_DISTRICT", ID_DISTRICT);
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
                    //jArray = new JSONArray(c);
                    ResponGetWard obj = new Gson().fromJson(objT, ResponGetWard.class);
                    //   List<Baitap_Tuan> mLis = Baitap_Tuan.getList(objT);
                    mView.show_api_get_ward(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }
}
