package com.vn.babumart.callback;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 27-June-2019
 * Time: 08:55
 * Version: 1.0
 */
public interface TaskCompleted {
    // Define data you like to return from AysncTask
    public void onTaskComplete(String result);
}
