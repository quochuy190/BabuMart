package com.vn.babumart.activity.login.Menu_Search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.babumart.App;
import com.vn.babumart.R;
import com.vn.babumart.adapter.AdapterListDistricts;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.Districts;
import com.vn.babumart.models.respon_api.ResponGetDistrict;
import com.vn.babumart.models.respon_api.ResponGetProvince;
import com.vn.babumart.models.respon_api.ResponGetWard;
import com.vn.babumart.untils.KeyboardUtil;
import com.vn.babumart.untils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Created by QQ on 10/16/2017.
 */

public class ActivityDistrict extends BaseActivity
        implements InterfaceSearchMenu.View {

    private List<Districts> mList;
    private AdapterListDistricts adapterService;
    @BindView(R.id.recycle_list_service)
    RecyclerView recycle_service;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.edt_search_appbar)
    EditText edt_search_service;
    List<Districts> temp;
    String sUserId;
    PresenterSearchMenu mPresenter;
    String sIdCity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.fragment_service);
        ButterKnife.bind(this);
        KeyboardUtil.hideSoftKeyboard(this);
        //  initData();
        mPresenter = new PresenterSearchMenu(this);
        //initAppbar();
        init();
        initData();
        initEvent();
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_recycleview;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /*  @Nullable
          @Override
          public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
              View view= inflater.inflate(R.layout.fragment_service, container, false);


              view.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                  }
              });
              return view;
          }
      */
    private void initEvent() {
        edt_search_service.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });


    }

    void filter(String text) {
        temp.clear();
        for (Districts d : mList) {
            String sName = StringUtil.removeAccent(d.getNAME().toLowerCase());
            String sInput = StringUtil.removeAccent(text.toLowerCase());
            if (sName.contains(sInput)) {
                //adding the element to filtered list
                temp.add(d);
            }
            /*if (d.getNAME().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                temp.add(d);
            }
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getNAME().contains(text)) {

            }*/
        }
        //update recyclerview
        adapterService.updateList(temp);
        adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                setResult(RESULT_OK, new Intent());
                App.mDistrict = (Districts) item;
                finish();
            }
        });
    }

    private void init() {
        mList = new ArrayList<>();
        temp = new ArrayList<>();
        adapterService = new AdapterListDistricts(temp, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        recycle_service.setNestedScrollingEnabled(false);
        recycle_service.setHasFixedSize(true);
        recycle_service.setLayoutManager(mLayoutManager);
        recycle_service.setItemAnimator(new DefaultItemAnimator());
        recycle_service.setAdapter(adapterService);
        adapterService.updateList(temp);
        /*adapterService.setOnIListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                App.mDistrict = mLisAirport.get(position);
                finish();

            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });*/
        adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                setResult(RESULT_OK, new Intent());
                App.mDistrict = (Districts) item;
                finish();
            }
        });

    }

    private void initData() {
        sIdCity = getIntent().getStringExtra(Constants.KEY_SEND_ID_CITY);
        if (sIdCity != null && sIdCity.length() > 0) {
            showDialogLoading();
            mPresenter.api_get_district(sIdCity);
        } else {
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void show_error_api() {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_api_get_city(ResponGetProvince objRespon) {
        hideDialogLoading();
    }

    @Override
    public void show_api_get_district(ResponGetDistrict obj) {
        hideDialogLoading();
        if (obj.getsERROR().equals("0000")) {
            if (obj.getLisDistrict() != null) {
                temp.clear();
                mList.addAll(obj.getLisDistrict());
                temp.addAll(mList);
                adapterService.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void show_api_get_ward(ResponGetWard obj) {

    }


}
