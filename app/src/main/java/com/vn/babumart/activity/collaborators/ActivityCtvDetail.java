package com.vn.babumart.activity.collaborators;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.vn.babumart.App;
import com.vn.babumart.R;
import com.vn.babumart.activity.login.Menu_Search.ActivityDistrict;
import com.vn.babumart.activity.login.Menu_Search.ActivityGender;
import com.vn.babumart.activity.login.Menu_Search.ActivityListBank;
import com.vn.babumart.activity.login.Menu_Search.ActivityListCity;
import com.vn.babumart.activity.login.Menu_Search.ActivityListWard;
import com.vn.babumart.base.BaseActivity;
import com.vn.babumart.callback.ClickDialog;
import com.vn.babumart.config.Config;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.City;
import com.vn.babumart.models.Districts;
import com.vn.babumart.models.ErrorApi;
import com.vn.babumart.models.MessageEvent;
import com.vn.babumart.models.ObjCTV;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.models.Ward;
import com.vn.babumart.models.respon_api.ResponGetLisCTV;
import com.vn.babumart.untils.KeyboardUtil;
import com.vn.babumart.untils.SharedPrefs;
import com.vn.babumart.untils.StringUtil;
import com.vn.babumart.upload_media.InterfaceUploadImage;
import com.vn.babumart.upload_media.PresenterUploadImage;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 20-May-2019
 * Time: 15:14
 * Version: 1.0
 */
