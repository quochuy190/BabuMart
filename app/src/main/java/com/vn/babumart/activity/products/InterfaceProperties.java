package com.vn.babumart.activity.products;


import com.vn.babumart.models.respon_api.ResponGetPropeti;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-May-2019
 * Time: 10:09
 * Version: 1.0
 */
public interface InterfaceProperties {
    interface Presenter {
        void api_get_properties(String USERNAME, String LIST_PROPERTIES);

    }

    interface View {
        void show_error_api();

        void show_get_properties(ResponGetPropeti obj);

    }
}
