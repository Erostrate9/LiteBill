package com.syx.litebill.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.syx.litebill.R;
import com.syx.litebill.adapter.ChartVpAdapter;
import com.syx.litebill.db.DBManager;
import com.syx.litebill.frag.frag_chart.ExpenseChartFragment;
import com.syx.litebill.frag.frag_chart.IncomeChartFragment;
import com.syx.litebill.dialog.CalendarDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthChartActivity extends AppCompatActivity implements View.OnClickListener {
    final static int EXP=0;
    final static int IN=1;
    private ImageView backIv,dateIv;
    private Button inBtn,expBtn;
    private TextView dateTv,inTv,expTv;
    private ViewPager chartVp;
    private ArrayList<Integer> yearList;
    private int year,month,selectYearPos=0;
    private ArrayList<Fragment> chartFragList;
    private IncomeChartFragment incomeChartFragment;
    private ExpenseChartFragment expenseChartFragment;
    private ChartVpAdapter chartVpAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_chart);
        yearList=new ArrayList<>();
        initView();
        initTime();
        initStatistics(year,month);
        initFrag();
        setVpSelectListner();
    }

    private void setVpSelectListner() {
        chartVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                setBtnStyle(position);
            }
        });
    }

    private void initFrag() {
        chartFragList = new ArrayList<>();
//        添加fragment对象
        incomeChartFragment = new IncomeChartFragment();
        expenseChartFragment=new ExpenseChartFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("year",year);
        bundle.putInt("month",month);
        incomeChartFragment.setArguments(bundle);
        expenseChartFragment.setArguments(bundle);
        chartFragList.add(expenseChartFragment);
        chartFragList.add(incomeChartFragment);
//        使用适配器
        chartVpAdapter=new ChartVpAdapter(getSupportFragmentManager(),chartFragList);
        chartVp.setAdapter(chartVpAdapter);
        //将Fragment加载到Activity中
    }

    private void initStatistics(int year, int month) {
        float inMoney=DBManager.getSumMoneyOn(IN,year,month);
        float expMoney=DBManager.getSumMoneyOn(EXP,year,month);
        int inCount=DBManager.getCountItemOneMonth(IN,year,month);
        int expCount=DBManager.getCountItemOneMonth(EXP,year,month);
        dateTv.setText(year+"年"+month+"月"+"账单");
        inTv.setText("共"+inCount+"笔收入，￥"+inMoney);
        expTv.setText("共"+expCount+"笔支出，￥"+expMoney);

    }

    private void initTime() {
        yearList=DBManager.getYearListFromAccounttb();
        selectYearPos=yearList.size()-1;
        year = yearList.get(selectYearPos);
        Calendar calendar=Calendar.getInstance();
        month = calendar.get(Calendar.MONTH)+1;
    }
    private void initView() {
        chartVp=findViewById(R.id.chart_vp);
        backIv=findViewById(R.id.chart_iv_back);
        dateIv=findViewById(R.id.chart_iv_date);
        inBtn=findViewById(R.id.chart_btn_income);
        expBtn=findViewById(R.id.chart_btn_expense);
        dateTv=findViewById(R.id.chart_tv_date);
        inTv=findViewById(R.id.chart_tv_in);
        expTv=findViewById(R.id.chart_tv_expense);

        inBtn.setOnClickListener(this);
        expBtn.setOnClickListener(this);
        backIv.setOnClickListener(this);
        dateIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.chart_iv_back:
                finish();
                break;
            case R.id.chart_iv_date:
                CalendarDialog calendarDialog= new CalendarDialog(this,selectYearPos,year,month);
                calendarDialog.show();
                calendarDialog.setOnRefreshListener(new CalendarDialog.OnRefreshListener() {
                    @Override
                    public void onRefresh(int selectPos,int selectYear, int selectMon) {
                        MonthChartActivity.this.year = selectYear;
                        MonthChartActivity.this.month = selectMon;
                        MonthChartActivity.this.selectYearPos=selectPos;
                        initStatistics(year,month);
                        MonthChartActivity.this.incomeChartFragment.setDate(year,month);
                        MonthChartActivity.this.expenseChartFragment.setDate(year,month);
//                        loadData();
                    }
                });
                break;
            case R.id.chart_btn_income:
                setBtnStyle(IN);
                chartVp.setCurrentItem(IN);
                break;
            case R.id.chart_btn_expense:
                setBtnStyle(EXP);
                chartVp.setCurrentItem(EXP);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }
    /*
    * 设置按钮样式的改变
    * 0-支出, 1-收入
    * */
    private void setBtnStyle(int kind){
        if(kind==EXP){
            expBtn.setBackgroundResource(R.drawable.main_edit_btn_bg);
            expBtn.setTextColor(Color.WHITE);
            inBtn.setBackgroundResource(R.drawable.dialog_btn_bg);
            inBtn.setTextColor(Color.BLACK);
            /*setPager*/
        }else if(kind==IN){
            inBtn.setBackgroundResource(R.drawable.main_edit_btn_bg);
            inBtn.setTextColor(Color.WHITE);
            expBtn.setBackgroundResource(R.drawable.dialog_btn_bg);
            expBtn.setTextColor(Color.BLACK);
            /*setPager*/
        }
    }
}