public class ActivityCtvDetail extends BaseActivity implements View.OnClickListener,
        InterfaceCollaborators.View, InterfaceUploadImage.View {
    private ObjCTV mObjCTV;
    @BindView(R.id.txt_name_ctv_detail)
    TextView txt_name_ctv_detail;
    @BindView(R.id.txt_user_ctv_detail)
    TextView txt_user_ctv_detail;
    @BindView(R.id.edt_name_ctv)
    EditText edt_name_ctv;
    @BindView(R.id.edt_birthday_ctv)
    EditText edt_birthday_ctv;
    @BindView(R.id.txt_gender_ctv)
    TextView edt_gender_ctv;
    @BindView(R.id.edt_email_ctv)
    EditText edt_email_ctv;
    @BindView(R.id.edt_number_bank_ctv)
    EditText edt_number_bank_ctv;
    @BindView(R.id.edt_name_bank_ctv)
    TextView edt_name_bank_ctv;
    @BindView(R.id.txt_city_ctv)
    TextView txt_city_ctv;
    @BindView(R.id.edt_name_tk_bank)
    TextView edt_name_tk_bank;
    @BindView(R.id.txt_district_ctv)
    TextView txt_district_ctv;
    @BindView(R.id.txt_ward_ctv)
    TextView txt_ward_ctv;
    @BindView(R.id.edt_address_ctv)
    EditText edt_address_ctv;
    @BindView(R.id.txt_title_info)
    TextView txt_title_info;
    @BindView(R.id.img_1_cmnd)
    ImageView img_1_cmnd;
    @BindView(R.id.img_2_cmnd)
    ImageView img_2_cmnd;
    @BindView(R.id.ll_bankname)
    ConstraintLayout ll_bankname;
    @BindView(R.id.txt_title_bonus)
    TextView txt_title_bonus;
    @BindView(R.id.txt_taianh)
    TextView txt_taianh;
    @BindView(R.id.edt_cmnd_number)
    EditText edt_cmnd_number;
    @BindView(R.id.img_avata_ctv_detail)
    CircleImageView img_avata_ctv_detail;
    @BindView(R.id.btn_update_info)
    Button btn_update_info;
    @BindView(R.id.edt_code_ctv)
    EditText edt_code_ctv;
    @BindView(R.id.ll_gender)
    ConstraintLayout ll_gender;
    @BindView(R.id.img_copy_code)
    ImageView img_copy_code;
    @BindView(R.id.ll_update_user)
    LinearLayout ll_update_user;
    @BindView(R.id.nsv_ctv)
    NestedScrollView nsv_ctv;
    Calendar myCalendar_to = Calendar.getInstance();
    boolean isUpdateCTV = false;
    private PresenterCTV mPresenter;
    private PresenterUploadImage mPresenterUpload;
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
        edt_birthday_ctv.setText(sdf.format(myCalendar_to.getTime()));
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_info_ctv_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterCTV(this);
        mPresenterUpload = new PresenterUploadImage(this);
        init();
        initAppbar();
        initData();
        initEvent();

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
        txt_title.setText(getResources().getString(R.string.app_name));
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

   /* private void disable_edt(EditText edt) {
        edt.setEnabled(true);
        edt.setInputType(InputType.TYPE_CLASS_TEXT);
        edt.setFocusable(true);
    }*/

    String sOpionImage = "";
    String sCode;

    private void initEvent() {
        img_copy_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mObjCTV != null && mObjCTV.getUSER_CODE() != null) {
                    sCode = mObjCTV.getUSER_CODE();
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText(getString(R.string.app_name), sCode);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(ActivityCtvDetail.this, "Mã CTV đã được copy vào Clipboard.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ll_gender.setOnClickListener(this);
        btn_update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_info_ctv();
            }
        });
        ll_bankname.setOnClickListener(this);
        // txt_title_reset_pass.setOnClickListener(this);
        img_1_cmnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sOpionImage = "0";
                checkPermissionsAndOpenFilePicker();
            }
        });
        img_2_cmnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sOpionImage = "1";
                checkPermissionsAndOpenFilePicker();
            }
        });
        txt_taianh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sOpionImage = "3";
                checkPermissionsAndOpenFilePicker();
            }
        });
        txt_city_ctv.setOnClickListener(this);
        txt_district_ctv.setOnClickListener(this);
        txt_ward_ctv.setOnClickListener(this);
        edt_birthday_ctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ActivityCtvDetail.this, R.style.MyDatePickerStyle, to_date, myCalendar_to
                        .get(Calendar.YEAR), myCalendar_to.get(Calendar.MONTH),
                        myCalendar_to.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    String sFullNam = "", sBirthday = "", sEmail = "", sGender = "1", sCityId = "", sDistrictId = "",
            sAddress = "", sSTK = "", sNameTK = "", sNameNH = "", sCmtNumber = "", sUserCTV = "", INVITE_CODE = "", sWardID = "";

    private void update_info_ctv() {
        if (edt_name_ctv.getText().toString().length() > 0) {
            sFullNam = edt_name_ctv.getText().toString();
        } else {
            showAlertDialog("Thông báo", "Bạn chưa nhập vào họ tên");
            KeyboardUtil.requestKeyboard(edt_name_ctv);
            return;
        }
        if (edt_birthday_ctv.getText().toString().length() > 0) {
            sBirthday = edt_birthday_ctv.getText().toString();
        } else {
            showAlertDialog("Thông báo", "Bạn chưa nhập vào ngày tháng năm sinh");
            KeyboardUtil.requestKeyboard(edt_birthday_ctv);
            return;
        }
        if (edt_email_ctv.getText().toString().length() > 0) {
            sEmail = edt_email_ctv.getText().toString();
        } else {
            showAlertDialog("Thông báo", "Bạn chưa nhập vào Email");
            KeyboardUtil.requestKeyboard(edt_email_ctv);
            return;
        }
        if (edt_address_ctv.getText().toString().length() > 0) {
            sAddress = edt_address_ctv.getText().toString();
        } else {
            showAlertDialog("Thông báo", "Mời bạn nhập vào địa chỉ");
            KeyboardUtil.requestKeyboard(edt_address_ctv);
            return;
        }
        if (objCity != null && objCity.getNAME() != null) {
            sCityId = objCity.getNAME();
        } else {
            showAlertDialog("Thông báo", "Mời bạn chọn lại tỉnh/thành phố");
            return;
        }
        if (objDistrict != null && objDistrict.getNAME() != null) {
            sDistrictId = objDistrict.getNAME();
        } else {
            showAlertDialog("Thông báo", "Mời bạn chọn lại quận/huyện");
            return;
        }
        if (objWard != null && objWard.getNAME() != null) {
            sWardID = objWard.getNAME();
        } else {
            showAlertDialog("Thông báo", "Mời bạn chọn lại phường xã");
            return;
        }
        if (edt_number_bank_ctv.getText().toString().length() > 0) {
            sSTK = edt_number_bank_ctv.getText().toString();
        }
        if (edt_name_bank_ctv.getText().toString().length() > 0) {
            sNameNH = edt_name_bank_ctv.getText().toString();
        }
        if (edt_name_tk_bank.getText().toString().length() > 0) {
            sNameTK = edt_name_tk_bank.getText().toString();
        }
        if (edt_cmnd_number.getText().toString().length() > 0) {
            sCmtNumber = edt_cmnd_number.getText().toString();
        }
        if (edt_code_ctv.getText().toString().length() > 0) {
            INVITE_CODE = edt_code_ctv.getText().toString();
        } else {
            INVITE_CODE = "";
        }
        showDialogLoading();
        if (IMAGE_PATH_AVATA != null && IMAGE_PATH_AVATA.length() > 0) {
            mPresenterUpload.api_upload_image(IMAGE_PATH_AVATA, "avata");
        } else if (IMAGE_PATH != null && IMAGE_PATH.length() > 0) {
            mPresenterUpload.api_upload_image(IMAGE_PATH, "font_img_cmt");
        } else if (IMAGE_PATH_2 != null && IMAGE_PATH_2.length() > 0) {
            mPresenterUpload.api_upload_image(IMAGE_PATH_2, "back_img_cmt");
        } else {
            get_api_update();
        }
    }

    String sAvata = "", sImgCmtFont = "", sImgCmtBack = "";

    private void get_api_update() {
        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenter.api_update_ctv(sUserName, sUserCTV, sFullNam, sBirthday, sGender,
                sEmail, sCityId, sDistrictId, sAddress, sSTK, sNameTK, sNameNH, sAvata,
                sCmtNumber, sImgCmtFont, sImgCmtBack, INVITE_CODE, sWardID);
    }

    private void initData() {
        ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
        mObjCTV = (ObjCTV) getIntent().getSerializableExtra(Constants.KEY_SEND_OBJ_CTV);
        isUpdateCTV = getIntent().getBooleanExtra(Constants.KEY_SEND_UPDATE_CTV, false);

        if (StringUtil.check_login_group()) {
            if (objLogin.getGROUPS().equals(Constants.User_Group_Type.CTV) ||
                    mObjCTV != null) {
                btn_update_info.setVisibility(View.GONE);
            }
        }
        if (isUpdateCTV) {
            showDialogComfirm("Thông báo",
                    "Bạn cần nhập vào mã giới thiệu để trở thành Cộng tác viên của IGO SHOPPING.",
                    false,
                    new ClickDialog() {
                        @Override
                        public void onClickYesDialog() {
                            edt_code_ctv.requestFocus();
                        }

                        @Override
                        public void onClickNoDialog() {

                        }
                    });
        }
        String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        boolean isUpdate = getIntent().getBooleanExtra(Constants.KEY_SEND_UPDATE_USER, false);
        if (isUpdate) {
            btn_update_info.setVisibility(View.VISIBLE);
            showDialogLoading();
            mPresenter.api_get_ctv_detail(sUserName, sUserName, Config.ID_SHOP);
        } else {
            if (mObjCTV != null) {
                showDialogLoading();
                mPresenter.api_get_ctv_detail(sUserName, mObjCTV.getUSERNAME(), Config.ID_SHOP);
                set_info_ctv(mObjCTV);
            }
        }

    }

    private void set_info_ctv(ObjCTV mObjCTV) {
        this.mObjCTV = mObjCTV;
        if (mObjCTV.getGROUPS().equals(Config.GROUP_KHACHHANG)) {
            ll_update_user.setVisibility(View.VISIBLE);
        } else {
            ll_update_user.setVisibility(View.GONE);
        }
        sUserCTV = mObjCTV.getUSERNAME();
        if (mObjCTV.getSTK() != null) {
            edt_number_bank_ctv.setText(mObjCTV.getSTK());
        }
        if (mObjCTV.getGENDER() != null) {
            if (mObjCTV.getGENDER().equals("1")) {
                edt_gender_ctv.setText("Nam");
            } else if (mObjCTV.getGENDER().equals("2")) {
                edt_gender_ctv.setText("Nữ");
            }
        }
        if (mObjCTV.getTEN_NH() != null) {
            edt_name_bank_ctv.setText(mObjCTV.getTEN_NH());
        }
        if (mObjCTV.getTENTK() != null) {
            edt_name_tk_bank.setText(mObjCTV.getTENTK());
        }
        if (mObjCTV.getDOB() != null) {
            edt_birthday_ctv.setText(mObjCTV.getDOB());
        }
        if (mObjCTV.getSO_CMT() != null) {
            edt_cmnd_number.setText(mObjCTV.getSO_CMT());
        }
             /*   if (mObjCTV.getTEN_NH() != null) {
                    edt_chinhanh_bank.setText(mObjCTV.getTEN_NH());
                } else {
                    edt_chinhanh_bank.setText("....");

                }*/
        if (mObjCTV.getUSER_CODE() != null) {
            txt_name_ctv_detail.setText("Mã CTV: " + mObjCTV.getUSER_CODE());

        } else {
            txt_name_ctv_detail.setText("Mã CTV: " + "....");
        }
        if (mObjCTV.getFULL_NAME() != null) {
            // txt_name_ctv_detail.setText(mObjCTV.getFULL_NAME());
            edt_name_ctv.setText(mObjCTV.getFULL_NAME());
        } else {
            edt_name_ctv.setText("");
            // txt_name_ctv_detail.setText("....");
        }
        if (mObjCTV.getUSERNAME() != null) {
            txt_user_ctv_detail.setText(mObjCTV.getUSERNAME());
        } else
            txt_user_ctv_detail.setText("....");

        if (mObjCTV.getEMAIL() != null) {
            edt_email_ctv.setText(mObjCTV.getEMAIL());
        } else
            edt_email_ctv.setText("");

        if (mObjCTV.getCITY() != null && mObjCTV.getCITY_ID() != null) {
            objCity.setNAME(mObjCTV.getCITY());
            objCity.setMATP(mObjCTV.getCITY_ID());
            txt_city_ctv.setText(mObjCTV.getCITY());
        }

        if (mObjCTV.getDISTRICT() != null) {
            txt_district_ctv.setText(mObjCTV.getDISTRICT());
            objDistrict.setNAME(mObjCTV.getDISTRICT());
            objDistrict.setMAQH(mObjCTV.getDISTRICT_ID());
        }
        if (mObjCTV.getWARD() != null) {
            txt_ward_ctv.setText(mObjCTV.getWARD());
            objWard.setNAME(mObjCTV.getWARD());
            objWard.setMAQH(mObjCTV.getWARD_ID());
        }
        if (mObjCTV.getADDRESS() != null) {
            edt_address_ctv.setText(mObjCTV.getADDRESS());
        }
        if (mObjCTV.getBALANCE() != null) {
            String styledText = "Số dư tài khoản hiện tại: <font color='#FF5C03'><b> "
                    + StringUtil.conventMonney_Long(mObjCTV.getBALANCE()) + " </b></font>";
            // ((TitleViewHolder) holder).txt_madiemdo.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
            txt_title_bonus.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
        } else {
            String styledText = "Số dư tài khoản hiện tại: <font color='#1263BB'> 0đ </font>";
            // ((TitleViewHolder) holder).txt_madiemdo.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
            txt_title_bonus.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
        }
        if (mObjCTV.getAVATAR() != null) {
            sAvata = mObjCTV.getAVATAR();
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.ic_avata_all)
                    .error(R.drawable.ic_avata_all);
            Glide.with(this).load(mObjCTV.getAVATAR())
                    .apply(options).into(img_avata_ctv_detail);
        } else
            Glide.with(this).load(R.drawable.ic_avata_all).into(img_avata_ctv_detail);
        if (mObjCTV.getIMG1() != null) {
            sImgCmtFont = mObjCTV.getIMG1();
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.ic_camera_color)
                    .error(R.drawable.ic_camera_color);
            Glide.with(this).load(mObjCTV.getIMG1())
                    .apply(options).into(img_1_cmnd);
        } else
            Glide.with(this).load(R.drawable.ic_camera_color).into(img_1_cmnd);
        if (mObjCTV.getIMG2() != null) {
            sImgCmtBack = mObjCTV.getIMG2();
            String sUrl = mObjCTV.getIMG2();
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.ic_camera_color)
                    .error(R.drawable.ic_camera_color);
            Glide.with(this).load(sUrl)
                    .apply(options).into(img_2_cmnd);
        } else
            Glide.with(this).load(R.drawable.ic_camera_color).into(img_2_cmnd);
    }

    private void init() {
    }

    City objCity = new City();
    Districts objDistrict = new Districts();
    Ward objWard = new Ward();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        /*    case R.id.txt_title_reset_pass:
                String sUserName = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
                showDialogLoading();
                mPresenter.api_reset_pass_ctv(sUserName, mObjCTV.getUSERNAME());
                break;*/
            case R.id.ll_bankname:
                App.mCity = null;
                App.mDistrict = null;
                Intent intent_bank = new Intent(ActivityCtvDetail.this, ActivityListBank.class);
                startActivityForResult(intent_bank, Constants.RequestCode.GET_Bank);
                break;

            case R.id.txt_city_ctv:
                App.mCity = null;
                App.mDistrict = null;
                Intent intent_city = new Intent(ActivityCtvDetail.this,
                        ActivityListCity.class);
                startActivityForResult(intent_city, Constants.RequestCode.GET_CITY);
                break;
            case R.id.ll_gender:
                App.mCity = null;
                App.mDistrict = null;
                Intent intent_gender = new Intent(ActivityCtvDetail.this, ActivityGender.class);
                startActivityForResult(intent_gender, Constants.RequestCode.GET_GENDER);
                break;
            case R.id.txt_district_ctv:
                if (objCity != null && objCity.getMATP() != null) {
                    App.mDistrict = null;
                    Intent intent_district = new Intent(ActivityCtvDetail.this, ActivityDistrict.class);
                    intent_district.putExtra(Constants.KEY_SEND_ID_CITY, objCity.getMATP());
                    startActivityForResult(intent_district, Constants.RequestCode.GET_DISTRICT);
                } else
                    showDialogNotify("Thông báo", "Bạn chưa chọn tỉnh thành phố nào.");
                break;
            case R.id.txt_ward_ctv:
                if (objDistrict != null && objDistrict.getMAQH() != null) {
                    App.mWard = null;
                    Intent intent_district = new Intent(ActivityCtvDetail.this, ActivityListWard.class);
                    intent_district.putExtra(Constants.KEY_SEND_ID_DISTRICT, objDistrict.getMAQH());
                    startActivityForResult(intent_district, Constants.RequestCode.GET_WARD);
                } else
                    showDialogNotify("Thông báo", "Bạn chưa chọn quận huyện nào.");
                break;
        }
    }


    @Override
    public void show_error_api() {
        hideDialogLoading();
    }

    @Override
    public void show_get_list_ctv(ResponGetLisCTV obj) {

    }

    @Override
    public void show_update_ctv(ErrorApi obj) {
        hideDialogLoading();
        if (obj.getsERROR().equals("0000")) {
            Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().postSticky(new MessageEvent(Constants.EventBus.KEY_UPDATE_USER, 1, 0));
            setResult(RESULT_OK, new Intent());
            finish();
        } else
            showAlertDialog("Thông báo", "" + obj.getsRESULT());
    }

    @Override
    public void show_reset_pass_ctv(ErrorApi obj) {
        hideDialogLoading();
        if (obj.getsERROR().equals("0000")) {
            showAlertDialog("Thông báo", "" + obj.getsRESULT());
            // Toast.makeText(this, "Reset mật khẩu thành công", Toast.LENGTH_SHORT).show();
        } else
            showAlertDialog("Thông báo", "" + obj.getsRESULT());
    }

    @Override
    public void show_ctv_detail(ObjCTV objLogin) {
        hideDialogLoading();
        if (objLogin != null && objLogin.getsERROR().equals("0000")) {
            set_info_ctv(objLogin);
        }
    }

    @Override
    public void show_list_ctv_child(ResponGetLisCTV objLogin) {

    }


    private void checkPermissionsAndOpenFilePicker() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            get_file_picker();
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    private ArrayList<MediaFile> mediaFiles = new ArrayList<>();
    public static final int PERMISSIONS_REQUEST_CODE = 0;
    private final static int FILE_REQUEST_CODE = 1;
    String IMAGE_PATH = "";
    String IMAGE_PATH_2 = "";
    String IMAGE_PATH_AVATA = "";

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    get_file_picker();
                } else {
                    showError();
                }
            }
        }
    }

    private void get_file_picker() {
        mediaFiles.clear();
        Intent intent = new Intent(ActivityCtvDetail.this, FilePickerActivity.class);
        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                .setCheckPermission(true)
                .setSelectedMediaFiles(mediaFiles)
                .setShowFiles(false)
                .setShowImages(true)
                .setShowVideos(false)
                .setShowAudios(false)
                .setMaxSelection(1)
                .enableImageCapture(true)
                // .setRootPath(Environment.getExternalStorageDirectory().getPath())
                .build());
        startActivityForResult(intent, FILE_REQUEST_CODE);
    }

    private void showError() {
        Toast.makeText(this, "Allow external storage reading", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            mediaFiles.clear();
            mediaFiles.addAll(data.<MediaFile>getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES));
            if (mediaFiles != null && mediaFiles.size() > 0) {
                if (sOpionImage.length() > 0 && sOpionImage.equals("0")) {
                    IMAGE_PATH = mediaFiles.get(0).getPath();
                    File imgFile = new File(IMAGE_PATH);
                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        img_1_cmnd.setImageBitmap(myBitmap);
                    }
                } else if (sOpionImage.length() > 0 && sOpionImage.equals("1")) {
                    IMAGE_PATH_2 = mediaFiles.get(0).getPath();
                    File imgFile = new File(IMAGE_PATH_2);
                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        img_2_cmnd.setImageBitmap(myBitmap);
                    }

                } else if (sOpionImage.length() > 0 && sOpionImage.equals("3")) {
                    IMAGE_PATH_AVATA = mediaFiles.get(0).getPath();
                    File imgFile = new File(IMAGE_PATH_AVATA);
                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        img_avata_ctv_detail.setImageBitmap(myBitmap);
                    }

                }
                //fileListAdapter.notifyDataSetChanged();

            } else {

            }
        }
        switch (requestCode) {
            case Constants.RequestCode.GET_CITY:
                if (resultCode == RESULT_OK) {
                    if (App.mCity != null) {
                        objCity = App.mCity;
                    }
                    if (objCity != null && objCity.getNAME() != null) {
                        txt_city_ctv.setText(objCity.getNAME());
                        txt_district_ctv.setText("");
                    } else {
                        txt_city_ctv.setText("");
                        txt_district_ctv.setText("");
                    }
                }
                break;
            case Constants.RequestCode.GET_Bank:
                if (App.mBankName != null) {
                    edt_name_bank_ctv.setText(App.mBankName.getsNameBank());
                } else {

                }

                break;
            case Constants.RequestCode.GET_DISTRICT:
                if (App.mDistrict != null) {
                    objDistrict = App.mDistrict;
                }
                if (objDistrict != null && objDistrict.getNAME() != null) {
                    txt_district_ctv.setText(App.mDistrict.getNAME());
                } else
                    txt_district_ctv.setText("");
                break;
            case Constants.RequestCode.GET_WARD:
                if (App.mWard != null) {
                    objWard = App.mWard;
                }
                if (objWard != null && objWard.getNAME() != null) {
                    txt_ward_ctv.setText(objWard.getNAME());
                } else
                    txt_ward_ctv.setText("");
                break;
            case Constants.RequestCode.GET_GENDER:
                if (App.mGender != null) {
                    edt_gender_ctv.setText(App.mGender.getsName());
                    sGender = App.mGender.getsId();
                }

                break;
        }
    }

    @Override
    public void show_error_api_uploadimage() {
        get_api_update();
    }

    @Override
    public void show_upload_image(ErrorApi objError) {
        if (objError != null && objError.getsERROR().equals("0000")) {
            if (objError.getsNameImage() != null) {
                switch (objError.getsNameImage()) {
                    case "avata":
                        sAvata = objError.getURL();
                        if (IMAGE_PATH.length() > 0) {
                            mPresenterUpload.api_upload_image(IMAGE_PATH, "font_img_cmt");
                        } else {
                            if (IMAGE_PATH_2.length() > 0) {
                                mPresenterUpload.api_upload_image(IMAGE_PATH_2, "back_img_cmt");
                            } else {
                                get_api_update();
                            }
                        }
                        break;
                    case "font_img_cmt":
                        sImgCmtFont = objError.getURL();
                        if (IMAGE_PATH_2.length() > 0) {
                            mPresenterUpload.api_upload_image(IMAGE_PATH_2, "back_img_cmt");
                        } else {
                            get_api_update();
                        }
                        break;
                    case "back_img_cmt":
                        sImgCmtBack = objError.getURL();
                        get_api_update();
                        break;
                }
            }
        }
    }

}
