package com.vn.babumart.activity.collaborators;


import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.ObjCTV;
import com.vn.babumart.models.respon_api.ResponGetLisCTV;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 18-May-2019
 * Time: 21:29
 * Version: 1.0
 */
public interface InterfaceCollaborators {
    interface Presenter {
        void api_get_lis_ctv(String USERNAME, String SEARCH, String ID_CITY, String I_PAGE, String NUMOFPAGE);

        void api_update_ctv(String USERNAME, String USER_CTV, String NAME, String DOB, String GENDER, String EMAIL,
                            String CITY_NAME, String DISTRICT_NAME, String ADDRESS, String STK, String TENTK,
                            String TENNH, String AVATA, String CMT, String IMG1, String IMG2, String INVITE_CODE, String WARD_NAME);

        void api_reset_pass_ctv(String USERNAME, String USER_CTV);

        void api_get_ctv_detail(String USERNAME, String USER_CTV, String IDSHOP);

        void api_get_list_ctv_child(String USERNAME, String SEARCH, String ID_CITY, String I_PAGE, String NUMOFPAGE);
    }

    interface View {
        void show_error_api();

        void show_get_list_ctv(ResponGetLisCTV obj);

        void show_update_ctv(ErrorApi obj);

        void show_reset_pass_ctv(ErrorApi obj);

        void show_ctv_detail(ObjCTV objLogin);

        void show_list_ctv_child(ResponGetLisCTV objLogin);
    }
}
