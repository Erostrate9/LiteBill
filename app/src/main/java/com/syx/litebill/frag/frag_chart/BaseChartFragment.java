package com.syx.litebill.frag.frag_chart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.syx.litebill.R;
import com.syx.litebill.adapter.ChartItemAdapter;
import com.syx.litebill.model.BarChartItemBean;
import com.syx.litebill.model.ChartItemBean;
import com.syx.litebill.db.DBManager;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseChartFragment extends Fragment {
    protected int kind;
    protected ListView chartLV;
    protected int year,month;
    protected ArrayList<ChartItemBean>mData;
    protected ChartItemAdapter itemAdapter;
    //代表柱状图的控件
    private BarChart barChart;
    protected String barColor;
    private TextView emptyTv;     //如果没有收支情况，显示的TextView

    public BaseChartFragment(){

    }
    public void setDate(int year, int month) {
        this.year=year;
        this.month= month;
        loadData(kind,year,month);
        // 清空柱状图当中的数据
        barChart.clear();
        barChart.invalidate();  //重新绘制柱状图
        setAxis(year,month);
        setAxisData(kind,year,month);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_income_chart,container,false);
        chartLV = view.findViewById(R.id.frag_chart_lv);
        //获取Activity传递的数据
        Bundle bundle = getArguments();
        year=bundle.getInt("year");
        month=bundle.getInt("month");
        //设置数据源
        mData=new ArrayList<>();
        //设置适配器
        itemAdapter=new ChartItemAdapter(getContext(),mData);
        chartLV.setAdapter(itemAdapter);
        //添加头布局
        addLVHeaderView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(kind,year,month);
    }
    private void addLVHeaderView(){
        //将布局转换为View对象
        View headerView = getLayoutInflater().inflate(R.layout.item_char_frag_top,null);
        chartLV.addHeaderView(headerView);
        //查找头布局中包含的控件
        barChart = headerView.findViewById(R.id.item_chart_frag_chart);
        emptyTv = headerView.findViewById(R.id.item_chart_frag_top_tv_empty);
        //设定柱状图不显示描述
        barChart.getDescription().setEnabled(false);
        //设置柱状图的内边距
        barChart.setExtraOffsets(20,20,20,20);
//        设置坐标轴
        setAxis(year,month);
        setAxisData(kind,year,month);

    }

    private void setAxisData(int kind, int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(year,month,0);
        int dayOfMonth=c.get(Calendar.DAY_OF_MONTH);
        ArrayList<IBarDataSet> sets = new ArrayList<>();
//        获取这个月每天的支出总金额，
        ArrayList<BarChartItemBean> list = DBManager.getTotalMoneyEveryDayInMonth(kind,year,month);
        if (list.size() == 0) {
            barChart.setVisibility(View.GONE);
            emptyTv.setVisibility(View.VISIBLE);
        }else{
            barChart.setVisibility(View.VISIBLE);
            emptyTv.setVisibility(View.GONE);
//            设置有多少根柱子
            ArrayList<BarEntry> barEntries1 = new ArrayList<>();
            for (int i = 0; i < dayOfMonth; i++) {
//                初始化每一根柱子，添加到柱状图当中
                BarEntry entry = new BarEntry(i, 0.0f);
                barEntries1.add(entry);
            }
            for (int i = 0; i < list.size(); i++) {
                BarChartItemBean itemBean = list.get(i);
                int day = itemBean.getDay();   //获取日期
                // 根据天数，获取x轴的位置
                int xIndex = day-1;
                BarEntry barEntry = barEntries1.get(xIndex);
                barEntry.setY(itemBean.getSummoney());
            }
            BarDataSet barDataSet1 = new BarDataSet(barEntries1, "");
            barDataSet1.setValueTextColor(Color.BLACK); // 值的颜色
            barDataSet1.setValueTextSize(8f); // 值的大小
            barDataSet1.setColor(Color.parseColor(barColor)); // 柱子的颜色

            // 设置柱子上数据显示的格式
            barDataSet1.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    // 此处的value默认保存一位小数
                    if (value==0) {
                        return "";
                    }
                    return value + "";
                }
            });
            sets.add(barDataSet1);

            BarData barData = new BarData(sets);
            barData.setBarWidth(0.2f); // 设置柱子的宽度
            barChart.setData(barData);
        }
    }

    /*
    * 设置柱状图坐标轴的显示方式
    * */
    private void setAxis(int year, int month) {
        //设置x轴
        XAxis xAxis=barChart.getXAxis();
        //设置x轴显示在下方
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置绘制该轴的网格线
        xAxis.setDrawGridLines(true);
        //设置x轴标签的个数
        Calendar c = Calendar.getInstance();
        c.set(year,month,0);
        int dayOfMonth=c.get(Calendar.DAY_OF_MONTH);
        xAxis.setLabelCount(dayOfMonth);
        //x轴标签的大小
        xAxis.setTextSize(12f);
        //设置x轴显示的值的格式
        xAxis.setValueFormatter(new ValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                int val=(int)value;
                if(val==0){
                    return month+"-01";
                }
                if(val==14){
                    return month+"-15";
                }
                if(val==dayOfMonth){
                    return month+"-"+dayOfMonth;
                }
                return "";
            }
        });
        //设置标签对x轴的偏移量,垂直方向
        xAxis.setYOffset(10);
        //y轴在子类的设置
        setYAxis(kind,year,month);

    }

    private void setYAxis(int kind, int year, int month) {
        //        获取本月收入最高的一天为多少，将他设定为y轴的最大值
        float maxMoney = DBManager.getMaxMoneyOneDayInMonth(kind,year, month);
        float max = (float) Math.ceil(maxMoney);   // 将最大金额向上取整
//        设置y轴
        YAxis yAxis_right = barChart.getAxisRight();
        yAxis_right.setAxisMaximum(max);  // 设置y轴的最大值
        yAxis_right.setAxisMinimum(0f);  // 设置y轴的最小值
        yAxis_right.setEnabled(false);  // 不显示右边的y轴

        YAxis yAxis_left = barChart.getAxisLeft();
        yAxis_left.setAxisMaximum(max);
        yAxis_left.setAxisMinimum(0f);
        yAxis_left.setEnabled(false);

//        设置不显示图例
        Legend legend = barChart.getLegend();
        legend.setEnabled(false);
    }

    public void loadData(int kind,int year,int month) {
        ArrayList<ChartItemBean> list = DBManager.getChartListFromAccounttb(kind, year, month);
        mData.clear();
        mData.addAll(list);
        itemAdapter.notifyDataSetChanged();
    }
}