package com.vn.babumart.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vn.babumart.R;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.callback.setOnItemClickListener;
import com.vn.babumart.models.Products;
import com.vn.babumart.untils.StringUtil;
import com.vn.babumart.untils.TimeUtils;
import com.vn.babumart.untils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterItemProduct extends RecyclerView.Adapter<AdapterItemProduct.FlightInfoViewHoder> {
    private List<Products> mLisObjService;
    private Context context;
    private setOnItemClickListener OnIListener;
    private ItemClickListener onListenerItemClickObjService;

    public setOnItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(setOnItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterItemProduct(ItemClickListener onListenerItemClickObjService) {
        //this.context = context;
        this.onListenerItemClickObjService = onListenerItemClickObjService;
    }


    @NonNull
    @Override
    public FlightInfoViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_home, parent, false);
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        layoutParams.width = (int) (Utils.get_width(context) * 0.43);
        itemView.setLayoutParams(layoutParams);
        return new FlightInfoViewHoder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightInfoViewHoder holder, final int position) {
        try {
            if (StringUtil.check_login_customer()) {
                holder.txt_commission_product.setVisibility(View.VISIBLE);
            } else {
                holder.txt_commission_product.setVisibility(View.GONE);
            }
            Products objService = mLisObjService.get(position);
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

            holder.img_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onListenerItemClickObjService.onClickItem(position, mLisObjService.get(position));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*holder.txt_more_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListenerItemClickObjService.onItemXemthemClick(mLisObjService.get(position));
            }
        });*/
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
        if (mLisObjService == null) {
            return 0;
        }
        return mLisObjService.size();
    }

    public class FlightInfoViewHoder extends RecyclerView.ViewHolder implements
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

        public FlightInfoViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onListenerItemClickObjService.onClickItem(getLayoutPosition(), mLisObjService.get(getLayoutPosition()));
        }

        @Override
        public boolean onLongClick(View v) {
            //  OnIListener.OnLongItemClickListener(getLayoutPosition());
            return false;
        }
    }

    public void setData(List<Products> data, Context context) {
        this.context = context;
        mLisObjService = new ArrayList<>();
        if (mLisObjService != data) {
            mLisObjService = data;
            notifyDataSetChanged();
        }
        notifyDataSetChanged();
    }
}
