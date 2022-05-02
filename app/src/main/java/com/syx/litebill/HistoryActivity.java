package com.syx.litebill;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.syx.litebill.adapter.AccountAdapter;
import com.syx.litebill.db.AccountBean;
import com.syx.litebill.db.DBManager;
import com.syx.litebill.utils.CalendarDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView historyLv;
    private TextView dateTv;
    private ImageView calendarIv;
    private ImageView backIv;
    private AccountAdapter adapter;
    private TextView emptyTv;
    //数据源
    private ArrayList<AccountBean> mData;
    private int year,month,selectYearPos=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyLv = findViewById(R.id.history_lv);
        dateTv=findViewById(R.id.history_tv_date);
        calendarIv=findViewById(R.id.history_iv_calendar);
        backIv=findViewById(R.id.history_iv_back);
        emptyTv = findViewById(R.id.history_tv_empty);
        calendarIv.setOnClickListener(this);
        backIv.setOnClickListener(this);

        mData= new ArrayList<>();
        adapter = new AccountAdapter(this, mData);
        historyLv.setAdapter(adapter);
        initTime();
        dateTv.setText(year+"年"+month+"月");
        loadData();
//        设置无数据时LV显示的控件
        historyLv.setEmptyView(emptyTv);
        setLVLongClickListener();
    }

    /*获取指定年份月份的AccoutBean list*/
    private void loadData() {
        mData.clear();
        ArrayList<AccountBean> list = DBManager.getAccountListOn(year, month);
        mData.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void initTime() {
        Calendar calender = Calendar.getInstance();
        year = calender.get(Calendar.YEAR);
        month = calender.get(Calendar.MONTH)+1;
    }

    private void setLVLongClickListener() {
        historyLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                获取正在被点击的这条信息
                AccountBean clickedBean = mData.get(position);
                //弹出用户是否删除的对话框
                showDeleteItemDialog(clickedBean);
                return false;
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
                    }
                });
        //显示提示对话框
        builder.create().show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.history_iv_back:
                finish();
                break;
            case R.id.history_iv_calendar:
                CalendarDialog calendarDialog= new CalendarDialog(this,selectYearPos,year,month);
                calendarDialog.show();
                calendarDialog.setOnRefreshListener(new CalendarDialog.OnRefreshListener() {
                    @Override
                    public void onRefresh(int selectPos,int selectYear, int selectMon) {
                        year = selectYear;
                        month = selectMon;
                        selectYearPos=selectPos;
                        dateTv.setText(year+"年"+month+"月");
                        loadData();
                    }
                });
                break;
        }
    }

}