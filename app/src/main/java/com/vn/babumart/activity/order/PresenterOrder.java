package com.vn.babumart.activity.order;

import android.util.Log;

import com.google.gson.Gson;
import com.vn.babumart.apiservice_base.ApiServicePost;
import com.vn.babumart.callback.CallbackData;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.ObjOrder;
import com.vn.babumart.models.respon_api.ResponGetProduct;
import com.vn.babumart.models.respon_api.ResponHistoryOrder;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 02-May-2019
 * Time: 10:44
 * Version: 1.0
 */
public class PresenterOrder implements InterfaceOrder.Presenter {
    private static final String TAG = "PresenterProduct";
    ApiServicePost mApiService;
    InterfaceOrder.View mView;

    public PresenterOrder(InterfaceOrder.View mView) {
        this.mView = mView;
        mApiService = new ApiServicePost();
    }

    @Override
    public void api_get_order_history(String USERNAME, String START_TIME, String END_TIME, String USER_CTV,
                                      String STATUS, String PAGE, String NUMOFPAGE) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_order_history";
        mMap.put("USERNAME", USERNAME);
        mMap.put("START_TIME", START_TIME);
        mMap.put("END_TIME", END_TIME);
        mMap.put("USER_CTV", USER_CTV);
        mMap.put("STATUS", STATUS);
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
                    ResponHistoryOrder obj = new Gson().fromJson(objT, ResponHistoryOrder.class);
                    mView.show_order_history(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_order_history_detail(String USERNAME, String CODE_ORDER) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_order_history_detail";
        mMap.put("USERNAME", USERNAME);
        mMap.put("CODE_ORDER", CODE_ORDER);

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
                    ObjOrder obj = new Gson().fromJson(objT, ObjOrder.class);
                    mView.show_order_history_detail(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_order_history_detail_pd(String USERNAME, String CODE_ORDER) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_order_history_detail_pd";
        mMap.put("USERNAME", USERNAME);
        mMap.put("CODE_ORDER", CODE_ORDER);

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
                    mView.show_order_history_detail_pd(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_edit_order_product(String USERNAME, String CODE_PRODUCT, String AMOUNT, String PRICE,
                                       String MONEY, String BONUS, String FULL_NAME, String MOBILE_RECEIVER,
                                       String ID_CITY, String ID_DISTRICT, String ADDRESS, String CODE_ORDER,
                                       String STATUS, String EXTRA_SHIP, String TIME_RECEIVER, String NOTE,
                                       String ID_PRODUCT_PROPERTIES, String DISTCOUNT) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "edit_order_product";
        mMap.put("USERNAME", USERNAME);
        mMap.put("CODE_PRODUCT", CODE_PRODUCT);
        mMap.put("AMOUNT", AMOUNT);
        mMap.put("PRICE", PRICE);
        mMap.put("MONEY", MONEY);
        mMap.put("BONUS", BONUS);
        mMap.put("FULL_NAME", FULL_NAME);
        mMap.put("MOBILE_RECEIVER", MOBILE_RECEIVER);
        mMap.put("ID_CITY", ID_CITY);
        mMap.put("ID_DISTRICT", ID_DISTRICT);
        mMap.put("ADDRESS", ADDRESS);
        mMap.put("CODE_ORDER", CODE_ORDER);
        mMap.put("STATUS", STATUS);
        mMap.put("EXTRA_SHIP", EXTRA_SHIP);
        mMap.put("TIME_RECEIVER", TIME_RECEIVER);
        mMap.put("NOTE", NOTE);
        mMap.put("ID_PRODUCT_PROPERTIES", ID_PRODUCT_PROPERTIES);
        mMap.put("DISTCOUNT", DISTCOUNT);

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
                    mView.show_edit_order_product(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_order_product(String USERNAME, String CODE_PRODUCT, String AMOUNT, String PRICE, String MONEY,
                                  String BONUS, String FULL_NAME, String MOBILE_RECEIVER, String ID_CITY,
                                  String ID_DISTRICT, String ADDRESS) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "order_product";
        mMap.put("USERNAME", USERNAME);//
        mMap.put("CODE_PRODUCT", CODE_PRODUCT);//VAKCBO#USYXBD
        mMap.put("AMOUNT", AMOUNT);// 1#2
        mMap.put("PRICE", PRICE);// giá sản phẩm VD: 95000#150000
        mMap.put("MONEY", MONEY);//đơn giá sản phẩm VD:95000#300000
        mMap.put("BONUS", BONUS);//19000#60000
        mMap.put("FULL_NAME", FULL_NAME);
        mMap.put("MOBILE_RECEIVER", MOBILE_RECEIVER);
        mMap.put("ID_CITY", ID_CITY);
        mMap.put("ID_DISTRICT", ID_DISTRICT);
        mMap.put("ADDRESS", ADDRESS);

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
                    mView.show_order_product(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_order_product_2(String USERNAME, String CODE_PRODUCT, String AMOUNT, String PRICE, String MONEY,
                                    String BONUS, String ID_PRODUCT_PROPERTIES, String FULL_NAME, String MOBILE_RECEIVER,
                                    String ID_CITY, String ID_DISTRICT, String ADDRESS) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "order_product2";
        mMap.put("USERNAME", USERNAME);//
        mMap.put("CODE_PRODUCT", CODE_PRODUCT);//VAKCBO#USYXBD
        mMap.put("AMOUNT", AMOUNT);// 1#2
        mMap.put("PRICE", PRICE);// giá sản phẩm VD: 95000#150000
        mMap.put("MONEY", MONEY);//đơn giá sản phẩm VD:95000#300000
        mMap.put("BONUS", BONUS);//19000#60000
        mMap.put("ID_PRODUCT_PROPERTIES", ID_PRODUCT_PROPERTIES);//19000#60000
        mMap.put("FULL_NAME", FULL_NAME);
        mMap.put("MOBILE_RECEIVER", MOBILE_RECEIVER);
        mMap.put("ID_CITY", ID_CITY);
        mMap.put("ID_DISTRICT", ID_DISTRICT);
        mMap.put("ADDRESS", ADDRESS);

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
                    mView.show_order_product(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_order_product_3(String USERNAME, String CODE_PRODUCT, String AMOUNT, String PRICE, String MONEY,
                                    String BONUS, String DISTCOUNT, String NOTE, String ID_PRODUCT_PROPERTIES, String FULL_NAME,
                                    String MOBILE_RECEIVER, String ID_CITY, String ID_DISTRICT, String ADDRESS, String FEESHIP, String ID_WARD) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "order_product3";
        mMap.put("USERNAME", USERNAME);//
        mMap.put("CODE_PRODUCT", CODE_PRODUCT);//VAKCBO#USYXBD
        mMap.put("AMOUNT", AMOUNT);// 1#2
        mMap.put("PRICE", PRICE);// giá sản phẩm VD: 95000#150000
        mMap.put("MONEY", MONEY);//đơn giá sản phẩm VD:95000#300000
        mMap.put("BONUS", BONUS);//19000#60000
        mMap.put("DISTCOUNT", DISTCOUNT);
        mMap.put("NOTE", NOTE);
        mMap.put("ID_PRODUCT_PROPERTIES", ID_PRODUCT_PROPERTIES);//19000#60000
        mMap.put("FULL_NAME", FULL_NAME);
        mMap.put("MOBILE_RECEIVER", MOBILE_RECEIVER);
        mMap.put("ID_CITY", ID_CITY);
        mMap.put("ID_DISTRICT", ID_DISTRICT);
        mMap.put("ADDRESS", ADDRESS);
        mMap.put("FEESHIP", FEESHIP);

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
                    mView.show_order_product(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }

    @Override
    public void api_get_config_commission(String USERNAME, String VALUES) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_config_commission";
        mMap.put("USERNAME", USERNAME);
        mMap.put("VALUES", VALUES);


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
                    mView.show_config_commission(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api();
                }
            }
        }, sService, mMap);
    }
}
