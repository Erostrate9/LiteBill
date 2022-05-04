package com.syx.litebill.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
        setDiaglogSize();
        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                InputMethodManager inputMethodManager=(InputMethodManager)
                        getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
            }
        };
        handler.sendEmptyMessageDelayed(1,100);
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
    /*设置Dialog尺寸和屏幕尺寸一致*/
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
