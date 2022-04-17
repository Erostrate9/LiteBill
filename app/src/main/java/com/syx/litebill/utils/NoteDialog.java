package com.syx.litebill.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.syx.litebill.R;

public class NoteDialog extends Dialog implements View.OnClickListener {
    private String note;
    private EditText et;
    private Button cancelButton, confirmButton;
    private OnConfirmListener onConfirmListener;
/*
* 设定回调接口的方法
* */
    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    public NoteDialog(@NonNull Context context, String note) {
        super(context);
        this.note=note;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        设置对话框显示布局
        setContentView(R.layout.dialog_note);
        et = findViewById(R.id.dialog_note_et);
        et.setText(note);
        cancelButton=findViewById(R.id.dialog_note_btn_cancel);
        confirmButton=findViewById(R.id.dialog_note_btn_confirm);
        cancelButton.setOnClickListener(this);
        confirmButton.setOnClickListener(this);
    }

    public interface OnConfirmListener{
        void onConfirm();
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.dialog_note_btn_cancel:
                cancel();
                break;
            case R.id.dialog_note_btn_confirm:
                if(onConfirmListener!=null){
                    onConfirmListener.onConfirm();
                }
                cancel();
                break;
        }
    }
    public String getNote(){
        return et.getText().toString().trim();
    }

}
