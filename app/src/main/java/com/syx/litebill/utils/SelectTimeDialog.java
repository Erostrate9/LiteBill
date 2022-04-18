package com.syx.litebill.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.syx.litebill.R;
import com.syx.litebill.db.AccountBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class SelectTimeDialog extends Dialog implements View.OnClickListener {
    private EditText hourEt, minEt;
    private DatePicker datePicker;
    private Button confirmBtn, cancelBtn;
    private AccountBean accountBean;
    private SelectTimeDialog.OnConfirmListener onConfirmListener;
    private String hour,min;
    public SelectTimeDialog(@NonNull Context context,AccountBean accountBean) {
        super(context);
        this.accountBean=accountBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time);
        hourEt=(EditText) findViewById(R.id.dialog_time_et_hour);
        minEt=(EditText) findViewById(R.id.dialog_time_et_minute);
        datePicker=(DatePicker)findViewById(R.id.dialog_time_dp);
        confirmBtn=(Button) findViewById(R.id.dialog_time_btn_confirm);
        cancelBtn=(Button) findViewById(R.id.dialog_time_btn_cancel);
        confirmBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        datePicker.init(accountBean.getYear(),accountBean.getMonth(),accountBean.getDay(),null);
        Date date = new Date();
        hourEt.setText(Integer.toString(date.getHours()));
        minEt.setText(Integer.toString(date.getMinutes()));
    }
    public interface OnConfirmListener{
        void onConfirm();
    }
    /*
     * 设定回调接口的方法
     * */
    public void setOnConfirmListener(SelectTimeDialog.OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_time_btn_confirm:
                hour=hourEt.getText().toString();
                min=minEt.getText().toString();
                String hour_pattern="^[01]?[0-9]$|^2[0-3]$";
                String min_pattern="^[0-5]?[0-9]$";
                if(!Pattern.matches(hour_pattern, hour)){
                    Toast.makeText(getContext(), "小时只能为0-23", Toast.LENGTH_SHORT).show();
                    break;
                }else if(!Pattern.matches(min_pattern, min)){
                    Toast.makeText(getContext(), "分钟只能为0-59", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(onConfirmListener!=null){
                    onConfirmListener.onConfirm();
                }
                cancel();
                break;
            case R.id.dialog_time_btn_cancel:
                cancel();
                break;
        }
    }
    public String getTime(){
        hour=hourEt.getText().toString();
        min=minEt.getText().toString();
        String time = String.format("%d年%d月%d日 %s:%s",
                getYear(),getMonth(),getDay(),hour,min);
        return time;
    }
    public int getYear(){
        return datePicker.getYear();
    }
    public int getMonth(){
        return datePicker.getMonth();
    }
    public int getDay(){
        return datePicker.getDayOfMonth();
    }
}
