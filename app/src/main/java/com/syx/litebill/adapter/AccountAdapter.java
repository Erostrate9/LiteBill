package com.syx.litebill.adapter;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.VerifiedInputEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.syx.litebill.R;
import com.syx.litebill.db.AccountBean;

import java.util.ArrayList;
import java.util.Calendar;

public class AccountAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<AccountBean>mData;
    private LayoutInflater inflater;
    private int year,month,day;

    public AccountAdapter(Context context, ArrayList<AccountBean>mData) {
        this.context = context;
        this.mData=mData;
        inflater = LayoutInflater.from(context);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
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
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_mainlv,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        AccountBean accountBean = mData.get(position);
        viewHolder.typeIv.setImageResource(accountBean.getSelectedImageId());
        viewHolder.typeTv.setText(accountBean.getTypename());
        viewHolder.noteTv.setText(accountBean.getNote());
        viewHolder.moneyTv.setText(String.valueOf("￥"+accountBean.getMoney()));
        if(accountBean.getYear()==year && accountBean.getMonth()==month && accountBean.getDay()==day){
            String time =accountBean.getTime().split(" ")[1];
            viewHolder.timeTv.setText("今天"+time);
        }else{
            viewHolder.timeTv.setText(accountBean.getTime());
        }

        return convertView;
    }

    class ViewHolder{
        ImageView typeIv;
        TextView typeTv,noteTv,timeTv,moneyTv;
        public ViewHolder(View view){
            typeIv = view.findViewById(R.id.item_mainlv_iv);
            typeTv = view.findViewById(R.id.item_mainlv_tv_title);
            timeTv = view.findViewById(R.id.item_mainlv_tv_time);
            noteTv = view.findViewById(R.id.item_mainlv_tv_note);
            moneyTv = view.findViewById(R.id.item_mainlv_tv_money);
        }

    }
}
