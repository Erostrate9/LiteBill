package com.syx.litebill.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.syx.litebill.R;
import com.syx.litebill.adapter.CalendarAdapter;
import com.syx.litebill.db.DBManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarDialog extends Dialog implements View.OnClickListener{
    private GridView calendarGv;
    private ImageView closeIv;
    private LinearLayout hsvLl;
    private Context context;
    private CalendarAdapter adapter;
    private ArrayList<TextView>hsvViewList;
    private ArrayList<Integer> yearList;
    private int selectMon=-1;
    //表示上一个被点击的年份的位置
    private int selectedPos=-1;
    private OnRefreshListener onRefreshListener;

    public interface OnRefreshListener{
        public void onRefresh(int selectPos,int year,int month);
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener){
        this.onRefreshListener=onRefreshListener;
    }
    public CalendarDialog(@NonNull Context context,int selectYearPos,int year,int month) {
        super(context);
        selectedPos=selectYearPos;
        selectMon=month;
        setContentView(R.layout.calendar_dialog);
        this.context=context;
        hsvViewList=new ArrayList<>();
        yearList=new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendarGv = findViewById(R.id.dialog_calendar_gv);
        closeIv = findViewById(R.id.dialog_calendar_iv);
        hsvLl = findViewById(R.id.dialog_calendar_hsv_ll);

        closeIv.setOnClickListener(this);
        setDialogSize();
        //向HorizontalScrollView中加入View
        addViewToLayout();
        initGridView();
        setGvClickListener();
    }

    private void setGvClickListener() {
        calendarGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                adapter.setSelectPos(position);
                adapter.notifyDataSetChanged();
                //获取到被选中的年份和月份
                selectMon=position+1;
                int year=yearList.get(selectedPos);
                onRefreshListener.onRefresh(selectedPos,year,selectMon);
                cancel();
            }
        });
    }

    private void initGridView() {
        int selectYear = yearList.get(selectedPos);
        adapter = new CalendarAdapter(getContext(),selectYear);
        if(selectMon==-1){
            int mon=Calendar.getInstance().get(Calendar.MONTH)+1;
            adapter.setSelectPos(mon-1);
            selectMon=mon;
        }else{
            adapter.setSelectPos(selectMon-1);
        }
        calendarGv.setAdapter(adapter);
    }

    private void addViewToLayout() {
        //获取数据库中存储了多少个年份
        yearList= DBManager.getYearListFromAccounttb();
        if (yearList.size()==0){
            int year = Calendar.getInstance().get(Calendar.YEAR);
            yearList.add(year);
        }
        //遍历年份,有几年就向ScrollView当中添加几个View
        for (int year:yearList){
            View view = getLayoutInflater().inflate(R.layout.item_dialog_calendar_hsv,null);
            hsvLl.addView(view);
            TextView hsvTv = view.findViewById(R.id.item_dialog_calendar_hsv_tv);
            hsvTv.setText(year+"");
            hsvViewList.add(hsvTv);
        }
        if(selectedPos==-1||selectedPos>=hsvViewList.size()){
            selectedPos=hsvViewList.size()-1;
        }
        changeTvBg();
        //设置每一个View的监听事件
        setHsvClickListener();
    }

    //设置Hsv中每一个View的监听事件
    private void setHsvClickListener() {
        for(int i=0;i<hsvViewList.size();i++){
            TextView tv=hsvViewList.get(i);
            final int pos=i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedPos=pos;
                    changeTvBg();
                    //获取被选中的年份，GridView显示数据发生改变
                    int year=yearList.get(selectedPos);
                    adapter.setYear(year);
                }
            });
        }
    }

    /*
    * 传入被选中年份的位置
    * 设置被选中年份的背景
    * */
    private void changeTvBg() {
        for(TextView tv:hsvViewList){
            tv.setBackgroundResource(R.drawable.dialog_btn_bg);
            tv.setTextColor(Color.BLACK);
        }
        TextView selectView=hsvViewList.get(selectedPos);
        selectView.setBackgroundResource(R.drawable.main_edit_btn_bg);
        selectView.setTextColor(Color.WHITE);
    }

    /*设置Dialog尺寸和屏幕尺寸一致*/
    public void setDialogSize(){
        Window window = getWindow();
        WindowManager.LayoutParams wlp=window.getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(dm);
        wlp.width=(int)dm.widthPixels;
        wlp.gravity = Gravity.TOP;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_calendar_iv:
                cancel();
                break;

        }
    }
}
