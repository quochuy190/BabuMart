package com.vn.babumart.adapter;

import android.annotation.SuppressLint;
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
import com.vn.babumart.callback.OnListenerItemClickObjService;
import com.vn.babumart.models.ObjCategoryProduct;

import java.util.List;

public class AdapterCategoryProduct extends RecyclerView.Adapter<AdapterCategoryProduct.CategoryServiceViewHolder> {
    public static Context mContext;
    private List<ObjCategoryProduct> mLisCateService;

    public static OnListenerItemClickObjService onListenerItemClickObjService;
    private ItemClickListener OnIListener;
    private ItemClickListener OnIListener_Title;

    public AdapterCategoryProduct(Context context, List<ObjCategoryProduct> mLisCateService,
                                  OnListenerItemClickObjService onListenerItemClickObjService) {
        this.onListenerItemClickObjService = onListenerItemClickObjService;
        mContext = context;
        this.mLisCateService = mLisCateService;
    }

    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public ItemClickListener getOnIListener_Title() {
        return OnIListener_Title;
    }

    public void setOnIListener_Title(ItemClickListener onIListener_Title) {
        OnIListener_Title = onIListener_Title;
    }

    @NonNull
    @Override
    public CategoryServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_category_product, parent, false);
        return new CategoryServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryServiceViewHolder holder, final int position) {
        holder.title.setText(mLisCateService.get(position).getsName());
        if (mLisCateService.get(position).getmList() != null &&
                mLisCateService.get(position).getmList().size() > 0){
            holder.horizontalAdapter.setData(mLisCateService.get(position).getmList()); // List of Strings
        }

        // holder.horizontalAdapter.setRowIndex(position);
        if (mLisCateService.get(position).isHideSub()) {
            holder.horizontalList.setVisibility(View.VISIBLE);
            holder.icon_down.setImageResource(R.drawable.ic_right);
        } else {
            holder.icon_down.setImageResource(R.drawable.ic_down);
            holder.horizontalList.setVisibility(View.GONE);
        }
        holder.icon_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnIListener.onClickItem(position, mLisCateService.get(position));
            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnIListener_Title.onClickItem(position, mLisCateService.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mLisCateService.size();
    }


    public static class CategoryServiceViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        private AdapterSubCategory horizontalAdapter;
        private RecyclerView horizontalList;
        private ImageView icon_down;

        @SuppressLint("WrongConstant")
        public CategoryServiceViewHolder(View view) {
            super(view);
            Context context = itemView.getContext();
            title = (TextView) view.findViewById(R.id.txt_title_objservice);
            icon_down = (ImageView) view.findViewById(R.id.icon_down);
            horizontalList = (RecyclerView) itemView.findViewById(R.id.recycle_lis_objservice);
            horizontalList.setLayoutManager(new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL,
                    false));
            horizontalAdapter = new AdapterSubCategory(mContext, onListenerItemClickObjService);
            horizontalList.setAdapter(horizontalAdapter);
        }
    }

    public void update_list(List<ObjCategoryProduct> list) {
        mLisCateService.clear();
        mLisCateService.addAll(list);
        notifyDataSetChanged();
    }

}
