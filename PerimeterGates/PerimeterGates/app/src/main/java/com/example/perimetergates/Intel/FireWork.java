package com.example.perimetergates.Intel;

public class FireWork {
    String id = null;
    String customer = null;
    String phone = null;
    String name = null;
    String reply = null;
    String reply_date = null;

    public FireWork(String id, String customer, String phone, String name, String reply, String reply_date) {
        this.id = id;
        this.customer = customer;
        this.phone = phone;
        this.name = name;
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
        return id + " " + customer + " " + phone + " " + name + " " + reply + " " + reply_date;
    }
}

