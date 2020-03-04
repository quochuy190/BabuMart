package com.vn.babumart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.vn.babumart.R;
import com.vn.babumart.callback.OnListenerItemClickObjService;
import com.vn.babumart.callback.setOnItemClickListener;
import com.vn.babumart.models.ObjCategoryProduct;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterSubCategory extends RecyclerView.Adapter<AdapterSubCategory.FlightInfoViewHoder> {
    private List<ObjCategoryProduct> mLisObjService = new ArrayList<>();
    private Context context;
    private setOnItemClickListener OnIListener;
    private OnListenerItemClickObjService onListenerItemClickObjService;

    public setOnItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(setOnItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterSubCategory(Context context, OnListenerItemClickObjService onListenerItemClickObjService) {
        this.context = context;
        this.onListenerItemClickObjService = onListenerItemClickObjService;
    }


    @NonNull
    @Override
    public FlightInfoViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sub_category, parent, false);
        return new FlightInfoViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightInfoViewHoder holder, final int position) {
        ObjCategoryProduct objService = mLisObjService.get(position);

        if (objService.getSUB_NAME() != null) {
            holder.txt_item_nem.setVisibility(View.VISIBLE);
            holder.txt_item_nem.setText(objService.getSUB_NAME());
        } else
            holder.txt_item_nem.setVisibility(View.GONE);
        holder.txt_item_nem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListenerItemClickObjService.onClickListener(mLisObjService.get(position));
            }
        });
        /*holder.txt_more_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListenerItemClickObjService.onItemXemthemClick(mLisObjService.get(position));
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return mLisObjService.size();
    }

    public class FlightInfoViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_item_sub_category)
        TextView txt_item_nem;

        public FlightInfoViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            //   OnIListener.OnItemClickListener(getLayoutPosition());
            onListenerItemClickObjService.onClickListener(mLisObjService.get(getLayoutPosition()));
        }

        @Override
        public boolean onLongClick(View v) {
            //   OnIListener.OnLongItemClickListener(getLayoutPosition());
            return false;
        }
    }

    public void setData(List<ObjCategoryProduct> data) {
        if (mLisObjService != data) {
            mLisObjService = data;
            notifyDataSetChanged();
        }
    }
}
