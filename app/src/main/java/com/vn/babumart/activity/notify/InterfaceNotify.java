package com.vn.babumart.activity.notify;

import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.respon_api.ResponGetnotify;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-May-2019
 * Time: 10:09
 * Version: 1.0
 */
public interface InterfaceNotify {
    interface Presenter {
        void api_get_list_notify(String USERNAME, String PAGE, String NUMOFPAGE);
        void api_update_notify(String USERNAME, String ID_NOTIFY);
    }

    interface View {
        void show_error_api();

        void show_update_notify(ErrorApi objError);

        void show_get_notify(ResponGetnotify obj);

    }
}
