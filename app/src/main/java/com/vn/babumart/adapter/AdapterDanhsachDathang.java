package com.vn.babumart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.babumart.R;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.models.ObjOrder;
import com.vn.babumart.untils.StringUtil;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 25-July-2019
 * Time: 15:59
 * Version: 1.0
 */
public class AdapterDanhsachDathang extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ObjOrder> mList;
    private ItemClickListener itemClickListener;
    private Context context;
    public static final int ITEM_VIEW_HOLDER = 0;
    public static final int ITEM_VIEW_LOADMORE = 1;

    public AdapterDanhsachDathang(List<ObjOrder> mList, Context mContext) {
        this.mList = mList;
        this.context = mContext;
    }

    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ITEM_VIEW_HOLDER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,
                        parent, false);
                return new TopicViewHoder(view);
            case ITEM_VIEW_LOADMORE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,
                        parent, false);
                return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderAll, int position) {
        try {
            ObjOrder obj = mList.get(position);
            if (obj == null) {
                ((LoadingViewHolder) holderAll).progressBar.setVisibility(View.VISIBLE);
            } else {
                TopicViewHoder holder = (TopicViewHoder) holderAll;
                if (obj != null) {
                    if (obj.getFULL_NAME_CTV() != null && obj.getFULL_NAME_CTV().length() > 0)
                        holder.txt_name_CTV.setText("Tên CTV: " + obj.getFULL_NAME_CTV());
                    else
                        holder.txt_name_CTV.setText("...");
                    if (obj.getCREATE_DATE() != null && obj.getCREATE_DATE().length() > 0)
                        holder.txt_time_start_order.setText(obj.getCREATE_DATE());
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
                        if (obj.getDISCOUNT() != null && obj.getDISCOUNT().length() > 0) {
                            long price = Long.parseLong(obj.getTOTAL_MONEY()) - Long.parseLong(obj.getDISCOUNT()
                                    .replaceAll("\\.", "").replaceAll(",", "")
                                    .replaceAll("đ", "").trim());
                            holder.txt_item_order_total_prime.setText(StringUtil.conventMonney_Long("" + price));
                        } else {
                            holder.txt_item_order_total_prime.setText(StringUtil.conventMonney_Long(obj.getTOTAL_MONEY()));
                        }

                    } else {
                        holder.txt_item_order_total_prime.setText("0đ");
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
                                holder.txt_item_order_status.setText(obj.getSTATUS_NAME());
                                holder.txt_item_order_status.setBackground(context.getResources()
                                        .getDrawable(R.drawable.spr_txt_status_hoanthanh));
                                break;
                            case "1":
                                holder.txt_item_order_status.setText(obj.getSTATUS_NAME());
                                holder.txt_item_order_status.setBackground(context.getResources()
                                        .getDrawable(R.drawable.spr_txt_status_datiepnhan));
                                break;
                            case "2":
                                holder.txt_item_order_status.setText(obj.getSTATUS_NAME());
                                holder.txt_item_order_status.setBackground(context.getResources()
                                        .getDrawable(R.drawable.spr_txt_status_dangxuly));
                                break;
                            case "3":
                                holder.txt_item_order_status.setText(obj.getSTATUS_NAME());
                                holder.txt_item_order_status.setBackground(context.getResources()
                                        .getDrawable(R.drawable.spr_txt_status_dangxuly));
                                break;
                            case "4":
                                holder.txt_item_order_status.setText(obj.getSTATUS_NAME());
                                holder.txt_item_order_status.setBackground(context.getResources()
                                        .getDrawable(R.drawable.spr_txt_status_dahuy));
                                break;

                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (mList != null) {
            ObjOrder object = mList.get(position);
            if (object != null) {
                return ITEM_VIEW_HOLDER;

            } else
                return ITEM_VIEW_LOADMORE;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mList.size();
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
            itemClickListener.onClickItem(getLayoutPosition(), mList.get(getLayoutPosition()));
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
}
