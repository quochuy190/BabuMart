package com.vn.babumart.activity.commission;

import android.util.Log;
import com.google.gson.Gson;
import com.vn.babumart.apiservice_base.ApiServicePost;
import com.vn.babumart.callback.CallbackData;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.respon_api.ResponGetCommission;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 02-May-2019
 * Time: 10:44
 * Version: 1.0
 */
public class PresenterCommission implements InterfaceCommission.Presenter {
    private static final String TAG = "PresenterLogin";
    ApiServicePost mApiService;
    InterfaceCommission.View mView;

    public PresenterCommission(InterfaceCommission.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }

    @Override
    public void api_get_commission(String USERNAME, String USER_CTV, String PAGE, String NUMOFPAGE) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_commission";
        mMap.put("USERNAME", USERNAME);
        mMap.put("USER_CTV", USER_CTV);
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
                    ResponGetCommission obj = new Gson().fromJson(objT, ResponGetCommission.class);
                    mView.show_get_commission(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_withdrawal(String USERNAME, String PAGE, String NUMOFPAGE) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_withdrawal";
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
                    ResponGetCommission obj = new Gson().fromJson(objT, ResponGetCommission.class);
                    mView.show_get_withdrawal(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_withdrawal_history(String USERNAME, String USER_CTV, String START_TIME,
                                           String END_TIME, String PAGE, String NUMOFPAGE) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_withdrawal_history";
        mMap.put("USERNAME", USERNAME);
        mMap.put("USER_CTV", USER_CTV);
        mMap.put("START_TIME", START_TIME);
        mMap.put("END_TIME", END_TIME);
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
                    ResponGetCommission obj = new Gson().fromJson(objT, ResponGetCommission.class);
                    mView.show_get_withdrawal_history(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_request_withdrawal(String USERNAME, String AMOUNT) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_request_withdrawal";
        mMap.put("USERNAME", USERNAME);
        mMap.put("AMOUNT", AMOUNT);

        mApiService.getApiPostResfull_ALL(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api();
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    ErrorApi obj = new Gson().fromJson(objT, ErrorApi.class);
                    mView.show_get_request_withdrawal(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_update_comission(String USERNAME, String USER_CTV, String AMOUNT) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "update_comission";
        mMap.put("USERNAME", USERNAME);
        mMap.put("USER_CTV", USER_CTV);
        mMap.put("AMOUNT", AMOUNT);

        mApiService.getApiPostResfull_ALL(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api();
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    ErrorApi obj = new Gson().fromJson(objT, ErrorApi.class);
                    mView.show_update_comission(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_update_withdrawal(String USERNAME, String ID_REQUEST) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "update_withdrawal";
        mMap.put("USERNAME", USERNAME);
        mMap.put("ID_REQUEST", ID_REQUEST);

        mApiService.getApiPostResfull_ALL(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api();
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    ErrorApi obj = new Gson().fromJson(objT, ErrorApi.class);
                    mView.show_update_withdrawal(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }
}
