package com.vn.babumart.activity.order;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.babumart.apiservice_base.ApiServicePost;
import com.vn.babumart.callback.CallbackData;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.respon_api.GetFeeShipGHNRespon;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 02-May-2019
 * Time: 10:44
 * Version: 1.0
 */
public class PresenterShip implements InterfaceShiper.Presenter {
    private static final String TAG = "PresenterProduct";
    ApiServicePost mApiService;
    InterfaceShiper.View mView;

    public PresenterShip(InterfaceShiper.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }

    @Override
    public void get_ship_payment(String USERNAME, String LISTID, String TODISTRICTID, String IDSHOP) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "sendGhn";
        mMap.put("USERNAME", USERNAME);
        mMap.put("LISTID", LISTID);
        mMap.put("TODISTRICTID", TODISTRICTID);

        mApiService.getApiPostResfull_ALL(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                //mView.show_error_api();
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    ErrorApi obj = new Gson().fromJson(objT, ErrorApi.class);
                    if (obj.getsERROR().equals("0000")) {
                        GetFeeShipGHNRespon objFee = new Gson().fromJson(obj.getsRESULT(), GetFeeShipGHNRespon.class);
                        objFee.setsERROR("0000");
                        mView.show_ship_payment(objFee);
                    } else {
                        GetFeeShipGHNRespon objFee = new GetFeeShipGHNRespon(obj.getsERROR(), obj.getsRESULT());
                        mView.show_ship_payment(objFee);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    //  mView.show_error_api();
                }
            }
        }, sService, mMap);
    }
}
