package com.syx.litebill.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.syx.litebill.R;
import com.syx.litebill.model.AccountBean;

import java.util.Date;

public class SelectTimeDialog extends Dialog implements View.OnClickListener {
    private EditText hourEt, minEt;
    private int hour,min;
    private DatePicker datePicker;
    private Button confirmBtn, cancelBtn;
    private AccountBean accountBean;
    private SelectTimeDialog.OnConfirmListener onConfirmListener;
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
        datePicker.init(accountBean.getYear(),accountBean.getMonth()-1,accountBean.getDay(),null);
        Date date = new Date();
        hourEt.setText(Integer.toString(date.getHours()));
        minEt.setText(Integer.toString(date.getMinutes()));
    }
    public interface OnConfirmListener{
        void onConfirm(String time,int year, int month, int day);
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
                int year= getYear();
                int month=getMonth();
                int day=getDay();
                String monthStr=String.valueOf(month);
                String dayStr=String.valueOf(day);
                if(month<10){
                    monthStr="0"+monthStr;
                }
                if(day<10){
                    dayStr="0"+dayStr;
                }
                String hourStr=hourEt.getText().toString();
                String minStr=minEt.getText().toString();
                if(!TextUtils.isEmpty(hourStr)){
                    hour = Integer.parseInt(hourStr);
                    if(hour<0 || hour>23){
                        Toast.makeText(getContext(), "小时只能为0-23", Toast.LENGTH_SHORT).show();
                        break;
                    }else{
                        if(hour<10){
                            hourStr="0"+hourStr;
                        }
                    }
                }else{
                    Toast.makeText(getContext(), "小时不能为空(0-23)", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(!TextUtils.isEmpty(minStr)){
                    min = Integer.parseInt(minStr);
                    if(min<0 || min>59){
                        Toast.makeText(getContext(), "分钟只能为0-59", Toast.LENGTH_SHORT).show();
                        break;
                    }else{
                        if(min<10){
                            minStr="0"+minStr;
                        }
                    }
                }else{
                    Toast.makeText(getContext(), "分钟不能为空(0-59)", Toast.LENGTH_SHORT).show();
                    break;
                }
                String time = String.format("%d年%s月%s日 %s:%s",
                        getYear(),monthStr,dayStr,hourStr,minStr);
                if(onConfirmListener!=null){
                    onConfirmListener.onConfirm(time,year,month,day);
                }
                cancel();
                break;
            case R.id.dialog_time_btn_cancel:
                cancel();
                break;
        }
    }
    public int getYear(){
        return datePicker.getYear();
    }
    public int getMonth(){
        return datePicker.getMonth()+1;
    }
    public int getDay(){
        return datePicker.getDayOfMonth();
    }
}
