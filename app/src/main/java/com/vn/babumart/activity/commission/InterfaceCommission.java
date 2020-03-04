package com.vn.babumart.activity.commission;


import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.respon_api.ResponGetCommission;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 04-June-2019
 * Time: 21:20
 * Version: 1.0
 */
public interface InterfaceCommission {
    interface Presenter {
        void api_get_commission(String USERNAME, String USER_CTV, String PAGE, String NUMOFPAGE);

        void api_get_withdrawal(String USERNAME, String PAGE, String NUMOFPAGE);

        void api_get_withdrawal_history(String USERNAME, String USER_CTV,
                                        String START_TIME, String END_TIME, String PAGE, String NUMOFPAGE);

        void api_get_request_withdrawal(String USERNAME, String AMOUNT);

        void api_update_comission(String USERNAME, String USER_CTV, String AMOUNT);

        void api_update_withdrawal(String USERNAME, String ID_REQUEST);
    }

    interface View {
        void show_error_api();

        void show_get_commission(ResponGetCommission obj);

        void show_get_withdrawal(ResponGetCommission obj);

        void show_get_withdrawal_history(ResponGetCommission obj);

        void show_get_request_withdrawal(ErrorApi obj);

        void show_update_comission(ErrorApi obj);

        void show_update_withdrawal(ErrorApi obj);
    }
}
