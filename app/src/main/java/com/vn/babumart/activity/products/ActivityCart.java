package com.vn.babumart.activity.products;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import com.vn.babumart.App;
import com.vn.babumart.R;
import com.vn.babumart.activity.order.ActivityAddOrder;
import com.vn.babumart.activity.order.InterfaceOrder;
import com.vn.babumart.activity.order.PresenterOrder;
import com.vn.babumart.adapter.AdapterCart;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.callback.ClickDialog;
import com.vn.babumart.callback.ItemClickCartListener;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.config.Config;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.CategoryProductHome;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.ObjCTV;
import com.vn.babumart.models.ObjConfigCommis;
import com.vn.babumart.models.ObjOrder;
import com.vn.babumart.models.Products;
import com.vn.babumart.models.PropetiObj;
import com.vn.babumart.models.respon_api.ObjLisCart;
import com.vn.babumart.models.respon_api.ResponGetCat;
import com.vn.babumart.models.respon_api.ResponGetProduct;
import com.vn.babumart.models.respon_api.ResponHistoryOrder;
import com.vn.babumart.models.respon_api.ResponSubProduct;
import com.vn.babumart.untils.SharedPrefs;
import com.vn.babumart.untils.StringUtil;
import com.vn.babumart.untils.TimeUtils;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 23-May-2019
 * Time: 23:03
 * Version: 1.0
 */
public class ActivityCart extends BaseActivity implements InterfaceOrder.View, InterfaceProduct.View {
    private static final String TAG = "ActivityCart";
    @BindView(R.id.txt_value_total)
    TextView txt_value_total;
    @BindView(R.id.btn_order)
    TextView btn_order;
    @BindView(R.id.txt_value_commission)
    TextView txt_value_commission;
    @BindView(R.id.txt_value_hohong_ctv)
    TextView txt_value_hohong_ctv;
    @BindView(R.id.txt_value_hohong_donhang)
    TextView txt_value_hohong_donhang;
    PresenterOrder mPresenter;
    PresenterProduct mPresenterProduct;
    BottomSheetDialog mBottomSheetDialog;
    ImageView img_product_dialog;
    TextView txt_name_pro_dialog;
    TextView txt_price_pro_dialog;
    TextView txt_code_pro_dialog;
    TextView txt_price_delete;
    TextView txt_commission_cart;
    TextView txt_precent_detail;
    View view_delete;
    Button btn_comfirm;
    ConstraintLayout ll_spinner_1, ll_spinner_2, ll_spinner_3, ll_spinner_4;
    TextView txt_title_spinner_1, txt_title_spinner_2, txt_title_spinner_3, txt_title_spinner_4;
    List<String> data_spinner_1, data_spinner_2, data_spinner_3, data_spinner_4;
    Spinner spiner_type_1, spiner_type_2, spiner_type_3, spiner_type_4;
    private float hh_ctv = 0, hh_donhang = 0, hh_sanpham = 0;
    private String sThuoctinh1 = "", sThuoctinh2 = "", sThuoctinh3 = "", sThuoctinh4 = "";
    PropetiObj.PropetiDetail mObjPro1, mObjPro2, mObjPro3, mObjPro4;
    Products mProductClick;
    AdapterCart adapterProduct;
    RecyclerView.LayoutManager mLayoutManagerProduct;
    List<Products> mLisCateProduct;
    @BindView(R.id.rcv_carts)
    RecyclerView recycle_lis_product;
    @BindView(R.id.txt_value_product)
    TextView txt_value_product;
    ImageView img_home;
    String sListId = "";
    String sUserName;
    ObjLisCart objCat;

    @Override
    public int setContentViewId() {
        return R.layout.activity_cart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterOrder(this);
        mPresenterProduct = new PresenterProduct(this);
        initBottom();
        initAppbar();
        initProduct();
        setup_hohong();
        initDataProduct();
        initEvent();
    }

    private void setup_hohong() {
        ObjCTV objCTV = SharedPrefs.getInstance().get(Constants.KEY_SAVE_OBJ_CTV_APPLICATION, ObjCTV.class);
        if (objCTV != null && objCTV.getCOMISSION() != null && objCTV.getCOMISSION().length() > 0) {
            txt_value_hohong_ctv.setText(objCTV.getCOMISSION() + "%");
            hh_ctv = Float.parseFloat(objCTV.getCOMISSION());
        } else {
            txt_value_hohong_ctv.setText("0%");
        }
        txt_value_hohong_donhang.setText("0%");
    }

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
        txt_price_delete = sheetView.findViewById(R.id.txt_price_delete);
        txt_commission_cart = sheetView.findViewById(R.id.txt_commission_cart);
        txt_precent_detail = sheetView.findViewById(R.id.txt_precent_detail);
        view_delete = sheetView.findViewById(R.id.view_delete);
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

