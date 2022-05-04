package com.syx.litebill.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.syx.litebill.model.AccountBean;
import com.syx.litebill.model.BarChartItemBean;
import com.syx.litebill.model.ChartItemBean;
import com.syx.litebill.model.TypeBean;

import java.util.ArrayList;
import java.util.List;

/*
* 负责管理数据库的类
* 主要对于表中的内容进行操作，增删改查
* */
public class DBManager {
    private static SQLiteDatabase db;

    //    初始化数据库对象
    public static void initDB(Context context) {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase(); //得到数据库对象

    }
    /*
    * 读取数据库当中的数据,写入内存集合里
    * kind 表示收入或支出
    * */
    public static List<TypeBean> getTypeList(int kind){
        List<TypeBean>list = new ArrayList<>() ;
        String sql  = "select * from typetb where kind = "+kind;
        Cursor cursor=db.rawQuery(sql,null);
        while(cursor.moveToNext()){
            @SuppressLint("Range") String typename=cursor.getString(cursor.getColumnIndex("typename"));
            @SuppressLint("Range") int id=cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") int imageId=cursor.getInt(cursor.getColumnIndex("imageId"));
            @SuppressLint("Range") int selectedImageId=cursor.getInt(cursor.getColumnIndex("selectedImageId"));
            @SuppressLint("Range") int kind1=cursor.getInt(cursor.getColumnIndex("kind"));
            TypeBean typeBean = new TypeBean(id,typename,imageId,selectedImageId,kind1);
            list.add(typeBean);
        }
        cursor.close();
        return list;
    }

