package com.vn.babumart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.vn.babumart.R;
import com.vn.babumart.callback.ItemClickListener;
import com.vn.babumart.models.ObjPieChart;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterListPieChart extends RecyclerView.Adapter<AdapterListPieChart.TopicViewHoder> {
    private List<ObjPieChart> mList;
    private Context context;
    private ItemClickListener OnIListener;


    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterListPieChart(List<ObjPieChart> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pie_chart, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        Pie pie = AnyChart.pie();
        ObjPieChart objPieChart = mList.get(position);
        pie.title(objPieChart.getTitle().toLowerCase());
        pie.animation(true, 100);
        pie.data(objPieChart.getData());
        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);
        holder.item_pie_chart.setChart(pie);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.item_pie_chart)
        AnyChartView item_pie_chart;

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

    public void updateList(List<ObjPieChart> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
