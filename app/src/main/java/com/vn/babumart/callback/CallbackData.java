package com.vn.babumart.callback;

/**
 * Created by QQ on 7/4/2017.
 */

public interface CallbackData<T> {
    void onGetDataSuccess(T objT);
    void onGetDataErrorFault(Exception e);
}
