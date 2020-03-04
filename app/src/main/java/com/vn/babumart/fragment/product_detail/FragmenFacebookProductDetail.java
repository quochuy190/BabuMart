/*
package com.vn.vnpthn_bhkv2.fragment.product_detail;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareMedia;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.widget.ShareDialog;
import com.vn.gd_shop.BuildConfig;
import com.vn.gd_shop.R;
import com.vn.vnpthn_bhkv2.App;
import com.vn.vnpthn_bhkv2.activity.products.ActivityProductDetail;
import com.vn.vnpthn_bhkv2.adapter.AdapterImageUpFace;
import com.vn.vnpthn_bhkv2.base.BaseFragment;
import com.vn.vnpthn_bhkv2.callback.ItemClickListener;
import com.vn.vnpthn_bhkv2.config.Constants;
import com.vn.vnpthn_bhkv2.models.ObjLogin;
import com.vn.vnpthn_bhkv2.models.Products;
import com.vn.vnpthn_bhkv2.untils.SharedPrefs;
import com.vn.vnpthn_bhkv2.untils.StringUtil;
import com.vn.vnpthn_bhkv2.untils.TimeUtils;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


*/
/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 22-April-2019
 * Time: 10:30
 * Version: 1.0
 *//*

public class FragmenFacebookProductDetail extends BaseFragment {
    private static final String TAG = "FragmenFacebookProductD";
    public static FragmenFacebookProductDetail fragment;
    @BindView(R.id.ll_share_fb)
    ConstraintLayout btn_share;
    @BindView(R.id.txt_des_up_face)
    TextView txt_des_up_face;
    @BindView(R.id.txt_copy_text)
    TextView txt_copy_text;
    @BindView(R.id.ll_coppy)
    ConstraintLayout btn_download;
    @BindView(R.id.rcv_image_face)
    RecyclerView rcv_image_face;
    private Products mProduct;
    private List<String> mList;
    AdapterImageUpFace adapterImageUpFace;
    RecyclerView.LayoutManager layoutManager;

    public static FragmenFacebookProductDetail getInstance() {
        if (fragment == null) {
            synchronized (FragmenFacebookProductDetail.class) {
                if (fragment == null)
                    fragment = new FragmenFacebookProductDetail();
            }
        }
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facebook_product_detail, container, false);
        ButterKnife.bind(this, view);
        btn_download.setEnabled(true);
        mLisImageFacebok = new ArrayList<>();
        initDataFacebook();
        initListImage();
        get_hash();
        FacebookSdk.sdkInitialize(getContext());
        initFacebook();
        initEvent();
        test_rxandroid();
        return view;
    }

    private void initListImage() {
        adapterImageUpFace = new AdapterImageUpFace(mListAnh, getContext());
        layoutManager = new GridLayoutManager(getContext(), 4);
        rcv_image_face.setNestedScrollingEnabled(false);
        rcv_image_face.setHasFixedSize(true);
        rcv_image_face.setLayoutManager(layoutManager);
        rcv_image_face.setItemAnimator(new DefaultItemAnimator());
        rcv_image_face.setAdapter(adapterImageUpFace);
        adapterImageUpFace.notifyDataSetChanged();
        adapterImageUpFace.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {

            }
        });
    }



  */
