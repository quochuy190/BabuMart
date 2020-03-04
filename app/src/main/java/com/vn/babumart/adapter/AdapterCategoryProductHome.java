package com.vn.babumart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.babumart.R;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.models.CategoryProductHome;

import java.util.List;

public class AdapterCategoryProductHome extends
        RecyclerView.Adapter<AdapterCategoryProductHome.CategoryServiceViewHolder> {
    public  Context mContext;
    private List<CategoryProductHome> mLisCateService;
    boolean isShowall = false;
    public static ItemClickListener onListenerItemClickObjService;
    private ItemClickListener OnIListener;

    public AdapterCategoryProductHome(Context context, List<CategoryProductHome> mLisCateService,
                                      ItemClickListener onListenerItemClickObjService, boolean isShowall) {
        this.onListenerItemClickObjService = onListenerItemClickObjService;
        mContext = context;
        this.mLisCateService = mLisCateService;
        this.isShowall = isShowall;
    }

    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    @NonNull
    @Override
    public CategoryServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_category_product_home, parent, false);
        return new CategoryServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryServiceViewHolder holder, final int position) {
        if (mLisCateService.get(position).getsName() != null)
            holder.title.setText(mLisCateService.get(position).getsName());
        else if (mLisCateService.get(position).getSUB_NAME() != null)
            holder.title.setText(mLisCateService.get(position).getSUB_NAME());
        holder.horizontalAdapter.setData(mLisCateService.get(position).getmList(),mContext); // List of Strings
        // holder.horizontalAdapter.setRowIndex(position);
        holder.icon_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnIListener.onClickItem(position, mLisCateService.get(position));
            }
        });
        if (isShowall) {
            holder.txt_title_view_all.setVisibility(View.VISIBLE);
            holder.icon_down.setVisibility(View.VISIBLE);
        } else {
            holder.txt_title_view_all.setVisibility(View.VISIBLE);
            holder.icon_down.setVisibility(View.VISIBLE);
        }

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnIListener.onClickItem(position, mLisCateService.get(position));
            }
        });
        holder.txt_title_view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnIListener.onClickItem(position, mLisCateService.get(position));
            }
        });
       /* holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnIListener.onClickItem(position, mLisCateService.get(position));
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return mLisCateService.size();
    }


    public static class CategoryServiceViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        TextView txt_title_view_all;
        private AdapterItemProduct horizontalAdapter;
        private RecyclerView horizontalList;
        private ImageView icon_down;

        public CategoryServiceViewHolder(View view) {
            super(view);
            Context context = itemView.getContext();
            title = (TextView) view.findViewById(R.id.txt_title_objservice);
            txt_title_view_all = (TextView) view.findViewById(R.id.txt_title_view_all);
            icon_down = (ImageView) view.findViewById(R.id.icon_down);
            horizontalList = (RecyclerView) itemView.findViewById(R.id.recycle_lis_objservice);
            horizontalList.setLayoutManager(new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL,
                    false));
            horizontalAdapter = new AdapterItemProduct( onListenerItemClickObjService);
            horizontalList.setAdapter(horizontalAdapter);
        }
    }

    public void update_list(List<CategoryProductHome> list) {
        mLisCateService.clear();
        mLisCateService.addAll(list);
        notifyDataSetChanged();
    }

}
