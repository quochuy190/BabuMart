package com.vn.babumart.fragment.left_menu.ctv;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.babumart.MainActivity;
import com.vn.babumart.R;
import com.vn.babumart.activity.charts.ActivityChartMenu;
import com.vn.babumart.activity.charts.ActivityReportCTVDetail;
import com.vn.babumart.activity.collaborators.FragmentListCTV;
import com.vn.babumart.activity.login.ActivityLogin;
import com.vn.babumart.activity.login.Activity_Webview;
import com.vn.babumart.activity.tintuc.ActivityDetailNews;
import com.vn.babumart.activity.tintuc.ActivityListNews;
import com.vn.babumart.activity.tintuc.InterfaceTintuc;
import com.vn.babumart.activity.tintuc.PresenterTintuc;
import com.vn.babumart.adapter.AdapterLeftMenu;
import com.vn.babumart.base.BaseFragment;
import com.vn.babumart.callback.ClickDialog;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.config.Config;
import com.vn.babumart.config.Constants;
import com.vn.babumart.fragment.scan_qrcode.SimpleScannerActivity;
import com.vn.babumart.models.InfomationObj;
import com.vn.babumart.models.MessageEvent;
import com.vn.babumart.models.ObjLeftMenu;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.models.ObjShopInfo;
import com.vn.babumart.models.respon_api.ResponseInfomation;
import com.vn.babumart.untils.SharedPrefs;
import com.vn.babumart.untils.StringUtil;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 22-April-2019
 * Time: 10:30
 * Version: 1.0
 */
public class FragmentLeftMenu extends BaseFragment implements InterfaceTintuc.View {
    private static final String TAG = "FragmentLeftMenu";
    public static FragmentLeftMenu fragment;
    private List<ObjLeftMenu> mList;
    private AdapterLeftMenu adapterService;
    @BindView(R.id.recycle_left_menu)
    RecyclerView recycle_product;
    @BindView(R.id.txt_logout)
    TextView txt_logout;
    @BindView(R.id.ic_facebook)
    ImageView ic_facebook;
    @BindView(R.id.imageView4)
    ImageView ic_group;
    @BindView(R.id.ic_website)
    ImageView ic_website;
    RecyclerView.LayoutManager mLayoutManager;
    private PresenterTintuc mPresenter;

