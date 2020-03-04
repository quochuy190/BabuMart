package com.vn.babumart.activity.tintuc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.babumart.R;
import com.vn.babumart.adapter.AdapterNewsHome;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.InfomationObj;
import com.vn.babumart.models.respon_api.ResponseInfomation;
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
public class ActivityListNews extends BaseActivity implements InterfaceTintuc.View {
    private List<InfomationObj> mList;
    private AdapterNewsHome adapter;
    @BindView(R.id.rcv_list_report_pay)
    RecyclerView recycle_service;
    RecyclerView.LayoutManager mLayoutManager;
    private PresenterTintuc mPresenter;
    int page = 1;
    int number = 30;
    private String sType = "", sCategory = "", sTitle = "imBeautiful";

    @Override
    public int setContentViewId() {
        return R.layout.activity_recycleview_title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterTintuc(this);
        init();
        initData();
        initAppbar();
    }

    private void initAppbar() {
        ImageView img_back = findViewById(R.id.img_back);
        TextView txt_title = findViewById(R.id.txt_title);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_title.setText(sTitle);
    }

    String sUser;

    private void initData() {
        sTitle = getIntent().getStringExtra(Constants.KEY_SEND_NEWS_TITLE);
        sType = getIntent().getStringExtra(Constants.KEY_SEND_NEWS_TYPE);
        sUser = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        if (sType != null && sType.length() > 0) {
            showDialogLoading();
            mPresenter.api_get_infomation(sUser, sType, "");
        }


    }

    private void init() {
        mList = new ArrayList<>();
        adapter = new AdapterNewsHome(mList, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        recycle_service.setNestedScrollingEnabled(false);
        recycle_service.setHasFixedSize(true);
        recycle_service.setLayoutManager(mLayoutManager);
        recycle_service.setItemAnimator(new DefaultItemAnimator());
        recycle_service.setAdapter(adapter);

        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                InfomationObj obj = (InfomationObj) item;
                Intent intent = new Intent(ActivityListNews.this, ActivityDetailNews.class);
                intent.putExtra(Constants.KEY_SEND_NEWS_TITLE, sTitle);
                intent.putExtra(Constants.KEY_SEND_NEWS_OBJ, obj);
                startActivity(intent);
            }
        });

    }

    @Override
    public void show_error_api() {

    }

    @Override
    public void show_api_infomation(ResponseInfomation objRes) {
        hideDialogLoading();
        if (objRes != null && objRes.getERROR().equals("0000")) {
            mList.clear();
            mList.addAll(objRes.getmList());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void show_api_infomation_daotao(ResponseInfomation objRes) {
        hideDialogLoading();
        if (objRes != null && objRes.getERROR().equals("0000")) {
            mList.clear();
            mList.addAll(objRes.getmList());
            adapter.notifyDataSetChanged();
        }
    }


}