/*  private void init() {
        mList = new ArrayList<>();
        adapterService = new AdapterImageUpFace(mList, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL,
                false);
        recycle_product.setNestedScrollingEnabled(false);
        recycle_product.setHasFixedSize(true);
        recycle_product.setLayoutManager(mLayoutManager);
        recycle_product.setItemAnimator(new DefaultItemAnimator());
        recycle_product.setAdapter(adapterService);
      *//*
*/
/*  adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                setResult(RESULT_OK, new Intent());
                App.mCity = (City) item;
            }
        });*//*
*/
/*
        adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {

            }
        });

    }*//*


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    ObjLogin objLogin;
    int iCountMax = 0;

    private void initDataFacebook() {
        objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        mList = new ArrayList<>();
        mProduct = App.mProduct;
        if (mProduct != null) {
            if (mProduct.getsUrlImage() != null) {
                mList.add(mProduct.getsUrlImage());
            }
            if (mProduct.getIMG1() != null) {
                mList.add(mProduct.getIMG1());
            }
            if (mProduct.getIMG2() != null) {
                mList.add(mProduct.getIMG2());
            }
            if (mProduct.getIMG3() != null) {
                mList.add(mProduct.getIMG3());
            }
            if (mProduct.getMEDIA_FB() != null && mProduct.getMEDIA_FB().length() > 0) {
                String[] arrayImage = mProduct.getMEDIA_FB().split("\\|\\|");
                if (arrayImage.length > 0) {
                    showDialogLoading();
                    for (String sLink : arrayImage) {
                        if (sLink != null && sLink.length() > 0) {
                            iCountMax++;
                            load_image(sLink);
                        }

                    }
                }
            }

            if (mProduct.getCONTENT_FB() != null) {
                String content = "";
                if (mProduct.getPRICE_PROMOTION() != null && mProduct.getSTART_PROMOTION()
                        != null && mProduct.getEND_PROMOTION() != null) {
                    if (TimeUtils.compare_two_date_currenttime(mProduct.getSTART_PROMOTION(),
                            mProduct.getEND_PROMOTION())) {
                        content = mProduct.getsName().toUpperCase()
                                + "<br>" + mProduct.getCONTENT_FB()
                                + "Giá bán niêm yết: " + StringUtil.conventMonney_Long(mProduct.getsPrice())
                                + "<br>" + "Giá khuyến mại: " + StringUtil.conventMonney_Long(mProduct.getPRICE_PROMOTION())
                                + "<br>" + "Tư vấn bán hàng: " + objLogin.getUSERNAME();
                    } else {
                        content = mProduct.getsName().toUpperCase()
                                + "<br>" + mProduct.getCONTENT_FB()
                                + "Giá bán: " + StringUtil.conventMonney_Long(mProduct.getsPrice())
                                + "<br>" + "Tư vấn bán hàng: " + objLogin.getUSERNAME();
                    }

                } else {
                    content = mProduct.getsName().toUpperCase()
                            + "<br>" + mProduct.getCONTENT_FB()
                            + "Giá bán: " + StringUtil.conventMonney_Long(mProduct.getsPrice())
                            + "<br>" + "Tư vấn bán hàng: " + objLogin.getUSERNAME();
                }
                if (mProduct.getWARRANTY() != null && Integer.parseInt(mProduct.getWARRANTY()) > 0) {
                    content = content +
                            "<br>" + "Bảo hành: " + mProduct.getWARRANTY() + " tháng";
                }
                //  content = content + "<br>";
                txt_des_up_face.setText(Html.fromHtml(content));
            } else {
                txt_des_up_face.setText("Mô tả: ...");
            }
            if (mList.size() == 0)
                mList.add("abc");
        }
        show_image_fb();

    }

    List<String> mListAnh = new ArrayList<>();

    public void show_image_fb() {
        mListAnh.clear();
        if (mProduct.getMEDIA_FB() != null && mProduct.getMEDIA_FB().length() > 0) {
            String[] arrayImage = mProduct.getMEDIA_FB().split("\\|\\|");
            if (arrayImage.length > 0) {
                for (String sLink : arrayImage) {
                    if (sLink != null && sLink.length() > 0)
                        mListAnh.add(sLink);
                }
            }
        }
        if (mProduct.getVIDEO_FB() != null)
            mListAnh.add("https://p7.hiclipart.com/preview/761/398/486/logo-brand-circle-font" +
                    "-youtube-play-button-png.jpg");
    }

    private void get_hash() {
        try {
            PackageInfo info = getContext().getPackageManager().getPackageInfo(
                    "com.neo.motherland",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkDiskPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "No Permissions", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    101);
        } else {
            start_download_media();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    start_download_media();
                    Toast.makeText(getContext(), "Permission given",
                            Toast.LENGTH_SHORT).show();
                    //saveImage(finalBitmap, image_name); <- or whatever you want to do after permission was given . For instance, open gallery or something
                } else {
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    private void initEvent() {
        txt_copy_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sString = "";
                sString = txt_des_up_face.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getActivity()
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(getString(R.string.app_name), sString);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Copy to Clipboard.", Toast.LENGTH_SHORT).show();
            }
        });
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDiskPermission();
            }
        });
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sString = "";
                sString = txt_des_up_face.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getActivity()
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(getString(R.string.app_name), sString);
                clipboard.setPrimaryClip(clip);
                share_multil_image();
            }
        });
    }

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    private void initFacebook() {
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        //  MessageDialog.show(this, getContext());
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                // This doesn't work
                hideDialogLoading();
                Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                hideDialogLoading();
                Toast.makeText(getContext(), "You Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                // This doesn't work
                hideDialogLoading();
                e.printStackTrace();
            }
        });
    }

    public void share_multil_image() {
        ShareVideo shareVideo2 = null;
        if (ActivityProductDetail.mUri != null) {
            shareVideo2 = new ShareVideo.Builder()
                    .setLocalUrl(ActivityProductDetail.mUri)
                    .build();
        }
        ShareContent shareContent = null;
        List<ShareMedia> mLis = new ArrayList<>();
        int iMax = 6;
        if (shareVideo2 != null)
            iMax = 5;
        if (ActivityProductDetail.mLisIma.size() < iMax) {
            iMax = ActivityProductDetail.mLisIma.size();
        }

        for (int i = 0; i < iMax; i++) {
            Bitmap bitmap = ActivityProductDetail.mLisIma.get(i);
            mLis.add(new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .setCaption(getString(R.string.app_name))
                    .setUserGenerated(false)
                    .build());
        }
        if (mLis.size() > 0) {
            shareContent = new ShareMediaContent.Builder()
                    .addMedia(mLis)
                    .addMedium(shareVideo2)
                    .build();
            showDialogLoading();
            shareDialog.show(shareContent, ShareDialog.Mode.AUTOMATIC);
        } else if (mProduct.getMEDIA_FB() != null && mProduct.getMEDIA_FB().length() > 0) {
            if (!isLoading) {
                showDialogLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideDialogLoading();
                        isLoading = true;
                        share_multil_image();
                    }
                }, 4000);
            } else {
                showAlertDialog("Thông báo", "Bài viết chưa có ảnh hay video để chia sẻ Facebook.");
            }
        } else {
            showAlertDialog("Thông báo", "Bài viết chưa có ảnh hay video để chia sẻ Facebook.");
        }
    }

    boolean isLoading = false;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void start_download_media() {
        if (mProduct.getMEDIA_FB() != null && mProduct.getMEDIA_FB().length() > 0) {
            String[] arrayImage = mProduct.getMEDIA_FB().split("\\|\\|");
            if (arrayImage.length > 0) {
                for (String sLink : arrayImage) {
                    if (sLink != null && sLink.length() > 0)
                        //  new DownloadTask().execute(sLink);
                        download_all(sLink);
                }
            }
        }
        if (mProduct.getVIDEO_FB() != null && mProduct.getVIDEO_FB().length() > 0) {
            download_video(mProduct.getVIDEO_FB());
        }
    }

    public void download_all(String sUrl) {
        try {
            btn_download.setEnabled(false);
            Toast.makeText(getContext(), "Bắt đầu tải ảnh về máy", Toast.LENGTH_SHORT).show();
            String filename = "filename.jpg";
            String downloadUrlOfImage = sUrl;
            File direct =
                    new File(Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                            .getAbsolutePath() + BuildConfig.APPLICATION_ID + "/");


            if (!direct.exists()) {
                direct.mkdir();
                //     Log.d(LOG_TAG, "dir created for first time");
            }
            DownloadManager dm = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(downloadUrlOfImage);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(filename)
                    .setMimeType("image/jpeg")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                            File.separator + BuildConfig.APPLICATION_ID + File.separator + filename);

            dm.enqueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void download_video(String sUrl) {
        try {
            btn_download.setEnabled(false);
            Toast.makeText(getContext(), "Bắt đầu tải ảnh về máy", Toast.LENGTH_SHORT).show();
            String filename = "filename.mp4";
            String downloadUrlOfImage = sUrl;
            File direct =
                    new File(Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                            .getAbsolutePath() + "/" + BuildConfig.APPLICATION_ID + "/");


            if (!direct.exists()) {
                direct.mkdir();
                //     Log.d(LOG_TAG, "dir created for first time");
            }
            DownloadManager dm = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(downloadUrlOfImage);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(filename)
                    .setMimeType("video/mp4")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                            File.separator + BuildConfig.APPLICATION_ID + File.separator + filename);

            dm.enqueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Bitmap> mLisImageFacebok;
    int count = 0;

    private void load_image(String sUrl) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(sUrl)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("request failed: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        count++;
                        if (count >= iCountMax) {
                            hideDialogLoading_delay();
                        }
                        response.body().byteStream(); // Read the data from the stream
                        Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                        if (bmp != null)
                            mLisImageFacebok.add(bmp);
                        Log.i(TAG, "onResponse: " + bmp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Disposable disposable;

    public void test_rxandroid() {
        // observable
        Observable<String> footballPlayesObservable = getFootballPlayesObservable();

        // observer
        Observer<String> footballPlayesObserver = getFootballPlayesObserver();

        // observer subscribing to observable
        footballPlayesObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(footballPlayesObserver);

    }

    private Observer<String> getFootballPlayesObserver() {
        return new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
                disposable = d;
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
            }
        };
    }

    private Observable<String> getFootballPlayesObservable() {
        return Observable.just("Messi", "Ronaldo", "Modric", "Salah", "Mbappe");
    }

}
*/
