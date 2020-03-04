package com.vn.babumart.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vn.babumart.R;
import com.vn.babumart.activity.commission.ActivityHhDetail;
import com.vn.babumart.adapter.AdapterListCommissions;
import com.vn.babumart.base.BaseFragment;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.models.ObjCommissions;
import com.vn.babumart.untils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author Quốc Huy
 * @version 1.0.0
 * @description
 * @desc Developer NEO Company.
 * @created 8/6/2018
 * @updated 8/6/2018
 * @modified by
 * @updated on 8/6/2018
 * @since 1.0
 */
public class FragmentCommissions extends BaseFragment {
    private static final String TAG = "FragmentOrder";
    public static FragmentCommissions fragment;
    private List<ObjCommissions> mList;
    private List<ObjCommissions> temp;
    private AdapterListCommissions adapterService;
    @BindView(R.id.recycle_list_hh)
    RecyclerView recycle_list_hh;
    @BindView(R.id.edt_search_hh)
    EditText edt_search_hh;
    RecyclerView.LayoutManager mLayoutManager;

    public static FragmentCommissions getInstance() {
        if (fragment == null) {
            synchronized (FragmentCommissions.class) {
                if (fragment == null)
                    fragment = new FragmentCommissions();
            }
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commissions_home, container, false);
        ButterKnife.bind(this, view);
        Log.e(TAG, "onCreateView: Order");
        init();
        initEvent();
        initData();
        return view;
    }

    private void initEvent() {
        edt_search_hh.addTextChangedListener(new TextWatcher() {
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
                filter(s.toString());
            }
        });
    }

    @SuppressLint("WrongConstant")
    private void init() {
        mList = new ArrayList<>();
        adapterService = new AdapterListCommissions(mList, getContext());
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycle_list_hh.setNestedScrollingEnabled(false);
        recycle_list_hh.setHasFixedSize(true);
        recycle_list_hh.setLayoutManager(mLayoutManager);
        recycle_list_hh.setItemAnimator(new DefaultItemAnimator());
        recycle_list_hh.setAdapter(adapterService);
        adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                Intent intent = new Intent(getContext(), ActivityHhDetail.class);
                getActivity().startActivity(intent);
            }
        });
    }

    private void initData() {
        mList.add(new ObjCommissions("Nguyễn Quốc Huy", "abc", "5000000"));
        mList.add(new ObjCommissions("Hoàng văn A", "abc", "5000000"));
        mList.add(new ObjCommissions("Nguyễn Văn B", "abc", "5000000"));
        mList.add(new ObjCommissions("Trần Quốc Toản", "abc", "5000000"));
        mList.add(new ObjCommissions("Lý Trọng Toàn", "abc", "5000000"));
        mList.add(new ObjCommissions("Nguyễn Quang Diệu", "abc", "5000000"));
        mList.add(new ObjCommissions("Bùi Thị Xuân", "abc", "5000000"));
        adapterService.notifyDataSetChanged();
    }


    void filter(String text) {
        temp = new ArrayList<>();
        for (ObjCommissions d : mList) {
            String sName = StringUtil.removeAccent(d.getNAMECTV().toLowerCase());
            String sUser = StringUtil.removeAccent(d.getMA_CTV().toLowerCase());
            String sInput = StringUtil.removeAccent(text.toLowerCase());
            if (sName.contains(sInput) || sUser.contains(sInput)) {
                //adding the element to filtered list
                temp.add(d);
            }
        }
        adapterService.updateList(temp);
        adapterService.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                Intent intent = new Intent(getContext(),ActivityHhDetail.class);
                getActivity().startActivity(intent);
            }
        });
    }


}
