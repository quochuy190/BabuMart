package com.vn.babumart;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.vn.babumart.activity.collaborators.ActivityCtvDetail;
import com.vn.babumart.activity.collaborators.InterfaceCollaborators;
import com.vn.babumart.activity.collaborators.PresenterCTV;
import com.vn.babumart.activity.login.ActivityIntroduce;
import com.vn.babumart.activity.login.InterfaceLogin;
import com.vn.babumart.activity.login.PresenterLogin;
import com.vn.babumart.activity.notify.ActivityNotify;
import com.vn.babumart.activity.notify.InterfaceNotify;
import com.vn.babumart.activity.notify.PresenterNotify;
import com.vn.babumart.activity.products.ActivityCart;
import com.vn.babumart.adapter.AdapterViewpager;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.callback.ClickDialog;
import com.vn.babumart.config.Config;
import com.vn.babumart.config.Constants;
import com.vn.babumart.fragment.FragmentCommissionCTV;
import com.vn.babumart.fragment.FragmentCommissionsHome;
import com.vn.babumart.fragment.FragmentHome;
import com.vn.babumart.fragment.FragmentOrder;
import com.vn.babumart.fragment.FragmentProduct;
import com.vn.babumart.fragment.left_menu.ctv.FragmentLeftMenu;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.ListBankName;
import com.vn.babumart.models.MessageEvent;
import com.vn.babumart.models.ObjBankName;
import com.vn.babumart.models.ObjCTV;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.models.ObjNotify;
import com.vn.babumart.models.ObjShopInfo;
import com.vn.babumart.models.respon_api.ObjLisCart;
import com.vn.babumart.models.respon_api.ResponGetLisCTV;
import com.vn.babumart.models.respon_api.ResponGetnotify;
import com.vn.babumart.untils.SharedPrefs;
import com.vn.babumart.untils.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        InterfaceLogin.View, InterfaceCollaborators.View, InterfaceNotify.View {
    private static final String TAG = "MainActivity";
    AdapterViewpager adapter;
    MenuItem prevMenuItem;
    @BindView(R.id.frame_leftmenu)
    FrameLayout frame_leftmenu;
    @BindView(R.id.img_nav_right)
    ImageView img_nav_right;
    @BindView(R.id.txt_name_nav)
    TextView txt_name_nav;
    @BindView(R.id.txt_code_ctv)
    TextView txt_code_ctv;
    @BindView(R.id.ic_notify_appbar)
    ImageView ic_notify_appbar;
    @BindView(R.id.ic_cart_appbar)
    ImageView ic_cart_appbar;
    @BindView(R.id.txt_badger_notify)
    TextView txt_badger_notify;
    @BindView(R.id.txt_badger_cart)
    TextView txt_badger_cart;
    public static DrawerLayout drawer;
    public static Toolbar toolbar;
    private PresenterLogin mPresenterLogin;
    private PresenterNotify mPresenterNotify;
    public static EditText edt_search_main;
    public static TextView txt_title_main;
    String UUID;
    FragmentHome fragmentHome;
    FragmentOrder fragmentOrder;
    FragmentProduct fragmentProduct;
    FragmentCommissionsHome fragmentCommissions;
    FragmentCommissionCTV fragmentCommissions_CTV;
    Fragment fragmentCurrent;

    BottomNavigationView navigation;
    PresenterCTV mPresenter;
    @BindView(R.id.ic_chat_main)
    ImageView ic_chat_main;
    @BindView(R.id.ic_zalo_main)
    ImageView ic_zalo_main;
    @BindView(R.id.ic_call_main)
    ImageView ic_call_main;
    boolean isLogin;
    boolean isUpdateUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterCTV(this);
        mPresenterLogin = new PresenterLogin(this);
        mPresenterNotify = new PresenterNotify(this);
        mPresenterLogin.api_getshopinfo(Config.ID_SHOP);
        initAppbar();
        UUID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        navigation = (BottomNavigationView) findViewById(R.id.nav_bottom_bar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        sType = getIntent().getStringExtra(Constants.KEY_SEND_NOTIFYCATION);
        isLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_IS_LOGIN, Boolean.class);
        get_width_height();
        initData();
        initEvent();
        get_hash();
        set_badger();
        if (isLogin) {
            if (App.isLoginHome) {
                mPresenterNotify.api_get_list_notify(sUserName, "1", "50");
                if (sType != null && sType.equals("3")) {
                    navigation.setSelectedItemId(R.id.navigation_order);
                } else if (sType != null && sType.equals("2")) {
                    navigation.setSelectedItemId(R.id.navigation_order);
                } else if (sType != null && sType.equals("4")) {
                    navigation.setSelectedItemId(R.id.navigation_bonus);
                } else if (sType != null && sType.equals("5")) {
                    navigation.setSelectedItemId(R.id.navigation_bonus);
                } else {
                    loadFragmentHome();
                }

                //  navigation.setSelectedItemId(R.id.navigation_order);
            } else {
                initLogin();
            }
        } else loadFragmentHome();


        //setupViewPager(viewPager);
    }

    private void initAppbar() {
        edt_search_main = findViewById(R.id.edt_search_main);
        txt_title_main = findViewById(R.id.txt_title_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void check_icon_appbar() {
        if (objShopInfo != null) {
            if (objShopInfo.getMOBILE() != null && objShopInfo.getMOBILE().length() > 0) {
                ic_call_main.setVisibility(View.VISIBLE);
            } else {
                ic_call_main.setVisibility(View.GONE);
            }
            if (objShopInfo.getFB_PAGE() != null && objShopInfo.getFB_PAGE().length() > 0) {
                ic_chat_main.setVisibility(View.VISIBLE);
            } else {
                ic_chat_main.setVisibility(View.GONE);
            }
            if (objShopInfo.getZALO() != null && objShopInfo.getZALO().length() > 0) {
                ic_zalo_main.setVisibility(View.VISIBLE);
            } else {
                ic_zalo_main.setVisibility(View.GONE);
            }
        }

    }


    /*  @Override
      public void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          EventBus.getDefault().register(this);
      }

      @Override
      public void onDestroy() {
          super.onDestroy();
          EventBus.getDefault().unregister(this);
      }*/
    private void get_width_height() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        App.mWidth = width;
        App.mHeight = height;
        Log.e(TAG, "get_height: " + height);
        Log.e(TAG, "get_width: " + width);
    }

    private void set_badger() {
        ObjLisCart objCat = SharedPrefs.getInstance().get(Constants.KEY_SAVE_LIST_CART, ObjLisCart.class);
        if (objCat != null && objCat.getmList() != null && objCat.getmList().size() > 0) {
            txt_badger_cart.setVisibility(View.VISIBLE);
            txt_badger_cart.setText("" + objCat.getmList().size());
        } else {
            txt_badger_cart.setVisibility(View.GONE);
        }
        if (mObjLogin != null && mObjLogin.getGROUPS() != null) {
            if (mObjLogin.getGROUPS().equals(Config.GROUP_CONGTACVIEN) ||
                    mObjLogin.getGROUPS().equals(Config.GROUP_KHACHHANG)) {
                ic_notify_appbar.setVisibility(View.VISIBLE);
                ic_cart_appbar.setVisibility(View.VISIBLE);
            } else {
                ic_notify_appbar.setVisibility(View.VISIBLE);
                ic_cart_appbar.setVisibility(View.GONE);
            }
        }
    }

    private void set_badger_noti(int count) {
        if (count > 0) {
            txt_badger_notify.setVisibility(View.VISIBLE);
            txt_badger_notify.setText("" + count);
        } else {
            txt_badger_notify.setVisibility(View.GONE);
        }
        if (mObjLogin.getGROUPS().equals(Config.GROUP_CONGTACVIEN)
                || mObjLogin.getGROUPS().equals(Config.GROUP_KHACHHANG)) {
            ic_notify_appbar.setVisibility(View.VISIBLE);
            ic_cart_appbar.setVisibility(View.VISIBLE);
        } else {
            ic_notify_appbar.setVisibility(View.VISIBLE);
            ic_cart_appbar.setVisibility(View.GONE);
        }
    }

    private void get_hash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.vn.ga_dongtao",
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

    private void initEvent() {
        ic_zalo_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://zalo.me/" + objShopInfo.getZALO()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Lỗi", Toast.LENGTH_LONG).show();
                }
            }
        });
        ic_chat_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (objShopInfo != null && objShopInfo.getFB_PAGE() != null) {
                    Uri uri = Uri.parse("https://www.messenger.com/t/" + objShopInfo.getFB_PAGE());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }

            }
        });
        ic_call_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (objShopInfo != null && objShopInfo.getMOBILE() != null)
                    showDialog_CallPhone(new ClickDialog() {
                        @Override
                        public void onClickYesDialog() {
                            objShopInfo = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJ_SHOP, ObjShopInfo.class);
                            StringUtil.call_phone(MainActivity.this,
                                    objShopInfo.getMOBILE().trim().replaceAll(" ", ""));
                        }

                        @Override
                        public void onClickNoDialog() {

                        }
                    });

                else
                    showDialogNotify("Thông báo",
                            "Hotline của shop hiện tại chưa sẵn sàng, mời bạn thử lại sau");
            }
        });
        txt_code_ctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mObjLogin != null && mObjLogin.getUSER_CODE() != null) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText(getString(R.string.app_name), mObjLogin.getUSER_CODE());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(MainActivity.this, "Mã giới thiệu đã được copy vào bộ nhớ đệm", Toast.LENGTH_SHORT).show();

                }
            }
        });
        img_nav_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
                if (StringUtil.check_login_group()) {
                    if (objLogin.getGROUPS().equals("5")
                            || objLogin.getGROUPS().equals("8")) {
                        Intent intent = new Intent(MainActivity.this, ActivityCtvDetail.class);
                        intent.putExtra(Constants.KEY_SEND_UPDATE_USER, true);
                        startActivityForResult(intent, Constants.RequestCode.GET_UPDATE_CTV);
                        close_drawer();
                    }
                }

            }
        });
        ic_cart_appbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityCart.class);
                startActivityForResult(intent, Constants.RequestCode.GET_START_CART);

            }
        });
        ic_notify_appbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, ActivityNotify.class),
                        Constants.RequestCode.GET_START_NOTIFY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RequestCode.GET_START_CART) {
            set_badger();
        }
        /*if (requestCode == Constants.RequestCode.GET_UPDATE_CTV && resultCode == RESULT_OK) {
            isUpdateUser = true;
            initLogin();
        }*/
        if (requestCode == Constants.RequestCode.GET_START_NOTIFY) {
            mPresenterNotify.api_get_list_notify(sUserName, "1", "150");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.isLoginHome = false;
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(Constants.EventBus.KEY_SEND_BUDGER_CART)) {
            set_badger();
        }
        if (event.message.equals(Constants.EventBus.KEY_SHOW_PRODUCT)) {
            navigation.setSelectedItemId(R.id.navigation_product);
        }
        if (event.message.equals(Constants.EventBus.KEY_SEND_NOTIFY)) {
            sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
            mPresenterNotify.api_get_list_notify(sUserName, "1", "150");
        }
        if (event.message.equals("home")) {
            if (fragmentOrder != null && fragmentOrder.isAdded())
                EventBus.getDefault().post(new MessageEvent("load_order",
                        Float.parseFloat("1"), 0));
            else
                App.isLoadOrder = true;
            navigation.setSelectedItemId(R.id.navigation_order);
        } else if (event.message.equals("product")) {
            navigation.setSelectedItemId(R.id.navigation_product);
        }
        if (event.message.equals(Constants.EventBus.KEY_LOGIN_GUEST)) {
            initData();
        }
        if (event.message.equals(Constants.EventBus.KEY_UPDATE_USER)) {
            initLogin();
        }
    }

    private ObjLogin mObjLogin;

    private String sUserName;
    String sType;

    private void initData() {
        isLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_IS_LOGIN, Boolean.class);
        if (isLogin) {
            mObjLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
            if (mObjLogin==null)
                return;
            if (mObjLogin.getUSER_CODE() == null)
                return;
            if (mObjLogin.getFULL_NAME() == null)
                return;
            sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
            if (StringUtil.check_login_group()) {
                if (mObjLogin.getGROUPS().equals("5") || mObjLogin.getGROUPS().equals("8")) {
                    txt_name_nav.setText(mObjLogin.getFULL_NAME());
                    txt_code_ctv.setText(Html.fromHtml("Mã CTV: " +
                            "<b>" + mObjLogin.getUSER_CODE().toUpperCase() + "</b>"));
                    img_nav_right.setImageResource(R.drawable.ic_setup);
                    mPresenter.api_get_ctv_detail(sUserName, sUserName, "");
                } else {
                    img_nav_right.setImageResource(R.drawable.ic_right);
                    txt_code_ctv.setText("Mã CTV: " + mObjLogin.getUSER_CODE().toUpperCase());
                    txt_name_nav.setText(mObjLogin.getFULL_NAME());
                }
            }
        } else {
            txt_name_nav.setText("Khách hàng");
        }
        start_left_menu();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // MainActivity.edt_search_main.setVisibility(View.VISIBLE);
                    MainActivity.txt_title_main.setVisibility(View.GONE);
                    //  toolbar.setTitle("Shop");
                    //fragment = FragmentHome.getInstance();
                    loadFragmentHome();
                    return true;
                case R.id.navigation_order:
                    isLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_IS_LOGIN, Boolean.class);
                    //  MainActivity.edt_search_main.setVisibility(View.VISIBLE);
                    MainActivity.txt_title_main.setVisibility(View.GONE);
                    loadFragmentOrder();
                    // toolbar.setTitle("My Gifts");
              /*      fragment = FragmentOrder.getInstance();
                    loadFragment(fragment);*/
                    return true;
                case R.id.navigation_product:
                    //  MainActivity.edt_search_main.setVisibility(View.VISIBLE);
                    MainActivity.txt_title_main.setVisibility(View.GONE);
                    loadFragmentProduct();
                    // toolbar.setTitle("Cart");
               /*     fragment = FragmentProduct.getInstance();
                    loadFragment(fragment);*/
                    return true;
                case R.id.navigation_bonus:
                    mObjLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
                    isLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_IS_LOGIN, Boolean.class);
                    if (isLogin) {
                        if (mObjLogin != null && mObjLogin.getGROUPS() != null) {
                            if (mObjLogin.getGROUPS().equals(Config.GROUP_CONGTACVIEN) ||
                                    mObjLogin.getGROUPS().equals(Config.GROUP_KHACHHANG)) {
                                MainActivity.edt_search_main.setVisibility(View.GONE);
                                //  MainActivity.txt_title_main.setVisibility(View.VISIBLE);
                                MainActivity.txt_title_main.setText("Hoa hồng");
                                loadFragment_HH_CTV();
                            } else if (mObjLogin != null && mObjLogin.getGROUPS()
                                    != null && mObjLogin.getGROUPS().equals("3")) {
                                MainActivity.edt_search_main.setVisibility(View.GONE);
                                //   MainActivity.txt_title_main.setVisibility(View.VISIBLE);
                                MainActivity.txt_title_main.setText("Hoa hồng");
                                loadFragmentSetup();
                            }
                        }
                    } else {
                        MainActivity.edt_search_main.setVisibility(View.GONE);
                        //  MainActivity.txt_title_main.setVisibility(View.VISIBLE);
                        MainActivity.txt_title_main.setText("Hoa hồng");
                        loadFragment_HH_CTV();
                    }

                    return true;
            }
            return false;
        }
    };
