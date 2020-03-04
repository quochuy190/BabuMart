package com.vn.babumart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vn.babumart.R;
import com.vn.babumart.callback.ItemClickListener;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterImageUpFace extends RecyclerView.Adapter<AdapterImageUpFace.TopicViewHoder> {
    private List<String> mList;
    private Context context;
    private ItemClickListener OnIListener;


    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterImageUpFace(List<String> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_up_face, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        String obj = mList.get(position);
        if (obj != null && obj.length() > 0) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.img_defaul)
                    .error(R.drawable.img_defaul);
            Glide.with(context).load(obj)
                    .apply(options).into(holder.img_up_face);
        } else {
            Glide.with(context).load(R.color.gray).into(holder.img_up_face);
        }

        /*Glide.with(context).load(obj).asBitmap()
                .placeholder(R.drawable.img_defaul)
                .into(new BitmapImageViewTarget(holder.img_up_face_item) {
                    @Override
                    public void onResourceReady(Bitmap drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        //   progressBar.setVisibility(View.GONE);
                    }
                });*/

        // Glide.with(context).load(obj).into(holder.img_up_face_item);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.img_up_face)
        ImageView img_up_face;

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

    public void updateList(List<String> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
