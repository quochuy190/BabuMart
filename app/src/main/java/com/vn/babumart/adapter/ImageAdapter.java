package com.vn.babumart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vn.babumart.R;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-May-2019
 * Time: 17:37
 * Version: 1.0
 */
public class ImageAdapter extends PagerAdapter {
    LayoutInflater mLayoutInflater;
    Context context;
    private List<String> mLisUrl = new ArrayList<>();

    public ImageAdapter(Context context, List<String> mLisUrl) {
        this.context = context;
        this.mLisUrl = mLisUrl;
    }

    @Override
    public int getCount() {
        return mLisUrl.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }
   /* Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }*/

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_adrapter_image_vp, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        // imageView.setImageResource(GalImages[position]);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.img_defaul)
                .error(R.drawable.img_defaul);
        Glide.with(context).load(mLisUrl.get(position))
                .apply(options).into(imageView);
     /*   Glide.with(context).load(mLisUrl.get(position)).asBitmap()
                .placeholder(R.drawable.img_defaul)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(Bitmap drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        //   progressBar.setVisibility(View.GONE);
                    }
                });*/
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
