package com.vn.babumart.activity.login.Menu_Search;


import com.vn.babumart.models.respon_api.ResponGetDistrict;
import com.vn.babumart.models.respon_api.ResponGetProvince;
import com.vn.babumart.models.respon_api.ResponGetWard;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 02-May-2019
 * Time: 11:41
 * Version: 1.0
 */
public interface InterfaceSearchMenu {
    interface Presenter {
        void api_get_citys();

        void api_get_district(String sCity_Id);

        void api_get_ward(String ID_DISTRICT);
    }

    interface View {
        void show_error_api();

        void show_api_get_city(ResponGetProvince objRespon);

        void show_api_get_district(ResponGetDistrict obj);

        void show_api_get_ward(ResponGetWard obj);
    }
}
