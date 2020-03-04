package com.vn.babumart.activity.products;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.babumart.activity.products.InterfaceProduct;
import com.vn.babumart.apiservice_base.ApiServicePost;
import com.vn.babumart.callback.CallbackData;
import com.vn.babumart.models.CategoryProductHome;
import com.vn.babumart.models.Products;
import com.vn.babumart.models.respon_api.ResponGetCat;
import com.vn.babumart.models.respon_api.ResponGetProduct;
import com.vn.babumart.models.respon_api.ResponSubProduct;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 02-May-2019
 * Time: 10:44
 * Version: 1.0
 */
public class PresenterProduct implements InterfaceProduct.Presenter {
    private static final String TAG = "PresenterProduct";
    ApiServicePost mApiService;
    InterfaceProduct.View mView;

    public PresenterProduct(InterfaceProduct.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }

    @Override
    public void api_get_product_cat(String USERNAME, String ID_PARENT) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_product_cat";
        mMap.put("USERNAME", USERNAME);
        mMap.put("ID_PARENT", ID_PARENT);
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
                    ResponGetCat obj = new Gson().fromJson(objT, ResponGetCat.class);
                    mView.show_product_cat(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_get_sub_product(String USERNAME, String ID_PARENT, String SEARCH_NAME) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_sub_product";
        mMap.put("USERNAME", USERNAME);
        mMap.put("ID_PARENT", ID_PARENT);
        mMap.put("SEARCH_NAME", SEARCH_NAME);
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
                    ResponSubProduct obj = new Gson().fromJson(objT, ResponSubProduct.class);
                    mView.show_product_sub_product(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_get_sub_product_child(String USERNAME, String SUB_ID) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_sub_product_child";
        mMap.put("USERNAME", USERNAME);
        mMap.put("SUB_ID", SUB_ID);
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
                    ResponSubProduct obj = new Gson().fromJson(objT, ResponSubProduct.class);
                    mView.show_product_sub_product_child(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_product_cat_detail(String USERNAME, String SUB_ID_PARENT, String SUB_ID,
                                           String PAGE, String NUMOFPAGE) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_product_cat_detail";
        mMap.put("USERNAME", USERNAME);
        mMap.put("SUB_ID_PARENT", SUB_ID_PARENT);
        mMap.put("SUB_ID", SUB_ID);
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
                    ResponGetProduct obj = new Gson().fromJson(objT, ResponGetProduct.class);
                    mView.show_product_cat_detail(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_get_product_trend(String USERNAME) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_product_trend";
        mMap.put("USERNAME", USERNAME);

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
                    CategoryProductHome obj = new Gson().fromJson(objT, CategoryProductHome.class);
                    mView.show_product_trend(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_product_by_listid(String USERNAME, String LISTID) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_product_by_listid";
        mMap.put("USERNAME", USERNAME);
        mMap.put("LISTID", LISTID);

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
                    ResponGetProduct obj = new Gson().fromJson(objT, ResponGetProduct.class);
                    mView.show_product_by_listid(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_product_detail(String USERNAME, String IDSHOP, String IDPRODUCT) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_product_detail";
        mMap.put("USERNAME", USERNAME);
        mMap.put("IDPRODUCT", IDPRODUCT);

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
                    CategoryProductHome obj = new Gson().fromJson(objT, CategoryProductHome.class);
                    if (obj.getsERROR().equals("0000")) {
                        Products objProduct = obj.getmList().get(0);
                        mView.show_product_detail(objProduct);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }
}
