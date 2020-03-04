package com.vn.babumart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.babumart.R;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.models.ReportProduct;
import com.vn.babumart.untils.StringUtil;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterReportProduct extends RecyclerView.Adapter<AdapterReportProduct.TopicViewHoder> {
    private List<ReportProduct> mList;
    private Context context;
    private ItemClickListener OnIListener;


    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterReportProduct(List<ReportProduct> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_table_report_product, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        ReportProduct obj = mList.get(position);
        if (obj.getsTypeReport().equals("4")) {
            if (obj.getPRODUCT_NAME() != null) {
                holder.txt_name.setText(obj.getPRODUCT_NAME().toLowerCase());
            } else {
                holder.txt_name.setText("....");
            }
        } else {
            if (obj.getNAME() != null) {
                holder.txt_name.setText(obj.getNAME());
            } else {
                holder.txt_name.setText("....");
            }
        }

        if (obj.getTOTAL_ORDER() != null) {
            holder.txt_pantity.setText(obj.getTOTAL_ORDER());
        } else {
            holder.txt_pantity.setText("....");
        }
        if (obj.getTOTAL_QUANTITY() != null) {
            holder.txt_sanluong.setText(obj.getTOTAL_QUANTITY());
        } else {
            holder.txt_sanluong.setText("....");
        }
        if (obj.getTOTAL_REVENUE() != null) {

            holder.txt_doanhso.setText(StringUtil.conventMonney_Long(obj.getTOTAL_REVENUE()));
        } else {
            holder.txt_doanhso.setText("....");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_name)
        TextView txt_name;
        @BindView(R.id.txt_pantity)
        TextView txt_pantity;
        @BindView(R.id.txt_sanluong)
        TextView txt_sanluong;
        @BindView(R.id.txt_doanhso)
        TextView txt_doanhso;
        @BindView(R.id.ll_item_report_product)
        LinearLayout ll_item_report_product;

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

    public void updateList(List<ReportProduct> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
