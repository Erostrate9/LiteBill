package com.syx.litebill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.syx.litebill.R;
import com.syx.litebill.model.ChartItemBean;

import java.util.ArrayList;

public class ChartItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ChartItemBean> mData;
    private LayoutInflater inflater;
    public ChartItemAdapter(Context context, ArrayList<ChartItemBean> mData){
        this.context=context;
        this.mData=mData;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(view==null){
            view = inflater.inflate(R.layout.item_chart_frag_lv,viewGroup,false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        ChartItemBean bean = mData.get(i);
        holder.iv.setImageResource(bean.getsImageId());
        holder.typeTv.setText(bean.getType());
        holder.ratioTv.setText(String.format("%.2f%%",bean.getRatio()*100));
        holder.totalTv.setText("￥ "+bean.getTotalMoney());
        return view;
    }
    class ViewHolder{
        TextView typeTv,ratioTv,totalTv;
        ImageView iv;
        public ViewHolder(View view){
            typeTv = view.findViewById(R.id.item_chart_frag_tv_type);
            ratioTv= view.findViewById(R.id.item_chart_frag_tv_ratio);
            totalTv= view.findViewById(R.id.item_chart_frag_tv_money);
            iv=view.findViewById(R.id.item_chart_frag_iv);
        }
    }
}
