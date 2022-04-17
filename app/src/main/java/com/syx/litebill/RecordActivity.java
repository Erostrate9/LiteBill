package com.syx.litebill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.syx.litebill.adapter.RecordPagerAdapter;
import com.syx.litebill.frag_record.ExpenseFragment;
import com.syx.litebill.frag_record.IncomeFragment;

import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        tabLayout = findViewById(R.id.record_tabs);
        viewPager = findViewById(R.id.record_vp);
//        设置viewPager加载页面
        initPager();

    }
    private void initPager(){
//        初始化viewPager集合
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        //支出
        ExpenseFragment expenseFragment=new ExpenseFragment();
        //收入
        IncomeFragment incomeFragment = new IncomeFragment();
        fragmentList.add(expenseFragment);
        fragmentList.add(incomeFragment);

//        创建适配器
        RecordPagerAdapter recordPagerAdapter=new RecordPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(recordPagerAdapter);
//        将TabLayout和ViewPager关联
        tabLayout.setupWithViewPager(viewPager);


    }
//    imageView的点击事件
    public void onClick(View view){
        switch (view.getId()){
            case R.id.record_iv_back:
                finish();
                break;
        }

    }
}