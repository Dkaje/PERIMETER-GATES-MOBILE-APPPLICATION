package com.example.perimetergates.Intel;

public class StoreMo {
    //id,category,type,image,quantity,price,reg_date
    String id = null;
    String category = null;
    String type = null;
    String image = null;
    String quantity = null;
    String price = null;
    String reg_date = null;

    public StoreMo(String id, String category, String type, String image, String quantity, String price, String reg_date) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.reg_date = reg_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String toString() {
        return id + " " + category + " " + type + " " + quantity + " " + price + " " + reg_date;
    }
}
