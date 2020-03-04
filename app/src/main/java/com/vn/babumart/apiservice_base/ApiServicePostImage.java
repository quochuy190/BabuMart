package com.vn.babumart.apiservice_base;

import com.vn.babumart.callback.CallbackData;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @description
 * @authour: $User
 * @createdate $Date
 */
public class ApiServicePostImage {
    ApiSeviceUploadImage apiService;
    InterfaceApiUpdateImage apiUploadImage;
    public void api_uploadImage(final CallbackData<String> callbackData, String part) {
        apiService = ApiSeviceUploadImage.retrofit.create(ApiSeviceUploadImage.class);
        File file = new File(part);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);
        Call<ResponseBody> getApiservice = apiService.uploadImage( body);
        getApiservice.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String jsonString = null;
                JSONObject jobj;
                try {
                    if (response.body()!=null){
                        jsonString = response.body().string();
                        callbackData.onGetDataSuccess(jsonString);
                    }
                } catch (IOException e) {
                    callbackData.onGetDataErrorFault(e);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackData.onGetDataErrorFault(new Exception(t));
            }
        });
    }
    public void apiUploadImage(final CallbackData<String> callbackData, String sUrl) {
        apiUploadImage = InterfaceApiUpdateImage.retrofit_upload_image.create(InterfaceApiUpdateImage.class);
        File file = new File(sUrl);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        Call<ResponseBody> getApiservice = apiUploadImage.uploadImage(body);
        getApiservice.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String jsonString = null;
                JSONObject jobj;
                try {
                    if (response.body() != null) {
                        jsonString = response.body().string();
                        callbackData.onGetDataSuccess(jsonString);
                    }
                } catch (IOException e) {
                    callbackData.onGetDataErrorFault(e);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackData.onGetDataErrorFault(new Exception(t));
            }
        });
    }

}
