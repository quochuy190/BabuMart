package com.vn.babumart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.vn.babumart.R;
import com.vn.babumart.callback.ItemClickCartListener;
import com.vn.babumart.models.ReportDefault;
import com.vn.babumart.untils.StringUtil;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterReportDefault extends RecyclerView.Adapter<AdapterReportDefault.TopicViewHoder> {
    private List<ReportDefault> mList;
    private Context context;
    private ItemClickCartListener OnIListener;


    public ItemClickCartListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickCartListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterReportDefault(List<ReportDefault> listAirport, Context context) {
        this.mList = listAirport;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_table_report_default, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(final TopicViewHoder holder, final int position) {
        final ReportDefault obj = mList.get(position);
        if (obj.getTOTAL_COMMISSION() != null)
            holder.txt_hh.setText(StringUtil.conventMonney_Long(obj.getTOTAL_COMMISSION()));
        else
            holder.txt_hh.setText("0 đ");
        if (obj.getTOTAL_TT() != null)
            holder.txt_thucthu.setText(StringUtil.conventMonney_Long(obj.getTOTAL_TT()));
        else
            holder.txt_thucthu.setText("0 đ");
        if (obj.getTOTAL_MONEY() != null)
            holder.txt_doanhso.setText(StringUtil.conventMonney_Long(obj.getTOTAL_MONEY()));
        else
            holder.txt_doanhso.setText("0 đ");
        if (obj.getTOTAL_ORDER() != null)
            holder.txt_quantity.setText(obj.getTOTAL_ORDER());
        else
            holder.txt_quantity.setText("0");
        switch (obj.getREPORT_TYPE()) {
            case "1":
                holder.txt_year.setText(obj.getYEAR());
                break;
            case "2":
                holder.txt_year.setText(obj.getMONTH());
                break;
            case "3":
                holder.txt_year.setText(obj.getDAY());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_year)
        TextView txt_year;
        @BindView(R.id.txt_quantity)
        TextView txt_quantity;
        @BindView(R.id.txt_doanhso)
        TextView txt_doanhso;
        @BindView(R.id.txt_hh)
        TextView txt_hh;
        @BindView(R.id.txt_thucthu)
        TextView txt_thucthu;


        public TopicViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //   itemView.setOnClickListener(this);
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

    public void updateList(List<ReportDefault> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
