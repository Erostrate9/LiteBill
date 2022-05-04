package com.syx.litebill.frag_chart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.syx.litebill.R;
import com.syx.litebill.adapter.ChartItemAdapter;
import com.syx.litebill.adapter.ChartVpAdapter;
import com.syx.litebill.db.ChartItemBean;
import com.syx.litebill.db.DBManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeChartFragment extends BaseChartFragment {
    public IncomeChartFragment(){
        kind=1;
    }
}