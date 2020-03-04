package com.vn.babumart.activity.charts;


import com.vn.babumart.models.respon_api.ResponGetReportDefault;
import com.vn.babumart.models.respon_api.ResponGetReportListCTV;
import com.vn.babumart.models.respon_api.ResponGetReportProduct;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 07-June-2019
 * Time: 11:11
 * Version: 1.0
 */
public interface InterfaceReport {
    interface Presenter {
        void api_report_default(String USERNAME, String YEAR, String MONTH, String REPORT_TYPE);

        void api_report_item(String USERNAME, String START_TIME, String END_TIME,
                             String REPORT_TYPE, String CODE_PRODUCT, String PAGE, String NUMOFPAGE);

        void api_get_sub_product(String USERNAME, String ID_PARENT);

        void api_get_sub_product_child(String USERNAME, String SUB_ID);

        void api_get_report_ctv(String USERNAME, String YEAR, String MONTH, String REPORT_TYPE);

        void api_get_report_ctv_detail(String USERNAME, String USER_CTV, String YEAR,
                                       String MONTH, String PAGE, String NUMOFPAGE);

        void api_get_report_fluctuations(String USERNAME, String YEAR, String MONTH,
                                         String PR_CODE, String REPORT_TYPE, String DISPLAY_TYPE);
    }

    interface View {
        void show_error_api();

        void show_report_item(ResponGetReportProduct objRespon);

        void show_report_default(ResponGetReportDefault obj);

        void show_get_sub_product(ResponGetReportDefault obj);

        void show_get_sub_product_child(ResponGetReportDefault obj);

        void show_get_report_ctv(ResponGetReportListCTV obj);

        void show_get_report_ctv_detail(ResponGetReportListCTV obj);

        void show_get_report_fluctuations(ResponGetReportListCTV obj);

    }
}
