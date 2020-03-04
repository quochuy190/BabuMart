package com.vn.babumart.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.vn.babumart.R;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.models.ObjNotify;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterListNotify extends RecyclerView.Adapter<AdapterListNotify.TopicViewHoder> {
    private List<ObjNotify> mList;
    private Context context;
    private ItemClickListener OnIListener;


    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterListNotify(List<ObjNotify> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notify, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        ObjNotify obj = mList.get(position);
        if (obj != null && obj.getCONTENT() != null && obj.getCONTENT().length() > 0) {
            if (obj.getIS_READ().equals("0")) {

                String sVersionCode = "<font color='#149cc6'><b>" + obj.getCONTENT() + "</b></font>";
                holder.txt_content.setText(Html.fromHtml(sVersionCode), TextView.BufferType.SPANNABLE);
            } else {
                String sVersionCode = "<font color='#0a0909'>" + obj.getCONTENT() + "</font>";
                holder.txt_content.setText(Html.fromHtml(sVersionCode), TextView.BufferType.SPANNABLE);
            }

        }
        if (obj != null && obj.getSENT_TIME() != null && obj.getSENT_TIME().length() > 0)
            holder.txt_notify_time.setText(obj.getSENT_TIME());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_notify_name)
        TextView txt_notify_name;
        @BindView(R.id.txt_notify_time)
        TextView txt_notify_time;
        @BindView(R.id.txt_content)
        TextView txt_content;
        @BindView(R.id.icon_notify)
        ImageView icon_notify;

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

    public void updateList(List<ObjNotify> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
