package com.syx.litebill.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
    }

}
