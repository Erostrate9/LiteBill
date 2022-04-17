package com.syx.litebill.frag_record;


import com.syx.litebill.R;
import com.syx.litebill.db.AccountBean;
import com.syx.litebill.db.DBManager;
import com.syx.litebill.db.TypeBean;

import java.util.List;

/*
 * 记录页面的收入模块
 * */
public class IncomeFragment extends BaseRecordFragment {

    @Override
    public void getIconAndShow() {
        List<TypeBean> inList = DBManager.getTypeList(1);
        getTypeBeans().addAll(inList);
        getAdapter().notifyDataSetChanged();
        getTypeIv().setImageResource(R.mipmap.in_qt_fs);
    }

    @Override
    public void saveAccountToDB(AccountBean accountBean) {
        accountBean.setKind(1);
        DBManager.insertItemToAccounttb(accountBean);
    }

}