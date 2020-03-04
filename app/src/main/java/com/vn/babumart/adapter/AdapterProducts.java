package com.vn.babumart.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vn.babumart.R;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.models.Products;
import com.vn.babumart.untils.StringUtil;
import com.vn.babumart.untils.TimeUtils;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.TopicViewHoder> {
    private List<Products> mList;
    private Context context;
    private ItemClickListener OnIListener;


    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterProducts(List<Products> listAirport, Context context) {
        this.mList = listAirport;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_home, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        Products objService = mList.get(position);
        if (objService.getsName() != null)
            holder.txt_name.setText(objService.getsName());
        if (objService.getsPrice() != null) {
            if (objService.getCOMISSION_PRODUCT() != null) {
                set_hh_sp(objService.getCOMISSION_PRODUCT(), objService.getsPrice(), holder.txt_commission_product);
            } else {
                holder.txt_commission_product.setText("Hoa hồng sp: 0%");
            }
            holder.txt_prime.setText(StringUtil.conventMonney_Long(objService.getsPrice()));
        }

        if (objService.getPRICE_PROMOTION() != null) {
            if (objService.getSTART_PROMOTION() != null && objService.getEND_PROMOTION() != null) {
                if (TimeUtils.compare_two_date_currenttime(objService.getSTART_PROMOTION(),
                        objService.getEND_PROMOTION())) {
                    holder.txt_commission_phantram.setVisibility(View.VISIBLE);
                    holder.txt_price_delete.setVisibility(View.VISIBLE);
                    holder.txt_price_delete.setText(StringUtil.conventMonney_Long(objService.getsPrice()));
                    holder.view_delete.setVisibility(View.VISIBLE);
                    try {
                        int price = Integer.parseInt(objService.getsPrice());
                        int price_promotion = Integer.parseInt(objService.getPRICE_PROMOTION());
                        float fprecent = (float) (price - price_promotion) / price;
                        int precent = (int) (fprecent * 100);
                        holder.txt_commission_phantram.setText("-" + precent + "%");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (objService.getPRICE_PROMOTION() != null)
                        holder.txt_prime.setText(StringUtil.conventMonney_Long(objService.getPRICE_PROMOTION()));
                    if (objService.getCOMISSION_PRODUCT() != null) {
                        set_hh_sp(objService.getCOMISSION_PRODUCT(), objService.getPRICE_PROMOTION(), holder.txt_commission_product);
                    } else {
                        holder.txt_commission_product.setText("Hoa hồng sp: 0%");
                    }

                } else {
                    holder.txt_commission_phantram.setVisibility(View.GONE);
                    holder.txt_price_delete.setVisibility(View.GONE);
                    holder.txt_price_delete.setText(StringUtil.conventMonney_Long(objService.getsPrice()));
                    holder.view_delete.setVisibility(View.GONE);
                }
            } else {
                holder.txt_commission_phantram.setVisibility(View.GONE);
                holder.txt_price_delete.setVisibility(View.GONE);
                holder.txt_price_delete.setText(StringUtil.conventMonney_Long(objService.getsPrice()));
                holder.view_delete.setVisibility(View.GONE);
            }


        } else {
            holder.txt_commission_phantram.setVisibility(View.GONE);
            holder.txt_price_delete.setVisibility(View.GONE);
            holder.txt_price_delete.setText(StringUtil.conventMonney_Long(objService.getsPrice()));
            holder.view_delete.setVisibility(View.GONE);
        }

        if (objService.getsUrlImage() != null) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.img_defaul)
                    .error(R.drawable.img_defaul);
            Glide.with(context).load(objService.getsUrlImage())
                    .apply(options).into(holder.img_product);
        } else {
            Glide.with(context).load(R.drawable.img_defaul).into(holder.img_product);
        }

    }
    private void set_hh_sp(String sCommission, String sPrice, TextView txt_com) {
        try {
            float commission = Float.parseFloat(sCommission);
            int price = Integer.parseInt(sPrice);
            int icom = (int) commission * price / 100;
            String sVersionCode = "HH: <font color='#149cc6'>"
                    + StringUtil.conventMonney_Long("" + icom) + " (" + sCommission +
                    "%)</font>";
            txt_com.setText(Html.fromHtml(sVersionCode), TextView.BufferType.SPANNABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    /*    if (objService.getCOMISSION_PRODUCT() != null) {
            String sVersionCode = "Hoa hồng sp: <font color='#149cc6'>" +
                    objService.getCOMISSION_PRODUCT() + "%</font>";
            holder.txt_commission_product.setText(Html.fromHtml(sVersionCode), TextView.BufferType.SPANNABLE);
            //    holder.txt_commission_product.setText("Hoa hồng sp: ("+objService.getCOMISSION_PRODUCT()+"%)");
        } else
            holder.txt_commission_product.setText("Hoa hồng sp: (0%)");*/
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.img_product)
        ImageView img_product;
        @BindView(R.id.txt_prime)
        TextView txt_prime;
        @BindView(R.id.txt_name)
        TextView txt_name;
        @BindView(R.id.txt_price_delete)
        TextView txt_price_delete;
        @BindView(R.id.txt_commission_product)
        TextView txt_commission_product;
        @BindView(R.id.txt_commission_phantram)
        TextView txt_commission_phantram;
        @BindView(R.id.view_delete)
        View view_delete;

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

    public void updateList(List<Products> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
