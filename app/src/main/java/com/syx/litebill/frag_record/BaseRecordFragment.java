package com.syx.litebill.frag_record;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.syx.litebill.R;
import com.syx.litebill.adapter.TypeBaseAdapter;
import com.syx.litebill.db.AccountBean;
import com.syx.litebill.db.DBManager;
import com.syx.litebill.db.TypeBean;
import com.syx.litebill.utils.KeyBoardUtils;
import com.syx.litebill.utils.NoteDialog;
import com.syx.litebill.utils.SelectTimeDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/*
* 记录页面的支出模块
* */
public abstract class BaseRecordFragment extends Fragment implements View.OnClickListener {
    private KeyboardView keyboardView;
    private EditText moneyEt;
    private ImageView typeIv;
    private TextView typeTv,noteTv,timeTv;
    private GridView typeGv;
    private List<TypeBean> typeBeans;



    private TypeBaseAdapter adapter;
    private AccountBean accountBean; //将需要插入到记账本当中的数据保存为对象

    public BaseRecordFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountBean=new AccountBean();
        accountBean.setTypename("其他");
        accountBean.setSelectedImageId(R.mipmap.ic_qita_fs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_expense, container, false);
        initView(view);
        setInitTime();
        //给GridView填充数据
        loadDataToGV();
        setGVListener(); // 设置Gridview每一项的点击事件
        return view;
    }

    /*获取当前时间,显示在tmieTv上*/
    private void setInitTime() {
        Date date = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = sdf.format(date);
        timeTv.setText(time);
        accountBean.setTime(time);
        Calendar calendar=Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month=calendar.get(calendar.MONTH)+1;
        int day=calendar.get(calendar.DAY_OF_MONTH);
        accountBean.setYear(year);
        accountBean.setMonth(month);
        accountBean.setDay(day);
    }

    /*
    *  设置Gridview每一项的点击事件
    * */
    private void setGVListener() {
        typeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                adapter.setSelectedPos(position);
                adapter.notifyDataSetChanged(); //提示绘制发生变化
                //top
                TypeBean typeBean = typeBeans.get(position);
                String typename=typeBean.getTypename();
                typeTv.setText(typename);
                int selectedImageId=typeBean.getSelectedImageId();
                typeIv.setImageResource(selectedImageId);
                accountBean.setTypename(typename);
                accountBean.setSelectedImageId(selectedImageId);
            }
        });
    }

    //给GridView填充数据的方法
    private void loadDataToGV() {
        typeBeans = new ArrayList<>();
        adapter=new TypeBaseAdapter(getContext(),typeBeans);
        typeGv.setAdapter(adapter);
//        获取数据库中的数据源
        getIconAndShow();

    }
    abstract public void getIconAndShow();

    private void initView(View view) {
        keyboardView=view.findViewById(R.id.frag_record_keyboard);
        moneyEt = view.findViewById(R.id.frag_record_et_money);
        typeIv = view.findViewById(R.id.frag_record_iv);
        typeTv = view.findViewById(R.id.frag_record_tv_type);
        noteTv = view.findViewById(R.id.frag_record_tv_note);
        timeTv = view.findViewById(R.id.frag_record_tv_time);
        typeGv = view.findViewById(R.id.frag_record_gv);
        noteTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);
        KeyBoardUtils keyBoardUtils = new KeyBoardUtils(keyboardView, moneyEt);
        keyBoardUtils.showKeyboard();
        //设置接口，监听确定按钮
        keyBoardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            @Override
            //点击了确定按钮
            public void onEnsure() {
                //获取记录的信息
                String moneyStr=moneyEt.getText().toString();
                String pattern="^[0-9]+.?[0-9]*$";
                boolean isMatch = Pattern.matches(pattern, moneyStr);
                if(TextUtils.isEmpty(moneyStr)||moneyStr.equals("0")){
                    Toast.makeText(getContext(), "金额不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!isMatch){
                    Toast.makeText(getContext(), "请输入正确的数字作为金额！", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    float money=Float.parseFloat(moneyStr);
                    accountBean.setMoney(money);
                }
                //将数据存储到数据库
                Toast.makeText(getContext(), ""+accountBean.getMoney()+","+accountBean.getTime()+","+accountBean.getYear(), Toast.LENGTH_SHORT).show();
                saveAccountToDB(accountBean);
                //返回上一级页面
                getActivity().finish();

            }
        });

    }

    public abstract void saveAccountToDB(AccountBean accountBean);

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.frag_record_tv_note:
                //显示备注对话框
                showNoteDialog();
                break;
            case R.id.frag_record_tv_time:
                showTimeDialog();
                break;
        }
    }

    private void showTimeDialog() {
        SelectTimeDialog stDialog = new SelectTimeDialog(getContext(),accountBean);
        stDialog.show();
        stDialog.setOnConfirmListener(new SelectTimeDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String time,int year, int month, int day) {
                if(!TextUtils.isEmpty(time)){
                    timeTv.setText(time);
                    accountBean.setTime(time);
                    accountBean.setYear(year);
                    accountBean.setMonth(month);
                    accountBean.setDay(day);

                }
                stDialog.cancel();
            }
        });
    }

    /*
    * 弹出备注对话框
    * */
    public void showNoteDialog() {
        NoteDialog noteDialog=new NoteDialog(getContext(),accountBean.getNote());
        noteDialog.show();
        noteDialog.setOnConfirmListener(new NoteDialog.OnConfirmListener() {
            @Override
            public void onConfirm() {
                String msg=noteDialog.getNote();
                if(!TextUtils.isEmpty(msg)){
                    noteTv.setText(msg);
                    accountBean.setNote(msg);
                }
                noteDialog.cancel();
            }
        });
    }

    public KeyboardView getKeyboardView() {
        return keyboardView;
    }

    public void setKeyboardView(KeyboardView keyboardView) {
        this.keyboardView = keyboardView;
    }

    public EditText getMoneyEt() {
        return moneyEt;
    }

    public void setMoneyEt(EditText moneyEt) {
        this.moneyEt = moneyEt;
    }

    public ImageView getTypeIv() {
        return typeIv;
    }

    public void setTypeIv(ImageView typeIv) {
        this.typeIv = typeIv;
    }

    public TextView getTypeTv() {
        return typeTv;
    }

    public void setTypeTv(TextView typeTv) {
        this.typeTv = typeTv;
    }

    public TextView getNoteTv() {
        return noteTv;
    }

    public void setNoteTv(TextView noteTv) {
        this.noteTv = noteTv;
    }

    public TextView getTimeTv() {
        return timeTv;
    }

    public void setTimeTv(TextView timeTv) {
        this.timeTv = timeTv;
    }

    public GridView getTypeGv() {
        return typeGv;
    }

    public void setTypeGv(GridView typeGv) {
        this.typeGv = typeGv;
    }

    public List<TypeBean> getTypeBeans() {
        return typeBeans;
    }

    public void setTypeBeans(List<TypeBean> typeBeans) {
        this.typeBeans = typeBeans;
    }

    public TypeBaseAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(TypeBaseAdapter adapter) {
        this.adapter = adapter;
    }

    public AccountBean getAccountBean() {
        return accountBean;
    }

    public void setAccountBean(AccountBean accountBean) {
        this.accountBean = accountBean;
    }

}