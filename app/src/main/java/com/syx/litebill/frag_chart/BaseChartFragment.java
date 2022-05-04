package com.syx.litebill.frag_chart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.syx.litebill.R;
import com.syx.litebill.adapter.ChartItemAdapter;
import com.syx.litebill.adapter.ChartVpAdapter;
import com.syx.litebill.db.ChartItemBean;
import com.syx.litebill.db.DBManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseChartFragment extends Fragment {
    protected int kind;
    protected ListView chartLV;
    protected int year,month;
    protected ArrayList<ChartItemBean>mData;
    protected ChartItemAdapter itemAdapter;

    public BaseChartFragment(){

    }
    public void setDate(int year, int month) {
        this.year=year;
        this.month= month;
        loadData(kind,year,month);
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
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(kind,year,month);
    }

    public void loadData(int kind,int year,int month) {
        ArrayList<ChartItemBean> list = DBManager.getChartListFromAccounttb(kind, year, month);
        mData.clear();
        mData.addAll(list);
        itemAdapter.notifyDataSetChanged();
    }
}