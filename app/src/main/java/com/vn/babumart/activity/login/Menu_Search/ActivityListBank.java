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
import com.vn.babumart.adapter.AdapterListBankName;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.models.ObjBankName;
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

public class ActivityListBank extends BaseActivity
        implements InterfaceSearchMenu.View {

    private List<ObjBankName> mListBank;
    private AdapterListBankName adapterService;
    @BindView(R.id.recycle_list_service)
    RecyclerView recycle_service;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.edt_search_appbar)
    EditText edt_search_service;
    List<ObjBankName> temp;
    String sUserId;
    PresenterSearchMenu mPresenter;

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
        for (ObjBankName d : mListBank) {
            String sName = StringUtil.removeAccent(d.getsNameBank().toLowerCase());
            String sInput = StringUtil.removeAccent(text.toLowerCase());
            if (sName.contains(sInput)) {
                //adding the element to filtered list
                temp.add(d);
            }
        }
        adapterService.updateList(temp);
        adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                setResult(RESULT_OK, new Intent());
                App.mBankName = (ObjBankName) item;
                finish();
            }
        });
    }

    private void init() {
        mListBank = new ArrayList<>();
        temp = new ArrayList<>();
        adapterService = new AdapterListBankName(temp, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        recycle_service.setNestedScrollingEnabled(false);
        recycle_service.setHasFixedSize(true);
        recycle_service.setLayoutManager(mLayoutManager);
        recycle_service.setItemAnimator(new DefaultItemAnimator());
        recycle_service.setAdapter(adapterService);
        adapterService.updateList(temp);
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
                setResult(RESULT_OK, new Intent());
                App.mBankName = (ObjBankName) item;
                finish();
            }
        });

    }

    private void initData() {
        mListBank.add(new ObjBankName("Ngân hàng Ngoại thương (Vietcombank)",
                "VIETCOMBANK", "1"));
        mListBank.add(new ObjBankName("Ngân hàng Công thương (Vietinbank)",
                "VIETINBANK", "2"));
        mListBank.add(new ObjBankName("Ngân hàng đầu tư và phát triển Việt Nam (BIDV)",
                "BIDV", "3"));
        mListBank.add(new ObjBankName("Ngân hàng Nông nghiệp (Agribank)",
                "AGRIBANK", "4"));
        mListBank.add(new ObjBankName("Ngân hàng TMCP Sài Gòn Thương Tín (SacomBank)",
                "SACOMBANK", "5"));
        mListBank.add(new ObjBankName("Ngân hàng Kỹ thương Việt Nam (TechcomBank)",
                "TECHCOMBANK", "6"));
        mListBank.add(new ObjBankName("Ngân hàng ACB",
                "ACB", "7"));
        mListBank.add(new ObjBankName("Ngân hàng Việt Nam Thịnh vượng (VPBank)",
                "VPBANK", "8"));
        mListBank.add(new ObjBankName("Ngân hàng Đông Á (DongABank)",
                "DONGABANK", "9"));
        mListBank.add(new ObjBankName("Ngân hàng EximBank",
                "EXIMBANK", "10"));
        mListBank.add(new ObjBankName("Ngân hàng Tiên Phong (TPBank)",
                "TPBANK", "11"));
        mListBank.add(new ObjBankName("Ngân hàng Ngoại thương (Vietcombank)",
                "VIETCOMBANK", "1"));
        mListBank.add(new ObjBankName("Ngân hàng Quốc dân (NCB)",
                "NCB", "12"));
        mListBank.add(new ObjBankName("Ngân hàng Đại Dương (OceanBank)",
                "OJB", "13"));
        mListBank.add(new ObjBankName("Ngân hàng Hàng Hải (MSBANK)",
                "MSBANK", "14"));
        mListBank.add(new ObjBankName("Ngân hàng HDBank",
                "HDBANK", "15"));
        mListBank.add(new ObjBankName("Ngân hàng Nam Á (NamABank)",
                "NAMABANK", "16"));
        mListBank.add(new ObjBankName("Ngân hàng Phương Đông (OCB)",
                "OCB", "17"));
        mListBank.add(new ObjBankName("Thẻ quốc tế Visa",
                "VISA", "18"));
        mListBank.add(new ObjBankName("Ngân hàng TMCP Sài Gòn (SCB)",
                "SCB", "19"));
        mListBank.add(new ObjBankName("Ngân hàng TNHH Indovina (IVB)",
                "IVB", "20"));
        mListBank.add(new ObjBankName("Ngân hàng thương mại cổ phần An Bình (ABBANK)",
                "ABBANK", "21"));
        mListBank.add(new ObjBankName("Ngân hàng Thương mại cổ phần Sài Gòn (SHB)",
                "SHB", "22"));
        mListBank.add(new ObjBankName("Ngân hàng Thương mại cổ phần Quốc tế Việt Nam (VIB)",
                "VIB", "23"));
        mListBank.add(new ObjBankName("Ngân Hàng Bản Việt",
                "VIETCAPITALBANK", "24"));
        mListBank.add(new ObjBankName("Ngân hàng TMCP Đại Chúng Việt Nam",
                "PVCOMBANK", "25"));
        mListBank.add(new ObjBankName("Ngân hàng thương mại cổ phần Sài Gòn Công Thương",
                "SAIGONBANK", "26"));
        mListBank.add(new ObjBankName("Ngân hàng thương mại cổ phần Quân đội",
                "MBBANK", "27"));
        mListBank.add(new ObjBankName("Ngân Hàng TMCP Bắc Á",
                "BACABANK", "28"));
        mListBank.add(new ObjBankName(" Ngân Hàng Shinhan Bank",
                "SHINHANBANK", "29"));
        temp.addAll(mListBank);
        adapterService.notifyDataSetChanged();
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


    }

    @Override
    public void show_api_get_district(ResponGetDistrict obj) {

    }

    @Override
    public void show_api_get_ward(ResponGetWard obj) {

    }

}
