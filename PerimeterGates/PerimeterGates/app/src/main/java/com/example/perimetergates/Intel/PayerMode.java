package com.example.perimetergates.Intel;

public class PayerMode {
    String payid = null;
    String entry = null;
    String mpesa = null;
    String amount = null;
    String orders = null;
    String ship = null;
    String cust_id = null;
    String name = null;
    String phone = null;
    String mode = null;
    String location = null;
    String status = null;
    String driver = null;
    String drive = null;
    String customer = null;
    String comment = null;
    String reg_date = null;

    public PayerMode(String payid, String entry, String mpesa, String amount, String orders, String ship, String cust_id, String name, String phone, String mode, String location, String status, String driver, String drive, String customer, String comment, String reg_date) {
        this.payid = payid;
        this.entry = entry;
        this.mpesa = mpesa;
        this.amount = amount;
        this.orders = orders;
        this.ship = ship;
        this.cust_id = cust_id;
        this.name = name;
        this.phone = phone;
        this.mode = mode;
        this.location = location;
        this.status = status;
        this.driver = driver;
        this.drive = drive;
        this.customer = customer;
        this.comment = comment;
        this.reg_date = reg_date;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getMpesa() {
        return mpesa;
    }

    public void setMpesa(String mpesa) {
        this.mpesa = mpesa;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getShip() {
        return ship;
    }

    public void setShip(String ship) {
        this.ship = ship;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDrive() {
        return drive;
    }

    public void setDrive(String drive) {
        this.drive = drive;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }
    public String toString() {
        return payid + " " + entry + " " + mpesa + " " + amount + " " + orders + " " + ship + " " + cust_id + " " + name + " " + phone + " " + mode + " " + location + " " + status + " " + driver + " " + drive + " " + customer + " " + comment + " " + reg_date;
    }
}
