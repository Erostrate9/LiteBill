package com.syx.litebill;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.syx.litebill.adapter.AccountAdapter;
import com.syx.litebill.model.AccountBean;
import com.syx.litebill.db.DBManager;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    //绑定控件
    private ListView searchLv ;
    private ImageView searchIvBack ;
    private ImageView searchIvS ;
    private EditText searchEt ;
    private TextView emptyTv ;
    private AccountAdapter adapter;
    //数据源
    private ArrayList<AccountBean> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        mData= new ArrayList<>();
        adapter = new AccountAdapter(this, mData);
        searchLv.setAdapter(adapter);
//        设置无数据时LV显示的控件
        searchLv.setEmptyView(emptyTv);
    }

    private void initView() {
        searchLv=findViewById(R.id.search_lv);
        searchIvBack=findViewById(R.id.search_iv_back);
        searchIvS =findViewById(R.id.search_iv_s);
        searchEt=findViewById(R.id.search_et);
        emptyTv=findViewById(R.id.search_tv_empty);

        searchIvS.setOnClickListener(this);
        searchIvBack.setOnClickListener(this);
        //        设置长按事件
        setLVLongClickListener();
    }

    private void setLVLongClickListener() {
        searchLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
        switch(view.getId()){
            case R.id.search_iv_s:
                mData.clear();
                String msg = searchEt.getText().toString().trim();
                if(TextUtils.isEmpty(msg)){
                    Toast.makeText(this, "查询内容不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    //查询数据库获取数据集合
                    ArrayList<AccountBean> list = DBManager.searchFromAccounttbByNote(msg);
                    mData.addAll(list);
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.search_iv_back:
                finish();
                break;
        }
    }
}