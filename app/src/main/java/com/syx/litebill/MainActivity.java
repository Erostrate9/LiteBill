package com.syx.litebill;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.syx.litebill.adapter.AccountAdapter;
import com.syx.litebill.model.AccountBean;
import com.syx.litebill.db.DBManager;
import com.syx.litebill.utils.BudgetDialog;
import com.syx.litebill.utils.MoreDialog;

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
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        preferences = getSharedPreferences("budget", Context.MODE_PRIVATE);
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
//        设置长按事件
        setLVLongClickListener();
    }

//    设置ListView每一项的长按事件
    private void setLVLongClickListener() {
        mainLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //点击了头布局
                if(position==0){
                    return false;
                }
                int pos = position-1;
//                获取正在被点击的这条信息
                AccountBean clickedBean = mData.get(pos);
                //弹出用户是否删除的对话框
                showDeleteItemDialog(clickedBean);
                return false;
            }
        });
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
        topInTv.setText("￥ "+incomeMon);
        topOutTv.setText("￥ "+expenseMon);
        /*设置显示运算剩余*/
        float budget=preferences.getFloat("budget",0);//预算
        if(budget==0){
            topBudgetTv.setText("￥ 0");
        }else{
            float left = budget-expenseMon;
            topBudgetTv.setText("￥ "+left);
        }
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
                Intent itSearch = new Intent(this, SearchActivity.class);
                startActivity(itSearch);
                break;
            case R.id.main_btn_edit:
                Intent itRecord = new Intent(this, RecordActivity.class);
                startActivity(itRecord);
                break;
            case R.id.item_mainlv_top_tv_budget:
                showBudgetDialog();
                break;

            case R.id.item_mainlv_top_iv_hide:
                toggleShow();
                break;
            case R.id.main_btn_more:
                MoreDialog moreDialog =new MoreDialog(this);
                moreDialog.show();
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
    /*
    * 弹出budgetDialog的方法
    * */
    public void showBudgetDialog() {
        float budget=preferences.getFloat("budget",0);//预算
        BudgetDialog budgetDialog=new BudgetDialog(this,budget);
        budgetDialog.show();
        budgetDialog.setOnConfirmListener(new BudgetDialog.OnConfirmListener() {
            @Override
            public void onConfirm(float money) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putFloat("budget",money);
                editor.commit();
                /*计算剩余金额*/
                float monthExpense=DBManager.getSumMoneyOn(0,todayYear,todayMonth);
                float left = money-monthExpense;
                topBudgetTv.setText("￥ "+left);
                budgetDialog.cancel();
            }
        });
    }
    private void showDeleteItemDialog(final AccountBean clickedBean){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("您确定要删除这条记录吗？")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //执行删除的操作
                        DBManager.deleteItemFromAccounttbById(clickedBean.getId());
                        mData.remove(clickedBean);
//                      实时刷新，移除集合中的对象
                        adapter.notifyDataSetChanged();
                        //改变头布局TextView显示的内容
                        setTopTvDisplay();
                    }
                });
        //显示提示对话框
        builder.create().show();
    }
}

