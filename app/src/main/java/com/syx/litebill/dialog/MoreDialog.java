package com.syx.litebill.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.syx.litebill.activity.AboutActivity;
import com.syx.litebill.activity.HistoryActivity;
import com.syx.litebill.activity.MonthChartActivity;
import com.syx.litebill.R;
import com.syx.litebill.activity.SettingActivity;

public class MoreDialog extends Dialog implements View.OnClickListener{
    private Button aboutBtn,settingBtn,historyBtn,infoBtn,vxBtn;
    private ImageView closeIv;
    private Context context;
    public MoreDialog(@NonNull Context context) {
            super(context);
            this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_more);
        initView();
    }

    private void initView() {
        aboutBtn=findViewById(R.id.dialog_more_btn_about);
        settingBtn=findViewById(R.id.dialog_more_btn_setting);
        historyBtn=findViewById(R.id.dialog_more_btn_history);
        infoBtn=findViewById(R.id.dialog_more_btn_info);
        vxBtn=findViewById(R.id.dialog_more_btn_vx);
        closeIv=findViewById(R.id.dialog_more_iv_close);

        aboutBtn.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        historyBtn.setOnClickListener(this);
        infoBtn.setOnClickListener(this);
        vxBtn.setOnClickListener(this);
        closeIv.setOnClickListener(this);
        setDiaglogSize();
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        switch (view.getId()){
            case R.id.dialog_more_btn_about:
                intent.setClass(getContext(),AboutActivity.class);
                context.startActivity(intent);
                break;
            case R.id.dialog_more_btn_setting:
                intent.setClass(getContext(), SettingActivity.class);
                context.startActivity(intent);
                break;
            case R.id.dialog_more_btn_history:
                intent.setClass(getContext(), HistoryActivity.class);
                context.startActivity(intent);
                break;
            case R.id.dialog_more_btn_info:
                intent.setClass(getContext(), MonthChartActivity.class);
                context.startActivity(intent);
                break;
            case R.id.dialog_more_btn_vx:

                break;
            case R.id.dialog_more_iv_close:
                break;
        }
        cancel();
    }
    /*??????Dialog???????????????????????????*/
    public void setDiaglogSize(){
        Window window = getWindow();
        WindowManager.LayoutParams wlp=window.getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(dm);
        wlp.width=(int)dm.widthPixels;
        wlp.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
    }
}
