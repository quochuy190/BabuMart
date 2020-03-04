package com.vn.babumart.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.babumart.R;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.models.ObjCommissions;
import com.vn.babumart.untils.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterRequestPay extends RecyclerView.Adapter<AdapterRequestPay.TopicViewHoder> {
    private List<ObjCommissions> mList;
    private Context context;
    private ItemClickListener OnIListener;


    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterRequestPay(List<ObjCommissions> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_report_pay_com, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, final int position) {
        ObjCommissions obj = mList.get(position);
        if (obj.getNAMECTV() != null)
            holder.txt_name_ctv.setText("Tên CTV: " + obj.getNAMECTV());
        else
            holder.txt_name_ctv.setText("Tên CTV: ...");
        if (obj.getUSER_CODE() != null)
            holder.txt_usercode_ctv.setText("Mã CTV: " + obj.getUSER_CODE());
        else
            holder.txt_usercode_ctv.setText("Mã CTV: ...");
        if (obj.getUPDATE_TIME() != null){
          //  holder.txt_time.setText("Thời gian: " + obj.getUPDATE_TIME());
            String sVersionCode = "Thời gian: <i>"+obj.getUPDATE_TIME()+"</i>";
            holder.txt_time.setText(Html.fromHtml(sVersionCode), TextView.BufferType.SPANNABLE);
        }

        else
            holder.txt_time.setText("Thời gian: ...");
        if (obj.getTOTAL_HH() != null) {
            String sVersionCode = "Tổng hoa hồng: <font color='#149cc6'><b>"
                    + StringUtil.conventMonney_Long(obj.getTOTAL_HH()) + "</b></font>";
            holder.txt_sodu.setText(Html.fromHtml(sVersionCode), TextView.BufferType.SPANNABLE);
            // holder.txt_sodu.setText("Số dư tài khoản: " + StringUtil.conventMonney_Long(obj.getTOTAL_HH()));
        } else
            holder.txt_sodu.setText("Số dư tài khoản: ...");
        if (obj.getAMOUNT() != null)
            holder.txt_num_rut.setText(StringUtil.conventMonney_Long(obj.getAMOUNT()));
        else
            holder.txt_num_rut.setText("");
        if (obj.getSTATUS() != null && obj.getSTATUS().equals("1")) {
            String sVersionCode = "Trạng thái: <font color='#149cc6'><b>Đã thanh toán</b></font>";
            holder.txt_status.setText(Html.fromHtml(sVersionCode), TextView.BufferType.SPANNABLE);
        } else {
            String sVersionCode = "Trạng thái: <font color='#FF5C03'><b>Chưa xử lý</b></font>";
            holder.txt_status.setText(Html.fromHtml(sVersionCode), TextView.BufferType.SPANNABLE);
        }
        if (obj.getCOMMENTS() != null) {
            holder.txt_content.setText("Nội dung: " + obj.getCOMMENTS());
        } else
            holder.txt_content.setText("Nội dung:");

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_name_ctv)
        TextView txt_name_ctv;
        @BindView(R.id.txt_usercode_ctv)
        TextView txt_usercode_ctv;
        @BindView(R.id.txt_time)
        TextView txt_time;
        @BindView(R.id.txt_sodu)
        TextView txt_sodu;
        @BindView(R.id.txt_num_rut)
        TextView txt_num_rut;
        @BindView(R.id.txt_status)
        TextView txt_status;
        @BindView(R.id.txt_content)
        TextView txt_content;
        @BindView(R.id.item_request)
        ConstraintLayout item_request;

        public TopicViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            OnIListener.onClickItem(getLayoutPosition(), mList.get(getLayoutPosition()));
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public void updateList(List<ObjCommissions> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
