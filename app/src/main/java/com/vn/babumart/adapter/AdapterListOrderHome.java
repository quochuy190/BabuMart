package com.vn.babumart.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.babumart.R;
import com.vn.babumart.callback.ILoadMore;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.models.ObjOrder;
import com.vn.babumart.untils.StringUtil;
import com.vn.babumart.untils.TimeUtils;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterListOrderHome extends RecyclerView.Adapter<AdapterListOrderHome.TopicViewHoder> {
    private Context context;
    private ItemClickListener OnIListener;
    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    ILoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<ObjOrder> items;
    int visibleThreshold = 5;
    int lastVisibleItem, totalItemCount;

    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterListOrderHome(List<ObjOrder> listAirport, Context context) {
        this.items = listAirport;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        ObjOrder obj = items.get(position);
        if (obj != null) {
            if (obj.getFULL_NAME_CTV() != null && obj.getFULL_NAME_CTV().length() > 0)
                holder.txt_name_CTV.setText("Tên CTV: " + obj.getFULL_NAME_CTV());
            else
                holder.txt_name_CTV.setText("...");
            if (obj.getCREATE_DATE() != null && obj.getCREATE_DATE().length() > 0)
                holder.txt_time_start_order.setText(TimeUtils.convent_date(obj.getCREATE_DATE(),
                        "dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy HH:mm"));
            else
                holder.txt_time_start_order.setText("...");
            if (obj.getSTATUS() != null) {
                holder.txt_item_order_status.setText(obj.getSTATUS_NAME().toUpperCase());
            } else {
                holder.txt_item_order_status.setText("...");
            }
            if (obj.getCREATE_BY() != null) {
                holder.txt_item_order_id_CTV.setText("Mã CTV: " + obj.getUSER_CODE().toUpperCase());
            } else {
                holder.txt_item_order_id_CTV.setText("Mã CTV: ...");
            }
            if (obj.getCODE_ORDER() != null) {
                holder.txt_code_order.setText("Mã ĐH: " + obj.getCODE_ORDER().toUpperCase());
            } else {
                holder.txt_code_order.setText("...");
            }
            if (obj.getTOTAL_MONEY() != null) {
                holder.txt_item_order_total_prime.setText(StringUtil.conventMonney_Long(obj.getTOTAL_MONEY()));
            } else {
                holder.txt_item_order_total_prime.setText("0 đ");
            }
            if (obj.getFULLNAME_RECEIVER() != null) {
                holder.txt_item_order_name_customer.setText(obj.getFULLNAME_RECEIVER());
            } else {
                holder.txt_item_order_name_customer.setText("....");
            }
            if (obj.getMOBILE_RECCEIVER() != null) {
                holder.txt_phone_customer.setText(obj.getMOBILE_RECCEIVER());
            } else {
                holder.txt_phone_customer.setText("...");
            }
            if (obj.getSTATUS() != null) {
                switch (obj.getSTATUS()) {
                    case "0":
                        holder.txt_item_order_status.setText("Đã hoàn thành");
                        holder.txt_item_order_status.setBackground(context.getResources()
                                .getDrawable(R.drawable.spr_txt_status_order_orange));
                        break;
                    case "1":
                        holder.txt_item_order_status.setText("Đang xử lý");
                        holder.txt_item_order_status.setBackground(context.getResources()
                                .getDrawable(R.drawable.spr_txt_status_order_blue));
                        break;
                    case "2":
                        holder.txt_item_order_status.setText("Đã tiếp nhận");
                        holder.txt_item_order_status.setBackground(context.getResources()
                                .getDrawable(R.drawable.spr_txt_status_order_green));
                        break;
                    case "3":
                        holder.txt_item_order_status.setText("Đang vận chuyển");
                        holder.txt_item_order_status.setBackground(context.getResources()
                                .getDrawable(R.drawable.spr_txt_status_order_green));
                        break;
                    case "4":
                        holder.txt_item_order_status.setText("Đã huỷ");
                        holder.txt_item_order_status.setBackground(context.getResources()
                                .getDrawable(R.drawable.spr_txt_status_order_red));
                        break;

                }
            }
        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_item_order_name_CTV)
        TextView txt_name_CTV;
        @BindView(R.id.txt_item_order_time)
        TextView txt_time_start_order;
        @BindView(R.id.txt_item_order_status)
        TextView txt_item_order_status;
        @BindView(R.id.txt_phone_customer)
        TextView txt_phone_customer;
        @BindView(R.id.txt_code_order)
        TextView txt_code_order;
        @BindView(R.id.txt_item_order_id_CTV)
        TextView txt_item_order_id_CTV;
        @BindView(R.id.txt_item_order_name_customer)
        TextView txt_item_order_name_customer;
        @BindView(R.id.txt_item_order_total_prime)
        TextView txt_item_order_total_prime;

        public TopicViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            OnIListener.onClickItem(getLayoutPosition(), items.get(getLayoutPosition()));
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        }
    }

    public void updateList(List<ObjOrder> list) {
        items = list;
        notifyDataSetChanged();
    }
}
