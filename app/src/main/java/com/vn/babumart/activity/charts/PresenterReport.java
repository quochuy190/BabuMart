package com.vn.babumart.activity.charts;

import android.util.Log;
import com.google.gson.Gson;
import com.vn.babumart.apiservice_base.ApiServicePost;
import com.vn.babumart.callback.CallbackData;
import com.vn.babumart.models.respon_api.ResponGetReportDefault;
import com.vn.babumart.models.respon_api.ResponGetReportListCTV;
import com.vn.babumart.models.respon_api.ResponGetReportProduct;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 02-May-2019
 * Time: 10:44
 * Version: 1.0
 */
public class PresenterReport implements InterfaceReport.Presenter {
    private static final String TAG = "PresenterReport";
    ApiServicePost mApiService;
    InterfaceReport.View mView;

    public PresenterReport(InterfaceReport.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }

    @Override
    public void api_report_default(String USERNAME, String YEAR, String MONTH, String REPORT_TYPE) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "report_default";
        mMap.put("USERNAME", USERNAME);
        mMap.put("YEAR", YEAR);
        mMap.put("MONTH", MONTH);
        mMap.put("REPORT_TYPE", REPORT_TYPE);
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
                    ResponGetReportDefault obj = new Gson().fromJson(objT, ResponGetReportDefault.class);
                    mView.show_report_default(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_report_item(String USERNAME, String START_TIME, String END_TIME, String REPORT_TYPE,
                                String CODE_PRODUCT, String PAGE, String NUMOFPAGE) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "report_item";
        mMap.put("USERNAME", USERNAME);
        mMap.put("START_TIME", START_TIME);
        mMap.put("END_TIME", END_TIME);
        mMap.put("REPORT_TYPE", REPORT_TYPE);
        mMap.put("CODE_PRODUCT", CODE_PRODUCT);
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
                    ResponGetReportProduct obj = new Gson().fromJson(objT, ResponGetReportProduct.class);
                    mView.show_report_item(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_sub_product(String USERNAME, String ID_PARENT) {

    }

    @Override
    public void api_get_sub_product_child(String USERNAME, String SUB_ID) {

    }

    @Override
    public void api_get_report_ctv(String USERNAME, String YEAR, String MONTH, String REPORT_TYPE) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "report_ctv";
        mMap.put("USERNAME", USERNAME);
        mMap.put("YEAR", YEAR);
        mMap.put("MONTH", MONTH);
        mMap.put("REPORT_TYPE", REPORT_TYPE);
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
                    ResponGetReportListCTV obj = new Gson().fromJson(objT, ResponGetReportListCTV.class);
                    mView.show_get_report_ctv(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_report_ctv_detail(String USERNAME, String USER_CTV, String YEAR,
                                          String MONTH, String PAGE, String NUMOFPAGE) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "report_ctv_detail";
        mMap.put("USERNAME", USERNAME);
        mMap.put("USER_CTV", USER_CTV);
        mMap.put("YEAR", YEAR);
        mMap.put("MONTH", MONTH);
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
                    ResponGetReportListCTV obj = new Gson().fromJson(objT, ResponGetReportListCTV.class);
                    mView.show_get_report_ctv_detail(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_report_fluctuations(String USERNAME, String YEAR, String MONTH, String PR_CODE,
                                            String REPORT_TYPE, String DISPLAY_TYPE) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "report_fluctuations";
        mMap.put("USERNAME", USERNAME);
        mMap.put("YEAR", YEAR);
        mMap.put("MONTH", MONTH);
        mMap.put("PR_CODE", PR_CODE);
        mMap.put("REPORT_TYPE", REPORT_TYPE);
        mMap.put("DISPLAY_TYPE", DISPLAY_TYPE);
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
                    ResponGetReportListCTV obj = new Gson().fromJson(objT, ResponGetReportListCTV.class);
                    mView.show_get_report_fluctuations(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);

    }
}
