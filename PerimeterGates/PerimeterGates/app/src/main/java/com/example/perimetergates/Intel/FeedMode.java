package com.example.perimetergates.Intel;

//id,payid,customer,phone,name,rating,message,reg_date
public class FeedMode {
    String id = null;
    String customer = null;
    String phone = null;
    String name = null;
    String rating = null;
    String message = null;
    String reg_date = null;
    String reply = null;
    String reply_date = null;

    public FeedMode(String id, String customer, String phone, String name, String rating, String message, String reg_date, String reply, String reply_date) {
        this.id = id;
        this.customer = customer;
        this.phone = phone;
        this.name = name;
        this.rating = rating;
        this.message = message;
        this.reg_date = reg_date;
        this.reply = reply;
        this.reply_date = reply_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReply_date() {
        return reply_date;
    }

    public void setReply_date(String reply_date) {
        this.reply_date = reply_date;
    }

    @Override
    public String toString() {
        return id + " " + customer + " " + phone + " " + name + " " + rating + " " + message + " " + reg_date;
    }
}