    /*
    * 向记账表中插入一条元素
    * sql = "create table accounttb(id integer primary key autoincrement,typename varchar(10)," +
                "selectedImageId integer, note varchar(80), money float, time varchar(60),year integer," +
                "month interger, day integer, kind integer)";
    * */
    public static void insertItemToAccounttb(AccountBean accountBean){
        ContentValues values = new ContentValues();
        values.put("typename",accountBean.getTypename());
        values.put("selectedImageId",accountBean.getSelectedImageId());
        values.put("note",accountBean.getNote());
        values.put("money",accountBean.getMoney());
        values.put("time",accountBean.getTime());
        values.put("year",accountBean.getYear());
        values.put("month",accountBean.getMonth());
        values.put("day",accountBean.getDay());
        values.put("kind",accountBean.getKind());
        db.insert("accounttb",null,values);
        Log.i("animee","insertItemToAccounttb:ok!!!"+accountBean);
    }
    /*
    * 获取记账表当中某一天的所有支出或者收入情况
    * */
    @SuppressLint("Range")
    public static ArrayList<AccountBean> getAccountListOn(int sYear, int sMonth, int sDay){
        ArrayList<AccountBean> list = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month =? and day=? order by id desc";
        Cursor cursor= db.rawQuery(sql,new String[]{sYear+"",sMonth+"",sDay+""});
        //遍历符合要求的每一行数据
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int selectedImageId = cursor.getInt(cursor.getColumnIndex("selectedImageId"));
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            int month = cursor.getInt(cursor.getColumnIndex("month"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            String typename=cursor.getString(cursor.getColumnIndex("typename"));
            String note=cursor.getString(cursor.getColumnIndex("note"));
            String time=cursor.getString(cursor.getColumnIndex("time"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            AccountBean bean=new AccountBean(id,typename,selectedImageId,note,money,time,year,month,day,kind);
            list.add(bean);
        }
        cursor.close();
        return list;
    }
    /*
     * 获取记账表当中某一月的所有支出或者收入情况
     * */
    @SuppressLint("Range")
    public static ArrayList<AccountBean> getAccountListOn(int sYear, int sMonth){
        ArrayList<AccountBean> list = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month =? order by id desc";
        Cursor cursor= db.rawQuery(sql,new String[]{sYear+"",sMonth+""});
        //遍历符合要求的每一行数据
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int selectedImageId = cursor.getInt(cursor.getColumnIndex("selectedImageId"));
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            int month = cursor.getInt(cursor.getColumnIndex("month"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            String typename=cursor.getString(cursor.getColumnIndex("typename"));
            String note=cursor.getString(cursor.getColumnIndex("note"));
            String time=cursor.getString(cursor.getColumnIndex("time"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            AccountBean bean=new AccountBean(id,typename,selectedImageId,note,money,time,year,month,day,kind);
            list.add(bean);
        }
        cursor.close();
        return list;
    }
    /*
     * 获取记账表当中某一月的所有支出或者收入情况
     * */
    @NonNull
    @SuppressLint("Range")
    public static ArrayList<AccountBean> getAccountListOn(int sYear){
        ArrayList<AccountBean> list = new ArrayList<>();
        String sql = "select * from accounttb where year=? order by id desc";
        Cursor cursor= db.rawQuery(sql,new String[]{sYear+""});
        //遍历符合要求的每一行数据
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int selectedImageId = cursor.getInt(cursor.getColumnIndex("selectedImageId"));
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            int month = cursor.getInt(cursor.getColumnIndex("month"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            String typename=cursor.getString(cursor.getColumnIndex("typename"));
            String note=cursor.getString(cursor.getColumnIndex("note"));
            String time=cursor.getString(cursor.getColumnIndex("time"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            AccountBean bean=new AccountBean(id,typename,selectedImageId,note,money,time,year,month,day,kind);
            list.add(bean);
        }
        cursor.close();
        return list;
    }
    /*
    * 获取某一天的支出或收入的总金额
    * 输入: int year, int month, int day, kind: 支出==0 收入==1
    * */
    @SuppressLint("Range")
    public static float getSumMoneyOn(int kind, int year, int month, int day){
        float total =0.0f;
        String sql = "select sum(money) from accounttb where kind=? and year=? and month=? and day=?";
        Cursor cursor=db.rawQuery(sql,new String[]{kind+"",year+"",month+"",day+""});
        if(cursor.moveToFirst()){
            total=cursor.getFloat(cursor.getColumnIndex("sum(money)"));
        }
        cursor.close();
        return total;
    }
    @SuppressLint("Range")
    public static float getSumMoneyOn(int kind, int year, int month){
        float total =0.0f;
        String sql = "select sum(money) from accounttb where kind=? and year=? and month=?";
        Cursor cursor=db.rawQuery(sql,new String[]{kind+"",year+"",month+""});
        if(cursor.moveToFirst()){
            total=cursor.getFloat(cursor.getColumnIndex("sum(money)"));
        }
        cursor.close();
        return total;
    }
    @SuppressLint("Range")
    public static float getSumMoneyOn(int kind, int year){
        float total =0.0f;
        String sql = "select sum(money) from accounttb where kind=? and year=?";
        Cursor cursor=db.rawQuery(sql,new String[]{kind+"",year+""});
        if(cursor.moveToFirst()){
            total=cursor.getFloat(cursor.getColumnIndex("sum(money)"));
        }
        cursor.close();
        return total;
    }
    @SuppressLint("Range")
    public static float getSumMoneyOn(int kind){
        float total =0.0f;
        String sql = "select sum(money) from accounttb where kind=?";
        Cursor cursor=db.rawQuery(sql,new String[]{kind+""});
        if(cursor.moveToFirst()){
            total=cursor.getFloat(cursor.getColumnIndex("sum(money)"));
        }
        cursor.close();
        return total;
    }
    /*
    * 根据传入的id,删除accounttb中的一条数据
    * */
    public static int deleteItemFromAccounttbById(int id){
        int i = db.delete("accounttb","id=?",new String[]{id+""});
        return i;
    }


    /*
    * 根据备注搜索收入或支出的情况列表
    * */
    @SuppressLint("Range")
    public static ArrayList<AccountBean> searchFromAccounttbByNote(String msg){
        ArrayList<AccountBean> list = new ArrayList<>();
        String sql = "select * from accounttb where note like '%"+msg+"%' order by id desc";
        Cursor cursor= db.rawQuery(sql,null);
        //遍历符合要求的每一行数据
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int selectedImageId = cursor.getInt(cursor.getColumnIndex("selectedImageId"));
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            int month = cursor.getInt(cursor.getColumnIndex("month"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            String typename=cursor.getString(cursor.getColumnIndex("typename"));
            String note=cursor.getString(cursor.getColumnIndex("note"));
            String time=cursor.getString(cursor.getColumnIndex("time"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            AccountBean bean=new AccountBean(id,typename,selectedImageId,note,money,time,year,month,day,kind);
            list.add(bean);
        }
        cursor.close();
        return list;
    }
    /*
    * 查询accounttb中有记录年份列表
    * */
    public static ArrayList<Integer>getYearListFromAccounttb(){
        ArrayList<Integer>list=new ArrayList<>();
        String sql="select distinct(year) from accounttb order by year asc";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            @SuppressLint("Range") int year=cursor.getInt(cursor.getColumnIndex("year"));
            list.add(year);
        }
        return list;
    }
    /*
    * 统计某月份支出或收入情况有多少条
    * 0-支出, 1-收入
    * */
    @SuppressLint("Range")
    public static int getCountItemOneMonth(int kind, int year, int month){
        int total=0;
        String sql="select count(money) from accounttb where kind=? and year=? and month=?";
        Cursor cursor = db.rawQuery(sql, new String[]{kind + "", year + "", month + ""});
        if(cursor.moveToFirst()){
            int count=cursor.getInt(cursor.getColumnIndex("count(money)"));
            total = count;
        }
        return total;
    }

    /*
    * 查询指定月份的收入或支出每一种的类型，图片id，总钱数，占当月总支出/收入的百分比
    * */
    @SuppressLint("Range")
    public static ArrayList<ChartItemBean>getChartListFromAccounttb(int kind, int year, int month){
        ArrayList<ChartItemBean> list = new ArrayList<>();
        float sumMoneyThisMonth = getSumMoneyOn(kind,year,month);
        String sql = "select typename,selectedImageId,sum(money)as total from accounttb where kind=? and year=? and month=? group by typename "+
                "order by total desc";
        Cursor cursor = db.rawQuery(sql, new String[]{kind + "", year + "", month + ""});
        while(cursor.moveToNext()){
             int sImageId=cursor.getInt(cursor.getColumnIndex("selectedImageId"));
             String typename = cursor.getString(cursor.getColumnIndex("typename"));
             float total=cursor.getInt(cursor.getColumnIndex("total"));
             float ratio=total/sumMoneyThisMonth;
            ChartItemBean chartItemBean = new ChartItemBean(sImageId, typename, ratio, total);
            list.add(chartItemBean);
        }
        return list;
    }

    /**
     * 获取这个月当中某一天收入支出最大的金额，金额是多少
     * */
    @SuppressLint("Range")
    public static float getMaxMoneyOneDayInMonth(int kind,int year,int month){
        String sql = "select sum(money) from accounttb where year=? and month=? and kind=? group by day order by sum(money) desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            return money;
        }
        return 0;
    }

    /** 根据指定月份每一日收入或者支出的总钱数的集合*/
    @SuppressLint("Range")
    public static ArrayList<BarChartItemBean>getTotalMoneyEveryDayInMonth(int kind,int year, int month){
        String sql = "select day,sum(money) from accounttb where year=? and month=? and kind=? group by day";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        ArrayList<BarChartItemBean>list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            float smoney = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            BarChartItemBean itemBean = new BarChartItemBean(year, month, day, smoney);
            list.add(itemBean);
        }
        return list;
    }
}


