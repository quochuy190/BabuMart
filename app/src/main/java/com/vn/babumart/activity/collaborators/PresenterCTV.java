package com.vn.babumart.activity.collaborators;

import android.util.Log;
import com.google.gson.Gson;
import com.vn.babumart.apiservice_base.ApiServicePost;
import com.vn.babumart.callback.CallbackData;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.ObjCTV;
import com.vn.babumart.models.respon_api.ResponGetLisCTV;
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
public class PresenterCTV implements InterfaceCollaborators.Presenter {
    private static final String TAG = "PresenterLogin";
    ApiServicePost mApiService;
    InterfaceCollaborators.View mView;

    public PresenterCTV(InterfaceCollaborators.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }


    @Override
    public void api_get_lis_ctv(String USERNAME, String SEARCH, String ID_CITY, String I_PAGE, String NUMOFPAGE) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_list_ctv";
        mMap.put("USERNAME", USERNAME);
        mMap.put("SEARCH", SEARCH);
        mMap.put("ID_CITY", ID_CITY);
        mMap.put("ID_CITY", ID_CITY);
        mMap.put("I_PAGE", I_PAGE);
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
                    ResponGetLisCTV obj = new Gson().fromJson(objT, ResponGetLisCTV.class);
                    mView.show_get_list_ctv(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_update_ctv(String USERNAME, String USER_CTV, String NAME, String DOB, String GENDER, String EMAIL,
                               String CITY_NAME, String DISTRICT_NAME, String ADDRESS, String STK, String TENTK,
                               String TENNH, String AVATA, String CMT, String IMG1, String IMG2, String INVITE_CODE, String WARD_NAME) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "edit_info_ctv1";
        mMap.put("USERNAME", USERNAME);
        mMap.put("USER_CTV", USER_CTV);
        mMap.put("NAME", NAME);
        mMap.put("DOB", DOB);
        mMap.put("GENDER", GENDER);
        mMap.put("EMAIL", EMAIL);
        mMap.put("CITY_NAME", CITY_NAME);
        mMap.put("DISTRICT_NAME", DISTRICT_NAME);
        mMap.put("ADDRESS", ADDRESS);
        mMap.put("STK", STK);
        mMap.put("TENTK", TENTK);
        mMap.put("TENNH", TENNH);
        mMap.put("AVATAR", AVATA);
        mMap.put("CMT", CMT);
        mMap.put("IMG1", IMG1);
        mMap.put("IMG2", IMG2);
        mMap.put("INVITE_CODE", INVITE_CODE);
        mMap.put("WARD_NAME", WARD_NAME);
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
                    mView.show_update_ctv(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_reset_pass_ctv(String USERNAME, String USER_CTV) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "reset_pass_ctv";
        mMap.put("USERNAME", USERNAME);
        mMap.put("USER_CTV", USER_CTV);

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
                    mView.show_reset_pass_ctv(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_ctv_detail(String USERNAME, String USER_CTV, String IDSHOP) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_info_ctv_detail";
        mMap.put("USERNAME", USERNAME);
        mMap.put("USER_CTV", USER_CTV);

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
                    ObjCTV obj = new Gson().fromJson(objT, ObjCTV.class);
                    SharedPrefs.getInstance().put(Constants.KEY_SAVE_OBJ_CTV_APPLICATION, obj);
                    mView.show_ctv_detail(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_list_ctv_child(String USERNAME, String SEARCH, String ID_CITY,
                                       String I_PAGE, String NUMOFPAGE) {
        Map<String, String> mMap = new LinkedHashMap<>();
            String sService = "get_list_ctv_child";
        mMap.put("USERNAME", USERNAME);
        mMap.put("SEARCH", SEARCH);
        mMap.put("ID_CITY", ID_CITY);
        mMap.put("I_PAGE", I_PAGE);
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
                    ResponGetLisCTV obj = new Gson().fromJson(objT, ResponGetLisCTV.class);
                    mView.show_get_list_ctv(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }
}
