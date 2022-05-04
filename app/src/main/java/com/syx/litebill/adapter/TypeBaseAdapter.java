package com.syx.litebill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.syx.litebill.R;
import com.syx.litebill.model.TypeBean;

import java.util.List;

public class TypeBaseAdapter extends BaseAdapter {
    private Context context;
    private List<TypeBean> mData;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<TypeBean> getmData() {
        return mData;
    }

    public void setmData(List<TypeBean> mData) {
        this.mData = mData;
    }

    public int getSelectedPos() {
        return selectedPos;
    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
    }

    private int selectedPos = 0;// 当前选中的位置

    public TypeBaseAdapter(Context context, List<TypeBean> mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView  = LayoutInflater.from(context).inflate(R.layout.item_recordfrag_gv, parent,false);
        ImageView iv = convertView.findViewById(R.id.item_recordfrag_iv);
        TextView tv = convertView.findViewById(R.id.item_recordfrag_tv);
        // 获取指定位置的数据源
        TypeBean typeBean = mData.get(position);
        tv.setText(typeBean.getTypename());
        // 判断当前位置是否为选中位置,若是则设置带颜色的图标,否则设置没有颜色的
        if(selectedPos==position){
            iv.setImageResource(typeBean.getSelectedImageId());
        }else{
            iv.setImageResource(typeBean.getImageId());
        }
        return convertView;
    }
}
