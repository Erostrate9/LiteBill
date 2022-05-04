package com.syx.litebill.frag_chart;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syx.litebill.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseChartFragment extends BaseChartFragment {
    public ExpenseChartFragment(){
        kind=0;
        barColor= "#ff0000";
    }
}