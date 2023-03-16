package com.example.perimetergates.Intel;

public class Purmode {
    //pur_id,category,type,quantity,reg_date,purchase
    String pur_id = null;
    String category = null;
    String type = null;
    String quantity = null;
    String reg_date = null;

    public Purmode(String pur_id, String category, String type, String quantity, String reg_date) {
        this.pur_id = pur_id;
        this.category = category;
        this.type = type;
        this.quantity = quantity;
        this.reg_date = reg_date;
    }

    public String getPur_id() {
        return pur_id;
    }

    public void setPur_id(String pur_id) {
        this.pur_id = pur_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String toString() {
        return pur_id + " " + category + " " + reg_date + " " + quantity + " " + type;
    }
}