/*
    public static void setback_toolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back_white);
    }*/

 /*   private void loadFragmentHome() {
        Fragment fragment = FragmentMainActivity.getInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_home_main, fragment);
        transaction.commit();
    }*/

    private void start_left_menu() {
        Fragment fragmentleft = FragmentLeftMenu.getInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_leftmenu, fragmentleft);
        transaction.commit();
    }

    public static void close_drawer() {
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_main;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            fragmentBackTack();
        }
    }

    boolean isDoubleClick;

    public void fragmentBackTack() {
        if (isDoubleClick) {
            finish();
            return;
        }
        this.isDoubleClick = true;
        Toast.makeText(this, "Chạm lần nữa để thoát", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isDoubleClick = false;
            }
        }, 2000);
    }

    String sUser, sPass;

    private void initLogin() {
        sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        sPass = SharedPrefs.getInstance().get(Constants.KEY_SAVE_PASSWORD, String.class);
        if (sUser != null && sPass != null) {
            Log.e(TAG, "initLogin: get login");
            showDialogLoading();
            mPresenterLogin.api_login(sUser, sPass, UUID);
        }
        mPresenterNotify.api_get_list_notify(sUserName, "1", "150");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private int count = 1;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
     /*   if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, ActivityCart.class));
        }
        if (item.getItemId() == R.id.action_notify) {
            startActivity(new Intent(MainActivity.this, ActivityNotify.class));
        }*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_home) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void show_error_api() {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_update_notify(ErrorApi objError) {

    }

    @Override
    public void show_get_notify(ResponGetnotify obj) {
        int coutnt = 0;
        hideDialogLoading();
        if (obj != null && obj.getsERROR().equals("0000")) {
            if (obj.getmList() != null && obj.getmList().size() > 0) {
                for (ObjNotify objNoti : obj.getmList()) {
                    if (objNoti.getIS_READ().equals("0")) {
                        coutnt++;
                    }
                }
            }
        }
        set_badger_noti(coutnt);
    }

    @Override
    public void show_get_list_ctv(ResponGetLisCTV obj) {

    }

    @Override
    public void show_update_ctv(ErrorApi obj) {

    }

    @Override
    public void show_reset_pass_ctv(ErrorApi obj) {

    }

    @Override
    public void show_ctv_detail(ObjCTV objLogin) {

    }

    @Override
    public void show_list_ctv_child(ResponGetLisCTV objLogin) {

    }

    @Override
    public void show_login(ObjLogin obj) {
        hideDialogLoading();
        if (obj != null && obj.getsERROR() != null && obj.getsERROR().equals("0000")) {
            //loadFragmentHome();
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_IS_LOGIN, true);
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_USER_LOGIN, obj);
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_USERNAME, obj.getUSERNAME());
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_PASSWORD, obj.getPASSWORD());
            EventBus.getDefault().post(new MessageEvent(Constants.EventBus.KEY_LOGIN_GUEST, 1, 0));
            start_left_menu();
            if (sType != null && sType.equals("3")) {
                navigation.setSelectedItemId(R.id.navigation_order);
            } else if (sType != null && sType.equals("2")) {
                navigation.setSelectedItemId(R.id.navigation_order);
            } else if (sType != null && sType.equals("4")) {
                navigation.setSelectedItemId(R.id.navigation_bonus);
            } else if (sType != null && sType.equals("5")) {
                navigation.setSelectedItemId(R.id.navigation_bonus);
            } else {
                loadFragmentHome();
            }
            App.isLoginHome = true;
            String sTokenKey = SharedPrefs.getInstance().get(Constants.KEY_TOKEN, String.class);
            mPresenterLogin.api_update_device(sUser, BuildConfig.VERSION_NAME,
                    android.os.Build.BRAND + " "
                            + android.os.Build.MODEL, sTokenKey, "1", android.os.Build.VERSION.RELEASE, UUID);

            //mPresenterLogin.api_getshopinfo(Config.ID_SHOP);
        } else if (obj != null && obj.getsRESULT() != null) {
            showDialogComfirm("Thông báo", obj.getsRESULT(), false, new ClickDialog() {
                @Override
                public void onClickYesDialog() {
                    SharedPrefs.getInstance().put(Constants.KEY_SAVE_LIST_CART, null);
                    SharedPrefs.getInstance().put(Constants.KEY_SAVE_IS_LOGIN, false);
                    Intent intent = new Intent(MainActivity.this, ActivityIntroduce.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onClickNoDialog() {

                }
            });
            // showDialogNotify("Thông báo", obj.getsRESULT());
        }
    }

    @Override
    public void show_register(ErrorApi obj) {

    }

    @Override
    public void show_update_device(ErrorApi obj) {

    }

    @Override
    public void show_get_shopinfo(ObjShopInfo objShop) {
        if (objShop != null && objShop.getERROR().equals("0000")) {
            objShopInfo = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJ_SHOP, ObjShopInfo.class);
        }
        check_icon_appbar();
    }

    @Override
    public void show_check_device(ErrorApi obj) {

    }

    private void loadFragmentHome() {
        mObjLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        fragmentHome = (FragmentHome) getSupportFragmentManager().findFragmentByTag(FragmentHome.class.getName());
        if (fragmentHome == null) {
            fragmentHome = FragmentHome.getInstance();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragmentHome.isAdded()) {
            transaction.add(R.id.frame_home_fragment, fragmentHome, FragmentHome.class.getName());
        } else {
            //  transaction.hide(fragmentCurrent);
            if (fragmentProduct != null && fragmentProduct.isAdded()) {
                transaction.hide(fragmentProduct);
            }
            if (fragmentOrder != null && fragmentOrder.isAdded()) {
                transaction.hide(fragmentOrder);
            }
            if (fragmentCommissions != null && fragmentCommissions.isAdded()) {
                transaction.hide(fragmentCommissions);
            }
            if (fragmentCommissions_CTV != null && fragmentCommissions_CTV.isAdded()) {
                transaction.hide(fragmentCommissions_CTV);
            }
            transaction.show(fragmentHome);
        }
        //   fragmentCurrent = fragmentHome;
        transaction.commit();
    }

    private void loadFragmentOrder() {
        fragmentOrder = (FragmentOrder) getSupportFragmentManager().findFragmentByTag(FragmentOrder.class.getName());
        if (fragmentOrder == null) {
            fragmentOrder = FragmentOrder.getInstance();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragmentOrder.isAdded()) {
            transaction.add(R.id.frame_home_fragment, fragmentOrder, FragmentOrder.class.getName());
        } else {
            if (fragmentProduct != null && fragmentProduct.isAdded()) {
                transaction.hide(fragmentProduct);
            }
            if (fragmentHome != null && fragmentHome.isAdded()) {
                transaction.hide(fragmentHome);
            }
            if (fragmentCommissions != null && fragmentCommissions.isAdded()) {
                transaction.hide(fragmentCommissions);
            }
            if (fragmentCommissions_CTV != null && fragmentCommissions_CTV.isAdded()) {
                transaction.hide(fragmentCommissions_CTV);
            }
            transaction.show(fragmentOrder);
        }
        //  fragmentCurrent = fragmentOrder;
        transaction.commit();
    }

    private void loadFragmentProduct() {
        fragmentProduct = (FragmentProduct) getSupportFragmentManager().findFragmentByTag(FragmentProduct.class.getName());
        if (fragmentProduct == null) {
            fragmentProduct = FragmentProduct.getInstance();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragmentProduct.isAdded()) {
            transaction.add(R.id.frame_home_fragment, fragmentProduct, FragmentProduct.class.getName());
        } else {
            if (fragmentOrder != null && fragmentOrder.isAdded()) {
                transaction.hide(fragmentOrder);
            }
            if (fragmentHome != null && fragmentHome.isAdded()) {
                transaction.hide(fragmentHome);
            }
            if (fragmentCommissions != null && fragmentCommissions.isAdded()) {
                transaction.hide(fragmentCommissions);
            }
            if (fragmentCommissions_CTV != null && fragmentCommissions_CTV.isAdded()) {
                transaction.hide(fragmentCommissions_CTV);
            }
            //   transaction.hide(fragmentCurrent);
            transaction.show(fragmentProduct);
        }
        // fragmentCurrent = fragmentProduct;
        transaction.commit();
    }

    private void loadFragmentSetup() {
        fragmentCommissions = (FragmentCommissionsHome) getSupportFragmentManager().
                findFragmentByTag(FragmentCommissionsHome.class.getName());
        if (fragmentCommissions == null) {
            fragmentCommissions = FragmentCommissionsHome.getInstance();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragmentCommissions.isAdded()) {
            transaction.add(R.id.frame_home_fragment, fragmentCommissions, FragmentCommissionsHome.class.getName());
        } else {
            if (fragmentProduct != null && fragmentProduct.isAdded()) {
                transaction.hide(fragmentProduct);
            }
            if (fragmentHome != null && fragmentHome.isAdded()) {
                transaction.hide(fragmentHome);
            }
            if (fragmentOrder != null && fragmentOrder.isAdded()) {
                transaction.hide(fragmentOrder);
            }
            if (fragmentCommissions_CTV != null && fragmentCommissions_CTV.isAdded()) {
                transaction.hide(fragmentCommissions_CTV);
            }
            //  transaction.hide(fragmentCurrent);
            transaction.show(fragmentCommissions);
        }
        //   fragmentCurrent = fragmentCommissions;
        transaction.commit();
    }

    private void loadFragment_HH_CTV() {
        fragmentCommissions_CTV = (FragmentCommissionCTV) getSupportFragmentManager().
                findFragmentByTag(FragmentCommissionCTV.class.getName());
        if (fragmentCommissions_CTV == null) {
            fragmentCommissions_CTV = FragmentCommissionCTV.getInstance();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragmentCommissions_CTV.isAdded()) {
            transaction.add(R.id.frame_home_fragment, fragmentCommissions_CTV, FragmentCommissionCTV.class.getName());
        } else {
            if (fragmentProduct != null && fragmentProduct.isAdded()) {
                transaction.hide(fragmentProduct);
            }
            if (fragmentHome != null && fragmentHome.isAdded()) {
                transaction.hide(fragmentHome);
            }
            if (fragmentOrder != null && fragmentOrder.isAdded()) {
                transaction.hide(fragmentOrder);
            }
            if (fragmentCommissions != null && fragmentCommissions.isAdded()) {
                transaction.hide(fragmentCommissions);
            }
            // transaction.hide(fragmentCurrent);
            transaction.show(fragmentCommissions_CTV);
        }
        //  fragmentCurrent = fragmentCommissions_CTV;
        transaction.commit();
    }

    List<ObjBankName> mListBank;

    public void save_bank_name() {
        mListBank = new ArrayList<>();
        ListBankName objBank = SharedPrefs.getInstance().get(Constants.KEY_SAVE_LIST_BANK_NAME, ListBankName.class);
        if (objBank != null && objBank.getmList() != null) {
        } else {
            ListBankName obj = new ListBankName(mListBank);
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_LIST_BANK_NAME, obj);
        }
    }

    ObjShopInfo objShopInfo;
    Dialog dialog_call;

    public void showDialog_CallPhone(final ClickDialog clickDialog) {
        dialog_call = new Dialog(this, R.style.Theme_Dialog);
        // dialog.setCancelable(false);
        dialog_call.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_call.setContentView(R.layout.dialog_confirm_call);
        dialog_call.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView txt_name = dialog_call.findViewById(R.id.txt_title);
        TextView txt_exit = dialog_call.findViewById(R.id.txt_exit);
        TextView txt_call = dialog_call.findViewById(R.id.txt_call);
        String sName = "";
        if (objShopInfo.getMOBILE() != null) {
            sName = "Tổng đài chăm sóc khách hàng <b><font color='#FF0000'>"
                    + objShopInfo.getMOBILE() + "</font></b>";
        } else {
            sName = "Tổng đài chăm sóc khách hàng";
        }

        txt_name.setText(Html.fromHtml(sName), TextView.BufferType.SPANNABLE);
        txt_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_call.dismiss();
                clickDialog.onClickYesDialog();
            }
        });
        txt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_call.dismiss();
            }
        });

        dialog_call.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.RequestCode.PERMISSION_CALL_PHONE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    StringUtil.call_phone(MainActivity.this,
                            objShopInfo.getMOBILE().trim().replaceAll(" ", ""));
                } else {
                    Toast.makeText(this, "No permission call phone.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
