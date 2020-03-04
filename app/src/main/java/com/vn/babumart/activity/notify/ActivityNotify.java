package com.vn.babumart.activity.notify;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.vn.babumart.R;
import com.vn.babumart.adapter.AdapterListNotify;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.callback.ClickDialog;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.ObjNotify;
import com.vn.babumart.models.respon_api.ResponGetnotify;
import com.vn.babumart.untils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 24-June-2019
 * Time: 16:19
 * Version: 1.0
 */
public class ActivityNotify extends BaseActivity implements InterfaceNotify.View {
    private static final String TAG = "ActivityNotify";
    private List<ObjNotify> mList;
    private AdapterListNotify adapter;
    @BindView(R.id.rcv_list_report_pay)
    RecyclerView recycle_service;
    RecyclerView.LayoutManager mLayoutManager;
    private PresenterNotify mPresenter;
    int page = 1;
    int number = 150;

    @Override
    public int setContentViewId() {
        return R.layout.activity_recycleview_title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterNotify(this);
        initAppbar();
        init();
        initEvent();

    }

    String sListId = "";

    private void initEvent() {
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mList != null && mList.size() > 0) {
                    for (ObjNotify mNoti : mList) {
                        if (mNoti.getIS_READ() != null && mNoti.getIS_READ().equals("0")) {
                            sListId = sListId + mNoti.getID() + "#";
                        }
                    }
                }
                if (sListId.length() > 0) {
                    showDialogComfirm("Thông báo", "Bạn có muốn chuyển toàn bộ thông báo về trạng thái đã đọc?"
                            , true, new ClickDialog() {
                                @Override
                                public void onClickYesDialog() {
                                    String sId = sListId.substring(0, sListId.length() - 1);
                                    Log.e(TAG, "onClick: " + sId);
                                    showDialogLoading();
                                    mPresenter.api_update_notify(sUser, sId);
                                }

                                @Override
                                public void onClickNoDialog() {

                                }
                            });

                }
            }
        });
    }

    ImageView img_home;

    private void initAppbar() {
        ImageView img_back = findViewById(R.id.img_back);
        img_home = findViewById(R.id.img_home);
        TextView txt_title = findViewById(R.id.txt_title);
        img_home.setImageResource(R.drawable.ic_open_book);
        img_home.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_title.setText("Thông báo");
    }

    String sUser;

    private void initData() {
        img_home.setAlpha((float) 0.1);
        img_home.setEnabled(false);
        showDialogLoading();
        sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenter.api_get_list_notify(sUser, "" + page, "" + number);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void init() {
        mList = new ArrayList<>();
        adapter = new AdapterListNotify(mList, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        recycle_service.setNestedScrollingEnabled(false);
        recycle_service.setHasFixedSize(true);
        recycle_service.setLayoutManager(mLayoutManager);
        recycle_service.setItemAnimator(new DefaultItemAnimator());
        recycle_service.setAdapter(adapter);

        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                ObjNotify objNotify = (ObjNotify) item;
                Intent intent = new Intent(ActivityNotify.this, ActivityDetailNotify.class);
                intent.putExtra(Constants.KEY_SEND_NOTIFY, objNotify);
                startActivity(intent);
            }
        });

    }

    @Override
    public void show_error_api() {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_update_notify(ErrorApi objError) {
        if (objError != null && objError.getsERROR().equals("0000")) {
            mPresenter.api_get_list_notify(sUser, "" + page, "" + number);
        } else {
            hideDialogLoading();
            showAlertDialog("Thông báo", objError.getsRESULT());
        }
    }

    @Override
    public void show_get_notify(ResponGetnotify obj) {
        mList.clear();
        hideDialogLoading();
        if (obj != null && obj.getsERROR() != null && obj.getsERROR().equals("0000")) {
            if (obj.getmList() != null && obj.getmList().size() > 0) {
                mList.addAll(obj.getmList());

            } else {
                showDialogNotify("Thông báo", "Hiện tại không có thông báo nào.");
            }
            if (obj.getSUM_NOT_READ() != null && obj.getSUM_NOT_READ().length() > 0) {
                if (Integer.parseInt(obj.getSUM_NOT_READ()) > 0) {
                    img_home.setAlpha((float) 1);
                    img_home.setEnabled(true);
                } else {
                    img_home.setAlpha((float) 0.1);
                    img_home.setEnabled(false);
                }
            } else {
                img_home.setAlpha((float) 0.1);
                img_home.setEnabled(false);
            }

        } else if (obj != null && obj.getsRESULT() != null) {
            showDialogNotify("Thông báo", obj.getsRESULT());
        }
        adapter.notifyDataSetChanged();
    }
}
