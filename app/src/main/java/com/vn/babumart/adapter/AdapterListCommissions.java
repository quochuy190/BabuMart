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
import com.vn.babumart.models.ObjCommissions;
import com.vn.babumart.untils.StringUtil;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterListCommissions extends RecyclerView.Adapter<AdapterListCommissions.TopicViewHoder> {
    private List<ObjCommissions> mList;
    private Context context;
    private ItemClickListener OnIListener;


    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterListCommissions(List<ObjCommissions> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_commission, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, final int position) {
        ObjCommissions obj = mList.get(position);
      /*  if (position % 2 == 0) {
            holder.txt_name_hh_item.setBackgroundResource(R.color.green_holde_1_table);
            holder.txt_user_hh_item.setBackgroundResource(R.color.green_holde_1_table);
            holder.txt_total_hh.setBackgroundResource(R.color.green_holde_1_table);
        } else {
            holder.txt_name_hh_item.setBackgroundResource(R.color.green_holde_2_table);
            holder.txt_user_hh_item.setBackgroundResource(R.color.green_holde_2_table);
            holder.txt_total_hh.setBackgroundResource(R.color.green_holde_2_table);
        }*/
        if (obj.getNAMECTV() != null && obj.getNAMECTV().length() > 0)
            holder.txt_name_hh_item.setText(obj.getNAMECTV());
/*
        if (obj.getMA_CTV() != null && obj.getMA_CTV().length() > 0)
            holder.txt_user_hh_item.setText(obj.getMA_CTV());
*/

        if (obj.getUSER_CODE() != null && obj.getUSER_CODE().length() > 0)
            holder.txt_user_hh_item.setText(obj.getUSER_CODE());
        else
            holder.txt_user_hh_item.setText("...");
        if (obj.getTOTAL_HH() != null && obj.getTOTAL_HH().length() > 0)
            holder.txt_total_hh.setText(StringUtil.conventMonney_Long(obj.getTOTAL_HH()));
        holder.txt_name_hh_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnIListener.onClickItem(position, mList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_name_hh_item)
        TextView txt_name_hh_item;
        @BindView(R.id.txt_user_hh_item)
        TextView txt_user_hh_item;
        @BindView(R.id.txt_total_hh_item)
        TextView txt_total_hh;
        @BindView(R.id.ll_item_commission)
        LinearLayout ll_item_commission;

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
