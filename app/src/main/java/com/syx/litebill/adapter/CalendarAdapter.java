package com.syx.litebill.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.syx.litebill.R;

import java.util.ArrayList;

/*
* 历史账单界面,点击日历表,弹出对话框中GridView对应的适配器
* */
public class CalendarAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> mData;
    private int year;
    private int selectPos =0;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        mData.clear();
        loadData();
        notifyDataSetChanged();
    }

    public int getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }


    public CalendarAdapter(Context context,int year){
        this.context=context;
        this.year=year;
        mData = new ArrayList<>();
        loadData();
    }

    private void loadData() {
        for (int i=1;i<13;i++){
            String data = year+"/"+(i<10?"0":"")+i;
            mData.add(data);
        }
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_dialog_calendar_gv,parent,false);
        TextView tv = convertView.findViewById(R.id.item_dialog_calendar_gv_tv);
        tv.setText(mData.get(position));
        tv.setBackgroundResource(R.color.grey_ececea);
        tv.setTextColor(Color.BLACK);
        if(position==selectPos){
            tv.setBackgroundResource(R.color.green_7b8b6f);
            tv.setTextColor(Color.WHITE);
        }
        return convertView;
    }
}
