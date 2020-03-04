package com.vn.babumart.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vn.babumart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Quốc Huy
 * @version 1.0.0
 * @description
 * @desc Developer NEO Company.
 * @created 6/29/2018
 * @updated 6/29/2018
 * @modified by
 * @updated on 6/29/2018
 * @since 1.0
 */
public class AdapterImaViewPager extends PagerAdapter {
    private Activity mContext;
    private List<String> mLisUrl = new ArrayList<>();

    public AdapterImaViewPager(Activity mContext, List<String> ints) {
        this.mContext = mContext;
        this.mLisUrl = ints;
    }

    @Override
    public int getCount() {
        return mLisUrl.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==  object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = mContext.getLayoutInflater().inflate(R.layout.item_adrapter_image_vp, container,
                false);
        ImageView imageView = view.findViewById(R.id.imageView);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.img_defaul)
                .error(R.drawable.img_defaul);
        Glide.with(mContext).load(mLisUrl.get(position))
                .apply(options).into(imageView);
       /* Glide.with(mContext).load(mLisUrl.get(position)).asBitmap()
                .placeholder(R.drawable.img_defaul)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(Bitmap drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        //   progressBar.setVisibility(View.GONE);
                    }
                });*/
        container.addView(view);
        return view;
    }
}
