package com.vn.babumart.adapter;

import android.content.Context;
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

public class AdapterReportListCTV extends RecyclerView.Adapter<AdapterReportListCTV.TopicViewHoder> {
    private List<ReportListCTV> mList;
    private Context context;
    private ItemClickListener OnIListener;


    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterReportListCTV(List<ReportListCTV> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.iten_chart_list_ctv, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        ReportListCTV obj = mList.get(position);
        if (position != 0) {
            holder.txt_name.setGravity(Gravity.LEFT);
            holder.txt_doanhso.setTextColor(context.getResources().getColor(R.color.orange));
            holder.txt_hoahong.setTextColor(context.getResources().getColor(R.color.green_stroke));
            if (obj != null && obj.getFULL_NAME() != null) {
                holder.txt_name.setText(obj.getFULL_NAME());
            } else
                holder.txt_name.setText("...");
            if (obj != null && obj.getSUM_ORDER() != null) {
                holder.txt_donhang.setText(obj.getSUM_ORDER());
            } else
                holder.txt_donhang.setText("...");
            if (obj != null && obj.getSUM_MONEY() != null) {
                holder.txt_doanhso.setText(StringUtil.conventMonney_Long(obj.getSUM_MONEY()));
            } else
                holder.txt_doanhso.setText("...");
            if (obj != null && obj.getSUM_COMMISSION() != null) {
                holder.txt_hoahong.setText(StringUtil.conventMonney_Long(obj.getSUM_COMMISSION()));
            } else
                holder.txt_hoahong.setText("...");
        } else if (position == 0) {
            holder.txt_doanhso.setTextColor(context.getResources().getColor(R.color.black));
            holder.txt_hoahong.setTextColor(context.getResources().getColor(R.color.black));
            holder.txt_name.setText("Tên CTV");
            holder.txt_donhang.setText("Số ĐH");
            holder.txt_doanhso.setText("Doanh số");
            holder.txt_hoahong.setText("Hoa hồng");

            holder.txt_name.setGravity(Gravity.CENTER);
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
        @BindView(R.id.txt_name)
        TextView txt_name;
        @BindView(R.id.txt_donhang)
        TextView txt_donhang;
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
