package com.syx.litebill.utils;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.syx.litebill.R;

public class KeyBoardUtils {
    private final Keyboard k1; //自定义键盘
    private KeyboardView keyboardView;
    private EditText editText;
 public interface OnEnsureListener{
     public void onEnsure();
 }
 OnEnsureListener onEnsureListener;
 public void setOnEnsureListener(OnEnsureListener onEnsureListener){
     this.onEnsureListener = onEnsureListener;
 }
    public KeyBoardUtils(KeyboardView keyboardView, EditText editText) {
        this.keyboardView = keyboardView;
        this.editText = editText;
        // 取消弹出系统键盘
        this.editText.setInputType(InputType.TYPE_NULL);
        k1=new Keyboard(this.editText.getContext(), R.xml.key);

        // 设置要显示键盘的样式
        this.keyboardView.setKeyboard(k1);
        this.keyboardView.setEnabled(true);
        this.keyboardView.setPreviewEnabled(false);
        // 设置键盘按钮被点击的监听
        this.keyboardView.setOnKeyboardActionListener(listener);
    }
    KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int i) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = editText.getText();
            int start = editText.getSelectionStart();
            switch(primaryCode){
                // 点击了删除
                case Keyboard.KEYCODE_DELETE:
                    if (editable!=null && editable.length()>0){
                        if(start>0){
                            editable.delete(start-1,start);
                        }
                    }
                    break;
                // 点击了清零
                case Keyboard.KEYCODE_CANCEL:
                    editable.clear();
                    break;
                // 点击了确定
                case Keyboard.KEYCODE_DONE:
                    // 通过接口回调，当点击确定时，调用该方法
                    onEnsureListener.onEnsure();
                    break;
                // 点击了数字
                default:
                    editable.insert(start, Character.toString((char)primaryCode));
                    break;
            }

        }

        @Override
        public void onText(CharSequence charSequence) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };
 // 显示键盘
    public void showKeyboard(){
        int visibility = keyboardView.getVisibility();
        if(visibility == View.INVISIBLE || visibility==View.GONE){
            keyboardView.setVisibility(View.VISIBLE);
        }
    }
    //隐藏键盘
    public void hideKeyboard(){
        int visibility = keyboardView.getVisibility();
        if (visibility==View.VISIBLE || visibility==View.INVISIBLE){
            keyboardView.setVisibility(View.GONE);
        }
    }
}
