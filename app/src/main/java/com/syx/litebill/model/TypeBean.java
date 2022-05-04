package com.syx.litebill.model;
/*
* 表示收入或支出的具体类型
* */
public class TypeBean {
    private int id;
    private String typename;//类型名称
    private int imageId; //未被选中图片id
    private int selectedImageId; //被选中图片id
    private int kind; //收入1 支出0

    public TypeBean(int id, String typename, int imageId, int selectedImageId, int kind) {
        this.id = id;
        this.typename = typename;
        this.imageId = imageId;
        this.selectedImageId = selectedImageId;
        this.kind = kind;
    }

    public TypeBean() {
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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getSelectedImageId() {
        return selectedImageId;
    }

    public void setSelectedImageId(int selectedImageId) {
        this.selectedImageId = selectedImageId;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}
