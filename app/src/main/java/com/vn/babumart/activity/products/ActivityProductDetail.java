package com.vn.babumart.activity.products;

import android.Manifest;
import android.app.Dialog;
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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import com.google.android.material.tabs.TabLayout;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.vn.babumart.App;
import com.vn.babumart.BuildConfig;
import com.vn.babumart.R;
import com.vn.babumart.activity.login.ActivityConfirmOTP;
import com.vn.babumart.activity.products.ActivityCart;
import com.vn.babumart.activity.products.InterfaceProduct;
import com.vn.babumart.activity.products.InterfaceProperties;
import com.vn.babumart.activity.products.PresenterProduct;
import com.vn.babumart.activity.products.PresenterProperties;
import com.vn.babumart.activity.tintuc.ActivityDetailNews;
import com.vn.babumart.adapter.AdapterImaViewPager;
import com.vn.babumart.adapter.AdapterImageUpFace;
import com.vn.babumart.adapter.AdapterImage_Zoom_Viewpage;
import com.vn.babumart.adapter.AdapterViewpager;
import com.vn.babumart.adapter.ImageAdapter;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.callback.ClickDialog;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.callback.TaskCompleted;
import com.vn.babumart.config.Config;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.CategoryProductHome;
import com.vn.babumart.models.InfomationObj;
import com.vn.babumart.models.MessageEvent;
import com.vn.babumart.models.ObjCTV;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.models.Products;
import com.vn.babumart.models.PropetiObj;
import com.vn.babumart.models.respon_api.ObjLisCart;
import com.vn.babumart.models.respon_api.ResponGetCat;
import com.vn.babumart.models.respon_api.ResponGetProduct;
import com.vn.babumart.models.respon_api.ResponGetPropeti;
import com.vn.babumart.models.respon_api.ResponSubProduct;
import com.vn.babumart.untils.SharedPrefs;
import com.vn.babumart.untils.StringUtil;
import com.vn.babumart.untils.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.vn.babumart.config.Config.mShipPolicy;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 10-May-2019
 * Time: 17:00
 * Version: 1.0
 */
