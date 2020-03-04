package com.vn.babumart.activity.notify;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;


import com.vn.babumart.R;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.ObjNotify;
import com.vn.babumart.models.respon_api.ResponGetnotify;
import com.vn.babumart.untils.SharedPrefs;
import com.vn.babumart.untils.StringUtil;

import butterknife.BindView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 11-September-2019
 * Time: 17:10
 * Version: 1.0
 */
public class ActivityDetailNotify extends BaseActivity implements InterfaceNotify.View {
    @BindView(R.id.txt_notify_time)
    TextView txt_notify_time;
    @BindView(R.id.webview_content)
    WebView webview_content;
    PresenterNotify mPresenter;

    @Override
    public int setContentViewId() {
        return R.layout.activity_notify_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterNotify(this);
        initAppbar();
        initData();
    }

    private void initData() {
        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        ObjNotify objNotify = (ObjNotify) getIntent().getSerializableExtra(Constants.KEY_SEND_NOTIFY);
        if (objNotify != null) {
            mPresenter.api_update_notify(sUserName, objNotify.getID());
            txt_notify_time.setText(objNotify.getSENT_TIME());
            StringUtil.initWebview(objNotify.getCONTENT(), webview_content);
        }
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
        txt_title.setText("Chi tiết thông báo");
    }

    @Override
    public void show_error_api() {

    }

    @Override
    public void show_update_notify(ErrorApi objError) {

    }

    @Override
    public void show_get_notify(ResponGetnotify obj) {

    }
}
