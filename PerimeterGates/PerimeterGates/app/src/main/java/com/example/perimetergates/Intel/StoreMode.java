package com.example.perimetergates.Intel;

public class StoreMode {
    //stock_id,pur_id,category,type,quantity,price,description,image,supplier,status,pay,reg_date
    String stock_id = null;
    String pur_id = null;
    String category = null;
    String type = null;
    String quantity = null;
    String price = null;
    String description = null;
    String image = null;
    String supplier = null;
    String status = null;
    String pay = null;
    String reg_date = null;

    public StoreMode(String stock_id, String pur_id, String category, String type, String quantity, String price, String description, String image, String supplier, String status, String pay, String reg_date) {
        this.stock_id = stock_id;
        this.pur_id = pur_id;
        this.category = category;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.image = image;
        this.supplier = supplier;
        this.status = status;
        this.pay = pay;
        this.reg_date = reg_date;
    }

    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String toString() {
        return stock_id + " " + pur_id + " " + category + " " + type + " " + quantity + " " + price + " " + description + " " + supplier + " " + status + " " + pay + " " + reg_date;
    }
}
