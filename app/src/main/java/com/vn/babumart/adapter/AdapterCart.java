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
import com.vn.babumart.callback.ItemClickCartListener;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.models.Products;
import com.vn.babumart.models.PropetiObj;
import com.vn.babumart.untils.StringUtil;
import com.vn.babumart.untils.TimeUtils;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.TopicViewHoder> {
    private List<Products> mList;
    private Context context;
    private ItemClickCartListener OnIListener;
    private ItemClickListener itemClickListener;

    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ItemClickCartListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickCartListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterCart(List<Products> listAirport, Context context) {
        this.mList = listAirport;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(final TopicViewHoder holder, final int position) {
        try {
            final Products obj = mList.get(position);
            if (obj != null) {
                if (obj.getmLisPropeti() != null && obj.getmLisPropeti().size() > 0) {
                    String sThuoctinh = "";
                    for (PropetiObj.PropetiDetail objDetail : obj.getmLisPropeti()) {
                        sThuoctinh = sThuoctinh + objDetail.getNAME_PARENT() + ":" +
                                objDetail.getSUB_PROPERTIES() + "   ";
                    }
                    String sVersionCode = "Thuộc tính: <font color='#e1ac06'>" + sThuoctinh + " </font>";
                    holder.txt_thuoctinh.setText(Html.fromHtml(sVersionCode), TextView.BufferType.SPANNABLE);

                } else {
                    holder.txt_thuoctinh.setVisibility(View.GONE);
                }
                if (obj.getCOMISSION_PRODUCT() != null && obj.getCOMISSION_PRODUCT().length() > 0) {
                    String sVersionCode = "Hoa hồng: <font color='#149cc6'>" + obj.getCOMISSION_PRODUCT()
                            + "% </font>";
                    holder.txt_hoahong.setText(Html.fromHtml(sVersionCode), TextView.BufferType.SPANNABLE);
                } else {
                    String sVersionCode = "Hoa hồng: <font color='#149cc6'>0% </font>";
                    holder.txt_hoahong.setText(Html.fromHtml(sVersionCode), TextView.BufferType.SPANNABLE);
                }
                if (obj.isVisibleDelete()) {
                    holder.img_delete.setVisibility(View.VISIBLE);
                } else
                    holder.img_delete.setVisibility(View.GONE);
                if (obj.getsName() != null && obj.getsName().length() > 0)
                    holder.txt_name.setText(obj.getsName());
                else
                    holder.txt_name.setText("...");
                if (obj != null && obj.getsPrice().length() > 0)
                    holder.txt_prime.setText(StringUtil.conventMonney(obj.getsPrice()));
                else
                    holder.txt_prime.setText("...");
                if (obj.getPRICE_PROMOTION() != null) {
                    if (obj.getSTART_PROMOTION() != null && obj.getEND_PROMOTION() != null) {
                        if (TimeUtils.compare_two_date_currenttime(obj.getSTART_PROMOTION(), obj.getEND_PROMOTION())) {
                            holder.view_delete.setVisibility(View.VISIBLE);
                            holder.txt_price_delete.setVisibility(View.VISIBLE);
                            holder.txt_price_delete.setText(StringUtil.conventMonney_Long(obj.getsPrice()));
                            if (obj.getPRICE_PROMOTION() != null) {
                                holder.txt_prime.setText(StringUtil.conventMonney_Long(obj.getPRICE_PROMOTION()));

                            }
                        } else {
                            holder.view_delete.setVisibility(View.GONE);
                            holder.txt_price_delete.setVisibility(View.GONE);
                        }
                    } else {
                        holder.view_delete.setVisibility(View.GONE);
                        holder.txt_price_delete.setVisibility(View.GONE);
                    }
                } else {
                    holder.view_delete.setVisibility(View.GONE);
                    holder.txt_price_delete.setVisibility(View.GONE);
                }
                if (obj != null && obj.getCODE_PRODUCT().length() > 0)
                    holder.txt_code_product.setText("Mã SP: " + obj.getCODE_PRODUCT());
                else
                    holder.txt_code_product.setText("...");

                if (obj.getsUrlImage() != null) {
                    RequestOptions options = new RequestOptions()
                            .placeholder(R.drawable.img_defaul)
                            .error(R.drawable.img_defaul);
                    Glide.with(context).load(obj.getsUrlImage())
                            .apply(options).into(holder.img_product);
                } else
                    Glide.with(context).load(R.drawable.img_defaul).into(holder.img_product);

                if (obj.getsQuantum() != null && obj.getsQuantum().length() > 0) {
                    if (obj.getsQuantum().matches("[0-9]+")) {
                        holder.txt_value_cart.setText(obj.getsQuantum());
                    } else {
                        holder.txt_value_cart.setText("0");
                    }
  /*              if (obj.getCOMMISSION() != null) {
                    int commission = Integer.parseInt(obj.getCOMMISSION());
                    int price = Integer.parseInt(obj.getsQuantum()) * Integer.parseInt(obj.getsPrice());
                    long com_price = (commission * price) / 100;
                    holder.txt_commission.setText("Hoa hồng: " + StringUtil.conventMonney_Long("" + com_price));
                } else
                    holder.txt_commission.setText("Hoa hồng: 0đ");*/

                } else {
                    holder.txt_value_cart.setText("0");
                    //  holder.txt_commission.setText("Hoa hồng: 0đ");
                }

                holder.txt_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnIListener.onClickItem_Add(position, obj);
                    }
                });
                holder.txt_minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnIListener.onClickItem_Minus(position, obj);
                    }
                });
                holder.img_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnIListener.onClickItem(position, obj);
                    }
                });
                if (StringUtil.check_login_customer()) {
                    holder.txt_hoahong.setVisibility(View.VISIBLE);
                } else {
                    holder.txt_hoahong.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_name_product_cart)
        TextView txt_name;
        @BindView(R.id.txt_price_cart)
        TextView txt_prime;
        @BindView(R.id.img_product_cart)
        ImageView img_product;
        @BindView(R.id.txt_minus)
        TextView txt_minus;
        @BindView(R.id.txt_value_cart)
        TextView txt_value_cart;
        @BindView(R.id.txt_add_cart)
        TextView txt_add;
        @BindView(R.id.img_delete)
        ImageView img_delete;
        @BindView(R.id.txt_thuoctinh)
        TextView txt_thuoctinh;
        @BindView(R.id.txt_code_product)
        TextView txt_code_product;
        @BindView(R.id.txt_hoahong)
        TextView txt_hoahong;
        @BindView(R.id.txt_price_delete)
        TextView txt_price_delete;
        @BindView(R.id.view_delete)
        View view_delete;


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

    public void updateList(List<Products> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
