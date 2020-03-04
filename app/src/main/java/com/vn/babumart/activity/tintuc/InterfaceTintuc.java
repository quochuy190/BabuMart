package com.vn.babumart.activity.tintuc;


import com.vn.babumart.models.respon_api.ResponseInfomation;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 18-July-2019
 * Time: 09:55
 * Version: 1.0
 */
public interface InterfaceTintuc {
    interface Presenter{
        void api_get_infomation(String USERNAME, String TYPES, String CATEGORY);

    }
    interface View{
        void show_error_api();
        void show_api_infomation(ResponseInfomation objRes);
        void show_api_infomation_daotao(ResponseInfomation objRes);
    }

}
