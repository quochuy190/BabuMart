package com.vn.babumart.activity.order;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.vn.babumart.R;
import com.vn.babumart.activity.products.InterfaceProperties;
import com.vn.babumart.activity.products.PresenterProperties;
import com.vn.babumart.adapter.AdapterProductInHistoryOrder;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.callback.ItemClickCartListener;
import com.vn.babumart.config.Config;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.CurrencyEditText;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.ObjCTV;
import com.vn.babumart.models.ObjConfigCommis;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.models.ObjOrder;
import com.vn.babumart.models.Products;
import com.vn.babumart.models.PropetiObj;
import com.vn.babumart.models.respon_api.ResponGetProduct;
import com.vn.babumart.models.respon_api.ResponGetPropeti;
import com.vn.babumart.models.respon_api.ResponHistoryOrder;
import com.vn.babumart.untils.KeyboardUtil;
import com.vn.babumart.untils.SharedPrefs;
import com.vn.babumart.untils.StringUtil;
import com.vn.babumart.untils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 03-June-2019
 * Time: 08:23
 * Version: 1.0
 */
public class ActivityHistoryOrderDetail extends BaseActivity
        implements InterfaceOrder.View, View.OnClickListener, InterfaceProperties.View {
    private static final String TAG = "ActivityHistoryOrderDet";
    AdapterProductInHistoryOrder adapterProduct;
    RecyclerView.LayoutManager mLayoutManagerProduct;
    List<Products> mLisCateProduct;
    @BindView(R.id.rcv_product_in_order_detail)
    RecyclerView recycle_lis_product;
    @BindView(R.id.txt_price)
    TextView txt_price;
    ObjOrder objOrder;
    String sUserName;
    ObjLogin mLogin;
    private PresenterOrder mPresenter;
    private PresenterProperties mPresenterProperties;
    private int sPrice;
    @BindView(R.id.txt_nguoinhan)
    TextView txt_nguoinhan;
    @BindView(R.id.txt_user)
    TextView txt_user;
    @BindView(R.id.txt_email)
    TextView txt_email;
    @BindView(R.id.txt_time)
    TextView txt_time;
    @BindView(R.id.txt_phone_ngnhan)
    TextView txt_phone_ngnhan;
    @BindView(R.id.txt_address_ngnhan)
    TextView txt_address_ngnhan;
    @BindView(R.id.txt_name)
    TextView txt_name;
    @BindView(R.id.txt_item_order_status)
    TextView txt_item_order_status;
    @BindView(R.id.ic_edit_blue)
    ImageView ic_edit_blue;
    @BindView(R.id.ic_edit_green)
    ImageView ic_edit_green;
    @BindView(R.id.ic_edit_product)
    ImageView ic_edit_product;
    @BindView(R.id.txt_total)
    TextView txt_total;
    @BindView(R.id.txt_time_dukien)
    TextView txt_time_dukien;
    @BindView(R.id.txt_ship)
    EditText txt_ship;
    @BindView(R.id.edt_gopy)
    EditText edt_gopy;
    @BindView(R.id.edt_discount)
    CurrencyEditText edt_discount;
    @BindView(R.id.btn_update)
    Button btn_update;
    @BindView(R.id.txt_hoahong)
    TextView txt_hoahong;
    @BindView(R.id.txt_code_order)
    TextView txt_code_order;

    @Override
    public int setContentViewId() {
        return R.layout.activity_order_history_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterOrder(this);
        mPresenterProperties = new PresenterProperties(this);
        initAppbar();
        initProduct();
        initBottom();
        get_all_history();
        initData();
        initEvent();
    }

    public void get_all_history() {
        myCalendar_to.add(Calendar.DAY_OF_MONTH, 4);
        updateTodate();
    }

    private void initEvent() {
        btn_comfirm.setOnClickListener(this);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_api_order();
            }
        });
        txt_time_dukien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ActivityHistoryOrderDetail.this, R.style.MyDatePickerStyle,
                        to_date, myCalendar_to
                        .get(Calendar.YEAR), myCalendar_to.get(Calendar.MONTH),
                        myCalendar_to.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        ic_edit_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogStatus();
            }
        });
        txt_item_order_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  if (mLogin.getGROUPS().equals("5") && objOrder.getSTATUS().equals("1")) {
                    if (mLisCateProduct.size() > 0) {
                        for (Products obj : mLisCateProduct) {
                            obj.setVisibleButtonAdd(true);
                        }
                        adapterProduct.notifyDataSetChanged();
                    }
                }*/
                showDialogStatus();
            }
        });
        txt_ship.addTextChangedListener(new TextWatcher() {
            private boolean lock;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // txt_ship.setText(StringUtil.conventMonney_Long(s.toString()));
            }
        });
        edt_discount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!isDelay) {
                    isDelay = true;
                    //txt_commission.setText(edt_price_distcount.getText().toString());
                    if (edt_discount.length() > 0)
                        set_price_total();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isDelay = false;
                        }
                    }, 100);
                }

            }
        });
    }

    boolean isDelay = false;
    String sStatusStart = "";

    private void initData() {
        mLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        objOrder = (ObjOrder) getIntent().getSerializableExtra(Constants.KEY_SAND_OBJ_ORDER);
        sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        if (objOrder != null && objOrder.getCODE_ORDER() != null) {
            showDialogLoading();
            sStatus = objOrder.getSTATUS();
            mPresenter.api_get_order_history_detail(sUserName, objOrder.getCODE_ORDER());
            mPresenter.api_get_order_history_detail_pd(sUserName, objOrder.getCODE_ORDER());
            set_bg_status();
        }
        if (objOrder.getDISCOUNT() != null && objOrder.getDISCOUNT().length() > 0) {
        /*    edt_discount.setText(objOrder.getDISCOUNT());
            Log.e(TAG, "getDISCOUNT: "+objOrder.getDISCOUNT());*/
            edt_discount.setText(StringUtil.conventMonney_Long(objOrder.getDISCOUNT()));
            //  edt_discount.setText("" + objOrder.getDISCOUNT());
        }
        if (objOrder.getDISTCOUNT() != null && objOrder.getDISTCOUNT().length() > 0) {
            //  edt_discount.setText(objOrder.getDISTCOUNT());
            edt_discount.setText(StringUtil.conventMonney_Long(objOrder.getDISTCOUNT()));
            //     Log.e(TAG, "getDISTCOUNT 2: "+objOrder.getDISTCOUNT());
        }
        if (!mLogin.getGROUPS().equals(Config.GROUP_CONGTACVIEN)
                || !mLogin.getGROUPS().equals(Config.GROUP_KHACHHANG)) {
            if (sStatus.equals("4") || sStatus.equals("0")) {
                ic_edit_green.setEnabled(false);
                txt_time_dukien.setEnabled(false);
                txt_item_order_status.setEnabled(false);
                ic_edit_blue.setEnabled(false);
                txt_ship.setFocusable(false);
                edt_gopy.setFocusable(false);
                txt_time_dukien.setFocusable(false);
                btn_update.setVisibility(View.GONE);
            } else {
                ic_edit_green.setEnabled(true);
                txt_ship.setFocusable(true);
                edt_gopy.setFocusable(true);
                txt_time_dukien.setFocusable(true);
                txt_time_dukien.setEnabled(true);
                txt_item_order_status.setEnabled(true);
                ic_edit_blue.setEnabled(true);
                btn_update.setVisibility(View.VISIBLE);
            }
        } else {
            if (sStatus.equals("1")) {
                btn_update.setVisibility(View.VISIBLE);
            } else {
                ic_edit_green.setEnabled(false);
                txt_time_dukien.setEnabled(false);
                txt_item_order_status.setEnabled(false);
                ic_edit_blue.setEnabled(false);
                txt_ship.setFocusable(false);
                edt_gopy.setFocusable(false);
                txt_time_dukien.setFocusable(false);
                btn_update.setVisibility(View.GONE);
            }
            ic_edit_blue.setEnabled(false);
            txt_time_dukien.setEnabled(false);
            txt_ship.setFocusable(false);
            edt_gopy.setFocusable(false);
            txt_time_dukien.setFocusable(false);
        }
    }

    private void set_bg_status() {
        switch (objOrder.getSTATUS()) {
            case "0":
                txt_item_order_status.setText("Đã hoàn thành");
                txt_item_order_status.setBackground(getResources()
                        .getDrawable(R.drawable.spr_txt_status_hoanthanh));
                break;
            case "1":
                txt_item_order_status.setText("Đã tiếp nhận");
                txt_item_order_status.setBackground(getResources()
                        .getDrawable(R.drawable.spr_txt_status_datiepnhan));
                break;
            case "2":
                txt_item_order_status.setText("Đang xử lý");
                txt_item_order_status.setBackground(getResources()
                        .getDrawable(R.drawable.spr_txt_status_dangxuly));
                break;
            case "3":
                txt_item_order_status.setText("Đang vận chuyển");
                txt_item_order_status.setBackground(getResources()
                        .getDrawable(R.drawable.spr_txt_status_dangxuly));
                break;
            case "4":
                txt_item_order_status.setText("Đã huỷ");
                txt_item_order_status.setBackground(getResources()
                        .getDrawable(R.drawable.spr_txt_status_dahuy));
                break;

        }
    }

    private void set_bg_status_click() {
        switch (sStatus) {
            case "0":
                txt_item_order_status.setText("Đã hoàn thành");
                txt_item_order_status.setBackground(getResources()
                        .getDrawable(R.drawable.spr_txt_status_hoanthanh));
                break;
            case "1":
                txt_item_order_status.setText("Đã tiếp nhận");
                txt_item_order_status.setBackground(getResources()
                        .getDrawable(R.drawable.spr_txt_status_datiepnhan));
                break;
            case "2":
                txt_item_order_status.setText("Đang xử lý");
                txt_item_order_status.setBackground(getResources()
                        .getDrawable(R.drawable.spr_txt_status_dangxuly));
                break;
            case "3":
                txt_item_order_status.setText("Đang vận chuyển");
                txt_item_order_status.setBackground(getResources()
                        .getDrawable(R.drawable.spr_txt_status_dangxuly));
                break;
            case "4":
                txt_item_order_status.setText("Đã huỷ");
                txt_item_order_status.setBackground(getResources()
                        .getDrawable(R.drawable.spr_txt_status_dahuy));
                break;

        }
    }

    Products mProduct;

    private void initProduct() {
        mLisCateProduct = new ArrayList<>();
        adapterProduct = new AdapterProductInHistoryOrder(mLisCateProduct, this);
        mLayoutManagerProduct = new GridLayoutManager(this, 1);
        recycle_lis_product.setHasFixedSize(true);
        recycle_lis_product.setLayoutManager(mLayoutManagerProduct);
        recycle_lis_product.setItemAnimator(new DefaultItemAnimator());
        recycle_lis_product.setAdapter(adapterProduct);
        adapterProduct.notifyDataSetChanged();
        adapterProduct.setOnIListener(new ItemClickCartListener() {
            @Override
            public void onClickItem(int position, Object item) {
                mProduct = (Products) item;
             /*   showDialogLoading();
                if (mProduct.getID_PRODUCT_PROPERTIES() != null && mProduct.getID_PRODUCT_PROPERTIES().length() > 0) {
                    mPresenterProperties.api_get_properties(sUserName, mProduct.getID_PRODUCT_PROPERTIES());
                }*/
            }

            @Override
            public void onClickItem_Add(int position, Object item) {
              /*  Products objPro = (Products) item;
                Integer iQuantum = 0;
                if (objPro.getNUM() != null && objPro.getNUM().length() > 0) {
                    iQuantum = Integer.parseInt(objPro.getNUM());
                }
                iQuantum++;
                mLisCateProduct.get(position).setNUM("" + iQuantum);
                adapterProduct.notifyDataSetChanged();
                set_price_total();*/
            }

            @Override
            public void onClickItem_Minus(int position, Object item) {
                Products obj = (Products) item;
              /*  Integer iQuantum = 0;
                String s = "";
                if (obj != null && obj.getNUM() != null && obj.getNUM().length() > 0) {
                    s = obj.getNUM();
                    iQuantum = Integer.parseInt(s);
                }
                if (iQuantum > 0) {
                    iQuantum--;
                    mLisCateProduct.get(position).setNUM("" + iQuantum);
                } else {
                    mLisCateProduct.get(position).setNUM("" + iQuantum);
                }
                adapterProduct.notifyDataSetChanged();
                set_price_total();*/
            }
        });
    }

    long extraShip = 0;
    private float hh_ctv = 0;

    private void set_price_total() {
        ObjCTV objCTV = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJ_CTV_APPLICATION, ObjCTV.class);
        if (objCTV.getCOMISSION() != null && objCTV.getCOMISSION().length() > 0) {
            hh_ctv = Float.parseFloat(objCTV.getCOMISSION());
        }
        long value_max = 0;
        int hh_max = 0;
        long lPrice = 0;
        int value = 0;
        long lCommission = 0;
        for (Products obj : mLisCateProduct) {
            if (obj != null && obj.getNUM() != null && obj.getNUM().length() > 0 && obj.getsPrice() != null) {
                lPrice = lPrice + (Integer.parseInt(obj.getNUM()) * Integer.parseInt(obj.getsPrice()));
                if (obj.getCOMISSION_PRODUCT() != null && obj.getCOMISSION_PRODUCT().length() > 0) {
                    String hh = obj.getCOMISSION_PRODUCT().replace(",", ".");
                    float hh_product = Float.parseFloat(hh);
                    long price_total = Long.parseLong(obj.getsPrice());
                    long value_hh = (long) (hh_product * price_total * Integer.parseInt(obj.getNUM()));
                  /*  long pire = Float.parseFloat(obj.getCOMISSION_PRODUCT()) *
                            (Integer.parseInt(obj.getsQuantum()) * Integer.parseInt(obj.getsPrice()));*/
                    lCommission = lCommission + (value_hh / 100);
                }
            }
        }
        if (lPrice > 0) {
            if (Config.mLisConfigCommis != null && Config.mLisConfigCommis.size() > 0) {
                for (ObjConfigCommis obj : Config.mLisConfigCommis) {
                    long down = 0;
                    long up = 0;
                    if (obj.getDISCOUNT_DOWN() != null) {
                        down = Long.parseLong(obj.getDISCOUNT_DOWN());
                    }
                    if (obj.getDISCOUNT_UP() != null) {
                        up = Long.parseLong(obj.getDISCOUNT_UP());
                        if (up > value_max) {
                            value_max = up;
                            if (obj.getVALUE() != null)
                                hh_max = Integer.parseInt(obj.getVALUE());
                        }
                    }
                    if (lPrice > down && lPrice <= up && obj.getVALUE() != null) {
                        value = Integer.parseInt(obj.getVALUE());
                        break;
                    }
                }
                if (lPrice > value_max)
                    value = hh_max;
                if (value > 0) {
                    long hh_donhang = value * lPrice / 100;
                    lCommission = lCommission + hh_donhang;
                }
            }

            lCommission = (long) (lCommission + ((hh_ctv * lPrice) / 100));

        } else {
            txt_hoahong.setText("0đ");
        }
        if (StringUtil.replace_all(edt_discount.getText().toString()).length() > 0) {
            int distcount = Integer.parseInt(edt_discount.getText().toString().trim().replaceAll(",", "")
                    .replaceAll("đ", "").replaceAll("\\.", ""));

            if (distcount <= lCommission) {
                txt_hoahong.setText(StringUtil.conventMonney_Long("" + (lCommission - distcount)));
                //  txt_value_total.setText(StringUtil.conventMonney_Long("" + lPrice));
                txt_price.setText(StringUtil.conventMonney_Long("" + (lPrice)));
                txt_total.setText(StringUtil.conventMonney_Long("" + (lPrice + extraShip - distcount)));
            } else {
                KeyboardUtil.dismissKeyboard(edt_discount);
                txt_hoahong.setText("0đ");
                txt_price.setText(StringUtil.conventMonney_Long("" + (lPrice - lCommission)));
                long hhong = lCommission;
              /*  new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        edt_discount.setText(StringUtil.formatNumber_new("" + hhong));
                        edt_discount.setText("" + hhong);
                    }
                }, 150);*/
            }
        } else {
            txt_hoahong.setText(StringUtil.conventMonney_Long("" + lCommission));
            //  txt_value_total.setText(StringUtil.conventMonney_Long("" + lPrice));
            txt_price.setText(StringUtil.conventMonney_Long("" + lPrice));
            txt_total.setText(StringUtil.conventMonney_Long("" + (lPrice + extraShip)));
        }


    }

    @Override
    public void show_error_api() {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_get_properties(ResponGetPropeti obj) {
        hideDialogLoading();
        if (obj != null && obj.getsERROR().equals("0000")) {
            if (obj.getLisDistrict() != null) {
                mProduct.setmListThuoctinhTong(obj.getLisDistrict());
                show_Bottom_Dialog(mProduct);
            }
        } else showDialogNotify("Thông báo", obj.getsRESULT());
    }

    @Override
    public void show_order_history(ResponHistoryOrder obj) {

    }

    @Override
    public void show_order_history_detail(ObjOrder objOrder) {
        hideDialogLoading_delay();
        try {
            if (objOrder != null && objOrder.getERROR().equals("0000")) {
                if (objOrder.getEXTRA_SHIP() != null && objOrder.getEXTRA_SHIP().length() > 0) {
                    txt_ship.setText(StringUtil.conventMonney_Long(objOrder.getEXTRA_SHIP()));
                    txt_ship.setFocusable(false);
                    extraShip = Long.parseLong(objOrder.getEXTRA_SHIP());
                }
               /* if (objOrder.getDISCOUNT() != null && objOrder.getDISCOUNT().length() > 0) {
                    edt_discount.setText(objOrder.getDISCOUNT());
                    Log.e(TAG, "initData: "+objOrder.getDISCOUNT());
                    // edt_discount.setText(StringUtil.formatNumber_new(objOrder.getDISCOUNT()));
                    //  edt_discount.setText("" + objOrder.getDISCOUNT());
                }
                if (objOrder.getDISTCOUNT() != null && objOrder.getDISTCOUNT().length() > 0) {
                    edt_discount.setText(objOrder.getDISTCOUNT());
                    Log.e(TAG, "initData: "+objOrder.getDISTCOUNT());
                }*/
                if (objOrder.getNOTE() != null)
                    edt_gopy.setText(objOrder.getNOTE());
                if (objOrder.getFULL_NAME_CTV() != null) {
                    txt_name.setText("Tên CTV: " + objOrder.getFULL_NAME_CTV());
                } else {
                    txt_name.setText("Tên CTV: ...");
                }
                if (objOrder.getID_CODE_ORDER() != null) {
                    txt_code_order.setText("Mã ĐH: " + objOrder.getID_CODE_ORDER());
                } else {
                    txt_code_order.setText("Mã ĐH: ...");
                }
                if (objOrder.getUSER_CODE() != null) {
                    txt_user.setText("Mã CTV: " + objOrder.getUSER_CODE());
                } else {
                    txt_user.setText("Mã CTV: ...");
                }
                if (objOrder.getEMAIL() != null) {
                    txt_email.setText("Email: " + objOrder.getEMAIL());
                } else {
                    txt_email.setText("Email: ...");
                }
                if (objOrder.getCREATE_DATE() != null) {
                    txt_time.setText(TimeUtils.convent_date(objOrder.getCREATE_DATE(),
                            "dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy HH:mm"));
                } else {
                    txt_time.setText("...");
                }
                if (objOrder.getTIME_RECEIVER() != null && objOrder.getTIME_RECEIVER().length() > 0) {
                    txt_time_dukien.setText(TimeUtils.convent_date(objOrder.getTIME_RECEIVER(),
                            "dd/MM/yyyy", "dd/MM/yyyy"));
                } else {
                    txt_time_dukien.setText("");
                }
                if (objOrder.getFULLNAME_RECEIVER() != null) {
                    sFullName = objOrder.getFULLNAME_RECEIVER();
                    txt_nguoinhan.setText(objOrder.getFULLNAME_RECEIVER());
                } else {
                    sFullName = "";
                    txt_nguoinhan.setText("...");
                }
                if (objOrder.getMOBILE_RECEIVER() != null) {
                    sPhone = objOrder.getMOBILE_RECEIVER();
                    txt_phone_ngnhan.setText(objOrder.getMOBILE_RECEIVER());
                } else {
                    txt_phone_ngnhan.setText("...");
                }
                if (objOrder.getADDRESS_RECEIVER() != null) {
                    sAddress = objOrder.getADDRESS_RECEIVER();
                    txt_address_ngnhan.setText(objOrder.getADDRESS_RECEIVER());
                } else {
                    txt_address_ngnhan.setText("...");
                }
                if (objOrder.getID_CODE_ORDER() != null)
                    sCode_Order = objOrder.getID_CODE_ORDER();
                if (objOrder.getCREATE_DATE() != null) {
                    sTimeReceiver = objOrder.getCREATE_DATE();
                }
                if (objOrder.getID_CITY() != null)
                    sCity = objOrder.getID_CITY();
                if (objOrder.getID_DISTRICT() != null)
                    sDistrict = objOrder.getID_DISTRICT();
                if (objOrder.getSTATUS() != null) {
                    switch (objOrder.getSTATUS()) {
                        case "0":
                            txt_item_order_status.setText(objOrder.getSTATUS_NAME());
                            sStatus = "0";
                            break;
                        case "1":
                            sStatus = "1";
                            txt_item_order_status.setText(objOrder.getSTATUS_NAME());

                            break;
                        case "2":
                            sStatus = "2";
                            txt_item_order_status.setText(objOrder.getSTATUS_NAME());
                            break;
                        case "3":
                            sStatus = "3";
                            txt_item_order_status.setText(objOrder.getSTATUS_NAME());
                            break;
                        case "4":
                            sStatus = "4";
                            txt_item_order_status.setText(objOrder.getSTATUS_NAME());
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    Calendar myCalendar_to = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener to_date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar_to.set(Calendar.YEAR, year);
            myCalendar_to.set(Calendar.MONTH, monthOfYear);
            myCalendar_to.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateTodate();
        }
    };

    private void updateTodate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_time_dukien.setText(sdf.format(myCalendar_to.getTime()));
    }

    @Override
    public void show_order_history_detail_pd(ResponGetProduct obj) {
        hideDialogLoading_delay();
        if (obj.getsERROR().equals("0000")) {
            mLisCateProduct.clear();
            if (obj.getmList() != null) {
                mLisCateProduct.addAll(obj.getmList());
            }
            set_click_edit();
            set_price_total();
          /*  for (Products objpro : mLisCateProduct) {
                if (objpro.getMONEY() != null && objpro.getMONEY().length() > 0) {
                    sPrice = sPrice + Integer.parseInt(objpro.getMONEY());
                }
            }
            txt_price.setText(StringUtil.conventMonney_Long("" + sPrice));*/
            adapterProduct.notifyDataSetChanged();
        }
    }

    private void set_click_edit() {
        if (sStatus.equals("1")) {
            for (Products objPro : mLisCateProduct) {
                objPro.setVisibleButtonAdd(true);
            }
        } else {
            for (Products objPro : mLisCateProduct) {
                objPro.setVisibleButtonAdd(false);
            }
        }

    }

    @Override
    public void show_edit_order_product(ErrorApi obj) {
        hideDialogLoading();
        if (obj.getsERROR().equals("0000")) {
            Toast.makeText(this, "Cập nhật đơn hàng thành công", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, new Intent());
            finish();
        } else showDialogNotify("Thông báo", obj.getsRESULT());
    }

    @Override
    public void show_order_product(ErrorApi obj) {

    }

    @Override
    public void show_config_commission(ErrorApi obj) {

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
        txt_title.setText("Đặt hàng");
    }

    Dialog dialog_yes;

    public void showDialogStatus() {
        dialog_yes = new Dialog(this, R.style.Theme_Dialog);
        dialog_yes.setCancelable(false);
        dialog_yes.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_yes.setContentView(R.layout.dialog_selected_status);
        dialog_yes.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView txt_title = (TextView) dialog_yes.findViewById(R.id.txt_warning_title);
        TextView txt_dangchoduyet = (TextView) dialog_yes.findViewById(R.id.txt_dangchoduyet);
        TextView txt_dangchuyen = (TextView) dialog_yes.findViewById(R.id.txt_dangchuyen);
        TextView txt_hoanthanh = (TextView) dialog_yes.findViewById(R.id.txt_hoanthanh);
        TextView txt_huy = (TextView) dialog_yes.findViewById(R.id.txt_huy);
        TextView txt_back = (TextView) dialog_yes.findViewById(R.id.txt_back);
        TextView txt_datiepnhan = (TextView) dialog_yes.findViewById(R.id.txt_datiepnhan);

        txt_title.setOnClickListener(this);
        txt_dangchoduyet.setOnClickListener(this);
        txt_dangchuyen.setOnClickListener(this);
        txt_hoanthanh.setOnClickListener(this);
        txt_huy.setOnClickListener(this);
        txt_back.setOnClickListener(this);
        txt_datiepnhan.setOnClickListener(this);
        if (mLogin.getGROUPS().equals(Config.GROUP_CONGTACVIEN)
                || mLogin.getGROUPS().equals(Config.GROUP_KHACHHANG)) {
            txt_dangchoduyet.setVisibility(View.GONE);
            txt_dangchuyen.setVisibility(View.GONE);
            txt_hoanthanh.setVisibility(View.GONE);
            txt_datiepnhan.setVisibility(View.VISIBLE);
        } else {
            if (objOrder.getSTATUS().equals("1")) {
                txt_dangchoduyet.setVisibility(View.VISIBLE);
                txt_dangchuyen.setVisibility(View.VISIBLE);
                txt_hoanthanh.setVisibility(View.VISIBLE);
                txt_datiepnhan.setVisibility(View.VISIBLE);
            } else if (objOrder.getSTATUS().equals("2")) {
                txt_dangchoduyet.setVisibility(View.VISIBLE);
                txt_dangchuyen.setVisibility(View.VISIBLE);
                txt_hoanthanh.setVisibility(View.VISIBLE);
                txt_datiepnhan.setVisibility(View.GONE);
            } else if (objOrder.getSTATUS().equals("3")) {
                txt_dangchoduyet.setVisibility(View.GONE);
                txt_dangchuyen.setVisibility(View.VISIBLE);
                txt_hoanthanh.setVisibility(View.VISIBLE);
                txt_datiepnhan.setVisibility(View.GONE);
            } else if (objOrder.getSTATUS().equals("4")) {
                txt_dangchoduyet.setVisibility(View.GONE);
                txt_dangchuyen.setVisibility(View.GONE);
                txt_hoanthanh.setVisibility(View.GONE);
                txt_datiepnhan.setVisibility(View.GONE);
            }
        }
        dialog_yes.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_dangchoduyet:
                txt_item_order_status.setText("Đang xử lý");
                sStatus = "2";
                set_click_edit();
                adapterProduct.notifyDataSetChanged();
                dialog_yes.dismiss();
                set_bg_status_click();
                break;
            case R.id.txt_datiepnhan:
                txt_item_order_status.setText("Đã tiếp nhận");
                dialog_yes.dismiss();
                sStatus = "1";
                set_click_edit();
                adapterProduct.notifyDataSetChanged();
                set_bg_status_click();
                break;
            case R.id.txt_dangchuyen:
                txt_item_order_status.setText("Đang vận chuyển");
                sStatus = "3";
                set_click_edit();
                adapterProduct.notifyDataSetChanged();
                dialog_yes.dismiss();
                set_bg_status_click();
                break;
            case R.id.txt_hoanthanh:
                sStatus = "0";
                set_click_edit();
                adapterProduct.notifyDataSetChanged();
                txt_item_order_status.setText("Đã hoàn thành");
                dialog_yes.dismiss();
                set_bg_status_click();
                break;
            case R.id.txt_huy:
                sStatus = "4";
                set_click_edit();
                adapterProduct.notifyDataSetChanged();
                txt_item_order_status.setText("Huỷ đơn");
                dialog_yes.dismiss();
                set_bg_status_click();
                break;
            case R.id.txt_back:
                dialog_yes.dismiss();
                break;
            case R.id.btn_comfirm:
                mBottomSheetDialog.dismiss();
                for (Products obj : mLisCateProduct) {
                    if (obj.getCODE_PRODUCT().equals(mProductClick.getCODE_PRODUCT())) {
                        String sProperties = "";
                        List<PropetiObj.PropetiDetail> mListPrppeti = new ArrayList<>();
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
                            obj.setmLisPropeti(mListPrppeti);
                        if (sProperties.length() > 0) {
                            sProperties = sProperties.substring(0, sProperties.length() - 1);
                            obj.setsThuoctinh(sProperties);
                        }
                    }
                }
                Log.e("abc", "onClick: " + mLisCateProduct.size());
                // set_price_total();
                adapterProduct.notifyDataSetChanged();
                break;
        }
    }

    String sTimeExerShip = "", sStatus = "", sFullName = "", sPhone = "", sAddress = "", sCity = "", sDistrict = "",
            sUsername = "", sCODE_PRODUCT = "", sAMOUNT = "", sPRICE = "", sMONEY = "", sBONUS = "",
            sCode_Order = "", sTimeReceiver = "", sNote = "";
    String sThuoctinh = "", sDISTCOUNT = "";

    private void get_api_order() {
        if (mLisCateProduct != null && mLisCateProduct.size() > 0) {
            for (int i = 0; i < mLisCateProduct.size(); i++) {
                Products obj = mLisCateProduct.get(i);
                if (mLisCateProduct.get(i).getNUM() != null && mLisCateProduct.get(i).getNUM().length() > 0) {
                    int iQuantum = Integer.parseInt(mLisCateProduct.get(i).getNUM());
                    if (iQuantum > 0) {
                        if (obj.getmLisPropeti() != null && obj.getmLisPropeti().size() > 0) {
                            String sPro = "";
                            for (PropetiObj.PropetiDetail objPropeti : obj.getmLisPropeti()) {
                                sPro = sPro + objPropeti.getSUB_ID() + ",";
                            }
                            sThuoctinh = sThuoctinh + sPro.substring(0, sPro.length() - 1) + "#";
                        } else if (obj.getOD_PRODUCT_PROPERTIES() != null && obj.getOD_PRODUCT_PROPERTIES().length() > 0) {
                            sThuoctinh = sThuoctinh + obj.getOD_PRODUCT_PROPERTIES() + "#";
                        }
                        sCODE_PRODUCT = sCODE_PRODUCT + mLisCateProduct.get(i).getCODE_PRODUCT() + "#";
                        sAMOUNT = sAMOUNT + mLisCateProduct.get(i).getNUM() + "#";
                        sPRICE = sPRICE + mLisCateProduct.get(i).getsPrice() + "#";
                        sMONEY = sMONEY + (iQuantum * (Integer.parseInt(mLisCateProduct.get(i).getsPrice()))) + "#";
                        if (mLisCateProduct.get(i).getCOMMISSION() != null) {
                            long commission = (long) ((Integer.parseInt(mLisCateProduct.get(i).getNUM())
                                    * Float.parseFloat(mLisCateProduct.get(i).getCOMMISSION())
                                    * Long.parseLong(mLisCateProduct.get(i).getsPrice())) / 100);
                            sBONUS = sBONUS + commission + "#";
                        }
                    }
                }
            }
        }
        sUsername = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        if (sThuoctinh.length() > 1) {
            sThuoctinh = sThuoctinh.substring(0, (sThuoctinh.length() - 1));
        }
        if (sCODE_PRODUCT.length() > 1)
            sCODE_PRODUCT = sCODE_PRODUCT.substring(0, (sCODE_PRODUCT.length() - 1));
        if (sBONUS.length() > 1)
            sBONUS = sBONUS.substring(0, (sBONUS.length() - 1));
        if (sPRICE.length() > 1)
            sPRICE = sPRICE.substring(0, (sPRICE.length() - 1));
        if (sAMOUNT.length() > 1)
            sAMOUNT = sAMOUNT.substring(0, (sAMOUNT.length() - 1));
        if (sMONEY.length() > 1)
            sMONEY = sMONEY.substring(0, (sMONEY.length() - 1));
        sTimeExerShip = txt_time_dukien.getText().toString();
        if (edt_gopy.getText().toString().length() > 0) {
            sNote = edt_gopy.getText().toString();
        } else
            sNote = "";
        String sExtraShip = txt_ship.getText().toString().replaceAll("VND", "");
        sExtraShip = sExtraShip.trim().replaceAll("đ", "");
        sExtraShip = sExtraShip.trim().replaceAll(",", "");
        if (edt_discount.getText().toString().length() > 0) {
            sDISTCOUNT = edt_discount.getText().toString();
            sDISTCOUNT = StringUtil.replace_all(sDISTCOUNT);
        }
        showDialogLoading();
        mPresenter.api_edit_order_product(sUsername, sCODE_PRODUCT, sAMOUNT, sPRICE, sMONEY, sBONUS,
                sFullName, sPhone, sCity, sDistrict, sAddress, sCode_Order, sStatus, sExtraShip,
                sTimeExerShip, sNote, sThuoctinh, sDISTCOUNT);
    }

    BottomSheetDialog mBottomSheetDialog;
    ImageView img_product_dialog;
    TextView txt_name_pro_dialog;
    TextView txt_price_pro_dialog;
    TextView txt_code_pro_dialog;
    Button btn_comfirm;
    ConstraintLayout ll_spinner_1, ll_spinner_2, ll_spinner_3, ll_spinner_4;
    TextView txt_title_spinner_1, txt_title_spinner_2, txt_title_spinner_3, txt_title_spinner_4;
    List<String> data_spinner_1, data_spinner_2, data_spinner_3, data_spinner_4;
    Spinner spiner_type_1, spiner_type_2, spiner_type_3, spiner_type_4;

    private void initBottom() {
        data_spinner_1 = new ArrayList<>();
        data_spinner_2 = new ArrayList<>();
        data_spinner_3 = new ArrayList<>();
        data_spinner_4 = new ArrayList<>();
        mBottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_cart, null);
        mBottomSheetDialog.setContentView(sheetView);
        img_product_dialog = sheetView.findViewById(R.id.img_product_dialog);
        txt_name_pro_dialog = sheetView.findViewById(R.id.txt_name_product_dialog);
        txt_price_pro_dialog = sheetView.findViewById(R.id.txt_price_cart);
        txt_code_pro_dialog = sheetView.findViewById(R.id.txt_code_pro_dialog);
        ll_spinner_1 = sheetView.findViewById(R.id.ll_spinner_1);
        ll_spinner_2 = sheetView.findViewById(R.id.ll_spinner_2);
        ll_spinner_3 = sheetView.findViewById(R.id.ll_spinner_3);
        ll_spinner_4 = sheetView.findViewById(R.id.ll_spinner_4);

        txt_title_spinner_1 = sheetView.findViewById(R.id.txt_title_spinner_1);
        txt_title_spinner_2 = sheetView.findViewById(R.id.txt_title_spinner_2);
        txt_title_spinner_3 = sheetView.findViewById(R.id.txt_title_spinner_3);
        txt_title_spinner_4 = sheetView.findViewById(R.id.txt_title_spinner_4);

        spiner_type_1 = sheetView.findViewById(R.id.spiner_type_1);
        spiner_type_2 = sheetView.findViewById(R.id.spiner_type_2);
        spiner_type_3 = sheetView.findViewById(R.id.spiner_type_3);
        spiner_type_4 = sheetView.findViewById(R.id.spiner_type_4);
        btn_comfirm = sheetView.findViewById(R.id.btn_comfirm);

    }

    private String sThuoctinh1 = "", sThuoctinh2 = "", sThuoctinh3 = "", sThuoctinh4 = "";
    PropetiObj.PropetiDetail mObjPro1, mObjPro2, mObjPro3, mObjPro4;
    Products mProductClick;

    private void show_Bottom_Dialog(Products objProduct) {
        mProductClick = objProduct;
        data_spinner_1.clear();
        data_spinner_2.clear();
        data_spinner_3.clear();
        data_spinner_4.clear();

        if (objProduct.getsUrlImage() != null) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.img_defaul)
                    .error(R.drawable.img_defaul);
            Glide.with(this).load(objProduct.getsUrlImage())
                    .apply(options).into(img_product_dialog);
            /*Glide.with(this).load(objProduct.getsUrlImage()).asBitmap()
                    .placeholder(R.drawable.img_defaul)
                    .into(new BitmapImageViewTarget(img_product_dialog) {
                        @Override
                        public void onResourceReady(Bitmap drawable, GlideAnimation anim) {
                            super.onResourceReady(drawable, anim);
                            //   progressBar.setVisibility(View.GONE);
                        }
                    });*/
        } else
            Glide.with(this).load(R.drawable.img_defaul).into(img_product_dialog);
        if (objProduct.getsName() != null && objProduct.getsName().length() > 0)
            txt_name_pro_dialog.setText(objProduct.getsName());
        else
            txt_name_pro_dialog.setText("...");
        if (objProduct != null && objProduct.getsPrice().length() > 0)
            txt_price_pro_dialog.setText(StringUtil.conventMonney(objProduct.getsPrice()));
        else
            txt_price_pro_dialog.setText("...");
        if (objProduct != null && objProduct.getCODE_PRODUCT().length() > 0)
            txt_code_pro_dialog.setText("Mã sản phẩm: " + objProduct.getCODE_PRODUCT());
        else
            txt_code_pro_dialog.setText("...");
        if (objProduct.getmListThuoctinhTong() != null) {
            if (objProduct.getmListThuoctinhTong().size() > 0) {
                ll_spinner_1.setVisibility(View.VISIBLE);
                PropetiObj objPro = objProduct.getmListThuoctinhTong().get(0);
                txt_title_spinner_1.setText(objPro.getNAME());
                for (PropetiObj.PropetiDetail objDetail : objPro.getINFO()) {
                    objDetail.setNAME_PARENT(objPro.getNAME());
                    data_spinner_1.add(objDetail.getSUB_PROPERTIES());
                }
            }
            if (objProduct.getmListThuoctinhTong().size() > 1) {
                ll_spinner_2.setVisibility(View.VISIBLE);
                PropetiObj objPro = objProduct.getmListThuoctinhTong().get(1);
                txt_title_spinner_2.setText(objPro.getNAME());
                for (PropetiObj.PropetiDetail objDetail : objPro.getINFO()) {
                    objDetail.setNAME_PARENT(objPro.getNAME());
                    data_spinner_2.add(objDetail.getSUB_PROPERTIES());
                }
            }
            if (objProduct.getmListThuoctinhTong().size() > 2) {
                ll_spinner_3.setVisibility(View.VISIBLE);
                PropetiObj objPro = objProduct.getmListThuoctinhTong().get(2);
                txt_title_spinner_3.setText(objPro.getNAME());
                for (PropetiObj.PropetiDetail objDetail : objPro.getINFO()) {
                    objDetail.setNAME_PARENT(objPro.getNAME());
                    data_spinner_3.add(objDetail.getSUB_PROPERTIES());
                }
            }
            if (objProduct.getmListThuoctinhTong().size() > 3) {
                ll_spinner_4.setVisibility(View.VISIBLE);
                PropetiObj objPro = objProduct.getmListThuoctinhTong().get(3);
                txt_title_spinner_4.setText(objPro.getNAME());
                for (PropetiObj.PropetiDetail objDetail : objPro.getINFO()) {
                    objDetail.setNAME_PARENT(objPro.getNAME());
                    data_spinner_4.add(objDetail.getSUB_PROPERTIES());
                }
            }
        }
        set_data_spinner_1();
        set_data_spinner_2();
        set_data_spinner_3();
        set_data_spinner_4();
        mBottomSheetDialog.show();
    }

    private void set_data_spinner_1() {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_spinner, data_spinner_1);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spiner_type_1.setAdapter(adapter);
        spiner_type_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sThuoctinh1 = data_spinner_1.get(position);
                mObjPro1 = mProductClick.getmListThuoctinhTong().get(0).getINFO().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void set_data_spinner_2() {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_spinner, data_spinner_2);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spiner_type_2.setAdapter(adapter);
        spiner_type_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sThuoctinh2 = data_spinner_2.get(position);
                mObjPro2 = mProductClick.getmListThuoctinhTong().get(1).getINFO().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void set_data_spinner_3() {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_spinner, data_spinner_3);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spiner_type_3.setAdapter(adapter);
        spiner_type_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sThuoctinh3 = data_spinner_3.get(position);
                mObjPro3 = mProductClick.getmListThuoctinhTong().get(2).getINFO().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void set_data_spinner_4() {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_spinner, data_spinner_4);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spiner_type_4.setAdapter(adapter);
        spiner_type_4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sThuoctinh4 = data_spinner_4.get(position);
                mObjPro4 = mProductClick.getmListThuoctinhTong().get(3).getINFO().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
