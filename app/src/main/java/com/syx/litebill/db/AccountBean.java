package com.syx.litebill.db;
/*描述记录一条数据的相关内容类*/
public class AccountBean {
    private int id;
    private String typename; //类型
    private int selectedImageId; //被选中类型图片
    private String note; //备注信息
    private float money; //金额
    private String time; //保存时间字符串
    private int year;
    private int month;
    private int day;
    private int kind;//收入: 1,支出:0

    public AccountBean() {
    }

    public AccountBean(int id, String typename, int selectedImageId, String note, float money,
                       String time, int year, int month, int day, int kind) {
        this.id = id;
        this.typename = typename;
        this.selectedImageId = selectedImageId;
        this.note = note;
        this.money = money;
        this.time = time;
        this.year = year;
        this.month = month;
        this.day = day;
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public int getSelectedImageId() {
        return selectedImageId;
    }

    public void setSelectedImageId(int selectedImageId) {
        this.selectedImageId = selectedImageId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

}