public class ActivityProductDetail extends BaseActivity
        implements InterfaceProperties.View, TaskCompleted, InterfaceProduct.View {
    private static final String TAG = "ActivityProductDetail";
    AdapterViewpager adapter;
    /* @BindView(R.id.ll_tab_layout_content)
     ConstraintLayout ll_tab_layout_content;*/
/*    @BindView(R.id.ll_tab_layout_face)
    ConstraintLayout ll_tab_layout_face;*/
    @BindView(R.id.txt_name_product_detail)
    TextView txt_name_product_detail;
    @BindView(R.id.tx_price_product_detail)
    TextView tx_price_product_detail;
    @BindView(R.id.tx_commission_detail)
    TextView tx_commission_detail;
    @BindView(R.id.txt_add_cart)
    TextView txt_add_cart;
    @BindView(R.id.scroll_product_detail)
    NestedScrollView scroll_product_detail;
    TabLayout tabLayout;
    @BindView(R.id.spiner_type_1)
    Spinner spiner_type_1;
    @BindView(R.id.spiner_type_2)
    Spinner spiner_type_2;
    @BindView(R.id.spiner_type_3)
    Spinner spiner_type_3;
    @BindView(R.id.spiner_type_4)
    Spinner spiner_type_4;
    @BindView(R.id.txt_title_spinner_2)
    TextView txt_title_spinner_2;
    @BindView(R.id.txt_title_spinner_3)
    TextView txt_title_spinner_3;
    @BindView(R.id.txt_title_spinner_4)
    TextView txt_title_spinner_4;
    @BindView(R.id.txt_title_spinner_1)
    TextView txt_title_spinner_1;
    @BindView(R.id.img_home)
    ImageView img_cart;
    @BindView(R.id.txt_badger_cart)
    TextView txt_badger_cart;
    @BindView(R.id.txt_title_vanchuyen)
    TextView txt_title_vanchuyen;
    /* @BindView(R.id.txt_affiliate_title)
     TextView txt_affiliate_title;
     @BindView(R.id.txt_facebook)
     TextView txt_facebook;*/
    @BindView(R.id.txt_precent_detail)
    TextView txt_precent_detail;
    @BindView(R.id.txt_title_waranty)
    TextView txt_title_waranty;
    @BindView(R.id.ic_waranty)
    ImageView ic_waranty;
    @BindView(R.id.tx_price_delete)
    TextView tx_price_delete;

    @BindView(R.id.view_delete_detail)
    View view_delete_detail;
    private Products mProduct;
    private List<String> mLisImage;
    private List<Products> mList;
    private ObjLisCart objLisCart;
    private PresenterProperties mPresenterProperties;
    private PresenterProduct mPresenterProduct;
    private String sThuoctinh1 = "", sThuoctinh2 = "", sThuoctinh3 = "", sThuoctinh4 = "";
    private String sProperties = "";
    @BindView(R.id.ll_share_fb)
    ConstraintLayout btn_share;
    @BindView(R.id.txt_des_up_face)
    TextView txt_des_up_face;
    @BindView(R.id.txt_copy_text)
    TextView txt_copy_text;
    @BindView(R.id.ll_dowload_image)
    ConstraintLayout btn_download;
    @BindView(R.id.ll_coppy_link)
    ConstraintLayout ll_coppy_link;
    @BindView(R.id.ll_coppy_text)
    ConstraintLayout ll_coppy_text;
    @BindView(R.id.ll_copy_catalog)
    ConstraintLayout ll_copy_catalog;
    @BindView(R.id.rcv_image_face)
    RecyclerView rcv_image_face;
    // private List<String> mList;
    AdapterImageUpFace adapterImageUpFace;
    RecyclerView.LayoutManager layoutManager;
    @BindView(R.id.ll_spinner)
    ConstraintLayout ll_spinner;
    @BindView(R.id.ll_spinner_2)
    ConstraintLayout ll_spinner_2;
    @BindView(R.id.ll_spinner_3)
    ConstraintLayout ll_spinner_3;
    @BindView(R.id.ll_spinner_4)
    ConstraintLayout ll_spinner_4;
    List<String> dataset, listPropeti_2, listPropeti_3, listPropeti_4;
    List<PropetiObj.PropetiDetail> mListThuoctinh1;
    List<PropetiObj.PropetiDetail> mListThuoctinh2;
    List<PropetiObj.PropetiDetail> mListThuoctinh3;
    List<PropetiObj.PropetiDetail> mListThuoctinh4;
    List<PropetiObj.PropetiDetail> mListPrppeti;
    PropetiObj.PropetiDetail mObjPro1, mObjPro2, mObjPro3, mObjPro4;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    @BindView(R.id.txt_guild_sell)
    TextView txt_guild_sell;
    @BindView(R.id.constraintLayout)
    ConstraintLayout ll_btn_all;
    @BindView(R.id.txt_guilid)
    TextView txt_guilid;
    @BindView(R.id.btn_register)
    Button btn_register;
    boolean isLogin;

    @Override
    public int setContentViewId() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        FacebookSdk.sdkInitialize(this);
        mPresenterProduct = new PresenterProduct(this);
        initAppbar();
        initData();
        initEvent();
        initDataFacebook();
        initListImage();
        get_hash();
        initFacebook();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(Constants.EventBus.KEY_LOGIN_GUEST)) {
            initData();
        }
    }

    private void init() {
        dataset = new ArrayList<>();
        mLisIma = new ArrayList<>();
        listPropeti_2 = new ArrayList<>();
        listPropeti_3 = new ArrayList<>();
        listPropeti_4 = new ArrayList<>();
        mListThuoctinh1 = new ArrayList<>();
        mListThuoctinh4 = new ArrayList<>();
        mListThuoctinh3 = new ArrayList<>();
        mListThuoctinh2 = new ArrayList<>();
        mListPrppeti = new ArrayList<>();
        mPresenterProperties = new PresenterProperties(this);
        ll_spinner_2.setVisibility(View.GONE);
        ll_spinner_3.setVisibility(View.GONE);
        ll_spinner_4.setVisibility(View.GONE);
        ll_spinner.setVisibility(View.GONE);
    }

    private void initFacebook() {
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        //  MessageDialog.show(this, getContext());
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                // This doesn't work
                hideDialogLoading();
                Toast.makeText(ActivityProductDetail.this, "Success!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                hideDialogLoading();
                Toast.makeText(ActivityProductDetail.this, "You Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                // This doesn't work
                hideDialogLoading();
                e.printStackTrace();
            }
        });
    }

    private void initListImage() {
        adapterImageUpFace = new AdapterImageUpFace(mListAnh, this);
        layoutManager = new GridLayoutManager(this, 3);
        rcv_image_face.setNestedScrollingEnabled(false);
        rcv_image_face.setHasFixedSize(true);
        rcv_image_face.setLayoutManager(layoutManager);
        rcv_image_face.setItemAnimator(new DefaultItemAnimator());
        rcv_image_face.setAdapter(adapterImageUpFace);
        adapterImageUpFace.notifyDataSetChanged();
        adapterImageUpFace.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                showDialog_Full_image(position);
            }
        });

    }

    ObjLogin objLogin;

    private void initDataFacebook() {
        objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        try {
            if (mProduct != null) {
                if (objLogin != null) {
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
                } else {
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
                                ;
                            } else {
                                content = mProduct.getsName().toUpperCase()
                                        + "<br>" + mProduct.getCONTENT_FB()
                                        + "Giá bán: " + StringUtil.conventMonney_Long(mProduct.getsPrice())
                                ;
                            }

                        } else {
                            content = mProduct.getsName().toUpperCase()
                                    + "<br>" + mProduct.getCONTENT_FB()
                                    + "Giá bán: " + StringUtil.conventMonney_Long(mProduct.getsPrice())
                            ;
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
                }

            }
            show_image_fb();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            PackageInfo info = getPackageManager().getPackageInfo(
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


    private void set_data_spinner() {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_spinner, dataset);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spiner_type_1.setAdapter(adapter);
        spiner_type_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sThuoctinh1 = dataset.get(position);
                mObjPro1 = mListThuoctinh1.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void set_badger() {
        ObjLisCart objCat = SharedPrefs.getInstance().get(Constants.KEY_SAVE_LIST_CART, ObjLisCart.class);
        if (objCat != null && objCat.getmList() != null && objCat.getmList().size() > 0) {
            txt_badger_cart.setVisibility(View.VISIBLE);
            txt_badger_cart.setText("" + objCat.getmList().size());
        } else {
            txt_badger_cart.setVisibility(View.GONE);
        }
    }

    private void set_data_spinner_2() {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_spinner, listPropeti_2);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spiner_type_2.setAdapter(adapter);
        spiner_type_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sThuoctinh2 = listPropeti_2.get(position);
                mObjPro2 = mListThuoctinh2.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void set_data_spinner_3() {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_spinner, listPropeti_3);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spiner_type_3.setAdapter(adapter);
        spiner_type_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sThuoctinh3 = listPropeti_3.get(position);
                mObjPro3 = mListThuoctinh3.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void set_data_spinner_4() {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_spinner, listPropeti_4);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spiner_type_4.setAdapter(adapter);
        spiner_type_4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sThuoctinh4 = listPropeti_4.get(position);
                mObjPro4 = mListThuoctinh4.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initViewpager() {
        tabLayout = (TabLayout) findViewById(R.id.id_tablayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImageAdapter adapter = new ImageAdapter(this, mLisImage);
        AdapterImaViewPager customPagerAdapter = new AdapterImaViewPager(this, mLisImage);
        viewPager.setAdapter(customPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    TextView txt_title;

    private void initAppbar() {
        ImageView img_back = findViewById(R.id.img_back);
        txt_title = findViewById(R.id.txt_title);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_title.setText("Chi tiết sản phẩm");
    }


    private void initData() {
        objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        isLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_IS_LOGIN, Boolean.class);
        if (isLogin) {
            btn_register.setVisibility(View.GONE);
            if (objLogin != null && objLogin.getGROUPS() != null) {
                if (objLogin.getGROUPS().equals("5") ||
                        objLogin.getGROUPS().equals(Constants.User_Group_Type.KHACH_HANG)) {
                    img_cart.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        txt_add_cart.setBackground(getResources().getDrawable(R.drawable.spr_txt_title_fragment_home));
                        txt_add_cart.setText("Thêm vào giỏ hàng");
                    }
                    txt_add_cart.setEnabled(true);
                    if (objLogin.getGROUPS().equals("8")) {
                        txt_guilid.setVisibility(View.GONE);
                        txt_guilid.setText(getString(R.string.txt_guild_guest_product_detail));
                        ll_btn_all.setVisibility(View.GONE);
                        tx_commission_detail.setVisibility(View.GONE);
                    } else {
                        txt_guilid.setTextColor(getResources().getColor(R.color.black));
                        txt_guilid.setText(getString(R.string.txt_guild_up_face));
                        txt_guilid.setVisibility(View.VISIBLE);
                        ll_btn_all.setVisibility(View.VISIBLE);
                        tx_commission_detail.setVisibility(View.VISIBLE);
                    }
                } else {
                    btn_add_selected("Không có quyền mua hàng");
                    img_cart.setVisibility(View.INVISIBLE);
                }
            } else {
                txt_guilid.setVisibility(View.GONE);
                ll_btn_all.setVisibility(View.GONE);
                tx_commission_detail.setVisibility(View.GONE);
            }
        } else {
            btn_register.setVisibility(View.VISIBLE);
            txt_guilid.setVisibility(View.VISIBLE);
            txt_guilid.setTextColor(getResources().getColor(R.color.blue_next_right));
            txt_guilid.setText(getString(R.string.txt_guild_guest_product_detail));
            ll_btn_all.setVisibility(View.GONE);
            tx_commission_detail.setVisibility(View.GONE);
        }

        mList = new ArrayList<>();
        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        // lấy danh sách sản phẩm trong giỏ hàng
        objLisCart = SharedPrefs.getInstance().get(Constants.KEY_SAVE_LIST_CART, ObjLisCart.class);
        if (objLisCart != null && objLisCart.getmList() != null && objLisCart.getmList().size() > 0) {
            mList.clear();
            mList.addAll(objLisCart.getmList());
        }
        mProduct = (Products) getIntent().getSerializableExtra(Constants.KEY_SEND_OBJ_PRODUCTS);
        if (mProduct != null) {
            showDialogLoading();
            mPresenterProduct.api_get_product_detail(sUserName, "", mProduct.getID());
            if (mProduct.getID_PRODUCT_PROPERTIES() != null && mProduct.getID_PRODUCT_PROPERTIES().length() > 0) {
                mPresenterProperties.api_get_properties(sUserName, mProduct.getID_PRODUCT_PROPERTIES());
            }
        }
        set_info_product();

    }

    private void set_info_product() {
        if (mProduct != null) {
            if (mProduct.getMEDIA_FB() != null && mProduct.getMEDIA_FB().length() > 0) {
                String[] arrayImage = mProduct.getMEDIA_FB().split("\\|\\|");
                if (arrayImage.length > 0) {
                    for (String sLink : arrayImage) {
                        if (sLink != null && sLink.length() > 0)
                            load_image(sLink);
                    }
                }
            }

            if (mProduct.getVIDEO_FB() != null) {
                startDownload(mProduct.getVIDEO_FB());
            }
            App.mProduct = mProduct;
            if (mProduct.getTRAINING() != null)
                if (isLogin) {
                    if (objLogin != null && objLogin.getGROUPS() != null) {
                        if (objLogin.getGROUPS().equals("8")) {
                            txt_guild_sell.setVisibility(View.GONE);
                        } else {
                            txt_guild_sell.setVisibility(View.VISIBLE);
                        }
                    }
                } else
                    txt_guild_sell.setVisibility(View.GONE);
            else
                txt_guild_sell.setVisibility(View.GONE);
           /* showDialogLoading();
            mPresenterProperties.api_get_properties(sUserName, mProduct.getID_PRODUCT_PROPERTIES());*/
            mLisImage = new ArrayList<>();
            boolean isClick = false;
            for (Products obj : mList) {
                if (obj.getCODE_PRODUCT().equals(mProduct.getCODE_PRODUCT())) {
                    isClick = true;
                }
            }
            if (isClick) {
                btn_add_selected("Đã thêm vào giỏ hàng");
            }
       /*     if (mProduct.getsUrlImage() != null) {
                mLisImage.add(mProduct.getsUrlImage());
            }*/
            if (mProduct.getIMG1() != null) {
                mLisImage.add(mProduct.getIMG1());
            }
            if (mProduct.getIMG2() != null) {
                mLisImage.add(mProduct.getIMG2());
            }
            if (mProduct.getIMG3() != null) {
                mLisImage.add(mProduct.getIMG3());
            }
            if (mProduct.getsName() != null && mProduct.getsName().length() > 0) {
                txt_name_product_detail.setText(mProduct.getsName());
            } else
                txt_name_product_detail.setText(".....");
            if (mProduct.getsPrice() != null && mProduct.getsPrice().length() > 0) {
                if (StringUtil.check_login_customer()) {
                    set_commission_max(Long.parseLong(mProduct.getsPrice()));
                }
                tx_price_product_detail.setText(StringUtil.conventMonney(mProduct.getsPrice()));
            } else
                tx_price_product_detail.setText("Đơn giá: ");
            if (mProduct.getCOMISSION_PRODUCT() != null) {
                if (StringUtil.check_login_customer()) {
                    set_hh_sp(mProduct.getCOMISSION_PRODUCT(), mProduct.getsPrice(),
                            tx_commission_detail);
                }
            } else {
                tx_commission_detail.setText("Hoa hồng sp: 0%");
            }
            if (mProduct.getWARRANTY() != null) {
                int waranty = Integer.parseInt(mProduct.getWARRANTY());
                if (waranty > 0) {
                    ic_waranty.setVisibility(View.VISIBLE);
                    txt_title_waranty.setVisibility(View.VISIBLE);
                    txt_title_waranty.setText("Bảo hành " + mProduct.getWARRANTY() + " tháng");
                } else {
                    ic_waranty.setVisibility(View.GONE);
                    txt_title_waranty.setVisibility(View.GONE);
                }
            } else {
                txt_title_waranty.setText("Bảo hành 0 tháng");
                ic_waranty.setVisibility(View.GONE);
                txt_title_waranty.setVisibility(View.GONE);
            }

            if (mProduct.getPRICE_PROMOTION() != null) {
                if (mProduct.getSTART_PROMOTION() != null && mProduct.getEND_PROMOTION() != null) {
                    if (TimeUtils.compare_two_date_currenttime(mProduct.getSTART_PROMOTION(), mProduct.getEND_PROMOTION())) {
                        show_promotion();
                        tx_price_delete.setText(StringUtil.conventMonney_Long(mProduct.getsPrice()));
                        try {
                            int price = Integer.parseInt(mProduct.getsPrice());
                            int price_promotion = Integer.parseInt(mProduct.getPRICE_PROMOTION());
                            float fprecent = (float) (price - price_promotion) / price;
                            int precent = (int) (fprecent * 100);
                            txt_precent_detail.setText("-" + precent + "%");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (mProduct.getPRICE_PROMOTION() != null) {
                            tx_price_product_detail.setText(StringUtil.conventMonney_Long(mProduct.getPRICE_PROMOTION()));
                            if (StringUtil.check_login_customer())
                                set_commission_max(Long.parseLong(mProduct.getPRICE_PROMOTION()));
                        }
                        if (mProduct.getCOMISSION_PRODUCT() != null) {
                            set_hh_sp(mProduct.getCOMISSION_PRODUCT(), mProduct.getPRICE_PROMOTION(),
                                    tx_commission_detail);
                        } else {
                            tx_commission_detail.setText("Hoa hồng sp: 0%");
                        }
                    } else {
                        hide_promotion();
                    }
                } else {
                    hide_promotion();
                }
            } else {
                hide_promotion();
            }
            if (mLisImage.size() == 0) {
                mLisImage.add("abc");
            }
            // loadFragmentFracebookProductDetil();
            initViewpager();
            set_badger();
        }
    }

    public void set_commission_max(long lPrice) {
        float commis_order = 0;
        float commis_ctv = 0;
        float commis_product = 0;
        int commis_max = 0;
        long lCommission = 0;
        ObjCTV objCTV = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJ_CTV_APPLICATION, ObjCTV.class);
        if (objCTV.getCOMISSION() != null && objCTV.getCOMISSION().length() > 0) {
            commis_ctv = Float.parseFloat(objCTV.getCOMISSION());
        }
        if (Config.mLisConfigCommis != null && Config.mLisConfigCommis.size() > 0) {
            String sValue = Config.mLisConfigCommis.get(Config.mLisConfigCommis.size() - 1).getVALUE();
            commis_order = Integer.parseInt(sValue);

        }
        if (mProduct.getCOMISSION_PRODUCT() != null && mProduct.getCOMISSION_PRODUCT().length() > 0) {
            commis_product = Float.parseFloat(mProduct.getCOMISSION_PRODUCT());
        }
        commis_max = (int) (commis_ctv + commis_order + commis_product);
        lCommission = (long) (lCommission + ((commis_max * lPrice) / 100));
        String sVersionCode = "Hoa hồng tối đa: "
                + StringUtil.conventMonney_Long("" + lCommission) + "<font color='#000000'>(" + commis_max +
                "%)</font>";

    }

    private void set_hh_sp(String sCommission, String sPrice, TextView txt_com) {
        try {
            float commission = Float.parseFloat(sCommission);
            int price = Integer.parseInt(sPrice);
            int icom = (int) commission * price / 100;
            String sVersionCode = "Hoa hồng sản phẩm: "
                    + StringUtil.conventMonney_Long("" + icom) + "<font color='#000000'>(" + sCommission +
                    "%)</font>";
            txt_com.setText(Html.fromHtml(sVersionCode), TextView.BufferType.SPANNABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    /*    if (objService.getCOMISSION_PRODUCT() != null) {
            String sVersionCode = "Hoa hồng sp: <font color='#149cc6'>" +
                    objService.getCOMISSION_PRODUCT() + "%</font>";
            holder.txt_commission_product.setText(Html.fromHtml(sVersionCode), TextView.BufferType.SPANNABLE);
            //    holder.txt_commission_product.setText("Hoa hồng sp: ("+objService.getCOMISSION_PRODUCT()+"%)");
        } else
            holder.txt_commission_product.setText("Hoa hồng sp: (0%)");*/
    }

    private void hide_promotion() {
        txt_precent_detail.setVisibility(View.GONE);
        tx_price_delete.setVisibility(View.GONE);
        view_delete_detail.setVisibility(View.GONE);
    }

    private void show_promotion() {
        txt_precent_detail.setVisibility(View.VISIBLE);
        tx_price_delete.setVisibility(View.VISIBLE);
        view_delete_detail.setVisibility(View.VISIBLE);
    }

    private void btn_add_selected(String sText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            txt_add_cart.setBackground(getResources().getDrawable(R.drawable.spr_btn_add_product_selected));
            txt_add_cart.setText(sText);
        }
        txt_add_cart.setEnabled(false);
    }

    private void initEvent() {
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityProductDetail.this,
                        ActivityConfirmOTP.class);
                intent.putExtra(Constants.KEY_SEND_LOGIN_GUEST, true);
                startActivityForResult(intent, Constants.RequestCode.GET_LOGIN_BUY);
            }
        });
        ll_copy_catalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogComfirm_Two_Button("Copy link catalog",
                        "Bạn muốn link catalog sản phẩm hiện thị có giá hay không có giá?",
                        "Hiển thị giá sp",
                        "Không có giá sp",
                        true,
                        new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
                                String sLink = "https://f5sell.com/catalog?s=";
                                sLink = sLink + Config.ID_SHOP + "&c=" + mProduct.getSUB_ID() + "&ctvid=" + objLogin.getUSER_CODE();
                                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText(getString(R.string.app_name), sLink);
                                clipboard.setPrimaryClip(clip);
                                Toast.makeText(ActivityProductDetail.this, "Link đã được copy vào Clipboard, " +
                                        "bạn có thể gửi cho khách hàng qua Zalo, Messenger, ...", Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onClickNoDialog() {
                                ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
                                String sLink = "https://f5sell.com/catalog?v=568&s=";
                                sLink = sLink + Config.ID_SHOP + "&c=" + mProduct.getSUB_ID() + "&ctvid=" + objLogin.getUSER_CODE();
                                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText(getString(R.string.app_name), sLink);
                                clipboard.setPrimaryClip(clip);
                                Toast.makeText(ActivityProductDetail.this, "Link đã được copy vào Clipboard, " +
                                        "bạn có thể gửi cho khách hàng qua Zalo, Messenger, ...", Toast.LENGTH_LONG).show();

                            }
                        }
                );
            }
        });
        txt_guild_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mProduct.getTRAINING() != null) {
                    Intent intent = new Intent(ActivityProductDetail.this, ActivityDetailNews.class);
                    intent.putExtra(Constants.KEY_SEND_NEWS_TITLE, "Hướng dẫn bán hàng");
                    InfomationObj objInfo = new InfomationObj();
                    objInfo.setCONTENT(mProduct.getTRAINING());
                    intent.putExtra(Constants.KEY_SEND_NEWS_OBJ, objInfo);
                    startActivity(intent);
                } else {
                    showDialogNotify("Thông báo", "Mời bạn liên hệ với chủ shop để được tư vấn " +
                            "thêm về sản phẩm để bán hàng dễ dàng hơn");
                }


            }
        });
        ll_coppy_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogComfirm_Two_Button("Copy link",
                        "Bạn muốn link sản phẩm hiện thị có giá hay không có giá?",
                        "Hiển thị giá",
                        "Không có giá",
                        true,
                        new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                objLogin.getUSER_CODE();
                                String sLink = "https://f5sell.com/catalog?s=";
                                sLink = sLink + Config.ID_SHOP + "&c=" + mProduct.getSUB_ID()
                                        + "&ctvid=" + objLogin.getUSER_CODE() + "&p=" + mProduct.getCODE_PRODUCT();
                                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText(getString(R.string.app_name), sLink);
                                clipboard.setPrimaryClip(clip);
                                Toast.makeText(ActivityProductDetail.this, "Link đã được copy vào Clipboard, " +
                                        "bạn có thể gửi cho khách hàng qua Zalo, Messenger, ...", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onClickNoDialog() {
                                objLogin.getUSER_CODE();
                                String sLink = "https://f5sell.com/catalog?v=536&s=";
                                sLink = sLink + Config.ID_SHOP + "&c=" + mProduct.getSUB_ID()
                                        + "&ctvid=" + objLogin.getUSER_CODE() + "&p=" + mProduct.getCODE_PRODUCT();
                                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText(getString(R.string.app_name), sLink);
                                clipboard.setPrimaryClip(clip);
                                Toast.makeText(ActivityProductDetail.this, "Link đã được copy vào Clipboard, " +
                                        "bạn có thể gửi cho khách hàng qua Zalo, Messenger, ...", Toast.LENGTH_LONG).show();
                            }
                        }
                );


            }
        });
        ll_coppy_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sString = "";
                sString = txt_des_up_face.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(getString(R.string.app_name), sString);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ActivityProductDetail.this, "Copy to Clipboard.", Toast.LENGTH_SHORT).show();

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
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(getString(R.string.app_name), sString);
                clipboard.setPrimaryClip(clip);
                share_multil_image();
            }
        });
        txt_title_vanchuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShipPolicy != null) {
                    Intent intent = new Intent(ActivityProductDetail.this, ActivityDetailNews.class);
                    intent.putExtra(Constants.KEY_SEND_NEWS_TITLE, "Chính sách vận chuyển");
                    intent.putExtra(Constants.KEY_SEND_NEWS_OBJ, mShipPolicy);
                    startActivity(intent);
                } else {
                    showAlertDialog("Thông báo", "Mời bạn liện hệ trực tiếp với shop để biết thêm về chính sách vận chuyển.");
                }

            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityProductDetail.this,
                        ActivityCart.class);
                startActivityForResult(intent, Constants.RequestCode.GET_START_CART);
            }
        });
        txt_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_IS_LOGIN, Boolean.class);
                if (isLogin) {
                    boolean isAddPro = false;
                    if (sThuoctinh1.length() > 0) {
                        sProperties = sThuoctinh1 + ",";
                    }
                    if (sThuoctinh2.length() > 0) {
                        sProperties = sThuoctinh2 + ",";
                    }
                    if (sThuoctinh3.length() > 0) {
                        sProperties = sThuoctinh3 + ",";
                    }
                    if (sThuoctinh4.length() > 0) {
                        sProperties = sThuoctinh4 + ",";
                    }
                    if (mObjPro1 != null)
                        mListPrppeti.add(mObjPro1);
                    if (mObjPro2 != null)
                        mListPrppeti.add(mObjPro2);
                    if (mObjPro3 != null)
                        mListPrppeti.add(mObjPro3);
                    if (mObjPro4 != null)
                        mListPrppeti.add(mObjPro4);
                    if (mListPrppeti.size() > 0)
                        mProduct.setmLisPropeti(mListPrppeti);
                    if (sProperties.length() > 0) {
                        sProperties = sProperties.substring(0, sProperties.length() - 1);
                        mProduct.setsThuoctinh(sProperties);
                    }
                    if (mList != null && mList.size() > 0) {
                        for (Products obj : mList) {
                            if (mProduct.getCODE_PRODUCT().equals(obj.getCODE_PRODUCT())) {
                                isAddPro = true;
                                return;
                            }
                        }
                        if (!isAddPro) {
                            mList.add(mProduct);
                            btn_add_selected("Đã thêm vào giỏ hàng");
                        }
                    } else {
                        btn_add_selected("Đã thêm vào giỏ hàng");
                        mList.add(mProduct);
                    }
                    ObjLisCart obj = new ObjLisCart(mList);
                    SharedPrefs.getInstance().put(Constants.KEY_SAVE_LIST_CART, obj);
                    EventBus.getDefault().post(new MessageEvent(Constants.EventBus.KEY_SEND_BUDGER_CART,
                            Float.parseFloat("1"), 0));
                    set_badger();
                } else {
                    showDialogComfirm("Đăng nhập",
                            "Hãy đăng ký tài khoản để được mua sản phẩm này với giá gốc và tham gia bán" +
                                    " hàng cùng GD Shop và hưởng hoa hồng CỰC SỐC.",
                            true, new ClickDialog() {
                                @Override
                                public void onClickYesDialog() {
                                    Intent intent = new Intent(ActivityProductDetail.this,
                                            ActivityConfirmOTP.class);
                                    intent.putExtra(Constants.KEY_SEND_LOGIN_GUEST, true);
                                    startActivityForResult(intent, Constants.RequestCode.GET_LOGIN_BUY);
                                }

                                @Override
                                public void onClickNoDialog() {

                                }
                            });
                }

            }
        });
      /*  ll_tab_layout_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_tab_layout_face.setSelected(true);
                ll_tab_layout_content.setSelected(false);
                loadFragmentFracebookProductDetil();
                scroll_product_detail.stopNestedScroll();
                // scroll_product_detail.scrollTo(0, (int)ll_tab_layout_content.getY());
            }
        });
        ll_tab_layout_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragmentContentProductDetil();
                scroll_product_detail.stopNestedScroll();
                // scroll_product_detail.scrollTo(0, (int)ll_tab_layout_content.getY());
                ll_tab_layout_face.setSelected(false);
                ll_tab_layout_content.setSelected(true);


            }
        });*/


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RequestCode.GET_START_CART) {
            EventBus.getDefault().post(new MessageEvent(Constants.EventBus.KEY_SEND_BUDGER_CART,
                    Float.parseFloat("1"), 0));
            finish();
        }
    }

    @Override
    public void show_error_api() {

    }

    @Override
    public void show_product_cat(ResponGetCat obj) {

    }

    @Override
    public void show_product_sub_product(ResponSubProduct obj) {

    }

    @Override
    public void show_product_sub_product_child(ResponSubProduct obj) {

    }

    @Override
    public void show_product_cat_detail(ResponGetProduct obj) {

    }

    @Override
    public void show_product_trend(CategoryProductHome obj) {

    }

    @Override
    public void show_product_by_listid(ResponGetProduct obj) {

    }

    @Override
    public void show_product_detail(Products objProduct) {
        hideDialogLoading();
        if (objProduct != null) {
            mProduct = objProduct;
            set_info_product();
        }
    }

    @Override
    public void show_get_properties(ResponGetPropeti obj) {
        hideDialogLoading();
        if (obj != null && obj.getsERROR().equals("0000")) {
            if (obj.getLisDistrict() != null) {
                mProduct.setmListThuoctinhTong(obj.getLisDistrict());
                if (obj.getLisDistrict().size() > 0) {
                    ll_spinner.setVisibility(View.VISIBLE);
                    PropetiObj objPro = obj.getLisDistrict().get(0);
                    txt_title_spinner_1.setText(objPro.getNAME());
                    for (PropetiObj.PropetiDetail objDetail : objPro.getINFO()) {
                        objDetail.setNAME_PARENT(objPro.getNAME());
                        dataset.add(objDetail.getSUB_PROPERTIES());
                        mListThuoctinh1.add(objDetail);
                    }
                }
                if (obj.getLisDistrict().size() > 1) {
                    ll_spinner_2.setVisibility(View.VISIBLE);
                    txt_title_spinner_2.setVisibility(View.VISIBLE);
                    spiner_type_2.setVisibility(View.VISIBLE);
                    PropetiObj objPro = obj.getLisDistrict().get(1);
                    txt_title_spinner_2.setText(objPro.getNAME());
                    for (PropetiObj.PropetiDetail objDetail : objPro.getINFO()) {
                        objDetail.setNAME_PARENT(objPro.getNAME());
                        listPropeti_2.add(objDetail.getSUB_PROPERTIES());
                        mListThuoctinh2.add(objDetail);
                    }
                }
                if (obj.getLisDistrict().size() > 2) {
                    ll_spinner_3.setVisibility(View.VISIBLE);
                    PropetiObj objPro = obj.getLisDistrict().get(2);
                    txt_title_spinner_3.setText(objPro.getNAME());
                    for (PropetiObj.PropetiDetail objDetail : objPro.getINFO()) {
                        objDetail.setNAME_PARENT(objPro.getNAME());
                        listPropeti_3.add(objDetail.getSUB_PROPERTIES());
                        mListThuoctinh3.add(objDetail);
                    }
                }
                if (obj.getLisDistrict().size() > 3) {
                    ll_spinner_4.setVisibility(View.VISIBLE);
                    PropetiObj objPro = obj.getLisDistrict().get(3);
                    txt_title_spinner_4.setText(objPro.getNAME());
                    for (PropetiObj.PropetiDetail objDetail : objPro.getINFO()) {
                        objDetail.setNAME_PARENT(objPro.getNAME());
                        listPropeti_4.add(objDetail.getSUB_PROPERTIES());
                        mListThuoctinh4.add(objDetail);
                    }
                }

            }
        } else {
            ll_spinner.setVisibility(View.GONE);
            ll_spinner_2.setVisibility(View.GONE);
            ll_spinner_3.setVisibility(View.GONE);
            ll_spinner_4.setVisibility(View.GONE);
        }
        set_data_spinner();
        set_data_spinner_2();
        set_data_spinner_4();
        set_data_spinner_3();
    }

    @Override
    public void onTaskComplete(String result) {
        if (!result.equals("")) { //successfully downloaded
           /* ImageView imgView;
            imgView = (ImageView) findViewById(R.id.imageView);
            imgView.setImageBitmap(BitmapFactory.decodeFile(result));*/
            Toast.makeText(this, "" + result, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Fail to download image", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public List<Bitmap> mLisIma;

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
                        hideDialogLoading_delay();
                        response.body().byteStream(); // Read the data from the stream
                        Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                        if (bmp != null)
                            mLisIma.add(bmp);
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

    File file;
    public static Uri mUri = null;

    private void startDownload(String sUrl) {
        PackageManager m = getPackageManager();
        String s = getPackageName();
        PackageInfo p = null;
        try {
            p = m.getPackageInfo(s, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        s = p.applicationInfo.dataDir + "/video/facebook.MP4";

        file = new File(s);
        if (file.exists()) {
            file.deleteOnExit();
        }

        String urlStr = sUrl;

        URL url = null;
        try {
            url = new URL(urlStr);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(),
                    url.getHost(), url.getPort(), url.getPath(),
                    url.getQuery(), url.getRef());
            url = uri.toURL();
            FileDownloader.getImpl()
                    .create(url.toString())
                    .setTag(sUrl)
                    .setPath(s)
                    .setListener(new FileDownloadListener() {
                        @Override
                        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            System.out.println("pending--------------->");
                        }

                        @Override
                        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            System.out.println("-------------------------------");
                            float t = (float) soFarBytes / (float) totalBytes;
                            System.out.println("t-------->" + t);
                        }

                        @Override
                        protected void completed(BaseDownloadTask task) {
                            Log.i(TAG, "completed: " + task.getPath());
                            System.out.println("path ---->" + task.getPath());
                            mUri = Uri.fromFile(new File(task.getPath()));
                        }

                        @Override
                        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            System.out.println("pause--------------->");
                        }

                        @Override
                        protected void error(BaseDownloadTask task, Throwable e) {
                            System.out.println("error--------------->" + e.toString());

                        }

                        @Override
                        protected void warn(BaseDownloadTask task) {
                            System.out.println("warn--------------->");
                        }
                    })
                    .start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void delete_file() {
        int count = 0;
        File file = new File(FileDownloadUtils.getDefaultSaveRootPath());
        do {
            if (!file.exists()) {
                break;
            }

            if (!file.isDirectory()) {
                break;
            }

            File[] files = file.listFiles();

            if (files == null) {
                break;
            }

            for (File file1 : files) {
                count++;
                file1.delete();
            }
            if (file.exists()) {
                file.delete();
            }
        } while (false);

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        delete_file();
        mLisIma.clear();
    }

    private void checkDiskPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "No Permissions", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    101);
        } else {
            start_download_media();
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    start_download_media();
                    Toast.makeText(this, "Permission given",
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

    public void download_all(String sUrl) {
        try {
            btn_download.setEnabled(false);
            Toast.makeText(this, "Bắt đầu tải ảnh về máy", Toast.LENGTH_SHORT).show();
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
            DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
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
            Toast.makeText(this, "Bắt đầu tải ảnh về máy", Toast.LENGTH_SHORT).show();
            String filename = "filename.mp4";
            String downloadUrlOfImage = sUrl.replaceAll("\\|\\|", "");
            File direct =
                    new File(Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                            .getAbsolutePath() + "/" + BuildConfig.APPLICATION_ID + "/");


            if (!direct.exists()) {
                direct.mkdir();
                //     Log.d(LOG_TAG, "dir created for first time");
            }
            DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
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

    boolean isLoading = false;
    Dialog dialog;

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
        if (mLisIma.size() < iMax) {
            iMax = mLisIma.size();
        }

        for (int i = 0; i < iMax; i++) {
            Bitmap bitmap = mLisIma.get(i);
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

    public void showDialog_Full_image(int posision) {
        dialog = new Dialog(this, R.style.Theme_Dialog);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_fullscreen_image);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        ViewPager viewPager = dialog.findViewById(R.id.viewpager_image);
        ImageView ic_delete = dialog.findViewById(R.id.ic_delete);
        AdapterImage_Zoom_Viewpage adapter = new AdapterImage_Zoom_Viewpage(this, mListAnh);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(posision);
        ic_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

}
