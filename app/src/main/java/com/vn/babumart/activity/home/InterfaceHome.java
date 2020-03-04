package com.vn.babumart.activity.home;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 16-August-2019
 * Time: 16:51
 * Version: 1.0
 */
public interface InterfaceHome {
    interface Presenter{
        void api_get_config(String sUserName, String sShopId);
    }
    interface View{
        void show_get_config();
    }
}
