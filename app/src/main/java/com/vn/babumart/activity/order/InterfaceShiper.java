package com.vn.babumart.activity.order;

import com.vn.babumart.models.respon_api.GetFeeShipGHNRespon;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 10-January-2020
 * Time: 10:38
 * Version: 1.0
 */
public interface InterfaceShiper {
    interface Presenter{
        void get_ship_payment(String USERNAME, String LISTID, String TODISTRICTID, String IDSHOP);
    }
    interface View{
        void show_error_api();

        void show_ship_payment(GetFeeShipGHNRespon obj);
    }
}
