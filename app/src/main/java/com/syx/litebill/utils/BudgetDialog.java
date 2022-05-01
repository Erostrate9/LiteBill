package com.syx.litebill.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.syx.litebill.R;

public class BudgetDialog  extends Dialog implements View.OnClickListener {
    private float budget;
    private EditText et;
    private Button  cancelButton,confirmButton;
    private BudgetDialog.OnConfirmListener onConfirmListener;
    /*
     * 设定回调接口的方法
     * */
    public void setOnConfirmListener(BudgetDialog.OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    public BudgetDialog(@NonNull Context context, float budget) {
        super(context);
        this.budget=budget;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        设置对话框显示布局
        setContentView(R.layout.dialog_budget);
        et = findViewById(R.id.dialog_budget_et);
        et.setText(String.valueOf(budget));
        cancelButton=findViewById(R.id.dialog_budget_btn_cancel);
        cancelButton.setOnClickListener(this);
        confirmButton=findViewById(R.id.dialog_budget_btn_confirm);
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
        void onConfirm(float money);
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.dialog_budget_btn_cancel:
                cancel();
                break;
            case R.id.dialog_budget_btn_confirm:
                String budgetStr=et.getText().toString();
                if(TextUtils.isEmpty(budgetStr)){
                Toast.makeText(getContext(), "输入预算不能为空!", Toast.LENGTH_SHORT).show();
                return;
            }
                float money=Float.valueOf(budgetStr);
                if(money<=0){
                    Toast.makeText(getContext(), "预算必须大于零", Toast.LENGTH_SHORT).show();
                    return;
                }
                this.budget=money;
                if(onConfirmListener!=null){
                    onConfirmListener.onConfirm(budget);
                }
                cancel();
                break;
        }
    }
    public float getBudget(){
        return budget;
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