    public static FragmentLeftMenu getInstance() {
        if (fragment == null) {
            synchronized (FragmentLeftMenu.class) {
                if (fragment == null)
                    fragment = new FragmentLeftMenu();
            }
        }
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_menu, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new PresenterTintuc(this);
        Log.e(TAG, "onCreateView: Setup");
        init();
        initData();
        initEvent();
        return view;
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
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(Constants.EventBus.KEY_LOGIN_GUEST)) {
            initData();
        }
    }

    private void initEvent() {
        ic_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objShop = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJ_SHOP, ObjShopInfo.class);
                if (objShop.getFB_PAGE() != null && objShop.getFB_PAGE().length() > 0)
                    start_facebook(objShop.getFB_PAGE());
                else
                    showAlertDialog("Thông báo", "Hiện tại shop đang chưa có Fanpage trên Facebook");
            }
        });
        ic_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objShop = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJ_SHOP, ObjShopInfo.class);
                if (objShop.getWEBSITE() != null && objShop.getWEBSITE().length() > 0) {
                    Intent intent = new Intent(getActivity(), Activity_Webview.class);
                    intent.putExtra(Constants.KEY_SEND_WEBVIEW_TITLE, "Giới thiệu");
                    intent.putExtra(Constants.KEY_SEND_WEBVIEW_OPTION, objShop.getWEBSITE());
                    startActivity(intent);
                } else
                    showAlertDialog("Thông báo", "Hiện tại shop đang xây dựng website");

            }
        });
        ic_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objShop = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJ_SHOP, ObjShopInfo.class);
                if (objShop.getFB_GROUPS() != null && objShop.getFB_GROUPS().length() > 0)
                    start_facebook_group(objShop.getFB_GROUPS());
                else
                    showAlertDialog("Thông báo", "Hiện tại shop đang chưa có group trên Facebook");
            }
        });
        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefs.getInstance().put(Constants.KEY_SAVE_LIST_CART, null);
                SharedPrefs.getInstance().put(Constants.KEY_SAVE_IS_LOGIN, false);
                SharedPrefs.getInstance().put(Constants.KEY_SAVE_USER_LOGIN, null);
                //    SharedPrefs.getInstance().put(Constants.KEY_SAVE_USERNAME, null);
                Intent intent = new Intent(getContext(), ActivityLogin.class);
                if (Config.mLisConfigCommis != null)
                    Config.mLisConfigCommis.clear();
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private ObjLogin objLogin;
    ObjShopInfo objShop;
    boolean isLogin;

    private void initData() {
        isLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_IS_LOGIN, Boolean.class);
        if (isLogin) {
            txt_logout.setText("Đăng xuất");
        } else {
            txt_logout.setText("Đăng nhập");
        }
        objShop = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJ_SHOP, ObjShopInfo.class);
        objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        mList.clear();
        if (objLogin != null && objLogin.getGROUPS() != null) {
            if (objLogin.getGROUPS().equals(Config.GROUP_SHOP) ||
                    objLogin.getGROUPS().equals(Config.GROUP_QUANLY)) {
                mList.add(new ObjLeftMenu("Thông tin CTV", R.drawable.ic_ctv, 1));
            } else if (objLogin.getGROUPS().equals(Config.GROUP_CONGTACVIEN)) {
                mList.add(new ObjLeftMenu("Danh sách CTV của bạn", R.drawable.ic_ctv, 1));
            }
        }
        mList.add(new ObjLeftMenu("Báo cáo", R.drawable.ic_left_menu_info_chart, 2));
        mList.add(new ObjLeftMenu("Chính sách", R.drawable.ic_left_menu_info_policy, 3));
        mList.add(new ObjLeftMenu("Đào tạo", R.drawable.ic_left_menu_traning, 4));
        mList.add(new ObjLeftMenu("Tin tức sự kiện", R.drawable.ic_left_menu_notify, 5));
        mList.add(new ObjLeftMenu("Quét mã QR", R.drawable.icon_qr_code, 6));
        mList.add(new ObjLeftMenu("Chia sẻ ứng dụng", R.drawable.ic_share_orange, 8));
        if (objShop != null && objShop.getSHOP_NAME() != null)
            mList.add(new ObjLeftMenu(objShop.getSHOP_NAME(), R.drawable.ic_logo, 7));
        else
            mList.add(new ObjLeftMenu("Shop F5Sell", R.drawable.ic_logo, 7));
        //  mList.add(new ObjLeftMenu("Duyệt bài", R.drawable.ic_left_menu_dyetbai, 8));
        adapterService.notifyDataSetChanged();
    }

    private void init() {
        mList = new ArrayList<>();
        adapterService = new AdapterLeftMenu(mList, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        recycle_product.setNestedScrollingEnabled(false);
        recycle_product.setHasFixedSize(true);
        recycle_product.setLayoutManager(mLayoutManager);
        recycle_product.setItemAnimator(new DefaultItemAnimator());
        recycle_product.setAdapter(adapterService);
      /*  adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                setResult(RESULT_OK, new Intent());
                App.mCity = (City) item;
            }
        });*/
        adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                ObjLeftMenu obj = (ObjLeftMenu) item;
                set_click(obj.getId());
            }
        });

    }

    private static final int MY_CAMERA_REQUEST_CODE = 100;

    public void checkPermissionCamera() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_REQUEST_CODE);
        } else {
            startActivity(new Intent(getContext(), SimpleScannerActivity.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getContext(), SimpleScannerActivity.class));
            } else {
                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    ObjLogin mLogin;

    private void set_click(int position) {
        mLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        Intent intent = null;
        switch (position) {
            case 1:
                intent = new Intent(getActivity(), FragmentListCTV.class);
                //  MainActivity.setback_toolbar();
                break;
            case 2:
                if (StringUtil.check_login_group()) {
                    if (mLogin.getGROUPS().equals("5")) {
                        intent = new Intent(getActivity(), ActivityReportCTVDetail.class);
                    } else {
                        intent = new Intent(getActivity(), ActivityChartMenu.class);
                    }
                }
                //  MainActivity.setback_toolbar();
                break;
            case 3:
                intent = new Intent(getActivity(), ActivityListNews.class);
                intent.putExtra(Constants.KEY_SEND_NEWS_TITLE, "Chính sách");
                intent.putExtra(Constants.KEY_SEND_NEWS_TYPE, "2");
                //  MainActivity.setback_toolbar();
                break;
            case 4:
                if (StringUtil.check_login_group()) {
                    intent = new Intent(getActivity(), ActivityListNews.class);
                    intent.putExtra(Constants.KEY_SEND_NEWS_TITLE, "Đào tạo");
                    intent.putExtra(Constants.KEY_SEND_NEWS_TYPE, "3");
                    //  MainActivity.setback_toolbar();
                } else {
                    showDialogComfirm("Thông báo",
                            "Mời bạn đăng nhập để tiếp tục sử dụng chức năng này.",
                            true, new ClickDialog() {
                                @Override
                                public void onClickYesDialog() {
                                    Intent intent = new Intent(getContext(),
                                            ActivityLogin.class);
                                    intent.putExtra(Constants.KEY_SEND_LOGIN_GUEST, true);
                                    startActivityForResult(intent, Constants.RequestCode.GET_LOGIN_BUY);
                                }

                                @Override
                                public void onClickNoDialog() {

                                }
                            });
                }

                break;
            case 5:
                intent = new Intent(getActivity(), ActivityListNews.class);
                intent.putExtra(Constants.KEY_SEND_NEWS_TITLE, "Tin tức sự kiện");
                intent.putExtra(Constants.KEY_SEND_NEWS_TYPE, "4");
                //  start_facebook("411146496150032");
                break;
            case 6:
                checkPermissionCamera();

                // intent = new Intent(getActivity(), ActivityNotify.class);
                //  MainActivity.setback_toolbar();
                break;
            case 8:
                StringUtil.share_app(getActivity(),
                        "Tải ứng dụng ngay để trở thành cộng tác viên và nhận nhiều hoa hồng, " +
                                "chiết khấu từ Shop nhé. " +
                                "\n\n" +
                                "http://f5sell.com/buy/f5sell.jsp?idshop=" + Config.ID_SHOP);
                // intent = new Intent(getActivity(), ActivityNotify.class);
                //  MainActivity.setback_toolbar();
                break;
          /*  case 3:
                intent = new Intent(getActivity(), Activity_Webview.class);
                intent.putExtra(Constants.KEY_SEND_WEBVIEW_TITLE, "Chính sách");
                intent.putExtra(Constants.KEY_SEND_WEBVIEW_OPTION, "https://imbeautiful.vn/chinh-sach");
                //  MainActivity.setback_toolbar();
                break;*/
            case 7:
                showDialogLoading();
                String sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
                mPresenter.api_get_infomation(sUser, "1", "");
            /*    intent = new Intent(getActivity(), Activity_Webview.class);
                intent.putExtra(Constants.KEY_SEND_WEBVIEW_TITLE, "Giới thiệu");
                intent.putExtra(Constants.KEY_SEND_WEBVIEW_OPTION, "https://imbeautiful.vn/");*/
                //  MainActivity.setback_toolbar();
                break;
        }
        if (intent != null) {
            startActivity(intent);
        } else {

        }
        MainActivity.close_drawer();
    }

    private void start_facebook(String sIdPage) {
        final String urlFb = "fb://page/" + sIdPage;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlFb));

        // If a Facebook app is installed, use it. Otherwise, launch
        // a browser
        final PackageManager packageManager = getContext().getPackageManager();
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() == 0) {
            final String urlBrowser = "https://www.facebook.com/pages/" + sIdPage;
            intent.setData(Uri.parse(urlBrowser));
        }
        startActivity(intent);
    }

    private void start_facebook_group(String id) {
        final String urlFb = "fb://group/" + id;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlFb));

        // If a Facebook app is installed, use it. Otherwise, launch
        // a browser
        final PackageManager packageManager = getContext().getPackageManager();
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() == 0) {
            final String urlBrowser = "https://www.facebook.com/group/" + id;
            intent.setData(Uri.parse(urlBrowser));
        }
        startActivity(intent);
    }

    @Override
    public void show_error_api() {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_api_infomation(ResponseInfomation objRes) {
        hideDialogLoading();
        if (objRes.getERROR().equals("0000")) {
            if (objRes.getmList() != null && objRes.getmList().size() > 0) {
                InfomationObj obj = objRes.getmList().get(0);
                Intent intent = new Intent(getContext(), ActivityDetailNews.class);
                intent.putExtra(Constants.KEY_SEND_NEWS_TITLE, "Giới thiệu");
                intent.putExtra(Constants.KEY_SEND_NEWS_OBJ, obj);
                startActivity(intent);
            }

        }

    }

    @Override
    public void show_api_infomation_daotao(ResponseInfomation objRes) {

    }
}
