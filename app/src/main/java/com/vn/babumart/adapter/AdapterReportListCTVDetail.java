package com.vn.babumart.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.vn.babumart.R;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.models.ReportListCTV;
import com.vn.babumart.untils.StringUtil;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterReportListCTVDetail extends RecyclerView.Adapter<AdapterReportListCTVDetail.TopicViewHoder> {
    private List<ReportListCTV> mList;
    private Context context;
    private ItemClickListener OnIListener;


    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterReportListCTVDetail(List<ReportListCTV> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_report_ctv_detail, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        ReportListCTV obj = mList.get(position);
        if (position != 0) {
            holder.txt_time_start.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
            holder.txt_time_end.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
            holder.txt_doanhso.setTextColor(context.getResources().getColor(R.color.orange));
            holder.txt_hoahong.setTextColor(context.getResources().getColor(R.color.green_stroke));
            if (obj != null && obj.getCREATE_DATE() != null) {
                holder.txt_time_start.setText(obj.getCREATE_DATE());
            } else
                holder.txt_time_start.setText("...");
            if (obj != null && obj.getCODE_ORDER() != null) {
                holder.txt_code_order.setText(obj.getCODE_ORDER());
            } else
                holder.txt_code_order.setText("...");
            if (obj != null && obj.getFN_TIME() != null) {
                holder.txt_time_end.setText(obj.getFN_TIME());
            } else
                holder.txt_time_end.setText("...");
            if (obj != null && obj.getSUM_MONEY() != null) {
                holder.txt_doanhso.setText(StringUtil.conventMonney_Long(obj.getSUM_MONEY()));
            } else
                holder.txt_doanhso.setText("...");
            if (obj != null && obj.getSUM_COMMISSION() != null) {
                holder.txt_hoahong.setText(StringUtil.conventMonney_Long(obj.getSUM_COMMISSION()));
            } else
                holder.txt_hoahong.setText("...");
        } else if (position == 0) {
            holder.txt_hoahong.setGravity(Gravity.CENTER);
            holder.txt_doanhso.setGravity(Gravity.CENTER);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_time_start)
        TextView txt_time_start;
        @BindView(R.id.txt_time_end)
        TextView txt_time_end;
        @BindView(R.id.txt_code_order)
        TextView txt_code_order;
        @BindView(R.id.txt_doanhso)
        TextView txt_doanhso;
        @BindView(R.id.txt_hoahong)
        TextView txt_hoahong;

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

    public void updateList(List<ReportListCTV> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