    private void hide_promotion() {
        txt_precent_detail.setVisibility(View.GONE);
        txt_price_delete.setVisibility(View.GONE);
        view_delete.setVisibility(View.GONE);
    }

    private void show_promotion() {
        txt_precent_detail.setVisibility(View.VISIBLE);
        txt_price_delete.setVisibility(View.VISIBLE);
        view_delete.setVisibility(View.VISIBLE);
    }

    private void show_Bottom_Dialog(Products objProduct) {
        mProductClick = objProduct;
        data_spinner_1.clear();
        data_spinner_2.clear();
        data_spinner_3.clear();
        data_spinner_4.clear();
        ll_spinner_1.setVisibility(View.GONE);
        ll_spinner_2.setVisibility(View.GONE);
        ll_spinner_3.setVisibility(View.GONE);
        ll_spinner_4.setVisibility(View.GONE);
        if (objProduct.getsUrlImage() != null) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.img_defaul)
                    .error(R.drawable.img_defaul);
            Glide.with(this).load(objProduct.getsUrlImage()).apply(options).into(img_product_dialog);
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
        if (objProduct.getCOMISSION_PRODUCT() != null) {
            set_hh_sp(objProduct.getCOMISSION_PRODUCT(), objProduct.getsPrice(),
                    txt_commission_cart);
        } else {
            txt_commission_cart.setText("Hoa hồng sp: 0%");
        }
        if (objProduct.getPRICE_PROMOTION() != null) {
            if (objProduct.getSTART_PROMOTION() != null && objProduct.getEND_PROMOTION() != null) {
                if (TimeUtils.compare_two_date_currenttime(objProduct.getSTART_PROMOTION(), objProduct.getEND_PROMOTION())) {
                    show_promotion();
                    txt_price_delete.setText(StringUtil.conventMonney_Long(objProduct.getsPrice()));
                    try {
                        int price = Integer.parseInt(objProduct.getsPrice());
                        int price_promotion = Integer.parseInt(objProduct.getPRICE_PROMOTION());
                        float fprecent = (float) (price - price_promotion) / price;
                        int precent = (int) (fprecent * 100);
                        txt_precent_detail.setText("-" + precent + "%");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (objProduct.getPRICE_PROMOTION() != null) {
                        txt_price_pro_dialog.setText(StringUtil.conventMonney_Long(objProduct.getPRICE_PROMOTION()));

                    }
                    if (objProduct.getCOMISSION_PRODUCT() != null) {
                        set_hh_sp(objProduct.getCOMISSION_PRODUCT(), objProduct.getPRICE_PROMOTION(),
                                txt_commission_cart);
                    } else {
                        txt_commission_cart.setText("Hoa hồng sp: 0%");
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
        } else {
            ll_spinner_1.setVisibility(View.GONE);
            ll_spinner_2.setVisibility(View.GONE);
            ll_spinner_3.setVisibility(View.GONE);
            ll_spinner_4.setVisibility(View.GONE);
        }
        set_data_spinner_1();
        set_data_spinner_2();
        set_data_spinner_3();
        set_data_spinner_4();
        mBottomSheetDialog.show();
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

    private void initEvent() {
        btn_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                set_price_total();
                adapterProduct.notifyDataSetChanged();
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Products objpro : mLisCateProduct) {
                    objpro.setVisibleDelete(true);
                }
                adapterProduct.notifyDataSetChanged();
            }
        });
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheck = false;
                String hoahong = "";
                if (txt_value_commission.getText().toString().length() > 0)
                    hoahong = txt_value_commission.getText().toString();
                for (Products obj : mLisCateProduct) {
                    if (obj.getsQuantum() != null && Integer.parseInt(obj.getsQuantum()) > 0) {
                        isCheck = true;
                    }
                }
                if (isCheck) {
                    Intent intent = new Intent(ActivityCart.this,
                            ActivityAddOrder.class);
                    intent.putExtra(Constants.KEY_SEND_TOTAL_HOAHONG, hoahong);
                    App.mLisProductCart.clear();
                    App.mLisProductCart.addAll(mLisCateProduct);
                    startActivityForResult(intent, Constants.RequestCode.GET_ADD_ORDER);
                } else {
                    showDialogNotify("Thông báo", "Bạn chưa chọn sản phẩm nào để đặt hàng");
                }

            }
        });
    }

    private void initProduct() {
        mLisCateProduct = new ArrayList<>();
        adapterProduct = new AdapterCart(mLisCateProduct, this);
        mLayoutManagerProduct = new GridLayoutManager(this, 1);
        recycle_lis_product.setHasFixedSize(true);
        recycle_lis_product.setLayoutManager(mLayoutManagerProduct);
        recycle_lis_product.setItemAnimator(new DefaultItemAnimator());
        recycle_lis_product.setAdapter(adapterProduct);
        adapterProduct.notifyDataSetChanged();
        adapterProduct.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                Products objPro = (Products) item;
                show_Bottom_Dialog(objPro);

            }
        });
        adapterProduct.setOnIListener(new ItemClickCartListener() {
            @Override
            public void onClickItem(int position, Object item) {
                final Products objPro = (Products) item;
                showDialogComfirm("Thông báo", "Bạn có chắc chắn muốn xoá sản phẩm này ra " +
                        "khỏi đơn hàng", true, new ClickDialog() {
                    @Override
                    public void onClickYesDialog() {
                        if (mLisCateProduct.size() > 0) {
                            for (int i = 0; i < mLisCateProduct.size(); i++) {
                                Products obj = mLisCateProduct.get(i);
                                if (obj.getCODE_PRODUCT().equals(objPro.getCODE_PRODUCT())) {
                                    mLisCateProduct.remove(obj);
                                }
                            }
                            ObjLisCart objLisCart = new ObjLisCart(mLisCateProduct);
                            SharedPrefs.getInstance().put(Constants.KEY_SAVE_LIST_CART, objLisCart);
                            adapterProduct.notifyDataSetChanged();
                            set_price_total();
                        }
                    }

                    @Override
                    public void onClickNoDialog() {

                    }
                });

            }

            @Override
            public void onClickItem_Add(int position, Object item) {
                Products objPro = (Products) item;
                Integer iQuantum = 0;
                if (objPro.getsQuantum() != null && objPro.getsQuantum().length() > 0) {
                    iQuantum = Integer.parseInt(objPro.getsQuantum());
                }
                iQuantum++;
                mLisCateProduct.get(position).setsQuantum("" + iQuantum);
                adapterProduct.notifyDataSetChanged();
                set_price_total();
            }

            @Override
            public void onClickItem_Minus(int position, Object item) {
                Products obj = (Products) item;
                Integer iQuantum = 0;
                String s = "";
                if (obj != null && obj.getsQuantum() != null && obj.getsQuantum().length() > 0) {
                    s = obj.getsQuantum();
                    iQuantum = Integer.parseInt(s);
                }
                if (iQuantum > 0) {
                    iQuantum--;
                    mLisCateProduct.get(position).setsQuantum("" + iQuantum);
                } else {
                    mLisCateProduct.get(position).setsQuantum("" + iQuantum);
                }
                adapterProduct.notifyDataSetChanged();
                set_price_total();
            }
        });
    }

    private void set_price_total() {
        try {
            long lPrice = 0;
            int value = 0;
            long value_max = 0;
            int hh_max = 0;
            long lCommission = 0;
            for (Products obj : mLisCateProduct) {
                if (obj != null && obj.getsQuantum() != null && obj.getsQuantum().length() > 0 && obj.getsPrice() != null) {
                    if (obj.getPRICE_PROMOTION() != null) {
                        if (obj.getSTART_PROMOTION() != null && obj.getEND_PROMOTION() != null) {
                            lPrice = lPrice + (Integer.parseInt(obj.getsQuantum()) *
                                    Integer.parseInt(obj.getPRICE_PROMOTION()));
                        } else {
                            lPrice = lPrice + (Integer.parseInt(obj.getsQuantum()) * Integer.parseInt(obj.getsPrice()));
                        }
                    } else {
                        lPrice = lPrice + (Integer.parseInt(obj.getsQuantum()) * Integer.parseInt(obj.getsPrice()));
                    }

                    if (obj.getCOMISSION_PRODUCT() != null && obj.getCOMISSION_PRODUCT().length() > 0) {
                        String hh = obj.getCOMISSION_PRODUCT().replace(",", ".");
                        float hh_product = Float.parseFloat(hh);
                        long price_total = 0;
                        if (obj.getPRICE_PROMOTION() != null) {
                            if (obj.getSTART_PROMOTION() != null && obj.getEND_PROMOTION() != null) {
                                price_total = Long.parseLong(obj.getPRICE_PROMOTION());
                            } else {
                                price_total = Long.parseLong(obj.getsPrice());
                            }
                        } else {
                            price_total = Long.parseLong(obj.getsPrice());
                        }
                        long value_hh = (long) (hh_product * price_total * Integer.parseInt(obj.getsQuantum()));
                  /*  long pire = Float.parseFloat(obj.getCOMISSION_PRODUCT()) *
                            (Integer.parseInt(obj.getsQuantum()) * Integer.parseInt(obj.getsPrice()));*/
                        lCommission = lCommission + (value_hh / 100);
                    }
                }
            }
            if (lCommission > 0) {
                txt_value_product.setText(StringUtil.conventMonney_Long("" + lCommission));
            } else {
                txt_value_product.setText("0đ");
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
                        long iHH_donhang = (value * lPrice) / 100;
                        txt_value_hohong_donhang.setText("(" + value + "%) "
                                + StringUtil.conventMonney_Long("" + iHH_donhang));
                        lCommission = lCommission + ((value * lPrice) / 100);
                    } else {
                        txt_value_hohong_donhang.setText("(0%) 0đ");
                    }
                }
                long hh_ctv_value = (long) ((hh_ctv * lPrice) / 100);
                int ctv = (int) hh_ctv;
                txt_value_hohong_ctv.setText("(" + ctv + "%) " + StringUtil.conventMonney_Long("" + hh_ctv_value));

                lCommission = (long) (lCommission + ((hh_ctv * lPrice) / 100));

            } else {
                txt_value_commission.setText("0 đ");
            }
            if (!StringUtil.check_login_customer()) {
                lCommission = (long) ((hh_ctv * lPrice) / 100);
                txt_value_hohong_donhang.setText("(0%) 0đ");
                txt_value_product.setText("0đ");
            }
            txt_value_commission.setText(StringUtil.conventMonney_Long("" + lCommission));
            txt_value_total.setText(StringUtil.conventMonney_Long("" + lPrice));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void initAppbar() {
        ImageView img_back = findViewById(R.id.img_back);
        img_home = findViewById(R.id.img_home);
        TextView txt_title = findViewById(R.id.txt_title);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_title.setText("Giỏ hàng");
        img_home.setVisibility(View.VISIBLE);
    }

    private void initDataProduct() {
        sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        objCat = SharedPrefs.getInstance().get(Constants.KEY_SAVE_LIST_CART, ObjLisCart.class);
        if (objCat != null && objCat.getmList() != null && objCat.getmList().size() > 0) {
            mLisCateProduct.clear();
            mLisCateProduct.addAll(objCat.getmList());
        }
        for (Products obj : mLisCateProduct) {
            obj.setVisibleDelete(false);
            obj.setsQuantum("1");
            sListId = sListId + obj.getID() + ",";
        }
        if (sListId.length() > 0) {
            sListId = sListId.substring(0, sListId.length() - 1);
            Log.e(TAG, "initDataProduct: " + sListId);
        }

        adapterProduct.notifyDataSetChanged();
        if (sListId.length() > 0) {
            showDialogLoading();
            mPresenterProduct.api_get_product_by_listid(sUserName, sListId);
        }
        set_price_total();
    }

    private void save_list_cart(List<Products> mLis) {
        ObjLisCart obj = new ObjLisCart(mLis);
        Gson gson = new Gson();
        String json = gson.toJson(mLis);
        SharedPrefs.getInstance().put(Constants.KEY_SAVE_LIST_CART, obj);
    }

    private List<Products> get_list_cart() {
/*        String json = SharedPrefs.getInstance().get(Constants.KEY_SAVE_LIST_CART, String.class);
        Gson gson = new Gson(); // Or use new GsonBuilder().create();
        objCat = gson.fromJson(json, ObjLisCart.class);*/
        objCat = SharedPrefs.getInstance().get(Constants.KEY_SAVE_LIST_CART, ObjLisCart.class);

        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RequestCode.GET_ADD_ORDER && resultCode == RESULT_OK)
            finish();
    }

    @Override
    public void show_error_api() {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_product_cat(ResponGetCat obj) {
        hideDialogLoading();
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
        hideDialogLoading();
        if (obj != null && obj.getsERROR().equals("0000")) {
            try {
                if (obj.getmList() != null && obj.getmList().size() > 0) {
                    for (Products objPro : obj.getmList()) {
                        for (Products objProduct : mLisCateProduct) {
                            if (objProduct.getCODE_PRODUCT().equals(objPro.getCODE_PRODUCT())) {
                                objProduct.setsPrice(objPro.getsPrice());
                                objProduct.setPRICE_PROMOTION(objPro.getPRICE_PROMOTION());
                                objProduct.setSTART_PROMOTION(objPro.getSTART_PROMOTION());
                                objProduct.setEND_PROMOTION(objPro.getEND_PROMOTION());
                                objProduct.setCOMISSION_PRODUCT(objPro.getCOMISSION_PRODUCT());
                            }
                        }
                    }
                    adapterProduct.notifyDataSetChanged();
                    set_price_total();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public void show_product_detail(Products objProduct) {

    }

    @Override
    public void show_order_history(ResponHistoryOrder obj) {

    }

    @Override
    public void show_order_history_detail(ObjOrder obj) {

    }

    @Override
    public void show_order_history_detail_pd(ResponGetProduct obj) {

    }

    @Override
    public void show_edit_order_product(ErrorApi obj) {

    }

    @Override
    public void show_order_product(ErrorApi obj) {

    }

    @Override
    public void show_config_commission(ErrorApi obj) {

    }
}
