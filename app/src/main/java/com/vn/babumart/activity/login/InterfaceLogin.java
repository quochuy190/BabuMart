package com.vn.babumart.activity.login;

import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.models.ObjShopInfo;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 02-May-2019
 * Time: 10:37
 * Version: 1.0
 */
public interface InterfaceLogin {
    interface Presenter {
        void api_login(String sUserName, String sPassWord, String sUUID);

        void api_register(String FULL_NAME, String MOBILE, String EMAIL, String ID_CITY,
                          String ID_DISTRICT, String ADDRESS, String PASSWORD, String INVITE_CODE, String ID_WARD);

        void api_update_device(String USERNAME, String APP_VERSION, String MODEL_NAME, String TOKEN_KEY,
                               String DEVICE_TYPE, String OS_VERSION, String UUID);

        void api_getshopinfo(String IDSHOP);

        void api_check_device(String APP_VERSION, String MODEL_NAME, String TOKEN_KEY, String DEVICE_TYPE,
                              String OS_VERSION, String UUID);
    }

    interface View {
        void show_error_api();

        void show_login(ObjLogin obj);

        void show_register(ErrorApi obj);

        void show_update_device(ErrorApi obj);

        void show_get_shopinfo(ObjShopInfo obj);

        void show_check_device(ErrorApi obj);
    }
}
