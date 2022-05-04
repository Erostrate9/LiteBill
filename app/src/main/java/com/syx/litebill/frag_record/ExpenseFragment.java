package com.syx.litebill.frag_record;


import com.syx.litebill.R;
import com.syx.litebill.model.AccountBean;
import com.syx.litebill.db.DBManager;
import com.syx.litebill.model.TypeBean;

import java.util.List;

/*
* 记录页面的支出模块
* */
public class ExpenseFragment extends BaseRecordFragment {

    @Override
    public void getIconAndShow() {
        List<TypeBean> outList = DBManager.getTypeList(0);
        getTypeBeans().addAll(outList);
        getAdapter().notifyDataSetChanged();
        getTypeIv().setImageResource(R.mipmap.ic_qita_fs);
        getAccountBean().setKind(0);
    }

    @Override
    public void saveAccountToDB(AccountBean accountBean) {
        accountBean.setKind(0);
        DBManager.insertItemToAccounttb(accountBean);
    }
}