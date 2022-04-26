package com.syx.litebill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.syx.litebill.adapter.AccountAdapter;
import com.syx.litebill.db.AccountBean;
import com.syx.litebill.db.DBManager;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ListView mainLv; //展示今日收支情况的ListView
    //生命数据源
    private ArrayList<AccountBean> mData;
    private int todayYear,todayMonth,todayDay;
    private AccountAdapter adapter;
    //头布局相关控件
    private View headerView;
    private TextView topOutTv,topInTv, topBudgetTv,topDayTv;
    private ImageView topShowIv,searchIV;
    private Button editBtn;
    private ImageButton moreBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mData = new ArrayList<>();
        //设置适配器
        adapter = new AccountAdapter(this,mData);
        mainLv.setAdapter(adapter);
    }
    /*初始化自带的View*/
    private void initView(){
        mainLv = findViewById(R.id.main_lv);
        editBtn=findViewById(R.id.main_btn_edit);
        moreBtn=findViewById(R.id.main_btn_more);
        searchIV = findViewById(R.id.main_iv_search);
        editBtn.setOnClickListener(this);
        moreBtn.setOnClickListener(this);
        searchIV.setOnClickListener(this);
        addLVHeaderView();
    }
    /*
    * 给ListView添加头布局的方法
    * */
    private void addLVHeaderView(){
        //将布局转换成View对象
        headerView = getLayoutInflater().inflate(R.layout.item_mainlv_top,null);
        mainLv.addHeaderView(headerView);
        //查找头布局可用控件
        topOutTv = headerView.findViewById(R.id.item_mainlv_top_tv_out);
        topInTv = headerView.findViewById(R.id.item_mainlv_top_tv_in);
        topBudgetTv = headerView.findViewById(R.id.item_mainlv_top_tv_budget);
        topDayTv = headerView.findViewById(R.id.item_mainlv_top_tv_day);
        topShowIv = headerView.findViewById(R.id.item_mainlv_top_iv_hide);
        topBudgetTv.setOnClickListener(this);
        headerView.setOnClickListener(this);
        topShowIv.setOnClickListener(this);

    }
    /*
    * 获取今日的具体时间
    * */
    private void initTime(){
        Calendar calendar=Calendar.getInstance();
        todayYear = calendar.get(Calendar.YEAR);
        todayMonth = calendar.get(Calendar.MONTH)+1;
        todayDay = calendar.get(Calendar.DAY_OF_MONTH);
    }
    //当Activity获取焦点时会调用的方法
    @Override
    protected void onResume() {
        super.onResume();
        initTime();
        loadDBDate();
        setTopTvDisplay();
    }
/*设置头布局中各数据文本的显示*/
    private void setTopTvDisplay() {
//        获取今日支出和收入总金额,显示在view当中
        float incomeToday = DBManager.getSumMoneyOn(1,todayYear,todayMonth,todayDay);
        float expenseToday = DBManager.getSumMoneyOn(0,todayYear,todayMonth,todayDay);
        String infoOneDay = "今日支出 ￥"+expenseToday+" 收入 ￥"+incomeToday;
        topDayTv.setText(infoOneDay);
        /*获取本月收入和支出总金额*/
        float incomeMon = DBManager.getSumMoneyOn(1,todayYear,todayMonth);
        float expenseMon = DBManager.getSumMoneyOn(0,todayYear,todayMonth);
        String incomeMonStr="￥ "+incomeMon;
        String expenseMonStr="￥ "+expenseMon;
        topInTv.setText(incomeMonStr);
        topOutTv.setText(expenseMonStr);

    }

    private void loadDBDate() {
        ArrayList<AccountBean>list = DBManager.getAccountListOn(todayYear,todayMonth,todayDay);
        mData.clear();
        mData.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_iv_search:

                break;
            case R.id.main_btn_edit:
                Intent it1 = new Intent(this, RecordActivity.class);
                startActivity(it1);
                break;
            case R.id.item_mainlv_top_tv_budget:

                break;

            case R.id.item_mainlv_top_iv_hide:
                toggleShow();
                break;
            default:
                if(view==headerView){
                    /*头布局被点击*/
                }
                break;
        }

    }

    private boolean isShow = true;
    /*
    * 点击头布局眼睛时,如果原来是明文,就加密,如果是密文就显示;
    * */
    private void toggleShow() {
        if(isShow){
            PasswordTransformationMethod pwdTM = PasswordTransformationMethod.getInstance();
            topInTv.setTransformationMethod(pwdTM);
            topOutTv.setTransformationMethod(pwdTM);
            topBudgetTv.setTransformationMethod(pwdTM);
            topShowIv.setImageResource(R.mipmap.ih_hide);
            isShow=false;
        }else{
//            密文->明文
            HideReturnsTransformationMethod returnTM = HideReturnsTransformationMethod.getInstance();
            topInTv.setTransformationMethod(returnTM);
            topOutTv.setTransformationMethod(returnTM);
            topBudgetTv.setTransformationMethod(returnTM);
            topShowIv.setImageResource(R.mipmap.ih_show);
            isShow=true;
        }
    }
}

