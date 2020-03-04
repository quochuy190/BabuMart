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
import com.vn.babumart.models.InfomationObj;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterNewsHome extends RecyclerView.Adapter<AdapterNewsHome.TopicViewHoder> {
    private List<InfomationObj> mList;
    private Context context;
    private ItemClickListener OnIListener;


    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterNewsHome(List<InfomationObj> listAirport, Context context) {
        this.mList = listAirport;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_home, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        InfomationObj obj = mList.get(position);
        if (obj.getIMAGE_COVER() != null) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.img_defaul)
                    .error(R.drawable.img_defaul);
            Glide.with(context).load(obj.getIMAGE_COVER())
                    .apply(options).into(holder.img_news_home);
           /* Glide.with(context).load(obj.getIMAGE_COVER()).asBitmap()
                    .placeholder(R.drawable.img_defaul)
                    .into(new BitmapImageViewTarget(holder.img_news_home) {
                        @Override
                        public void onResourceReady(Bitmap drawable, GlideAnimation anim) {
                            super.onResourceReady(drawable, anim);
                            //   progressBar.setVisibility(View.GONE);
                        }
                    });*/
        } else {
            Glide.with(context).load(R.drawable.img_defaul).into(holder.img_news_home);
        }
        if (obj.getTITLE() != null) {
            holder.txt_tieude.setText(obj.getTITLE().trim());
        }
        if (obj.getCONTENT() != null) {
            holder.txt_content.setText(Html.fromHtml(obj.getCONTENT()));
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_xemthem)
        TextView txt_xemthem;
        @BindView(R.id.txt_tieude)
        TextView txt_tieude;
        @BindView(R.id.img_news_home)
        ImageView img_news_home;
        @BindView(R.id.txt_content)
        TextView txt_content;

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

    public void updateList(List<InfomationObj> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
