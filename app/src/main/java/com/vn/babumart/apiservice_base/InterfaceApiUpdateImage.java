package com.vn.babumart.apiservice_base;


import com.vn.babumart.config.Config;
import java.util.concurrent.TimeUnit;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by QQ on 7/4/2017.
 */

public interface InterfaceApiUpdateImage<T> {
    // @FormUrlEncoded
    @Multipart
    @POST("/f/upload_avatar.jsp")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file);

  /*  OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();

            Request.Builder builder = originalRequest.newBuilder()
                    .header("Authorization", Config.KEY_AUTHORIZATION);
            Request newRequest = builder
                    .build();
            return chain.proceed(newRequest);
        }
    }).build();*/
    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();
    Retrofit retrofit_upload_image = new Retrofit.Builder()
            .baseUrl(Config.BASE_URL_UPLOAD_MEDIA)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();

}

